package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 企业产品分类属性模板
 * 
 * @author akwei
 */
@Table(name = "cmpproductsortattrmodule")
public class CmpProductSortAttrModule {

	@Id
	private int sortId;

	@Column
	private long companyId;

	/**
	 * json格式数据
	 */
	@Column
	private String attrName;

	public int getSortId() {
		return sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
}