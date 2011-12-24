package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.hk.bean.CmpComment;
import com.hk.bean.UrlInfo;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;
import com.hk.svr.laba.parser.LabaAPIOutPutParser;
import com.hk.svr.laba.parser.LabaOutPutParser;

public class CmpCommentVo {
	private CmpComment cmpComment;

	private String content;

	private String source;

	private String sourceurl;

	public CmpComment getCmpComment() {
		return cmpComment;
	}

	public void setCmpComment(CmpComment cmpComment) {
		this.cmpComment = cmpComment;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static List<CmpCommentVo> createVoList(List<CmpComment> list,
			UrlInfo urlInfo, boolean forApi) {
		UserService userService = (UserService) HkUtil.getBean("userService");
		List<CmpCommentVo> volist = new ArrayList<CmpCommentVo>();
		List<Long> idList = new ArrayList<Long>();
		for (CmpComment o : list) {
			idList.add(o.getUserId());
			CmpCommentVo vo = new CmpCommentVo();
			vo.setCmpComment(o);
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
				content = parser.getHtml(urlInfo, o.getContent(), o
						.getUserId());
			}
			vo.setContent(content);
			volist.add(vo);
		}
		Map<Long, User> map = userService.getUserMapInId(idList);
		for (CmpComment o : list) {
			o.setUser(map.get(o.getUserId()));
		}
		return volist;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceurl() {
		return sourceurl;
	}

	public void setSourceurl(String sourceurl) {
		this.sourceurl = sourceurl;
	}
}