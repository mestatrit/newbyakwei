package com.hk.svr.user.exception;

import com.hk.svr.exception.HkException;

public class EmailDuplicateException extends HkException {
	private static final long serialVersionUID = 6183952973577901857L;

	private String email;

	public EmailDuplicateException(String message, String email) {
		super(message + "[ " + email + " ]");
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}