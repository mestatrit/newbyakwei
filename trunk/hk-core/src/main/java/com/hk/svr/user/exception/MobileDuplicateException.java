package com.hk.svr.user.exception;

import com.hk.svr.exception.HkException;

public class MobileDuplicateException extends HkException {
	private static final long serialVersionUID = -2433163106348390738L;

	private String mobile;

	public MobileDuplicateException(String message, String mobile) {
		super(message);
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}
}