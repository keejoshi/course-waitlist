package com.assessment.coursewaitlist.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Domain tests for {@link CourseOffering}. Each test carries the id of the
 * checklist entry it implements in docs/REQUIREMENTS_TRACEABILITY.md.
 */
class CourseOfferingTest {

	private static final String PREREQUISITE = "CS101";

	private static final Course COURSE = new Course("CS201", "Data Structures", Set.of(PREREQUISITE));

	private static CourseOffering offeringWithCapacity(int capacity) {
		return new CourseOffering("CS201-A", COURSE, capacity);
	}

	private static Learner eligible(String id) {
		Learner learner = new Learner(id, "Learner " + id);
		learner.markCompleted(PREREQUISITE);
		return learner;
	}

	private static Learner ineligible(String id) {
		return new Learner(id, "Learner " + id);
	}

	private static void assertState(CourseOffering offering, List<String> enrolled, List<String> waitlisted) {
		assertThat(offering.enrolledLearnerIds()).as("enrolled").containsExactlyElementsOf(enrolled);
		assertThat(offering.waitlistedLearnerIds()).as("waitlist").containsExactlyElementsOf(waitlisted);
		assertThat(offering.availableSeats()).as("available seats").isEqualTo(offering.capacity() - enrolled.size());
	}

	// --- Available capacity -------------------------------------------------

	@Test
	@DisplayName("TC-D-01: eligible learner enrolls while seats remain")
	void tcD01_eligibleLearnerEnrollsWhileSeatsRemain() {
		CourseOffering offering = offeringWithCapacity(2);

		EnrollmentResult result = offering.enroll(eligible("A"));

		assertThat(result.outcome()).isEqualTo(EnrollmentResult.Outcome.ENROLLED);
		assertThat(result.promotedLearnerIds()).isEmpty();
		assertState(offering, List.of("A"), List.of());
	}

	@Test
	@DisplayName("TC-D-02: course with no prerequisites admits any learner")
	void tcD02_courseWithNoPrerequisitesAdmitsAnyLearner() {
		Course open = new Course("CS100", "Introduction", Set.of());
		CourseOffering offering = new CourseOffering("CS100-A", open, 1);

		EnrollmentResult result = offering.enroll(new Learner("A", "No completions"));

		assertThat(result.outcome()).isEqualTo(EnrollmentResult.Outcome.ENROLLED);
		assertState(offering, List.of("A"), List.of());
	}

	@Test
	@DisplayName("TC-D-03: enrollment fills exactly to capacity; the last seat is granted")
	void tcD03_enrollmentFillsExactlyToCapacity() {
		CourseOffering offering = offeringWithCapacity(3);

		assertThat(offering.enroll(eligible("A")).outcome()).isEqualTo(EnrollmentResult.Outcome.ENROLLED);
		assertThat(offering.enroll(eligible("B")).outcome()).isEqualTo(EnrollmentResult.Outcome.ENROLLED);
		assertThat(offering.availableSeats()).isEqualTo(1);
		assertThat(offering.enroll(eligible("C")).outcome()).isEqualTo(EnrollmentResult.Outcome.ENROLLED);

		assertState(offering, List.of("A", "B", "C"), List.of());
		assertThat(offering.availableSeats()).isZero();
	}

	// --- Full capacity ------------------------------------------------------

	@Test
	@DisplayName("TC-D-04: full offering waitlists an eligible learner; enrolled count unchanged")
	void tcD04_fullOfferingWaitlistsEligibleLearner() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("A"));

		EnrollmentResult result = offering.enroll(eligible("B"));

		assertThat(result.outcome()).isEqualTo(EnrollmentResult.Outcome.WAITLISTED);
		assertThat(result.promotedLearnerIds()).isEmpty();
		assertState(offering, List.of("A"), List.of("B"));
	}

	// --- Duplicate requests -------------------------------------------------

	@Test
	@DisplayName("TC-D-05: enrolled learner enrolling again gets ALREADY_ENROLLED; state unchanged")
	void tcD05_duplicateEnrollmentReturnsAlreadyEnrolled() {
		CourseOffering offering = offeringWithCapacity(2);
		Learner a = eligible("A");
		offering.enroll(a);

		EnrollmentResult sameObject = offering.enroll(a);
		EnrollmentResult sameIdNewObject = offering.enroll(eligible("A"));

		assertThat(sameObject.outcome()).isEqualTo(EnrollmentResult.Outcome.ALREADY_ENROLLED);
		assertThat(sameIdNewObject.outcome()).isEqualTo(EnrollmentResult.Outcome.ALREADY_ENROLLED);
		assertState(offering, List.of("A"), List.of());
	}

	@Test
	@DisplayName("TC-D-06: waitlisted learner enrolling again gets ALREADY_WAITLISTED; position and size unchanged")
	void tcD06_duplicateWaitlistRequestReturnsAlreadyWaitlisted() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("A"));
		Learner b = eligible("B");
		offering.enroll(b);
		offering.enroll(eligible("C"));

		EnrollmentResult result = offering.enroll(b);

		assertThat(result.outcome()).isEqualTo(EnrollmentResult.Outcome.ALREADY_WAITLISTED);
		assertThat(result.promotedLearnerIds()).isEmpty();
		assertState(offering, List.of("A"), List.of("B", "C"));
	}

	// --- Prerequisite failure -----------------------------------------------

	@Test
	@DisplayName("TC-D-07: ineligible learner with a seat free is rejected; not enrolled, not waitlisted")
	void tcD07_ineligibleLearnerRejectedWhenSeatFree() {
		CourseOffering offering = offeringWithCapacity(2);

		EnrollmentResult result = offering.enroll(ineligible("X"));

		assertThat(result.outcome()).isEqualTo(EnrollmentResult.Outcome.PREREQUISITES_NOT_MET);
		assertState(offering, List.of(), List.of());
	}

	@Test
	@DisplayName("TC-D-08: ineligible learner when full is rejected; not waitlisted")
	void tcD08_ineligibleLearnerRejectedWhenFull() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("A"));

		EnrollmentResult result = offering.enroll(ineligible("X"));

		assertThat(result.outcome()).isEqualTo(EnrollmentResult.Outcome.PREREQUISITES_NOT_MET);
		assertState(offering, List.of("A"), List.of());
	}

	@Test
	@DisplayName("TC-D-09: learner with only some of several prerequisites is ineligible")
	void tcD09_partialPrerequisitesAreNotEnough() {
		Course advanced = new Course("CS301", "Algorithms", Set.of("CS101", "CS201"));
		CourseOffering offering = new CourseOffering("CS301-A", advanced, 2);
		Learner partial = new Learner("P", "Partial");
		partial.markCompleted("CS101");

		assertThat(offering.enroll(partial).outcome()).isEqualTo(EnrollmentResult.Outcome.PREREQUISITES_NOT_MET);
		assertState(offering, List.of(), List.of());

		partial.markCompleted("CS201");
		assertThat(offering.enroll(partial).outcome()).isEqualTo(EnrollmentResult.Outcome.ENROLLED);
		assertState(offering, List.of("P"), List.of());
	}

	// --- Drop and FIFO promotion --------------------------------------------

	@Test
	@DisplayName("TC-D-10: drop with an empty waitlist frees one seat")
	void tcD10_dropWithEmptyWaitlistFreesSeat() {
		CourseOffering offering = offeringWithCapacity(2);
		offering.enroll(eligible("A"));
		offering.enroll(eligible("B"));

		DropResult result = offering.drop("A");

		assertThat(result.outcome()).isEqualTo(DropResult.Outcome.DROPPED);
		assertThat(result.promotedLearnerIds()).isEmpty();
		assertState(offering, List.of("B"), List.of());
		assertThat(offering.availableSeats()).isEqualTo(1);
	}

	@Test
	@DisplayName("TC-D-11: drop promotes the head of the waitlist and names the promoted learner")
	void tcD11_dropPromotesHeadOfWaitlist() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("A"));
		offering.enroll(eligible("B"));
		offering.enroll(eligible("C"));

		DropResult result = offering.drop("A");

		assertThat(result.outcome()).isEqualTo(DropResult.Outcome.DROPPED);
		assertThat(result.promotedLearnerIds()).containsExactly("B");
		assertState(offering, List.of("B"), List.of("C"));
	}

	@Test
	@DisplayName("TC-D-12: successive drops promote waitlisted learners in arrival order")
	void tcD12_successiveDropsPromoteInFifoOrder() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("S"));
		offering.enroll(eligible("A"));
		offering.enroll(eligible("B"));
		offering.enroll(eligible("C"));
		assertState(offering, List.of("S"), List.of("A", "B", "C"));

		assertThat(offering.drop("S").promotedLearnerIds()).containsExactly("A");
		assertState(offering, List.of("A"), List.of("B", "C"));

		assertThat(offering.drop("A").promotedLearnerIds()).containsExactly("B");
		assertState(offering, List.of("B"), List.of("C"));

		assertThat(offering.drop("B").promotedLearnerIds()).containsExactly("C");
		assertState(offering, List.of("C"), List.of());
	}

	// --- Skip-and-retain policy ---------------------------------------------

	@Test
	@DisplayName("TC-D-13: ineligible head is skipped, next eligible learner promoted, skipped learner keeps position")
	void tcD13_ineligibleHeadIsSkippedAndKeepsPosition() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("S"));
		Learner a = eligible("A");
		offering.enroll(a);
		offering.enroll(eligible("B"));
		offering.enroll(eligible("C"));
		a.revokeCompletion(PREREQUISITE);

		DropResult result = offering.drop("S");

		assertThat(result.promotedLearnerIds()).containsExactly("B");
		assertState(offering, List.of("B"), List.of("A", "C"));
	}

	@Test
	@DisplayName("TC-D-14: several ineligible learners are skipped; order of all remaining waiters preserved")
	void tcD14_severalIneligibleLearnersSkippedAndOrderPreserved() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("S"));
		Learner a = eligible("A");
		Learner b = eligible("B");
		Learner d = eligible("D");
		offering.enroll(a);
		offering.enroll(b);
		offering.enroll(eligible("C"));
		offering.enroll(d);
		offering.enroll(eligible("E"));
		a.revokeCompletion(PREREQUISITE);
		b.revokeCompletion(PREREQUISITE);
		d.revokeCompletion(PREREQUISITE);

		assertThat(offering.drop("S").promotedLearnerIds()).containsExactly("C");
		assertState(offering, List.of("C"), List.of("A", "B", "D", "E"));

		assertThat(offering.drop("C").promotedLearnerIds()).containsExactly("E");
		assertState(offering, List.of("E"), List.of("A", "B", "D"));
	}

	@Test
	@DisplayName("TC-D-15: when every waiter is ineligible the seat stays vacant and the waitlist is unchanged")
	void tcD15_noEligibleWaiterLeavesSeatVacant() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("S"));
		Learner a = eligible("A");
		Learner b = eligible("B");
		offering.enroll(a);
		offering.enroll(b);
		a.revokeCompletion(PREREQUISITE);
		b.revokeCompletion(PREREQUISITE);

		DropResult result = offering.drop("S");

		assertThat(result.outcome()).isEqualTo(DropResult.Outcome.DROPPED);
		assertThat(result.promotedLearnerIds()).isEmpty();
		assertState(offering, List.of(), List.of("A", "B"));
		assertThat(offering.availableSeats()).isEqualTo(1);
	}

	@Test
	@DisplayName("TC-D-16: skipped learner who regains eligibility is promoted on the next drop, ahead of later arrivals")
	void tcD16_skippedLearnerRegainsEligibilityAndIsPromotedOnNextDrop() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("S"));
		Learner a = eligible("A");
		offering.enroll(a);
		offering.enroll(eligible("B"));
		offering.enroll(eligible("C"));
		a.revokeCompletion(PREREQUISITE);
		assertThat(offering.drop("S").promotedLearnerIds()).containsExactly("B");
		assertState(offering, List.of("B"), List.of("A", "C"));

		a.markCompleted(PREREQUISITE);
		DropResult result = offering.drop("B");

		assertThat(result.promotedLearnerIds()).containsExactly("A");
		assertState(offering, List.of("A"), List.of("C"));
	}

	// --- Open-seat policy: waiters before new requesters --------------------

	@Test
	@DisplayName("TC-D-17: exactly one open seat, eligible waiter, new eligible requester: waiter gets the seat, requester is waitlisted")
	void tcD17_eligibleWaiterTakesOpenSeatBeforeNewRequester() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("A"));
		Learner w = eligible("W");
		offering.enroll(w);
		// W is skipped when A drops, leaving exactly one open seat with W still first in line.
		w.revokeCompletion(PREREQUISITE);
		assertThat(offering.drop("A").promotedLearnerIds()).isEmpty();
		assertState(offering, List.of(), List.of("W"));
		assertThat(offering.availableSeats()).isEqualTo(1);
		w.markCompleted(PREREQUISITE);

		EnrollmentResult result = offering.enroll(eligible("N"));

		assertThat(result.outcome()).isEqualTo(EnrollmentResult.Outcome.WAITLISTED);
		assertThat(result.promotedLearnerIds()).containsExactly("W");
		assertState(offering, List.of("W"), List.of("N"));
	}

	@Test
	@DisplayName("TC-D-18: open seat, only ineligible waiters, new eligible requester: requester enrolled, waiters retained in order")
	void tcD18_newRequesterTakesOpenSeatWhenNoWaiterIsEligible() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("A"));
		Learner w1 = eligible("W1");
		Learner w2 = eligible("W2");
		offering.enroll(w1);
		offering.enroll(w2);
		w1.revokeCompletion(PREREQUISITE);
		w2.revokeCompletion(PREREQUISITE);
		offering.drop("A");
		assertState(offering, List.of(), List.of("W1", "W2"));

		EnrollmentResult result = offering.enroll(eligible("N"));

		assertThat(result.outcome()).isEqualTo(EnrollmentResult.Outcome.ENROLLED);
		assertThat(result.promotedLearnerIds()).isEmpty();
		assertState(offering, List.of("N"), List.of("W1", "W2"));
	}

	// --- Invalid and repeated drops -----------------------------------------

	@Test
	@DisplayName("TC-D-19: repeated drop returns NOT_ENROLLED; state unchanged")
	void tcD19_repeatedDropReturnsNotEnrolled() {
		CourseOffering offering = offeringWithCapacity(2);
		offering.enroll(eligible("A"));
		offering.enroll(eligible("B"));
		offering.enroll(eligible("C"));
		assertThat(offering.drop("A").promotedLearnerIds()).containsExactly("C");
		assertState(offering, List.of("B", "C"), List.of());

		DropResult second = offering.drop("A");
		DropResult third = offering.drop("A");

		assertThat(second.outcome()).isEqualTo(DropResult.Outcome.NOT_ENROLLED);
		assertThat(second.promotedLearnerIds()).isEmpty();
		assertThat(third.outcome()).isEqualTo(DropResult.Outcome.NOT_ENROLLED);
		assertState(offering, List.of("B", "C"), List.of());
	}

	@Test
	@DisplayName("TC-D-20: drop by a learner never enrolled or waitlisted returns NOT_ENROLLED; state unchanged")
	void tcD20_dropByUnknownLearnerReturnsNotEnrolled() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("A"));
		offering.enroll(eligible("B"));

		DropResult result = offering.drop("NOBODY");

		assertThat(result.outcome()).isEqualTo(DropResult.Outcome.NOT_ENROLLED);
		assertThat(result.promotedLearnerIds()).isEmpty();
		assertState(offering, List.of("A"), List.of("B"));
	}

	// --- Leaving the waitlist -----------------------------------------------

	@Test
	@DisplayName("TC-D-21: waitlisted learner leaves: DROPPED_FROM_WAITLIST, enrolled unchanged, order kept, and no promotion even with a vacant seat and an eligible waiter")
	void tcD21_waitlistedLearnerLeavesWaitlist() {
		// Full offering: leaving does not touch the enrolled learner or the order of the others.
		CourseOffering full = offeringWithCapacity(1);
		full.enroll(eligible("A"));
		full.enroll(eligible("B"));
		full.enroll(eligible("C"));
		full.enroll(eligible("D"));

		DropResult fromFull = full.drop("C");

		assertThat(fromFull.outcome()).isEqualTo(DropResult.Outcome.DROPPED_FROM_WAITLIST);
		assertThat(fromFull.promotedLearnerIds()).isEmpty();
		assertState(full, List.of("A"), List.of("B", "D"));

		// Vacant seat and an eligible waiter: a promotion is possible here, so this part
		// fails if leaving the waitlist wrongly offers the open seat.
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("S"));
		Learner w1 = eligible("W1");
		Learner w2 = eligible("W2");
		Learner w3 = eligible("W3");
		offering.enroll(w1);
		offering.enroll(w2);
		offering.enroll(w3);
		w1.revokeCompletion(PREREQUISITE);
		w2.revokeCompletion(PREREQUISITE);
		w3.revokeCompletion(PREREQUISITE);
		offering.drop("S");
		w1.markCompleted(PREREQUISITE);
		assertState(offering, List.of(), List.of("W1", "W2", "W3"));
		assertThat(offering.availableSeats()).isEqualTo(1);

		DropResult result = offering.drop("W2");

		assertThat(result.outcome()).isEqualTo(DropResult.Outcome.DROPPED_FROM_WAITLIST);
		assertThat(result.promotedLearnerIds()).isEmpty();
		assertState(offering, List.of(), List.of("W1", "W3"));
		assertThat(offering.availableSeats()).isEqualTo(1);
	}

	@Test
	@DisplayName("TC-D-22: repeated waitlist drop returns NOT_ENROLLED; state unchanged")
	void tcD22_repeatedWaitlistDropReturnsNotEnrolled() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("A"));
		offering.enroll(eligible("B"));
		offering.enroll(eligible("C"));
		assertThat(offering.drop("B").outcome()).isEqualTo(DropResult.Outcome.DROPPED_FROM_WAITLIST);

		DropResult result = offering.drop("B");

		assertThat(result.outcome()).isEqualTo(DropResult.Outcome.NOT_ENROLLED);
		assertThat(result.promotedLearnerIds()).isEmpty();
		assertState(offering, List.of("A"), List.of("C"));
	}

	// --- Re-enrollment and revocation ---------------------------------------

	@Test
	@DisplayName("TC-D-23: learner who dropped can enroll again and joins the tail when the offering is full")
	void tcD23_droppedLearnerCanEnrollAgainAndJoinsTail() {
		CourseOffering offering = offeringWithCapacity(1);
		Learner a = eligible("A");
		offering.enroll(a);
		offering.enroll(eligible("B"));
		offering.enroll(eligible("C"));
		offering.drop("A");
		assertState(offering, List.of("B"), List.of("C"));

		EnrollmentResult whenFull = offering.enroll(a);

		assertThat(whenFull.outcome()).isEqualTo(EnrollmentResult.Outcome.WAITLISTED);
		assertState(offering, List.of("B"), List.of("C", "A"));

		// With a seat free, a learner who dropped simply enrolls again.
		CourseOffering roomy = offeringWithCapacity(2);
		roomy.enroll(a);
		roomy.drop("A");
		assertThat(roomy.enroll(a).outcome()).isEqualTo(EnrollmentResult.Outcome.ENROLLED);
		assertState(roomy, List.of("A"), List.of());
	}

	@Test
	@DisplayName("TC-D-24: revoking a prerequisite does not remove an enrolled learner, even when a later promotion runs")
	void tcD24_revokedPrerequisiteDoesNotRemoveEnrolledLearner() {
		CourseOffering offering = offeringWithCapacity(2);
		Learner a = eligible("A");
		offering.enroll(a);
		offering.enroll(eligible("B"));
		offering.enroll(eligible("W1"));
		offering.enroll(eligible("W2"));

		a.revokeCompletion(PREREQUISITE);

		assertState(offering, List.of("A", "B"), List.of("W1", "W2"));
		// A is still enrolled, so a repeat request is still a duplicate and nobody is promoted.
		assertThat(offering.enroll(a).outcome()).isEqualTo(EnrollmentResult.Outcome.ALREADY_ENROLLED);
		assertState(offering, List.of("A", "B"), List.of("W1", "W2"));

		// A promotion pass after a drop fills only the seat that opened; A keeps theirs.
		assertThat(offering.drop("B").promotedLearnerIds()).containsExactly("W1");
		assertState(offering, List.of("A", "W1"), List.of("W2"));

		// The promotion pass that precedes a new enrollment does not evict A either.
		assertThat(offering.enroll(eligible("N")).outcome()).isEqualTo(EnrollmentResult.Outcome.WAITLISTED);
		assertState(offering, List.of("A", "W1"), List.of("W2", "N"));
	}

	@Test
	@DisplayName("TC-D-25: a promoted learner leaves the waitlist and never appears in both collections")
	void tcD25_promotedLearnerIsRemovedFromWaitlist() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("A"));
		Learner b = eligible("B");
		offering.enroll(b);

		offering.drop("A");

		assertThat(offering.enrolledLearnerIds()).containsExactly("B");
		assertThat(offering.waitlistedLearnerIds()).doesNotContain("B").isEmpty();
		// B now holds a seat, so a repeat request is a duplicate enrollment, not a waitlist entry.
		assertThat(offering.enroll(b).outcome()).isEqualTo(EnrollmentResult.Outcome.ALREADY_ENROLLED);
		assertState(offering, List.of("B"), List.of());
	}

	// --- Invalid arguments and construction ---------------------------------

	@Test
	@DisplayName("TC-D-26: null learner and null or blank ids are rejected; state unchanged")
	void tcD26_invalidArgumentsAreRejectedWithoutChangingState() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("A"));
		offering.enroll(eligible("B"));

		assertThatIllegalArgumentException().isThrownBy(() -> offering.enroll(null))
			.withMessageContaining("learner");
		assertThatIllegalArgumentException().isThrownBy(() -> offering.drop(null))
			.withMessageContaining("learner id");
		assertThatIllegalArgumentException().isThrownBy(() -> offering.drop(""));
		assertThatIllegalArgumentException().isThrownBy(() -> offering.drop("   "));

		assertState(offering, List.of("A"), List.of("B"));
	}

	@Test
	@DisplayName("TC-D-27: construction rejects capacity below 1, null course, and blank ids")
	void tcD27_constructionRejectsInvalidArguments() {
		assertThatIllegalArgumentException().isThrownBy(() -> new CourseOffering("O", COURSE, 0))
			.withMessageContaining("capacity");
		assertThatIllegalArgumentException().isThrownBy(() -> new CourseOffering("O", COURSE, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> new CourseOffering("O", null, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> new CourseOffering(" ", COURSE, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> new CourseOffering(null, COURSE, 1));

		CourseOffering smallest = new CourseOffering("O", COURSE, 1);
		assertThat(smallest.capacity()).isEqualTo(1);
		assertThat(smallest.id()).isEqualTo("O");
		assertThat(smallest.course()).isEqualTo(COURSE);
	}

	@Test
	@DisplayName("TC-D-30: state snapshots cannot be used to modify the offering")
	void tcD30_snapshotsCannotModifyTheOffering() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("A"));
		offering.enroll(eligible("B"));
		List<String> enrolledBefore = offering.enrolledLearnerIds();
		List<String> waitlistBefore = offering.waitlistedLearnerIds();

		assertThatThrownBy(() -> enrolledBefore.add("X")).isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> waitlistBefore.clear()).isInstanceOf(UnsupportedOperationException.class);
		assertState(offering, List.of("A"), List.of("B"));

		// A snapshot is a copy: it does not follow later changes.
		offering.drop("A");
		assertThat(enrolledBefore).containsExactly("A");
		assertThat(waitlistBefore).containsExactly("B");
		assertState(offering, List.of("B"), List.of());
	}

	@Test
	@DisplayName("TC-D-31: after a mixed sequence, enrolled never exceeds capacity and the collections stay disjoint")
	void tcD31_invariantsHoldThroughoutMixedSequence() {
		CourseOffering offering = offeringWithCapacity(2);
		Learner c = eligible("C");
		Runnable[] steps = {
				() -> offering.enroll(eligible("A")),
				() -> offering.enroll(eligible("B")),
				() -> offering.enroll(c),
				() -> offering.enroll(eligible("A")),
				() -> offering.enroll(ineligible("X")),
				() -> offering.enroll(eligible("D")),
				() -> offering.enroll(c),
				() -> c.revokeCompletion(PREREQUISITE),
				() -> offering.drop("A"),
				() -> offering.drop("A"),
				() -> offering.drop("NOBODY"),
				() -> offering.enroll(eligible("E")),
				() -> c.markCompleted(PREREQUISITE),
				() -> offering.drop("B"),
				() -> offering.drop("E"),
				() -> offering.enroll(eligible("A")),
				() -> offering.drop("D"),
				() -> offering.drop("C") };

		for (Runnable step : steps) {
			step.run();
			List<String> enrolled = offering.enrolledLearnerIds();
			List<String> waitlisted = offering.waitlistedLearnerIds();
			assertThat(enrolled).hasSizeLessThanOrEqualTo(offering.capacity()).doesNotHaveDuplicates();
			assertThat(waitlisted).doesNotHaveDuplicates();
			Set<String> overlap = new HashSet<>(enrolled);
			overlap.retainAll(waitlisted);
			assertThat(overlap).as("learners both enrolled and waitlisted").isEmpty();
			assertThat(offering.availableSeats()).isEqualTo(offering.capacity() - enrolled.size());
		}

		// A B enrolled; C D waitlisted; A drops -> D promoted (C skipped); E waitlisted;
		// B drops -> C promoted; E drops from waitlist; A enrolls -> waitlisted;
		// D drops -> A promoted; C drops -> seat free.
		assertState(offering, List.of("A"), List.of());
	}

	// --- Open-seat policy: when open seats are (not) offered ----------------

	@Test
	@DisplayName("TC-D-32: duplicate and rejected requests do not fill an open seat; the regained-eligibility waiter keeps waiting")
	void tcD32_duplicateAndRejectedRequestsDoNotTriggerPromotion() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("A"));
		Learner w = eligible("W");
		offering.enroll(w);
		w.revokeCompletion(PREREQUISITE);
		offering.drop("A");
		w.markCompleted(PREREQUISITE);
		assertState(offering, List.of(), List.of("W"));

		EnrollmentResult duplicate = offering.enroll(w);
		EnrollmentResult rejected = offering.enroll(ineligible("X"));
		DropResult invalidDrop = offering.drop("NOBODY");

		assertThat(duplicate.outcome()).isEqualTo(EnrollmentResult.Outcome.ALREADY_WAITLISTED);
		assertThat(duplicate.promotedLearnerIds()).isEmpty();
		assertThat(rejected.outcome()).isEqualTo(EnrollmentResult.Outcome.PREREQUISITES_NOT_MET);
		assertThat(rejected.promotedLearnerIds()).isEmpty();
		assertThat(invalidDrop.outcome()).isEqualTo(DropResult.Outcome.NOT_ENROLLED);
		assertState(offering, List.of(), List.of("W"));
	}

	@Test
	@DisplayName("TC-D-33: a drop fills every open seat from the waitlist in FIFO order, including seats left vacant earlier")
	void tcD33_dropFillsAllOpenSeatsInFifoOrder() {
		CourseOffering offering = offeringWithCapacity(2);
		offering.enroll(eligible("A"));
		offering.enroll(eligible("B"));
		Learner w1 = eligible("W1");
		Learner w2 = eligible("W2");
		Learner w3 = eligible("W3");
		offering.enroll(w1);
		offering.enroll(w2);
		offering.enroll(w3);
		w1.revokeCompletion(PREREQUISITE);
		w2.revokeCompletion(PREREQUISITE);
		w3.revokeCompletion(PREREQUISITE);
		assertThat(offering.drop("A").promotedLearnerIds()).isEmpty();
		assertState(offering, List.of("B"), List.of("W1", "W2", "W3"));
		w1.markCompleted(PREREQUISITE);
		w3.markCompleted(PREREQUISITE);

		DropResult result = offering.drop("B");

		assertThat(result.outcome()).isEqualTo(DropResult.Outcome.DROPPED);
		assertThat(result.promotedLearnerIds()).containsExactly("W1", "W3");
		assertState(offering, List.of("W1", "W3"), List.of("W2"));
	}

	// --- Added after the Step 1 code-quality review ---------------------------

	@Test
	@DisplayName("TC-D-34: two open seats, one eligible waiter, new eligible requester: waiter is promoted and the requester is also enrolled")
	void tcD34_waiterPromotedAndRequesterAlsoEnrolledWhenTwoSeatsAreOpen() {
		CourseOffering offering = offeringWithCapacity(2);
		offering.enroll(eligible("A"));
		offering.enroll(eligible("B"));
		Learner w = eligible("W");
		offering.enroll(w);
		w.revokeCompletion(PREREQUISITE);
		offering.drop("A");
		offering.drop("B");
		w.markCompleted(PREREQUISITE);
		assertState(offering, List.of(), List.of("W"));
		assertThat(offering.availableSeats()).isEqualTo(2);

		EnrollmentResult result = offering.enroll(eligible("N"));

		assertThat(result.outcome()).isEqualTo(EnrollmentResult.Outcome.ENROLLED);
		assertThat(result.promotedLearnerIds()).containsExactly("W");
		// The waiter is seated before the requester.
		assertState(offering, List.of("W", "N"), List.of());
	}

	@Test
	@DisplayName("TC-D-35: waitlisted learner whose prerequisite was revoked requests again: ALREADY_WAITLISTED, not a prerequisite failure; position kept")
	void tcD35_duplicateCheckComesBeforePrerequisiteCheckForWaitlistedLearner() {
		CourseOffering offering = offeringWithCapacity(1);
		offering.enroll(eligible("A"));
		Learner b = eligible("B");
		offering.enroll(b);
		offering.enroll(eligible("C"));
		b.revokeCompletion(PREREQUISITE);

		EnrollmentResult result = offering.enroll(b);

		assertThat(result.outcome()).isEqualTo(EnrollmentResult.Outcome.ALREADY_WAITLISTED);
		assertThat(result.promotedLearnerIds()).isEmpty();
		assertState(offering, List.of("A"), List.of("B", "C"));
	}

	@Test
	@DisplayName("TC-D-38: snapshot returns enrolled, waitlist and free seats together, is immutable, and does not follow later changes")
	void tcD38_snapshotIsConsistentImmutableAndDetached() {
		CourseOffering offering = offeringWithCapacity(3);
		offering.enroll(eligible("A"));
		offering.enroll(eligible("B"));

		OfferingSnapshot partlyFilled = offering.snapshot();

		assertThat(partlyFilled.enrolledLearnerIds()).containsExactly("A", "B");
		assertThat(partlyFilled.waitlistedLearnerIds()).isEmpty();
		assertThat(partlyFilled.availableSeats()).isEqualTo(1);
		assertThat(partlyFilled.capacity()).isEqualTo(3);

		offering.enroll(eligible("C"));
		offering.enroll(eligible("D"));
		offering.enroll(eligible("E"));
		OfferingSnapshot full = offering.snapshot();

		assertThat(full.enrolledLearnerIds()).containsExactly("A", "B", "C");
		assertThat(full.waitlistedLearnerIds()).containsExactly("D", "E");
		assertThat(full.availableSeats()).isZero();
		assertThat(full.capacity()).isEqualTo(3);
		// The single-value accessors agree with the snapshot.
		assertThat(full.enrolledLearnerIds()).isEqualTo(offering.enrolledLearnerIds());
		assertThat(full.waitlistedLearnerIds()).isEqualTo(offering.waitlistedLearnerIds());
		assertThat(full.availableSeats()).isEqualTo(offering.availableSeats());

		// Immutable, and unaffected by later changes to the offering.
		assertThatThrownBy(() -> full.enrolledLearnerIds().add("X")).isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> full.waitlistedLearnerIds().remove(0))
			.isInstanceOf(UnsupportedOperationException.class);
		offering.drop("A");
		assertThat(full.enrolledLearnerIds()).containsExactly("A", "B", "C");
		assertThat(full.waitlistedLearnerIds()).containsExactly("D", "E");
		assertThat(partlyFilled.enrolledLearnerIds()).containsExactly("A", "B");
		assertState(offering, List.of("B", "C", "D"), List.of("E"));

		// A snapshot cannot be built in a contradictory state.
		assertThatIllegalArgumentException().isThrownBy(() -> new OfferingSnapshot(List.of("A"), List.of("A"), 0));
		assertThatIllegalArgumentException().isThrownBy(() -> new OfferingSnapshot(List.of("A"), List.of(), -1));
		assertThatIllegalArgumentException().isThrownBy(() -> new OfferingSnapshot(List.of("A", "A"), List.of(), 0));
		assertThatIllegalArgumentException().isThrownBy(() -> new OfferingSnapshot(null, List.of(), 0));
	}

}
