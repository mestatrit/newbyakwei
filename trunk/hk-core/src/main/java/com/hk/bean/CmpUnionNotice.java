package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Table;

/**
 * 通知
 * 
 * @author akwei
 */
@Table(name = "cmpunionnotice", id = "noticeid")
public class CmpUnionNotice {
	public static final byte READFLG_N = 0;

	public static final byte READFLG_Y = 0;

	private long noticeId;

	private long uid;

	private int noticeflg;

	private long objId;

	private String data;

	private byte readflg;

	private Date createTime;

	public long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(long noticeId) {
		this.noticeId = noticeId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getNoticeflg() {
		return noticeflg;
	}

	public void setNoticeflg(int noticeflg) {
		this.noticeflg = noticeflg;
	}

	public long getObjId() {
		return objId;
	}

	public void setObjId(long objId) {
		this.objId = objId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte getReadflg() {
		return readflg;
	}

	public void setReadflg(byte readflg) {
		this.readflg = readflg;
	}
}