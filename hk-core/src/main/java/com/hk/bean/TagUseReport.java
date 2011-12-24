package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;

@Table(name = "taguseresport", id = "sysid")
public class TagUseReport {
	private long sysId;

	private long tagId;

	private int pnum1;

	private int pnum2;

	private Date createTime;

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public int getPnum1() {
		return pnum1;
	}

	public void setPnum1(int pnum1) {
		this.pnum1 = pnum1;
	}

	public int getPnum2() {
		return pnum2;
	}

	public void setPnum2(int pnum2) {
		this.pnum2 = pnum2;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}