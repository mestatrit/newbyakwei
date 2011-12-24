package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 招生简章
 * 
 * @author akwei
 */
@Table(name = "cmporgstudyad")
public class CmpOrgStudyAd {

	/**
	 * id
	 */
	@Id
	private long adid;

	/**
	 * 企业id
	 */
	@Column
	private long companyId;

	/**
	 * 机构id
	 */
	@Column
	private long orgId;

	/**
	 * 标题
	 */
	@Column
	private String title;

	/**
	 * 授课学校
	 */
	@Column
	private String schoolName;

	/**
	 * 授课日期
	 */
	@Column
	private Date beginTime;

	/**
	 * 有效日期
	 */
	@Column
	private Date availableTime;

	/**
	 * 上课地点
	 */
	@Column
	private String studyAddr;

	/**
	 * 授课方式
	 */
	@Column
	private String teachType;

	/**
	 * 招生对象
	 */
	@Column
	private String studyUser;

	/**
	 * 创建时间
	 */
	@Column
	private Date createTime;

	/**
	 * 培训费用
	 */
	@Column
	private String price;

	@Column
	private long kindId;

	@Column
	private long kindId2;

	@Column
	private long kindId3;

	public long getAdid() {
		return adid;
	}

	public void setAdid(long adid) {
		this.adid = adid;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(Date availableTime) {
		this.availableTime = availableTime;
	}

	public String getStudyAddr() {
		return studyAddr;
	}

	public void setStudyAddr(String studyAddr) {
		this.studyAddr = studyAddr;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getTeachType() {
		return teachType;
	}

	public void setTeachType(String teachType) {
		this.teachType = teachType;
	}

	public String getStudyUser() {
		return studyUser;
	}

	public void setStudyUser(String studyUser) {
		this.studyUser = studyUser;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.title, true, 50)) {
			return Err.CMPORGSTUDYAD_TITLE_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.schoolName, true, 50)) {
			return Err.CMPORGSTUDYAD_SCHOOLNAME_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.studyAddr, true, 200)) {
			return Err.CMPORGSTUDYAD_STUDYADDR_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.studyUser, true, 50)) {
			return Err.CMPORGSTUDYAD_STUDYUSER_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.teachType, true, 50)) {
			return Err.CMPORGSTUDYAD_TEACHTYPE_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.price, true, 20)) {
			return Err.CMPORGSTUDYAD_PRICE_ERROR;
		}
		if (this.availableTime == null) {
			return Err.CMPORGSTUDYAD_AVAILABLETIME_ERROR;
		}
		if (this.beginTime == null) {
			return Err.CMPORGSTUDYAD_BEGINTIME_ERROR;
		}
		return Err.SUCCESS;
	}

	public long getKindId() {
		return kindId;
	}

	public void setKindId(long kindId) {
		this.kindId = kindId;
	}

	public long getKindId2() {
		return kindId2;
	}

	public void setKindId2(long kindId2) {
		this.kindId2 = kindId2;
	}

	public long getKindId3() {
		return kindId3;
	}

	public void setKindId3(long kindId3) {
		this.kindId3 = kindId3;
	}
}