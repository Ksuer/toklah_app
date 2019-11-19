package com.toklahBackend.exception;

import javax.ws.rs.core.Response.Status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends CustomException {

private static final long serialVersionUID = 1L;
	
	private static final Integer STATUS_UNAUTHORIZED = Status.UNAUTHORIZED.getStatusCode();

	public UnAuthorizedException() {
		super(STATUS_UNAUTHORIZED, "Unautorized");
	}
	
	public UnAuthorizedException(final String message) {
		super(STATUS_UNAUTHORIZED,message);
	}

	public UnAuthorizedException(final String message, final Throwable cause) {
		super(STATUS_UNAUTHORIZED,message, cause);
	}
}
