package com.hk.svr.laba.exception;

import com.hk.svr.exception.HkException;

public class ContentEmptyException extends HkException {
	private static final long serialVersionUID = -1310654934645059063L;

	public ContentEmptyException(String message) {
		super(message);
	}
}