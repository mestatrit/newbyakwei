package com.hk.bean;

import java.util.List;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmptablesort", id = "sortid")
public class CmpTableSort {
	private long sortId;

	private long companyId;

	private String name;

	private int totalCount;

	private int freeCount;

	private List<CmpPersonTable> cmpPersonTableList;

	public long getSortId() {
		return sortId;
	}

	public void setSortId(long sortId) {
		this.sortId = sortId;
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
		String s = DataUtil.toTextRow(name);
		if (s == null || s.length() > 15) {
			return Err.CMPTABLESORT_NAME_ERROR;
		}
		return Err.SUCCESS;
	}

	public List<CmpPersonTable> getCmpPersonTableList() {
		return cmpPersonTableList;
	}

	public void setCmpPersonTableList(List<CmpPersonTable> cmpPersonTableList) {
		this.cmpPersonTableList = cmpPersonTableList;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getFreeCount() {
		return freeCount;
	}

	public void setFreeCount(int freeCount) {
		this.freeCount = freeCount;
	}
}