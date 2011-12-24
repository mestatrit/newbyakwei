package com.hk.web.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.HkbLog;
import com.hk.bean.PvtChat;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.Sms;
import com.hk.svr.MsgService;
import com.hk.svr.UserService;
import com.hk.svr.msg.exception.MsgFormatErrorException;
import com.hk.svr.msg.validate.MsgValidate;
import com.hk.svr.pub.FmtUrlContent;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.user.exception.UserNotExistException;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebConfig;
import com.hk.web.util.HkWebUtil;

@Component("/op/sms")
public class OpSmsAction extends BaseAction {

	@Autowired
	private MsgService msgService;

	@Autowired
	private UserService userService;

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tosend(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("receiverId");
		req.setSessionText("func.msg.force_msg_send");
		return "/WEB-INF/page/user/sendsms.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tosendtome(HkRequest req, HkResponse resp) throws Exception {
		long userId = this.getLoginUser(req).getUserId();
		UserOtherInfo info = this.userService.getUserOtherInfo(userId);
		if (!info.isMobileAlreadyBind()) {
			return "r:/home.do?userId=" + userId;
		}
		return "/WEB-INF/page/user/sendsms_me.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sendtome(HkRequest req, HkResponse resp) throws Exception {
		long userId = this.getLoginUser(req).getUserId();
		String content = req.getString("content");
		String userport = HkWebUtil.getUserPort(userId);
		UserOtherInfo info = this.userService.getUserOtherInfo(userId);
		if (!info.isMobileAlreadyBind()) {
			return "r:/home.do?userId=" + userId;
		}
		Sms sms = new Sms();
		sms.setContent(content);
		sms.setMobile(info.getMobile());
		sms.setPort(userport);
		HkbLog hkbLog = HkbLog.create(userId, HkLog.SEND_SMS_TO_ME, 0,
				-HkbConfig.getSendSms());
		HkWebUtil.sendSms(sms, userId, hkbLog);
		req.setSessionText("func.sendsmsok");
		return "r:/home.do?userId=" + userId;
	}

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long receiverId = req.getLong("receiverId");
		long senderId = this.getLoginUser(req).getUserId();
		// UserOtherInfo info = this.userService.getUserOtherInfo(receiverId);
		String content = req.getString("content");
		try {
			MsgValidate.validateCreateMsg(senderId, receiverId, content);
		}
		catch (MsgFormatErrorException e) {
			req.setAttribute("content", content);
			req.setSessionMessage("私信内容不能为空或者不能超过140个字符");
			return "/op/user_sms.do?userId=" + receiverId;
		}
		catch (UserNotExistException e) {
			req.setAttribute("content", content);
			req.setSessionMessage("请选择收信人");
			return "/msg_tosend.do?userId=" + receiverId;// totototo
		}
		content = DataUtil.toHtml(content).replaceAll("，", ",").replaceAll("。",
				".");
		FmtUrlContent fmtUrlContent = new FmtUrlContent(content, true,
				HkWebConfig.getShortUrlDomain());
		content = fmtUrlContent.getFmtContent();
		this.msgService
				.sendMsg(receiverId, senderId, content, PvtChat.SmsFLG_N);
		// Sms sms = new Sms();
		// sms.setContent(content);
		// sms.setMobile(info.getMobile());
		// sms.setPort(HkWebUtil.getUserPort(senderId));
		HkbLog hkbLog = HkbLog.create(senderId, HkLog.SEND_SMS, receiverId,
				-HkbConfig.getSendSms());
		this.userService.addHkb(hkbLog);
		// HkWebUtil.sendSms(sms, senderId, hkbLog);
		req.setSessionMessage(req.getText("func.msg_send_ok"));
		return "r:/home.do?userId=" + receiverId;
	}
}