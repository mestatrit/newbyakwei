package com.hk.web.cmpunion.valuecreater.feed;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.CmpUnionFeed;
import com.hk.frame.util.JsonUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.web.cmpunion.action.CmpUnionFeedVo;

public class CmpActValueCreater implements FeedValueCreater {

	public String getValue(HttpServletRequest request,
			CmpUnionFeedVo cmpUnionFeedVo) {
		CmpUnionFeed feed = cmpUnionFeedVo.getCmpUnionFeedList().iterator()
				.next();
		Map<String, String> map = JsonUtil.getMapFromJson(feed.getData());
		String cmp_html = map.get("cmp");
		StringBuilder sb = new StringBuilder();
		sb.append(" <a href=\"").append(request.getContextPath()).append(
				"/union/act.do?uid=").append(feed.getUid()).append("&actId=")
				.append(map.get("actid")).append("\" target=\"blank\">")
				.append(map.get("name")).append("</a>");
		return ResourceConfig.getText("view.cmpunion.feed.act.content",
				cmp_html, sb.toString());
	}
}