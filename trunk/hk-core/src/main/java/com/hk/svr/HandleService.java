package com.hk.svr;

import java.util.List;

import com.hk.bean.HandleCheckInUser;

/**
 * 预处理事项，通过定时程序触发的模块
 * 
 * @author akwei
 */
public interface HandleService {
	void createHandleCheckInUser(HandleCheckInUser handleCheckInUser);

	void deleteHandleCheckInUser(long userId, long companyId);

	List<HandleCheckInUser> getHandleCheckInUserList(int begin, int size);
}