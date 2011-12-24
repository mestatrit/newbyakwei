package com.hk.svr.company.exception;

import com.hk.svr.exception.HkException;

public class NoAvailableCmpSmsPortException extends HkException {
	private static final long serialVersionUID = -424148819027126924L;

	public NoAvailableCmpSmsPortException(String message) {
		super(message);
	}
}