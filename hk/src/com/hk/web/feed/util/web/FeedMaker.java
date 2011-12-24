package com.hk.web.feed.util.web;

import javax.servlet.http.HttpServletRequest;

import com.hk.web.feed.action.FeedVo;

public interface FeedMaker {

	String getContentForWap(HttpServletRequest request, FeedVo feedVo);

	String getContentForWeb(HttpServletRequest request, FeedVo feedVo);
}