package com.hk.web.laba.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Laba;
import com.hk.bean.LabaCmt;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.LabaService;
import com.hk.svr.UserService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.laba.parser.LabaOutPutParser;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebConfig;
import com.hk.web.util.HkWebUtil;

@Component("/op/labacmt")
public class OpLabaCmtAction extends BaseAction {
	@Autowired
	private LabaService labaService;

	@Autowired
	private UserService userService;

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
	public String tocreate(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("labaId");
		long cmtId = req.getLongAndSetAttr("cmtId");
		LabaCmt labaCmt = this.labaService.getLabaCmt(cmtId);
		if (labaCmt == null) {
			return "r:/laba/laba.do?labaId=" + req.getLong("labaId");
		}
		User user = this.userService.getUser(labaCmt.getUserId());
		req.setAttribute("user", user);
		return this.getWapJsp("/laba/createlabacmt.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String share(HkRequest req, HkResponse resp) throws Exception {
		long labaId = req.getLong("labaId");
		long replyCmtId = req.getLong("replyCmtId");
		LabaCmt labaCmt = this.labaService.getLabaCmt(replyCmtId);
		if (labaCmt != null) {
			User user = this.userService.getUser(labaCmt.getUserId());
			String content = "转@" + user.getNickName() + ": "
					+ new LabaOutPutParser().getText(labaCmt.getContent());
			LabaInPutParser parser = new LabaInPutParser(HkWebConfig
					.getShortUrlDomain());
			LabaInfo labaInfo = parser.parse(content);
			labaInfo.setAddLabaTagRef(false);// 转载的喇叭，如果有标签，不把标签加到喇叭标签的关系表
			labaInfo.setIp(req.getRemoteAddr());
			labaInfo.setUserId(this.getLoginUser(req).getUserId());
			labaInfo.setSendFrom(Laba.SENDFROM_WAP);
			this.labaService.createLaba(labaInfo);
			this.setOpFuncSuccessMsg(req);
		}
		return "r:/laba/laba.do?labaId=" + labaId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String shareweb(HkRequest req, HkResponse resp) throws Exception {
		long replyCmtId = req.getLong("replyCmtId");
		LabaCmt labaCmt = this.labaService.getLabaCmt(replyCmtId);
		if (labaCmt != null) {
			User user = this.userService.getUser(labaCmt.getUserId());
			String content = "转@" + user.getNickName() + ": "
					+ new LabaOutPutParser().getText(labaCmt.getContent());
			LabaInPutParser parser = new LabaInPutParser(HkWebConfig
					.getShortUrlDomain());
			LabaInfo labaInfo = parser.parse(content);
			labaInfo.setAddLabaTagRef(false);// 转载的喇叭，如果有标签，不把标签加到喇叭标签的关系表
			labaInfo.setIp(req.getRemoteAddr());
			labaInfo.setUserId(this.getLoginUser(req).getUserId());
			labaInfo.setSendFrom(Laba.SENDFROM_WAP);
			this.labaService.createLaba(labaInfo);
		}
		this.onSuccess2(req, "sharecmtok", null);
		return null;
	}

	public String create(HkRequest req, HkResponse resp) throws Exception {
		if (req.getString("msg_submit") != null
				|| req.getString("msgandsms_submit") != null) {
			long userId = req.getLong("userId");
			return "/msg/send.do?receiverId=" + userId + "&fromlaba=1";
		}
		long replyCmtId = req.getLong("replyCmtId");
		long labaId = req.getLongAndSetAttr("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return this.getNotFoundForward(resp);
		}
		String content = req.getString("content");
		if (DataUtil.isEmpty(content) && req.getString("cmtandlaba") != null) {
			if (replyCmtId == 0) {
				return "/laba/op/op_share.do";
			}
			return "/op/labacmt_share.do";
		}
		LabaCmt replyLabaCmt = null;
		User replyLabaCmtUser = null;
		if (replyCmtId > 0) {
			replyLabaCmt = this.labaService.getLabaCmt(replyCmtId);
			if (replyLabaCmt == null) {
				return "r:/laba/laba.do?labaId=" + labaId;
			}
			replyLabaCmtUser = this.userService.getUser(replyLabaCmt
					.getUserId());
			if (replyLabaCmtUser != null) {
				content = "@" + replyLabaCmtUser.getNickName() + " " + content;
			}
		}
		if (content == null) {
			return "r:/laba/laba.do?labaId=" + labaId;
		}
		User loginUser = this.getLoginUser(req);
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setReplyLabaId(labaId);
		labaInfo.setIp(req.getRemoteAddr());
		labaInfo.setUserId(loginUser.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_WAP);
		LabaCmt labaCmt = new LabaCmt();
		labaCmt.setLabaId(labaId);
		labaCmt.setUserId(loginUser.getUserId());
		labaCmt.setContent(labaInfo.getParsedContent());
		int code = labaCmt.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			req.setAttribute("content", content);
			if (replyCmtId == 0) {
				return "/laba/laba.do";
			}
			return "/op/labacmt_tocreate.do";
		}
		if (replyCmtId > 0 && replyLabaCmtUser != null) {
			labaInfo.setReplyUserId(replyLabaCmtUser.getUserId());
		}
		this.labaService.createLabaCmt(labaCmt, labaInfo);
		if (req.getString("cmtandlaba") != null) {// 顺便转发喇叭
			if (replyCmtId > 0 && replyLabaCmt != null
					&& replyLabaCmtUser != null) {// 转发内容为回复内容
				content = req.getString("content")
						+ HkWebUtil.labarefflg
						+ "@"
						+ replyLabaCmtUser.getNickName()
						+ ":"
						+ new LabaOutPutParser().getText(replyLabaCmt
								.getContent());
				parser = new LabaInPutParser(HkWebConfig.getShortUrlDomain());
				labaInfo = parser.parse(content);
				labaInfo.setReplyLabaId(labaId);
				labaInfo.setIp(req.getRemoteAddr());
				labaInfo.setUserId(loginUser.getUserId());
				labaInfo.setSendFrom(Laba.SENDFROM_WAP);
			}
			else {// 转发内容为喇叭内容
				content = req.getString("content") + HkWebUtil.labarefflg + "@"
						+ laba.getUser().getNickName() + ":"
						+ new LabaOutPutParser().getText(laba.getContent());
				parser = new LabaInPutParser(HkWebConfig.getShortUrlDomain());
				labaInfo = parser.parse(content);
				labaInfo.setReplyLabaId(labaId);
				labaInfo.setIp(req.getRemoteAddr());
				labaInfo.setUserId(loginUser.getUserId());
				labaInfo.setSendFrom(Laba.SENDFROM_WAP);
			}
			this.labaService.createLaba(labaInfo);
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/laba/laba.do?labaId=" + labaId;
	}

	public String createweb(HkRequest req, HkResponse resp) throws Exception {
		long replyCmtId = req.getLong("replyCmtId");
		long labaId = req.getLongAndSetAttr("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return this.getNotFoundForward(resp);
		}
		String content = req.getString("content");
		LabaCmt replyLabaCmt = null;
		User replyLabaCmtUser = null;
		if (replyCmtId > 0) {
			replyLabaCmt = this.labaService.getLabaCmt(replyCmtId);
			if (replyLabaCmt == null) {
				return null;
			}
			replyLabaCmtUser = this.userService.getUser(replyLabaCmt
					.getUserId());
			if (replyLabaCmtUser != null) {
				content = "@" + replyLabaCmtUser.getNickName() + " " + content;
			}
		}
		if (DataUtil.isEmpty(content)) {
			return this.onError(req, Err.LABACMT_CONTENT_ERROR, "labacmterror",
					null);
		}
		User loginUser = this.getLoginUser(req);
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setReplyLabaId(labaId);
		labaInfo.setIp(req.getRemoteAddr());
		labaInfo.setUserId(loginUser.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_WAP);
		LabaCmt labaCmt = new LabaCmt();
		labaCmt.setLabaId(labaId);
		labaCmt.setUserId(loginUser.getUserId());
		labaCmt.setContent(labaInfo.getParsedContent());
		int code = labaCmt.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "labacmterror", null);
		}
		if (replyCmtId > 0 && replyLabaCmtUser != null) {
			labaInfo.setReplyUserId(replyLabaCmtUser.getUserId());
		}
		this.labaService.createLabaCmt(labaCmt, labaInfo);
		if (req.getInt("cmtandlaba") == 1) {// 顺便转发喇叭
			if (replyCmtId > 0 && replyLabaCmt != null
					&& replyLabaCmtUser != null) {// 转发内容为回复内容
				content = req.getString("content")
						+ HkWebUtil.labarefflg
						+ "@"
						+ replyLabaCmtUser.getNickName()
						+ ":"
						+ new LabaOutPutParser().getText(replyLabaCmt
								.getContent());
				parser = new LabaInPutParser(HkWebConfig.getShortUrlDomain());
				labaInfo = parser.parse(content);
				labaInfo.setReplyLabaId(labaId);
				labaInfo.setIp(req.getRemoteAddr());
				labaInfo.setUserId(loginUser.getUserId());
				labaInfo.setSendFrom(Laba.SENDFROM_WAP);
			}
			else {// 转发内容为喇叭内容
				content = req.getString("content") + HkWebUtil.labarefflg + "@"
						+ laba.getUser().getNickName() + ":"
						+ new LabaOutPutParser().getText(laba.getContent());
				parser = new LabaInPutParser(HkWebConfig.getShortUrlDomain());
				labaInfo = parser.parse(content);
				labaInfo.setReplyLabaId(labaId);
				labaInfo.setIp(req.getRemoteAddr());
				labaInfo.setUserId(loginUser.getUserId());
				labaInfo.setSendFrom(Laba.SENDFROM_WAP);
			}
			this.labaService.createLaba(labaInfo);
		}
		labaCmt = this.labaService.getLabaCmt(labaCmt.getCmtId());
		LabaCmtVo labaCmtVo = LabaCmtVo.createLabaCmtVo(labaCmt, this
				.getUrlInfoWeb(req), true);
		req.setAttribute("cmtvo", labaCmtVo);
		return this.getWeb4Jsp("/laba/createok.jsp");
	}

	/**
	 * 删除喇叭评论
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delweb(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long cmtId = req.getLong("cmtId");
		LabaCmt labaCmt = this.labaService.getLabaCmt(cmtId);
		if (labaCmt == null) {
			return null;
		}
		Laba laba = this.labaService.getLaba(labaCmt.getLabaId());
		if (laba == null) {
			return null;
		}
		if (labaCmt.getUserId() == loginUser.getUserId()
				|| laba.getUserId() == loginUser.getUserId()) {
			this.labaService.deleteLabaCmt(cmtId);
		}
		return null;
	}

	/**
	 * 删除喇叭评论
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long cmtId = req.getLong("cmtId");
		long labaId = req.getLong("labaId");
		LabaCmt labaCmt = this.labaService.getLabaCmt(cmtId);
		if (labaCmt == null) {
			return "r:/laba/laba.do?labaId=" + labaId;
		}
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			return "r:/laba/laba.do?labaId=" + labaId;
		}
		if (labaCmt.getUserId() == loginUser.getUserId()
				|| laba.getUserId() == loginUser.getUserId()) {
			this.labaService.deleteLabaCmt(cmtId);
			this.setDelSuccessMsg(req);
		}
		return "r:/laba/laba.do?labaId=" + labaId;
	}
}