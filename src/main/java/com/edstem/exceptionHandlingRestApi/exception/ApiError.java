package com.edstem.exceptionHandlingRestApi.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ApiError {
	private HttpStatus status;
	private String message;
	private LocalDateTime timestamp;
	private List<String> errors;

	public ApiError(HttpStatus status, String message, List<String> errors) {
		this.status = status;
		this.message = message;
		this.errors = errors;
		this.timestamp = LocalDateTime.now();
	}

	public ApiError(HttpStatus status, String message, String error) {
		this(status, message, List.of(error));
	}

}
