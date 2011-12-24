package com.hk.svr.badge;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.HandleCheckInUser;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpCheckInService;

/**
 * 1个月有效报到30次
 * 
 * @author akwei
 */
public class L1007HandleUserBadge extends BaseHandleUserBadge {

	@Autowired
	private CmpCheckInService cmpCheckInService;

	public void execute(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser, Badge badge) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DATE, 1);
		Date begin = cal.getTime();
		begin = DataUtil.getDate(begin);
		cal.setTime(begin);
		cal.add(Calendar.MONTH, 1);
		Date end = cal.getTime();
		int user_month_checkin_effect_count = this.cmpCheckInService
				.countEffectCmpCheckInUserLogByUserId(handleCheckInUser
						.getUserId(), begin, end);
		if (user_month_checkin_effect_count >= 30) {
			this.createUserBadge(badge, handleCheckInUser);
		}
	}
}