package com.hk.web.feed.util.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Feed;
import com.hk.bean.User;
import com.hk.bean.UserBadge;
import com.hk.frame.util.JsonUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.web.feed.action.FeedVo;

public class BadgeFeedMaker implements FeedMaker {

	public String getContentForWap(HttpServletRequest request, FeedVo feedVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContentForWeb(HttpServletRequest request, FeedVo feedVo) {
		Feed feed = feedVo.getFirst();
		Map<String, String> map = JsonUtil.getMapFromJson(feed.getData());
		User user = new User();
		user.setUserId(feed.getUserId());
		user.setNickName(map.get("nickname"));
		user.setHeadPath(map.get("headpath"));
		feed.setUser(user);
		UserBadge userBadge = new UserBadge();
		userBadge.setOid(Long.valueOf(map.get("oid")));
		userBadge.setBadgeId(Long.valueOf(map.get("badgeid")));
		userBadge.setIntro(map.get("badge_intro"));
		userBadge.setName(map.get("badge_name"));
		userBadge.setPath(map.get("badge_path"));
		feed.setUserBadge(userBadge);
		String badgedata = null;
		if (userBadge.getOid() == 0) {
			badgedata = "<a href=\"" + request.getContextPath()
					+ "/h4/user_findbadge.do?userId=" + feed.getUserId()
					+ "&badgeId=" + userBadge.getBadgeId() + "\">"
					+ userBadge.getName() + "</a>";
		}
		else {
			badgedata = "<a href=\"userbadge/" + userBadge.getOid() + "\">"
					+ userBadge.getName() + "</a>";
		}
		return ResourceConfig.getText("feedcontent.getbadge", badgedata);
	}
}