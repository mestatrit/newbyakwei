package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户tip日志，可记录已经完成的事件和事件日程
 * 
 * @author akwei
 */
@Table(name = "usercmptip")
public class UserCmpTip {
	@Id
	private long oid;

	@Column
	private long tipId;

	@Column
	private long companyId;

	@Column
	private long userId;

	/**
	 * 事情完成程度 0:没有程度,1:已经加入到事件日程,2:已经完成
	 */
	@Column
	private byte doneflg;

	@Column
	private Date createTime;

	@Column
	private int pcityId;

	/**
	 * 数据内容包括 cmptip 中的 userid,content,doneflg,createtime,companyid,companyname
	 */
	@Column
	private String data;

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	private CmpTip cmpTip;

	public long getTipId() {
		return tipId;
	}

	public void setTipId(long tipId) {
		this.tipId = tipId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getDoneflg() {
		return doneflg;
	}

	public void setDoneflg(byte doneflg) {
		this.doneflg = doneflg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getPcityId() {
		return pcityId;
	}

	public void setPcityId(int pcityId) {
		this.pcityId = pcityId;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public boolean isDone() {
		if (this.doneflg == CmpTip.DONEFLG_DONE) {
			return true;
		}
		return false;
	}

	public boolean isToDo() {
		if (this.doneflg == CmpTip.DONEFLG_TODO) {
			return true;
		}
		return false;
	}

	public CmpTip getCmpTip() {
		return cmpTip;
	}

	public void setCmpTip(CmpTip cmpTip) {
		this.cmpTip = cmpTip;
	}
}