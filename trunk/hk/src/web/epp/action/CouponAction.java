package web.epp.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpHkbLog;
import com.hk.bean.Company;
import com.hk.bean.Coupon;
import com.hk.bean.User;
import com.hk.bean.UserMailAuth;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.mail.MailUtil;
import com.hk.frame.util.page.SimplePage;
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

@Component("/epp/coupon")
public class CouponAction extends EppBaseAction {

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

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long couponId = req.getLongAndSetAttr("couponId");
		com.hk.bean.Coupon coupon = this.couponService.getCoupon(couponId);
		req.setAttribute("coupon", coupon);
		return this.getWapPath(req, "coupon/coupon.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-4-19
	 */
	public String view2(HkRequest req, HkResponse resp) throws Exception {
		long couponId = req.getLongAndSetAttr("couponId");
		com.hk.bean.Coupon coupon = this.couponService.getCoupon(couponId);
		req.setAttribute("coupon", coupon);
		req.reSetAttribute("error");
		return this.getWapPath(req, "coupon/coupon2.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String download(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long couponId = req.getLong("couponId");
		if (req.getString("formobile") != null) {
			return this.downloadtomobile(req, resp);
		}
		if (this.isNotLogin(req)) {
			return this.getLoginPath(req);
		}
		User loginUser = this.getLoginUser2(req);
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		String mail = info.getEmail();
		if (DataUtil.isEmpty(mail)) {// 如果没有email，用户填写email
			return "r:/epp/coupon_toinputemail.do?couponId=" + couponId
					+ "&companyId=" + companyId;
		}
		return this.downloadtomail(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toinputemail(HkRequest req, HkResponse resp) throws Exception {
		if (this.isNotLogin(req)) {
			return this.getLoginPath(req);
		}
		long companyId = req.getLong("companyId");
		long couponId = req.getLong("couponId");
		com.hk.bean.Coupon coupon = this.couponService.getCoupon(couponId);
		req.setAttribute("coupon", coupon);
		req.setAttribute("companyId", companyId);
		req.setAttribute("couponId", couponId);
		return this.getWapPath(req, "coupon/inputmail.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String saveemail(HkRequest req, HkResponse resp) throws Exception {
		if (this.isNotLogin(req)) {
			return this.getLoginPath(req);
		}
		long couponId = req.getLong("couponId");
		req.setAttribute("couponId", couponId);
		String email = req.getString("email");
		int code = UserOtherInfo.validateEmail(email);
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/epp/coupon_toinputemail.do";
		}
		return "r:/epp/coupon_downloadtomail.do?companyId="
				+ req.getLong("companyId") + "&couponId=" + couponId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String downloadtomobile(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLong("companyId");
		long couponId = req.getLong("couponId");
		com.hk.bean.User loginUser = this.getLoginUser2(req);
		if (loginUser == null) {
			return "r:/epp/login.do?return_url="
					+ DataUtil.urlEncoder("/coupon_download.do?companyId="
							+ companyId + "&couponId=" + couponId
							+ "&formobile=1");
		}
		com.hk.bean.Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			return "r:/epp/coupon_list.do?companyId=" + companyId;
		}
		com.hk.bean.UserCoupon userCoupon = this.couponService
				.createUserCoupon(loginUser.getUserId(), couponId);
		if (userCoupon == null) {
			req.setSessionText(String.valueOf(Err.COUPON_REMAIN_EMPTY));
			return "r:/epp/coupon.do?couponId=" + couponId + "&companyId="
					+ companyId;
		}
		long oid = userCoupon.getOid();
		Company company = this.companyService.getCompany(companyId);
		// 计算截止日期
		Date userEndTime = coupon.createEndTime(new Date());
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		// 如果手机未绑定，用户须上行短信
		if (!info.isMobileAlreadyBind()) {
			req.setSessionText(String
					.valueOf(Err.USEROTHERINFO_MOBILE_NOT_BIND));
			return "r:/epp/coupon_view2.do?couponId=" + couponId
					+ "&companyId=" + companyId + "&error="
					+ Err.USEROTHERINFO_MOBILE_NOT_BIND;
		}
		if (!DataUtil.isCmpMobile(info.getMobile())) {// 如果手机已绑定但为非移动号码，用户须上行短信
			req.setSessionText(String.valueOf(Err.MOBILE_CMP_NOT_MOBILE));
			return "r:/epp/coupon_view2.do?couponId=" + couponId
					+ "&companyId=" + companyId + "&error="
					+ Err.MOBILE_CMP_NOT_MOBILE;
		}
		// 移动号码，直接发送
		Sms sms = new Sms();
		sms.setMobile(info.getMobile());
		sms.setContent(DataUtil.toText(req.getText(
				"func.sms.send_coupon_to_user.content", coupon.getName(),
				coupon.getContent(), userCoupon.getMcode(), sdf
						.format(userEndTime))));
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
		return "r:/epp/coupon_usercoupon.do?oid=" + oid + "&sendok=2"
				+ "&companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String usercoupon(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		com.hk.bean.UserCoupon userCoupon = this.couponService
				.getUserCoupon(oid);
		if (req.getInt("sendok") == 1) {
			req.setText("func.send_usercoupon_info_mail_ok");
		}
		else if (req.getInt("sendok") == 2) {
			req.setText("func.send_usercoupon_info_sms_ok");
		}
		req.setAttribute("oid", oid);
		if (userCoupon != null) {
			Coupon coupon = this.couponService.getCoupon(userCoupon
					.getCouponId());
			userCoupon.setCoupon(coupon);
		}
		req.setAttribute("userCoupon", userCoupon);
		return this.getWapPath(req, "coupon/usercoupon.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String downloadtomail(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLong("companyId");
		long couponId = req.getLong("couponId");
		if (this.isNotLogin(req)) {
			return this.getLoginPath(req);
		}
		User loginUser = this.getLoginUser2(req);
		com.hk.bean.Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			return "r:/epp/coupon_list.do?companyId=" + companyId;
		}
		com.hk.bean.UserCoupon userCoupon = this.couponService
				.createUserCoupon(loginUser.getUserId(), couponId);
		if (userCoupon == null) {
			req.setSessionText(String.valueOf(Err.COUPON_REMAIN_EMPTY));
			return "r:/epp/coupon.do?couponId=" + couponId + "&companyId="
					+ companyId;
		}
		long oid = userCoupon.getOid();
		// 计算截止日期
		Date userEndTime = coupon.createEndTime(new Date());
		String content = req.getText("func.send_coupon_to_user.content", coupon
				.getName(), coupon.getContent(), userCoupon.getMcode(), sdf
				.format(userEndTime));
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		if (!info.isAuthedMail()) {// 发送优惠券内容并发送认证email连接
			UserMailAuth userMailAuth = this.userMailAuthService
					.createUserMailAuth(info.getUserId());
			content = content
					+ req.getText("func.send_coupon_to_user.content2",
							userMailAuth.getAuthcode());
		}
		if (info.getEmail() == null) {
			return "r:/epp/coupon.do?couponId=" + couponId + "&companyId="
					+ companyId;
		}
		String title = coupon.getName();
		try {
			this.mailUtil.sendHtmlMail(info.getEmail(), title, content);
		}
		catch (MessagingException e) {
			// 发送错误，重新发送
			try {
				this.mailUtil.sendHtmlMail(info.getEmail(), title, content);
			}
			catch (MessagingException e1) {
				req.setSessionText(String.valueOf(Err.EMAIL_SEND_ERROR));
				return "r:/epp/coupon.do?couponId=" + couponId + "&companyId="
						+ companyId;
			}
		}
		return "r:/epp/coupon_usercoupon.do?oid=" + oid + "&sendok=1"
				+ "&companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<com.hk.bean.Coupon> list = this.couponService
				.getCouponListByCompanyId(companyId, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapPath(req, "coupon/list.jsp");
	}
}