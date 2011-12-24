package com.hk.web.act.action;

import com.hk.bean.ActUser;

public class SelUserVo {
	private ActUser actUser;

	private boolean selected;

	public ActUser getActUser() {
		return actUser;
	}

	public void setActUser(ActUser actUser) {
		this.actUser = actUser;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}