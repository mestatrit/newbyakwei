package com.dev3g.cactus.util.httputil;

public class ConnectException extends Exception {

	private static final long serialVersionUID = -8150833483861828231L;

	public ConnectException() {
		super();
	}

	public ConnectException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectException(String message) {
		super(message);
	}

	public ConnectException(Throwable cause) {
		super(cause);
	}
}
