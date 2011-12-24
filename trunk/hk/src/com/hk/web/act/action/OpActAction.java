package com.hk.web.act.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Act;
import com.hk.bean.ActUser;
import com.hk.bean.HkbLog;
import com.hk.bean.InfoSmsPort;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserSms;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.BatchSms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.ActService;
import com.hk.svr.InfoSmsPortService;
import com.hk.svr.UserService;
import com.hk.svr.UserSmsService;
import com.hk.svr.act.exception.DuplicateActNameException;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.user.exception.NoAvailableActSysNumException;
import com.hk.svr.user.exception.NoSmsPortException;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebUtil;

@Component("/act/op/act")
public class OpActAction extends BaseAction {
	@Autowired
	private ActService actService;

	@Autowired
	InfoSmsPortService infoSmsPortService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSmsService userSmsService;

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
	public String seluser(HkRequest req, HkResponse resp) throws Exception {
		int size = 20;
		int selok = req.getInt("selok");
		long actId = req.getLong("actId");
		SmsReceiverSet smsReceiverSet = (SmsReceiverSet) req
				.getSessionValue(HkWebUtil.USERIDSET);
		if (selok == 1) {
			if (smsReceiverSet == null) {
				smsReceiverSet = new SmsReceiverSet();
				smsReceiverSet.setActId(actId);
			}
			smsReceiverSet.getUseridSet().clear();
		}
		else {
			if (smsReceiverSet == null) {
				smsReceiverSet = new SmsReceiverSet();
				smsReceiverSet.setActId(actId);
			}
		}
		long userId = req.getLong("userId");
		int del = req.getInt("del");
		if (del == 1) {
			smsReceiverSet.getUseridSet().remove(Long.valueOf(userId));
		}
		else {
			if (userId > 0 && this.actService.getActUser(actId, userId) != null) {
				smsReceiverSet.addUserId(userId);
			}
		}
		String nickNames = req.getString("nickNames");
		if (nickNames != null) {
			nickNames = nickNames.replaceAll("@", "");
			String[] nickName = nickNames.split(" ");
			for (int i = 0; i < nickName.length; i++) {
				User u = this.userService.getUserByNickName(nickName[i]);
				ActUser au = this.actService.getActUser(actId, u.getUserId());
				if (au != null) {
					smsReceiverSet.addUserId(u.getUserId());
				}
			}
		}
		req.setSessionValue(HkWebUtil.USERIDSET, smsReceiverSet);
		if (selok == 1) {// 如果选择完毕，就到发送信息页面
			return "r:/act/op/act_tosendsms.do?actId=" + actId;
		}
		SimplePage page = req.getSimplePage(size);
		List<ActUser> list = this.actService.getActUserList(actId, page
				.getBegin(), size);
		page.setListSize(list.size());
		List<Long> idList = new ArrayList<Long>();
		for (ActUser o : list) {
			idList.add(o.getUserId());
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		List<SelUserVo> volist = new ArrayList<SelUserVo>();
		for (ActUser o : list) {
			o.setUser(map.get(o.getUserId()));
			SelUserVo vo = new SelUserVo();
			vo.setActUser(o);
			vo.setSelected(smsReceiverSet.getUseridSet()
					.contains(o.getUserId()));
			volist.add(vo);
		}
		List<Long> useridlist = new ArrayList<Long>();
		useridlist.addAll(smsReceiverSet.getUseridSet());
		List<User> seluserlist = this.userService.getUserListInId(useridlist);
		StringBuilder sb = new StringBuilder();
		for (User o : seluserlist) {
			sb.append("@").append(o.getNickName()).append(" ");
		}
		req.setAttribute("userdata", sb.toString());
		req.setAttribute("volist", volist);
		req.setAttribute("actId", actId);
		this.checkHkb(req);
		return "/WEB-INF/page/act/op/seluser.jsp";
	}

	private void checkHkb(HkRequest req) {
		SmsReceiverSet smsReceiverSet = (SmsReceiverSet) req
				.getSessionValue(HkWebUtil.USERIDSET);
		Set<Long> useridset = null;
		boolean selall = true;
		if (smsReceiverSet != null) {
			useridset = smsReceiverSet.getUseridSet();
		}
		int hkb = 0;
		if (useridset != null && useridset.size() > 0) {
			hkb = useridset.size() * HkbConfig.getSendSms();
			selall = false;
		}
		else {
			long actId = req.getLong("actId");
			List<ActUser> list = this.actService.getActUserList(actId);
			hkb = list.size() - 1;
		}
		if (hkb > 0) {
			if (!this.userService.hasEnoughHkb(this.getLoginUser(req)
					.getUserId(), -hkb)) {
				UserOtherInfo info = this.userService.getUserOtherInfo(this
						.getLoginUser(req).getUserId());
				int count = hkb - info.getHkb();
				if (selall) {
					req.setSessionText("func.no_enough_hkb_sendgroupactsms1",
							count + "");
				}
				else {
					req.setSessionText("func.no_enough_hkb_sendgroupactsms2",
							count + "");
				}
			}
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tosendsms(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		Act act = this.actService.getAct(actId);
		req.setAttribute("act", act);
		req.setAttribute("actId", actId);
		SmsReceiverSet receiverSet = (SmsReceiverSet) req
				.getSessionValue(HkWebUtil.USERIDSET);
		req.setAttribute(HkWebUtil.USERIDSET, receiverSet);
		this.checkHkb(req);
		return "/WEB-INF/page/act/op/sendsms.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sendsms(HkRequest req, HkResponse resp) throws Exception {
		String[] userId = req.getStrings("userId");
		if (userId != null) {
			return this.processSelUserSend(req);
		}
		return this.processActUserSend(req);
	}

	private String processSelUserSend(HkRequest req) {
		String[] userId = req.getStrings("userId");
		long actId = req.getLong("actId");
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < userId.length; i++) {
			long id = Long.parseLong(userId[i]);
			if (this.actService.getActUser(actId, id) != null) {
				idList.add(id);
			}
		}
		return this.processSendSms(req, idList);
	}

	private String processActUserSend(HkRequest req) {
		long actId = req.getLong("actId");
		Act act = this.actService.getAct(actId);
		if (act == null) {
			return null;
		}
		List<Long> idList = new ArrayList<Long>();
		List<ActUser> list = this.actService.getActUserList(actId);
		for (ActUser o : list) {
			if (o.getUserId() != act.getUserId()) {
				idList.add(o.getUserId());
			}
		}
		return this.processSendSms(req, idList);
	}

	private String processSendSms(HkRequest req, List<Long> idList) {
		long actId = req.getLong("actId");
		Act act = this.actService.getAct(actId);
		if (act == null) {
			return null;
		}
		User loginUser = this.getLoginUser(req);
		UserOtherInfo userOtherInfo = this.userService
				.getUserOtherInfo(loginUser.getUserId());
		int count = idList.size();
		int hkb = HkbConfig.getSendSms() * count;
		if (!this.userService.hasEnoughHkb(act.getUserId(), -hkb)) {
			int add = hkb - userOtherInfo.getHkb();
			req.setText("func.no_enough_hkb_sendgroupactsms", add);
			return "/act/op/act_tosendsms.do";
		}
		SmsPortProcessAble userinfomation_smsport = (SmsPortProcessAble) HkUtil
				.getBean("userinfomation_smsport");
		InfoSmsPort infoSmsPort = this.infoSmsPortService.getInfoSmsPort(act
				.getPortId());
		String content = req.getString("content");
		// 以后保存到数据库使用 usersms
		UserSms userSms = new UserSms();
		userSms.setSenderId(loginUser.getUserId());
		userSms.setPort(userinfomation_smsport.getBaseSmsPort()
				+ infoSmsPort.getPortNumber());
		userSms.setContent(DataUtil.toHtmlRow(content));
		for (Long l : idList) {
			userSms.addReceiverId(l);
		}
		req.setAttribute("userSms", userSms);
		int code = userSms.validate();
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/act/op/act_tosendsms.do";
		}
		this.userSmsService.createUserSms(userSms);
		List<UserOtherInfo> infolist = this.userService
				.getUserOtherInfoListInId(idList);
		List<String> mobileList = new ArrayList<String>();
		for (UserOtherInfo o : infolist) {
			mobileList.add(o.getMobile());
		}
		BatchSms batchSms = new BatchSms();
		batchSms.setExmsgid(userSms.getMsgId() + "");
		batchSms.setContent(DataUtil.toTextRow(userSms.getContent()));
		batchSms.setPort(userinfomation_smsport.getBaseSmsPort()
				+ infoSmsPort.getPortNumber());
		batchSms.setMobile(mobileList);
		HkbLog hkbLog = HkbLog.create(act.getUserId(),
				HkLog.SEND_GROUP_ACT_SMS, act.getUserId(), -hkb);
		HkWebUtil.sendBatchSms(batchSms, hkbLog);
		this.userService.addHkb(hkbLog);
		req.setSessionText("func.smssendok");
		req.removeSessionvalue(HkWebUtil.USERIDSET);
		return "r:/act/act.do?actId=" + actId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toadd(HkRequest req, HkResponse resp) throws Exception {
		return "/WEB-INF/page/act/op/add.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String add(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		long userId = this.getLoginUser(req).getUserId();
		String addr = req.getString("addr");
		String intro = req.getString("intro");
		Date beginTime = DataUtil.parseTime(req.getString("beginTime"),
				"yyyyMMddHHmm");
		Date endTime = DataUtil.parseTime(req.getString("endTime"),
				"yyyyMMddHHmm");
		byte needCheck = req.getByte("needCheck");
		Act o = new Act();
		o.setName(DataUtil.toHtmlRow(name));
		o.setUserId(userId);
		o.setAddr(DataUtil.toHtmlRow(addr));
		o.setIntro(DataUtil.toHtmlRow(intro));
		o.setBeginTime(beginTime);
		o.setEndTime(endTime);
		o.setNeedCheck(needCheck);
		req.setAttribute("o", o);
		int code = o.validate(false);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/act/op/act_toadd.do";
		}
		try {
			this.actService.createAct(o);
		}
		catch (NoSmsPortException e) {
			req.setText("func.nosmsport");
			return "/act/op/act_toadd.do";
		}
		catch (DuplicateActNameException e) {
			req.setText("func.act.name_duplicate");
			return "/act/op/act_toadd.do";
		}
		catch (NoAvailableActSysNumException e) {
			req.setText("func.noactsysnum");
			return "/act/op/act_toadd.do";
		}
		req.setSessionText("func.act_create_ok");
		return "r:/act/act.do?actId=" + o.getActId();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toedit(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		Act o = (Act) req.getAttribute("o");
		if (o == null) {
			o = this.actService.getAct(actId);
		}
		req.setAttribute("actId", actId);
		req.setAttribute("o", o);
		return "/WEB-INF/page/act/op/edit.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String edit(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		String name = req.getString("name");
		long userId = this.getLoginUser(req).getUserId();
		String addr = req.getString("addr");
		String intro = req.getString("intro");
		Date beginTime = DataUtil.parseTime(req.getString("beginTime"),
				"yyyyMMddHHmm");
		Date endTime = DataUtil.parseTime(req.getString("endTime"),
				"yyyyMMddHHmm");
		byte needCheck = req.getByte("needCheck");
		Act o = this.actService.getAct(actId);
		o.setName(name);
		o.setUserId(userId);
		o.setAddr(addr);
		o.setIntro(intro);
		o.setBeginTime(beginTime);
		o.setEndTime(endTime);
		o.setNeedCheck(needCheck);
		req.setAttribute("o", o);
		int code = o.validate(true);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/act/op/act_edit.do";
		}
		try {
			this.actService.updateAct(o);
		}
		catch (DuplicateActNameException e) {
			req.setText("func.act.name_duplicate");
			return "/act/op/act_edit.do";
		}
		req.setSessionText("func.updateinfook");
		return "r:/act/act.do?actId=" + o.getActId();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deluser(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		long userId = req.getLong("userId");
		this.actService.deleteActUser(actId, userId);
		req.setSessionText("op.exeok");
		return "r:/act/act_user.do?actId=" + actId + "&page="
				+ req.getInt("repage");
	}

	/**
	 * 设置参与者通过审核
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String checkpassuser(HkRequest req, HkResponse resp)
			throws Exception {
		long actId = req.getLong("actId");
		this.checkUser(req, ActUser.CHECKFLG_Y);
		return "r:/act/act_user.do?actId=" + actId + "&page="
				+ req.getInt("repage");
	}

	/**
	 * 设置参与者不通过审核
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String checkunpassuser(HkRequest req, HkResponse resp)
			throws Exception {
		long actId = req.getLong("actId");
		this.checkUser(req, ActUser.CHECKFLG_N);
		return "r:/act/act_user.do?actId=" + actId + "&page="
				+ req.getInt("repage");
	}

	public void checkUser(HkRequest req, byte checkflg) {
		long actId = req.getLong("actId");
		long userId = req.getLong("userId");
		ActUser actUser = this.actService.getActUser(actId, userId);
		if (actUser != null) {
			actUser.setCheckflg(checkflg);
			this.actService.updateActUser(actUser);
			req.setSessionText("op.exeok");
		}
	}
}