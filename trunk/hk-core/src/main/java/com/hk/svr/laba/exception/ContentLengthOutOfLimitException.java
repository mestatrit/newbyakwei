package com.hk.svr.laba.exception;

import com.hk.svr.exception.HkException;

public class ContentLengthOutOfLimitException extends HkException {
	private static final long serialVersionUID = -83230205931738661L;

	public ContentLengthOutOfLimitException(String message) {
		super(message);
	}
}