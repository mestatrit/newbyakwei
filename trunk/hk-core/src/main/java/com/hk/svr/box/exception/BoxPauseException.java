package com.hk.svr.box.exception;

import com.hk.svr.exception.HkException;

public class BoxPauseException extends HkException {
	private static final long serialVersionUID = 6309366544900012692L;

	public BoxPauseException(String message) {
		super(message);
	}
}