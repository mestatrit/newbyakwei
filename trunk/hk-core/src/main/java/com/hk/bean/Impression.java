package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "impression", id = "oid")
public class Impression {
	private long oid;

	private long senderId;

	private String content;

	private Date createTime;

	private long prouserId;

	private User sender;

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public long getProuserId() {
		return prouserId;
	}

	public void setProuserId(long prouserId) {
		this.prouserId = prouserId;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
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

	public static int validateContent(String content) {
		String s = DataUtil.toTextRow(content);
		if (DataUtil.isEmpty(s)) {
			return Err.IMPRESSION_CONTENT_ERROR;
		}
		if (s.toLowerCase().indexOf("http://") != -1
				|| s.toLowerCase().indexOf("https://") != -1) {// 如果有url，则去除url计算字数
			String cont = DataUtil.replaceAllUrl(s, "");
			if (cont.length() > 140) {
				return Err.IMPRESSION_CONTENT_ERROR;
			}
		}
		else {
			if (s.length() > 140) {
				return Err.IMPRESSION_CONTENT_ERROR;
			}
		}
		return Err.SUCCESS;
	}
}