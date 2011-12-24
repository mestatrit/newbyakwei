package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 用户报名表
 * 
 * @author akwei
 */
@Table(name = "cmporgstudyaduser")
public class CmpOrgStudyAdUser {

	public static byte GENDER_MALE = 1;

	public static byte GENDER_FEMALE = 2;

	@Id
	private long oid;

	/**
	 * 招生简章id
	 */
	@Column
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

	@Column
	private long userId;

	/**
	 * 姓名
	 */
	@Column
	private String name;

	/**
	 * 联系电话
	 */
	@Column
	private String tel;

	/**
	 * 手机号码
	 */
	@Column
	private String mobile;

	/**
	 * QQ/MSN
	 */
	@Column
	private String im;

	/**
	 * 所在城市
	 */
	@Column
	private String city;

	/**
	 * 留言
	 */
	@Column
	private String msg;

	@Column
	private String email;

	@Column
	private byte sex;

	@Column
	private Date createTime;

	private CmpOrgStudyAd cmpOrgStudyAd;

	public void setCmpOrgStudyAd(CmpOrgStudyAd cmpOrgStudyAd) {
		this.cmpOrgStudyAd = cmpOrgStudyAd;
	}

	public CmpOrgStudyAd getCmpOrgStudyAd() {
		return cmpOrgStudyAd;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

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

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 10)) {
			return Err.CMPORGSTUDYADUSER_NAME_ERROR;
		}
		if (!HkValidate.validateLength(this.tel, true, 20)) {
			return Err.CMPORGSTUDYADUSER_TEL_ERROR;
		}
		if (!HkValidate.validateLength(this.mobile, true, 20)) {
			return Err.CMPORGSTUDYADUSER_MOBILE_ERROR;
		}
		if (DataUtil.isEmpty(this.tel) && DataUtil.isEmpty(this.mobile)) {
			return Err.CMPORGSTUDYADUSER_TEL_MOBILE_ERROR;
		}
		if (!HkValidate.validateLength(this.im, true, 50)) {
			return Err.CMPORGSTUDYADUSER_IM_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.city, true, 20)) {
			return Err.CMPORGSTUDYADUSER_CITY_ERROR;
		}
		if (!HkValidate.validateLength(this.msg, true, 300)) {
			return Err.CMPORGSTUDYADUSER_MSG_ERROR;
		}
		return Err.SUCCESS;
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

	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}
}