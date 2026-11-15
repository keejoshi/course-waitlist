package com.assessment.coursewaitlist.domain;

import java.util.List;

/**
 * An immutable, internally consistent view of a {@link CourseOffering} at one
 * instant: who is enrolled, who is waiting, and how many seats are free.
 *
 * <p>All three values are captured under the offering's lock in a single
 * step, so a snapshot never shows a learner in both lists or a seat count
 * that disagrees with the enrolled list.
 *
 * @param enrolledLearnerIds enrolled learners, in the order they took their seats
 * @param waitlistedLearnerIds waitlisted learners, earliest first
 * @param availableSeats the number of free seats
 */
public record OfferingSnapshot(List<String> enrolledLearnerIds, List<String> waitlistedLearnerIds,
		int availableSeats) {

	public OfferingSnapshot {
		enrolledLearnerIds = Require.idList(enrolledLearnerIds, "enrolled learner ids");
		waitlistedLearnerIds = Require.idList(waitlistedLearnerIds, "waitlisted learner ids");
		if (availableSeats < 0) {
			throw new IllegalArgumentException("available seats must not be negative but was " + availableSeats);
		}
		for (String learnerId : waitlistedLearnerIds) {
			if (enrolledLearnerIds.contains(learnerId)) {
				throw new IllegalArgumentException("learner is both enrolled and waitlisted: " + learnerId);
			}
		}
	}

	/** The capacity of the offering: enrolled learners plus free seats. */
	public int capacity() {
		return enrolledLearnerIds.size() + availableSeats;
	}

}
