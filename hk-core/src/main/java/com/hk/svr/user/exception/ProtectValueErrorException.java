package com.hk.svr.user.exception;

import com.hk.svr.exception.HkException;

public class ProtectValueErrorException extends HkException {
	private static final long serialVersionUID = 4845285182566459370L;

	public ProtectValueErrorException(String message) {
		super(message);
	}
}