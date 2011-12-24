package com.hk.svr.box.exception;

import com.hk.svr.exception.HkException;

public class TotalCountException extends HkException {
	private static final long serialVersionUID = -4838303415552553367L;

	public TotalCountException(String message) {
		super(message);
	}
}