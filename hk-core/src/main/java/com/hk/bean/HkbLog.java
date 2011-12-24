package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;

@Table(name = "hkblog", id = "logid")
public class HkbLog {
	private long logId;

	private long userId;

	private int hkbtype;// 酷币流动方式(短信吹喇叭...)

	private int addcount;// 酷币流动数量

	private Date createTime;

	private long objId;

	public HkbLog() {
		// TODO Auto-generated constructor stub
	}

	public static HkbLog create(long userId, int hkbtype, long objId,
			int addcount) {
		HkbLog log = new HkbLog();
		log.setUserId(userId);
		log.setHkbtype(hkbtype);
		log.setAddcount(addcount);
		log.setObjId(objId);
		return log;
	}

	public void setObjId(long objId) {
		this.objId = objId;
	}

	public long getObjId() {
		return objId;
	}

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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