package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 产品其他属性，需要分类设置属性模板后，产品按照分类设置的模板进行选择属性
 * 
 * @author akwei
 */
@Table(name = "cmpproductattr")
public class CmpProductAttr {

	@Id
	private long productId;

	@Column
	private long companyId;

	@Column
	private long attr1;

	@Column
	private long attr2;

	@Column
	private long attr3;

	@Column
	private long attr4;

	@Column
	private long attr5;

	@Column
	private long attr6;

	@Column
	private long attr7;

	@Column
	private long attr8;

	@Column
	private long attr9;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getAttr1() {
		return attr1;
	}

	public void setAttr1(long attr1) {
		this.attr1 = attr1;
	}

	public long getAttr2() {
		return attr2;
	}

	public void setAttr2(long attr2) {
		this.attr2 = attr2;
	}

	public long getAttr3() {
		return attr3;
	}

	public void setAttr3(long attr3) {
		this.attr3 = attr3;
	}

	public long getAttr4() {
		return attr4;
	}

	public void setAttr4(long attr4) {
		this.attr4 = attr4;
	}

	public long getAttr5() {
		return attr5;
	}

	public void setAttr5(long attr5) {
		this.attr5 = attr5;
	}

	public long getAttr6() {
		return attr6;
	}

	public void setAttr6(long attr6) {
		this.attr6 = attr6;
	}

	public long getAttr7() {
		return attr7;
	}

	public void setAttr7(long attr7) {
		this.attr7 = attr7;
	}

	public long getAttr8() {
		return attr8;
	}

	public void setAttr8(long attr8) {
		this.attr8 = attr8;
	}

	public long getAttr9() {
		return attr9;
	}

	public void setAttr9(long attr9) {
		this.attr9 = attr9;
	}
}