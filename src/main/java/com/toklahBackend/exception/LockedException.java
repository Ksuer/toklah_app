package com.toklahBackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.LOCKED)
public class LockedException extends CustomException {

private static final long serialVersionUID = 1L;
	
	private static final Integer STATUS_LOCKED=  HttpStatus.LOCKED.value();

	public LockedException() {
		super(STATUS_LOCKED, "Locked");
	}
	
	public LockedException(final String message) {
		super(STATUS_LOCKED,message);
	}

	public LockedException(final String message, final Throwable cause) {
		super(STATUS_LOCKED,message, cause);
	}
}
