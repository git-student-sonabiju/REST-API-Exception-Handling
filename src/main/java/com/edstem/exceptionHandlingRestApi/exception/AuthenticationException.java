package com.edstem.exceptionHandlingRestApi.exception;

public class AuthenticationException extends RuntimeException {
	public AuthenticationException(String message) {
		super(message);
	}
}
