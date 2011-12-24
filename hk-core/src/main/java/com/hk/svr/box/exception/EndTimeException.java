package com.hk.svr.box.exception;

import com.hk.svr.exception.HkException;

public class EndTimeException extends HkException {
	private static final long serialVersionUID = 5945421167726083515L;

	public EndTimeException(String message) {
		super(message);
	}
}