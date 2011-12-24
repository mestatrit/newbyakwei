package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 人物角色(预定类别)
 * 
 * @author akwei
 */
@Table(name = "cmpactorrole")
public class CmpActorRole {

	@Id
	private long roleId;

	@Column
	private long companyId;

	@Column
	private String name;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
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

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 20)) {
			return Err.CMPACTORROLE_NAME_ERROR;
		}
		return Err.SUCCESS;
	}
}