package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;

@Table(name = "reflaba")
public class RefLaba {
	@Column
	private long labaId;

	@Column
	private long refUserId;

	@Column
	private Date createTime;

	private User refUser;

	public void setRefUser(User refUser) {
		this.refUser = refUser;
	}

	public User getRefUser() {
		if (refUser == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			refUser = userService.getUser(refUserId);
		}
		return refUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getLabaId() {
		return labaId;
	}

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}

	public long getRefUserId() {
		return refUserId;
	}

	public void setRefUserId(long refUserId) {
		this.refUserId = refUserId;
	}
}