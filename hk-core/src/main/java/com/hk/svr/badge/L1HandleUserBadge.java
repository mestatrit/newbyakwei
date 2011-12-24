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
 * 当在一个周期内，有效报到次数达到标准时，可获得此徽章
 * 
 * @author akwei
 */
public class L1HandleUserBadge extends BaseHandleUserBadge {

	@Autowired
	private CmpCheckInService cmpCheckInService;

	public void execute(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser, Badge badge) {
		int num = badge.getNum();
		int cycle = badge.getCycle();// 周期为天
		Calendar cal = Calendar.getInstance();
		Date end = DataUtil.getEndDate(cal.getTime());
		cal.add(Calendar.DATE, -(cycle - 1));
		Date begin = DataUtil.getDate(cal.getTime());
		int count = this.cmpCheckInService
				.countEffectCmpCheckInUserLogByCompanyIdAndUserId(
						handleCheckInUser.getCompanyId(), handleCheckInUser
								.getUserId(), begin, end);
		if (count >= num) {
			this.createUserBadge(badge, handleCheckInUser);
		}
	}
}