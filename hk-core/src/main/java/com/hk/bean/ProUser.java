package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 星光大道用户
 * 
 * @author akwei
 */
@Table(name = "prouser", id = "oid")
public class ProUser {
	private long oid;

	private String intro;

	private Date uptime;

	private String nickName;

	private String input;

	private long createrId;

	private long userId;

	private User creater;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(long createrId) {
		this.createrId = createrId;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	public int validate(boolean ignoreEmail) {
		if (DataUtil.isEmpty(nickName) || nickName.length() > 20) {
			return Err.PROUSER_NICKNAME_ERROR;
		}
		String s = DataUtil.toText(intro);
		if (s == null || s.length() > 500) {
			return Err.PROUSER_INTRO_ERROR;
		}
		if (!ignoreEmail) {
			if (!DataUtil.isLegalEmail(this.input)) {
				return Err.PROUSER_EMAIL_ERROR;
			}
		}
		return Err.SUCCESS;
	}
}