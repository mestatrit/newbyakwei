package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 某个足迹报到过的人
 * 
 * @author akwei
 */
@Table(name = "cmpcheckinuser", id = "oid")
public class CmpCheckInUser {

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private long userId;

	@Column
	private Date uptime;

	@Column
	private byte sex;

	/**
	 * 有效报到次数
	 */
	@Column
	private int effectCount;

	private User user;

	private Company company;

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public byte getSex() {
		return sex;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	public Date getUptime() {
		return uptime;
	}

	public int getEffectCount() {
		return effectCount;
	}

	public void setEffectCount(int effectCount) {
		this.effectCount = effectCount;
	}
}