package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "apiuser", id = "apiuserid")
public class ApiUser {
	public static final byte CHECKFLG_FAIL = -1;

	public static final byte CHECKFLG_N = 0;

	public static final byte CHECKFLG_Y = 1;

	private int apiUserId;

	private String userKey;

	private String name;

	private byte lockflg;// 是否锁定

	private Date createTime;

	private String url;

	private long userId;

	private byte checkflg;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getCheckflg() {
		return checkflg;
	}

	public void setCheckflg(byte checkflg) {
		this.checkflg = checkflg;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getApiUserId() {
		return apiUserId;
	}

	public void setApiUserId(int apiUserId) {
		this.apiUserId = apiUserId;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getLockflg() {
		return lockflg;
	}

	public void setLockflg(byte lockflg) {
		this.lockflg = lockflg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int validate() {
		if (this.userId <= 0) {
			return Err.USERID_ERROR;
		}
		String s = DataUtil.toTextRow(this.name);
		if (DataUtil.isEmpty(s)) {
			return Err.APIUSER_NAME_ERROR;
		}
		if (s.length() > 20) {
			return Err.APIUSER_NAME_ERROR;
		}
		if (!DataUtil.isEmpty(url)) {
			if (DataUtil.toTextRow(url).length() > 200) {
				return Err.APIUSER_URL_ERROR;
			}
		}
		return Err.SUCCESS;
	}
}