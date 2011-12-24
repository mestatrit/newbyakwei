package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 足迹的分类
 * 
 * @author akwei
 */
@Table(name = "companykind", id = "kindid")
public class CompanyKind {

	public static final byte KINDID_MEIFA = 4;

	private int kindId;

	private int priceTip;// 0:无消费水平, 1平均消费, 2:最低消费

	private String name;

	private int parentId;

	private int cmpCount;

	private int orderflg;

	private CompanyUserStatusDescr companyUserStatusDescr;

	// public CompanyKind copy() {
	// CompanyKind c = new CompanyKind();
	// c.setKindId(this.kindId);
	// c.setParentId(parentId);
	// c.setPriceTip(priceTip);
	// c.setName(name);
	// return c;
	// }
	public CompanyUserStatusDescr getCompanyUserStatusDescr() {
		return companyUserStatusDescr;
	}

	public void setCompanyUserStatusDescr(
			CompanyUserStatusDescr companyUserStatusDescr) {
		this.companyUserStatusDescr = companyUserStatusDescr;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriceTip() {
		return priceTip;
	}

	public void setPriceTip(int priceTip) {
		this.priceTip = priceTip;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getCmpCount() {
		return cmpCount;
	}

	public void setCmpCount(int cmpCount) {
		this.cmpCount = cmpCount;
	}

	public int getOrderflg() {
		return orderflg;
	}

	public void setOrderflg(int orderflg) {
		this.orderflg = orderflg;
	}

	public int validate() {
		if (this.parentId <= 0) {
			return Err.COMPANYKIND_PARENTID_ERROR;
		}
		String s = DataUtil.toTextRow(this.name);
		if (DataUtil.isEmpty(s)) {
			return Err.COMPANYKIND_NAME_ERROR;
		}
		if (s.length() > 20) {
			return Err.COMPANYKIND_NAME_ERROR;
		}
		return Err.SUCCESS;
	}
}