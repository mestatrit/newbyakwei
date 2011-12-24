package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;

@Table(name = "bomber", id = "userid")
public class Bomber {
	public static final byte USERLEVEL_NORMAL = 0;

	public static final byte USERLEVEL_ADMIN = 1;

	public static final byte USERLEVEL_SUPERADMIN = 2;

	private long userId;

	private int bombCount;

	private int remainCount;

	private Date createTime;

	private byte userLevel;

	private int pinkCount;

	private int remainPinkCount;

	private User user;

	public User getUser() {
		if (user == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			user = userService.getUser(userId);
		}
		return user;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getBombCount() {
		return bombCount;
	}

	public void setBombCount(int bombCount) {
		this.bombCount = bombCount;
	}

	public int getRemainCount() {
		return remainCount;
	}

	public void setRemainCount(int remainCount) {
		this.remainCount = remainCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(byte userLevel) {
		this.userLevel = userLevel;
	}

	public int getPinkCount() {
		return pinkCount;
	}

	public void setPinkCount(int pinkCount) {
		this.pinkCount = pinkCount;
	}

	public int getRemainPinkCount() {
		return remainPinkCount;
	}

	public void setRemainPinkCount(int remainPinkCount) {
		this.remainPinkCount = remainPinkCount;
	}
}