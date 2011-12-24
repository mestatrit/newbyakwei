package com.hk.svr.user.exception;

import com.hk.svr.exception.HkException;

public class UserNotExistException extends HkException {
	private static final long serialVersionUID = -7925844702460032480L;

	private long userId;

	public long getUserId() {
		return userId;
	}

	public UserNotExistException(String message, long userId) {
		super(message + " [ " + userId + " ]");
	}

	public UserNotExistException(String message) {
		super(message);
	}
}