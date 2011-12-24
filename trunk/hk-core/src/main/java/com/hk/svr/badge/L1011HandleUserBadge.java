package com.hk.svr.badge;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.HandleCheckInUser;
import com.hk.svr.CmpCheckInService;

/**
 * 同时拥有10个地主席位
 * 
 * @author akwei
 */
public class L1011HandleUserBadge extends BaseHandleUserBadge {

	@Autowired
	private CmpCheckInService cmpCheckInService;

	public void execute(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser, Badge badge) {
		int mayorcount = this.cmpCheckInService
				.countMayorByUserId(handleCheckInUser.getUserId());
		if (mayorcount >= 10) {
			this.createUserBadge(badge, handleCheckInUser);
		}
	}
}