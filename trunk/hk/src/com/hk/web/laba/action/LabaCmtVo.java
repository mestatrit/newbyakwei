package com.hk.web.laba.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hk.bean.LabaCmt;
import com.hk.bean.UrlInfo;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;
import com.hk.svr.laba.parser.LabaOutPutParser;

public class LabaCmtVo {
	private LabaCmt labaCmt;

	private String content;

	public LabaCmtVo(LabaCmt labaCmt) {
		this.labaCmt = labaCmt;
	}

	public LabaCmt getLabaCmt() {
		return labaCmt;
	}

	public void setLabaCmt(LabaCmt labaCmt) {
		this.labaCmt = labaCmt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static LabaCmtVo createLabaCmtVo(LabaCmt labaCmt, UrlInfo urlInfo,
			boolean needUser) {
		LabaCmtVo vo = new LabaCmtVo(labaCmt);
		LabaOutPutParser parser = new LabaOutPutParser();
		if (urlInfo == null) {
			vo.setContent(parser.getText(labaCmt.getContent()));
		}
		else {
			vo.setContent(parser.getHtml(urlInfo, labaCmt.getContent(), 0));
		}
		if (needUser) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			vo.getLabaCmt().setUser(
					userService.getUser(vo.getLabaCmt().getUserId()));
		}
		return vo;
	}

	public static List<LabaCmtVo> createLabaCmtVoList(List<LabaCmt> list,
			UrlInfo urlInfo) {
		List<LabaCmtVo> volist = new ArrayList<LabaCmtVo>();
		UserService userService = (UserService) HkUtil.getBean("userService");
		List<Long> idList = new ArrayList<Long>();
		for (LabaCmt cmt : list) {
			idList.add(cmt.getUserId());
			LabaCmtVo vo = LabaCmtVo.createLabaCmtVo(cmt, urlInfo, false);
			volist.add(vo);
		}
		Map<Long, User> map = userService.getUserMapInId(idList);
		for (LabaCmt cmt : list) {
			cmt.setUser(map.get(cmt.getUserId()));
		}
		return volist;
	}
}