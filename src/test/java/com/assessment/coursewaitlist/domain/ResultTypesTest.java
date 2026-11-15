package com.assessment.coursewaitlist.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Direct tests for {@link EnrollmentResult} and {@link DropResult}. */
class ResultTypesTest {

	@Test
	@DisplayName("TC-D-36: EnrollmentResult rejects contradictory and malformed values and is immutable")
	void tcD36_enrollmentResultRejectsContradictoryValuesAndIsImmutable() {
		// Promotions may accompany only a request that was placed.
		assertThat(new EnrollmentResult(EnrollmentResult.Outcome.ENROLLED, List.of("W")).promotedLearnerIds())
			.containsExactly("W");
		assertThat(new EnrollmentResult(EnrollmentResult.Outcome.WAITLISTED, List.of("W1", "W2")).promotedLearnerIds())
			.containsExactly("W1", "W2");
		for (EnrollmentResult.Outcome unchanged : List.of(EnrollmentResult.Outcome.ALREADY_ENROLLED,
				EnrollmentResult.Outcome.ALREADY_WAITLISTED, EnrollmentResult.Outcome.PREREQUISITES_NOT_MET)) {
			assertThat(new EnrollmentResult(unchanged, List.of()).promotedLearnerIds()).isEmpty();
			assertThatIllegalArgumentException().isThrownBy(() -> new EnrollmentResult(unchanged, List.of("W")))
				.withMessageContaining(unchanged.name());
		}

		// Malformed values.
		assertThatIllegalArgumentException().isThrownBy(() -> new EnrollmentResult(null, List.of()));
		assertThatIllegalArgumentException()
			.isThrownBy(() -> new EnrollmentResult(EnrollmentResult.Outcome.ENROLLED, null));
		assertThatIllegalArgumentException()
			.isThrownBy(() -> new EnrollmentResult(EnrollmentResult.Outcome.ENROLLED, Arrays.asList("W", null)));
		assertThatIllegalArgumentException()
			.isThrownBy(() -> new EnrollmentResult(EnrollmentResult.Outcome.ENROLLED, List.of(" ")));
		assertThatIllegalArgumentException()
			.isThrownBy(() -> new EnrollmentResult(EnrollmentResult.Outcome.ENROLLED, List.of("W", "W")));

		// The promoted list is copied on the way in and cannot be modified on the way out.
		List<String> source = new ArrayList<>(List.of("W1"));
		EnrollmentResult result = new EnrollmentResult(EnrollmentResult.Outcome.ENROLLED, source);
		source.add("W2");
		assertThat(result.promotedLearnerIds()).containsExactly("W1");
		assertThatThrownBy(() -> result.promotedLearnerIds().add("X")).isInstanceOf(UnsupportedOperationException.class);
		assertThat(result.promotedLearnerIds()).containsExactly("W1");
	}

	@Test
	@DisplayName("TC-D-37: DropResult rejects contradictory and malformed values and is immutable")
	void tcD37_dropResultRejectsContradictoryValuesAndIsImmutable() {
		// Only giving up a seat can promote anyone.
		assertThat(new DropResult(DropResult.Outcome.DROPPED, List.of("W")).promotedLearnerIds()).containsExactly("W");
		assertThat(new DropResult(DropResult.Outcome.DROPPED, List.of()).promotedLearnerIds()).isEmpty();
		for (DropResult.Outcome noSeatOpened : List.of(DropResult.Outcome.DROPPED_FROM_WAITLIST,
				DropResult.Outcome.NOT_ENROLLED)) {
			assertThat(new DropResult(noSeatOpened, List.of()).promotedLearnerIds()).isEmpty();
			assertThatIllegalArgumentException().isThrownBy(() -> new DropResult(noSeatOpened, List.of("W")))
				.withMessageContaining(noSeatOpened.name());
		}

		// Malformed values.
		assertThatIllegalArgumentException().isThrownBy(() -> new DropResult(null, List.of()));
		assertThatIllegalArgumentException().isThrownBy(() -> new DropResult(DropResult.Outcome.DROPPED, null));
		assertThatIllegalArgumentException()
			.isThrownBy(() -> new DropResult(DropResult.Outcome.DROPPED, Arrays.asList("W", null)));
		assertThatIllegalArgumentException().isThrownBy(() -> new DropResult(DropResult.Outcome.DROPPED, List.of("")));
		assertThatIllegalArgumentException()
			.isThrownBy(() -> new DropResult(DropResult.Outcome.DROPPED, List.of("W", "W")));

		// The promoted list is copied on the way in and cannot be modified on the way out.
		List<String> source = new ArrayList<>(List.of("W1"));
		DropResult result = new DropResult(DropResult.Outcome.DROPPED, source);
		source.clear();
		assertThat(result.promotedLearnerIds()).containsExactly("W1");
		assertThatThrownBy(() -> result.promotedLearnerIds().clear()).isInstanceOf(UnsupportedOperationException.class);
		assertThat(result.promotedLearnerIds()).containsExactly("W1");
	}

}
