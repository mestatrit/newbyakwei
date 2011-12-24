package com.hk.web.notice.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Notice;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;

public class LabaReplyNoticeContentCreater implements NoticeContentCreater {
	public String execute(HttpServletRequest request, Notice notice) {
		Boolean isWeb = (Boolean) request.getAttribute("isWeb");
		if (isWeb != null && isWeb) {
			return this.processWeb(notice);
		}
		return this.processWap(request, notice);
	}

	public String processWap(HttpServletRequest request, Notice notice) {
		Map<String, String> map = DataUtil.getMapFromJson(notice.getData());
		String content = DataUtil.limitLength(map.get("content"), 16);
		long userId = Long.valueOf(map.get("userid"));
		String nickName = map.get("nickname");
		String userUrl = request.getContextPath() + "/home.do?userId=" + userId;
		return ResourceConfig.getText("noticecontent.labareply", userUrl,
				nickName, content);
	}

	public String processWeb(Notice notice) {
		Map<String, String> map = DataUtil.getMapFromJson(notice.getData());
		String content = map.get("content");
		long userId = Long.valueOf(map.get("userid"));
		long labaId = Long.valueOf(map.get("labaid"));
		String nickName = map.get("nickname");
		String userurl = "@<a href=\"/user/" + userId + "\">" + nickName
				+ "</a>";
		String labaurl = "<a href=\"/laba/" + labaId + "\">" + content + "</a>";
		return ResourceConfig.getText("noticecontent.labareplyweb", userurl,
				labaurl);
	}
}