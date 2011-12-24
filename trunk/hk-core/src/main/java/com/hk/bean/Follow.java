package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;

@Table(name = "follow")
public class Follow {
	public static final byte FOLLOW_BOOTH = 1;

	public static final byte FOLLOW_SINGLE = 0;

	private long sysId;

	private long userId;

	private long friendId;

	private byte bothFollow;

	private User followUser;

	public boolean isAllFollow() {
		if (this.bothFollow == FOLLOW_BOOTH) {
			return true;
		}
		return false;
	}

	public void setFollowUser(User followUser) {
		this.followUser = followUser;
	}

	public User getFollowUser() {
		if (this.followUser == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			followUser = userService.getUser(friendId);
		}
		return followUser;
	}

	public void setBothFollow(byte bothFollow) {
		this.bothFollow = bothFollow;
	}

	public byte getBothFollow() {
		return bothFollow;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getFriendId() {
		return friendId;
	}

	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public static byte getFOLLOW_BOOTH() {
		return FOLLOW_BOOTH;
	}

	public static byte getFOLLOW_SINGLE() {
		return FOLLOW_SINGLE;
	}
}