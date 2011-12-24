package com.hk.web.notice.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Notice;
import com.hk.frame.util.JsonUtil;
import com.hk.frame.util.ResourceConfig;

public class ChangeMayorNoticeContentCreater implements NoticeContentCreater {

	public String execute(HttpServletRequest request, Notice notice) {
		Boolean isWeb = (Boolean) request.getAttribute("isWeb");
		if (isWeb != null && isWeb) {
			return this.processWeb(notice);
		}
		return this.processWap(request, notice);
	}

	private String processWap(HttpServletRequest request, Notice notice) {
		Map<String, String> map = JsonUtil.getMapFromJson(notice.getData());
		String userId = map.get("userid");
		String nickName = map.get("nickname");
		String userUrl = "<a href=\"" + request.getContextPath()
				+ "/home.do?userId=" + userId + "\">" + nickName + "</a>";
		String companyId = map.get("companyid");
		String cmpname = map.get("cmpname");
		String cmpurl = "<a href=\"" + request.getContextPath()
				+ "/e/cmp.do?companyId=" + companyId + "\">" + cmpname + "</a>";
		return ResourceConfig.getText("noticecontent.changemayor", userUrl,
				cmpurl);
	}

	private String processWeb(Notice notice) {
		Map<String, String> map = JsonUtil.getMapFromJson(notice.getData());
		String userId = map.get("userid");
		String nickName = map.get("nickname");
		String userUrl = "<a href=\"/user/" + userId + "\">" + nickName
				+ "</a>";
		String companyId = map.get("companyid");
		String cmpname = map.get("cmpname");
		String cmpurl = "<a href=\"/venue/" + companyId + "\">" + cmpname
				+ "</a>";
		return ResourceConfig.getText("noticecontent.changemayor", userUrl,
				cmpurl);
	}
}