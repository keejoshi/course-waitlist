package com.assessment.coursewaitlist.api;

/** Validation rules shared by the request types and controllers. */
final class ApiRules {

	/** Learner ids and course codes: 1 to 64 letters, digits, dots, underscores, or hyphens. */
	static final String IDENTIFIER = "[A-Za-z0-9._-]{1,64}";

	static final String IDENTIFIER_MESSAGE = "must be 1 to 64 characters: letters, digits, '.', '_' or '-'";

	private ApiRules() {
	}

}
