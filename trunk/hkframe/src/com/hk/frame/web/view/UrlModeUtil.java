package com.hk.frame.web.view;

import java.util.List;

public class UrlModeUtil {
	private List<UrlMode> urlModeList;

	private UrlModeUtil() {//
	}

	public void setUrlModeList(List<UrlMode> urlModeList) {
		this.urlModeList = urlModeList;
	}

	public List<UrlMode> getUrlModeList() {
		return urlModeList;
	}

	public UrlMode getUrlMode(int id) {
		for (UrlMode o : urlModeList) {
			if (o.getUrlModeId() == id) {
				return o;
			}
		}
		return null;
	}
}