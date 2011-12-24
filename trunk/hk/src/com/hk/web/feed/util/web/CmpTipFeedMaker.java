package com.hk.web.feed.util.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Feed;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.web.feed.action.FeedVo;

public class CmpTipFeedMaker implements FeedMaker {
	public String getContentForWap(HttpServletRequest request, FeedVo feedVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContentForWeb(HttpServletRequest request, FeedVo feedVo) {
		Feed feed = feedVo.getFirst();
		Map<String, String> map = DataUtil.getMapFromJson(feed.getData());
		User user = new User();
		user.setUserId(feed.getUserId());
		user.setNickName(map.get("nickname"));
		user.setHeadPath(map.get("headpath"));
		feed.setUser(user);
		feed.setTipId(Long.valueOf(map.get("tipid")));
		String companydata = "<a href=\"/venue/" + map.get("companyid") + "\">"
				+ map.get("cmpname") + "</a>";
		String content = map.get("tip");
		return ResourceConfig.getText("feedcontent.wrotetips", companydata,
				content);
	}
}