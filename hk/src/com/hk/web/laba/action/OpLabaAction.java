package com.hk.web.laba.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Bomber;
import com.hk.bean.Company;
import com.hk.bean.FavLabaDelInfo;
import com.hk.bean.Follow;
import com.hk.bean.HkbLog;
import com.hk.bean.Laba;
import com.hk.bean.LabaDelInfo;
import com.hk.bean.LabaTag;
import com.hk.bean.MyUserCard;
import com.hk.bean.PinkLabaDelInfo;
import com.hk.bean.RefLaba;
import com.hk.bean.Tag;
import com.hk.bean.User;
import com.hk.bean.UserCard;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserSmsPort;
import com.hk.frame.util.ContentFilterUtil;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.BombService;
import com.hk.svr.CompanyService;
import com.hk.svr.FollowService;
import com.hk.svr.LabaService;
import com.hk.svr.TagService;
import com.hk.svr.UserCardService;
import com.hk.svr.UserService;
import com.hk.svr.UserSmsPortService;
import com.hk.svr.laba.exception.LabaNotExistException;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.laba.parser.LabaOutPutParser;
import com.hk.svr.laba.validate.LabaValidate;
import com.hk.svr.laba.validate.TagValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.LabaParserCfg;
import com.hk.web.pub.action.LabaVo;
import com.hk.web.util.HkWebConfig;
import com.hk.web.util.HkWebUtil;

@Component("/laba/op/op")
public class OpLabaAction extends BaseAction {

	@Autowired
	private TagService tagService;

	@Autowired
	private LabaService labaService;

	@Autowired
	private ContentFilterUtil contentFilterUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private UserCardService userCardService;

	@Autowired
	private UserSmsPortService userSmsPortService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private FollowService followService;

	@Autowired
	private BombService bombService;

	private final Log log = LogFactory.getLog(OpLabaAction.class);

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tosendsms(HkRequest req, HkResponse resp) {
		long labaId = req.getLong("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return "r:/square.do";
		}
		User loginUser = this.getLoginUser(req);
		if (!this.userService.isMobileAlreadyBind(loginUser.getUserId())) {
			req.setSessionMessage(req.getText("func.mobilenotbind"));
			return "r:/laba/laba.do?labaId=" + labaId;
		}
		if (!this.userService.hasEnoughHkb(loginUser.getUserId(), -HkbConfig
				.getSendSms())) {
			req.setSessionMessage(req.getText("func.noenoughhkb_sendsms"));
			return "r:/laba/laba.do?labaId=" + labaId;
		}
		LabaParserCfg cfg = new LabaParserCfg();
		cfg.setCheckFav(false);
		cfg.setParseContent(true);
		cfg.setParseLongContent(true);
		LabaVo vo = LabaVo.create(laba, cfg);
		req.setAttribute("vo", vo);
		req.setAttribute("labaId", labaId);
		req.reSetAttribute("f");
		/** ****************** 名片查询*************************** */
		int size = 20;
		String key = req.getString("key");
		SimplePage page = req.getSimplePage(size);
		List<MyUserCard> usercardlist = this.userCardService.getMyUserCardList(
				key, loginUser.getUserId(), page.getBegin(), size);
		List<Long> idList = new ArrayList<Long>();
		for (MyUserCard o : usercardlist) {
			idList.add(o.getCardUserId());
		}
		Map<Long, UserCard> map = this.userCardService
				.getUserCardMapInUserId(idList);
		for (MyUserCard o : usercardlist) {
			o.setUserCard(map.get(o.getCardUserId()));
		}
		page.setListSize(usercardlist.size());
		req.setAttribute("list", usercardlist);
		req.setEncodeAttribute("key", key);
		return "/WEB-INF/page/laba/sendsms.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sendsms(HkRequest req, HkResponse resp) {
		long userId = req.getLong("userId");
		long labaId = req.getLong("labaId");
		long senderId = this.getLoginUser(req).getUserId();
		// 验证对方是否认证，是否有号码
		UserOtherInfo info = this.userService.getUserOtherInfo(userId);
		if (info == null) {
			return "r:/square.do";
		}
		// if (!info.isMobileAlreadyBind()) {
		// return "r:/square.do";
		// }
		if (DataUtil.isEmpty(info.getMobile())) {
			return "r:/square.do";
		}
		// 验证对方是否认证，是否有号码
		UserOtherInfo myInfo = this.userService.getUserOtherInfo(senderId);
		if (myInfo == null) {
			return "r:/square.do";
		}
		if (!myInfo.isMobileAlreadyBind()) {
			return "r:/square.do";
		}
		if (DataUtil.isEmpty(myInfo.getMobile())) {
			return "r:/square.do";
		}
		// 查看自己有没有足够的酷币
		if (!this.userService.hasEnoughHkb(senderId, -HkbConfig.getSendSms())) {
			req.setSessionMessage(req.getText("func.noenoughhkb_sendsms"));
			return "r:/laba/laba.do?labaId=" + labaId;
		}
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return "r:/square.do";
		}
		LabaParserCfg cfg = new LabaParserCfg();
		cfg.setCheckFav(false);
		cfg.setParseLongContent(true);
		cfg.setUrlInfo(null);
		LabaVo vo = LabaVo.create(laba, cfg);
		Sms sms = new Sms();
		sms.setMobile(info.getMobile());
		String content = null;
		content = vo.getLongContent();
		if (DataUtil.isEmpty(content)) {
			content = vo.getContent();
		}
		UserSmsPort userSmsPort = this.userSmsPortService
				.getUserSmsPortByUserId(senderId);
		if (userSmsPort == null) {
			req.setSessionMessage(req.getText("func.nousersmsport"));
			return "r:/laba/laba.do?labaId=" + labaId;
		}
		sms.setContent(DataUtil.toTextRow(content));
		SmsPortProcessAble smsPortProcessAble = (SmsPortProcessAble) HkUtil
				.getBean("usersms_smsport");
		sms
				.setPort(smsPortProcessAble.getBaseSmsPort()
						+ userSmsPort.getPort());
		HkbLog hkbLog = HkbLog.create(senderId, HkLog.SEND_LABA_SMS, labaId,
				-HkbConfig.getSendSms());
		HkWebUtil.sendSms(sms, senderId, hkbLog);
		req.setSessionMessage(req.getText("func.sendsmsok"));
		return "r:/laba/laba.do?labaId=" + labaId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toaddtag(HkRequest req, HkResponse resp) throws Exception {
		long labaId = req.getLong("labaId");
		String queryString = req.getQueryString();
		List<Tag> list = this.labaService.getTagList(labaId,
				LabaTag.ACCESSIONAL_Y);
		if (list.size() >= 3) {
			req.setSessionMessage("频道已经达到3个,不能继续添加了");
			return "r:/laba/laba.do?" + queryString;
		}
		req.setAttribute("queryString", queryString);
		req.reSetAttribute("labaId");
		return "/WEB-INF/page/laba/addtag.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addtag(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		String queryString = req.getString("queryString", "");
		String name = req.getString("name");
		if (this.contentFilterUtil.hasFilterString(name)) {
			return "r:/square.do";
		}
		if (TagValidate.validateTagName(name) != Err.SUCCESS) {
			req.setSessionMessage("请输入正确的字符");
			return "/laba/op/op_toaddtag.do?" + queryString;
		}
		long labaId = req.getLong("labaId");
		Tag tag = this.tagService.createTag(name);
		if (!this.labaService.addTagForLaba(labaId, loginUser.getUserId(), tag
				.getTagId(), LabaTag.ACCESSIONAL_Y)) {
			req.setSessionMessage("频道已经存在");
			return "/laba/op/op_toaddtag.do?" + queryString;
		}
		req.setSessionMessage("操作成功");
		return "r:/laba/laba.do?labaId=" + labaId + queryString;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addtagweb(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		String name = req.getString("name");
		if (this.contentFilterUtil.hasFilterString(name)) {
			return "r:/square.do";
		}
		int code = TagValidate.validateTagName(name);
		if (code != Err.SUCCESS) {
			return this.initError(req, code, -1, null, "addta",
					"onaddtagerror", null);
		}
		long labaId = req.getLong("labaId");
		Tag tag = this.tagService.createTag(name);
		if (!this.labaService.addTagForLaba(labaId, loginUser.getUserId(), tag
				.getTagId(), LabaTag.ACCESSIONAL_Y)) {
			return this.initError(req, Err.TAG_ALREADY_EXIST, -1, null,
					"addtag", "onaddtagerror", null);
		}
		return this
				.initSuccess(req, "addag", "onaddtagsuccess", tag.getTagId());
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createtag(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		String name = req.getString("name");
		int code = TagValidate.validateTagName(name);
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "tagerror", null);
		}
		long labaId = req.getLong("labaId");
		int tagCount = this.tagService.countLabaTagByLabaIdAndAccessional(
				labaId, LabaTag.ACCESSIONAL_Y);
		if (tagCount >= 3) {
			return this.onError(req, Err.LABATAG_OUTOF_LIMIT, "tagerror", null);
		}
		Tag tag = this.tagService.createTag(name);
		if (!this.labaService.addTagForLaba(labaId, loginUser.getUserId(), tag
				.getTagId(), LabaTag.ACCESSIONAL_Y)) {
			return this.onError(req, Err.TAG_ALREADY_EXIST, "tagerror", null);
		}
		return this.onSuccess2(req, "tagok", tag.getTagId());
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deltag(HkRequest req, HkResponse resp) throws Exception {
		long labaId = req.getLong("labaId");
		long tagId = req.getLong("otagId");
		User loginUser = this.getLoginUser(req);
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return "r:/square.do";
		}
		if (laba.getUserId() == loginUser.getUserId()) {
			this.labaService.deleteTagForLaba(labaId, tagId);
		}
		else {
			this.labaService.deleteTagForLaba(labaId, tagId, loginUser
					.getUserId());
		}
		return "r:/laba/laba.do?" + req.getQueryString();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deltagweb(HkRequest req, HkResponse resp) throws Exception {
		long labaId = req.getLong("labaId");
		long tagId = req.getLong("tagId");
		User loginUser = this.getLoginUser(req);
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return null;
		}
		if (laba.getUserId() == loginUser.getUserId()) {
			this.labaService.deleteTagForLaba(labaId, tagId);
		}
		else {
			this.labaService.deleteTagForLaba(labaId, tagId, loginUser
					.getUserId());
		}
		return this.initSuccess(req, "deltag", "ondeltagsuccess", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deltag4(HkRequest req, HkResponse resp) throws Exception {
		long labaId = req.getLong("labaId");
		long tagId = req.getLong("tagId");
		User loginUser = this.getLoginUser(req);
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return null;
		}
		if (laba.getUserId() == loginUser.getUserId()) {
			this.labaService.deleteTagForLaba(labaId, tagId);
		}
		else {
			this.labaService.deleteTagForLaba(labaId, tagId, loginUser
					.getUserId());
		}
		return null;
	}

	/**
	 * 转述喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String share(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		String lastUrl = req.getString("lastUrl");
		long labaId = req.getLong("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return "r:" + lastUrl;
		}
		if (laba.getRefLabaId() > 0) {
			laba = this.labaService.getLaba(laba.getRefLabaId());
		}
		// 自己不能转发自己的喇叭
		if (loginUser.getUserId() == laba.getUserId()) {
			req.setSessionText("func.can_not_via_your_laba");
			return "r:/square.do";
		}
		RefLaba refLaba = this.labaService.getRefLaba(laba.getLabaId(),
				loginUser.getUserId());
		if (refLaba != null) {
			req.setSessionMessage(req.getText("func.already_via_laba"));
			return "r:/square.do";
		}
		User user = this.userService.getUser(laba.getUserId());
		LabaOutPutParser labaOutPutParser = new LabaOutPutParser();
		String content = DataUtil.toText(labaOutPutParser.getText(laba
				.getContent()));
		String inputContent = req.getString("content");
		if (inputContent != null
				&& !DataUtil.endWithInterpunction(inputContent)) {
			if (inputContent.endsWith("吗")) {// 自动加标点符号
				inputContent = inputContent + "？";
			}
			else {
				inputContent = inputContent + ". ";
			}
		}
		if (inputContent == null) {
			content = "转@" + user.getNickName() + ": " + content;
		}
		else {
			content = inputContent + HkWebUtil.labarefflg + "@"
					+ laba.getUser().getNickName() + ":" + content;
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setAddLabaTagRef(false);// 转载的喇叭，如果有标签，不把标签加到喇叭标签的关系表
		labaInfo.setRefLabaId(laba.getLabaId());// 转发的喇叭
		labaInfo.setReplyLabaId(laba.getLabaId());// 回应的喇叭
		labaInfo.setIp(req.getRemoteAddr());
		labaInfo.setUserId(loginUser.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_WAP);
		long newLabaId = this.labaService.createLaba(labaInfo);
		this.setOpFuncSuccessMsg(req);
		req.setSessionValue("newLabaId", newLabaId);
		return "r:/square.do";
	}

	/**
	 * 转述喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String refweb(HkRequest req, HkResponse resp) {
		return this.refweb(req, resp, 0);
	}

	public String refweb(HkRequest req, HkResponse resp, int posttype) {
		User loginUser = this.getLoginUser(req);
		long labaId = req.getLong("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		if (laba.getRefLabaId() > 0) {
			laba = this.labaService.getLaba(laba.getRefLabaId());
		}
		// 自己不能转发自己的喇叭
		if (loginUser.getUserId() == laba.getUserId()) {
			return null;
		}
		RefLaba refLaba = this.labaService.getRefLaba(laba.getLabaId(),
				loginUser.getUserId());
		if (refLaba != null) {
			return this.initError(req, Err.LABA_REFLABA_EXIST, -1, null,
					"create", (String) req.getAttribute("errorFunctionName"),
					null);
		}
		User user = this.userService.getUser(laba.getUserId());
		LabaOutPutParser labaOutPutParser = new LabaOutPutParser();
		String content = DataUtil.toText(labaOutPutParser.getText(laba
				.getContent()));
		content = "转@" + user.getNickName() + ": " + content;
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setAddLabaTagRef(false);// 转载的喇叭，如果有标签，不把标签加到喇叭标签的关系表
		labaInfo.setRefLabaId(laba.getLabaId());// 转发的喇叭
		labaInfo.setIp(req.getRemoteAddr());
		labaInfo.setUserId(loginUser.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_WAP);
		long newLabaId = this.labaService.createLaba(labaInfo);
		req.setAttribute("newLabaId", newLabaId);
		if (posttype == 0) {
			resp.sendHtml(newLabaId);
			return null;
		}
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "create", (String) req
				.getAttribute("successFunctionName"), newLabaId);
	}

	/**
	 * 吹喇叭,在喇叭单页面，如果内容为空，就为转发喇叭，喇叭内容最多500字
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String create(HkRequest req, HkResponse resp) {
		// if (req.getInt("nova") == 0) {
		// if (!this.isTokenValid(req)) {
		// return "r:/square.do";
		// }
		// this.clearToken(req);
		// }
		if (req.getString("msg_submit") != null
				|| req.getString("msgandsms_submit") != null) {
			long userId = req.getLong("userId");
			return "/msg/send.do?receiverId=" + userId + "&fromlaba=1";
		}
		String lastUrl = req.getString("lastUrl");
		if (DataUtil.isEmpty(lastUrl)) {
			lastUrl = "/square.do";
		}
		if (req.getString("cmt") != null) {
			return "/op/labacmt_create.do";
		}
		if (req.getString("rt") != null) {
			return "/laba/op/op_share.do?ref=1";
		}
		User loginUser = this.getLoginUser(req);
		String content = req.getString("content");
		// if (req.getString("via") != null) {// 作为转发操作
		// return "/laba/op/op_share.do?ref=1";
		// }
		// req.setSessionValue("input_content", content);
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			req.setSessionMessage(req.getText(code + ""));
			return "r:" + lastUrl;
		}
		boolean lesschar = false;
		if (content.length() < 8) {
			lesschar = true;
		}
		if (this.contentFilterUtil.hasFilterString(content)) {
			return "r:/square.do";
		}
		// long userId = req.getLong("userId");
		long labaId = req.getLong("labaId");
		Laba laba = null;
		if (labaId > 0) {
			laba = this.labaService.getLaba(labaId);
			if (laba == null) {
				return "r:/square.do";
			}
		}
		if (req.getInt("noparsecmp") == 0) {
			List<String> cmpnamelist = LabaInPutParser
					.parseCompanyName(content);
			if (cmpnamelist.size() > 0) {// 查找关联足迹
				return "r:/laba/op/op_selcmp.do?content="
						+ DataUtil.urlEncoder(content) + "&name="
						+ DataUtil.urlEncoder(cmpnamelist.iterator().next());
			}
		}
		long newLabaId = 0;
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		if (labaInfo.isEmptyContent()) {
			req.setSessionText(Err.LABA_CONTENT_ERROR + "");
			return "r:" + lastUrl;
		}
		if (labaId > 0) {
			Laba laba2 = this.labaService.getLaba(labaId);
			if (laba2 != null && laba2.getUserId() == loginUser.getUserId()) {
				req.setSessionText("func.can_not_via_your_laba");
				return "r:/square.do";
			}
			if (laba2 == null) {
				req.setSessionText("func.laba_not_found");
				return "r:/square.do";
			}
			labaInfo.setReplyLabaId(labaId);
		}
		labaInfo.setIp(req.getRemoteAddr());
		labaInfo.setUserId(loginUser.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_WAP);
		newLabaId = this.labaService.createLaba(labaInfo);
		req.setSessionValue("lesschar", lesschar);
		req.setSessionMessage("吹喇叭成功");
		if (newLabaId > 0) {
			req.setSessionValue("newLabaId", newLabaId);
		}
		int channel = req.getInt("channel");
		long tagId = req.getLong("tagId");
		if (channel == 1) {
			this.labaService.addTagForLaba(newLabaId, loginUser.getUserId(),
					tagId, LabaTag.ACCESSIONAL_Y);
			return "r:/laba/taglabalist.do?tagId=" + tagId;
		}
		String return_url = req.getString("return_url");
		if (!DataUtil.isEmpty(return_url)) {
			return "r:" + return_url;
		}
		return "r:/square.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String replylaba(HkRequest req, HkResponse resp) {
		String content = req.getString("content");
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			// return this.initError(req, code, "create");
			return this.initError(req, code, -1, null, "create", (String) req
					.getAttribute("errorFunctionName"), null);
		}
		if (this.contentFilterUtil.hasFilterString(content)) {
			return null;
		}
		long labaId = req.getLong("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return null;
		}
		User loginUser = this.getLoginUser(req);
		if (laba.getUserId() == loginUser.getUserId()) {
			return null;
		}
		User user = this.userService.getUser(laba.getUserId());
		String n = "@" + user.getNickName();
		if (content.indexOf(n) == -1) {// 如果回复中不带有昵称,就加上
			content = n + " " + content;
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		if (labaInfo.isEmptyContent()) {
			return null;
		}
		labaInfo.setReplyLabaId(labaId);
		labaInfo.setIp(req.getRemoteAddr());
		labaInfo.setUserId(loginUser.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_WAP);
		long newLabaId = this.labaService.createLaba(labaInfo);
		// return this.onSuccess(req, newLabaId + "", "create");
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "create", (String) req
				.getAttribute("successFunctionName"), newLabaId);
	}

	/**
	 * 发新喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String createnewlaba(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		String content = req.getString("content");
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createlabaerror", null);
		}
		if (this.contentFilterUtil.hasFilterString(content)) {
			return null;
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		if (labaInfo.isEmptyContent()) {
			return null;
		}
		labaInfo.setIp(req.getRemoteAddr());
		labaInfo.setUserId(loginUser.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_WEB);
		this.labaService.createLaba(labaInfo);
		return this.onSuccess2(req, "createlabaok", null);
	}

	/**
	 * 发新喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String create4(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		String content = req.getString("content");
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createlabaerror", null);
		}
		if (this.contentFilterUtil.hasFilterString(content)) {
			return null;
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		if (labaInfo.isEmptyContent()) {
			return null;
		}
		labaInfo.setIp(req.getRemoteAddr());
		labaInfo.setUserId(loginUser.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_WEB);
		if (!req.isTokenValid()) {
			return this.onSuccess2(req, "createlabaok", null);
		}
		// token验证通过,设置新tooken
		req.saveToken();
		this.labaService.createLaba(labaInfo);
		return this.onSuccess2(req, "createlabaok", null);
	}

	/**
	 * 删除以引用的喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delref(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long labaId = req.getLong("labaId");
		List<Laba> list = this.labaService.getLabaListByRefLabaIdAndUserId(
				labaId, loginUser.getUserId());
		if (list.size() > 0) {
			return "r:/laba/op/op_del.do?labaId="
					+ list.iterator().next().getLabaId();
		}
		return "r:/square.do";
	}

	/**
	 * 删除以引用的喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delrefweb(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long labaId = req.getLong("labaId");
		List<Laba> list = this.labaService.getLabaListByRefLabaIdAndUserId(
				labaId, loginUser.getUserId());
		if (list.size() > 0) {
			this.dellaba(list.iterator().next().getLabaId(), loginUser);
		}
		return null;
	}

	/**
	 * 删除自己发送的喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long labaId = req.getLong("labaId");
		LabaDelInfo labaDelInfo = this.dellaba(labaId, loginUser);
		if (labaDelInfo != null) {
			req.setSessionMessage("删除了一个喇叭.");
			this.setDelInfo(req, labaDelInfo);
		}
		return "/laba/back.do";
	}

	private LabaDelInfo dellaba(long labaId, User loginUser) {
		if (labaId <= 0) {
			return null;
		}
		Laba laba = this.labaService.getLaba(labaId);
		if (laba != null && laba.getUserId() == loginUser.getUserId()) {
			return labaService.removeLaba(loginUser.getUserId(), labaId, false);
		}
		return null;
	}

	/**
	 * 删除自己发送的喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delweb(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long labaId = req.getLong("labaId");
		if (labaId == 0) {
			Long l = (Long) req.getAttribute("labaId");
			if (l != null) {
				labaId = l;
			}
		}
		if (labaId <= 0) {
			return null;
		}
		this.dellaba(labaId, loginUser);
		return null;
	}

	/**
	 * 收藏喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String fav(HkRequest req, HkResponse resp) {
		if (req.getString("addtag") != null) {
			return "/laba/op/op_addtag.do";
		}
		long labaId = req.getLong("labaId");
		long userId = this.getLoginUser(req).getUserId();
		try {
			LabaValidate.validateLaba(labaId);
			this.labaService.collectLaba(userId, labaId);
			req.setSessionMessage("收藏成功");
		}
		catch (LabaNotExistException e) {
			log.warn(e.getMessage());
		}
		return "/laba/back.do";
	}

	/**
	 * 收藏喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String favweb(HkRequest req, HkResponse resp) {
		long labaId = req.getLong("labaId");
		long userId = this.getLoginUser(req).getUserId();
		if (this.labaService.getLaba(labaId) == null) {
			return null;
		}
		this.labaService.collectLaba(userId, labaId);
		return null;
	}

	/**
	 * 删除收藏
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delfav(HkRequest req, HkResponse resp) throws Exception {
		if (req.getString("addtag") != null) {
			return "/laba/op/op_addtag.do";
		}
		long labaId = req.getLong("labaId");
		this.labaService.delCollectLaba(this.getLoginUser(req).getUserId(),
				labaId);
		req.setSessionMessage("从收藏中删除成功");
		FavLabaDelInfo info = new FavLabaDelInfo();
		info.setLabaId(labaId);
		this.setDelInfo(req, info);
		return "/laba/back.do";
	}

	/**
	 * 删除收藏
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delfavweb(HkRequest req, HkResponse resp) throws Exception {
		long labaId = req.getLong("labaId");
		this.labaService.delCollectLaba(this.getLoginUser(req).getUserId(),
				labaId);
		return null;
	}

	/**
	 * 使用炸弹炸喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String bomblaba(HkRequest req, HkResponse resp) {
		if (!this.isBomber(req)) {
			return null;
		}
		long labaId = req.getLong("labaId");
		if (labaService.getLaba(labaId) == null) {
			return "r:/square.do";
		}
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return "r:/square.do";
		}
		User user = this.getLoginUser(req);
		Bomber bomber = this.bombService.getBomber(user.getUserId());
		if (bomber.getUserLevel() != Bomber.USERLEVEL_SUPERADMIN
				&& bomber.getRemainCount() < 1) {
			req.setMessage("炸弹数量不够");
			return "/laba/back.do";
		}
		LabaDelInfo labaDelInfo = labaService.removeLaba(user.getUserId(),
				labaId, true);// 喇叭被炸掉，减少喇叭创建人的积分
		if (labaDelInfo != null) {
			req.setSessionMessage("删除了一个喇叭.");
			this.setDelInfo(req, labaDelInfo);
		}
		return "/laba/back.do";
	}

	/**
	 * 使用炸弹炸喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String bomblaba4(HkRequest req, HkResponse resp) {
		if (!this.isBomber(req)) {
			return null;
		}
		long labaId = req.getLong("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return null;
		}
		User user = this.getLoginUser(req);
		Bomber bomber = this.bombService.getBomber(user.getUserId());
		if (bomber.getUserLevel() != Bomber.USERLEVEL_SUPERADMIN
				&& bomber.getRemainCount() < 1) {
			req.setMessage("炸弹数量不够");
			resp.sendHtml(1);
			return null;
		}
		labaService.removeLaba(user.getUserId(), labaId, true);
		return null;
	}

	/**
	 * 发送喇叭给自己
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tosendfromhome(HkRequest req, HkResponse resp)
			throws Exception {
		long userId = req.getLong("userId");
		User user = this.userService.getUser(userId);
		if (user == null) {
			return null;
		}
		boolean canSendMsg = false;
		User loginUser = this.getLoginUser(req);
		Follow follow = this.followService.getFollow(loginUser.getUserId(),
				userId);
		if (follow != null && follow.getBothFollow() == Follow.FOLLOW_BOOTH) {
			canSendMsg = true;
		}
		String queryString = req.getQueryString();
		req.setAttribute("queryString", queryString);
		req.setAttribute("canSendMsg", canSendMsg);
		req.reSetAttribute("userId");
		req.setAttribute("nickName", user.getNickName());
		req.reSetAttribute("from");
		req.reSetAttribute("repage");
		req.reSetAttribute("ouserId");
		return "/WEB-INF/page/laba/tosendfromhome.jsp";
	}

	/**
	 * 把喇叭加入精华
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String pinklaba(HkRequest req, HkResponse resp) {
		if (!this.isBomber(req)) {
			return null;
		}
		long labaId = req.getLong("labaId");
		User user = this.getLoginUser(req);
		Bomber bomber = this.bombService.getBomber(user.getUserId());
		if (bomber.getUserLevel() == Bomber.USERLEVEL_SUPERADMIN
				|| bomber.getRemainPinkCount() > 0) {
			this.bombService.pinkLaba(user.getUserId(), labaId);
			if (bomber.getUserLevel() != Bomber.USERLEVEL_SUPERADMIN) {
				this.bombService.addRemainPinkCount(user.getUserId(), -1);
			}
			req.setSessionMessage("推荐精华成功");
			PinkLabaDelInfo info = new PinkLabaDelInfo();
			info.setLabaId(labaId);
			this.setDelInfo(req, info);
		}
		else {
			req.setSessionMessage("精华不足,不能推荐精华");
		}
		return "/laba/back.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String topinklaba(HkRequest req, HkResponse resp) {
		if (!this.isBomber(req)) {
			return null;
		}
		String queryString = req.getQueryString();
		req.setAttribute("queryString", queryString);
		return "/WEB-INF/page/laba/confirm_pinklaba.jsp";
	}

	/**
	 * 确认把喇叭加入精华
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String confirmpinklaba(HkRequest req, HkResponse resp) {
		if (!this.isBomber(req)) {
			return null;
		}
		if (req.getString("ok") != null) {
			return "/laba/op/op_pinklaba.do";
		}
		return "/laba/back.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tobomblaba(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isBomber(req)) {
			return null;
		}
		String queryString = req.getQueryString();
		req.setAttribute("queryString", queryString);
		return "/WEB-INF/page/laba/confirm_bomblaba.jsp";
	}

	private boolean isBomber(HkRequest req) {
		User loginUser = this.getLoginUser(req);
		Bomber bomber = bombService.getBomber(loginUser.getUserId());
		if (bomber == null) {
			return false;
		}
		return true;
	}

	/**
	 * 确认炸掉喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String confirmbomblaba(HkRequest req, HkResponse resp)
			throws Exception {
		if (!this.isBomber(req)) {
			return null;
		}
		if (req.getString("ok") != null) {
			return "/laba/op/op_bomblaba.do";
		}
		return "/laba/back.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String selcmp(HkRequest req, HkResponse resp) {
		String content = req.getString("content");
		String name = req.getString("name", "");
		SimplePage page = req.getSimplePage(20);
		long companyId = req.getLong("companyId");
		if (companyId > 0) {
			Company o = this.companyService.getCompany(companyId);
			if (o != null) {
				String r = DataUtil.getFilterRegValue(name);
				content = content.replaceAll("\\[" + r + "\\]", "\\{\\["
						+ o.getCompanyId() + "," + o.getName() + "\\}");
			}
			List<String> cmpnamelist = LabaInPutParser
					.parseCompanyName(content);
			if (cmpnamelist.size() > 0) {
				return "r:/laba/op/op_selcmp.do?nova=1&content="
						+ DataUtil.urlEncoder(content) + "&name="
						+ DataUtil.urlEncoder(cmpnamelist.iterator().next());
			}
			return "r:/laba/op/op_create.do?nova=1&content="
					+ DataUtil.urlEncoder(content) + "&noparsecmp=1";
		}
		List<Company> list = this.companyService.getCompanyListWithSearch(name,
				page.getBegin(), page.getSize());
		if (list.size() == 0) {
			return "r:/laba/op/op_create.do?nova=1&content="
					+ DataUtil.urlEncoder(content) + "&noparsecmp=1";
		}
		if (list.size() == 1) {
			Company o = list.iterator().next();
			if (o != null) {
				String r = DataUtil.getFilterRegValue(name);
				content = content.replaceAll("\\[" + r + "\\]", "\\{\\["
						+ o.getCompanyId() + "," + o.getName() + "\\}");
			}
			List<String> cmpnamelist = LabaInPutParser
					.parseCompanyName(content);
			if (cmpnamelist.size() > 0) {
				return "r:/laba/op/op_selcmp.do?content="
						+ DataUtil.urlEncoder(content) + "&name="
						+ DataUtil.urlEncoder(cmpnamelist.iterator().next());
			}
			return "r:/laba/op/op_create.do?nova=1&content="
					+ DataUtil.urlEncoder(content) + "&noparsecmp=1";
		}
		req.setAttribute("list", list);
		req.setEncodeAttribute("name", name);
		req.setEncodeAttribute("content", content);
		return this.getWapJsp("laba/selcmp.jsp");
	}
}