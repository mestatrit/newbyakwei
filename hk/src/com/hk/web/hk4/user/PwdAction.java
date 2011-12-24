package com.hk.web.hk4.user;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.User;
import com.hk.bean.UserFgtMail;
import com.hk.bean.UserFgtSms;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserProtect;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.mail.MailUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.SmsClient;
import com.hk.svr.UserFgtPwdService;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ProtectBean;
import com.hk.svr.pub.ProtectConfig;
import com.hk.svr.user.exception.SendOutOfLimitException;
import com.hk.web.pub.action.BaseAction;

@Component("/h4/pwd")
public class PwdAction extends BaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private MailUtil mailUtil;

	@Autowired
	private UserFgtPwdService userFgtPwdService;

	@Autowired
	private SmsClient smsClient;

	private final Log log = LogFactory.getLog(PwdAction.class);

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			return "r:/user/" + loginUser.getUserId();
		}
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWeb4Jsp("pwd/inputmail.jsp");
		}
		String email = req.getString("email");
		if (DataUtil.isEmpty(email)) {
			return this.onError(req, Err.EMAIL_ERROR, "pwderror", null);
		}
		UserOtherInfo userOtherInfo = this.userService
				.getUserOtherInfoByeEmail(email);
		if (userOtherInfo == null) {
			return this.onError(req, Err.USER_NOT_EXIST, "pwderror", null);
		}
		long userId = userOtherInfo.getUserId();
		UserProtect userProtect = this.userService.getUserProtect(userId);
		// 如果没有设置密码保护则发送密码验证email进行验证
		if (userProtect == null) {
			try {
				String value = this.userService.createDedValueForFgtPwd(userId);
				String title = req.getText("func.mail.fgtpwd.mail.title");
				String content = req.getText(
						"func.mail.fgtpwd.mail.contentweb2", value);
				this.mailUtil.sendHtmlMail(userOtherInfo.getEmail(), title,
						content);
				// 邮件发送成功
				return this.onSuccess2(req, "pwdok", null);
			}
			catch (SendOutOfLimitException e) {
				return this.onError(req, Err.EMAIL_SENDCOUNT_LIMIT, "pwderror",
						null);
			}
			catch (MessagingException e) {
				log.warn(e.getMessage());
				return this
						.onError(req, Err.EMAIL_SEND_ERROR, "pwderror", null);
			}
		}
		req.setSessionValue("fgtpwd_userId", userId);
		// 到提示密码保护页面
		return this.onSuccess2(req, "pwdok2", userId);
	}

	/**
	 * 邮件发送成功
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String mailok(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("email");
		return this.getWeb4Jsp("pwd/inputmailok.jsp");
	}

	/**
	 * 通验证email中的验证码
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String checkv(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			return "r:/user/" + loginUser.getUserId();
		}
		String v = req.getStringAndSetAttr("v");
		UserFgtMail userFgtMail = this.userService.getUserFgtMailByDesValue(v);
		if (userFgtMail == null) {
			return "r:/login";
		}
		return this.getWeb4Jsp("pwd/savenewpwd.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String inputprotect(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			return "r:/user/" + loginUser.getUserId();
		}
		long userId = req.getLongAndSetAttr("userId");
		if (userId <= 0) {
			return "r:/login";
		}
		int ch = req.getInt("ch");
		if (ch == 0) {
			List<ProtectBean> list = ProtectConfig.getProtectList();
			UserProtect userProtect = this.userService.getUserProtect(userId);
			req.setAttribute("list", list);
			req.setAttribute("userProtect", userProtect);
			for (ProtectBean o : list) {
				if (o.getId() == userProtect.getPconfig()) {
					req.setAttribute("question", o);
					break;
				}
			}
			return this.getWeb4Jsp("pwd/inputprotect.jsp");
		}
		UserProtect userProtect = this.userService.getUserProtect(userId);
		if (userProtect == null) {
			return this.onError(req, Err.FGTPWD_PROTECT_ERROR, "perror", null);
		}
		String pvalue = req.getString("pvalue");
		if (pvalue != null && pvalue.equals(userProtect.getPvalue())) {
			req.setSessionValue(session_protect_check_ok, true);
			req.setSessionValue(session_fgtpwd_userId, userId);
			return this.onSuccess2(req, "pok", null);
		}
		return this.onError(req, Err.FGTPWD_PROTECT_ERROR, "perror", null);
	}

	private String session_fgtpwd_userId = "fgtpwd_userId";

	private String session_protect_check_ok = "protect_check_ok";

	/**
	 * 通过email收取验证码之后输入新的密码
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String savenewpwd(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWeb4Jsp("pwd/savenewpwd.jsp");
		}
		String v = req.getString("v");
		Boolean protect_check_ok = (Boolean) req
				.getSessionValue(session_protect_check_ok);
		UserFgtMail userFgtMail = this.userService.getUserFgtMailByDesValue(v);
		if (userFgtMail == null && protect_check_ok == null) {
			return null;
		}
		long userId = 0;
		if (userFgtMail != null) {
			userId = userFgtMail.getUserId();
		}
		else {
			if (protect_check_ok != null) {
				userId = (Long) req.getSessionValue(session_fgtpwd_userId);
			}
		}
		String password = req.getString("password", "");
		int code = User.validatePassword(password);
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "pwderror", null);
		}
		this.userService.updateNewPwd(userId, password);
		this.userService.removeUsrFgtMail(userId);
		req.removeSessionvalue(session_fgtpwd_userId);
		req.removeSessionvalue(session_protect_check_ok);
		req.setSessionText("func.user.updatepwdok");
		return this.onSuccess2(req, "pwdok", null);
	}

	/**
	 * 输入手机号码，发送认证短信到手机，然后用户输入验证码认证
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sendsms(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWapJsp("pwd/sendsms.jsp");
		}
		String mobile = req.getString("mobile");
		if (DataUtil.isEmpty(mobile)) {
			return this.onError(req, Err.MOBILE_ERROR, "senderror", null);
		}
		if (!DataUtil.isCmpMobile(mobile)) {
			return this.onError(req, Err.MOBILE_CMP_NOT_MOBILE, "senderror",
					null);
		}
		UserOtherInfo info = this.userService.getUserOtherInfoByMobile(mobile);
		if (info == null) {
			return this.onError(req, Err.USER_NOT_EXIST, "senderror", null);
		}
		if (!info.isMobileAlreadyBind()) {
			return this.onError(req, Err.USEROTHERINFO_MOBILE_NOT_BIND,
					"senderror", null);
		}
		UserFgtSms userFgtSms = this.userFgtPwdService.getUserFgtSms(info
				.getUserId());
		if (userFgtSms != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(userFgtSms.getUptime());
			int date = cal.get(Calendar.DATE);
			Calendar now = Calendar.getInstance();
			int now_date = now.get(Calendar.DATE);
			if (date == now_date && userFgtSms.getSendCount() >= 3) {
				return this.onError(req, Err.SMS_SENDCOUNT_OUT_OF_LIMIT,
						"senderror", null);
			}
			if (date == now_date) {
				userFgtSms.addSendCount(1);
			}
			else {
				userFgtSms.setSendCount(1);
			}
		}
		else {
			userFgtSms = new UserFgtSms();
			userFgtSms.setUserId(info.getUserId());
			userFgtSms.createSmscode();
		}
		userFgtSms.setUptime(new Date());
		this.userFgtPwdService.createUserFgtSms(userFgtSms);
		try {
			this.smsClient.send(mobile, req.getText(
					"view2.userfgtsms.conetent", userFgtSms.getSmscode()));
			req.setSessionText("view2.userfgtsms.sendok");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return this.onSuccess2(req, "sendok", null);
	}

	/**
	 * 输入手机号码，发送认证短信到手机，然后用户输入验证码认证
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String inputsmscode(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		if (ch == 0) {
			req.reSetAttribute("mobile");
			return this.getWeb4Jsp("pwd/inputsmscode.jsp");
		}
		String smscode = req.getString("smscode");
		if (DataUtil.isEmpty(smscode)) {
			return this.onError(req, Err.USERFGTSMS_SMSCODE_ERROR, "smserror",
					null);
		}
		String mobile = req.getString("mobile");
		if (DataUtil.isEmpty(mobile)) {
			return this.onError(req, Err.USERFGTSMS_SMSCODE_ERROR, "smserror",
					null);
		}
		UserOtherInfo info = this.userService.getUserOtherInfoByMobile(mobile);
		if (!info.isMobileAlreadyBind()) {
			if (DataUtil.isEmpty(mobile)) {
				return this.onError(req, Err.USEROTHERINFO_MOBILE_NOT_BIND,
						"smserror", null);
			}
		}
		UserFgtSms userFgtSms = this.userFgtPwdService.getUserFgtSms(info
				.getUserId());
		if (userFgtSms == null || !userFgtSms.getSmscode().equals(smscode)) {
			return this.onError(req, Err.USERFGTSMS_SMSCODE_ERROR, "smserror",
					null);
		}
		this.userFgtPwdService.deleteUserFgtSms(info.getUserId());
		req.setSessionValue(session_protect_check_ok, true);
		req.setSessionValue(session_fgtpwd_userId, info.getUserId());
		return this.onSuccess2(req, "smsok", null);
	}
}