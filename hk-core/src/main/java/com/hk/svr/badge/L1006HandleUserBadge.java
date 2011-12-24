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
 * 7天内在同一个地方报到3次
 * 
 * @author akwei
 */
public class L1006HandleUserBadge extends BaseHandleUserBadge {

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
		Date end = last.getCreateTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(end);
		cal.add(Calendar.DATE, -6);
		Date begin = cal.getTime();
		int count = this.cmpCheckInService
				.countEffectCmpCheckInUserLogByCompanyIdAndUserId(
						handleCheckInUser.getCompanyId(), handleCheckInUser
								.getUserId(), begin, end);
		if (count >= 3) {
			this.createUserBadge(badge, handleCheckInUser);
		}
	}
}