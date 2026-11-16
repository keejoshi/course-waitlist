package com.assessment.coursewaitlist.api;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.type.LogicalType;

/**
 * Makes JSON reading strict for text fields: a JSON number or boolean is not
 * silently turned into a string. A request such as {@code {"learnerId": 5}}
 * is therefore rejected as malformed (400) instead of being read as the id
 * {@code "5"}. This applies to every text field of every request body.
 */
@Configuration
class JsonConfiguration {

	@Bean
	Jackson2ObjectMapperBuilderCustomizer rejectNumbersAndBooleansForTextFields() {
		return builder -> builder.postConfigurer(mapper -> mapper.coercionConfigFor(LogicalType.Textual)
			.setCoercion(CoercionInputShape.Integer, CoercionAction.Fail)
			.setCoercion(CoercionInputShape.Float, CoercionAction.Fail)
			.setCoercion(CoercionInputShape.Boolean, CoercionAction.Fail));
	}

}
