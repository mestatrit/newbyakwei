package com.hk.bean;

import java.util.HashMap;
import java.util.Map;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.JsonObj;

public class CmpOrgStyle {

	public CmpOrgStyle() {
	}

	public CmpOrgStyle(Map<String, String> map) {
		JsonObj jsonObj = new JsonObj(map);
		this.bgColor = jsonObj.getString("bgcolor");
		this.linkColor = jsonObj.getString("linkcolor");
		this.linkHoverColor = jsonObj.getString("linkhovercolor");
		this.navLinkHoverBgColor = jsonObj.getString("navlinkhoverbgcolor");
		this.titleColor = jsonObj.getString("titlecolor");
	}

	public String toJsonValue() {
		Map<String, String> map = new HashMap<String, String>();
		if (this.titleColor != null) {
			map.put("titlecolor", this.titleColor);
		}
		if (this.bgColor != null) {
			map.put("bgcolor", this.bgColor);
		}
		if (this.linkColor != null) {
			map.put("linkcolor", this.linkColor);
		}
		if (this.linkHoverColor != null) {
			map.put("linkhovercolor", this.linkHoverColor);
		}
		if (this.navLinkHoverBgColor != null) {
			map.put("navlinkhoverbgcolor", this.navLinkHoverBgColor);
		}
		return DataUtil.toJson(map);
	}

	/**
	 * 标题文字颜色
	 */
	private String titleColor;

	/**
	 * 背景色
	 */
	private String bgColor;

	/**
	 * 链接颜色
	 */
	private String linkColor;

	/**
	 * 链接hover色
	 */
	private String linkHoverColor;

	/**
	 * 导航链接hover背景色
	 */
	private String navLinkHoverBgColor;

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getLinkColor() {
		return linkColor;
	}

	public void setLinkColor(String linkColor) {
		this.linkColor = linkColor;
	}

	public String getLinkHoverColor() {
		return linkHoverColor;
	}

	public void setLinkHoverColor(String linkHoverColor) {
		this.linkHoverColor = linkHoverColor;
	}

	public void setNavLinkHoverBgColor(String navLinkHoverBgColor) {
		this.navLinkHoverBgColor = navLinkHoverBgColor;
	}

	public String getNavLinkHoverBgColor() {
		return navLinkHoverBgColor;
	}

	public String getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}
}