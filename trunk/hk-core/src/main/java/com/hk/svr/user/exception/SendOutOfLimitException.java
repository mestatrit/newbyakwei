package com.hk.svr.user.exception;

import com.hk.svr.exception.HkException;

public class SendOutOfLimitException extends HkException {
	private static final long serialVersionUID = 5826297370385470651L;

	public SendOutOfLimitException(String message) {
		super(message);
	}
}