package com.hk.svr.user.exception;

import com.hk.svr.exception.HkException;

public class NoSmsPortException extends HkException {
	private static final long serialVersionUID = -3323163564844037578L;

	public NoSmsPortException(String message) {
		super(message);
	}
}