package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Table;

/**
 * 用户主页的最近访问
 * 
 * @author akwei
 */
@Table(name = "recentvisitor", id = "oid")
public class RecentVisitor {
	private long oid;

	private long userId;

	private long visitorId;

	private Date uptime;// 最后访问时间

	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
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

	public long getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(long visitorId) {
		this.visitorId = visitorId;
	}
}