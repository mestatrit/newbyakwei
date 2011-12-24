package com.hk.svr.user.exception;

import com.hk.svr.exception.HkException;

public class IllegalImageException extends HkException {
	private static final long serialVersionUID = 5132899670091392742L;

	public IllegalImageException(String message) {
		super(message + " only allow jpeg,png,gif");
	}
}