package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmptip")
public class CmpTip {

	public static final byte DONEFLG_TODO = 0;

	public static final byte DONEFLG_DONE = 1;

	public static final byte SHOWFLG_N = 0;

	public static final byte SHOWFLG_Y = 1;

	@Id
	private long tipId;

	@Column
	private long companyId;

	@Column
	private long userId;

	@Column
	private String content;

	@Column
	private Date createTime;

	@Column
	private byte showflg;

	@Column
	private byte doneflg;

	private String ip;

	private int pcityId;

	private User user;

	private Company company;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public long getTipId() {
		return tipId;
	}

	public void setTipId(long tipId) {
		this.tipId = tipId;
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

	public byte getShowflg() {
		return showflg;
	}

	public void setShowflg(byte showflg) {
		this.showflg = showflg;
	}

	public byte getDoneflg() {
		return doneflg;
	}

	public void setDoneflg(byte doneflg) {
		this.doneflg = doneflg;
	}

	public boolean isDone() {
		if (this.doneflg == DONEFLG_DONE) {
			return true;
		}
		return false;
	}

	public boolean isToDo() {
		if (this.doneflg == DONEFLG_TODO) {
			return true;
		}
		return false;
	}

	public int validate() {
		String s = DataUtil.toText(content);
		if (DataUtil.isEmpty(s)) {
			return Err.CMPTIP_CONTENT_ERROR;
		}
		if (s.length() > 500 || s.length() < 10) {
			return Err.CMPTIP_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPcityId() {
		return pcityId;
	}

	public void setPcityId(int pcityId) {
		this.pcityId = pcityId;
	}

	public String getSimpleContent() {
		if (this.content == null) {
			return null;
		}
		if (this.content.length() > 40) {
			return DataUtil.toHtmlRow(DataUtil.limitLength(DataUtil
					.toTextRow(this.content), 40));
		}
		return this.content;
	}

	public String getSimpleContent2() {
		if (this.content == null) {
			return null;
		}
		if (this.content.length() > 45) {
			return DataUtil.toHtmlRow(DataUtil.limitLength(DataUtil
					.toTextRow(this.content), 45));
		}
		return this.content;
	}

	public boolean isMoreContent() {
		if (this.content == null) {
			return false;
		}
		if (this.content.length() > 40) {
			return true;
		}
		return false;
	}
}