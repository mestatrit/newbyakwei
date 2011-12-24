package com.hk.svr.box.exception;

import com.hk.svr.exception.HkException;

public class BoxKeyDuplicateException extends HkException {
	private static final long serialVersionUID = 8919484259777908225L;

	public BoxKeyDuplicateException(String message) {
		super(message);
	}
}