package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmpordertable", id = "oid")
public class CmpOrderTable {
	public static final byte OBJSTATUS_BOOKED = 0;

	public static final byte OBJSTATUS_HAVINGMAILS = 1;

	private long oid;

	private long tableId;

	private Date beginTime;

	private Date endTime;

	private String name;

	private String tel;

	private String remark;

	private int personNum;

	private byte objstatus;

	private long companyId;

	private Date mealTime;

	private CmpTable cmpTable;

	public void setCmpTable(CmpTable cmpTable) {
		this.cmpTable = cmpTable;
	}

	public CmpTable getCmpTable() {
		return cmpTable;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getTableId() {
		return tableId;
	}

	public void setTableId(long tableId) {
		this.tableId = tableId;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getPersonNum() {
		return personNum;
	}

	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}

	public byte getObjstatus() {
		return objstatus;
	}

	public void setObjstatus(byte objstatus) {
		this.objstatus = objstatus;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.name);
		if (DataUtil.isEmpty(s) || s.length() > 15) {
			return Err.CMPORDERTABLE_NAME_RROR;
		}
		if (this.beginTime == null) {
			return Err.TIME_ERROR;
		}
		if (this.endTime == null) {
			return Err.TIME_ERROR;
		}
		if (this.personNum <= 0) {
			return Err.CMPORDERTABLE_PERSONNUM_ERROR;
		}
		s = DataUtil.toTextRow(this.remark);
		if (s != null && s.length() > 200) {
			return Err.CMPORDERTABLE_REMARK_ERROR;
		}
		if (this.tableId <= 0) {
			return Err.CMPTABLE_NOTEXIST;
		}
		return Err.SUCCESS;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getMealTime() {
		return mealTime;
	}

	public void setMealTime(Date mealTime) {
		this.mealTime = mealTime;
	}

	public boolean isHavingMeals() {
		if (this.objstatus == OBJSTATUS_HAVINGMAILS) {
			return true;
		}
		return false;
	}
}