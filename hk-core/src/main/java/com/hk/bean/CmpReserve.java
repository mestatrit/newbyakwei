package com.hk.bean;

import java.util.Date;
import java.util.List;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 用户预约的企业服务
 * 
 * @author akwei
 */
@Table(name = "cmpreserve")
public class CmpReserve {

	/**
	 * 预约中
	 */
	public static final byte RESERVESTATUS_DEF = 1;

	/**
	 * 服务中
	 */
	public static final byte RESERVESTATUS_DOING = 2;

	/**
	 * 服务结束
	 */
	public static final byte RESERVESTATUS_SUCCESS = 3;

	/**
	 *服务取消
	 */
	public static final byte RESERVESTATUS_CANCEL = 4;

	/**
	 * 已经到达
	 */
	public static final byte RESERVESTATUS_ARRIVE = 5;

	@Id
	private long reserveId;

	@Column
	private long companyId;

	@Column
	private long actorId;

	@Column
	private long userId;

	/**
	 * 预约状态
	 */
	@Column
	private byte reserveStatus;

	@Column
	private Date createTime;

	@Column
	private Date reserveTime;

	@Column
	private Date endTime;

	/**
	 * ,分隔的svrid
	 */
	@Column
	private String svrdata;

	@Column
	private String username;

	@Column
	private String mobile;

	private CmpActor cmpActor;

	private List<CmpSvr> cmpSvrList;

	private Company company;

	public void setCmpSvrList(List<CmpSvr> cmpSvrList) {
		this.cmpSvrList = cmpSvrList;
	}

	public List<CmpSvr> getCmpSvrList() {
		return cmpSvrList;
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

	public long getActorId() {
		return actorId;
	}

	public void setActorId(long actorId) {
		this.actorId = actorId;
	}

	public byte getReserveStatus() {
		return reserveStatus;
	}

	public void setReserveStatus(byte reserveStatus) {
		this.reserveStatus = reserveStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(Date reserveTime) {
		this.reserveTime = reserveTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getSvrdata() {
		return svrdata;
	}

	public void setSvrdata(String svrdata) {
		this.svrdata = svrdata;
	}

	public int validate() {
		if (this.reserveTime == null) {
			return Err.CMPRESERVE_RESERVETIME_ERROR;
		}
		if (this.endTime == null) {
			return Err.CMPRESERVE_RESERVETIME_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.username, true, 30)) {
			return Err.CMPRESERVE_USERNAME_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.mobile, true, 30)) {
			return Err.CMPRESERVE_MOBILE_ERROR;
		}
		return Err.SUCCESS;
	}

	public CmpActor getCmpActor() {
		return cmpActor;
	}

	public void setCmpActor(CmpActor cmpActor) {
		this.cmpActor = cmpActor;
	}

	public boolean isCancel() {
		if (this.reserveStatus == RESERVESTATUS_CANCEL) {
			return true;
		}
		return false;
	}

	public boolean isSuccess() {
		if (this.reserveStatus == RESERVESTATUS_SUCCESS) {
			return true;
		}
		return false;
	}

	/**
	 * 是否已过期
	 * 
	 * @return
	 *         2010-8-10
	 */
	public boolean isExpire() {
		if (this.reserveTime.getTime() <= System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 是否已经是预约状态
	 * 
	 * @return
	 *         2010-8-18
	 */
	public boolean isReserved() {
		if (this.reserveStatus == RESERVESTATUS_DEF) {
			return true;
		}
		return false;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}