package com.hk.svr.bomb.exception;

import com.hk.svr.exception.HkException;

public class NotEnoughPinkException extends HkException {
	private static final long serialVersionUID = -3642638104270759404L;

	public NotEnoughPinkException(String message) {
		super(message);
	}
}