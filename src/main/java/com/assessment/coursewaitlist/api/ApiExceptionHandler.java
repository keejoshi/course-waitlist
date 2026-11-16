package com.assessment.coursewaitlist.api;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.assessment.coursewaitlist.service.LearnerAlreadyExistsException;
import com.assessment.coursewaitlist.service.LearnerNotFoundException;

/**
 * Turns every error into an RFC 9457 problem document
 * ({@code application/problem+json}) with one extra member, {@code code}: a
 * stable, machine-readable reason. Framework errors (malformed JSON, unknown
 * path, wrong method) go through the same handler, so all errors share one
 * shape. No stack trace or exception class name is ever sent to the client.
 */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	static final String CODE = "code";

	static final String INVALID_REQUEST = "INVALID_REQUEST";

	private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@ExceptionHandler(RequestRejectedException.class)
	ResponseEntity<ProblemDetail> handleRejected(RequestRejectedException ex) {
		return problem(ex.status(), ex.code(), ex.getMessage());
	}

	@ExceptionHandler(LearnerNotFoundException.class)
	ResponseEntity<ProblemDetail> handleLearnerNotFound(LearnerNotFoundException ex) {
		return problem(HttpStatus.NOT_FOUND, "LEARNER_NOT_FOUND", ex.getMessage());
	}

	@ExceptionHandler(LearnerAlreadyExistsException.class)
	ResponseEntity<ProblemDetail> handleLearnerAlreadyExists(LearnerAlreadyExistsException ex) {
		return problem(HttpStatus.CONFLICT, "LEARNER_ALREADY_EXISTS", ex.getMessage());
	}

	/** Anything unexpected: logged in full on the server, reported to the client without detail. */
	@ExceptionHandler(Exception.class)
	ResponseEntity<ProblemDetail> handleUnexpected(Exception ex) {
		log.error("Unexpected error while handling a request", ex);
		return problem(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "An unexpected error occurred.");
	}

	/** Invalid request body fields: lists each field and what is wrong with it. */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> errors = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(error -> error.getField() + " " + error.getDefaultMessage())
			.sorted()
			.toList();
		ProblemDetail body = ProblemDetail.forStatusAndDetail(status, "Invalid request: " + String.join("; ", errors));
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	/** Invalid path values: lists what is wrong with each. */
	@Override
	protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> errors = ex.getParameterValidationResults()
			.stream()
			.flatMap(result -> result.getResolvableErrors()
				.stream()
				.map(MessageSourceResolvable::getDefaultMessage)
				.map(message -> result.getMethodParameter().getParameterName() + " " + message))
			.sorted()
			.toList();
		ProblemDetail body = ProblemDetail.forStatusAndDetail(status, "Invalid request: " + String.join("; ", errors));
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	/** Unknown path: a neutral message instead of the framework's "No static resource ..." wording. */
	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		ProblemDetail body = ProblemDetail.forStatusAndDetail(status, "No resource exists at this path.");
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	/** Every framework-raised error passes through here; this adds the {@code code} member. */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		ResponseEntity<Object> response = super.handleExceptionInternal(ex, body, headers, statusCode, request);
		if (response != null && response.getBody() instanceof ProblemDetail problem
				&& (problem.getProperties() == null || !problem.getProperties().containsKey(CODE))) {
			problem.setProperty(CODE, codeFor(statusCode));
		}
		return response;
	}

	private static String codeFor(HttpStatusCode statusCode) {
		HttpStatus status = HttpStatus.resolve(statusCode.value());
		if (status == HttpStatus.BAD_REQUEST) {
			return INVALID_REQUEST;
		}
		return (status == null) ? "ERROR" : status.name().toUpperCase(Locale.ROOT);
	}

	private static ResponseEntity<ProblemDetail> problem(HttpStatus status, String code, String detail) {
		ProblemDetail body = ProblemDetail.forStatusAndDetail(status, detail);
		body.setProperty(CODE, code);
		return ResponseEntity.status(status).body(body);
	}

}
