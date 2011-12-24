package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 机构留言板
 * 
 * @author akwei
 */
@Table(name = "cmporgmsg")
public class CmpOrgMsg {

	@Id
	private long oid;

	@Column
	private long companyId;

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

	@Column
	private String email;

	@Column
	private String im;

	/**
	 * 留言内容
	 */
	@Column
	private String content;

	/**
	 * 留言时间
	 */
	@Column
	private Date createTime;

	@Column
	private String ip;

	@Column
	private long orgId;

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

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

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 20)) {
			return Err.CMPORGMSG_NAME_ERROR;
		}
		if (!HkValidate.validateLength(this.tel, 20)) {
			return Err.CMPORGMSG_TEL_ERROR;
		}
		if (!HkValidate.validateLength(this.im, 50)) {
			return Err.CMPORGMSG_IM_ERROR;
		}
		if (!HkValidate.validateLength(this.email, 50)) {
			return Err.CMPORGMSG_EMAIL_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.content, true, 300)) {
			return Err.CMPORGMSG_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}
}