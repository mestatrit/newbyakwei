package com.hk.svr.box.exception;

import com.hk.svr.exception.HkException;

public class PrizeCountException extends HkException {
	private static final long serialVersionUID = -7960490724262776959L;

	public PrizeCountException(String message) {
		super(message);
	}
}