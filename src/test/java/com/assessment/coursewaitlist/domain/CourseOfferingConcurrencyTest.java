package com.assessment.coursewaitlist.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Focused concurrency tests for the domain model.
 *
 * <p>Every task gets its own thread and waits on a shared start gate, so all
 * tasks of a round are released together. Each scenario is repeated to give
 * interleavings a chance to differ. With correct code these tests always
 * pass: they make no timing assumptions. Their ability to detect a missing
 * lock is probabilistic; see docs/DESIGN.md, limitation L6, for what was
 * observed when the locks were deliberately removed.
 */
class CourseOfferingConcurrencyTest {

	private static final String PREREQUISITE = "CS101";

	private static final Course COURSE = new Course("CS201", "Data Structures", Set.of(PREREQUISITE));

	private static final int ROUNDS = 25;

	private static Learner eligible(String id) {
		Learner learner = new Learner(id, "Learner " + id);
		learner.markCompleted(PREREQUISITE);
		return learner;
	}

	/**
	 * Runs the tasks concurrently, one thread per task. Every thread signals
	 * that it is ready and then blocks on a start gate; the gate opens once all
	 * are ready. Results are returned in task order. A failure inside a task
	 * surfaces as an exception from this method.
	 */
	private static <T> List<T> runTogether(List<Callable<T>> tasks) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(tasks.size());
		try {
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
			assertThat(ready.await(30, TimeUnit.SECONDS)).as("all worker threads ready").isTrue();
			start.countDown();
			List<T> results = new ArrayList<>();
			for (Future<T> future : futures) {
				results.add(future.get(60, TimeUnit.SECONDS));
			}
			return results;
		}
		finally {
			executor.shutdownNow();
		}
	}

	/** Checks the offering's invariants on one atomic snapshot. */
	private static void assertConsistent(OfferingSnapshot snapshot, int capacity) {
		List<String> enrolled = snapshot.enrolledLearnerIds();
		List<String> waitlisted = snapshot.waitlistedLearnerIds();
		assertThat(enrolled).hasSizeLessThanOrEqualTo(capacity).doesNotHaveDuplicates();
		assertThat(waitlisted).doesNotHaveDuplicates();
		Set<String> overlap = new HashSet<>(enrolled);
		overlap.retainAll(waitlisted);
		assertThat(overlap).as("learners both enrolled and waitlisted").isEmpty();
		assertThat(snapshot.availableSeats()).as("available seats").isEqualTo(capacity - enrolled.size());
	}

	@Test
	@DisplayName("TC-C-01: concurrent enrollment of distinct learners fills exactly to capacity and waitlists the rest")
	void tcC01_concurrentDistinctEnrollmentsNeverExceedCapacity() throws Exception {
		int capacity = 5;
		int learners = 40;
		for (int round = 0; round < ROUNDS; round++) {
			CourseOffering offering = new CourseOffering("CS201-A", COURSE, capacity);
			List<Callable<EnrollmentResult>> tasks = new ArrayList<>();
			for (int i = 0; i < learners; i++) {
				Learner learner = eligible("L" + i);
				tasks.add(() -> offering.enroll(learner));
			}

			List<EnrollmentResult> results = runTogether(tasks);

			assertThat(results).filteredOn(r -> r.outcome() == EnrollmentResult.Outcome.ENROLLED).hasSize(capacity);
			assertThat(results).filteredOn(r -> r.outcome() == EnrollmentResult.Outcome.WAITLISTED)
				.hasSize(learners - capacity);
			OfferingSnapshot snapshot = offering.snapshot();
			assertConsistent(snapshot, capacity);
			assertThat(snapshot.enrolledLearnerIds()).hasSize(capacity);
			assertThat(snapshot.waitlistedLearnerIds()).hasSize(learners - capacity);
			Set<String> everyone = new HashSet<>(snapshot.enrolledLearnerIds());
			everyone.addAll(snapshot.waitlistedLearnerIds());
			assertThat(everyone).hasSize(learners);
		}
	}

	@Test
	@DisplayName("TC-C-02: concurrent enrollment of the same learner succeeds exactly once")
	void tcC02_concurrentDuplicateEnrollmentsSucceedOnce() throws Exception {
		int requests = 32;
		for (int round = 0; round < ROUNDS; round++) {
			// Enrolled case: a seat is free, so exactly one request takes it.
			CourseOffering open = new CourseOffering("CS201-A", COURSE, 3);
			List<Callable<EnrollmentResult>> enrollTasks = new ArrayList<>();
			for (int i = 0; i < requests; i++) {
				enrollTasks.add(() -> open.enroll(eligible("SAME")));
			}
			List<EnrollmentResult> enrollResults = runTogether(enrollTasks);
			assertThat(enrollResults).filteredOn(r -> r.outcome() == EnrollmentResult.Outcome.ENROLLED).hasSize(1);
			assertThat(enrollResults).filteredOn(r -> r.outcome() == EnrollmentResult.Outcome.ALREADY_ENROLLED)
				.hasSize(requests - 1);
			assertThat(open.enrolledLearnerIds()).containsExactly("SAME");
			assertThat(open.waitlistedLearnerIds()).isEmpty();

			// Waitlisted case: the offering is full, so exactly one request joins the waitlist.
			CourseOffering full = new CourseOffering("CS201-B", COURSE, 1);
			full.enroll(eligible("HOLDER"));
			List<Callable<EnrollmentResult>> waitlistTasks = new ArrayList<>();
			for (int i = 0; i < requests; i++) {
				waitlistTasks.add(() -> full.enroll(eligible("SAME")));
			}
			List<EnrollmentResult> waitlistResults = runTogether(waitlistTasks);
			assertThat(waitlistResults).filteredOn(r -> r.outcome() == EnrollmentResult.Outcome.WAITLISTED).hasSize(1);
			assertThat(waitlistResults).filteredOn(r -> r.outcome() == EnrollmentResult.Outcome.ALREADY_WAITLISTED)
				.hasSize(requests - 1);
			assertThat(full.enrolledLearnerIds()).containsExactly("HOLDER");
			assertThat(full.waitlistedLearnerIds()).containsExactly("SAME");
		}
	}

	@Test
	@DisplayName("TC-C-03: concurrent duplicate drops of several learners: each is dropped once and each freed seat promotes exactly one waiter, in FIFO order")
	void tcC03_concurrentDuplicateDropsDropOnceAndPromoteOnce() throws Exception {
		int seats = 8;
		int waiters = 12;
		int dropsPerLearner = 4;
		for (int round = 0; round < ROUNDS; round++) {
			CourseOffering offering = new CourseOffering("CS201-A", COURSE, seats);
			for (int i = 1; i <= seats; i++) {
				offering.enroll(eligible("E" + i));
			}
			List<String> expectedPromoted = new ArrayList<>();
			List<String> expectedStillWaiting = new ArrayList<>();
			for (int i = 1; i <= waiters; i++) {
				offering.enroll(eligible("W" + i));
				(i <= seats ? expectedPromoted : expectedStillWaiting).add("W" + i);
			}
			// Several threads drop each enrolled learner, and all enrolled learners are dropped at once.
			List<Callable<DropResult>> tasks = new ArrayList<>();
			for (int i = 1; i <= seats; i++) {
				String learnerId = "E" + i;
				for (int d = 0; d < dropsPerLearner; d++) {
					tasks.add(() -> offering.drop(learnerId));
				}
			}

			List<DropResult> results = runTogether(tasks);

			// Each learner is dropped exactly once; every other attempt is a repeated drop.
			assertThat(results).filteredOn(r -> r.outcome() == DropResult.Outcome.DROPPED).hasSize(seats);
			assertThat(results).filteredOn(r -> r.outcome() == DropResult.Outcome.NOT_ENROLLED)
				.hasSize(seats * (dropsPerLearner - 1));
			for (int i = 1; i <= seats; i++) {
				List<DropResult> forLearner = results.subList((i - 1) * dropsPerLearner, i * dropsPerLearner);
				assertThat(forLearner).as("drops of E" + i)
					.filteredOn(r -> r.outcome() == DropResult.Outcome.DROPPED)
					.hasSize(1);
			}
			// Each successful drop promoted exactly one waiter; a repeated drop promoted nobody.
			assertThat(results).filteredOn(r -> r.outcome() == DropResult.Outcome.DROPPED)
				.allSatisfy(r -> assertThat(r.promotedLearnerIds()).hasSize(1));
			// The first eight waiters, and only they, were promoted, each exactly once.
			assertThat(results).flatExtracting(DropResult::promotedLearnerIds)
				.containsExactlyInAnyOrderElementsOf(expectedPromoted);
			OfferingSnapshot snapshot = offering.snapshot();
			assertConsistent(snapshot, seats);
			assertThat(snapshot.enrolledLearnerIds()).containsExactlyInAnyOrderElementsOf(expectedPromoted);
			// The learners still waiting kept their FIFO order.
			assertThat(snapshot.waitlistedLearnerIds()).containsExactlyElementsOf(expectedStillWaiting);
		}
	}

	@Test
	@DisplayName("TC-C-04: concurrent mixed enrolls and drops leave a consistent state")
	void tcC04_concurrentMixedEnrollsAndDropsKeepInvariants() throws Exception {
		int capacity = 4;
		int learners = 40;
		for (int round = 0; round < ROUNDS; round++) {
			CourseOffering offering = new CourseOffering("CS201-A", COURSE, capacity);
			List<Callable<Void>> tasks = new ArrayList<>();
			for (int i = 0; i < learners; i++) {
				Learner learner = eligible("L" + i);
				boolean leaves = i % 2 == 0;
				tasks.add(() -> {
					offering.enroll(learner);
					if (leaves) {
						// The learner is enrolled or waitlisted here, so this drop always removes them.
						DropResult drop = offering.drop(learner.id());
						assertThat(drop.outcome()).isNotEqualTo(DropResult.Outcome.NOT_ENROLLED);
						// A repeated drop must be a no-op.
						assertThat(offering.drop(learner.id()).outcome()).isEqualTo(DropResult.Outcome.NOT_ENROLLED);
					}
					return null;
				});
			}

			runTogether(tasks);

			OfferingSnapshot snapshot = offering.snapshot();
			assertConsistent(snapshot, capacity);
			List<String> enrolled = snapshot.enrolledLearnerIds();
			List<String> waitlisted = snapshot.waitlistedLearnerIds();
			Set<String> present = new HashSet<>(enrolled);
			present.addAll(waitlisted);
			Set<String> expected = new HashSet<>();
			for (int i = 1; i < learners; i += 2) {
				expected.add("L" + i);
			}
			// Exactly the learners who never dropped remain, each once.
			assertThat(present).isEqualTo(expected);
			assertThat(enrolled.size() + waitlisted.size()).isEqualTo(expected.size());
			// Everyone is eligible, so no seat may be vacant while anyone waits.
			assertThat(enrolled).hasSize(capacity);
			assertThat(waitlisted).hasSize(expected.size() - capacity);
		}
	}

	@Test
	@DisplayName("TC-C-05: eligibility is checked against one consistent set of completions while they change concurrently")
	void tcC05_eligibilityCheckIsAtomicAcrossSeveralPrerequisites() throws Exception {
		Course twoPrerequisites = new Course("CS301", "Algorithms", Set.of("CS101", "CS201"));
		Learner learner = new Learner("L", "Learner L");
		learner.markCompleted("CS101");
		int readers = 4;
		int checksPerReader = 200_000;
		CountDownLatch readersDone = new CountDownLatch(readers);
		List<Callable<Integer>> tasks = new ArrayList<>();
		// The writer moves the learner between {CS101} and {CS201}, always through the empty
		// set, so the learner never holds both prerequisites and is never eligible.
		tasks.add(() -> {
			int cycles = 0;
			while (readersDone.getCount() > 0) {
				learner.revokeCompletion("CS101");
				learner.markCompleted("CS201");
				learner.revokeCompletion("CS201");
				learner.markCompleted("CS101");
				cycles++;
			}
			return cycles;
		});
		for (int r = 0; r < readers; r++) {
			tasks.add(() -> {
				int wronglyEligible = 0;
				try {
					for (int i = 0; i < checksPerReader; i++) {
						if (learner.isEligibleFor(twoPrerequisites)) {
							wronglyEligible++;
						}
					}
				}
				finally {
					readersDone.countDown();
				}
				return wronglyEligible;
			});
		}

		List<Integer> results = runTogether(tasks);

		assertThat(results.get(0)).as("writer cycles completed while readers ran").isPositive();
		assertThat(results.subList(1, results.size())).as("checks that saw a state the learner was never in")
			.containsOnly(0);
		// At rest the learner holds only CS101.
		assertThat(learner.completedCourseCodes()).containsExactly("CS101");
		assertThat(learner.isEligibleFor(twoPrerequisites)).isFalse();
	}

	@Test
	@DisplayName("TC-C-06: every snapshot taken during concurrent enrolls and drops is internally consistent")
	void tcC06_snapshotsTakenDuringChurnAreConsistent() throws Exception {
		int capacity = 3;
		int writers = 12;
		int cyclesPerWriter = 300;
		int readers = 4;
		for (int round = 0; round < 5; round++) {
			CourseOffering offering = new CourseOffering("CS201-A", COURSE, capacity);
			CountDownLatch writersDone = new CountDownLatch(writers);
			List<Callable<Integer>> tasks = new ArrayList<>();
			for (int w = 0; w < writers; w++) {
				Learner learner = eligible("L" + w);
				tasks.add(() -> {
					try {
						for (int i = 0; i < cyclesPerWriter; i++) {
							offering.enroll(learner);
							offering.drop(learner.id());
						}
					}
					finally {
						writersDone.countDown();
					}
					return 0;
				});
			}
			for (int r = 0; r < readers; r++) {
				tasks.add(() -> {
					int taken = 0;
					do {
						OfferingSnapshot snapshot = offering.snapshot();
						assertConsistent(snapshot, capacity);
						// Everyone is eligible, so nobody may be waiting while a seat is free.
						if (!snapshot.waitlistedLearnerIds().isEmpty()) {
							assertThat(snapshot.availableSeats()).as("free seats while learners wait").isZero();
						}
						taken++;
					}
					while (writersDone.getCount() > 0);
					return taken;
				});
			}

			List<Integer> results = runTogether(tasks);

			assertThat(results.subList(writers, results.size())).as("snapshots taken by each reader")
				.allMatch(taken -> taken > 0);
			// Every writer ended on a drop, so the offering is empty again.
			OfferingSnapshot end = offering.snapshot();
			assertThat(end.enrolledLearnerIds()).isEmpty();
			assertThat(end.waitlistedLearnerIds()).isEmpty();
			assertThat(end.availableSeats()).isEqualTo(capacity);
		}
	}

}
