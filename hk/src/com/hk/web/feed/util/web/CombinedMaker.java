package com.hk.web.feed.util.web;

import com.hk.bean.Feed;

public interface CombinedMaker {

	/**
	 * 查看2个动态是否可以合并
	 * 
	 * @param arg0
	 * @param arg1
	 * @return true:可以合并, false:不可以合并
	 *         2010-4-23
	 */
	boolean isCombined(Feed arg0, Feed arg1);
}