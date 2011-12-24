package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 产品分类属性模板中属性值
 * 
 * @author akwei
 */
@Table(name = "cmpproductsortattr")
public class CmpProductSortAttr {

	@Id
	private long attrId;

	@Column
	private long companyId;

	@Column
	private long sortId;

	/**
	 * 属性对应项，例如(1:对应第一项属性,2:对应第二性属性)
	 */
	@Column
	private int attrflg;

	@Column
	private String name;

	public long getAttrId() {
		return attrId;
	}

	public void setAttrId(long attrId) {
		this.attrId = attrId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAttrflg() {
		return attrflg;
	}

	public void setAttrflg(int attrflg) {
		this.attrflg = attrflg;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 20)) {
			return Err.CMPPRODUCTSORTATTR_NAME_ERROR;
		}
		return Err.SUCCESS;
	}
}