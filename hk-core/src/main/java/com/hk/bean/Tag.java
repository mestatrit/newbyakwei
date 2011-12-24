package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "tag", id = "tagid")
public class Tag {
	private long tagId;

	private String name;

	private int labaCount;

	private int userCount;

	private long userId;

	private int hot;

	private Date updateTime;

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLabaCount() {
		return labaCount;
	}

	public void setLabaCount(int labaCount) {
		this.labaCount = labaCount;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public static int validateName(String name) {
		String s = DataUtil.toTextRow(name);
		if (DataUtil.isEmpty(s)) {
			return Err.TAG_NAME_ERROR;
		}
		if (s.length() > 15) {
			return Err.TAG_NMAE_LENGTH_TOO_LONG;
		}
		return Err.SUCCESS;
	}
}