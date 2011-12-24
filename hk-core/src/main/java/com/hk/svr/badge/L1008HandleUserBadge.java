package com.hk.svr.badge;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.HandleCheckInUser;
import com.hk.svr.CmpCheckInService;

/**
 * 12个小时内有10个不同地方的报到
 * 
 * @author akwei
 */
public class L1008HandleUserBadge extends BaseHandleUserBadge {

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
		Calendar cal = Calendar.getInstance();
		cal.setTime(last.getCreateTime());
		Date end = cal.getTime();
		cal.add(Calendar.HOUR_OF_DAY, -12);
		Date begin = cal.getTime();
		int user_12hours_checkin_effect_count = this.cmpCheckInService
				.countEffectCmpCheckInUserLogByUserIdForDiffCompanyId(
						handleCheckInUser.getUserId(), begin, end);
		if (user_12hours_checkin_effect_count >= 10) {
			this.createUserBadge(badge, handleCheckInUser);
		}
	}
}