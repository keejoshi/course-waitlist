package com.assessment.coursewaitlist.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Immutable catalog data for a course: its code, title, and the codes of the
 * courses a learner must have completed before taking it.
 *
 * <p>An empty prerequisite set means every learner is eligible.
 */
public record Course(String code, String title, Set<String> prerequisiteCodes) {

	public Course {
		Require.nonBlank(code, "course code");
		Require.nonBlank(title, "course title");
		Require.nonNull(prerequisiteCodes, "prerequisite codes");
		// Copy first, then validate the copy, so the caller cannot change what was checked.
		Set<String> copy = new HashSet<>(prerequisiteCodes);
		for (String prerequisiteCode : copy) {
			Require.nonBlank(prerequisiteCode, "prerequisite code");
		}
		if (copy.contains(code)) {
			throw new IllegalArgumentException("a course cannot be its own prerequisite: " + code);
		}
		prerequisiteCodes = Set.copyOf(copy);
	}

}
