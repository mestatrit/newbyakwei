package com.hk.svr.badge;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.HandleCheckInUser;
import com.hk.svr.CompanyService;

/**
 * 发现了3个新地方
 * 
 * @author akwei
 */
public class L1014HandleUserBadge extends BaseHandleUserBadge {
	@Autowired
	private CompanyService companyService;

	public void execute(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser, Badge badge) {
		int create_venue_count = this.companyService
				.countCompanyByCreaterId(handleCheckInUser.getUserId());
		if (create_venue_count >= 3) {
			this.createUserBadge(badge, handleCheckInUser);
		}
	}
}