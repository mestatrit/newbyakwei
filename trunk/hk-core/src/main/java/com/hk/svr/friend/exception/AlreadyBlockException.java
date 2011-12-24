package com.hk.svr.friend.exception;

import com.hk.svr.exception.HkException;

public class AlreadyBlockException extends HkException {
	private static final long serialVersionUID = -7162813986338388635L;

	private long userId;

	private long blockUserId;

	public AlreadyBlockException(long userId, long blockUserId) {
		super("you have bean blocked");
		this.userId = userId;
		this.blockUserId = blockUserId;
	}

	public long getUserId() {
		return userId;
	}

	public long getBlockUserId() {
		return blockUserId;
	}
}