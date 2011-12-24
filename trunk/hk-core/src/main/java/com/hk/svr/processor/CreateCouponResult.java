package com.hk.svr.processor;

public class CreateCouponResult {

	/**
	 * 临时数据的id
	 */
	private long oid;

	/**
	 * 在省表中找到
	 */
	private int provinceId;

	private int errorCode;

	/**
	 * @return Err.NOENOUGH_HKB 没有足够的hkb,Err.SUCCESS 成功
	 *         2010-5-4
	 */
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
}