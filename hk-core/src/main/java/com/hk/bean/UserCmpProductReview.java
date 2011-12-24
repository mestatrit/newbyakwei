package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "usercmpproductreview")
public class UserCmpProductReview {
	private long labaId;

	private long userId;

	private long productId;

	private long companyId;

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getCompanyId() {
		return companyId;
	}

	private CmpProduct cmpProduct;

	public CmpProduct getCmpProduct() {
		return cmpProduct;
	}

	public void setCmpProduct(CmpProduct cmpProduct) {
		this.cmpProduct = cmpProduct;
	}

	public long getLabaId() {
		return labaId;
	}

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}
}