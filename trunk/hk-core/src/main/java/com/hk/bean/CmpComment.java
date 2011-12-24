package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmpcomment", id = "cmtid")
public class CmpComment {
	private long cmtId;

	private long companyId;

	private long userId;

	private String content;

	private Date createTime;

	private int sendfrom;

	private String ip;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getCmtId() {
		return cmtId;
	}

	public void setCmtId(long cmtId) {
		this.cmtId = cmtId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
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

	public static int validateContent(String content) {
		String s = DataUtil.toTextRow(content);
		if (DataUtil.isEmpty(s)) {
			return Err.CMPCOMMENT_CONTENT_ERROR;
		}
		if (s.toLowerCase().indexOf("http://") != -1
				|| s.toLowerCase().indexOf("https://") != -1) {// 如果有url，则去除url计算字数
			String cont = DataUtil.replaceAllUrl(s, "");
			if (cont.length() > 140) {
				return Err.CMPCOMMENT_CONTENT_LENGTH_TOO_LONG;
			}
		}
		else {
			if (s.length() > 140) {
				return Err.CMPCOMMENT_CONTENT_LENGTH_TOO_LONG;
			}
		}
		return Err.SUCCESS;
	}

	public int getSendfrom() {
		return sendfrom;
	}

	public void setSendfrom(int sendfrom) {
		this.sendfrom = sendfrom;
	}

	public long getTime() {
		return this.createTime.getTime();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}