package com.hk.svr.user.exception;

import com.hk.svr.exception.HkException;

public class MsnDuplicateException extends HkException {
	private static final long serialVersionUID = 6872340943013225474L;

	public MsnDuplicateException(String message) {
		super(message);
	}
}