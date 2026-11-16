package com.assessment.coursewaitlist.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegisterLearnerRequestTest {

	@Test
	@DisplayName("TC-A-22: RegisterLearnerRequest copies its course list: changing the original cannot change the request, and the copy is unmodifiable")
	void tcA22_registerLearnerRequestDefensivelyCopiesItsCourseList() {
		List<String> original = new ArrayList<>(List.of("CS101", "MATH100"));

		RegisterLearnerRequest request = new RegisterLearnerRequest("L1", "Asha", original);

		// Changes to the caller's list after construction do not reach the request.
		original.add("INJECTED");
		original.set(0, "REPLACED");
		assertThat(request.completedCourseCodes()).containsExactly("CS101", "MATH100");
		original.clear();
		assertThat(request.completedCourseCodes()).containsExactly("CS101", "MATH100");

		// The request's own list cannot be modified either.
		assertThatThrownBy(() -> request.completedCourseCodes().add("X"))
			.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> request.completedCourseCodes().set(0, "X"))
			.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> request.completedCourseCodes().clear())
			.isInstanceOf(UnsupportedOperationException.class);
		assertThat(request.completedCourseCodes()).containsExactly("CS101", "MATH100");

		// Null handling is unchanged: an omitted list is empty, not null ...
		RegisterLearnerRequest omitted = new RegisterLearnerRequest("L2", "Ben", null);
		assertThat(omitted.completedCourseCodes()).isNotNull().isEmpty();
		assertThatThrownBy(() -> omitted.completedCourseCodes().add("X"))
			.isInstanceOf(UnsupportedOperationException.class);
		// ... and a null element is kept in place for request validation to reject (TC-A-16),
		// rather than failing during construction.
		RegisterLearnerRequest withNullElement = new RegisterLearnerRequest("L3", "Cy", Arrays.asList("CS101", null));
		assertThat(withNullElement.completedCourseCodes()).containsExactly("CS101", null);
	}

}
