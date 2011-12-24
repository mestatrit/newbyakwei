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
 * 连续4个晚上都在某处
 * 
 * @author akwei
 */
public class L1009HandleUserBadge extends BaseHandleUserBadge {

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
		cal.add(Calendar.DATE, 1);
		for (int i = 0; i < 4; i++) {
			cal.add(Calendar.DATE, -1);
			Date date = cal.getTime();
			int count = this.cmpCheckInService
					.countEffectNightlyCmpCheckInUserLogByCompanyAndUserId(
							handleCheckInUser.getCompanyId(), handleCheckInUser
									.getUserId(), date);
			if (count == 0) {
				return;
			}
		}
		this.createUserBadge(badge, handleCheckInUser);
	}
}