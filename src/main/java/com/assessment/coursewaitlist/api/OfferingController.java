package com.assessment.coursewaitlist.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.coursewaitlist.domain.DropResult;
import com.assessment.coursewaitlist.domain.EnrollmentResult;
import com.assessment.coursewaitlist.service.EnrollmentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

/**
 * REST endpoints for the single course offering. There is one offering, so
 * no path carries an offering id.
 */
@RestController
@RequestMapping("/api/offering")
public class OfferingController {

	private final EnrollmentService service;

	public OfferingController(EnrollmentService service) {
		this.service = service;
	}

	/** The offering and its current enrolled list, waitlist, and free seats. */
	@GetMapping
	public OfferingResponse offering() {
		return OfferingResponse.of(service.offeringState());
	}

	/**
	 * Requests a seat. Returns 200 with status {@code ENROLLED} or
	 * {@code WAITLISTED}; a duplicate request is 409 and a prerequisite failure
	 * is 422.
	 */
	@PostMapping("/enrollments")
	public EnrollmentResponse enroll(@Valid @RequestBody EnrollRequest request) {
		String learnerId = request.learnerId();
		EnrollmentResult result = service.enroll(learnerId);
		return switch (result.outcome()) {
			case ENROLLED, WAITLISTED -> EnrollmentResponse.of(learnerId, result);
			case ALREADY_ENROLLED -> throw new RequestRejectedException(HttpStatus.CONFLICT, "ALREADY_ENROLLED",
					"Learner " + learnerId + " is already enrolled.");
			case ALREADY_WAITLISTED -> throw new RequestRejectedException(HttpStatus.CONFLICT, "ALREADY_WAITLISTED",
					"Learner " + learnerId + " is already on the waitlist.");
			case PREREQUISITES_NOT_MET -> throw new RequestRejectedException(HttpStatus.UNPROCESSABLE_ENTITY,
					"PREREQUISITES_NOT_MET",
					"Learner " + learnerId + " has not completed every prerequisite of the course.");
		};
	}

	/**
	 * Drops a learner. Returns 200 with status {@code DROPPED} or
	 * {@code DROPPED_FROM_WAITLIST}; a learner who is neither enrolled nor
	 * waitlisted, including a repeated drop, is 409.
	 */
	@DeleteMapping("/enrollments/{learnerId}")
	public DropResponse drop(@PathVariable @Pattern(regexp = ApiRules.IDENTIFIER,
			message = ApiRules.IDENTIFIER_MESSAGE) String learnerId) {
		DropResult result = service.drop(learnerId);
		return switch (result.outcome()) {
			case DROPPED, DROPPED_FROM_WAITLIST -> DropResponse.of(learnerId, result);
			case NOT_ENROLLED -> throw new RequestRejectedException(HttpStatus.CONFLICT, "NOT_ENROLLED",
					"Learner " + learnerId + " is neither enrolled nor on the waitlist.");
		};
	}

}
