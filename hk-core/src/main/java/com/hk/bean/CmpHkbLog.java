package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;

@Table(name = "cmphkblog", id = "logid")
public class CmpHkbLog {
	private long logId;

	private long userId;

	private long companyId;

	private int hkbtype;

	private int addcount;// 使用数量

	private Date createTime;

	public CmpHkbLog() {
		// TODO Auto-generated constructor stub
	}

	public static CmpHkbLog create(long companyId, int hkbtype, long userId,
			int addcount) {
		CmpHkbLog log = new CmpHkbLog();
		log.setCompanyId(companyId);
		log.setHkbtype(hkbtype);
		log.setAddcount(addcount);
		log.setUserId(userId);
		return log;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public int getHkbtype() {
		return hkbtype;
	}

	public void setHkbtype(int hkbtype) {
		this.hkbtype = hkbtype;
	}

	public int getAddcount() {
		return addcount;
	}

	public void setAddcount(int addcount) {
		this.addcount = addcount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}