package com.hk.web.hk4.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.City;
import com.hk.bean.IpCity;
import com.hk.bean.User;
import com.hk.bean.UserRegData;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.IpCityService;
import com.hk.svr.MsgService;
import com.hk.svr.UserService;
import com.hk.svr.ZoneService;
import com.hk.svr.processor.UserProcessor;
import com.hk.svr.processor.UserRegResult;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.MobileDuplicateException;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebUtil;

@Component("/h4/reg")
public class RegAction extends BaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private MsgService msgService;

	@Autowired
	private UserProcessor userProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		int ch = req.getInt("ch");
		if (this.getLoginUser(req) != null) {
			return "r:/user/" + this.getLoginUser(req).getUserId();
		}
		req.reSetAttribute("inviteCode");
		if (ch == 1) {
			return this.processReg(req, resp);
		}
		int pcityId = this.getPcityId(req);
		if (pcityId <= 0) {
			String ip = req.getRemoteAddr();
			if (ip != null) {
				IpCity ipCity = this.ipCityService.getIpCityByIp(ip);
				if (ipCity != null) {
					City city = this.zoneService.getCityLike(DataUtil
							.filterZoneName(ipCity.getName()));
					req.setAttribute("city", city);
				}
			}
		}
		return this.getWeb4Jsp("reg/reg.jsp");
	}

	/**
	 * 通过邀请方式到注册页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String hi(HkRequest req, HkResponse resp) throws Exception {
		String domain = req.getString("domain");
		if (!DataUtil.isEmpty(domain)) {
			User user = this.userService.getUserByDomain(domain);
			if (user != null) {
				req.setAttribute("user", user);
			}
		}
		return "/h4/reg.do";
	}

	private String processReg(HkRequest req, HkResponse resp) {
		if (this.getLoginUser(req) != null) {
			return null;
		}
		String email = req.getString("email");
		String mobile = req.getString("mobile");
		String password = req.getString("password");
		String zoneName = req.getString("zoneName");
		byte sex = req.getByte("sex", (byte) -1);
		RegUser regUser = new RegUser(req);
		List<Integer> codelist = regUser.validate(false);
		if (codelist.size() > 0) {
			return this.onErrorList(req, codelist, "regerrorlist");
		}
		UserRegData userRegData = new UserRegData();
		userRegData.setEmail(email);
		userRegData.setMobile(mobile);
		userRegData.setPassword(password);
		userRegData.setZoneName(zoneName);
		userRegData.setSex(sex);
		userRegData.setInviteUserId(req.getLong("inviteUserId"));
		userRegData.setIp(req.getRemoteAddr());
		userRegData.setProuserId(req.getLong("prouserId"));
		userRegData.setInviteCode(req.getString("inviteCode"));
		userRegData.setNickName(DataUtil.toHtmlRow(req.getString("nickName")));
		try {
			UserRegResult userRegResult = this.userProcessor.reg(userRegData,
					true);
			if (userRegResult.getErrorCode() != Err.SUCCESS) {
				return this.onError(req, userRegResult.getErrorCode(),
						"regerror", null);
			}
			long userId = userRegResult.getUserId();
			if (userRegResult.isNickNameDuplicate()) {
				req.setSessionText("view2.nickname_duplicate_tip", userRegData
						.getNickName());
				req.setSessionValue("nickname_duplicate", true);
				long svrid = HkSvrUtil.getServiceUserId();
				String msg = req.getText("view2.nickname_duplicate_tip",
						userRegData.getNickName());
				this.msgService.sendMsg(userId, svrid, msg);
			}
			req.setSessionValue("login_userId", userId);
			String input = email;
			if (input.indexOf("@") != -1) {
				HkWebUtil.sendRegMail(req, input);
			}
			this.processLoginAndReg(req, resp, input, null, true, true);
			return this.onSuccess2(req, "regok", null);
		}
		catch (EmailDuplicateException e) {
			return this.onError(req, Err.EMAIL_ALREADY_EXIST2,
					new Object[] { req.getContextPath() }, "regerror", null);
		}
		catch (MobileDuplicateException e) {
			return this
					.onError(req, Err.MOBILE_ALREADY_EXIST, "regerror", null);
		}
	}
}