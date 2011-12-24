package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;

@Table(name = "chgcardactuser")
public class ChgCardActUser {
	private long actId;

	private long userId;

	private long sysId;

	private User user;

	private UserCard userCard;

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public long getSysId() {
		return sysId;
	}

	public void setUserCard(UserCard userCard) {
		this.userCard = userCard;
	}

	public UserCard getUserCard() {
		return userCard;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		if (user == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			user = userService.getUser(userId);
		}
		return user;
	}

	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}