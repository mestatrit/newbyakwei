package com.hk.svr.badge;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.CmpCheckInUser;
import com.hk.bean.HandleCheckInUser;
import com.hk.bean.User;
import com.hk.svr.CmpCheckInService;

/**
 * 跟3个异性一起到达某地，前后差10分钟
 * 
 * @author akwei
 */
public class L1004HandleUserBadge extends BaseHandleUserBadge {

	@Autowired
	private CmpCheckInService cmpCheckInService;

	public void execute(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser, Badge badge) {
		User user = this.getUser(handleCheckInUser.getUserId());
		byte sex = user.getSex();
		if (sex > 0) {// 用户设置了性别
			CmpCheckInUser cmpCheckInUser = this.getCmpCheckInUser(
					handleCheckInUser.getUserId(), handleCheckInUser
							.getCompanyId());
			if (cmpCheckInUser != null) {
				Date date = cmpCheckInUser.getUptime();
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MINUTE, -10);
				Date begin = cal.getTime();
				cal.add(Calendar.MINUTE, 20);
				Date end = cal.getTime();
				int userCount = this.cmpCheckInService
						.countCmpCheckInUserByCompanyIdAndSex(handleCheckInUser
								.getCompanyId(), this.getDiffSex(sex), begin,
								end);
				if (userCount >= 3) {
					this.createUserBadge(badge, handleCheckInUser);
				}
			}
		}
	}

	private byte getDiffSex(byte sex) {
		if (sex == User.SEX_MALE) {
			return User.SEX_FEMALE;
		}
		return User.SEX_MALE;
	}
}