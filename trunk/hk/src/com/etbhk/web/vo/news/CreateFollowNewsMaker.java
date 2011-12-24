package com.etbhk.web.vo.news;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.etbhk.web.vo.Tb_NewsVo;
import com.hk.bean.taobao.JsonKey;
import com.hk.bean.taobao.Tb_News;
import com.hk.frame.util.JsonUtil;
import com.hk.frame.util.ResourceConfig;

public class CreateFollowNewsMaker implements NewsMaker {

	@Override
	public String getNewsData(HttpServletRequest request, Tb_NewsVo tbNewsVo) {
		Tb_News tbNews = tbNewsVo.getLast();
		Map<String, String> map = JsonUtil.getMapFromJson(tbNews.getData());
		String userid_str = map.get(JsonKey.USERID);
		String nick = map.get(JsonKey.USER_NICK);
		String friendid_str = map.get(JsonKey.FRIENDID);
		String friend_nick = map.get(JsonKey.FRIEND_NICK);
		String user_html = "<a href=\"/p/" + userid_str + "\">" + nick + "</a>";
		String friend_html = "<a href=\"/p/" + friendid_str + "\">"
				+ friend_nick + "</a>";
		return ResourceConfig.getText("ehk.news.data" + tbNews.getNtype(),
				user_html, friend_html);
	}
}