package com.hk.web.user.action;

import com.hk.bean.Follow;
import com.hk.bean.Laba;

public class FollowVo {
	private boolean hasFollowed;

	private Follow follow;

	private Laba laba;

	public boolean isHasFollowed() {
		return hasFollowed;
	}

	public void setHasFollowed(boolean hasFollowed) {
		this.hasFollowed = hasFollowed;
	}

	public Follow getFollow() {
		return follow;
	}

	public void setFollow(Follow follow) {
		this.follow = follow;
	}

	public Laba getLaba() {
		return laba;
	}

	public void setLaba(Laba laba) {
		this.laba = laba;
	}
}