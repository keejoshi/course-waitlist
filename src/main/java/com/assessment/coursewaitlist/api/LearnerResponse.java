package com.assessment.coursewaitlist.api;

import java.util.List;

import com.assessment.coursewaitlist.service.LearnerView;

/** A registered learner. */
public record LearnerResponse(String id, String name, List<String> completedCourseCodes) {

	static LearnerResponse of(LearnerView view) {
		return new LearnerResponse(view.id(), view.name(), view.completedCourseCodes());
	}

}
