package com.hk.svr;

import com.hk.bean.ApiUser;

public interface ApiUserService {
	ApiUser createApiUser(ApiUser apiUser);

	ApiUser getApiUser(long apiUserId);

	ApiUser getApiUserByUserKey(String userKey);

	void delete(int apiUserId);

	void updateApiUser(ApiUser apiUser);
}