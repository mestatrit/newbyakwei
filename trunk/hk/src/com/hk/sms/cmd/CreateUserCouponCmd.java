package com.hk.sms.cmd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpHkbLog;
import com.hk.bean.Company;
import com.hk.bean.Coupon;
import com.hk.bean.UserCoupon;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.CompanyService;
import com.hk.svr.CouponService;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;

public class CreateUserCouponCmd extends BaseCmd {

	@Autowired
	private CouponService couponService;

	@Autowired
	private CompanyService companyService;

	private Log log = LogFactory.getLog(CreateUserCouponCmd.class);

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) throws Exception {
		UserSmsMo userSmsMo = this.getUserSmsMo(receivedSms);
		long userId = userSmsMo.getUserOtherInfo().getUserId();
		long couponId = Long.parseLong(receivedSms.getContent().substring(2));
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			return null;
		}
		Company company = this.companyService.getCompany(coupon.getCompanyId());
		if (!coupon.isRemain()) {
			Sms sms = this.createReceiveSms(receivedSms);
			sms.setContent(ResourceConfig.getText("func.sms.coupon.noremain"));
			return null;
		}
		UserCoupon userCoupon = this.couponService.createUserCoupon(userId,
				couponId);
		if (userCoupon == null) {
			Sms sms = new Sms();
			sms.setMobile(receivedSms.getMobile());
			sms.setLinkid(receivedSms.getLinkid());
			sms.setContent(ResourceConfig
					.getText("func.sms.send_coupon_noremain"));
			this.sendMsg(sms);
		}
		else {
			if (company.getHkb() < -100) {
				log.info("[mo] company no enough hkb to send sms to user");
				return null;
			}
			userCoupon.setCoupon(coupon);
			Sms sms = new Sms();
			sms.setMobile(receivedSms.getMobile());
			sms.setLinkid(receivedSms.getLinkid());
			sms.setContent(DataUtil.toText(ResourceConfig.getText(
					"func.sms.send_coupon_to_user.content", company.getName(),
					coupon.getName(), coupon.getContent(), userCoupon
							.getMcode(), userCoupon.getEndTimeStr())));
			int contentLen = sms.getContent().length();
			int batch = contentLen / 70;
			int yu = contentLen % 70;
			if (yu != 0) {
				batch++;
			}
			this.sendMsg(sms);
			CmpHkbLog cmpHkbLog = CmpHkbLog.create(company.getCompanyId(),
					HkLog.CMP_SEND_COUPON_SMS_TO_USER, userId, -HkbConfig
							.getSendSms()
							* batch);
			this.companyService.addHkb(cmpHkbLog);
		}
		return null;
	}
}