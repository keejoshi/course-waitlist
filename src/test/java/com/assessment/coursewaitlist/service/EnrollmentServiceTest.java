package com.assessment.coursewaitlist.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.assessment.coursewaitlist.domain.Course;
import com.assessment.coursewaitlist.domain.CourseOffering;
import com.assessment.coursewaitlist.domain.DropResult;
import com.assessment.coursewaitlist.domain.EnrollmentResult;
import com.assessment.coursewaitlist.domain.Learner;

/**
 * Service tests, run without Spring. Each test carries the id of its entry
 * in docs/REQUIREMENTS_TRACEABILITY.md.
 */
class EnrollmentServiceTest {

	private static final String PREREQUISITE = "CS101";

	private CourseOffering offering;

	private LearnerDirectory directory;

	private EnrollmentService service;

	@BeforeEach
	void setUp() {
		offering = new CourseOffering("CS201-A", new Course("CS201", "Data Structures", Set.of(PREREQUISITE)), 1);
		directory = new LearnerDirectory();
		service = new EnrollmentService(offering, directory);
	}

	private void registerEligible(String id) {
		service.registerLearner(id, "Learner " + id, List.of(PREREQUISITE));
	}

	@Test
	@DisplayName("TC-S-01: enroll and drop resolve the registered learner and return the domain result")
	void tcS01_enrollAndDropResolveLearnerAndReturnDomainResult() {
		registerEligible("A");
		registerEligible("B");

		EnrollmentResult first = service.enroll("A");
		EnrollmentResult second = service.enroll("B");
		EnrollmentResult duplicate = service.enroll("A");

		assertThat(first.outcome()).isEqualTo(EnrollmentResult.Outcome.ENROLLED);
		assertThat(second.outcome()).isEqualTo(EnrollmentResult.Outcome.WAITLISTED);
		assertThat(duplicate.outcome()).isEqualTo(EnrollmentResult.Outcome.ALREADY_ENROLLED);
		assertThat(offering.enrolledLearnerIds()).containsExactly("A");
		assertThat(offering.waitlistedLearnerIds()).containsExactly("B");

		DropResult drop = service.drop("A");
		DropResult repeated = service.drop("A");

		assertThat(drop.outcome()).isEqualTo(DropResult.Outcome.DROPPED);
		assertThat(drop.promotedLearnerIds()).containsExactly("B");
		assertThat(repeated.outcome()).isEqualTo(DropResult.Outcome.NOT_ENROLLED);
		assertThat(offering.enrolledLearnerIds()).containsExactly("B");
		assertThat(offering.waitlistedLearnerIds()).isEmpty();
	}

	@Test
	@DisplayName("TC-S-02: an unknown learner is rejected by every operation and the state is unchanged")
	void tcS02_unknownLearnerIsRejectedAndStateUnchanged() {
		registerEligible("A");
		registerEligible("B");
		service.enroll("A");
		service.enroll("B");
		OfferingView before = service.offeringState();

		assertThatThrownBy(() -> service.enroll("GHOST")).isInstanceOf(LearnerNotFoundException.class)
			.hasMessageContaining("GHOST");
		assertThatThrownBy(() -> service.drop("GHOST")).isInstanceOf(LearnerNotFoundException.class);
		assertThatThrownBy(() -> service.markCompleted("GHOST", PREREQUISITE))
			.isInstanceOf(LearnerNotFoundException.class);
		assertThatThrownBy(() -> service.revokeCompletion("GHOST", PREREQUISITE))
			.isInstanceOf(LearnerNotFoundException.class);
		assertThatThrownBy(() -> service.enroll(null)).isInstanceOf(LearnerNotFoundException.class);

		assertThat(service.offeringState()).isEqualTo(before);
		// A failed lookup must not register the learner as a side effect.
		assertThatThrownBy(() -> directory.require("GHOST")).isInstanceOf(LearnerNotFoundException.class);
	}

	@Test
	@DisplayName("TC-S-03: registering an id twice is rejected and the original learner object is kept")
	void tcS03_duplicateRegistrationIsRejectedAndOriginalLearnerKept() {
		LearnerView created = service.registerLearner("A", "Original", List.of(PREREQUISITE));
		Learner original = directory.require("A");

		assertThatThrownBy(() -> service.registerLearner("A", "Impostor", List.of()))
			.isInstanceOf(LearnerAlreadyExistsException.class)
			.hasMessageContaining("A");

		assertThat(created).isEqualTo(new LearnerView("A", "Original", List.of(PREREQUISITE)));
		// The very same object is still registered, with its name and completions intact.
		assertThat(directory.require("A")).isSameAs(original);
		assertThat(original.name()).isEqualTo("Original");
		assertThat(original.completedCourseCodes()).containsExactly(PREREQUISITE);
		// So the learner is still eligible: the rejected registration did not wipe the completion.
		assertThat(service.enroll("A").outcome()).isEqualTo(EnrollmentResult.Outcome.ENROLLED);
	}

	@Test
	@DisplayName("TC-S-04: a completion change made through the service is seen at the next promotion")
	void tcS04_completionChangesThroughServiceAreSeenAtPromotion() {
		registerEligible("A");
		registerEligible("W1");
		registerEligible("W2");
		service.enroll("A");
		service.enroll("W1");
		service.enroll("W2");

		service.revokeCompletion("W1", PREREQUISITE);
		DropResult drop = service.drop("A");

		// W1 is skipped and keeps their place; W2 is promoted.
		assertThat(drop.promotedLearnerIds()).containsExactly("W2");
		assertThat(offering.enrolledLearnerIds()).containsExactly("W2");
		assertThat(offering.waitlistedLearnerIds()).containsExactly("W1");

		service.markCompleted("W1", PREREQUISITE);
		// Marking the completion does not itself fill a seat (limitation L1) ...
		assertThat(offering.waitlistedLearnerIds()).containsExactly("W1");
		// ... but W1 is promoted at the next drop.
		assertThat(service.drop("W2").promotedLearnerIds()).containsExactly("W1");
		assertThat(offering.enrolledLearnerIds()).containsExactly("W1");
		assertThat(offering.waitlistedLearnerIds()).isEmpty();
	}

	@Test
	@DisplayName("TC-S-05: the offering view matches the domain snapshot and is immutable")
	void tcS05_offeringViewMatchesSnapshotAndIsImmutable() {
		registerEligible("A");
		registerEligible("B");
		registerEligible("C");
		service.enroll("A");
		service.enroll("B");
		service.enroll("C");

		OfferingView view = service.offeringState();

		assertThat(view).isEqualTo(new OfferingView("CS201-A", "CS201", "Data Structures", List.of(PREREQUISITE), 1, 0,
				List.of("A"), List.of("B", "C")));
		assertThat(view.enrolledLearnerIds()).isEqualTo(offering.snapshot().enrolledLearnerIds());
		assertThat(view.waitlistedLearnerIds()).isEqualTo(offering.snapshot().waitlistedLearnerIds());
		assertThat(view.availableSeats()).isEqualTo(offering.snapshot().availableSeats());

		assertThatThrownBy(() -> view.enrolledLearnerIds().add("X")).isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> view.waitlistedLearnerIds().clear()).isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> view.prerequisiteCodes().add("X")).isInstanceOf(UnsupportedOperationException.class);

		// The view is a copy: it does not follow later changes.
		service.drop("A");
		assertThat(view.enrolledLearnerIds()).containsExactly("A");
		assertThat(service.offeringState().enrolledLearnerIds()).containsExactly("B");
		assertThat(service.offeringState().waitlistedLearnerIds()).containsExactly("C");
	}

	@Test
	@DisplayName("TC-S-07: concurrent registrations of the same id: exactly one succeeds, the rest are rejected, and the winner's data is kept")
	void tcS07_concurrentDuplicateRegistrationsRegisterExactlyOneLearner() throws Exception {
		int threads = 24;
		int rounds = 2000;
		ExecutorService executor = Executors.newFixedThreadPool(threads);
		try {
			for (int round = 0; round < rounds; round++) {
				LearnerDirectory roundDirectory = new LearnerDirectory();
				EnrollmentService roundService = new EnrollmentService(offering, roundDirectory);
				CountDownLatch ready = new CountDownLatch(threads);
				CountDownLatch start = new CountDownLatch(1);
				List<Future<Object>> attempts = new ArrayList<>();
				for (int t = 0; t < threads; t++) {
					// Every attempt carries different data, so the winner can be identified afterwards.
					String name = "Candidate " + t;
					List<String> completed = List.of(PREREQUISITE, "MARK" + t);
					attempts.add(executor.submit(() -> {
						ready.countDown();
						start.await();
						try {
							return roundService.registerLearner("SAME", name, completed);
						}
						catch (LearnerAlreadyExistsException duplicate) {
							return duplicate;
						}
					}));
				}
				assertThat(ready.await(10, TimeUnit.SECONDS)).as("all registration threads ready").isTrue();
				start.countDown();

				List<LearnerView> registered = new ArrayList<>();
				int rejected = 0;
				for (Future<Object> attempt : attempts) {
					// Any exception other than the duplicate result surfaces here and fails the test.
					Object outcome = attempt.get(10, TimeUnit.SECONDS);
					if (outcome instanceof LearnerView view) {
						registered.add(view);
					}
					else {
						assertThat(outcome).isInstanceOf(LearnerAlreadyExistsException.class);
						assertThat(((LearnerAlreadyExistsException) outcome).learnerId()).isEqualTo("SAME");
						rejected++;
					}
				}

				assertThat(registered).as("successful registrations in round " + round).hasSize(1);
				assertThat(rejected).as("rejected registrations in round " + round).isEqualTo(threads - 1);
				// Exactly one learner is in the directory, and it holds the winner's own data, unmixed.
				assertThat(roundDirectory.size()).isEqualTo(1);
				LearnerView winner = registered.get(0);
				Learner stored = roundDirectory.require("SAME");
				assertThat(stored.name()).isEqualTo(winner.name());
				assertThat(stored.completedCourseCodes()).containsExactlyInAnyOrderElementsOf(winner.completedCourseCodes())
					.hasSize(2)
					.contains(PREREQUISITE, "MARK" + winner.name().substring("Candidate ".length()));
				assertThat(roundDirectory.require("SAME")).isSameAs(stored);
			}
		}
		finally {
			executor.shutdownNow();
		}
	}

}
