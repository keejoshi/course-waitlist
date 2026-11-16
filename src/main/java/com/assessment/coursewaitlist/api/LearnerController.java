package com.assessment.coursewaitlist.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.coursewaitlist.service.EnrollmentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

/**
 * REST endpoints for the learners known to the service: registration, and
 * marking or revoking a completed course. These exist so that prerequisite
 * eligibility, which is re-checked at promotion, can be set up and changed.
 */
@RestController
@RequestMapping("/api/learners")
public class LearnerController {

	private final EnrollmentService service;

	public LearnerController(EnrollmentService service) {
		this.service = service;
	}

	/** Registers a learner. A second registration of the same id is 409. */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LearnerResponse register(@Valid @RequestBody RegisterLearnerRequest request) {
		return LearnerResponse.of(service.registerLearner(request.id(), request.name(), request.completedCourseCodes()));
	}

	/** Marks a course as completed. Repeating the request has no further effect. */
	@PutMapping("/{learnerId}/completed-courses/{courseCode}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void markCompleted(
			@PathVariable @Pattern(regexp = ApiRules.IDENTIFIER,
					message = ApiRules.IDENTIFIER_MESSAGE) String learnerId,
			@PathVariable @Pattern(regexp = ApiRules.IDENTIFIER,
					message = ApiRules.IDENTIFIER_MESSAGE) String courseCode) {
		service.markCompleted(learnerId, courseCode);
	}

	/** Revokes a completed course. Repeating the request has no further effect. */
	@DeleteMapping("/{learnerId}/completed-courses/{courseCode}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void revokeCompletion(
			@PathVariable @Pattern(regexp = ApiRules.IDENTIFIER,
					message = ApiRules.IDENTIFIER_MESSAGE) String learnerId,
			@PathVariable @Pattern(regexp = ApiRules.IDENTIFIER,
					message = ApiRules.IDENTIFIER_MESSAGE) String courseCode) {
		service.revokeCompletion(learnerId, courseCode);
	}

}
