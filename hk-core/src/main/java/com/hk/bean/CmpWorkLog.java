package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 工作人员工作记录
 * 
 * @author akwei
 */
@Table(name = "cmpworklog")
public class CmpWorkLog {

	@Id
	private long logid;

	@Column
	private long actorId;

	@Column
	private long reserveId;

	@Column
	private long companyId;

	@Column
	private Date createTime;

	public long getLogid() {
		return logid;
	}

	public void setLogid(long logid) {
		this.logid = logid;
	}

	public long getActorId() {
		return actorId;
	}

	public void setActorId(long actorId) {
		this.actorId = actorId;
	}

	public long getReserveId() {
		return reserveId;
	}

	public void setReserveId(long reserveId) {
		this.reserveId = reserveId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}