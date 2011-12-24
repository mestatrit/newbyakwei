package com.hk.api.action.cmp;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.api.util.SessionKey;
import com.hk.bean.CmpHkbLog;
import com.hk.bean.Company;
import com.hk.bean.Coupon;
import com.hk.bean.UserCoupon;
import com.hk.bean.UserMailAuth;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
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

// @Component("/pubapi/protect/coupon")
public class ProtectCouponAction extends BaseApiAction {

	@Autowired
	private CouponService couponService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	@Autowired
	private MailUtil mailUtil;

	@Autowired
	private UserMailAuthService userMailAuthService;

	@Autowired
	private SmsClient smsClient;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String usercoupon(HkRequest req, HkResponse resp) throws Exception {
		SessionKey o = this.getSessionKey(req);
		long userId = o.getUserId();
		long oid = req.getLong("oid");
		UserCoupon userCoupon = this.couponService.getUserCoupon(oid);
		if (userCoupon == null || userCoupon.getUserId() != userId) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		Coupon coupon = this.couponService.getCoupon(userCoupon.getCouponId());
		userCoupon.setCoupon(coupon);
		VelocityContext context = new VelocityContext();
		context.put("userCoupon", userCoupon);
		this.write(resp, "vm/e/usercoupon.vm", context);
		return null;
	}

	/**
	 * 发送优惠券内容到email,如果用户email没有认证，下发激活码与优惠券内容
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createusercoupon(HkRequest req, HkResponse resp) {
		SessionKey o = this.getSessionKey(req);
		long userId = o.getUserId();
		long couponId = req.getLong("couponId");
		byte sendmail = req.getByte("sendmail");
		String mail = req.getString("mail");
		if (mail != null) {// 如果传入email，则修改当前email，然后置为非认证状态
			int code = UserOtherInfo.validateEmail(mail);
			if (code != Err.SUCCESS) {
				APIUtil.sendFailRespStatus(resp, code);
				return null;
			}
			try {
				this.userService.updateEmail(userId, mail);
				this.userService.updateValidateEmail(userId,
						UserOtherInfo.VALIDATEEMAIL_N);
			}
			catch (EmailDuplicateException e) {
				APIUtil.sendFailRespStatus(resp, Err.EMAIL_ALREADY_EXIST);
				return null;
			}
		}
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		Company company = this.companyService.getCompany(coupon.getCompanyId());
		UserCoupon userCoupon = this.couponService.createUserCoupon(userId,
				couponId);
		if (userCoupon == null) {
			APIUtil.sendFailRespStatus(resp, Err.COUPON_REMAIN_EMPTY);// 没有剩余
			return null;
		}
		// 计算截止日期
		userCoupon.setCoupon(coupon);
		String datestr = userCoupon.getEndTimeStr();
		String content = req.getText("func.send_coupon_to_user.content",
				company.getName(), coupon.getName(), coupon.getContent(),
				userCoupon.getMcode(), datestr);
		UserOtherInfo info = this.userService.getUserOtherInfo(userId);
		if (sendmail == 1) {
			if (info.isAuthedMail()) {// 如果已经认证email，直接发送优惠券内容
			}
			else {// 发送优惠券内容并发送认证email连接
				UserMailAuth userMailAuth = this.userMailAuthService
						.createUserMailAuth(info.getUserId());
				content = content
						+ req.getText("func.send_coupon_to_user.content2",
								userMailAuth.getAuthcode());
			}
			if (info.getEmail() == null) {
				APIUtil.sendFailRespStatus(resp, Err.EMAIL_ERROR);
				return null;
			}
			String title = req.getText("func.send_coupon_to_user.title",
					company.getName(), coupon.getName());
			try {
				this.mailUtil.sendHtmlMail(info.getEmail(), title, content);
			}
			catch (MessagingException e) {
				// 发送错误，重新发送
				try {
					this.mailUtil.sendHtmlMail(info.getEmail(), title, content);
				}
				catch (MessagingException e1) {
					APIUtil.sendFailRespStatus(resp, Err.EMAIL_SEND_ERROR);
					return null;
				}
			}
		}
		byte sendsms = req.getByte("sendsms");
		if (sendsms == 1) {
			// 如果手机未绑定，用户须上行短信
			if (!info.isMobileAlreadyBind()) {
				APIUtil.sendFailRespStatus(resp,
						Err.USEROTHERINFO_MOBILE_NOT_BIND);
				return null;
			}
			if (!DataUtil.isCmpMobile(info.getMobile())) {// 如果手机已绑定但为非移动号码，用户须上行短信
				APIUtil.sendFailRespStatus(resp, Err.MOBILE_CMP_NOT_MOBILE);
				return null;
			}
			// 移动号码，直接发送
			Sms sms = new Sms();
			sms.setMobile(info.getMobile());
			sms.setContent(DataUtil.toText(ResourceConfig.getText(
					"func.sms.send_coupon_to_user.content", company.getName(),
					coupon.getName(), coupon.getContent(), userCoupon
							.getMcode(), userCoupon.getEndTimeStr())));
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
				int batch = sms.getContent().length() / 70;
				int yu = sms.getContent().length() % 70;
				if (yu != 0) {
					batch++;
				}
				CmpHkbLog cmpHkbLog = CmpHkbLog.create(company.getCompanyId(),
						HkLog.CMP_SEND_COUPON_SMS_TO_USER, info.getUserId(),
						-HkbConfig.getSendSms() * batch);
				this.companyService.addHkb(cmpHkbLog);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createusercoupon", true);
		map.put("oid", userCoupon.getOid());
		APIUtil.sendSuccessRespStatus(resp, map);
		return null;
	}
}