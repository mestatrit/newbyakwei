package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 栏目下的分组,每个栏目都可以有分组功能
 * 
 * @author akwei
 */
@Table(name = "cmparticlegroup")
public class CmpArticleGroup {

	@Id
	private long groupId;

	@Column
	private String name;

	@Column
	private long companyId;

	@Column
	private long cmpNavOid;

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCmpNavOid() {
		return cmpNavOid;
	}

	public void setCmpNavOid(long cmpNavOid) {
		this.cmpNavOid = cmpNavOid;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 20)) {
			return Err.CMPARTICLEGROUP_NAME_ERROR;
		}
		return Err.SUCCESS;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}