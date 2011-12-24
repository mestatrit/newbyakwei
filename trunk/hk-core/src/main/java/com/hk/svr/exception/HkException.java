package com.hk.svr.exception;

public class HkException extends Exception {
	private static final long serialVersionUID = -1829680543637003496L;

	public HkException() {
		super();
	}

	public HkException(String message, Throwable cause) {
		super(message, cause);
	}

	public HkException(String message) {
		super(message);
	}

	public HkException(Throwable cause) {
		super(cause);
	}
}