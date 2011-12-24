package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;

@Table(name = "followed")
public class Followed {
	private long sysId;

	private long userId;

	private long followingUserId;

	private byte bothFollow;

	private User followingUser;

	public void setFollowingUser(User followingUser) {
		this.followingUser = followingUser;
	}

	public User getFollowingUser() {
		if (followingUser == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			followingUser = userService.getUser(followingUserId);
		}
		return this.followingUser;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getFollowingUserId() {
		return followingUserId;
	}

	public void setFollowingUserId(long followingUserId) {
		this.followingUserId = followingUserId;
	}

	public byte getBothFollow() {
		return bothFollow;
	}

	public void setBothFollow(byte bothFollow) {
		this.bothFollow = bothFollow;
	}

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}
}