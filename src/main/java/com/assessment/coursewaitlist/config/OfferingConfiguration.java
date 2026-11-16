package com.assessment.coursewaitlist.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assessment.coursewaitlist.domain.Course;
import com.assessment.coursewaitlist.domain.CourseOffering;

/** Creates the one {@link CourseOffering} of the application from its configuration. */
@Configuration
@EnableConfigurationProperties(OfferingProperties.class)
public class OfferingConfiguration {

	@Bean
	CourseOffering courseOffering(OfferingProperties properties) {
		Course course = new Course(properties.courseCode(), properties.courseTitle(), properties.prerequisiteCodes());
		return new CourseOffering(properties.id(), course, properties.capacity());
	}

}
