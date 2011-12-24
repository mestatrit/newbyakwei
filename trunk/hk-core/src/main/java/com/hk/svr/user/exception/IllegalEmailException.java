package com.hk.svr.user.exception;

import com.hk.svr.exception.HkException;

public class IllegalEmailException extends HkException {
	private static final long serialVersionUID = 2275614665632843415L;

	public IllegalEmailException(String message) {
		super(message);
	}
}