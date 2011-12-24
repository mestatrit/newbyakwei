package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 企业qq联系
 * 
 * @author akwei
 */
@Table(name = "cmpcontact")
public class CmpContact {

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private String qq;

	@Column
	private String qqhtml;

	@Column
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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

	public String getQqhtml() {
		return qqhtml;
	}

	public void setQqhtml(String qqhtml) {
		this.qqhtml = qqhtml;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.qq);
		if (DataUtil.isEmpty(s) || s.length() > 20) {
			return Err.CMPCONTACT_QQ_ERROR;
		}
		if (DataUtil.isEmpty(qqhtml) || this.qqhtml.length() > 1000) {
			return Err.CMPCONTACT_QQHTML_ERROR;
		}
		if (!HkValidate.validateLength(this.name, true, 20)) {
			return Err.CMPCONTACT_NAME_ERROR;
		}
		return Err.SUCCESS;
	}
}