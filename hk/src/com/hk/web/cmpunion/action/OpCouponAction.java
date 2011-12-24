package com.hk.web.cmpunion.action;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpHkbLog;
import com.hk.bean.Company;
import com.hk.bean.Coupon;
import com.hk.bean.UserCoupon;
import com.hk.bean.UserMailAuth;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.mail.MailUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.Sms;
import com.hk.sms.SmsClient;
import com.hk.svr.CompanyService;
import com.hk.svr.CouponService;
import com.hk.svr.UserMailAuthService;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.user.exception.EmailDuplicateException;

@Component("/union/op/coupon")
public class OpCouponAction extends CmpUnionBaseAction {

	@Autowired
	private CouponService couponService;

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserMailAuthService userMailAuthService;

	@Autowired
	private MailUtil mailUtil;

	@Autowired
	private SmsClient smsClient;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}

	public String download(HkRequest req, HkResponse resp) throws Exception {
		long couponId = req.getLong("couponId");
		long uid = req.getLong("uid");
		if (req.getString("formobile") != null) {
			return this.tomobile(req, resp);
		}
		long userId = this.getLoginUser(req).getUserId();
		UserOtherInfo userOtherInfo = this.userService.getUserOtherInfo(userId);
		if (DataUtil.isEmpty(userOtherInfo.getEmail())) {
			return "r:/union/op/coupon_tosetmail.do?uid=" + uid + "&couponId="
					+ couponId;
		}
		return this.tomail(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tosetmail(HkRequest req, HkResponse resp) throws Exception {
		long couponId = req.getLongAndSetAttr("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		Company company = this.companyService.getCompany(coupon.getCompanyId());
		req.setAttribute("coupon", coupon);
		req.setAttribute("company", company);
		return this.getUnionWapJsp("coupon/setmail.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setmailanddownload(HkRequest req, HkResponse resp)
			throws Exception {
		String email = req.getString("email");
		long userId = this.getLoginUser(req).getUserId();
		// 如果传入email，则修改当前email，然后置为非认证状态
		int code = UserOtherInfo.validateEmail(email);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/union/op/coupon_tosetmail.do";
		}
		try {
			this.userService.updateEmail(userId, email);
			this.userService.updateValidateEmail(userId,
					UserOtherInfo.VALIDATEEMAIL_N);
		}
		catch (EmailDuplicateException e) {
			req.setText(Err.EMAIL_ALREADY_EXIST + "");
			return "/union/op/coupon_tosetmail.do";
		}
		return this.tomail(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tomobile(HkRequest req, HkResponse resp) throws Exception {
		long userId = this.getLoginUser(req).getUserId();
		long couponId = req.getLongAndSetAttr("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			return null;
		}
		Company company = this.companyService.getCompany(coupon.getCompanyId());
		if (company == null) {
			return null;
		}
		req.setAttribute("coupon", coupon);
		req.setAttribute("company", company);
		long uid = req.getLongAndSetAttr("uid");
		UserOtherInfo userOtherInfo = this.userService.getUserOtherInfo(userId);
		if (!userOtherInfo.isMobileAlreadyBind()) {
			req.setSessionText("func.createusercoupon.mobile_not_bind", "yh"
					+ couponId);
			return "r:/union/coupon.do?uid=" + uid + "&couponId=" + couponId;
		}
		if (!DataUtil.isCmpMobile(userOtherInfo.getMobile())) {
			req.setSessionText("func.createusercoupon.mobile_not_cmp_mobile",
					"yh" + couponId);
			return "r:/union/coupon.do?uid=" + uid + "&couponId=" + couponId;
		}
		UserCoupon userCoupon = this.couponService.createUserCoupon(userId,
				couponId);
		if (userCoupon == null) {
			req.setSessionText(Err.COUPON_REMAIN_EMPTY + "");
			return "r:/union/coupon.do?uid=" + uid + "&couponId=" + couponId;
		}
		req.setAttribute("userCoupon", userCoupon);
		userCoupon.setCoupon(coupon);
		// 移动号码，直接发送
		Sms sms = new Sms();
		sms.setMobile(userOtherInfo.getMobile());
		sms.setContent(DataUtil.toText(req.getText(
				"func.sms.send_coupon_to_user.content", company.getName(),
				coupon.getName(), coupon.getContent(), userCoupon.getMcode(),
				userCoupon.getEndTimeStr())));
		boolean sendok = false;
		try {
			smsClient.send(sms);
			sendok = true;
		}
		catch (Exception e) {// 重发一次
			smsClient.sendIgnoreError(sms);
			sendok = true;
		}
		if (sendok) {
			req.setText("func.send_usercoupon_info_sms_ok");
			int batch = sms.getContent().length() / 70;
			int yu = sms.getContent().length() % 70;
			if (yu != 0) {
				batch++;
			}
			CmpHkbLog cmpHkbLog = CmpHkbLog.create(company.getCompanyId(),
					HkLog.CMP_SEND_COUPON_SMS_TO_USER, userId, -HkbConfig
							.getSendSms()
							* batch);
			this.companyService.addHkb(cmpHkbLog);
		}
		return this.getUnionWapJsp("coupon/usercoupon.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tomail(HkRequest req, HkResponse resp) throws Exception {
		long userId = this.getLoginUser(req).getUserId();
		long couponId = req.getLongAndSetAttr("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			return null;
		}
		Company company = this.companyService.getCompany(coupon.getCompanyId());
		if (company == null) {
			return null;
		}
		req.setAttribute("coupon", coupon);
		req.setAttribute("company", company);
		long uid = req.getLongAndSetAttr("uid");
		UserOtherInfo userOtherInfo = this.userService.getUserOtherInfo(userId);
		UserCoupon userCoupon = this.couponService.createUserCoupon(userId,
				couponId);
		if (userCoupon == null) {
			req.setSessionText(Err.COUPON_REMAIN_EMPTY + "");
			return "r:/union/coupon.do?uid=" + uid + "&couponId=" + couponId;
		}
		req.setAttribute("userCoupon", userCoupon);
		// 计算截止日期
		userCoupon.setCoupon(coupon);
		String datestr = userCoupon.getEndTimeStr();
		String content = req.getText("func.send_coupon_to_user.content",
				company.getName(), coupon.getName(), coupon.getContent(),
				userCoupon.getMcode(), datestr);
		if (!userOtherInfo.isAuthedMail()) {// 如果没有认证email， 发送优惠券内容并发送认证email连接
			UserMailAuth userMailAuth = this.userMailAuthService
					.createUserMailAuth(userId);
			content = content
					+ req.getText("func.send_coupon_to_user.content2",
							userMailAuth.getAuthcode());
		}
		if (userOtherInfo.getEmail() == null) {
			req.setSessionText(Err.EMAIL_SEND_ERROR + "");
			return "r:/union/coupon.do?uid=" + uid + "&couponId=" + couponId;
		}
		String title = req.getText("func.send_coupon_to_user.title", company
				.getName(), coupon.getName());
		try {
			this.mailUtil
					.sendHtmlMail(userOtherInfo.getEmail(), title, content);
			req.setText("func.send_usercoupon_info_mail_ok");
		}
		catch (MessagingException e) {
			// 发送错误，重新发送
			try {
				this.mailUtil.sendHtmlMail(userOtherInfo.getEmail(), title,
						content);
				req.setText("func.send_usercoupon_info_mail_ok");
			}
			catch (MessagingException e1) {
				req.setSessionText(Err.EMAIL_SEND_ERROR + "");
				return "r:/union/coupon.do?uid=" + uid + "&couponId="
						+ couponId;
			}
		}
		return this.getUnionWapJsp("coupon/usercoupon.jsp");
	}
}