package com.hk.svr.box.exception;

import com.hk.svr.exception.HkException;

public class BoxStopException extends HkException {
	private static final long serialVersionUID = 7615634559752819160L;

	public BoxStopException(String message) {
		super(message);
	}
}