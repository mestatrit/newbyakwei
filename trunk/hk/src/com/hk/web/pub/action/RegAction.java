package com.hk.web.pub.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Invite;
import com.hk.bean.RegCode;
import com.hk.bean.User;
import com.hk.bean.UserRegData;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.InviteService;
import com.hk.svr.RegCodeService;
import com.hk.svr.processor.UserProcessor;
import com.hk.svr.processor.UserRegResult;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.MobileDuplicateException;
import com.hk.web.util.HkWebUtil;

@Component("/reg")
public class RegAction extends BaseAction {

	@Autowired
	private InviteService inviteService;

	@Autowired
	private RegCodeService regCodeService;

	@Autowired
	private UserProcessor userProcessor;

	/**
	 * 注册流程
	 * 
	 * @throws Exception
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.regwap(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-28
	 */
	public String regweb(HkRequest req, HkResponse resp) {
		return null;
	}

	public String regwap(HkRequest req, HkResponse resp) throws Exception {
		if (this.getLoginUser(req) != null) {
			return "r:/square.do";
		}
		req.reSetAttribute("inviteCode");
		int agree = req.getInt("agree");
		req.setAttribute("agree", agree);
		String input = req.getString("input", "");
		input = input.replaceAll("　", "");
		String password = req.getString("password");
		String password1 = req.getString("password1");
		req.setAttribute("input", input);
		/*************** 验证码 **********************/
		// String code = req.getString("code");
		// String session_code = (String) req
		// .getSessionValue(HkUtil.CLOUD_IMAGE_AUTH);
		// if (session_code == null || !session_code.equals(code)) {
		// req.setText(String.valueOf(Err.REG_INPUT_VALIDATECODE));
		// return "/reg_toreg.do";
		// }
		/*************** 验证码 end **********************/
		if (agree != 1) {
			req.setMessage("只有同意火酷协议,才可以注册为会员");
			return "/reg_toreg.do";
		}
		if (input == null || password == null) {
			return "/reg_toreg.do";
		}
		if (!password.equals(password1)) {
			req.setMessage(req.getText("func.reg.pwdnotequal"));
			return "/reg_toreg.do";
		}
		int vacode = User.validateReg(input, password);
		if (vacode != Err.SUCCESS) {
			req.setText(vacode + "");
			return "/reg_toreg.do";
		}
		UserRegData userRegData = new UserRegData();
		userRegData.setEmail(input);
		userRegData.setPassword(password);
		userRegData.setIp(req.getRemoteAddr());
		userRegData.setInviteCode(req.getString("inviteCode"));
		userRegData.setNickName(DataUtil.toHtmlRow(req.getString("nickName")));
		try {
			UserRegResult userRegResult = this.userProcessor.reg(userRegData,
					true);
			if (userRegResult.getErrorCode() != Err.SUCCESS) {
				req.setText(String.valueOf(userRegResult.getErrorCode()));
				return "/reg_toreg.do";
			}
			long userId = userRegResult.getUserId();
			req.setSessionValue("login_userId", userId);
			if (input.indexOf("@") != -1) {
				HkWebUtil.sendRegMail(req, input);
			}
			return this.processLoginAndReg(req, resp, input,
					"/next/op/op_toUpdateNickName.do", true, false);
		}
		catch (EmailDuplicateException e) {
			req.setMessage(req.getText("func.mailalreadyexist"));
			return "/reg_toreg.do";
		}
		catch (MobileDuplicateException e) {
			req.setMessage(req.getText("func.mobilealreadyexist"));
			return "/reg_toreg.do";
		}
	}

	/**
	 * 到注册页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toreg(HkRequest req, HkResponse resp) {
		if (this.getLoginUser(req) != null) {
			return "r:/square.do";
		}
		long inviterId = req.getLong("inviterId");
		long inviteId = req.getLong("inviteId");
		RegCode regCode = null;
		if (inviterId > 0) {
			regCode = this.regCodeService.getRegCodeByUserId(inviterId);
		}
		else if (inviteId > 0) {
			Invite invite = this.inviteService.getInvite(inviteId);
			if (invite != null) {
				regCode = this.regCodeService.getRegCodeByUserId(invite
						.getUserId());
			}
		}
		req.setAttribute("regCode", regCode);
		req.setAttribute("inviteId", inviteId);
		req.setAttribute("inviterId", inviterId);
		req.reSetAttribute("prouserId");
		if (req.getAttribute("agree") == null) {
			req.setAttribute("agree", 1);
		}
		return "/WEB-INF/page/reg/reg.jsp";
	}

	/**
	 * 到注册页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toregweb(HkRequest req, HkResponse resp) {
		if (this.getLoginUser(req) != null) {
			return "r:/home_web.do";
		}
		long inviterId = req.getLong("inviterId");
		long inviteId = req.getLong("inviteId");
		RegCode regCode = null;
		if (inviterId > 0) {
			regCode = this.regCodeService.getRegCodeByUserId(inviterId);
		}
		else if (inviteId > 0) {
			Invite invite = this.inviteService.getInvite(inviteId);
			if (invite != null) {
				regCode = this.regCodeService.getRegCodeByUserId(invite
						.getUserId());
			}
		}
		req.setAttribute("regCode", regCode);
		req.setAttribute("inviteId", inviteId);
		req.setAttribute("inviterId", inviterId);
		req.reSetAttribute("prouserId");
		if (req.getAttribute("agree") == null) {
			req.setAttribute("agree", 1);
		}
		return this.getWeb3Jsp("reg/reg.jsp");
	}
}