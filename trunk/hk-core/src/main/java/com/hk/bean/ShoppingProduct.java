package com.hk.bean;

public class ShoppingProduct {
	private int count;

	private long productId;

	private CmpProduct cmpProduct;

	public void setCmpProduct(CmpProduct cmpProduct) {
		this.cmpProduct = cmpProduct;
	}

	public CmpProduct getCmpProduct() {
		return cmpProduct;
	}

	/**
	 * @param count 添加数量
	 * @return true:该商品为空
	 */
	public boolean addCount(int count) {
		int c = this.getCount() + count;
		if (c < 0) {
			c = 0;
		}
		this.setCount(c);
		if (this.getCount() == 0) {
			return true;
		}
		return false;
	}

	public ShoppingProduct(long productId) {
		this.setProductId(productId);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}
}