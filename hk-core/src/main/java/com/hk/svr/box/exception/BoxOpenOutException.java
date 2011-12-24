package com.hk.svr.box.exception;

import com.hk.svr.exception.HkException;

public class BoxOpenOutException extends HkException {
	private static final long serialVersionUID = -725498509911701752L;

	public BoxOpenOutException(String message) {
		super(message);
	}
}