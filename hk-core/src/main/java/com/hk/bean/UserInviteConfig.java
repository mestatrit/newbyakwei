package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户邀请码配置
 * 
 * @author akwei
 */
@Table(name = "userinviteconfig")
public class UserInviteConfig {

	@Id
	private long userId;

	/**
	 * 可用的邀请码数量
	 */
	@Column
	private int inviteNum;

	/**
	 * 获取邀请码批的数量
	 */
	@Column
	private int batchNum;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getInviteNum() {
		return inviteNum;
	}

	public void setInviteNum(int inviteNum) {
		this.inviteNum = inviteNum;
	}

	public void addInviteNum(int add) {
		this.inviteNum = this.inviteNum + add;
		if (this.inviteNum < 0) {
			this.inviteNum = 0;
		}
	}

	public int getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(int batchNum) {
		this.batchNum = batchNum;
	}
}