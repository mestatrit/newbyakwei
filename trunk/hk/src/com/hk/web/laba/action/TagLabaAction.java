package com.hk.web.laba.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Laba;
import com.hk.bean.ScoreLog;
import com.hk.bean.Tag;
import com.hk.bean.TagLaba;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.LabaService;
import com.hk.svr.TagService;
import com.hk.svr.UserService;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.ScoreConfig;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.LabaVo;

/**
 * 某个标签的喇叭
 * 
 * @author akwei
 */
@Component("/laba/taglabalist")
public class TagLabaAction extends BaseAction {

	private int size = 21;

	@Autowired
	private LabaService labaService;

	@Autowired
	private TagService tagService;

	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long tagId = req.getLong("tagId");
		long reuserId = req.getLong("reuserId");
		Tag tag = tagService.getTag(tagId);
		if (tag == null) {
			return null;
		}
		if (reuserId > 0) {
			this.tagService.addTagClick(tagId, reuserId, 1);
		}
		// 用户主动点击，加积分
		if (this.getLoginUser(req) != null && reuserId > 0) {
			ScoreLog scoreLog = ScoreLog.create(reuserId, HkLog.CLICKTAGINLABA,
					tagId, ScoreConfig.getClickTagInLaba());
			this.userService.addScore(scoreLog);
		}
		SimplePage page = req.getSimplePage(size);
		int fromhelp = req.getInt("fromhelp");
		long helpUserId = req.getLong("helpUserId");
		List<TagLaba> list = null;
		if (fromhelp == 0) {
			list = labaService.getTagLabaList(tagId, page.getBegin(), size);
		}
		else {// 从帮助过来的
			list = labaService.getTagLabaListByUserId(tagId, helpUserId, page
					.getBegin(), size);
		}
		page.setListSize(list.size());
		List<Laba> labalist = new ArrayList<Laba>();
		for (TagLaba o : list) {
			labalist.add(o.getLaba());
		}
		List<LabaVo> labavolist = LabaVo.createVoList(labalist, this
				.getLabaParserCfg(req));
		req.setAttribute("tagId", tagId);
		req.setAttribute("tag", tag);
		req.setAttribute("labavolist", labavolist);
		req.setAttribute("fromhelp", fromhelp);
		req.setAttribute("helpUserId", helpUserId);
		return "/WEB-INF/page/laba/taglabalist.jsp";
	}

	/**
	 * pc版展示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-4-27
	 */
	public String web(HkRequest req, HkResponse resp) throws Exception {
		long tagId = req.getLongAndSetAttr("tagId");
		long reuserId = req.getLong("reuserId");
		Tag tag = tagService.getTag(tagId);
		if (tag == null) {
			return null;
		}
		if (reuserId > 0) {
			this.tagService.addTagClick(tagId, reuserId, 1);
		}
		// 用户主动点击，加积分
		if (this.getLoginUser(req) != null && reuserId > 0) {
			ScoreLog scoreLog = ScoreLog.create(reuserId, HkLog.CLICKTAGINLABA,
					tagId, ScoreConfig.getClickTagInLaba());
			this.userService.addScore(scoreLog);
		}
		SimplePage page = req.getSimplePage(size);
		int fromhelp = req.getInt("fromhelp");
		long helpUserId = req.getLong("helpUserId");
		List<TagLaba> list = null;
		if (fromhelp == 0) {
			list = labaService.getTagLabaList(tagId, page.getBegin(), page
					.getSize() + 1);
		}
		else {// 从帮助过来的
			list = labaService.getTagLabaListByUserId(tagId, helpUserId, page
					.getBegin(), page.getSize() + 1);
		}
		this.processListForPage(page, list);
		List<Laba> labalist = new ArrayList<Laba>();
		for (TagLaba o : list) {
			labalist.add(o.getLaba());
		}
		List<LabaVo> labavolist = LabaVo.createVoList(labalist, this
				.getLabaParserCfgWeb4(req));
		req.setAttribute("tag", tag);
		req.setAttribute("labavolist", labavolist);
		req.setAttribute("fromhelp", fromhelp);
		req.setAttribute("helpUserId", helpUserId);
		return this.getWeb4Jsp("laba/taglabalist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String viewbyname(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("v");
		Tag o = this.tagService.getTagByName(name);
		if (o == null) {
			return null;
		}
		return "r:/laba/taglabalist.do?tagId=" + o.getTagId();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String n(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("v");
		Tag o = this.tagService.getTagByName(name);
		if (o == null) {
			return null;
		}
		return "r:/laba/taglabalist_web.do?tagId=" + o.getTagId();
	}
}