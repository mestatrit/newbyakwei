package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 动态其他数据，主要是为了某些特殊更新及删除操作来准备的，例如更新了足迹名称，删除了tips等
 * 
 * @author akwei
 */
@Table(name = "feedinfo")
public class FeedInfo {
	@Id
	private long feedId;

	@Column
	private byte feedType;

	@Column
	private long objId;

	@Column
	private long obj2Id;

	@Column
	private Date createTime;

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}

	public byte getFeedType() {
		return feedType;
	}

	public void setFeedType(byte feedType) {
		this.feedType = feedType;
	}

	public long getObjId() {
		return objId;
	}

	public void setObjId(long objId) {
		this.objId = objId;
	}

	public long getObj2Id() {
		return obj2Id;
	}

	public void setObj2Id(long obj2Id) {
		this.obj2Id = obj2Id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}