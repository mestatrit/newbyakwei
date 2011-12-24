package com.hk.svr.badge;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.HandleCheckInUser;
import com.hk.svr.CmpCheckInService;

/**
 * 报到过3个KTV
 * 
 * @author akwei
 */
public class L1013HandleUserBadge extends BaseHandleUserBadge {

	private int ktvKindId = 5;

	@Autowired
	private CmpCheckInService cmpCheckInService;

	public void execute(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser, Badge badge) {
		int ktv_checkin_count = this.cmpCheckInService
				.countEffectCmpCheckInUserLogByKindIdAndUserIdDiffCompanyId(
						ktvKindId, handleCheckInUser.getUserId());
		if (ktv_checkin_count >= 3) {
			this.createUserBadge(badge, handleCheckInUser);
		}
	}
}