package com.hk.svr.badge;

import java.util.Map;

import com.hk.bean.Badge;
import com.hk.bean.HandleCheckInUser;

/**
 * 超级巨星，到过50个不同的地方
 * 
 * @author akwei
 */
public class L1003HandleUserBadge extends BaseHandleUserBadge {
	public void execute(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser, Badge badge) {
		long userId = handleCheckInUser.getUserId();
		int user_rarrived_count = this.getUser_rarrived_count(userId);
		if (user_rarrived_count >= 50) {
			this.createUserBadge(badge, handleCheckInUser);
		}
	}
}
