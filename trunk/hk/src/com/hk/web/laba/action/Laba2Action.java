package com.hk.web.laba.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Bomber;
import com.hk.bean.FavLaba;
import com.hk.bean.Follow;
import com.hk.bean.IpCityLaba;
import com.hk.bean.IpCityRange;
import com.hk.bean.IpCityRangeLaba;
import com.hk.bean.Laba;
import com.hk.bean.LabaCmt;
import com.hk.bean.LabaTag;
import com.hk.bean.PinkLaba;
import com.hk.bean.RefLaba;
import com.hk.bean.ScoreLog;
import com.hk.bean.Tag;
import com.hk.bean.TagLaba;
import com.hk.bean.UrlInfo;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BombService;
import com.hk.svr.FollowService;
import com.hk.svr.IpCityService;
import com.hk.svr.LabaService;
import com.hk.svr.TagService;
import com.hk.svr.UserService;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.ScoreConfig;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.IpZoneInfo;
import com.hk.web.pub.action.LabaParserCfg;
import com.hk.web.pub.action.LabaVo;

@Component("/laba")
public class Laba2Action extends BaseAction {

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private LabaService labaService;

	@Autowired
	private BombService bombService;

	@Autowired
	private FollowService followService;

	@Autowired
	private TagService tagService;

	@Autowired
	private UserService userService;

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String back(HkRequest req, HkResponse resp) throws Exception {
		String from = req.getString("from");
		if (from != null) {
			if (from.equals("home")) {
				return "r:/home_web.do?userId=" + req.getLong("ouserId");
			}
			if (from.equals("userlaba")) {
				return "r:/userlaba.do?userId=" + req.getLong("ouserId")
						+ "&page=" + req.getInt("repage");
			}
			if (from.equals("replylaba")) {
				return "r:/laba.do?labaId=" + req.getLong("replylabaId");
			}
		}
		return null;
	}

	/**
	 * 到回复页面(喇叭单页),如果是转的喇叭，就到被转发的喇叭页面 如果是引用回复别人喇叭，点击时间到被引用的喇叭页面，点击回应到当前喇叭页面
	 * 如果是引用回复自己的喇叭，都到当前喇叭页面, 判断通知是否是当前查看喇叭,如果是就把通知置为已读
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long labaId = req.getLongAndSetAttr("labaId");
		if (labaId <= 0) {
			return this.getNotFoundForward(resp);
		}
		this.checkNoticeLaba(req);
		req.reSetAttribute("ouserId");
		User loginUser = this.getLoginUser(req);
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return this.getNotFoundForward(resp);
		}
		UrlInfo urlInfo = this.getUrlInfoWeb(req);
		LabaParserCfg cfg = this.getLabaParserCfgWeb(req);
		cfg.setParseLongContent(true);
		cfg.setUrlInfo(urlInfo);
		LabaVo labaVo = LabaVo.create(laba, cfg);
		if (laba.getRefLabaId() > 0) {// 如果是引用回复，就到被引用的喇叭页面
			Laba rlaba = this.labaService.getLaba(laba.getRefLabaId());
			if (rlaba != null) {
				return "r:/laba.do?labaId=" + laba.getRefLabaId() + "&"
						+ req.getQueryString();
			}
		}
		// 频道集合
		List<Tag> taglist2 = this.labaService.getTagList(labaId,
				LabaTag.ACCESSIONAL_Y);
		req.setAttribute("taglist2", taglist2);
		if (loginUser != null && taglist2.size() < 3) {
			req.setAttribute("canaddtag", true);
		}
		if (laba.getSendFrom() < 100) {// 喇叭发送方式
			labaVo.setSendFromData(req.getText("laba.sendfrom.data."
					+ laba.getSendFrom()));
		}
		if (loginUser != null) {
			boolean collected = this.labaService.isCollected(loginUser
					.getUserId(), labaId);
			labaVo.setFav(collected);
		}
		// 引用当前喇叭的集合
		List<RefLaba> reflabalist = this.labaService.getRefLabaList(labaId, 0,
				21);
		if (reflabalist.size() == 21) {
			req.setAttribute("morerefuser", true);
			reflabalist.remove(20);
		}
		req.setAttribute("reflabalist", reflabalist);
		req.setAttribute("labaVo", labaVo);
		if (loginUser != null) {
			Bomber bomber = this.bombService.getBomber(loginUser.getUserId());
			req.setAttribute("bomber", bomber);// 是否持有炸弹
		}
		// 查看是否是精华喇叭
		PinkLaba pinkLaba = this.labaService.getPinkLaba(labaId);
		if (pinkLaba != null) {
			req.setAttribute("pink", true);
		}
		req.setAttribute("query_string", req.getQueryString());
		if (loginUser != null) {
			Follow follow = this.followService.getFollow(laba.getUserId(),
					loginUser.getUserId());
			if (follow != null) {
				req.setAttribute("friend", true);
			}
			req.setAttribute("fav", this.labaService.isCollected(loginUser
					.getUserId(), labaId));
		}
		User user = labaVo.getLaba().getUser();
		req.setAttribute("user", user);
		SimplePage page = req.getSimplePage(20);
		List<LabaCmt> labacmtlist = this.labaService.getLabaCmtListByLabaId(
				labaId, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, labacmtlist);
		List<LabaCmtVo> labacmtvolist = LabaCmtVo.createLabaCmtVoList(
				labacmtlist, urlInfo);
		req.setAttribute("labacmtvolist", labacmtvolist);
		return this.getWeb3Jsp("laba/laba.jsp");
	}

	/**
	 * 到回复页面(喇叭单页),如果是转的喇叭，就到被转发的喇叭页面 如果是引用回复别人喇叭，点击时间到被引用的喇叭页面，点击回应到当前喇叭页面
	 * 如果是引用回复自己的喇叭，都到当前喇叭页面, 判断通知是否是当前查看喇叭,如果是就把通知置为已读
	 */
	public String view4(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("to_laba", true);
		long labaId = req.getLongAndSetAttr("labaId");
		if (labaId <= 0) {
			return this.getNotFoundForward(resp);
		}
		this.checkNoticeLaba(req);
		req.reSetAttribute("ouserId");
		User loginUser = this.getLoginUser(req);
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return this.getNotFoundForward(resp);
		}
		UrlInfo urlInfo = this.getUrlInfoWeb(req);
		LabaParserCfg cfg = this.getLabaParserCfgWeb4(req);
		cfg.setParseLongContent(true);
		cfg.setUrlInfo(urlInfo);
		LabaVo labaVo = LabaVo.create(laba, cfg);
		if (laba.getRefLabaId() > 0) {// 如果是引用回复，就到被引用的喇叭页面
			Laba rlaba = this.labaService.getLaba(laba.getRefLabaId());
			if (rlaba != null) {
				return "r:/laba_view4.do?labaId=" + laba.getRefLabaId() + "&"
						+ req.getQueryString();
			}
		}
		// 频道集合
		List<Tag> taglist2 = this.labaService.getTagList(labaId,
				LabaTag.ACCESSIONAL_Y);
		req.setAttribute("taglist2", taglist2);
		if (loginUser != null && taglist2.size() < 3) {
			req.setAttribute("canaddtag", true);
		}
		if (laba.getSendFrom() < 100) {// 喇叭发送方式
			labaVo.setSendFromData(req.getText("laba.sendfrom.data."
					+ laba.getSendFrom()));
		}
		if (loginUser != null) {
			boolean collected = this.labaService.isCollected(loginUser
					.getUserId(), labaId);
			labaVo.setFav(collected);
		}
		List<LabaVo> labavolist = new ArrayList<LabaVo>();
		labavolist.add(labaVo);
		req.setAttribute("labavolist", labavolist);
		if (loginUser != null) {
			Bomber bomber = this.bombService.getBomber(loginUser.getUserId());
			req.setAttribute("bomber", bomber);// 是否持有炸弹
		}
		// 查看是否是精华喇叭
		PinkLaba pinkLaba = this.labaService.getPinkLaba(labaId);
		if (pinkLaba != null) {
			req.setAttribute("pink", true);
		}
		req.setAttribute("query_string", req.getQueryString());
		if (loginUser != null) {
			Follow follow = this.followService.getFollow(laba.getUserId(),
					loginUser.getUserId());
			if (follow != null) {
				req.setAttribute("friend", true);
			}
			req.setAttribute("fav", this.labaService.isCollected(loginUser
					.getUserId(), labaId));
		}
		User user = labaVo.getLaba().getUser();
		req.setAttribute("user", user);
		SimplePage page = req.getSimplePage(20);
		List<LabaCmt> labacmtlist = this.labaService.getLabaCmtListByLabaId(
				labaId, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, labacmtlist);
		List<LabaCmtVo> labacmtvolist = LabaCmtVo.createLabaCmtVoList(
				labacmtlist, urlInfo);
		req.setAttribute("labacmtvolist", labacmtvolist);
		return this.getWeb4Jsp("laba/laba.jsp");
	}

	/**
	 * 喇叭的回复集合
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String loadlabacmtlist(HkRequest req, HkResponse resp) {
		long labaId = req.getLongAndSetAttr("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return null;
		}
		req.setAttribute("laba", laba);
		List<LabaCmt> list = this.labaService.getLabaCmtListByLabaId(labaId, 0,
				21);
		if (list.size() == 21) {
			req.setAttribute("morecmt", true);
			list.remove(list.size() - 1);
		}
		List<LabaCmtVo> labacmtvolist = LabaCmtVo.createLabaCmtVoList(list,
				this.getUrlInfoWeb(req));
		req.setAttribute("labacmtvolist", labacmtvolist);
		return this.getWeb3Jsp("inc/labacmtvo_inc.jsp");
	}

	/**
	 * 喇叭的回复集合
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String loadlabacmtlist4(HkRequest req, HkResponse resp) {
		long labaId = req.getLongAndSetAttr("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return null;
		}
		req.setAttribute("laba", laba);
		List<LabaCmt> list = this.labaService.getLabaCmtListByLabaId(labaId, 0,
				21);
		if (list.size() == 21) {
			req.setAttribute("morecmt", true);
			list.remove(list.size() - 1);
		}
		List<LabaCmtVo> labacmtvolist = LabaCmtVo.createLabaCmtVoList(list,
				this.getUrlInfoWeb(req));
		req.setAttribute("labacmtvolist", labacmtvolist);
		return this.getWeb4Jsp("inc/labacmtvo_inc.jsp");
	}

	/**
	 * 喇叭的回复集合
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String reply(HkRequest req, HkResponse resp) {
		// long labaId = req.getLong("labaId");
		// int ajax = req.getIntAndSetAttr("ajax");
		// SimplePage page = req.getSimplePage(20);
		// List<LabaReply> list = labaService.getLabaReplyList(labaId, page
		// .getBegin(), page.getSize() + 1);
		// if (list.size() == page.getSize() + 1) {
		// req.setAttribute("hasmore", true);
		// list.remove(page.getSize());
		// }
		// List<Laba> labalist = new ArrayList<Laba>();
		// for (LabaReply o : list) {
		// labalist.add(o.getReplyLaba());
		// }
		// List<LabaVo> labavolist = LabaVo.createVoList(labalist, this
		// .getLabaParserCfg(req));
		// req.setAttribute("labaId", labaId);
		// req.setAttribute("labavolist", labavolist);
		// if (ajax == 1) {
		// return this.getWeb3Jsp("inc/labavolist_inc.jsp");
		// }
		return this.getWeb3Jsp("laba/reply.jsp");
	}

	/**
	 * 标签的喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String taglaba(HkRequest req, HkResponse resp) {
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
		SimplePage page = req.getSimplePage(20);
		int fromhelp = req.getInt("fromhelp");
		long helpUserId = req.getLong("helpUserId");
		List<TagLaba> list = null;
		if (fromhelp == 0) {
			list = labaService.getTagLabaList(tagId, page.getBegin(), page
					.getSize() + 1);
		}
		else {// 从帮助过来的
			list = labaService.getTagLabaListByUserId(tagId, helpUserId, page
					.getBegin(), page.getSize());
		}
		this.processListForPage(page, list);
		List<Laba> labalist = new ArrayList<Laba>();
		for (TagLaba o : list) {
			labalist.add(o.getLaba());
		}
		List<LabaVo> labavolist = LabaVo.createVoList(labalist, this
				.getLabaParserCfg(req));
		req.setAttribute("labavolist", labavolist);
		req.setAttribute("fromhelp", fromhelp);
		req.setAttribute("helpUserId", helpUserId);
		req.setAttribute("tag", tag);
		return this.getWeb3Jsp("laba/taglaba.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String list(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			req.setAttribute("userId", loginUser.getUserId());
		}
		String w = req.getStringAndSetAttr("w");
		if (w == null) {
			if (loginUser != null) {
				this.list_follow(req);
			}
			else {
				w = "all";
			}
		}
		else if (w.equals("follow")) {
			this.list_follow(req);
		}
		else if (w.equals("range")) {
			this.list_range(req);
		}
		else if (w.equals("ip")) {
			this.list_ip(req);
		}
		else if (w.equals("city")) {
			this.list_city(req);
		}
		else if (w.equals("all")) {
			this.list_all(req);
		}
		return this.getWeb3Jsp("/laba/list.jsp");
	}

	private void list_follow(HkRequest req) {
		User loginUser = this.getLoginUser(req);
		if (loginUser == null) {
			return;
		}
		SimplePage page = req.getSimplePage(20);
		List<Laba> list = labaService.getLabaListForFollowByUserId(loginUser
				.getUserId(), page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		LabaParserCfg cfg = this.getLabaParserCfgWeb4(req);
		List<LabaVo> labavolist = LabaVo.createVoList(list, cfg);
		req.setAttribute("labavolist", labavolist);
	}

	private void list_range(HkRequest req) {
		SimplePage page = req.getSimplePage(20);
		IpCityRange range = this.ipCityService.getIpCityRange(req
				.getRemoteAddr());
		if (range != null) {
			List<IpCityRangeLaba> list = labaService.getIpCityRangeLabaList(
					range.getRangeId(), page.getBegin(), page.getSize() + 1);
			this.processListForPage(page, list);
			List<Laba> labalist = new ArrayList<Laba>();
			for (IpCityRangeLaba o : list) {
				labalist.add(o.getLaba());
			}
			List<LabaVo> labavolist = LabaVo.createVoList(labalist, this
					.getLabaParserCfgWeb4(req));
			req.setAttribute("labavolist", labavolist);
		}
	}

	private void list_city(HkRequest req) {
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(20);
		int ipCityId = req.getInt("ipCityId");
		if (ipCityId == 0) {
			IpCityRange range = this.ipCityService.getIpCityRange(req
					.getRemoteAddr());
			if (range != null) {
				ipCityId = range.getCityId();
			}
			if (ipCityId == 0) {
				if (loginUser != null) {
					User user = this.userService.getUser(loginUser.getUserId());
					IpZoneInfo ipZoneInfo = new IpZoneInfo(user.getPcityId());
					ipCityId = ipZoneInfo.getIpCityId();
				}
			}
		}
		List<IpCityLaba> list = labaService.getIpCityLabaList(ipCityId, page
				.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		List<Laba> labalist = new ArrayList<Laba>();
		for (IpCityLaba o : list) {
			labalist.add(o.getLaba());
		}
		List<LabaVo> labavolist = LabaVo.createVoList(labalist, this
				.getLabaParserCfgWeb4(req));
		req.setAttribute("labavolist", labavolist);
	}

	private void list_ip(HkRequest req) {
		SimplePage page = req.getSimplePage(20);
		List<Laba> list = labaService.getIpLabaList(req.getRemoteAddr(), page
				.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		List<LabaVo> labavolist = LabaVo.createVoList(list, this
				.getLabaParserCfgWeb4(req));
		req.setAttribute("labavolist", labavolist);
	}

	private void list_all(HkRequest req) {
		SimplePage page = req.getSimplePage(20);
		List<Laba> list = labaService.getLabaList(page.getBegin(), page
				.getSize() + 1);
		this.processListForPage(page, list);
		List<LabaVo> labavolist = LabaVo.createVoList(list, this
				.getLabaParserCfgWeb4(req));
		req.setAttribute("labavolist", labavolist);
	}

	/**
	 * 搜索喇叭内容
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String s(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("sfrom");
		String sw = req.getString("sw", "");
		sw = DataUtil.getSearchValue(sw);
		if (isEmpty(sw)) {
			return "r:/laba_list.do";
		}
		SimplePage page = req.getSimplePage(20);
		List<Laba> list = labaService.getLabaListForSearch(sw, page.getBegin(),
				20 + 1);
		this.processListForPage(page, list);
		List<LabaVo> labavolist = LabaVo.createVoList(list, this
				.getLabaParserCfg(req));
		req.setAttribute("labavolist", labavolist);
		req.setEncodeAttribute("sw", sw);
		return this.getWeb3Jsp("laba/search.jsp");
	}

	/**
	 * 搜索喇叭内容
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String webs(HkRequest req, HkResponse resp) throws Exception {
		String sw = req.getString("sw", "");
		sw = DataUtil.getSearchValue(sw);
		if (isEmpty(sw)) {
			return "r:";
		}
		SimplePage page = req.getSimplePage(20);
		List<Laba> list = labaService.getLabaListForSearch(sw, page.getBegin(),
				20 + 1);
		this.processListForPage(page, list);
		List<LabaVo> labavolist = LabaVo.createVoList(list, this
				.getLabaParserCfg(req));
		req.setAttribute("labavolist", labavolist);
		req.setEncodeAttribute("sw", sw);
		return this.getWeb4Jsp("/laba/search.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String userlaba(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		if (userId == 0) {
			return null;
		}
		req.setAttribute("userId", userId);
		SimplePage page = req.getSimplePage(20);
		List<Laba> list = labaService.getLabaListByUserId(userId, page
				.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		List<LabaVo> labavolist = LabaVo.createVoList(list, this
				.getLabaParserCfgWeb(req));
		req.setAttribute("labavolist", labavolist);
		req.setAttribute("labavo_not_show_head", true);// 不显示头像
		req.setAttribute("laba_url_add", "from=userlaba&ouserId=" + userId
				+ "&repage=" + page.getPage());
		User user = userService.getUser(userId);
		req.setAttribute("user", user);
		return this.getWeb3Jsp("laba/userlabalist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String userlaba4(HkRequest req, HkResponse resp) {
		long userId = req.getLongAndSetAttr("userId");
		if (userId == 0) {
			return null;
		}
		User user = userService.getUser(userId);
		req.setAttribute("user", user);
		SimplePage page = req.getSimplePage(20);
		List<Laba> list = labaService.getLabaListByUserId(userId, page
				.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		List<LabaVo> labavolist = LabaVo.createVoList(list, this
				.getLabaParserCfgWeb4(req));
		req.setAttribute("labavolist", labavolist);
		return this.getWeb4Jsp("laba/userlaba.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String list4(HkRequest req, HkResponse resp) {
		req.setAttribute("to_laba", true);
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			req.setAttribute("userId", loginUser.getUserId());
		}
		String w = req.getStringAndSetAttr("w");
		if (w == null) {
			if (loginUser != null) {
				this.list_follow(req);
			}
			else {
				w = "all";
			}
		}
		else if (w.equals("follow")) {
			this.list_follow(req);
		}
		else if (w.equals("range")) {
			this.list_range(req);
		}
		else if (w.equals("ip")) {
			this.list_ip(req);
		}
		else if (w.equals("city")) {
			this.list_city(req);
		}
		else if (w.equals("all")) {
			this.list_all(req);
		}
		if (req.getInt("inc") == 1) {
			return this.getWeb4Jsp("laba/labacontent_inc.jsp");
		}
		return this.getWeb4Jsp("laba/labalist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String fav(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		if (userId == 0) {
			return null;
		}
		req.setAttribute("userId", userId);
		SimplePage page = req.getSimplePage(20);
		List<FavLaba> list = this.labaService.getFavLabaListByUserId(userId,
				page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		List<Long> idList = new ArrayList<Long>();
		for (FavLaba f : list) {
			idList.add(f.getLabaId());
		}
		List<Laba> labalist = this.labaService.getLabaListInId(idList);
		List<LabaVo> labavolist = LabaVo.createVoList(labalist, this
				.getLabaParserCfgWeb(req));
		req.setAttribute("labavolist", labavolist);
		User user = userService.getUser(userId);
		req.setAttribute("user", user);
		return this.getWeb3Jsp("laba/favlist.jsp");
	}
}