package com.assessment.coursewaitlist.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * One offering of a course with a fixed number of seats. It owns the enrolled
 * list and the FIFO waitlist and enforces every rule about them.
 *
 * <h2>Invariants</h2>
 * <ul>
 * <li>The number of enrolled learners never exceeds the capacity.</li>
 * <li>A learner is enrolled, waitlisted, or neither; never both, never twice.</li>
 * <li>A learner is eligible at the moment they are enrolled or promoted.</li>
 * <li>Nobody joins the waitlist while a seat is free.</li>
 * <li>A duplicate, rejected, or invalid request changes nothing.</li>
 * <li>Promotion never reorders the learners who remain on the waitlist.</li>
 * </ul>
 *
 * <h2>Open-seat policy</h2>
 * Open seats are offered to the waitlist at exactly two moments: right after an
 * enrolled learner drops, and right before a valid new enrollment request is
 * placed. Each time, the earliest eligible waitlisted learner is promoted,
 * repeatedly, until no seat is free or no eligible learner is waiting.
 * Ineligible learners are skipped and keep their positions. If nobody waiting
 * is eligible, the seat stays vacant.
 *
 * <h2>Thread safety</h2>
 * All state is guarded by one private lock, held for the whole of each
 * operation, so every check and the change that follows it are atomic.
 * Eligibility checks take the learner's own lock while this lock is held;
 * the order is always offering first, learner second, and a learner never
 * calls back into an offering, so the two locks cannot deadlock.
 */
public final class CourseOffering {

	private final String id;

	private final Course course;

	private final int capacity;

	private final Object lock = new Object();

	/** Enrolled learners by id, in the order they took their seats. Guarded by {@code lock}. */
	private final Map<String, Learner> enrolled = new LinkedHashMap<>();

	/** Waitlisted learners by id, in arrival (FIFO) order. Guarded by {@code lock}. */
	private final Map<String, Learner> waitlist = new LinkedHashMap<>();

	public CourseOffering(String id, Course course, int capacity) {
		this.id = Require.nonBlank(id, "offering id");
		this.course = Require.nonNull(course, "course");
		if (capacity < 1) {
			throw new IllegalArgumentException("capacity must be at least 1 but was " + capacity);
		}
		this.capacity = capacity;
	}

	public String id() {
		return id;
	}

	public Course course() {
		return course;
	}

	public int capacity() {
		return capacity;
	}

	/**
	 * Requests a seat for the learner.
	 *
	 * <p>Checks run in this order: duplicate, prerequisites, capacity. A
	 * duplicate or ineligible request returns without changing anything. For a
	 * valid new request, open seats are first offered to eligible waitlisted
	 * learners; the requester then takes a seat if one is still free and
	 * otherwise joins the end of the waitlist.
	 */
	public EnrollmentResult enroll(Learner learner) {
		Require.nonNull(learner, "learner");
		synchronized (lock) {
			if (enrolled.containsKey(learner.id())) {
				return EnrollmentResult.of(EnrollmentResult.Outcome.ALREADY_ENROLLED);
			}
			if (waitlist.containsKey(learner.id())) {
				return EnrollmentResult.of(EnrollmentResult.Outcome.ALREADY_WAITLISTED);
			}
			if (!learner.isEligibleFor(course)) {
				return EnrollmentResult.of(EnrollmentResult.Outcome.PREREQUISITES_NOT_MET);
			}
			List<String> promoted = promoteEligibleWaiters();
			if (enrolled.size() < capacity) {
				enrolled.put(learner.id(), learner);
				return new EnrollmentResult(EnrollmentResult.Outcome.ENROLLED, promoted);
			}
			waitlist.put(learner.id(), learner);
			return new EnrollmentResult(EnrollmentResult.Outcome.WAITLISTED, promoted);
		}
	}

	/**
	 * Removes the learner from the offering.
	 *
	 * <p>An enrolled learner gives up their seat, and open seats are then
	 * offered to the waitlist. A waitlisted learner leaves the waitlist; no seat
	 * changes hands and nobody is promoted. A learner who is neither changes
	 * nothing, which is also the result of repeating a drop.
	 */
	public DropResult drop(String learnerId) {
		Require.nonBlank(learnerId, "learner id");
		synchronized (lock) {
			if (enrolled.remove(learnerId) != null) {
				return new DropResult(DropResult.Outcome.DROPPED, promoteEligibleWaiters());
			}
			if (waitlist.remove(learnerId) != null) {
				return DropResult.of(DropResult.Outcome.DROPPED_FROM_WAITLIST);
			}
			return DropResult.of(DropResult.Outcome.NOT_ENROLLED);
		}
	}

	/**
	 * A consistent view of the whole offering, captured under the lock in one
	 * step. Use this whenever more than one of the enrolled list, the waitlist,
	 * and the free-seat count is needed together.
	 */
	public OfferingSnapshot snapshot() {
		synchronized (lock) {
			return new OfferingSnapshot(List.copyOf(enrolled.keySet()), List.copyOf(waitlist.keySet()),
					capacity - enrolled.size());
		}
	}

	/**
	 * Ids of the enrolled learners, in the order they took their seats. Immutable
	 * copy. To read this together with the waitlist or seat count, use
	 * {@link #snapshot()}; separate calls are not atomic with each other.
	 */
	public List<String> enrolledLearnerIds() {
		synchronized (lock) {
			return List.copyOf(enrolled.keySet());
		}
	}

	/**
	 * Ids of the waitlisted learners, earliest first. Immutable copy. See
	 * {@link #snapshot()} for a combined view.
	 */
	public List<String> waitlistedLearnerIds() {
		synchronized (lock) {
			return List.copyOf(waitlist.keySet());
		}
	}

	/** The number of free seats. See {@link #snapshot()} for a combined view. */
	public int availableSeats() {
		synchronized (lock) {
			return capacity - enrolled.size();
		}
	}

	/**
	 * Fills open seats from the waitlist, earliest eligible learner first.
	 * Ineligible learners are passed over and left where they are. Must be
	 * called with {@code lock} held.
	 */
	private List<String> promoteEligibleWaiters() {
		List<String> promoted = new ArrayList<>();
		Iterator<Learner> waiters = waitlist.values().iterator();
		while (enrolled.size() < capacity && waiters.hasNext()) {
			Learner waiter = waiters.next();
			if (waiter.isEligibleFor(course)) {
				waiters.remove();
				enrolled.put(waiter.id(), waiter);
				promoted.add(waiter.id());
			}
		}
		return List.copyOf(promoted);
	}

}
