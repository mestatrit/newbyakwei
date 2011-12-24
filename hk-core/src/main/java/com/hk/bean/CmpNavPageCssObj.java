package com.hk.bean;

import java.util.HashMap;
import java.util.Map;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.JsonObj;
import com.hk.frame.util.JsonUtil;

public class CmpNavPageCssObj {

	private String navLinkColor;

	private String linkColor;

	private String bgColor;

	/**
	 * 把json值转化为具体数据
	 * 
	 * @param cssData
	 */
	public CmpNavPageCssObj(String cssData) {
		if (!DataUtil.isEmpty(cssData)) {
			JsonObj jsonObj = JsonUtil.getJsonObj(cssData);
			this.setNavLinkColor(jsonObj.getString("navlinkcolor"));
			this.setLinkColor(jsonObj.getString("linkcolor"));
			this.setBgColor(jsonObj.getString("bgcolor"));
		}
	}

	public String getNavLinkColor() {
		return navLinkColor;
	}

	public void setNavLinkColor(String navLinkColor) {
		this.navLinkColor = navLinkColor;
	}

	public String getLinkColor() {
		return linkColor;
	}

	public void setLinkColor(String linkColor) {
		this.linkColor = linkColor;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String toJsonValue() {
		Map<String, String> map = new HashMap<String, String>();
		if (!DataUtil.isEmpty(this.navLinkColor)) {
			map.put("navlinkcolor", this.navLinkColor);
		}
		if (!DataUtil.isEmpty(this.linkColor)) {
			map.put("linkcolor", this.linkColor);
		}
		if (!DataUtil.isEmpty(this.bgColor)) {
			map.put("bgcolor", this.bgColor);
		}
		return JsonUtil.toJson(map);
	}
}