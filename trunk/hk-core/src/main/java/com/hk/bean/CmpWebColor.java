package com.hk.bean;

import java.util.HashMap;
import java.util.Map;

import com.hk.frame.util.DataUtil;

public class CmpWebColor {

	private Map<String, String> map = null;

	private String columnBgColor;

	private String columnLinkColor;

	private String columnLinkHoverBgColor;

	private String column2BgColor;

	private String column2Color;

	private String column2NavLinkColor;

	private String column2NavLinkActiveColor;

	private String homeProductBgColor;

	private String homeTitleLinkColor;

	private String linkColor;

	private String buttonBgColor;

	private String buttonBorderColor;

	private String buttonColor;

	private String userNavBgColor;

	private String userNavLinkColor;

	private String homeModTitleLinkColor;

	private String fontColor;

	public CmpWebColor() {
	}

	public CmpWebColor(String json) {
		if (json != null) {
			map = DataUtil.getMapFromJson(json);
		}
		if (map != null) {
			this.columnBgColor = map.get("column_bg_color");
			this.columnLinkColor = map.get("column_link_color");
			this.columnLinkHoverBgColor = map.get("column_link_hover_bg_color");
			this.homeProductBgColor = map.get("home_product_bg_color");
			this.homeTitleLinkColor = map.get("home_title_link_color");
			this.linkColor = map.get("link_color");
			this.column2BgColor = map.get("column2_bg_color");
			this.column2Color = map.get("column2_color");
			this.buttonBorderColor = map.get("btn_border_color");
			this.buttonColor = map.get("btn_color");
			this.buttonBgColor = map.get("btn_bg_color");
			this.userNavBgColor = map.get("usernav_bg_color");
			this.userNavLinkColor = map.get("usernav_link_color");
			this.homeModTitleLinkColor = map.get("home_mod_title_link_color");
			this.column2NavLinkColor = map.get("column2_nav_link_color");
			this.column2NavLinkActiveColor = map
					.get("column2_nav_link_active_color");
			this.fontColor = map.get("font_color");
		}
	}

	public String toJsonData() {
		map = new HashMap<String, String>();
		if (!DataUtil.isEmpty(this.columnBgColor)) {
			map.put("column_bg_color", this.columnBgColor);
		}
		if (!DataUtil.isEmpty(this.columnLinkColor)) {
			map.put("column_link_color", this.columnLinkColor);
		}
		if (!DataUtil.isEmpty(this.columnLinkHoverBgColor)) {
			map.put("column_link_hover_bg_color", this.columnLinkHoverBgColor);
		}
		if (!DataUtil.isEmpty(this.homeProductBgColor)) {
			map.put("home_product_bg_color", this.homeProductBgColor);
		}
		if (!DataUtil.isEmpty(this.homeTitleLinkColor)) {
			map.put("home_title_link_color", this.homeTitleLinkColor);
		}
		if (!DataUtil.isEmpty(this.linkColor)) {
			map.put("link_color", this.linkColor);
		}
		if (!DataUtil.isEmpty(this.column2Color)) {
			map.put("column2_color", this.column2Color);
		}
		if (!DataUtil.isEmpty(this.column2BgColor)) {
			map.put("column2_bg_color", this.column2BgColor);
		}
		if (!DataUtil.isEmpty(this.buttonBorderColor)) {
			map.put("btn_border_color", this.buttonBorderColor);
		}
		if (!DataUtil.isEmpty(this.buttonColor)) {
			map.put("btn_color", this.buttonColor);
		}
		if (!DataUtil.isEmpty(this.buttonBgColor)) {
			map.put("btn_bg_color", this.buttonBgColor);
		}
		if (!DataUtil.isEmpty(this.userNavBgColor)) {
			map.put("usernav_bg_color", this.userNavBgColor);
		}
		if (!DataUtil.isEmpty(this.userNavLinkColor)) {
			map.put("usernav_link_color", this.userNavLinkColor);
		}
		if (!DataUtil.isEmpty(this.homeModTitleLinkColor)) {
			map.put("home_mod_title_link_color", this.homeModTitleLinkColor);
		}
		if (!DataUtil.isEmpty(this.column2NavLinkColor)) {
			map.put("column2_nav_link_color", this.column2NavLinkColor);
		}
		if (!DataUtil.isEmpty(this.column2NavLinkActiveColor)) {
			map.put("column2_nav_link_active_color",
					this.column2NavLinkActiveColor);
		}
		if (!DataUtil.isEmpty(this.fontColor)) {
			map.put("font_color", this.fontColor);
		}
		return DataUtil.toJson(map);
	}

	public String getColumnBgColor() {
		return columnBgColor;
	}

	public void setColumnBgColor(String columnBgColor) {
		this.columnBgColor = columnBgColor;
	}

	public String getColumnLinkColor() {
		return columnLinkColor;
	}

	public void setColumnLinkColor(String columnLinkColor) {
		this.columnLinkColor = columnLinkColor;
	}

	public String getColumnLinkHoverBgColor() {
		return columnLinkHoverBgColor;
	}

	public void setColumnLinkHoverBgColor(String columnLinkHoverBgColor) {
		this.columnLinkHoverBgColor = columnLinkHoverBgColor;
	}

	public String getHomeProductBgColor() {
		return homeProductBgColor;
	}

	public void setHomeProductBgColor(String homeProductBgColor) {
		this.homeProductBgColor = homeProductBgColor;
	}

	public String getHomeTitleLinkColor() {
		return homeTitleLinkColor;
	}

	public void setHomeTitleLinkColor(String homeTitleLinkColor) {
		this.homeTitleLinkColor = homeTitleLinkColor;
	}

	public String getLinkColor() {
		return linkColor;
	}

	public void setLinkColor(String linkColor) {
		this.linkColor = linkColor;
	}

	public String getButtonColor() {
		return buttonColor;
	}

	public void setButtonColor(String buttonColor) {
		this.buttonColor = buttonColor;
	}

	public String getButtonBorderColor() {
		return buttonBorderColor;
	}

	public void setButtonBorderColor(String buttonBorderColor) {
		this.buttonBorderColor = buttonBorderColor;
	}

	public String getButtonBgColor() {
		return buttonBgColor;
	}

	public void setButtonBgColor(String buttonBgColor) {
		this.buttonBgColor = buttonBgColor;
	}

	public String getColumn2BgColor() {
		return column2BgColor;
	}

	public void setColumn2BgColor(String column2BgColor) {
		this.column2BgColor = column2BgColor;
	}

	public String getColumn2Color() {
		return column2Color;
	}

	public void setColumn2Color(String column2Color) {
		this.column2Color = column2Color;
	}

	public String getUserNavBgColor() {
		return userNavBgColor;
	}

	public void setUserNavBgColor(String userNavBgColor) {
		this.userNavBgColor = userNavBgColor;
	}

	public String getUserNavLinkColor() {
		return userNavLinkColor;
	}

	public void setUserNavLinkColor(String userNavLinkColor) {
		this.userNavLinkColor = userNavLinkColor;
	}

	public String getHomeModTitleLinkColor() {
		return homeModTitleLinkColor;
	}

	public void setHomeModTitleLinkColor(String homeModTitleLinkColor) {
		this.homeModTitleLinkColor = homeModTitleLinkColor;
	}

	public String getColumn2NavLinkColor() {
		return column2NavLinkColor;
	}

	public void setColumn2NavLinkColor(String column2NavLinkColor) {
		this.column2NavLinkColor = column2NavLinkColor;
	}

	public String getColumn2NavLinkActiveColor() {
		return column2NavLinkActiveColor;
	}

	public void setColumn2NavLinkActiveColor(String column2NavLinkActiveColor) {
		this.column2NavLinkActiveColor = column2NavLinkActiveColor;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
}