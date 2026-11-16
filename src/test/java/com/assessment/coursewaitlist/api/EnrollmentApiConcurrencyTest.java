package com.assessment.coursewaitlist.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

/**
 * Concurrency test over real HTTP: the application listens on a random port
 * and requests arrive on the embedded server's own threads.
 *
 * <p>Network and JSON handling spread requests out in time, so this test
 * overlaps the offering's critical section far less than the in-process
 * domain concurrency tests do. It is an end-to-end check that the HTTP layer
 * adds no shared state of its own and reports results faithfully under load;
 * the evidence for the lock itself is in the domain tests. See
 * docs/DESIGN.md, limitation L6.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class EnrollmentApiConcurrencyTest {

	private static final int CAPACITY = 2;

	private static final int LEARNERS = 32;

	private static final int READERS = 4;

	private static final int ROUNDS = 12;

	@Autowired
	TestRestTemplate rest;

	@SuppressWarnings("unchecked")
	private static List<String> ids(Map<String, Object> body, String field) {
		return (List<String>) body.get(field);
	}

	/** Runs the tasks concurrently, one thread each, released together from a start gate. */
	private static <T> List<T> runTogether(ExecutorService executor, List<Callable<T>> tasks) throws Exception {
		CountDownLatch ready = new CountDownLatch(tasks.size());
		CountDownLatch start = new CountDownLatch(1);
		List<Future<T>> futures = new ArrayList<>();
		for (Callable<T> task : tasks) {
			futures.add(executor.submit(() -> {
				ready.countDown();
				start.await();
				return task.call();
			}));
		}
		assertThat(ready.await(30, TimeUnit.SECONDS)).as("all request threads ready").isTrue();
		start.countDown();
		List<T> results = new ArrayList<>();
		for (Future<T> future : futures) {
			results.add(future.get(60, TimeUnit.SECONDS));
		}
		return results;
	}

	@Test
	@DisplayName("TC-A-19: concurrent HTTP enrollments and drops: exactly capacity enrolled, the rest waitlisted, every state read consistent")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void tcA19_concurrentHttpEnrollmentsNeverExceedCapacity() throws Exception {
		for (int i = 0; i < LEARNERS; i++) {
			ResponseEntity<Map> created = rest.postForEntity("/api/learners",
					Map.of("id", "L" + i, "name", "Learner " + i, "completedCourseCodes", List.of("CS101")), Map.class);
			assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		}

		ExecutorService executor = Executors.newFixedThreadPool(LEARNERS + READERS);
		AtomicBoolean writing = new AtomicBoolean(true);
		try {
			// Readers poll the state for the whole test, while enrollments and drops are in flight.
			List<Future<Integer>> readers = new ArrayList<>();
			for (int r = 0; r < READERS; r++) {
				readers.add(executor.submit(() -> {
					int reads = 0;
					do {
						assertConsistent(rest.getForEntity("/api/offering", Map.class).getBody());
						reads++;
					}
					while (writing.get());
					return reads;
				}));
			}

			for (int round = 0; round < ROUNDS; round++) {
				// All learners request a seat at once.
				List<Callable<ResponseEntity<Map>>> enrollTasks = new ArrayList<>();
				for (int i = 0; i < LEARNERS; i++) {
					String learnerId = "L" + i;
					enrollTasks.add(() -> rest.postForEntity("/api/offering/enrollments", Map.of("learnerId", learnerId),
							Map.class));
				}
				List<String> enrolledByResponse = new ArrayList<>();
				List<String> waitlistedByResponse = new ArrayList<>();
				for (ResponseEntity<Map> response : runTogether(executor, enrollTasks)) {
					assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
					Map<String, Object> body = response.getBody();
					assertThat(ids(body, "promotedLearnerIds")).isEmpty();
					String status = (String) body.get("status");
					assertThat(status).isIn("ENROLLED", "WAITLISTED");
					("ENROLLED".equals(status) ? enrolledByResponse : waitlistedByResponse)
						.add((String) body.get("learnerId"));
				}

				// Exactly the capacity was enrolled; everyone else is waiting; nobody appears twice.
				assertThat(enrolledByResponse).as("round " + round).hasSize(CAPACITY).doesNotHaveDuplicates();
				assertThat(waitlistedByResponse).as("round " + round).hasSize(LEARNERS - CAPACITY).doesNotHaveDuplicates();
				Map<String, Object> full = rest.getForEntity("/api/offering", Map.class).getBody();
				assertConsistent(full);
				// The state agrees with what each client was told.
				assertThat(ids(full, "enrolledLearnerIds")).containsExactlyInAnyOrderElementsOf(enrolledByResponse);
				assertThat(ids(full, "waitlistedLearnerIds")).containsExactlyInAnyOrderElementsOf(waitlistedByResponse);
				assertThat(full.get("availableSeats")).isEqualTo(0);

				// All learners drop at once. Each is enrolled or waiting, so every drop succeeds exactly once.
				List<Callable<ResponseEntity<Map>>> dropTasks = new ArrayList<>();
				for (int i = 0; i < LEARNERS; i++) {
					String learnerId = "L" + i;
					dropTasks.add(() -> rest.exchange("/api/offering/enrollments/" + learnerId, HttpMethod.DELETE, null,
							Map.class));
				}
				List<String> promotedDuringDrops = new ArrayList<>();
				for (ResponseEntity<Map> response : runTogether(executor, dropTasks)) {
					assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
					Map<String, Object> body = response.getBody();
					assertThat((String) body.get("status")).isIn("DROPPED", "DROPPED_FROM_WAITLIST");
					promotedDuringDrops.addAll(ids(body, "promotedLearnerIds"));
				}
				// A learner can be promoted at most once per round.
				assertThat(promotedDuringDrops).as("round " + round).doesNotHaveDuplicates();
				Map<String, Object> empty = rest.getForEntity("/api/offering", Map.class).getBody();
				assertConsistent(empty);
				assertThat(ids(empty, "enrolledLearnerIds")).as("round " + round).isEmpty();
				assertThat(ids(empty, "waitlistedLearnerIds")).as("round " + round).isEmpty();
				assertThat(empty.get("availableSeats")).isEqualTo(CAPACITY);
			}

			writing.set(false);
			for (Future<Integer> reader : readers) {
				assertThat(reader.get(60, TimeUnit.SECONDS)).as("state reads by a reader").isPositive();
			}
		}
		finally {
			writing.set(false);
			executor.shutdownNow();
		}
	}

	@Test
	@DisplayName("TC-A-20: concurrent HTTP registrations of the same id: exactly one 201, the rest 409, and the registered learner keeps the winner's data")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void tcA20_concurrentHttpDuplicateRegistrationsCreateOneLearner() throws Exception {
		int requests = 24;
		int rounds = 15;
		ExecutorService executor = Executors.newFixedThreadPool(requests);
		try {
			for (int round = 0; round < rounds; round++) {
				String learnerId = "DUP" + round;
				List<Callable<ResponseEntity<Map>>> tasks = new ArrayList<>();
				for (int t = 0; t < requests; t++) {
					// Only even-numbered candidates hold the prerequisite, so the winner's data decides eligibility.
					List<String> completed = (t % 2 == 0) ? List.of("CS101") : List.of("MATH100");
					Map<String, Object> body = Map.of("id", learnerId, "name", "Candidate " + t, "completedCourseCodes",
							completed);
					tasks.add(() -> rest.postForEntity("/api/learners", body, Map.class));
				}

				List<Map<String, Object>> created = new ArrayList<>();
				int conflicts = 0;
				for (ResponseEntity<Map> response : runTogether(executor, tasks)) {
					if (response.getStatusCode() == HttpStatus.CREATED) {
						created.add(response.getBody());
					}
					else {
						// Nothing but the duplicate-registration result is acceptable: no 500, no other error.
						assertThat(response.getStatusCode()).as("round " + round).isEqualTo(HttpStatus.CONFLICT);
						assertThat(response.getBody().get("code")).isEqualTo("LEARNER_ALREADY_EXISTS");
						conflicts++;
					}
				}

				assertThat(created).as("successful registrations in round " + round).hasSize(1);
				assertThat(conflicts).as("rejected registrations in round " + round).isEqualTo(requests - 1);
				// The learner that exists is the winner, with the winner's completions and nobody else's.
				Map<String, Object> winner = created.get(0);
				assertThat(winner.get("id")).isEqualTo(learnerId);
				boolean winnerIsEligible = ids(winner, "completedCourseCodes").contains("CS101");
				ResponseEntity<Map> enrollment = rest.postForEntity("/api/offering/enrollments",
						Map.of("learnerId", learnerId), Map.class);
				assertThat(enrollment.getStatusCode()).as("round " + round + ", winner " + winner.get("name"))
					.isEqualTo(winnerIsEligible ? HttpStatus.OK : HttpStatus.UNPROCESSABLE_ENTITY);
				// A later registration of the same id is still a duplicate.
				assertThat(rest
					.postForEntity("/api/learners", Map.of("id", learnerId, "name", "Late", "completedCourseCodes",
							List.of("CS101")), Map.class)
					.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
				// Leave the offering as it was for the next round.
				if (winnerIsEligible) {
					assertThat(rest
						.exchange("/api/offering/enrollments/" + learnerId, HttpMethod.DELETE, null, Map.class)
						.getStatusCode()).isEqualTo(HttpStatus.OK);
				}
			}
			Map<String, Object> state = rest.getForEntity("/api/offering", Map.class).getBody();
			assertConsistent(state);
			assertThat(ids(state, "enrolledLearnerIds")).isEmpty();
			assertThat(ids(state, "waitlistedLearnerIds")).isEmpty();
		}
		finally {
			executor.shutdownNow();
		}
	}

	private static void assertConsistent(Map<String, Object> state) {
		List<String> enrolled = ids(state, "enrolledLearnerIds");
		List<String> waitlisted = ids(state, "waitlistedLearnerIds");
		assertThat(enrolled).hasSizeLessThanOrEqualTo(CAPACITY).doesNotHaveDuplicates();
		assertThat(waitlisted).doesNotHaveDuplicates();
		Set<String> overlap = new HashSet<>(enrolled);
		overlap.retainAll(waitlisted);
		assertThat(overlap).as("learners both enrolled and waitlisted").isEmpty();
		assertThat(state.get("availableSeats")).as("available seats").isEqualTo(CAPACITY - enrolled.size());
		// Everyone is eligible, so nobody may be waiting while a seat is free.
		if (!waitlisted.isEmpty()) {
			assertThat(state.get("availableSeats")).as("free seats while learners wait").isEqualTo(0);
		}
	}

}
