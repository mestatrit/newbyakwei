package com.hk.web.cmpunion.valuecreater.feed;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.CmpUnionFeed;
import com.hk.frame.util.JsonUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.web.cmpunion.action.CmpUnionFeedVo;

public class BoxValueCreater implements FeedValueCreater {

	public String getValue(HttpServletRequest request,
			CmpUnionFeedVo cmpUnionFeedVo) {
		List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
		for (CmpUnionFeed cmpUnionFeed : cmpUnionFeedVo.getCmpUnionFeedList()) {
			maplist.add(JsonUtil.getMapFromJson(cmpUnionFeed.getData()));
		}
		Map<String, String> firstMap = maplist.iterator().next();
		CmpUnionFeed firstFeed = cmpUnionFeedVo.getCmpUnionFeedList()
				.iterator().next();
		String cmp_html = firstMap.get("cmp");
		StringBuilder sb = new StringBuilder();
		for (Map<String, String> map : maplist) {
			sb.append(" <a href=\"").append(request.getContextPath()).append(
					"/union/box.do?uid=").append(firstFeed.getUid()).append(
					"&boxId=").append(map.get("boxid")).append(
					"\" target=\"blank\">").append(map.get("name")).append(
					"</a>");
		}
		return ResourceConfig.getText("view.cmpunion.feed.product.content",
				cmp_html, sb.toString());
	}
}