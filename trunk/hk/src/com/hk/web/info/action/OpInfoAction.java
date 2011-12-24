package com.hk.web.info.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.HkbLog;
import com.hk.bean.Information;
import com.hk.bean.Laba;
import com.hk.bean.LabaTag;
import com.hk.bean.Tag;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.InformationService;
import com.hk.svr.LabaService;
import com.hk.svr.TagService;
import com.hk.svr.UserService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.user.exception.NoSmsPortException;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebConfig;

@Component("/info/op/info")
public class OpInfoAction extends BaseAction {
	@Autowired
	private UserService userService;

	@Autowired
	private InformationService informationService;

	@Autowired
	private TagService tagService;

	@Autowired
	private LabaService labaService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		List<Information> list = this.informationService.getInformationList(
				loginUser.getUserId(), 0, 1);
		if (list.size() > 0) {// 开启的信息台列表
			return "r:/info/info.do?userId=" + loginUser.getUserId();
		}
		return "r:/info/op/info_toadd.do";// 如果没有开启,就到开启页面
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toedit(HkRequest req, HkResponse resp) {
		long infoId = req.getLong("infoId");
		Information o = (Information) req.getAttribute("o");
		if (o == null) {
			o = this.informationService.getInformation(infoId);
		}
		req.setAttribute("o", o);
		req.setAttribute("infoId", infoId);
		return "/WEB-INF/page/info/edit.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String edit(HkRequest req, HkResponse resp) {
		long infoId = req.getLong("infoId");
		User loginUser = this.getLoginUser(req);
		String name = req.getString("name");
		String intro = req.getString("intro");
		String tag = req.getString("tag");
		Information o = this.informationService.getInformation(infoId);
		if (o.getUserId() != loginUser.getUserId()) {
			return null;
		}
		o.setName(DataUtil.toHtmlRow(name));
		o.setIntro(DataUtil.toHtml(intro));
		o.setTag(DataUtil.toHtmlRow(tag));
		int code = o.validate();
		req.setAttribute("o", o);
		if (code != Err.SUCCESS) {
			req.setMessage(req.getText(code + ""));
			return "/info/op/info_toedit.do";
		}
		this.informationService.updateInformation(o);
		req.setSessionMessage(req.getText("op.exeok"));
		return "r:/info/info.do?userId=" + loginUser.getUserId();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createlaba(HkRequest req, HkResponse resp) {
		long infoId = req.getLong("infoId");
		User loginUser = this.getLoginUser(req);
		String content = req.getString("content");
		// content = DataUtil.toHtmlRow(content);
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			req.setMessage(req.getText(code + ""));
			return "/info/info_view.do";
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setIp(req.getRemoteAddr());
		labaInfo.setUserId(loginUser.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_WAP);
		Information info = this.informationService.getInformation(infoId);
		long labaId = this.labaService.createLaba(labaInfo);
		// 自动外挂临时频道，增加者为信息台主人，这样主人可以随时维护
		Tag tag = this.tagService.createTag(info.getTag());// 创建喇叭频道
		this.labaService.addTagForLaba(labaId, info.getUserId(),
				tag.getTagId(), LabaTag.ACCESSIONAL_Y);// 添加频道
		return "r:/info/info_view.do?infoId=" + infoId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toadd(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		if (!info.isMobileAlreadyBind()) {
			req.setSessionMessage(req.getText("func.mobilenotbind"));
			return "r:/more.do";
		}
		int hkb_t1 = HkbConfig.getOpeninfo() * 1;
		int hkb_t3 = HkbConfig.getOpeninfo() * 3;
		int hkb_t6 = HkbConfig.getOpeninfo() * 6;
		int hkb_t12 = HkbConfig.getOpeninfo() * 12;
		req.setAttribute("hkb_t1", hkb_t1);
		req.setAttribute("hkb_t3", hkb_t3);
		req.setAttribute("hkb_t6", hkb_t6);
		req.setAttribute("hkb_t12", hkb_t12);
		req.setAttribute("nowhkb", info.getHkb());
		return "/WEB-INF/page/info/add.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String add(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		if (!info.isMobileAlreadyBind()) {
			req.setSessionMessage(req.getText("func.mobilenotbind"));
			return "r:/more.do";
		}
		if (info.getHkb() < HkbConfig.getOpeninfo()) {// 没有足够的hkb开启信息台
			req.setSessionMessage(req.getText("func.noenoughhkb"));
			return "r:/more.do";
		}
		String name = req.getString("name");
		String intro = req.getString("intro");
		String tag = req.getString("tag");
		int t = req.getInt("t");
		req.setAttribute("t", t);
		if (t <= 0) {// 月份
			t = 1;
		}
		// TAG使用自增id
		Information o = new Information();
		o.setTag(DataUtil.toHtmlRow(tag));
		o.setName(DataUtil.toHtmlRow(name));
		o.setIntro(DataUtil.toHtml(intro));
		o.setUserId(loginUser.getUserId());
		int code = o.validate();
		req.setAttribute("o", o);
		if (info.getHkb() < HkbConfig.getOpeninfo() * t) {// 没有足够的hkb开启信息台
			req.setSessionMessage(req.getText("func.noenoughhkb"));
			return "/info/op/info_toadd.do";
		}
		if (code != Err.SUCCESS) {
			req.setMessage(req.getText(code + ""));
			return "/info/op/info_toadd.do";
		}
		req.setSessionMessage(req.getText("op.exeok"));
		try {
			this.informationService.createInformation(o, t);
			HkbLog hkbLog = HkbLog.create(loginUser.getUserId(),
					HkbConfig.CREATEINFORMATION, o.getInfoId(), -HkbConfig
							.getOpeninfo()
							* t);
			// 开启,扣除hkb
			this.userService.addHkb(hkbLog);
		}
		catch (NoSmsPortException e) {
			req.setMessage(req.getText("func.noavailablesmsport"));
			return "/info/op/info_toadd.do";
		}
		return "r:/info/info_view.do?infoId=" + o.getInfoId();
	}

	/**
	 * 设置信息台状态(开启,停止)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String chgstatus(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long infoId = req.getLong("infoId");
		Information information = this.informationService
				.getInformation(infoId);
		if (information == null
				|| information.getUserId() != loginUser.getUserId()) {
			return null;
		}
		byte stat = req.getByte("stat");
		this.informationService.changeUseStatus(infoId, stat);
		req.setSessionMessage(req.getText("op.exeok"));
		String r_url = req.getString("r_url");
		if (!DataUtil.isEmpty(r_url)) {
			return "r:" + r_url;
		}
		return "r:/info/info.do";
	}
}