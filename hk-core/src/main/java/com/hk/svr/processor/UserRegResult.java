package com.hk.svr.processor;

import com.hk.svr.pub.Err;

public class UserRegResult {

	private int errorCode;

	private boolean nickNameDuplicate;

	private long userId;

	private int cityId;

	/**
	 * 成功返回 {@link Err.SUCCESS} ,失败返回 Err.INVITECODE_ERROR
	 * {@link Err.INVITECODE_ERROR}
	 * 
	 * @return
	 *         2010-4-28
	 */
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isNickNameDuplicate() {
		return nickNameDuplicate;
	}

	public void setNickNameDuplicate(boolean nickNameDuplicate) {
		this.nickNameDuplicate = nickNameDuplicate;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getCityId() {
		return cityId;
	}
}