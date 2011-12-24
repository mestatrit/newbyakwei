package com.hk.web.info.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.InfoSmsPort;
import com.hk.bean.Information;
import com.hk.bean.Laba;
import com.hk.bean.Tag;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.SmsClient;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.InfoSmsPortService;
import com.hk.svr.InformationService;
import com.hk.svr.LabaService;
import com.hk.svr.TagService;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.LabaVo;

@Component("/info/info")
public class InfoAction extends BaseAction {
	@Autowired
	private InformationService informationService;

	@Autowired
	private UserService userService;

	private int size = 20;

	@Autowired
	private InfoSmsPortService infoSmsPortService;

	@Autowired
	private TagService tagService;

	@Autowired
	private SmsClient smsClient;

	@Autowired
	private LabaService labaService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long userId = req.getLong("userId");
		if (userId == 0) {
			if (loginUser != null) {
				userId = loginUser.getUserId();
			}
		}
		if (userId == 0) {
			return null;
		}
		if (loginUser != null && loginUser.getUserId() == userId) {
			List<Information> list = this.informationService
					.getInformationList(userId, 0, 1);// 如果用户没有信息台，就到创建页面
			if (list.size() == 0) {
				return "r:/info/op/info_toadd.do";
			}
		}
		SimplePage page = req.getSimplePage(size);
		List<Information> list = this.informationService.getInformationList(
				userId, page.getBegin(), size);
		List<InformationVo> infovolist = new ArrayList<InformationVo>();
		for (Information o : list) {
			InformationVo vo = new InformationVo(o);
			vo.setUseStatusData(req.getText("information.usestatus_"
					+ o.getUseStatus()));
			infovolist.add(vo);
		}
		page.setListSize(list.size());
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		req.setAttribute("infovolist", infovolist);
		req.setAttribute("userId", userId);
		return "/WEB-INF/page/info/list.jsp";
	}

	/**
	 * 查看信息台
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String view(HkRequest req, HkResponse resp) throws Exception {
		long infoId = req.getInt("infoId");
		Information info = this.informationService.getInformation(infoId);
		if (info == null) {
			return null;
		}
		Tag tag = this.tagService.getTagByName(info.getTag());
		if (tag == null) {
			tag = this.tagService.createTag(info.getTag());
		}
		User user = this.userService.getUser(info.getUserId());
		SimplePage page = req.getSimplePage(size);
		List<Laba> labalist = labaService.getInformationLabaList(user
				.getUserId(), tag.getTagId(), page.getBegin(), 15);
		List<Laba> labalist2 = labaService.getInformationUserLabaList(user
				.getUserId(), tag.getTagId(), page.getBegin(), 15);
		labalist.addAll(0, labalist2);
		List<LabaVo> labavolist = LabaVo.createVoList(labalist, this
				.getLabaParserCfg(req));
		req.setAttribute("labavolist", labavolist);
		req.setAttribute("infoId", infoId);
		req.setAttribute("user", user);
		req.setAttribute("info", info);
		InfoSmsPort infoSmsPort = this.infoSmsPortService.getInfoSmsPort(info
				.getPortId());
		String port = infoSmsPort.getPortNumber();
		SmsPortProcessAble smsPortProcessAble = (SmsPortProcessAble) HkUtil
				.getBean("userinfomation_smsport");
		String smsNumber = this.smsClient.getSmsConfig().getSpNumber()
				+ smsPortProcessAble.getBaseSmsPort() + port;
		req.setAttribute("smsNumber", smsNumber);
		return "/WEB-INF/page/info/view.jsp";
	}
}