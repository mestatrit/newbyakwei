package com.hk.sms.cmd;

import com.hk.bean.UserOtherInfo;

public class UserSmsMo {
	private UserOtherInfo userOtherInfo;

	private boolean newUser;

	public UserOtherInfo getUserOtherInfo() {
		return userOtherInfo;
	}

	public void setUserOtherInfo(UserOtherInfo userOtherInfo) {
		this.userOtherInfo = userOtherInfo;
	}

	public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}
}