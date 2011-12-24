package com.hk.svr.user.exception;

import com.hk.svr.exception.HkException;

public class IllegalMobileException extends HkException {
	private static final long serialVersionUID = -4476546657914878080L;

	private String mobile;

	public IllegalMobileException(String message, String mobile) {
		super(message);
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}
}