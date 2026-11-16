package com.assessment.coursewaitlist.api;

import static org.hamcrest.Matchers.contains;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

/**
 * Shared helpers for the API tests. The default configuration is used: one
 * offering, CS201-A, prerequisite CS101, capacity 2.
 */
abstract class ApiTestSupport {

	static final String PREREQUISITE = "CS101";

	static final int CAPACITY = 2;

	@Autowired
	MockMvc mockMvc;

	ResultActions registerLearner(String json) throws Exception {
		return mockMvc.perform(post("/api/learners").contentType(MediaType.APPLICATION_JSON).content(json));
	}

	void registerEligible(String... ids) throws Exception {
		for (String id : ids) {
			registerLearner("{\"id\":\"%s\",\"name\":\"Learner %s\",\"completedCourseCodes\":[\"%s\"]}".formatted(id, id,
					PREREQUISITE))
				.andExpect(status().isCreated());
		}
	}

	void registerIneligible(String id) throws Exception {
		registerLearner("{\"id\":\"%s\",\"name\":\"Learner %s\"}".formatted(id, id)).andExpect(status().isCreated());
	}

	ResultActions enroll(String learnerId) throws Exception {
		return enrollWithBody("{\"learnerId\":\"%s\"}".formatted(learnerId));
	}

	ResultActions enrollWithBody(String json) throws Exception {
		return mockMvc
			.perform(post("/api/offering/enrollments").contentType(MediaType.APPLICATION_JSON).content(json));
	}

	ResultActions drop(String learnerId) throws Exception {
		return mockMvc.perform(delete("/api/offering/enrollments/{learnerId}", learnerId));
	}

	ResultActions markCompleted(String learnerId, String courseCode) throws Exception {
		return mockMvc.perform(put("/api/learners/{learnerId}/completed-courses/{courseCode}", learnerId, courseCode));
	}

	ResultActions revokeCompletion(String learnerId, String courseCode) throws Exception {
		return mockMvc
			.perform(delete("/api/learners/{learnerId}/completed-courses/{courseCode}", learnerId, courseCode));
	}

	/** Asserts the exact enrolled list, waitlist order, and free-seat count reported by the API. */
	void assertState(List<String> enrolled, List<String> waitlisted) throws Exception {
		mockMvc.perform(get("/api/offering"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.enrolledLearnerIds", enrolled.isEmpty() ? empty() : contains(enrolled.toArray())))
			.andExpect(
					jsonPath("$.waitlistedLearnerIds", waitlisted.isEmpty() ? empty() : contains(waitlisted.toArray())))
			.andExpect(jsonPath("$.availableSeats", is(CAPACITY - enrolled.size())))
			.andExpect(jsonPath("$.capacity", is(CAPACITY)));
	}

	/** Asserts an RFC 9457 problem response with the given status and code. */
	static ResultActions expectProblem(ResultActions result, int httpStatus, String code) throws Exception {
		return result.andExpect(status().is(httpStatus))
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
			.andExpect(jsonPath("$.status", is(httpStatus)))
			.andExpect(jsonPath("$.code", is(code)))
			.andExpect(jsonPath("$.title").isNotEmpty())
			.andExpect(jsonPath("$.detail").isNotEmpty());
	}

}
