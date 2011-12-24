package com.hk.web.notice.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Notice;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.svr.FollowService;

public class FollowNoticeContentCreater implements NoticeContentCreater {
	public String execute(HttpServletRequest request, Notice notice) {
		Boolean isWeb = (Boolean) request.getAttribute("isWeb");
		if (isWeb != null && isWeb) {
			return this.processWeb(notice);
		}
		return this.processWap(request, notice);
	}

	private String processWap(HttpServletRequest request, Notice notice) {
		Map<String, String> map = DataUtil.getMapFromJson(notice.getData());
		long followerId = Long.valueOf(map.get("userid"));
		String nickName = map.get("nickname");
		String userUrl = request.getContextPath() + "/home.do?userId="
				+ followerId;
		String furl = request.getContextPath() + "/follow/op/op_add.do?userId="
				+ followerId;
		FollowService followService = (FollowService) HkUtil
				.getBean("followService");
		if (followService.getFollow(notice.getUserId(), followerId) != null) {
			return ResourceConfig.getText("noticecontent.follow2", userUrl,
					nickName, furl);
		}
		return ResourceConfig.getText("noticecontent.follow", userUrl,
				nickName, furl);
	}

	private String processWeb(Notice notice) {
		Map<String, String> map = DataUtil.getMapFromJson(notice.getData());
		long followerId = Long.valueOf(map.get("userid"));
		String nickName = map.get("nickname");
		String userUrl = "/user/" + followerId;
		return ResourceConfig.getText("noticecontent.follow2", userUrl,
				nickName);
	}
}