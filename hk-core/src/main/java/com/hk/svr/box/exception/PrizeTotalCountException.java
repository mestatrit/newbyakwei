package com.hk.svr.box.exception;

import com.hk.svr.exception.HkException;

public class PrizeTotalCountException extends HkException {
	private static final long serialVersionUID = 2173543854587269607L;

	public PrizeTotalCountException(String message) {
		super(message);
	}
}