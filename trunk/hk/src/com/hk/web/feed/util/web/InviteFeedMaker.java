package com.hk.web.feed.util.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Feed;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.web.feed.action.FeedVo;

public class InviteFeedMaker implements FeedMaker {

	public String getContentForWap(HttpServletRequest request, FeedVo feedVo) {
		Feed feed = feedVo.getFirst();
		Map<String, String> map = DataUtil.getMapFromJson(feed.getData());
		long fr_userId = Long.valueOf(map.get("fr_userid"));
		String fr_nickName = map.get("fr_nickname");
		User user = new User();
		user.setUserId(feed.getUserId());
		user.setNickName(map.get("nickname"));
		user.setHeadPath(map.get("headpath"));
		feed.setUser(user);
		String userurl = "<a href=\"" + request.getContextPath()
				+ "/home.do?userId=" + feed.getUserId() + "\">"
				+ user.getNickName() + "</a>";
		String frurl = "<a href=\"" + request.getContextPath()
				+ "/home.do?userId=" + fr_userId + "\">" + fr_nickName + "</a>";
		return ServletUtil.getText(request, "feedcontent.invite", userurl,
				frurl);
	}

	public String getContentForWeb(HttpServletRequest request, FeedVo feedVo) {
		Feed feed = feedVo.getFirst();
		Map<String, String> map = DataUtil.getMapFromJson(feed.getData());
		User user = new User();
		user.setUserId(feed.getUserId());
		user.setNickName(map.get("nickname"));
		user.setHeadPath(map.get("headpath"));
		feed.setUser(user);
		long fr_userId = Long.valueOf(map.get("fr_userid"));
		String fr_nickName = map.get("fr_nickname");
		String userurl = "<a href=\"/user/" + feed.getUserId() + "\">"
				+ user.getNickName() + "</a>";
		String frurl = "<a href=\"/user/" + fr_userId + "\">" + fr_nickName
				+ "</a>";
		return ServletUtil.getText(request, "feedcontent.invite", userurl,
				frurl);
	}
}