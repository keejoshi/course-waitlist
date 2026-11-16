package com.assessment.coursewaitlist.service;

/** A learner with the given id is already registered. */
public class LearnerAlreadyExistsException extends RuntimeException {

	private final String learnerId;

	public LearnerAlreadyExistsException(String learnerId) {
		super("A learner is already registered with id " + learnerId + ".");
		this.learnerId = learnerId;
	}

	public String learnerId() {
		return learnerId;
	}

}
