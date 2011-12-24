package com.etbhk.web.vo.news;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.etbhk.web.vo.Tb_NewsVo;
import com.hk.bean.taobao.JsonKey;
import com.hk.bean.taobao.Tb_News;
import com.hk.frame.util.JsonUtil;
import com.hk.frame.util.ResourceConfig;

public class CreateAnswerNewsMaker implements NewsMaker {

	@Override
	public String getNewsData(HttpServletRequest request, Tb_NewsVo tbNewsVo) {
		Tb_News tbNews = tbNewsVo.getLast();
		Map<String, String> map = JsonUtil.getMapFromJson(tbNews.getData());
		String userid_str = map.get(JsonKey.USERID);
		String nick = map.get(JsonKey.USER_NICK);
		String aid_str = map.get(JsonKey.ASK_ID);
		String ask_title = map.get(JsonKey.ASK_TITLE);
		// String answerid_str = map.get(JsonKey.ANSWER_ID);
		String answer_content = map.get(JsonKey.ANSWER_CONTENT);
		String user_html = "<a href=\"/p/" + userid_str + "\">" + nick + "</a>";
		String ask_html = "<a href=\"" + request.getContextPath()
				+ "/tb/ask?aid=" + aid_str + "\">" + ask_title + "</a>";
		String answer_html = answer_content;
		String answer_href_0 = "<a href=\"" + request.getContextPath()
				+ "/tb/ask?aid=" + aid_str + "\">";
		String answer_href_1 = "</a>";
		return ResourceConfig.getText("ehk.news.data" + tbNews.getNtype(),
				user_html, ask_html, answer_html, answer_href_0, answer_href_1);
	}
}
