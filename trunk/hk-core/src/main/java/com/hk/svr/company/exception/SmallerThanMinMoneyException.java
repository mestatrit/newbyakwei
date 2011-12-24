package com.hk.svr.company.exception;

import com.hk.svr.exception.HkException;

public class SmallerThanMinMoneyException extends HkException {
	private static final long serialVersionUID = 301669627581755895L;

	private int minmoney;

	public void setMinmoney(int minmoney) {
		this.minmoney = minmoney;
	}

	public int getMinmoney() {
		return minmoney;
	}

	public SmallerThanMinMoneyException(String message) {
		super(message);
	}
}