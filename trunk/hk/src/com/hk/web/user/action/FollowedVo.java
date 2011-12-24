package com.hk.web.user.action;

import com.hk.bean.Followed;
import com.hk.bean.Laba;

public class FollowedVo {
	private Followed followed;

	private boolean hasFollowed;

	private Laba laba;

	public void setLaba(Laba laba) {
		this.laba = laba;
	}

	public Laba getLaba() {
		return laba;
	}

	public Followed getFollowed() {
		return followed;
	}

	public void setFollowed(Followed followed) {
		this.followed = followed;
	}

	public boolean isHasFollowed() {
		return hasFollowed;
	}

	public void setHasFollowed(boolean hasFollowed) {
		this.hasFollowed = hasFollowed;
	}
}