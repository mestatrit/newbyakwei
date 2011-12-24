package com.hk.svr.box.exception;

import com.hk.svr.exception.HkException;

public class TimeException extends HkException {
	private static final long serialVersionUID = -286424241786166283L;

	public TimeException(String message) {
		super(message);
	}
}