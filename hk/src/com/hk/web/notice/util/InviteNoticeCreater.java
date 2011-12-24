package com.hk.web.notice.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Notice;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;

public class InviteNoticeCreater implements NoticeContentCreater {
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
		return ResourceConfig.getText("noticecontent.invite", userId + "",
				nickName);
	}

	public String processWeb(Notice notice) {
		Map<String, String> map = DataUtil.getMapFromJson(notice.getData());
		long userId = Long.valueOf(map.get("userid"));
		String nickName = map.get("nickname");
		String userurl = "@<a href=\"/user/" + userId + "\">" + nickName
				+ "</a>";
		return ResourceConfig.getText("noticecontent.inviteweb", userurl);
	}
}