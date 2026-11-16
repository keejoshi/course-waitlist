package com.assessment.coursewaitlist.config;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Configuration of the single course offering, read from
 * {@code course-waitlist.offering.*}. An invalid value stops the application
 * from starting.
 */
@ConfigurationProperties(prefix = "course-waitlist.offering")
@Validated
public record OfferingProperties(@NotBlank String id, @NotBlank String courseCode, @NotBlank String courseTitle,
		Set<String> prerequisiteCodes, @Min(1) int capacity) {

	public OfferingProperties {
		prerequisiteCodes = prerequisiteCodes == null ? Set.of() : Set.copyOf(prerequisiteCodes);
	}

}
