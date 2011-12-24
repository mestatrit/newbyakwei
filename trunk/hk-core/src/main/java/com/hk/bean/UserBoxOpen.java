package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.BoxService;
import com.hk.svr.UserService;

@Table(name = "userboxopen", id = "sysid")
public class UserBoxOpen {

	@Id
	private long sysId;

	@Column
	private long boxId;

	@Column
	private long userId;

	@Column
	private long prizeId;

	@Column
	private Date createTime;

	public User getUser() {
		UserService userService = (UserService) HkUtil.getBean("userService");
		return userService.getUser(userId);
	}

	public BoxPrize getBoxPrize() {
		BoxService boxService = (BoxService) HkUtil.getBean("boxService");
		return boxService.getBoxPrize(prizeId);
	}

	public long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(long prizeId) {
		this.prizeId = prizeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public long getBoxId() {
		return boxId;
	}

	public void setBoxId(long boxId) {
		this.boxId = boxId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}