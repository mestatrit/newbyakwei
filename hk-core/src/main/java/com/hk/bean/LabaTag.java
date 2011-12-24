package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Table;

@Table(name = "labatag")
public class LabaTag {
	public static final byte ACCESSIONAL_N = 0;

	public static final byte ACCESSIONAL_Y = 1;

	@Column
	private long labaId;

	@Column
	private long tagId;

	@Column
	private long userId;// 标签添加人,可以是喇叭发表人和附加标签的添加人

	@Column
	private byte accessional;// 是否是附加标签

	private Tag tag;

	public long getLabaId() {
		return labaId;
	}

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getAccessional() {
		return accessional;
	}

	public void setAccessional(byte accessional) {
		this.accessional = accessional;
	}
}