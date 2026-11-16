package com.assessment.coursewaitlist.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

/**
 * API tests for the offering endpoints, through the full Spring MVC stack.
 * Every test starts from a fresh application context, so the offering is
 * empty and no learner is registered. Each test carries the id of its entry
 * in docs/REQUIREMENTS_TRACEABILITY.md.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class OfferingApiTest extends ApiTestSupport {

	@Test
	@DisplayName("TC-A-01: enroll with a seat free returns 200 ENROLLED and the state shows the learner")
	void tcA01_enrollWithSeatFreeReturnsEnrolled() throws Exception {
		registerEligible("A");
		assertState(List.of(), List.of());

		enroll("A").andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.learnerId", is("A")))
			.andExpect(jsonPath("$.status", is("ENROLLED")))
			.andExpect(jsonPath("$.promotedLearnerIds", empty()));

		assertState(List.of("A"), List.of());
	}

	@Test
	@DisplayName("TC-A-02: enroll when full returns 200 WAITLISTED and the enrolled list is unchanged")
	void tcA02_enrollWhenFullReturnsWaitlisted() throws Exception {
		registerEligible("A", "B", "C");
		enroll("A").andExpect(jsonPath("$.status", is("ENROLLED")));
		enroll("B").andExpect(jsonPath("$.status", is("ENROLLED")));

		enroll("C").andExpect(status().isOk())
			.andExpect(jsonPath("$.learnerId", is("C")))
			.andExpect(jsonPath("$.status", is("WAITLISTED")))
			.andExpect(jsonPath("$.promotedLearnerIds", empty()));

		assertState(List.of("A", "B"), List.of("C"));
	}

	@Test
	@DisplayName("TC-A-03: an enrolled learner enrolling again gets 409 ALREADY_ENROLLED and the state is unchanged")
	void tcA03_duplicateEnrollmentReturnsConflict() throws Exception {
		registerEligible("A", "B");
		enroll("A").andExpect(status().isOk());
		enroll("B").andExpect(status().isOk());

		expectProblem(enroll("A"), 409, "ALREADY_ENROLLED").andExpect(jsonPath("$.detail", containsString("A")));
		expectProblem(enroll("A"), 409, "ALREADY_ENROLLED");

		assertState(List.of("A", "B"), List.of());
	}

	@Test
	@DisplayName("TC-A-04: a waitlisted learner enrolling again gets 409 ALREADY_WAITLISTED and keeps their position")
	void tcA04_duplicateWaitlistRequestReturnsConflict() throws Exception {
		registerEligible("A", "B", "C", "D");
		enroll("A");
		enroll("B");
		enroll("C").andExpect(jsonPath("$.status", is("WAITLISTED")));
		enroll("D").andExpect(jsonPath("$.status", is("WAITLISTED")));

		expectProblem(enroll("C"), 409, "ALREADY_WAITLISTED");

		assertState(List.of("A", "B"), List.of("C", "D"));
	}

	@Test
	@DisplayName("TC-A-05: an ineligible learner gets 422 with a seat free and when full; never enrolled or waitlisted")
	void tcA05_prerequisiteFailureReturnsUnprocessableEntity() throws Exception {
		registerIneligible("X");
		registerEligible("A", "B");

		// Seat free.
		expectProblem(enroll("X"), 422, "PREREQUISITES_NOT_MET").andExpect(jsonPath("$.detail", containsString("X")));
		assertState(List.of(), List.of());

		// Full.
		enroll("A");
		enroll("B");
		expectProblem(enroll("X"), 422, "PREREQUISITES_NOT_MET");
		assertState(List.of("A", "B"), List.of());
	}

	@Test
	@DisplayName("TC-A-06: drop returns 200 DROPPED, reports the promoted learner, and the state shows the promotion")
	void tcA06_dropReturnsDroppedAndReportsPromotion() throws Exception {
		registerEligible("A", "B", "C", "D");
		enroll("A");
		enroll("B");
		enroll("C");
		enroll("D");
		assertState(List.of("A", "B"), List.of("C", "D"));

		drop("A").andExpect(status().isOk())
			.andExpect(jsonPath("$.learnerId", is("A")))
			.andExpect(jsonPath("$.status", is("DROPPED")))
			.andExpect(jsonPath("$.promotedLearnerIds", contains("C")));

		assertState(List.of("B", "C"), List.of("D"));

		// With nobody left to promote, the seat simply opens.
		drop("D").andExpect(jsonPath("$.status", is("DROPPED_FROM_WAITLIST")));
		drop("B").andExpect(status().isOk())
			.andExpect(jsonPath("$.status", is("DROPPED")))
			.andExpect(jsonPath("$.promotedLearnerIds", empty()));
		assertState(List.of("C"), List.of());
	}

	@Test
	@DisplayName("TC-A-07: a repeated drop, or a drop by a learner never enrolled, gets 409 NOT_ENROLLED; state unchanged")
	void tcA07_repeatedDropReturnsConflict() throws Exception {
		registerEligible("A", "B", "C", "NEVER");
		enroll("A");
		enroll("B");
		enroll("C");
		drop("A").andExpect(status().isOk()).andExpect(jsonPath("$.promotedLearnerIds", contains("C")));
		assertState(List.of("B", "C"), List.of());

		expectProblem(drop("A"), 409, "NOT_ENROLLED").andExpect(jsonPath("$.detail", containsString("A")));
		expectProblem(drop("A"), 409, "NOT_ENROLLED");
		expectProblem(drop("NEVER"), 409, "NOT_ENROLLED");

		assertState(List.of("B", "C"), List.of());
	}

	@Test
	@DisplayName("TC-A-08: an unknown learner gets 404 LEARNER_NOT_FOUND on enroll and on drop; state unchanged")
	void tcA08_unknownLearnerReturnsNotFound() throws Exception {
		registerEligible("A", "B", "C");
		enroll("A");
		enroll("B");
		enroll("C");

		expectProblem(enroll("GHOST"), 404, "LEARNER_NOT_FOUND").andExpect(jsonPath("$.detail", containsString("GHOST")));
		expectProblem(drop("GHOST"), 404, "LEARNER_NOT_FOUND");

		assertState(List.of("A", "B"), List.of("C"));
		// The failed requests did not register the learner as a side effect.
		expectProblem(enroll("GHOST"), 404, "LEARNER_NOT_FOUND");
	}

	@Test
	@DisplayName("TC-A-09: missing body, missing or blank id, bad id format, and malformed JSON get 400; state unchanged")
	void tcA09_invalidRequestsReturnBadRequest() throws Exception {
		registerEligible("A");
		enroll("A");

		// Missing body.
		expectProblem(mockMvc.perform(post("/api/offering/enrollments").contentType(MediaType.APPLICATION_JSON)), 400,
				"INVALID_REQUEST");
		// Missing field, null, blank, whitespace.
		expectProblem(enrollWithBody("{}"), 400, "INVALID_REQUEST")
			.andExpect(jsonPath("$.detail", containsString("learnerId")));
		expectProblem(enrollWithBody("{\"learnerId\":null}"), 400, "INVALID_REQUEST");
		expectProblem(enrollWithBody("{\"learnerId\":\"\"}"), 400, "INVALID_REQUEST");
		expectProblem(enrollWithBody("{\"learnerId\":\"   \"}"), 400, "INVALID_REQUEST");
		// Characters outside the allowed set, and too long.
		expectProblem(enrollWithBody("{\"learnerId\":\"A B\"}"), 400, "INVALID_REQUEST");
		expectProblem(enrollWithBody("{\"learnerId\":\"<script>\"}"), 400, "INVALID_REQUEST");
		expectProblem(enrollWithBody("{\"learnerId\":\"%s\"}".formatted("x".repeat(65))), 400, "INVALID_REQUEST");
		// Malformed JSON and a non-object body.
		expectProblem(enrollWithBody("{\"learnerId\":"), 400, "INVALID_REQUEST");
		expectProblem(enrollWithBody("not json"), 400, "INVALID_REQUEST");
		// Invalid id in the drop path.
		expectProblem(drop("A B"), 400, "INVALID_REQUEST").andExpect(jsonPath("$.detail", containsString("learnerId")));
		expectProblem(drop("x".repeat(65)), 400, "INVALID_REQUEST");
		// Wrong content type.
		expectProblem(mockMvc.perform(post("/api/offering/enrollments").contentType(MediaType.TEXT_PLAIN).content("A")),
				415, "UNSUPPORTED_MEDIA_TYPE");

		assertState(List.of("A"), List.of());
	}

	@Test
	@DisplayName("TC-A-11: a waitlisted learner leaving gets 200 DROPPED_FROM_WAITLIST; nobody promoted; enrolled unchanged")
	void tcA11_waitlistedLearnerLeavesWaitlist() throws Exception {
		registerEligible("A", "B", "C", "D", "E");
		enroll("A");
		enroll("B");
		enroll("C");
		enroll("D");
		enroll("E");
		assertState(List.of("A", "B"), List.of("C", "D", "E"));

		drop("D").andExpect(status().isOk())
			.andExpect(jsonPath("$.learnerId", is("D")))
			.andExpect(jsonPath("$.status", is("DROPPED_FROM_WAITLIST")))
			.andExpect(jsonPath("$.promotedLearnerIds", empty()));

		assertState(List.of("A", "B"), List.of("C", "E"));
		// Leaving twice is a repeated drop.
		expectProblem(drop("D"), 409, "NOT_ENROLLED");
		assertState(List.of("A", "B"), List.of("C", "E"));
	}

	@Test
	@DisplayName("TC-A-12: the state query returns the configured offering and FIFO order, with exactly the documented fields")
	void tcA12_stateQueryShowsOfferingAndFifoOrder() throws Exception {
		registerEligible("L1", "L2", "L3", "L4", "L5");

		// Empty offering: fixed details come from configuration.
		mockMvc.perform(get("/api/offering"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.offeringId", is("CS201-A")))
			.andExpect(jsonPath("$.course.code", is("CS201")))
			.andExpect(jsonPath("$.course.title", is("Data Structures")))
			.andExpect(jsonPath("$.course.prerequisiteCodes", contains("CS101")))
			.andExpect(jsonPath("$.capacity", is(2)))
			.andExpect(jsonPath("$.availableSeats", is(2)))
			.andExpect(jsonPath("$.enrolledLearnerIds", empty()))
			.andExpect(jsonPath("$.waitlistedLearnerIds", empty()))
			// Only the documented fields are exposed: no domain object, lock, or learner detail.
			.andExpect(jsonPath("$.keys()",
					containsInAnyOrder("offeringId", "course", "capacity", "availableSeats", "enrolledLearnerIds",
							"waitlistedLearnerIds")))
			.andExpect(jsonPath("$.course.keys()", containsInAnyOrder("code", "title", "prerequisiteCodes")));

		// Arrival order L3, L1, L5, L2, L4: first two enrolled, the rest wait in that order.
		enroll("L3");
		enroll("L1");
		enroll("L5");
		enroll("L2");
		enroll("L4");
		assertState(List.of("L3", "L1"), List.of("L5", "L2", "L4"));

		// Promotion follows the same order, and a returning learner joins the tail.
		drop("L3").andExpect(jsonPath("$.promotedLearnerIds", contains("L5")));
		enroll("L3").andExpect(jsonPath("$.status", is("WAITLISTED")));
		assertState(List.of("L1", "L5"), List.of("L2", "L4", "L3"));
	}

	@Test
	@DisplayName("TC-A-14: skip-and-retain through the API: ineligible head is skipped, keeps position, and is promoted once eligible again")
	void tcA14_skipAndRetainThroughTheApi() throws Exception {
		registerEligible("A", "B", "W1", "W2", "W3");
		enroll("A");
		enroll("B");
		enroll("W1");
		enroll("W2");
		enroll("W3");
		assertState(List.of("A", "B"), List.of("W1", "W2", "W3"));

		revokeCompletion("W1", PREREQUISITE).andExpect(status().isNoContent());

		// W1 is at the head but ineligible: W2 is promoted and W1 keeps first place.
		drop("A").andExpect(status().isOk()).andExpect(jsonPath("$.promotedLearnerIds", contains("W2")));
		assertState(List.of("B", "W2"), List.of("W1", "W3"));

		// Revoking the prerequisite of an enrolled learner does not remove them.
		revokeCompletion("B", PREREQUISITE).andExpect(status().isNoContent());
		assertState(List.of("B", "W2"), List.of("W1", "W3"));

		// Once eligible again, W1 is promoted at the next drop, ahead of W3.
		markCompleted("W1", PREREQUISITE).andExpect(status().isNoContent());
		assertState(List.of("B", "W2"), List.of("W1", "W3"));
		drop("W2").andExpect(jsonPath("$.promotedLearnerIds", contains("W1")));
		assertState(List.of("B", "W1"), List.of("W3"));
	}

	@Test
	@DisplayName("TC-A-15: waitlist priority through the API: an eligible waiter takes the open seat before a new requester")
	void tcA15_waitlistPriorityThroughTheApi() throws Exception {
		registerEligible("A", "B", "W", "N");
		enroll("A");
		enroll("B");
		enroll("W");
		// W becomes ineligible, so both drops leave seats vacant with W still waiting.
		revokeCompletion("W", PREREQUISITE);
		drop("A").andExpect(jsonPath("$.promotedLearnerIds", empty()));
		assertState(List.of("B"), List.of("W"));
		// Capacity is 2 and only B is enrolled, so exactly one seat is open.
		markCompleted("W", PREREQUISITE);
		// Marking the completion alone does not seat W (limitation L1).
		assertState(List.of("B"), List.of("W"));

		// One open seat, eligible waiter W, new eligible requester N: W is seated first.
		enroll("N").andExpect(status().isOk())
			.andExpect(jsonPath("$.learnerId", is("N")))
			.andExpect(jsonPath("$.status", is("WAITLISTED")))
			.andExpect(jsonPath("$.promotedLearnerIds", contains("W")));

		assertState(List.of("B", "W"), List.of("N"));
	}

	@Test
	@DisplayName("TC-A-18: every error is application/problem+json with status, title, detail and code, and leaks no internals")
	void tcA18_errorsAreProblemDetailsWithoutInternals() throws Exception {
		registerEligible("A");
		registerIneligible("X");
		enroll("A");

		var errors = List.of(
				// Domain rejections.
				expectProblem(enroll("A"), 409, "ALREADY_ENROLLED"),
				expectProblem(enroll("X"), 422, "PREREQUISITES_NOT_MET"),
				expectProblem(drop("X"), 409, "NOT_ENROLLED"),
				expectProblem(enroll("GHOST"), 404, "LEARNER_NOT_FOUND"),
				expectProblem(registerLearner("{\"id\":\"A\",\"name\":\"Again\"}"), 409, "LEARNER_ALREADY_EXISTS"),
				// Validation and framework errors use the same shape.
				expectProblem(enrollWithBody("{}"), 400, "INVALID_REQUEST"),
				expectProblem(enrollWithBody("{oops"), 400, "INVALID_REQUEST"),
				expectProblem(mockMvc.perform(put("/api/offering")), 405, "METHOD_NOT_ALLOWED"),
				expectProblem(mockMvc.perform(get("/api/no-such-resource")), 404, "NOT_FOUND"),
				// There is one offering and no offering id: such a path does not exist.
				expectProblem(mockMvc.perform(get("/api/offerings/CS201-A")), 404, "NOT_FOUND"),
				expectProblem(mockMvc.perform(delete("/api/offerings/CS201-A/enrollments/A")), 404, "NOT_FOUND"));

		for (var error : errors) {
			error.andExpect(jsonPath("$.type").exists())
				.andExpect(jsonPath("$.instance").exists())
				.andExpect(jsonPath("$.trace").doesNotExist())
				.andExpect(jsonPath("$.exception").doesNotExist())
				.andExpect(jsonPath("$.message").doesNotExist())
				.andExpect(jsonPath("$.path").doesNotExist())
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("Exception"))))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("com.assessment"))))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("org.springframework"))))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("static resource"))));
		}

		// An unexpected failure is reported as a 500 problem that reveals nothing about its cause.
		ResponseEntity<ProblemDetail> unexpected = new ApiExceptionHandler()
			.handleUnexpected(new IllegalStateException("internal detail that must not leak"));
		assertThat(unexpected.getStatusCode().value()).isEqualTo(500);
		assertThat(unexpected.getBody().getProperties()).containsEntry("code", "INTERNAL_ERROR");
		assertThat(unexpected.getBody().getDetail()).isEqualTo("An unexpected error occurred.")
			.doesNotContain("internal detail");

		assertState(List.of("A"), List.of());
	}

}
