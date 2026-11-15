package com.assessment.coursewaitlist.domain;

import java.util.List;

/**
 * The result of an enrollment request.
 *
 * <p>Promotions can only accompany a request that was placed, so
 * {@code promotedLearnerIds} must be empty unless the outcome is
 * {@link Outcome#ENROLLED} or {@link Outcome#WAITLISTED}. A contradictory
 * combination is rejected at construction.
 *
 * @param outcome what happened to the requesting learner
 * @param promotedLearnerIds waitlisted learners who were promoted into open
 * seats before the request was placed, in promotion order; usually empty
 */
public record EnrollmentResult(Outcome outcome, List<String> promotedLearnerIds) {

	public enum Outcome {

		/** The learner now holds a seat. */
		ENROLLED,

		/** The offering is full; the learner was added to the end of the waitlist. */
		WAITLISTED,

		/** The learner already holds a seat. Nothing changed. */
		ALREADY_ENROLLED,

		/** The learner is already on the waitlist. Nothing changed. */
		ALREADY_WAITLISTED,

		/** The learner has not completed every prerequisite. Nothing changed. */
		PREREQUISITES_NOT_MET

	}

	public EnrollmentResult {
		Require.nonNull(outcome, "outcome");
		promotedLearnerIds = Require.idList(promotedLearnerIds, "promoted learner ids");
		boolean requestWasPlaced = outcome == Outcome.ENROLLED || outcome == Outcome.WAITLISTED;
		if (!requestWasPlaced && !promotedLearnerIds.isEmpty()) {
			throw new IllegalArgumentException(
					"outcome " + outcome + " changes nothing and cannot report promoted learners");
		}
	}

	static EnrollmentResult of(Outcome outcome) {
		return new EnrollmentResult(outcome, List.of());
	}

}
