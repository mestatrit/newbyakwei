package com.hk.svr.box.exception;

import com.hk.svr.exception.HkException;

public class BoxIntroOutOfLengthException extends HkException {
	private static final long serialVersionUID = 4338346625528866958L;

	public BoxIntroOutOfLengthException(String message) {
		super(message);
	}
}