package com.hk.bean;

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
@Table(name = "cmporgstudyadcontent")
public class CmpOrgStudyAdContent {

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
	 * 内容
	 */
	@Column
	private String content;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.content, true, 20000)) {
			return Err.CMPORGSTUDYADCONTENT_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}
}