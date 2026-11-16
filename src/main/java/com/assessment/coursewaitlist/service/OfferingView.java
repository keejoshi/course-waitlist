package com.assessment.coursewaitlist.service;

import java.util.List;

import com.assessment.coursewaitlist.domain.CourseOffering;
import com.assessment.coursewaitlist.domain.OfferingSnapshot;

/**
 * An immutable copy of the offering's fixed details together with one atomic
 * snapshot of its enrolled list, waitlist, and free-seat count.
 */
public record OfferingView(String offeringId, String courseCode, String courseTitle, List<String> prerequisiteCodes,
		int capacity, int availableSeats, List<String> enrolledLearnerIds, List<String> waitlistedLearnerIds) {

	public OfferingView {
		prerequisiteCodes = List.copyOf(prerequisiteCodes);
		enrolledLearnerIds = List.copyOf(enrolledLearnerIds);
		waitlistedLearnerIds = List.copyOf(waitlistedLearnerIds);
	}

	static OfferingView of(CourseOffering offering, OfferingSnapshot snapshot) {
		return new OfferingView(offering.id(), offering.course().code(), offering.course().title(),
				offering.course().prerequisiteCodes().stream().sorted().toList(), offering.capacity(),
				snapshot.availableSeats(), snapshot.enrolledLearnerIds(), snapshot.waitlistedLearnerIds());
	}

}
