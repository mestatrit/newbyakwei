package com.hk.svr.box.exception;

import com.hk.svr.exception.HkException;

public class BoxNotExistException extends HkException {
	private static final long serialVersionUID = -5359791747635592002L;

	public BoxNotExistException(String message) {
		super(message);
	}
}