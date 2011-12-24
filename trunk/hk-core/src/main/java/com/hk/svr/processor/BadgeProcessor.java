package com.hk.svr.processor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Company;
import com.hk.bean.Feed;
import com.hk.bean.IpCityRange;
import com.hk.bean.User;
import com.hk.bean.UserBadge;
import com.hk.frame.util.DataUtil;
import com.hk.svr.BadgeService;
import com.hk.svr.CompanyService;
import com.hk.svr.FeedService;
import com.hk.svr.IpCityService;
import com.hk.svr.UserService;

public class BadgeProcessor {

	@Autowired
	private BadgeService badgeService;

	@Autowired
	private FeedService feedService;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	public void createUserBadge(UserBadge userBadge, String ip) {
		this.badgeService.createUserBadge(userBadge);
		this.onUserBadgeCreate(userBadge, ip);
	}

	private void onUserBadgeCreate(UserBadge userBadge, String ip) {
		if (userBadge.getOid() <= 0) {
			return;
		}
		Feed feed = new Feed();
		feed.setUserId(userBadge.getUserId());
		feed.setCreateTime(new Date());
		feed.setFeedType(Feed.FEEDTYPE_GETBADGE);
		User user = this.userService.getUser(userBadge.getUserId());
		feed.setCityId(user.getPcityId());
		if (ip != null) {
			IpCityRange ipCityRange = this.ipCityService.getIpCityRange(ip);
			if (ipCityRange != null) {
				feed.setRangeId(ipCityRange.getRangeId());
				feed.setIpNumber(DataUtil.parseIpNumber(ip));
			}
		}
		Company company = this.companyService.getCompany(userBadge
				.getCompanyId());
		if (company != null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("nickname", user.getNickName());
			map.put("headpath", user.getHeadPath());
			map.put("oid", String.valueOf(userBadge.getOid()));
			map.put("badgeid", String.valueOf(userBadge.getBadgeId()));
			map.put("badge_name", userBadge.getName());
			map.put("badge_intro", userBadge.getIntro());
			map.put("badge_path", userBadge.getPath());
			feed.setData(DataUtil.toJson(map));
			this.feedService.createFeed(feed, null);
		}
	}
}