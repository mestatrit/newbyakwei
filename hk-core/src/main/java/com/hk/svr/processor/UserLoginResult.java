package com.hk.svr.processor;

/**
 * 如果验证通过，则userId为登录用户id,error=0，否则error为错误代码
 * 
 * @author akwei
 */
public class UserLoginResult {

	private int error;

	private long userId;

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}