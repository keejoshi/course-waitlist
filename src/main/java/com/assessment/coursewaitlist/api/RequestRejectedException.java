package com.assessment.coursewaitlist.api;

import org.springframework.http.HttpStatus;

/**
 * A well-formed request that the offering's rules do not allow, such as a
 * duplicate enrollment. Carries the HTTP status and the stable error code
 * reported to the client.
 */
class RequestRejectedException extends RuntimeException {

	private final HttpStatus status;

	private final String code;

	RequestRejectedException(HttpStatus status, String code, String detail) {
		super(detail);
		this.status = status;
		this.code = code;
	}

	HttpStatus status() {
		return status;
	}

	String code() {
		return code;
	}

}
