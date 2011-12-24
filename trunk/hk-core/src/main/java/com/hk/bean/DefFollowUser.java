package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;

@Table(name = "deffollowuser", id = "userid")
public class DefFollowUser {
	private long userId;

	private Date createTime;

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		if (this.user == null) {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}