package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Table;

@Table(name = "cmptabledailydata", id = "dataid")
public class CmpTableDailyData {
	private long dataId;

	private long companyId;

	private long sortId;

	private long tableId;

	private int pbcount;

	private Date createDate;

	public long getDataId() {
		return dataId;
	}

	public void setDataId(long dataId) {
		this.dataId = dataId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getSortId() {
		return sortId;
	}

	public void setSortId(long sortId) {
		this.sortId = sortId;
	}

	public long getTableId() {
		return tableId;
	}

	public void setTableId(long tableId) {
		this.tableId = tableId;
	}

	public int getPbcount() {
		return pbcount;
	}

	public void setPbcount(int pbcount) {
		this.pbcount = pbcount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}