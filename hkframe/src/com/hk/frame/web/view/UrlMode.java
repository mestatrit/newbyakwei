package com.hk.frame.web.view;

public class UrlMode {
	private int urlModeId;

	private String name;

	private boolean needGwt;

	public boolean isNeedGwt() {
		return needGwt;
	}

	public void setNeedGwt(boolean needGwt) {
		this.needGwt = needGwt;
	}

	public int getUrlModeId() {
		return urlModeId;
	}

	public void setUrlModeId(int urlModeId) {
		this.urlModeId = urlModeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}