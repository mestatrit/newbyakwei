package com.hk.web.util;

public class HkStatus {
	private String version;

	private long userId;

	private String input;

	private int showModeId;

	private int cssColorId;

	private int urlModeId;

	public int getUrlModeId() {
		return urlModeId;
	}

	public void setUrlModeId(int urlModeId) {
		this.urlModeId = urlModeId;
	}

	public int getShowModeId() {
		return showModeId;
	}

	public void setShowModeId(int showModeId) {
		this.showModeId = showModeId;
	}

	public int getCssColorId() {
		return cssColorId;
	}

	public void setCssColorId(int cssColorId) {
		this.cssColorId = cssColorId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	@Override
	public String toString() {
		return this.version + ";" + this.userId + ";" + this.input + ";"
				+ this.showModeId + ";" + this.cssColorId + ";"
				+ this.urlModeId;
	}
}