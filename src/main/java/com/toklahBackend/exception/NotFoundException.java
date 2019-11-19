package com.toklahBackend.exception;

import javax.ws.rs.core.Response.Status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends CustomException {

	private static final long serialVersionUID = 1L;

	private static final Integer STATUS_NOT_FOUND = Status.NOT_FOUND.getStatusCode();

	public NotFoundException() {
		super(STATUS_NOT_FOUND, "user not found");
	}

	public NotFoundException(final String message) {
		super(STATUS_NOT_FOUND, message);
	}

	public NotFoundException(final String message, final Throwable cause) {
		super(STATUS_NOT_FOUND, message, cause);
	}
}

