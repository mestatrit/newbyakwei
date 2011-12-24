package com.hk.svr.user.exception;

import com.hk.svr.exception.HkException;

public class MobileBindException extends HkException {
	private static final long serialVersionUID = 8195685157935461400L;

	public MobileBindException(String message) {
		super(message);
	}
}