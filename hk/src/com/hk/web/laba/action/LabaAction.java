package com.hk.web.laba.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Bomber;
import com.hk.bean.Laba;
import com.hk.bean.LabaCmt;
import com.hk.bean.LabaTag;
import com.hk.bean.PinkLaba;
import com.hk.bean.RefLaba;
import com.hk.bean.Tag;
import com.hk.bean.UrlInfo;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BombService;
import com.hk.svr.FollowService;
import com.hk.svr.LabaService;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.LabaParserCfg;
import com.hk.web.pub.action.LabaVo;

@Component("/laba/laba")
public class LabaAction extends BaseAction {
	@Autowired
	private BombService bombService;

	@Autowired
	private LabaService labaService;

	@Autowired
	private FollowService followService;

	/**
	 * 喇叭单页
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.tore(req, resp);
	}

	/**
	 * 到回复页面(喇叭单页),如果是转的喇叭，就到被转发的喇叭页面 如果是引用回复别人喇叭，点击时间到被引用的喇叭页面，点击回应到当前喇叭页面
	 * 如果是引用回复自己的喇叭，都到当前喇叭页面, 判断通知是否是当前查看喇叭,如果是就把通知置为已读
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tore(HkRequest req, HkResponse resp) throws Exception {
		String queryString = req.getQueryString();
		req.setAttribute("queryString", queryString);
		UrlInfo urlInfo = this.getUrlInfo(req);
		long labaId = req.getLongAndSetAttr("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			req.setSessionText("func.laba_not_found");
			return "r:/square.do";
		}
		this.checkNoticeLaba(req);
		User loginUser = this.getLoginUser(req);
		int sip = req.getInt("sip");// 是否查看主喇叭
		if (sip == 0 && laba.getMainLabaId() > 0) {
			Laba mainlaba = labaService.getLaba(laba.getMainLabaId());// 主喇叭
			if (mainlaba != null) {
				if (loginUser != null) {// 如果源头喇叭是自己的，就到老的单页面
					if (mainlaba.getUserId() == loginUser.getUserId()) {
						return "r:/laba/laba.do?labaId=" + labaId + "&sip=1";
					}
				}
				return "r:/laba/laba.do?labaId=" + laba.getMainLabaId();
			}
		}
		if (loginUser != null) {
			if (this.followService.getBlockUser(laba.getUserId(), loginUser
					.getUserId()) != null) {
				req.setSessionText("func.laba.cannotreplyforblockyou");
				return "r:/square.do";
			}
		}
		if (laba.getRefLabaId() > 0) {// 如果是转发的喇叭，就到被转发的喇叭页面
			Laba rlaba = this.labaService.getLaba(laba.getRefLabaId());
			if (rlaba != null) {
				return "r:/laba/laba.do?labaId=" + laba.getRefLabaId();
			}
		}
		List<Tag> taglist2 = this.labaService.getTagList(labaId,
				LabaTag.ACCESSIONAL_Y);
		req.setAttribute("taglist2", taglist2);
		LabaParserCfg cfg = this.getLabaParserCfg(req);
		cfg.setParseLongContent(true);
		cfg.setUrlInfo(urlInfo);
		LabaVo labaVo = LabaVo.create(laba, cfg);
		if (laba.getSendFrom() < 100) {
			labaVo.setSendFromData(req.getText("laba.sendfrom.data."
					+ laba.getSendFrom()));
		}
		List<RefLaba> reflabalist = this.labaService.getRefLabaList(labaId, 0,
				11);
		boolean morerefuser = false;
		if (reflabalist.size() == 11) {
			morerefuser = true;
			reflabalist.remove(10);
		}
		req.setAttribute("morerefuser", morerefuser);
		req.setAttribute("reflabalist", reflabalist);
		req.setAttribute("labaVo", labaVo);
		req.setAttribute("labaId", labaId);
		req.reSetAttribute("olabaId");
		req.reSetAttribute("infoId");
		req.reSetAttribute("companyId");
		req.reSetEncodeAttribute("sw");
		req.reSetAttribute("ouserId");
		req.reSetAttribute("w");
		req.reSetAttribute("tagId");
		req.reSetAttribute("relabaId");
		req.reSetAttribute("repage");
		req.reSetAttribute("from");
		req.reSetAttribute("ref");
		req.reSetAttribute("ipCityId");
		SimplePage page = req.getSimplePage(20);
		List<LabaCmt> labacmtlist = this.labaService.getLabaCmtListByLabaId(
				labaId, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, labacmtlist);
		List<LabaCmtVo> labacmtvolist = LabaCmtVo.createLabaCmtVoList(
				labacmtlist, urlInfo);
		req.setAttribute("labacmtvolist", labacmtvolist);
		if (loginUser != null) {
			Bomber bomber = this.bombService.getBomber(loginUser.getUserId());
			req.setAttribute("bomber", bomber);// 是否持有炸弹
		}
		// 查看是否是精华喇叭
		PinkLaba pinkLaba = this.labaService.getPinkLaba(labaId);
		if (pinkLaba != null) {
			req.setAttribute("pink", true);
		}
		req.reSetAttribute("sip");
		int cp = req.getInt("cp");// 复制内容到输入框中进行普通回复
		if (cp == 1) {
			LabaVo labavo2 = LabaVo.create(laba, null);
			req.setAttribute("labavo2", labavo2);
			req.setAttribute("cp", cp);
		}
		return "/WEB-INF/page/laba/laba_reply.jsp";
	}

	/**
	 * 转载喇叭的火友
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String refuser(HkRequest req, HkResponse resp) throws Exception {
		long labaId = req.getLong("labaId");
		SimplePage page = req.getSimplePage(10);
		List<RefLaba> reflabalist = this.labaService.getRefLabaList(labaId,
				page.getBegin(), 10);
		req.setAttribute("labaId", labaId);
		req.reSetAttribute("olabaId");
		req.setAttribute("reflabalist", reflabalist);
		return "/WEB-INF/page/laba/refuser.jsp";
	}
}