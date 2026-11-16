package com.assessment.coursewaitlist.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/** Body of an enrollment request. */
public record EnrollRequest(
		@NotNull(message = "is required") @Pattern(regexp = ApiRules.IDENTIFIER,
				message = ApiRules.IDENTIFIER_MESSAGE) String learnerId) {
}
