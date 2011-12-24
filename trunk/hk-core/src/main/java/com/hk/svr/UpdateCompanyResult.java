package com.hk.svr;

public class UpdateCompanyResult {

	private int errorCode;

	private boolean nameChanged;

	/**
	 * 在省表中找到
	 */
	private int provinceId;

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public boolean isNameChanged() {
		return nameChanged;
	}

	public void setNameChanged(boolean nameChanged) {
		this.nameChanged = nameChanged;
	}
}