package com.hk.frame.web.view;

import java.util.List;

public class CssColorUtil {
	private List<CssColor> cssColorList;

	private CssColorUtil() {//
	}

	public void setCssColorList(List<CssColor> cssColorList) {
		this.cssColorList = cssColorList;
	}

	public List<CssColor> getCssColorList() {
		return cssColorList;
	}

	public CssColor getCssColor(int id) {
		for (CssColor o : cssColorList) {
			if (o.getCssColorId() == id) {
				return o;
			}
		}
		return null;
	}
}