package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "labacmt", id = "cmtid")
public class LabaCmt {
	@Id
	private long cmtId;

	@Column
	private long labaId;

	@Column
	private long userId;

	@Column
	private String content;

	@Column
	private Date createTime;

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public long getCmtId() {
		return cmtId;
	}

	public void setCmtId(long cmtId) {
		this.cmtId = cmtId;
	}

	public long getLabaId() {
		return labaId;
	}

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int validate() {
		String s = DataUtil.toTextRow(content);
		if (DataUtil.isEmpty(s) || s.length() > 140) {
			return Err.LABACMT_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}
}