package com.assessment.coursewaitlist.api;

import java.util.List;

import com.assessment.coursewaitlist.domain.EnrollmentResult;

/**
 * Result of a successful enrollment request.
 *
 * @param learnerId the requesting learner
 * @param status {@code ENROLLED} or {@code WAITLISTED}
 * @param promotedLearnerIds waitlisted learners who were given open seats
 * before this request was placed; usually empty
 */
public record EnrollmentResponse(String learnerId, String status, List<String> promotedLearnerIds) {

	static EnrollmentResponse of(String learnerId, EnrollmentResult result) {
		return new EnrollmentResponse(learnerId, result.outcome().name(), result.promotedLearnerIds());
	}

}
