package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户装备
 * 
 * @author akwei
 */
@Table(name = "userequipment")
public class UserEquipment {

	public static final byte TOUCHFLG_N = 0;

	public static final byte TOUCHFLG_Y = 1;

	public static final byte USEFLG_N = 0;

	public static final byte USEFLG_Y = 1;

	@Id
	private long oid;

	@Column
	private long userId;

	@Column
	private long eid;

	@Column
	private byte useflg;

	@Column
	private long enjoyUserId;

	@Column
	private long companyId;

	/**
	 * 是否已触发
	 */
	@Column
	private byte touchflg;

	private Company company;

	private User enjoyUser;

	private Equipment equipment;

	/**
	 * 下次报到将要获得的点数
	 */
	private int prePoints;

	public void setPrePoints(int prePoints) {
		this.prePoints = prePoints;
	}

	public int getPrePoints() {
		return prePoints;
	}

	public void setTouchflg(byte touchflg) {
		this.touchflg = touchflg;
	}

	public byte getTouchflg() {
		return touchflg;
	}

	public long getEnjoyUserId() {
		return enjoyUserId;
	}

	public void setEnjoyUserId(long enjoyUserId) {
		this.enjoyUserId = enjoyUserId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public byte getUseflg() {
		return useflg;
	}

	public void setUseflg(byte useflg) {
		this.useflg = useflg;
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

	public long getEid() {
		return eid;
	}

	public void setEid(long eid) {
		this.eid = eid;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public User getEnjoyUser() {
		return enjoyUser;
	}

	public void setEnjoyUser(User enjoyUser) {
		this.enjoyUser = enjoyUser;
	}
}