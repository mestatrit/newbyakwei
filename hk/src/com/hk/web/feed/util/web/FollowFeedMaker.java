package com.hk.web.feed.util.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Feed;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.web.feed.action.FeedVo;
import com.hk.web.feed.util.FeedUtil;

public class FollowFeedMaker implements FeedMaker {

	public String getContentForWap(HttpServletRequest request, FeedVo feedVo) {
		List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
		for (Feed feed : feedVo.getFeedList()) {
			maplist.add(DataUtil.getMapFromJson(feed.getData()));
		}
		Map<String, String> firstMap = maplist.iterator().next();
		String userhtml = FeedUtil.getUserWapUrl(request.getContextPath(), Long
				.valueOf(firstMap.get("userid")), firstMap.get("usernickname"));
		StringBuilder sb = new StringBuilder();
		for (Map<String, String> map : maplist) {
			sb.append(FeedUtil.getUserWapUrl(request.getContextPath(), Long
					.valueOf(map.get("userid")), map.get("nickname")));
		}
		return ResourceConfig.getText("feedcontent.follow_me", userhtml, sb
				.toString());
	}

	public String getContentForWeb(HttpServletRequest request, FeedVo feedVo) {
		List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
		for (Feed feed : feedVo.getFeedList()) {
			maplist.add(DataUtil.getMapFromJson(feed.getData()));
		}
		Map<String, String> firstMap = maplist.iterator().next();
		String userhtml = FeedUtil.getUserWebUrl(Long.valueOf(firstMap
				.get("userid")), firstMap.get("usernickname"));
		StringBuilder sb = new StringBuilder();
		for (Map<String, String> map : maplist) {
			sb.append(FeedUtil.getUserWebUrl(Long.valueOf(map.get("userid")),
					map.get("nickname")));
		}
		return ResourceConfig.getText("feedcontent.follow_me", userhtml, sb
				.toString());
	}
}