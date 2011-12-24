package com.etbhk.web.vo.news;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.etbhk.web.vo.Tb_NewsVo;
import com.hk.bean.taobao.JsonKey;
import com.hk.bean.taobao.Tb_News;
import com.hk.frame.util.JsonUtil;
import com.hk.frame.util.ResourceConfig;

public class CreateItemCmtNewsMaker implements NewsMaker {

	@Override
	public String getNewsData(HttpServletRequest request, Tb_NewsVo tbNewsVo) {
		Tb_News tbNews = tbNewsVo.getLast();
		Map<String, String> map = JsonUtil.getMapFromJson(tbNews.getData());
		String userid_str = map.get(JsonKey.USERID);
		String nick = map.get(JsonKey.USER_NICK);
		// String item_cmtid_str = map.get(JsonKey.ITEM_CMTID);
		String item_cmt_content = map.get(JsonKey.ITEM_CMT_CONTENT);
		String itemid_str = map.get(JsonKey.ITEMID);
		String item_title = map.get(JsonKey.ITEM_TITLE);
		String user_html = "<a href=\"/p/" + userid_str + "\">" + nick + "</a>";
		String item_html = "<a href=\"" + request.getContextPath()
				+ "/tb/item?itemid=" + itemid_str + "\">" + item_title + "</a>";
		String item_cmt_html = item_cmt_content;
		String item_cmt_href_0 = "<a href=\"" + request.getContextPath()
				+ "/tb/item?itemid=" + itemid_str + "\">";
		String item_cmt_href_1 = "</a>";
		return ResourceConfig.getText("ehk.news.data" + tbNews.getNtype(),
				user_html, item_html, item_cmt_html, item_cmt_href_0,
				item_cmt_href_1);
	}
}