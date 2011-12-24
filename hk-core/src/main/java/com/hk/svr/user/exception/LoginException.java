package com.hk.svr.user.exception;

import com.hk.svr.exception.HkException;

public class LoginException extends HkException {
	private static final long serialVersionUID = 2020159575961101136L;

	public LoginException(String message) {
		super(message);
	}
}