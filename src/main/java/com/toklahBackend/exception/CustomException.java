package com.toklahBackend.exception;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final Integer status;

	public CustomException(final Integer status, final String message) {
		super(message);
		this.status = status;
	}

	public CustomException(final Integer status, final String message, final Throwable cause) {
		super(message, cause);
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}
}

