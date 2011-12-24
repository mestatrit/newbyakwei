package com.hk.svr.badge;

import java.util.Map;

import com.hk.bean.Badge;
import com.hk.bean.HandleCheckInUser;

/**
 * 探险家，到过25个不同的地方
 * 
 * @author akwei
 */
public class L1002HandleUserBadge extends BaseHandleUserBadge {
	public void execute(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser, Badge badge) {
		long userId = handleCheckInUser.getUserId();
		int user_rarrived_count = this.getUser_rarrived_count(userId);
		if (user_rarrived_count >= 25) {
			this.createUserBadge(badge, handleCheckInUser);
		}
	}
}
