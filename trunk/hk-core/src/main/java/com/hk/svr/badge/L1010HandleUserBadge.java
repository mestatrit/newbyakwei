package com.hk.svr.badge;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.HandleCheckInUser;
import com.hk.svr.CmpCheckInService;

/**
 * 晚上3点以后还在出没
 * 
 * @author akwei
 */
public class L1010HandleUserBadge extends BaseHandleUserBadge {

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
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (hour == 3) {
			this.createUserBadge(badge, handleCheckInUser);
		}
	}
}