package com.hk.svr.box.exception;

import com.hk.svr.exception.HkException;

public class BoxTimOutException extends HkException {
	private static final long serialVersionUID = -2981530867550598059L;

	public BoxTimOutException(String message) {
		super(message);
	}
}