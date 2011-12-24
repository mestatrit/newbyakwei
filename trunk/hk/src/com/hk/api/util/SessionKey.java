package com.hk.api.util;

public class SessionKey {
	private String key;

	private long userId;

	private long activeTime;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}

	public void active() {
		this.setActiveTime(System.currentTimeMillis());
	}
}