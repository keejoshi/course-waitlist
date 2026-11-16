package com.assessment.coursewaitlist.service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;

import com.assessment.coursewaitlist.domain.Learner;

/**
 * The learners known to the service, held in memory.
 *
 * <p>It guarantees exactly one {@link Learner} object per learner id for the
 * life of the application. The domain relies on this: the offering keeps
 * references to waitlisted learners and reads their current completions at
 * promotion time, so a completion change must reach the same object.
 */
@Component
public class LearnerDirectory {

	private final ConcurrentMap<String, Learner> learners = new ConcurrentHashMap<>();

	/**
	 * Registers a new learner with their initial completions.
	 * @throws LearnerAlreadyExistsException if the id is already registered; the
	 * existing learner is left untouched
	 */
	public Learner register(String id, String name, Collection<String> completedCourseCodes) {
		Learner learner = new Learner(id, name);
		for (String courseCode : completedCourseCodes) {
			learner.markCompleted(courseCode);
		}
		// putIfAbsent is atomic: of two simultaneous registrations exactly one wins.
		if (learners.putIfAbsent(id, learner) != null) {
			throw new LearnerAlreadyExistsException(id);
		}
		return learner;
	}

	/** The number of registered learners. Package-private: used only by tests in this package. */
	int size() {
		return learners.size();
	}

	/**
	 * Returns the registered learner.
	 * @throws LearnerNotFoundException if no learner has that id
	 */
	public Learner require(String id) {
		Learner learner = (id == null) ? null : learners.get(id);
		if (learner == null) {
			throw new LearnerNotFoundException(id);
		}
		return learner;
	}

}
