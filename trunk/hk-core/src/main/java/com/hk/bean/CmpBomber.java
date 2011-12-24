package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 企业设定的炸弹人（可进行互动内容管理，例如论坛数据）
 * 
 * @author akwei
 */
@Table(name = "cmpbomber")
public class CmpBomber {

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private long userId;

	@Column
	private int bombcount;

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getBombcount() {
		return bombcount;
	}

	public void setBombcount(int bombcount) {
		this.bombcount = bombcount;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}