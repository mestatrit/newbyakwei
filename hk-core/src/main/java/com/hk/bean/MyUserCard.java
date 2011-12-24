package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "myusercard")
public class MyUserCard {
	private long sysId;

	private long userId;

	private long cardUserId;

	private UserCard userCard;

	public void setUserCard(UserCard userCard) {
		this.userCard = userCard;
	}

	public UserCard getUserCard() {
		return userCard;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public long getSysId() {
		return sysId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCardUserId() {
		return cardUserId;
	}

	public void setCardUserId(long cardUserId) {
		this.cardUserId = cardUserId;
	}
}