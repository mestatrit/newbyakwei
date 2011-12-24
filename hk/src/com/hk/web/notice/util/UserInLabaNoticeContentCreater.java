package com.hk.web.notice.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Notice;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;

public class UserInLabaNoticeContentCreater implements NoticeContentCreater {
	public String execute(HttpServletRequest request, Notice notice) {
		Boolean isWeb = (Boolean) request.getAttribute("isWeb");
		if (isWeb != null && isWeb) {
			return this.processWeb(notice);
		}
		return this.processWap(notice);
	}

	public String processWap(Notice notice) {
		Map<String, String> map = DataUtil.getMapFromJson(notice.getData());
		long userId = Long.valueOf(map.get("userid"));
		String nickName = map.get("nickname");
		long labaId = Long.valueOf(map.get("labaid"));
		String content = DataUtil.limitLength(map.get("content"), 16);
		return ResourceConfig.getText("notice.noticttype_"
				+ notice.getNoticeType(), String.valueOf(userId), nickName,
				String.valueOf(labaId), content);
	}

	public String processWeb(Notice notice) {
		Map<String, String> map = DataUtil.getMapFromJson(notice.getData());
		long userId = Long.valueOf(map.get("userid"));
		String nickName = map.get("nickname");
		long labaId = Long.valueOf(map.get("labaid"));
		String content = map.get("content");
		String userurl = "@<a href=\"/user/" + userId + "\">" + nickName
				+ "</a>";
		String labaurl = "<a href=\"/laba/" + labaId + "\">" + content + "</a>";
		return ResourceConfig.getText("notice.noticttypeweb_4", userurl,
				labaurl);
	}
}
