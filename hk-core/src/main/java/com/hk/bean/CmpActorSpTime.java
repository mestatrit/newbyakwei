package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.Err;

/**
 * 企业工作人员的特殊时间设置记录(某天休假，当天请假时间段，非工作日某天当班)
 * 
 * @author akwei
 */
@Table(name = "cmpactorsptime")
public class CmpActorSpTime {

	public static final byte SPFLG_NOTWORK = 1;

	public static final byte SPFLG_WORK = 2;

	@Id
	private long oid;

	@Column
	private long actorId;

	@Column
	private long companyId;

	@Column
	private Date beginTime;

	@Column
	private Date endTime;

	@Column
	private Date createTime;

	/**
	 * 工作状态0:暂停工作时间,1:工作时间
	 */
	@Column
	private byte spflg;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getActorId() {
		return actorId;
	}

	public void setActorId(long actorId) {
		this.actorId = actorId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte getSpflg() {
		return spflg;
	}

	public void setSpflg(byte spflg) {
		this.spflg = spflg;
	}

	public int validate() {
		if (this.beginTime == null || this.endTime == null) {
			return Err.CMPACTORSPTIME_TIME_ERROR;
		}
		return Err.SUCCESS;
	}
}