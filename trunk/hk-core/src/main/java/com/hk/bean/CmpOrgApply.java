package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 用户提交机构申请
 * 
 * @author akwei
 */
@Table(name = "cmporgapply")
public class CmpOrgApply {

	/**
	 * 未审核
	 */
	public static final byte CHECKFLG_UNCHECKED = 0;

	/**
	 * 审核不通过
	 */
	public static final byte CHECKFLG_NO = 1;

	/**
	 * 审核通过
	 */
	public static final byte CHECKFLG_YES = 2;

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private String orgName;

	/**
	 * 申请人姓名
	 */
	@Column
	private String userName;

	@Column
	private long userId;

	@Column
	private String tel;

	@Column
	private String email;

	/**
	 * 审核标志0:未审核,1:审核不通过,2:审核通过
	 */
	@Column
	private byte checkflg;

	@Column
	private Date createTime;

	/**
	 * 审核通过后，创建的机构id
	 */
	@Column
	private long orgId;

	@Column
	private int cityId;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public byte getCheckflg() {
		return checkflg;
	}

	public void setCheckflg(byte checkflg) {
		this.checkflg = checkflg;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isCheckOk() {
		if (this.checkflg == CHECKFLG_YES) {
			return true;
		}
		return false;
	}

	public boolean isCheckNo() {
		if (this.checkflg == CHECKFLG_NO) {
			return true;
		}
		return false;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.orgName, true, 50)) {
			return Err.CMPORGAPPLY_ORGNAME_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.tel, true, 20)) {
			return Err.CMPORGAPPLY_TEL_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.userName, true, 20)) {
			return Err.CMPORGAPPLY_USERNAME_ERROR;
		}
		if (!HkValidate.validateLength(this.email, true, 50)) {
			return Err.CMPORGAPPLY_EMAIL_ERROR;
		}
		return Err.SUCCESS;
	}
}