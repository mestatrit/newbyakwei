package com.hk.web.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.hk.bean.Impression;
import com.hk.bean.UrlInfo;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;
import com.hk.svr.laba.parser.LabaAPIOutPutParser;
import com.hk.svr.laba.parser.LabaOutPutParser;

public class ImpressionVo {
	private Impression impression;

	private String content;

	public Impression getImpression() {
		return impression;
	}

	public void setImpression(Impression impression) {
		this.impression = impression;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static List<ImpressionVo> createVoList(List<Impression> list,
			UrlInfo urlInfo, boolean forApi) {
		UserService userService = (UserService) HkUtil.getBean("userService");
		List<ImpressionVo> volist = new ArrayList<ImpressionVo>();
		List<Long> idList = new ArrayList<Long>();
		for (Impression o : list) {
			idList.add(o.getSenderId());
			ImpressionVo vo = new ImpressionVo();
			vo.setImpression(o);
			LabaOutPutParser parser = null;
			if (forApi) {
				parser = new LabaAPIOutPutParser();
			}
			else {
				parser = new LabaOutPutParser();
			}
			String content = null;
			if (urlInfo == null) {
				content = parser.getText(o.getContent());
			}
			else {
				content = parser.getHtml(urlInfo, o.getContent(), 0);
			}
			vo.setContent(content);
			volist.add(vo);
		}
		Map<Long, User> map = userService.getUserMapInId(idList);
		for (Impression o : list) {
			o.setSender(map.get(o.getSenderId()));
		}
		return volist;
	}
}