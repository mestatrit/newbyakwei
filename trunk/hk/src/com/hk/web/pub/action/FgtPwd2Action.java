package com.hk.web.pub.action;

import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.User;
import com.hk.bean.UserFgtMail;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserProtect;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.mail.MailUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.SmsClient;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.svr.pub.ProtectBean;
import com.hk.svr.pub.ProtectConfig;
import com.hk.svr.user.exception.SendOutOfLimitException;
import com.hk.web.util.HkStatus;
import com.hk.web.util.HkWebUtil;

/**
 * 用户用手机号码验证时，提示请用手机发送新密码到10669160257来修改账号密码
 * 如果用户没有设置过密码保护，直接把密码发送到注册填写的E-mail中，如果用户设置过密码保护，就到密码保护回答问题页面，尽心验证
 * 
 * @author akwei
 */
@Component("/fgtpwd2")
public class FgtPwd2Action extends BaseAction {
	@Autowired
	private UserService userService;

	@Autowired
	private MailUtil mailUtil;

	@Autowired
	private SmsClient smsClient;

	private final Log log = LogFactory.getLog(FgtPwd2Action.class);

	public String execute(HkRequest req, HkResponse resp) {
		SmsPortProcessAble smsPortProcessAble = (SmsPortProcessAble) HkUtil
				.getBean("updatePwd_smsPort");
		String number = this.smsClient.getSmsConfig().getSpNumber()
				+ smsPortProcessAble.getBaseSmsPort();
		req.setAttribute("number", number);
		return this.getWeb3Jsp("fgtpwd2/inputmail.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toinputemail(HkRequest req, HkResponse resp) {
		return this.getWeb3Jsp("fgtpwd2/inputmail.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String checkemail(HkRequest req, HkResponse resp) {
		String email = req.getString("email");
		String mobile = null;
		if (email == null) {
			return this.initError(req, Err.EMAIL_ERROR, "fgtpwd");
		}
		UserOtherInfo userOtherInfo = null;
		if (email.indexOf("@") == -1) {
			mobile = email;
			userOtherInfo = this.userService.getUserOtherInfoByMobile(mobile);
		}
		else {
			userOtherInfo = this.userService.getUserOtherInfoByeEmail(email);
		}
		if (userOtherInfo == null) {
			return this.initError(req, Err.USER_NOT_EXIST, "fgtpwd");
		}
		long userId = userOtherInfo.getUserId();
		UserProtect userProtect = this.userService.getUserProtect(userId);
		if (userProtect == null) {
			try {
				String value = this.userService.createDedValueForFgtPwd(userId);
				String title = req.getText("func.mail.fgtpwd.mail.title");
				String content = req.getText(
						"func.mail.fgtpwd.mail.contentweb", value);
				req.setMessage(req.getText("func.mail.fgtpwd.mail.sendok"));
				UserOtherInfo info = this.userService.getUserOtherInfo(userId);
				this.mailUtil.sendHtmlMail(info.getEmail(), title, content);
				// 邮件发送成功
				return this.initError(req, Err.FGTPWD_EMAIL_SEND_OK, "fgtpwd");
			}
			catch (SendOutOfLimitException e) {
				return this.initError(req, Err.EMAIL_SENDCOUNT_LIMIT, "fgtpwd");
			}
			catch (MessagingException e) {
				log.warn(e.getMessage());
				return this.initError(req, Err.EMAIL_SEND_ERROR, "fgtpwd");
			}
		}
		req.setSessionValue("fgtpwd_userId", userId);
		// 到提示密码保护页面
		return this.initError(req, Err.FGTPWD_PROTECT_INPUT, "fgtpwd");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toinputprotect(HkRequest req, HkResponse resp) {
		Long userId = (Long) req.getSessionValue("fgtpwd_userId");
		if (userId == null) {
			return "r:/tologin.do";
		}
		if (HkSvrUtil.isNotUser(userId)) {
			return "r:/tologin.do";
		}
		List<ProtectBean> list = ProtectConfig.getProtectList();
		UserProtect userProtect = this.userService.getUserProtect(userId);
		req.setAttribute("list", list);
		req.setAttribute("userProtect", userProtect);
		return this.getWeb3Jsp("fgtpwd2/inputprotect.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String chekprotect(HkRequest req, HkResponse resp) {
		Long userId = (Long) req.getSessionValue("fgtpwd_userId");
		if (userId == null) {
			return "r:/tologin.do";
		}
		UserProtect userProtect = this.userService.getUserProtect(userId);
		if (userProtect == null) {
			return "r:/tologin.do";
		}
		String pvalue = req.getString("pvalue");
		if (pvalue != null && pvalue.equals(userProtect.getPvalue())) {
			req.setSessionValue("protect_check_ok", true);
			return this.initSuccess(req, "fgtpwd");
		}
		return this.initError(req, Err.FGTPWD_PROTECT_ERROR, "fgtpwd");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toinputnewpwd(HkRequest req, HkResponse resp) {
		Long userId = (Long) req.getSessionValue("fgtpwd_userId");
		if (userId == null) {
			return "r:/tologin.do";
		}
		Boolean check = (Boolean) req.getSessionValue("protect_check_ok");
		if (check == null) {
			return "r:/tologin.do";
		}
		return this.getWeb3Jsp("fgtpwd2/inputnewpwd.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String savenewpwd(HkRequest req, HkResponse resp) {
		Long userId = (Long) req.getSessionValue("fgtpwd_userId");
		if (userId == null) {
			return null;
		}
		Boolean check = (Boolean) req.getSessionValue("protect_check_ok");
		if (check == null) {
			return null;
		}
		String password = req.getString("password");
		int code = User.validatePassword(password);
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "fgtpwd");
		}
		this.userService.updateNewPwd(userId, password);
		req.setSessionMessage("密码修改成功");
		req.removeSessionvalue("fgtpwd_userId");
		req.removeSessionvalue("protect_check_ok");
		User user = this.userService.getUser(userId);
		UserOtherInfo userOtherInfo = this.userService.getUserOtherInfo(userId);
		HkStatus hkStatus = new HkStatus();
		hkStatus.setUserId(user.getUserId());
		hkStatus.setInput(userOtherInfo.getEmail());
		HkWebUtil.setHkCookie(req,resp, hkStatus);
		return this.initSuccess(req, "fgtpwd");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toinputnewpwd2(HkRequest req, HkResponse resp) {
		String v = req.getString("v");
		UserFgtMail userFgtMail = this.userService.getUserFgtMailByDesValue(v);
		if (userFgtMail == null) {
			return "r:/tologin.do";
		}
		req.setSessionValue("v", v);
		return this.getWeb3Jsp("fgtpwd2/inputnewpwd2.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String savenewpwd2(HkRequest req, HkResponse resp) {
		String v = (String) req.getSessionValue("v");
		UserFgtMail userFgtMail = this.userService.getUserFgtMailByDesValue(v);
		if (userFgtMail == null) {
			return null;
		}
		long userId = userFgtMail.getUserId();
		String password = req.getString("password");
		int code = User.validatePassword(password);
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "fgtpwd");
		}
		this.userService.updateNewPwd(userId, password);
		this.userService.removeUsrFgtMail(userId);
		req.setSessionMessage("密码修改成功");
		req.removeSessionvalue("fgtpwd_userId");
		req.removeSessionvalue("protect_check_ok");
		User user = this.userService.getUser(userId);
		UserOtherInfo userOtherInfo = this.userService.getUserOtherInfo(userId);
		HkStatus hkStatus = new HkStatus();
		hkStatus.setUserId(user.getUserId());
		hkStatus.setInput(userOtherInfo.getEmail());
		HkWebUtil.setHkCookie(req,resp, hkStatus);
		return this.initSuccess(req, "fgtpwd");
	}
}