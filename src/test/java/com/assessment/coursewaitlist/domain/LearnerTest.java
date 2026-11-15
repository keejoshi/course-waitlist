package com.assessment.coursewaitlist.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LearnerTest {

	@Test
	@DisplayName("TC-D-28: Learner marks and revokes completions; eligible only with all prerequisites")
	void tcD28_learnerMarksAndRevokesCompletionsAndIsEligibleOnlyWithAllPrerequisites() {
		Course course = new Course("CS301", "Algorithms", Set.of("CS101", "CS201"));
		Learner learner = new Learner("L1", "Learner One");

		assertThat(learner.isEligibleFor(course)).isFalse();

		learner.markCompleted("CS101");
		assertThat(learner.hasCompleted("CS101")).isTrue();
		assertThat(learner.isEligibleFor(course)).isFalse();

		learner.markCompleted("CS201");
		assertThat(learner.isEligibleFor(course)).isTrue();

		// Marking twice has no further effect.
		learner.markCompleted("CS201");
		assertThat(learner.completedCourseCodes()).containsExactlyInAnyOrder("CS101", "CS201");

		learner.revokeCompletion("CS101");
		assertThat(learner.hasCompleted("CS101")).isFalse();
		assertThat(learner.isEligibleFor(course)).isFalse();

		// Revoking a course that was never completed has no effect.
		learner.revokeCompletion("MATH101");
		assertThat(learner.completedCourseCodes()).containsExactly("CS201");

		learner.markCompleted("CS101");
		assertThat(learner.isEligibleFor(course)).isTrue();

		// The exposed set is a copy and cannot be used to change the learner.
		assertThatThrownBy(() -> learner.completedCourseCodes().clear())
			.isInstanceOf(UnsupportedOperationException.class);
		assertThat(learner.isEligibleFor(course)).isTrue();

		// Identity is the id.
		assertThat(learner).isEqualTo(new Learner("L1", "Another Name")).isNotEqualTo(new Learner("L2", "Learner One"));

		assertThatIllegalArgumentException().isThrownBy(() -> new Learner(" ", "Name"));
		assertThatIllegalArgumentException().isThrownBy(() -> new Learner("L1", null));
		assertThatIllegalArgumentException().isThrownBy(() -> learner.markCompleted(" "));
		assertThatIllegalArgumentException().isThrownBy(() -> learner.revokeCompletion(null));
		assertThatIllegalArgumentException().isThrownBy(() -> learner.isEligibleFor(null));
	}

}
