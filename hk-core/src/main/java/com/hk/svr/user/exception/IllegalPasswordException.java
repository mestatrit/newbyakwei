package com.hk.svr.user.exception;

import com.hk.svr.exception.HkException;

public class IllegalPasswordException extends HkException {
	private static final long serialVersionUID = -7288617606625617846L;

	private String password;

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public IllegalPasswordException(String message, String password) {
		super(message);
		this.password = password;
	}
}