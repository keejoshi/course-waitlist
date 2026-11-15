package com.assessment.coursewaitlist.domain;

import java.util.List;

/**
 * The result of a drop request.
 *
 * <p>Only giving up a seat can promote anyone, so {@code promotedLearnerIds}
 * must be empty unless the outcome is {@link Outcome#DROPPED}. A contradictory
 * combination is rejected at construction.
 *
 * @param outcome what happened to the requesting learner
 * @param promotedLearnerIds waitlisted learners who were promoted into the
 * seats opened by the drop, in promotion order
 */
public record DropResult(Outcome outcome, List<String> promotedLearnerIds) {

	public enum Outcome {

		/** The learner held a seat and has given it up. */
		DROPPED,

		/** The learner was on the waitlist and has left it. No seat was affected. */
		DROPPED_FROM_WAITLIST,

		/** The learner was neither enrolled nor waitlisted. Nothing changed. */
		NOT_ENROLLED

	}

	public DropResult {
		Require.nonNull(outcome, "outcome");
		promotedLearnerIds = Require.idList(promotedLearnerIds, "promoted learner ids");
		if (outcome != Outcome.DROPPED && !promotedLearnerIds.isEmpty()) {
			throw new IllegalArgumentException("outcome " + outcome + " cannot report promoted learners");
		}
	}

	static DropResult of(Outcome outcome) {
		return new DropResult(outcome, List.of());
	}

}
