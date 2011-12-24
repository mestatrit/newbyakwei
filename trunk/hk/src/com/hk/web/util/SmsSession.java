package com.hk.web.util;

import java.util.HashMap;
import java.util.Map;

public class SmsSession {
	private long activeTime;

	private String sessionId;

	private final Map<String, Object> map = new HashMap<String, Object>();

	public void updateActiveTime() {
		this.setActiveTime(System.currentTimeMillis());
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}

	public long getActiveTime() {
		return activeTime;
	}

	public SmsSession(String sessionId) {
		this.sessionId = sessionId;
		this.activeTime = System.currentTimeMillis();
	}

	public String getSessionId() {
		return sessionId;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setAttribute(String key, Object value) {
		this.map.put(key, value);
	}

	public void clearAttribute() {
		this.map.clear();
	}

	public void removeAttribute(String key) {
		this.map.remove(key);
	}

	public Object getAttribute(String key) {
		return this.map.get(key);
	}
}