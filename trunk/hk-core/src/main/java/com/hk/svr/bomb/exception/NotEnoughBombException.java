package com.hk.svr.bomb.exception;

import com.hk.svr.exception.HkException;

public class NotEnoughBombException extends HkException {
	private static final long serialVersionUID = -2784117567320322787L;

	public NotEnoughBombException(String message) {
		super(message);
	}
}