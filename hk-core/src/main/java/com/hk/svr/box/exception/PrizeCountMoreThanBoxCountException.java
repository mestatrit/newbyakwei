package com.hk.svr.box.exception;

import com.hk.svr.exception.HkException;

public class PrizeCountMoreThanBoxCountException extends HkException {
	private static final long serialVersionUID = 6981865998501493118L;

	public PrizeCountMoreThanBoxCountException(String message) {
		super(message);
	}
}