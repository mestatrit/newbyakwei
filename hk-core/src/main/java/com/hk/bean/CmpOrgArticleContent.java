package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 企业相关文章
 * 
 * @author akwei
 */
@Table(name = "cmporgarticlecontent")
public class CmpOrgArticleContent {

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private long orgId;

	@Column
	private String content;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int validate() {
		String s = DataUtil.toText(this.content);
		if (DataUtil.isEmpty(s) || s.length() > 20000) {
			return Err.CMPORGARTICLE_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}
}