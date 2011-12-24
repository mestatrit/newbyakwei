package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

/**
 * 存储足迹分类的足迹数量
 * 
 * @author akwei
 */
@Table(name = "cmpkinddata", id = "kindid")
public class CmpKindData {
	private int kindId;

	private int cmpCount;

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}

	public int getCmpCount() {
		return cmpCount;
	}

	public void setCmpCount(int cmpCount) {
		this.cmpCount = cmpCount;
	}
}