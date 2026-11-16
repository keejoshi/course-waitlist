package com.assessment.coursewaitlist.service;

import java.util.List;

import com.assessment.coursewaitlist.domain.Learner;

/** An immutable copy of a learner's details at one instant. */
public record LearnerView(String id, String name, List<String> completedCourseCodes) {

	public LearnerView {
		completedCourseCodes = List.copyOf(completedCourseCodes);
	}

	static LearnerView of(Learner learner) {
		return new LearnerView(learner.id(), learner.name(), learner.completedCourseCodes().stream().sorted().toList());
	}

}
