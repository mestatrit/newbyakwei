package com.hk.web.feed.util.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Feed;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.web.feed.action.FeedVo;

public class EquFeedMaker implements FeedMaker {

	public String getContentForWap(HttpServletRequest request, FeedVo feedVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContentForWeb(HttpServletRequest request, FeedVo feedVo) {
		Feed feed = feedVo.getFirst();
		Map<String, String> datamap = DataUtil.getMapFromJson(feed.getData());
		String vs = datamap.get("vs");
		if (vs != null && vs.equals("1")) {
			return this.processVs(feed, datamap);
		}
		String forcmp = datamap.get("forcmp");
		if (forcmp != null && forcmp.equals("1")) {
			return this.processCmp(feed, datamap);
		}
		String sos = datamap.get("sos");
		if (sos != null && sos.equals("1")) {
			return this.processSos(feed, datamap);
		}
		return null;
	}

	private String processCmp(Feed feed, Map<String, String> datamap) {
		long companyId = Long.valueOf(datamap.get("companyid"));
		String nickName = datamap.get("nickname");
		String cmpname = datamap.get("cmpname");
		String cmpurl = "<a href=\"/venue/" + companyId + "/\">" + cmpname
				+ "</a>";
		String userurl = "<a href=\"/user/" + feed.getUserId() + "/\">"
				+ nickName + "</a>";
		String ename = datamap.get("ename");
		return ResourceConfig.getText("feedcontent.equ.cmp", userurl, cmpurl,
				ename);
	}

	private String processVs(Feed feed, Map<String, String> datamap) {
		long enjoyUserId = Long.valueOf(datamap.get("enjoyuserid"));
		String nickName = datamap.get("nickname");
		String enjoyNickName = datamap.get("enjoynickname");
		String userurl = "<a href=\"/user/" + feed.getUserId() + "/\">"
				+ nickName + "</a>";
		String enjoyuserurl = "<a href=\"/user/" + enjoyUserId + "/\">"
				+ enjoyNickName + "</a>";
		String ename = datamap.get("ename");
		return ResourceConfig.getText("feedcontent.equ.vs", userurl,
				enjoyuserurl, ename);
	}

	private String processSos(Feed feed, Map<String, String> datamap) {
		long ouserId = Long.valueOf(datamap.get("ouserid"));
		String nickName = datamap.get("nickname");
		String vsename = datamap.get("vsename");
		String onickName = datamap.get("onickname");
		String ename = datamap.get("ename");
		String userurl = "<a href=\"/user/" + feed.getUserId() + "/\">"
				+ nickName + "</a>";
		String ouserurl = "<a href=\"/user/" + ouserId + "/\">" + onickName
				+ "</a>";
		return ResourceConfig.getText("feedcontent.equ.sos", userurl, vsename,
				ouserurl, ename);
	}
}