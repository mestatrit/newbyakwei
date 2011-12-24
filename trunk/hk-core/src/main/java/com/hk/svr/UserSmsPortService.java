package com.hk.svr;

import com.hk.bean.UserSmsPort;

public interface UserSmsPortService {
//	void batchCreateUserSmsPort(int portLen, int size);

	void updateUserSmsPort(UserSmsPort userSmsPort);

	/**
	 * 随机获得一个未被占用的号码,把此号码userid设置为当前参数userid
	 * 
	 * @return
	 */
	UserSmsPort makeAvailableUserSmsPort(long userId);

	/**
	 * 获得用户使用的号码，如果没有返回null
	 * 
	 * @param userId
	 * @return
	 */
	UserSmsPort getUserSmsPortByUserId(long userId);

	UserSmsPort getUserSmsPortByPort(String port);
}