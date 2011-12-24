package com.hk.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

@Table(name = "authcompany", id = "sysid")
public class AuthCompany {

	public static final byte MAINSTATUS_CHECKFAIL = -1;// 不通过

	public static final byte MAINSTATUS_UNCHECK = 0;// 未审核

	public static final byte MAINSTATUS_CHECKED = 1;// 通过

	@Id
	private long sysId;

	@Column
	private long companyId;// 目标企业

	@Column
	private long userId;// 申请认领的用户

	@Column
	private Date createTime;// 申请时间

	@Column
	private String content;// 申请内容

	@Column
	private byte mainStatus;// 审核状态 0:未审核,-1:不通过,1:通过

	/**
	 * 要创建的足迹的名称
	 */
	@Column
	private String name;

	@Column
	private String tel;

	/**
	 * 联系人姓名
	 */
	@Column
	private String username;

	private Company company;

	private User user;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte getMainStatus() {
		return mainStatus;
	}

	public void setMainStatus(byte mainStatus) {
		this.mainStatus = mainStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> validateList() {
		List<Integer> list = new ArrayList<Integer>();
		if (!HkValidate.validateEmptyAndLength(this.content, true, 200)) {
			list.add(Err.AUTHCOMPANY_CONTENT_ERROR);
		}
		if (!HkValidate.validateEmptyAndLength(this.name, true, 50)) {
			list.add(Err.AUTHCOMPANY_NAME_ERROR);
		}
		if (!HkValidate.validateEmptyAndLength(this.tel, true, 40)) {
			list.add(Err.AUTHCOMPANY_TEL_ERROR);
		}
		if (!HkValidate.validateEmptyAndLength(this.username, true, 30)) {
			list.add(Err.AUTHCOMPANY_USERNAME_ERROR);
		}
		return list;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.content, true, 200)) {
			return Err.AUTHCOMPANY_CONTENT_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.name, true, 50)) {
			return Err.AUTHCOMPANY_NAME_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.tel, true, 40)) {
			return Err.AUTHCOMPANY_TEL_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.username, true, 30)) {
			return Err.AUTHCOMPANY_USERNAME_ERROR;
		}
		return Err.SUCCESS;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isCheckFail() {
		if (this.mainStatus == MAINSTATUS_CHECKFAIL) {
			return true;
		}
		return false;
	}

	public boolean isCheckOk() {
		if (this.mainStatus == MAINSTATUS_CHECKED) {
			return true;
		}
		return false;
	}
}