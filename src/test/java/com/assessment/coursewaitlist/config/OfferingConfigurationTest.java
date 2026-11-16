package com.assessment.coursewaitlist.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import com.assessment.coursewaitlist.domain.CourseOffering;

class OfferingConfigurationTest {

	private final ApplicationContextRunner runner = new ApplicationContextRunner()
		.withConfiguration(AutoConfigurations.of(ValidationAutoConfiguration.class))
		.withUserConfiguration(OfferingConfiguration.class);

	private ApplicationContextRunner withOffering(String capacity) {
		return runner.withPropertyValues("course-waitlist.offering.id=MATH300-B",
				"course-waitlist.offering.course-code=MATH300", "course-waitlist.offering.course-title=Analysis",
				"course-waitlist.offering.prerequisite-codes=MATH100,MATH200",
				"course-waitlist.offering.capacity=" + capacity);
	}

	@Test
	@DisplayName("TC-S-06: the single offering is built from configuration; an invalid configuration stops startup")
	void tcS06_offeringIsBuiltFromConfigurationAndInvalidConfigurationFailsStartup() {
		withOffering("7").run(context -> {
			assertThat(context).hasNotFailed();
			// Exactly one offering exists.
			assertThat(context).hasSingleBean(CourseOffering.class);
			CourseOffering offering = context.getBean(CourseOffering.class);
			assertThat(offering.id()).isEqualTo("MATH300-B");
			assertThat(offering.capacity()).isEqualTo(7);
			assertThat(offering.course().code()).isEqualTo("MATH300");
			assertThat(offering.course().title()).isEqualTo("Analysis");
			assertThat(offering.course().prerequisiteCodes()).containsExactlyInAnyOrder("MATH100", "MATH200");
			assertThat(offering.snapshot().availableSeats()).isEqualTo(7);
		});

		withOffering("0").run(context -> assertThat(context).hasFailed());
		withOffering("-3").run(context -> assertThat(context).hasFailed());
		// Missing configuration altogether.
		runner.run(context -> assertThat(context).hasFailed());
		// A course that lists itself as a prerequisite is rejected by the domain.
		runner
			.withPropertyValues("course-waitlist.offering.id=X-1", "course-waitlist.offering.course-code=X",
					"course-waitlist.offering.course-title=X", "course-waitlist.offering.prerequisite-codes=X",
					"course-waitlist.offering.capacity=1")
			.run(context -> assertThat(context).hasFailed());
		// No prerequisites configured is valid.
		runner
			.withPropertyValues("course-waitlist.offering.id=OPEN-1", "course-waitlist.offering.course-code=OPEN",
					"course-waitlist.offering.course-title=Open", "course-waitlist.offering.capacity=1")
			.run(context -> {
				assertThat(context).hasNotFailed();
				assertThat(context.getBean(CourseOffering.class).course().prerequisiteCodes()).isEmpty();
			});
	}

}
