package com.hk.svr.act.exception;

import com.hk.svr.exception.HkException;

public class DuplicateActNameException extends HkException {
	private static final long serialVersionUID = 5135902735837953056L;

	public DuplicateActNameException(String message) {
		super(message);
	}
}