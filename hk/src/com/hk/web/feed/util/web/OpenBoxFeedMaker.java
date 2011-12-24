package com.hk.web.feed.util.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Feed;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.web.feed.action.FeedVo;
import com.hk.web.feed.util.FeedUtil;

public class OpenBoxFeedMaker implements FeedMaker {

	public String getContentForWap(HttpServletRequest request, FeedVo feedVo) {
		List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
		Map<String, String> vmap = new HashMap<String, String>();
		for (Feed feed : feedVo.getFeedList()) {
			Map<String, String> map = DataUtil.getMapFromJson(feed.getData());
			// 把重复的 boxid过滤
			if (!vmap.containsKey(map.get("boxid"))) {
				maplist.add(map);
				vmap.put(map.get("boxid"), "1");
			}
		}
		Feed firstFeed = feedVo.getFirst();
		Map<String, String> firstMap = maplist.iterator().next();
		String userhtml = FeedUtil.getUserWapUrl(request.getContextPath(),
				firstFeed.getUserId(), firstMap.get("usernickname"));
		StringBuilder sb = new StringBuilder();
		for (Map<String, String> map : maplist) {
			sb.append(FeedUtil.getBoxWapUrl(request.getContextPath(), Long
					.valueOf(map.get("boxid")), map.get("boxname")));
		}
		return ResourceConfig.getText("feedcontent.openboxs_me", userhtml, sb
				.toString());
	}

	public String getContentForWeb(HttpServletRequest request, FeedVo feedVo) {
		List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
		Map<String, String> vmap = new HashMap<String, String>();
		for (Feed feed : feedVo.getFeedList()) {
			Map<String, String> map = DataUtil.getMapFromJson(feed.getData());
			// 把重复的 boxid过滤
			if (!vmap.containsKey(map.get("boxid"))) {
				maplist.add(map);
				vmap.put(map.get("boxid"), "1");
			}
		}
		Feed firstFeed = feedVo.getFirst();
		Map<String, String> firstMap = maplist.iterator().next();
		String userhtml = FeedUtil.getUserWebUrl(firstFeed.getUserId(),
				firstMap.get("usernickname"));
		StringBuilder sb = new StringBuilder();
		for (Map<String, String> map : maplist) {
			sb.append(FeedUtil.getBoxWebUrl(Long.valueOf(map.get("boxid")), map
					.get("boxname")));
		}
		return ResourceConfig.getText("feedcontent.openboxs_me", userhtml, sb
				.toString());
	}
}