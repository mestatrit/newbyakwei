package com.hk.svr.badge;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.HandleCheckInUser;
import com.hk.svr.CmpCheckInService;

/**
 * 某足迹有效报到次数达到标准时，获得徽章
 * 
 * @author akwei
 */
public class L2HandleUserBadge extends BaseHandleUserBadge {

	@Autowired
	private CmpCheckInService cmpCheckInService;

	public void execute(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser, Badge badge) {
		int num = badge.getNum();
		int count = this.cmpCheckInService
				.countEffectCmpCheckInUserLogByCompanyIdAndUserId(
						handleCheckInUser.getCompanyId(), handleCheckInUser
								.getUserId());
		if (count >= num) {
			this.createUserBadge(badge, handleCheckInUser);
		}
	}
}