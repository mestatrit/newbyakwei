package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

/**
 * 关键词按月统计数据对象
 * 
 * @author akwei
 */
@Table(name = "keytagsearchinfo", id = "oid")
public class KeyTagSearchInfo {
	private long oid;

	private long tagId;

	private int year;

	private int month;

	private int searchCount;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
}