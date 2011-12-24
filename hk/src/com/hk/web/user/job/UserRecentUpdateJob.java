package com.hk.web.user.job;

import java.util.Calendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.User;
import com.hk.bean.UserRecentUpdate;
import com.hk.svr.LabaService;
import com.hk.svr.UserService;

public class UserRecentUpdateJob {
	@Autowired
	private UserService userService;

	@Autowired
	private LabaService labaService;

	private final Log log = LogFactory.getLog(UserRecentUpdateJob.class);

	public void invoke() {
		Calendar now = Calendar.getInstance();
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, -30);
		List<User> list = this.userService.getAllUserList();
		for (User u : list) {
			try {
				UserRecentUpdate update = this.userService
						.getUserRecentUpdate(u.getUserId());
				int labaCount = this.labaService.countLaba(u.getUserId(), date
						.getTime(), now.getTime());
				if (update == null) {
					update = new UserRecentUpdate();
					update.setUserId(u.getUserId());
					update.setLast30LabaCount(labaCount);
					this.userService.createUserRecentUpdate(update);
				}
				else {
					update.setLast30LabaCount(labaCount);
					this.userService.updateUserRecentUpdate(update);
				}
			}
			catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}
}