package com.assessment.coursewaitlist.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.assessment.coursewaitlist.domain.CourseOffering;
import com.assessment.coursewaitlist.domain.DropResult;
import com.assessment.coursewaitlist.domain.EnrollmentResult;
import com.assessment.coursewaitlist.domain.Learner;

/**
 * Application service for the single course offering.
 *
 * <p>It resolves learner ids to the registered {@link Learner} objects and
 * delegates to the domain. It adds no enrollment rules and no locking of its
 * own: every rule, and the lock that makes each operation atomic, lives in
 * {@link CourseOffering}. Callers receive immutable results and views, never
 * the domain objects themselves.
 */
@Service
public class EnrollmentService {

	private final CourseOffering offering;

	private final LearnerDirectory learners;

	public EnrollmentService(CourseOffering offering, LearnerDirectory learners) {
		this.offering = offering;
		this.learners = learners;
	}

	/**
	 * Requests a seat for a registered learner.
	 * @throws LearnerNotFoundException if the learner is not registered
	 */
	public EnrollmentResult enroll(String learnerId) {
		return offering.enroll(learners.require(learnerId));
	}

	/**
	 * Drops a registered learner from the offering or its waitlist.
	 * @throws LearnerNotFoundException if the learner is not registered
	 */
	public DropResult drop(String learnerId) {
		return offering.drop(learners.require(learnerId).id());
	}

	/** The offering's details and one atomic snapshot of its state. */
	public OfferingView offeringState() {
		return OfferingView.of(offering, offering.snapshot());
	}

	/**
	 * Registers a learner with their initial completions.
	 * @throws LearnerAlreadyExistsException if the id is already registered
	 */
	public LearnerView registerLearner(String id, String name, Collection<String> completedCourseCodes) {
		return LearnerView.of(learners.register(id, name, completedCourseCodes));
	}

	/**
	 * Records a completed course for a registered learner. This does not by
	 * itself offer an open seat to the waitlist (design limitation L1).
	 * @throws LearnerNotFoundException if the learner is not registered
	 */
	public void markCompleted(String learnerId, String courseCode) {
		learners.require(learnerId).markCompleted(courseCode);
	}

	/**
	 * Revokes a completed course for a registered learner. A learner who is
	 * already enrolled keeps their seat.
	 * @throws LearnerNotFoundException if the learner is not registered
	 */
	public void revokeCompletion(String learnerId, String courseCode) {
		learners.require(learnerId).revokeCompletion(courseCode);
	}

}
