package com.hk.svr.box.exception;

import com.hk.svr.exception.HkException;

public class UserOpenLimitException extends HkException {
	private static final long serialVersionUID = 1879424183928913990L;

	public UserOpenLimitException(String message) {
		super(message);
	}
}