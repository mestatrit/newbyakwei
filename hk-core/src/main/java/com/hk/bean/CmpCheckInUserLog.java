package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 某个足迹报到的日志
 * 
 * @author akwei
 */
@Table(name = "cmpcheckinuserlog", id = "logid")
public class CmpCheckInUserLog {

	public static final byte NIGHTFLG_N = 0;

	public static final byte NIGHTFLG_Y = 1;

	/**
	 * 有效报到
	 */
	public static final byte EFFECTFLG_Y = 0;

	/**
	 * 无效报到
	 */
	public static final byte EFFECTFLG_N = 1;

	@Id
	private long logId;

	@Column
	private long userId;

	@Column
	private long companyId;

	@Column
	private Date createTime;

	@Column
	private byte sex;

	@Column
	private byte effectflg;

	/**
	 * 足迹的parentId;
	 */
	@Column
	private int parentId;

	/**
	 * 足迹的kindId
	 */
	@Column
	private int kindId;

	/**
	 * 是否是夜晚报到
	 */
	@Column
	private byte nightflg;

	/**
	 * 足迹组的id
	 */
	@Column
	private long groupId;

	@Column
	private int pcityId;

	private Company company;

	private User user;

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte getNightflg() {
		return nightflg;
	}

	public void setNightflg(byte nightflg) {
		this.nightflg = nightflg;
	}

	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public byte getEffectflg() {
		return effectflg;
	}

	public void setEffectflg(byte effectflg) {
		this.effectflg = effectflg;
	}

	public boolean isEffective() {
		if (this.effectflg == EFFECTFLG_Y) {
			return true;
		}
		return false;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public int getPcityId() {
		return pcityId;
	}

	public void setPcityId(int pcityId) {
		this.pcityId = pcityId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}