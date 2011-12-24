package com.hk.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;

@Table(name = "notice", id = "noticeid")
public class Notice {

	public static final byte NOTICETYPE_CREATEMSG = 1;

	public static final byte NOTICETYPE_LABAREPLY = 2;

	public static final byte NOTICETYPE_FOLLOW = 3;

	public static final byte NOTICETYPE_USER_IN_LABA = 4;

	public static final byte NOTICETYPE_INVITE = 5;

	public static final byte NOTICETYPE_CHANGEMAYOR = 6;

	public static final byte READFLG_N = 0;

	public static final byte READFLG_Y = 1;

	public static List<Byte> getNoticeTypeList() {
		List<Byte> list = new ArrayList<Byte>();
		list.add(NOTICETYPE_LABAREPLY);
		list.add(NOTICETYPE_USER_IN_LABA);
		list.add(NOTICETYPE_FOLLOW);
		list.add(NOTICETYPE_INVITE);
		return list;
	}

	@Id
	private long noticeId;

	@Column
	private byte noticeType;

	@Column
	private long userId;

	@Column
	private String data;

	@Column
	private byte readflg;

	@Column
	private Date createTime;

	private String labaContent;

	private String labaCmtContent;

	private User sender;

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getSender() {
		return sender;
	}

	public long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(long noticeId) {
		this.noticeId = noticeId;
	}

	public byte getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(byte noticeType) {
		this.noticeType = noticeType;
	}

	public static byte getOBJTYPE_LABAREPLY() {
		return NOTICETYPE_LABAREPLY;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte getOBJTYPE_CREATEMSG() {
		return NOTICETYPE_CREATEMSG;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public byte getReadflg() {
		return readflg;
	}

	public void setReadflg(byte readflg) {
		this.readflg = readflg;
	}

	public boolean isNoRead() {
		if (this.readflg == READFLG_N) {
			return true;
		}
		return false;
	}

	public long getLabaId() {
		Map<String, String> map = DataUtil.getMapFromJson(this.data);
		String d = map.get("labaid");
		if (d == null) {
			return 0;
		}
		return Long.valueOf(d);
	}

	public String getLabaContent() {
		return labaContent;
	}

	public void setLabaContent(String labaContent) {
		this.labaContent = labaContent;
	}

	public String getLabaCmtContent() {
		return labaCmtContent;
	}

	public void setLabaCmtContent(String labaCmtContent) {
		this.labaCmtContent = labaCmtContent;
	}
}