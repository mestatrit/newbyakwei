package com.hk.web.pub.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.ScoreLog;
import com.hk.bean.UserMailAuth;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserMailAuthService;
import com.hk.svr.UserService;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.ScoreConfig;

/**
 * 认证E-mail,通过邮件中的链接，获取认证码，对比认证码来识别E-mail
 * 
 * @author akwei
 */
@Component("/pub/vamail")
public class VaMailAction extends BaseAction {
	@Autowired
	private UserService userService;

	@Autowired
	private UserMailAuthService userMailAuthService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String v = req.getString("v");
		UserMailAuth o = this.userMailAuthService.getUserMailAuthByAuthcode(v);
		if (o == null || o.isOver()) {
			req.setText("func.usermailauth_url_unavailable");
			return "/WEB-INF/page/pub/authmailerror.jsp";
		}
		this.userService.updateValidateEmail(o.getUserId(),
				UserOtherInfo.VALIDATEEMAIL_Y);
		this.userMailAuthService.deleteUserMailAuth(o.getUserId());
		ScoreLog scoreLog = ScoreLog.create(o.getUserId(), HkLog.VALIDATEEMAIL,
				0, ScoreConfig.getValidateEmail());
		this.userService.addScore(scoreLog);
		req.setSessionText("func.usermailauth.validateok");
		return "r:/tologin.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String web(HkRequest req, HkResponse resp) throws Exception {
		String v = req.getString("v");
		UserMailAuth o = this.userMailAuthService.getUserMailAuthByAuthcode(v);
		if (o == null || o.isOver()) {
			req.setText("func.usermailauth_url_unavailable");
			// return "/WEB-INF/page/pub/authmailerror.jsp";
			return this.getWeb3Jsp("pub/authmailerror.jsp");
		}
		this.userService.updateValidateEmail(o.getUserId(),
				UserOtherInfo.VALIDATEEMAIL_Y);
		this.userMailAuthService.deleteUserMailAuth(o.getUserId());
		ScoreLog scoreLog = ScoreLog.create(o.getUserId(), HkLog.VALIDATEEMAIL,
				0, ScoreConfig.getValidateEmail());
		this.userService.addScore(scoreLog);
		req.setSessionText("func.usermailauth.validateok");
		// return "r:/tologin.do";
		return "r:/reg_toregweb.do";
	}
}