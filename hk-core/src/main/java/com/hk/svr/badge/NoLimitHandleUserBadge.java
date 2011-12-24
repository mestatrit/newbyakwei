package com.hk.svr.badge;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.HandleCheckInUser;
import com.hk.svr.CmpCheckInService;

public class NoLimitHandleUserBadge extends BaseHandleUserBadge {

	@Autowired
	private CmpCheckInService cmpCheckInService;

	public void execute(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser, Badge badge) {
		long userId = handleCheckInUser.getUserId();
		Integer userAllEffectCheckInCount = (Integer) paramMap
				.get("userAllEffectCheckInCount");
		if (userAllEffectCheckInCount == null) {
			userAllEffectCheckInCount = this.cmpCheckInService
					.countEffectCmpCheckInUserLogByUserId(userId);
			paramMap
					.put("userAllEffectCheckInCount", userAllEffectCheckInCount);
		}
		int checkinnum = badge.getNum();
		if (userAllEffectCheckInCount >= checkinnum) {
			this.createUserBadge(badge, handleCheckInUser);
		}
	}
}