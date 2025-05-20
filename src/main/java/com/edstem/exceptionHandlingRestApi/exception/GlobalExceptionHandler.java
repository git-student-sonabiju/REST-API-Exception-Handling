package com.edstem.exceptionHandlingRestApi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
		logger.warn("Resource not found: {}", ex.getMessage());
		ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), "Resource Not Found");
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors()
				.stream().map(err -> err.getField() + ": " + err.getDefaultMessage()).toList();
		logger.info("Validation failed: {}", errors);
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Validation failed", errors);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex) {
		logger.error("Data integrity violation: {}", ex.getMessage());
		ApiError error = new ApiError(HttpStatus.CONFLICT, "Database error", ex.getRootCause().getMessage());
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ApiError> handleAuthentication(AuthenticationException ex) {
		logger.warn("Authentication/authorization failure: {}", ex.getMessage());
		ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage(), "Authentication failed");
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeneral(Exception ex) {
		logger.error("Internal server error: ", ex);
		ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
