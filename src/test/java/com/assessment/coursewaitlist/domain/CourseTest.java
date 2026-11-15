package com.assessment.coursewaitlist.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CourseTest {

	@Test
	@DisplayName("TC-D-29: Course is immutable and rejects blank codes")
	void tcD29_courseIsImmutableAndRejectsBlankCodes() {
		Set<String> prerequisites = new HashSet<>(Set.of("CS101"));
		Course course = new Course("CS201", "Data Structures", prerequisites);

		// Later changes to the caller's set do not reach the course.
		prerequisites.add("MATH101");
		assertThat(course.prerequisiteCodes()).containsExactly("CS101");

		// The exposed set cannot be modified.
		assertThatThrownBy(() -> course.prerequisiteCodes().add("MATH101"))
			.isInstanceOf(UnsupportedOperationException.class);
		assertThat(course.prerequisiteCodes()).containsExactly("CS101");

		assertThatIllegalArgumentException().isThrownBy(() -> new Course(" ", "Title", Set.of()));
		assertThatIllegalArgumentException().isThrownBy(() -> new Course(null, "Title", Set.of()));
		assertThatIllegalArgumentException().isThrownBy(() -> new Course("CS201", "", Set.of()));
		assertThatIllegalArgumentException().isThrownBy(() -> new Course("CS201", "Title", null));
		assertThatIllegalArgumentException().isThrownBy(() -> new Course("CS201", "Title", Set.of(" ")));
		assertThatIllegalArgumentException().isThrownBy(() -> new Course("CS201", "Title", Set.of("CS201")));
		// A null element is reported as an invalid argument, like a blank one.
		assertThatIllegalArgumentException()
			.isThrownBy(() -> new Course("CS201", "Title", new HashSet<>(Arrays.asList("CS101", null))));
	}

}
