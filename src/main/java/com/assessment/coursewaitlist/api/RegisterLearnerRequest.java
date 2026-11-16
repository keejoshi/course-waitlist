package com.assessment.coursewaitlist.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Body of a learner registration request.
 *
 * @param id the new learner's id
 * @param name the learner's display name
 * @param completedCourseCodes courses already completed; may be omitted. Held as
 * an unmodifiable copy
 */
public record RegisterLearnerRequest(
		@NotNull(message = "is required") @Pattern(regexp = ApiRules.IDENTIFIER,
				message = ApiRules.IDENTIFIER_MESSAGE) String id,
		@NotBlank(message = "is required") @Size(max = 100, message = "must be at most 100 characters") String name,
		@Size(max = 100, message = "must contain at most 100 course codes") List<@NotNull(
				message = "must not be null") @Pattern(regexp = ApiRules.IDENTIFIER,
						message = ApiRules.IDENTIFIER_MESSAGE) String> completedCourseCodes) {

	public RegisterLearnerRequest {
		// Defensive copy, so later changes to the caller's list cannot reach this request.
		// A plain copy is used rather than List.copyOf because it keeps a null element in
		// place for validation to report, instead of failing here with a different error.
		completedCourseCodes = completedCourseCodes == null ? List.of()
				: Collections.unmodifiableList(new ArrayList<>(completedCourseCodes));
	}

}
