package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "cmptemplate", id = "companyid")
public class CmpTemplate {
	private long companyId;

	private int templateId;

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
}