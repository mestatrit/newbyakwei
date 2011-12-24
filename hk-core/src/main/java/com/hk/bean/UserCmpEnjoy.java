package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户在某个足迹使用道具
 * 
 * @author akwei
 */
@Table(name = "usercmpenjoy")
public class UserCmpEnjoy {

	@Id
	private long oid;

	@Column
	private long userId;

	@Column
	private long companyId;

	@Column
	private long ueid;

	@Column
	private Date createTime;

	private UserEquipment userEquipment;

	public void setUserEquipment(UserEquipment userEquipment) {
		this.userEquipment = userEquipment;
	}

	public UserEquipment getUserEquipment() {
		return userEquipment;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setUeid(long ueid) {
		this.ueid = ueid;
	}

	public long getUeid() {
		return ueid;
	}
}