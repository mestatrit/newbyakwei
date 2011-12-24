package com.hk.web.feed.util.web;

import java.util.Map;

import com.hk.bean.Feed;
import com.hk.frame.util.JsonUtil;

public class BoxCombinedMaker implements CombinedMaker {

	public boolean isCombined(Feed arg0, Feed arg1) {
		if (arg0.getUserId() == arg1.getUserId()) {
			Map<String, String> map0 = JsonUtil.getMapFromJson(arg0.getData());
			Map<String, String> map1 = JsonUtil.getMapFromJson(arg1.getData());
			String id0 = map0.get("boxid");
			String id1 = map1.get("boxid");
			if (id0 != null && id1 != null) {
				if (id0.equals(id1)) {
					return true;
				}
			}
		}
		return false;
	}
}