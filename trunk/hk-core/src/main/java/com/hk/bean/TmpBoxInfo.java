package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "tmpboxinfo", id = "boxid")
public class TmpBoxInfo {
	private long boxId;

	private String content;

	public long getBoxId() {
		return boxId;
	}

	public void setBoxId(long boxId) {
		this.boxId = boxId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}