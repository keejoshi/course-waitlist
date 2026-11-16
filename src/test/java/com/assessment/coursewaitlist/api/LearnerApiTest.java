package com.assessment.coursewaitlist.api;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

/** API tests for the learner endpoints. Every test starts from a fresh application context. */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class LearnerApiTest extends ApiTestSupport {

	@Test
	@DisplayName("TC-A-16: learner registration returns 201; a duplicate id is 409 and leaves the learner intact; invalid input is 400")
	void tcA16_learnerRegistration() throws Exception {
		registerLearner("{\"id\":\"L1\",\"name\":\"Asha\",\"completedCourseCodes\":[\"MATH100\",\"CS101\"]}")
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is("L1")))
			.andExpect(jsonPath("$.name", is("Asha")))
			.andExpect(jsonPath("$.completedCourseCodes", contains("CS101", "MATH100")))
			.andExpect(jsonPath("$.keys()", containsInAnyOrder("id", "name", "completedCourseCodes")));
		// Completions may be omitted.
		registerLearner("{\"id\":\"L2\",\"name\":\"Ben\"}").andExpect(status().isCreated())
			.andExpect(jsonPath("$.completedCourseCodes", empty()));

		// Duplicate id: rejected, and the original learner keeps their completion.
		expectProblem(registerLearner("{\"id\":\"L1\",\"name\":\"Impostor\"}"), 409, "LEARNER_ALREADY_EXISTS")
			.andExpect(jsonPath("$.detail", containsString("L1")));
		enroll("L1").andExpect(status().isOk()).andExpect(jsonPath("$.status", is("ENROLLED")));
		// L2 was registered without the prerequisite.
		expectProblem(enroll("L2"), 422, "PREREQUISITES_NOT_MET");

		// Invalid input.
		expectProblem(mockMvc.perform(post("/api/learners").contentType(MediaType.APPLICATION_JSON)), 400,
				"INVALID_REQUEST");
		expectProblem(registerLearner("{}"), 400, "INVALID_REQUEST")
			.andExpect(jsonPath("$.detail", containsString("id")))
			.andExpect(jsonPath("$.detail", containsString("name")));
		expectProblem(registerLearner("{\"id\":\"L 3\",\"name\":\"Cy\"}"), 400, "INVALID_REQUEST");
		expectProblem(registerLearner("{\"id\":\"L3\",\"name\":\"  \"}"), 400, "INVALID_REQUEST");
		expectProblem(registerLearner("{\"id\":\"L3\",\"name\":\"%s\"}".formatted("n".repeat(101))), 400,
				"INVALID_REQUEST");
		expectProblem(registerLearner("{\"id\":\"L3\",\"name\":\"Cy\",\"completedCourseCodes\":[\"CS 101\"]}"), 400,
				"INVALID_REQUEST");
		expectProblem(registerLearner("{\"id\":\"L3\",\"name\":\"Cy\",\"completedCourseCodes\":[null]}"), 400,
				"INVALID_REQUEST");
		expectProblem(registerLearner("{\"id\":\"L3\",\"name\":\"Cy\",\"completedCourseCodes\":\"CS101\"}"), 400,
				"INVALID_REQUEST");
		// None of the rejected requests registered L3.
		expectProblem(enroll("L3"), 404, "LEARNER_NOT_FOUND");

		assertState(List.of("L1"), List.of());
	}

	@Test
	@DisplayName("TC-A-17: marking and revoking a completion return 204, are repeatable, and change eligibility; unknown learner is 404")
	void tcA17_markAndRevokeCompletion() throws Exception {
		registerIneligible("L1");
		expectProblem(enroll("L1"), 422, "PREREQUISITES_NOT_MET");

		// Mark: now eligible. Repeating has no further effect.
		markCompleted("L1", PREREQUISITE).andExpect(status().isNoContent());
		markCompleted("L1", PREREQUISITE).andExpect(status().isNoContent());
		enroll("L1").andExpect(status().isOk()).andExpect(jsonPath("$.status", is("ENROLLED")));
		drop("L1").andExpect(status().isOk());

		// Revoke: ineligible again. Repeating has no further effect.
		revokeCompletion("L1", PREREQUISITE).andExpect(status().isNoContent());
		revokeCompletion("L1", PREREQUISITE).andExpect(status().isNoContent());
		expectProblem(enroll("L1"), 422, "PREREQUISITES_NOT_MET");
		// A different course does not satisfy the prerequisite.
		markCompleted("L1", "MATH100").andExpect(status().isNoContent());
		expectProblem(enroll("L1"), 422, "PREREQUISITES_NOT_MET");

		// Unknown learner and invalid values.
		expectProblem(markCompleted("GHOST", PREREQUISITE), 404, "LEARNER_NOT_FOUND");
		expectProblem(revokeCompletion("GHOST", PREREQUISITE), 404, "LEARNER_NOT_FOUND");
		expectProblem(markCompleted("L1", "CS 101"), 400, "INVALID_REQUEST")
			.andExpect(jsonPath("$.detail", containsString("courseCode")));
		expectProblem(revokeCompletion("L 1", PREREQUISITE), 400, "INVALID_REQUEST");

		assertState(List.of(), List.of());
	}

	@Test
	@DisplayName("TC-A-21: JSON numbers and booleans are rejected for text fields with 400, and ids with '.', '_' and '-' work in bodies and paths")
	void tcA21_nonTextJsonValuesAreRejectedForTextFields() throws Exception {
		registerEligible("5", "A");

		// A JSON number is not read as the learner id "5", even though learner "5" exists.
		expectProblem(enrollWithBody("{\"learnerId\":5}"), 400, "INVALID_REQUEST");
		expectProblem(enrollWithBody("{\"learnerId\":5.0}"), 400, "INVALID_REQUEST");
		expectProblem(enrollWithBody("{\"learnerId\":true}"), 400, "INVALID_REQUEST");
		expectProblem(enrollWithBody("{\"learnerId\":[\"A\"]}"), 400, "INVALID_REQUEST");
		expectProblem(enrollWithBody("{\"learnerId\":{\"id\":\"A\"}}"), 400, "INVALID_REQUEST");
		assertState(List.of(), List.of());

		// The same holds for every text field of a registration.
		expectProblem(registerLearner("{\"id\":7,\"name\":\"Seven\"}"), 400, "INVALID_REQUEST");
		expectProblem(registerLearner("{\"id\":\"L7\",\"name\":7}"), 400, "INVALID_REQUEST");
		expectProblem(registerLearner("{\"id\":\"L7\",\"name\":false}"), 400, "INVALID_REQUEST");
		expectProblem(registerLearner("{\"id\":\"L7\",\"name\":\"Seven\",\"completedCourseCodes\":[101]}"), 400,
				"INVALID_REQUEST");
		expectProblem(registerLearner("{\"id\":\"L7\",\"name\":\"Seven\",\"completedCourseCodes\":[\"CS101\",true]}"),
				400, "INVALID_REQUEST");
		// None of them registered anything.
		expectProblem(enroll("7"), 404, "LEARNER_NOT_FOUND");
		expectProblem(enroll("L7"), 404, "LEARNER_NOT_FOUND");

		// The string "5" is a valid id and still works.
		enroll("5").andExpect(status().isOk()).andExpect(jsonPath("$.status", is("ENROLLED")));

		// Ids may contain '.', '_' and '-', in request bodies and in paths.
		String dotted = "user.name_1-x";
		registerEligible(dotted);
		revokeCompletion(dotted, "CS101").andExpect(status().isNoContent());
		markCompleted(dotted, "CS101").andExpect(status().isNoContent());
		markCompleted(dotted, "MATH.100_A-1").andExpect(status().isNoContent());
		enroll(dotted).andExpect(status().isOk()).andExpect(jsonPath("$.learnerId", is(dotted)));
		assertState(List.of("5", dotted), List.of());
		drop(dotted).andExpect(status().isOk()).andExpect(jsonPath("$.learnerId", is(dotted)));
		assertState(List.of("5"), List.of());
	}

}
