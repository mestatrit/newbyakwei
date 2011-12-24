package com.hk.svr.badge;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.HandleCheckInUser;
import com.hk.svr.CmpCheckInService;

/**
 * 一晚上到过4个或4个以上不同的地方
 * 
 * @author akwei
 */
public class L1005HandleUserBadge extends BaseHandleUserBadge {

	@Autowired
	private CmpCheckInService cmpCheckInService;

	public void execute(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser, Badge badge) {
		List<CmpCheckInUserLog> list = this.cmpCheckInService
				.getCmpCheckInUserLogListByUserId(
						handleCheckInUser.getUserId(), 0, 1);
		if (list.size() == 0) {
			return;
		}
		CmpCheckInUserLog last = list.get(0);
		int night_venue_count = this.cmpCheckInService
				.countNigghtlyCmpCheckInUserByUserId(handleCheckInUser
						.getUserId(), last.getCreateTime());
		if (night_venue_count >= 4) {
			this.createUserBadge(badge, handleCheckInUser);
		}
	}
}