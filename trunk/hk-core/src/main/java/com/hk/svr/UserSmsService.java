package com.hk.svr;

import java.util.List;
import java.util.Map;
import com.hk.bean.UserSms;

public interface UserSmsService {
	void createUserSms(UserSms userSms);

	void updateUserSms(UserSms userSms);

	List<UserSms> getUserSmsListByUserId(long userId, int begin, int size);

	List<UserSms> getUserSmsList(int begin, int size);

	UserSms getUserSms(long msgId);

	void processUpdateUserSms(long msgId, long receiverId, byte state,
			String statemsg);

	Map<Long, UserSms> getUserSmsMapInId(List<Long> idList);
}