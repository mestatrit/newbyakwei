package com.hk.listener.user;

import java.util.EventObject;

public class FollowEvent extends EventObject {
	private static final long serialVersionUID = 4178713408077701822L;

	private long friendId;

	private long userId;

	private boolean resutlt;

	private String ip;

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public FollowEvent(Long userId) {
		super(userId);
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public long getFriendId() {
		return friendId;
	}

	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}

	public boolean isResutlt() {
		return resutlt;
	}

	public void setResutlt(boolean resutlt) {
		this.resutlt = resutlt;
	}
}