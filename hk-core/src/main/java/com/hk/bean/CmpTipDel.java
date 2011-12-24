package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "cmptipdel")
public class CmpTipDel {

	public CmpTipDel() {
	}

	public CmpTipDel(CmpTip cmpTip) {
		this.cmpTip = cmpTip;
		this.tipId = cmpTip.getTipId();
		this.companyId = cmpTip.getCompanyId();
		this.userId = cmpTip.getUserId();
		this.content = cmpTip.getContent();
		this.createTime = cmpTip.getCreateTime();
		this.showflg = cmpTip.getShowflg();
		this.doneflg = cmpTip.getDoneflg();
	}

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

	@Column
	private long opuserId;

	@Column
	private long optime;

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	private CmpTip cmpTip;

	public void setCmpTip(CmpTip cmpTip) {
		this.cmpTip = cmpTip;
	}

	public CmpTip getCmpTip() {
		return cmpTip;
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

	public long getOpuserId() {
		return opuserId;
	}

	public void setOpuserId(long opuserId) {
		this.opuserId = opuserId;
	}

	public long getOptime() {
		return optime;
	}

	public void setOptime(long optime) {
		this.optime = optime;
	}
}