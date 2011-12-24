package com.hk.svr.laba.exception;

import com.hk.svr.exception.HkException;

public class LabaNotExistException extends HkException {
	private static final long serialVersionUID = -9010708848977285225L;

	public LabaNotExistException(String message) {
		super(message);
	}
}