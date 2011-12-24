package com.hk.svr.badge;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.CmpCheckInUser;
import com.hk.bean.HandleCheckInUser;
import com.hk.bean.User;
import com.hk.bean.UserBadge;
import com.hk.svr.CmpCheckInService;
import com.hk.svr.UserService;
import com.hk.svr.processor.BadgeProcessor;

public abstract class BaseHandleUserBadge implements HandleUserBadge {

	@Autowired
	private UserService userService;

	@Autowired
	private CmpCheckInService cmpCheckInService;

	@Autowired
	private BadgeProcessor badgeProcessor;

	protected User getUser(long userId) {
		return this.userService.getUser(userId);
	}

	protected int getUser_rarrived_count(long userId) {
		return cmpCheckInService.countCmpCheckInUserByUserId(userId);
	}

	protected CmpCheckInUser getCmpCheckInUser(long userId, long companyId) {
		return this.cmpCheckInService.getCmpCheckInUser(companyId, userId);
	}

	protected void createUserBadge(Badge badge,
			HandleCheckInUser handleCheckInUser) {
		UserBadge userBadge = new UserBadge(badge);
		userBadge.setUserId(handleCheckInUser.getUserId());
		userBadge.setCompanyId(handleCheckInUser.getCompanyId());
		this.badgeProcessor.createUserBadge(userBadge, null);
	}
}