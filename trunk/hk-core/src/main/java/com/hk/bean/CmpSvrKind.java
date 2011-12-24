package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 企业服务的分类
 * 
 * @author akwei
 */
@Table(name = "cmpsvrkind")
public class CmpSvrKind {

	@Id
	private long kindId;

	@Column
	private long companyId;

	@Column
	private String name;

	public long getKindId() {
		return kindId;
	}

	public void setKindId(long kindId) {
		this.kindId = kindId;
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

	public int valiate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 20)) {
			return Err.CMPSVRKIND_NAME_ERROR;
		}
		return Err.SUCCESS;
	}
}