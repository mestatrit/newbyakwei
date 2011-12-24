package com.hk.web.feed.util;

import com.hk.web.util.HkWebConfig;

public class FeedUtil {

	public static String getUserWebUrl(long userId, String nickName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"");
		sb.append("/user/");
		sb.append(userId);
		sb.append("\">");
		sb.append(nickName);
		sb.append("</a> ");
		return sb.toString();
	}

	public static String getUserWapUrl(String contextPath, long userId,
			String nickName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"");
		sb.append("http://");
		sb.append(HkWebConfig.getWebDomain());
		sb.append(contextPath);
		sb.append("/home.do?userId=");
		sb.append(userId);
		sb.append("\">");
		sb.append(nickName);
		sb.append("</a> ");
		return sb.toString();
	}

	public static String getBoxWapUrl(String contextPath, long boxId,
			String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"");
		sb.append("http://");
		sb.append(HkWebConfig.getWebDomain());
		sb.append(contextPath);
		sb.append("/box/box.do?boxId=");
		sb.append(boxId);
		sb.append("\">");
		sb.append(name);
		sb.append("</a> ");
		return sb.toString();
	}

	public static String getBoxWebUrl(long boxId, String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"");
		sb.append("/box/");
		sb.append(boxId);
		sb.append("\" class=\"feeda\">");
		sb.append(name);
		sb.append("</a> ");
		return sb.toString();
	}
}