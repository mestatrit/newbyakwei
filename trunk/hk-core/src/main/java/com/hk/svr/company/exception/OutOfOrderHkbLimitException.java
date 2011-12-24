package com.hk.svr.company.exception;

import com.hk.svr.exception.HkException;

public class OutOfOrderHkbLimitException extends HkException {
	private static final long serialVersionUID = 384580825516340069L;

	public OutOfOrderHkbLimitException(String message) {
		super(message);
	}
}