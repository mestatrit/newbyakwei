package com.hk.svr;

import com.hk.bean.UserMailAuth;

public interface UserMailAuthService {
	UserMailAuth getUserMailAuth(long userId);

	UserMailAuth createUserMailAuth(long userId);

	UserMailAuth getUserMailAuthByAuthcode(String authcode);

	void deleteUserMailAuth(long userId);
}