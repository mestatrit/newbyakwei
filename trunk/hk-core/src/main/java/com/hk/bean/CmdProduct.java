package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "cmdproduct", id = "oid")
public class CmdProduct {
	@Id
	private long oid;

	@Column
	private int pcityId;

	@Column
	private long productId;

	@Column
	private long companyId;

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getCompanyId() {
		return companyId;
	}

	private CmpProduct cmpProduct;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public int getPcityId() {
		return pcityId;
	}

	public void setPcityId(int pcityId) {
		this.pcityId = pcityId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public CmpProduct getCmpProduct() {
		return cmpProduct;
	}

	public void setCmpProduct(CmpProduct cmpProduct) {
		this.cmpProduct = cmpProduct;
	}
}