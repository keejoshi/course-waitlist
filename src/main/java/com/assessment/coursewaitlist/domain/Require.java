package com.assessment.coursewaitlist.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Argument checks shared by the domain types. */
final class Require {

	private Require() {
	}

	static String nonBlank(String value, String name) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException(name + " must not be null or blank");
		}
		return value;
	}

	static <T> T nonNull(T value, String name) {
		if (value == null) {
			throw new IllegalArgumentException(name + " must not be null");
		}
		return value;
	}

	/**
	 * Returns an immutable copy of a list of ids after checking that the copy
	 * contains no null, blank, or repeated id.
	 */
	static List<String> idList(Collection<String> ids, String name) {
		nonNull(ids, name);
		List<String> copy = new ArrayList<>(ids);
		for (String id : copy) {
			nonBlank(id, "each of the " + name);
		}
		if (copy.stream().distinct().count() != copy.size()) {
			throw new IllegalArgumentException(name + " must not contain the same id twice: " + copy);
		}
		return List.copyOf(copy);
	}

}
