package com.toklahBackend.exception;

import javax.ws.rs.core.Response.Status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends CustomException {

	private static final long serialVersionUID = 1L;

	private static final Integer STATUS_CONFLICT = Status.CONFLICT.getStatusCode();

	public ConflictException() {
		super(STATUS_CONFLICT, "Conflict");
	}

	public ConflictException(final String message) {
		super(STATUS_CONFLICT, message);
	}

	public ConflictException(final String message, final Throwable cause) {
		super(STATUS_CONFLICT, message, cause);
	}

}