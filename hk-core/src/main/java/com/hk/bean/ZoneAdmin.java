package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.ZoneUtil;

@Table(name = "zoneadmin", id = "oid")
public class ZoneAdmin {
	@Id
	private long oid;

	@Column
	private long userId;

	@Column
	private int pcityId;

	private User user;

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

	public int getPcityId() {
		return pcityId;
	}

	public void setPcityId(int pcityId) {
		this.pcityId = pcityId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Pcity getPcity() {
		return ZoneUtil.getPcity(this.pcityId);
	}
}