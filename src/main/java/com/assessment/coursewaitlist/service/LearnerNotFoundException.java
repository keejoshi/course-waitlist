package com.assessment.coursewaitlist.service;

/** No learner with the given id has been registered. */
public class LearnerNotFoundException extends RuntimeException {

	private final String learnerId;

	public LearnerNotFoundException(String learnerId) {
		super("No learner is registered with id " + learnerId + ".");
		this.learnerId = learnerId;
	}

	public String learnerId() {
		return learnerId;
	}

}
