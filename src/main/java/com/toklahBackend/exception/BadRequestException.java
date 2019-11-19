package com.toklahBackend.exception;

import javax.ws.rs.core.Response.Status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends CustomException {

	private static final long serialVersionUID = 1L;
	private static final Integer BAD_REQUEST= Status.BAD_REQUEST.getStatusCode();

	public BadRequestException() {
      super(BAD_REQUEST, "Bad Request");
	}

	public BadRequestException(final String message) {
		super(BAD_REQUEST, message);
	}
}