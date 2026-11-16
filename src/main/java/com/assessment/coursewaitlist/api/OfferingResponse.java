package com.assessment.coursewaitlist.api;

import java.util.List;

import com.assessment.coursewaitlist.service.OfferingView;

/** The single offering and its current state, taken from one atomic snapshot. */
public record OfferingResponse(String offeringId, CourseResponse course, int capacity, int availableSeats,
		List<String> enrolledLearnerIds, List<String> waitlistedLearnerIds) {

	/** The course being offered. */
	public record CourseResponse(String code, String title, List<String> prerequisiteCodes) {
	}

	static OfferingResponse of(OfferingView view) {
		return new OfferingResponse(view.offeringId(),
				new CourseResponse(view.courseCode(), view.courseTitle(), view.prerequisiteCodes()), view.capacity(),
				view.availableSeats(), view.enrolledLearnerIds(), view.waitlistedLearnerIds());
	}

}
