package com.hk.svr.badge;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.HandleCheckInUser;
import com.hk.svr.CmpCheckInService;

/**
 * 查看足迹所在组的报到次数，如果符合，就获得徽章
 * 
 * @author akwei
 */
public class L5HandleUserBadge extends BaseHandleUserBadge {

	@Autowired
	private CmpCheckInService cmpCheckInService;

	public void execute(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser, Badge badge) {
		int num = badge.getNum();
		long groupId = badge.getGroupId();
		int count = this.cmpCheckInService
				.countEffectCmpCheckInUserLogByGroupIdAndUserId(groupId,
						handleCheckInUser.getUserId());
		if (count >= num) {
			this.createUserBadge(badge, handleCheckInUser);
		}
	}
}