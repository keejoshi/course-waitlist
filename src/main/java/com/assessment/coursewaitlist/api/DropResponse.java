package com.assessment.coursewaitlist.api;

import java.util.List;

import com.assessment.coursewaitlist.domain.DropResult;

/**
 * Result of a successful drop request.
 *
 * @param learnerId the learner who dropped
 * @param status {@code DROPPED} (gave up a seat) or
 * {@code DROPPED_FROM_WAITLIST} (left the waitlist)
 * @param promotedLearnerIds waitlisted learners promoted into the seat that
 * opened; always empty when leaving the waitlist
 */
public record DropResponse(String learnerId, String status, List<String> promotedLearnerIds) {

	static DropResponse of(String learnerId, DropResult result) {
		return new DropResponse(learnerId, result.outcome().name(), result.promotedLearnerIds());
	}

}
