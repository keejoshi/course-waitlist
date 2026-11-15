package com.assessment.coursewaitlist.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * A learner and the courses they have completed.
 *
 * <p>Completions can be added and revoked at any time, from any thread. All
 * access to them is guarded by one private lock, so an eligibility check sees
 * a single consistent set of completions even when a course has several
 * prerequisites. Eligibility is a point-in-time answer: it is evaluated when
 * asked and never cached. Identity is the learner id.
 */
public final class Learner {

	private final String id;

	private final String name;

	private final Object lock = new Object();

	/** Guarded by {@code lock}. */
	private final Set<String> completedCourseCodes = new HashSet<>();

	public Learner(String id, String name) {
		this.id = Require.nonBlank(id, "learner id");
		this.name = Require.nonBlank(name, "learner name");
	}

	public String id() {
		return id;
	}

	public String name() {
		return name;
	}

	/** Records a completed course. Recording the same course twice has no further effect. */
	public void markCompleted(String courseCode) {
		Require.nonBlank(courseCode, "course code");
		synchronized (lock) {
			completedCourseCodes.add(courseCode);
		}
	}

	/** Removes a completed course. Revoking a course that was never completed has no effect. */
	public void revokeCompletion(String courseCode) {
		Require.nonBlank(courseCode, "course code");
		synchronized (lock) {
			completedCourseCodes.remove(courseCode);
		}
	}

	public boolean hasCompleted(String courseCode) {
		Require.nonBlank(courseCode, "course code");
		synchronized (lock) {
			return completedCourseCodes.contains(courseCode);
		}
	}

	/** An immutable copy of the completed course codes at the time of the call. */
	public Set<String> completedCourseCodes() {
		synchronized (lock) {
			return Set.copyOf(completedCourseCodes);
		}
	}

	/**
	 * The prerequisite rule: a learner is eligible for a course when they have
	 * completed every one of its prerequisites. All prerequisites are checked
	 * against the same state of the learner's completions.
	 */
	public boolean isEligibleFor(Course course) {
		Require.nonNull(course, "course");
		synchronized (lock) {
			return completedCourseCodes.containsAll(course.prerequisiteCodes());
		}
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Learner learner && id.equals(learner.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "Learner[" + id + "]";
	}

}
