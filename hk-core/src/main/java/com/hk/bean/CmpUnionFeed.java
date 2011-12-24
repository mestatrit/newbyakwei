package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Table;

/**
 * 动态
 * 
 * @author akwei
 */
@Table(name = "cmpunionfeed", id = "feedid")
public class CmpUnionFeed {
	private long feedId;

	private long uid;

	private long objId;

	private int feedflg;

	private String data;

	private Date createTime;

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getObjId() {
		return objId;
	}

	public void setObjId(long objId) {
		this.objId = objId;
	}

	public int getFeedflg() {
		return feedflg;
	}

	public void setFeedflg(int feedflg) {
		this.feedflg = feedflg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}