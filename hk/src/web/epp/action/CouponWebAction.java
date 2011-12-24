package web.epp.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.Coupon;
import com.hk.bean.User;
import com.hk.bean.UserCoupon;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.mail.MailUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CouponService;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.EmailDuplicateException;

@Component("/epp/web/coupon")
public class CouponWebAction extends EppBaseAction {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private CouponService couponService;

	@Autowired
	private UserService userService;

	@Autowired
	private MailUtil mailUtil;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<Coupon> list = this.couponService
				.getCouponListByCompanyIdForUseful(companyId, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("coupon/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-4-19
	 */
	public String view(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long couponId = req.getLongAndSetAttr("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		req.setAttribute("coupon", coupon);
		return this.getWebPath("coupon/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-4-19
	 */
	public String wapview(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long couponId = req.getLongAndSetAttr("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		req.setAttribute("coupon", coupon);
		return this.getWapPath("coupon/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String wap(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<Coupon> list = this.couponService
				.getCouponListByCompanyIdForUseful(companyId, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapPath("coupon/list.jsp");
	}

	/**
	 * 下载优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String prvdownload(HkRequest req, HkResponse resp) {
		long couponId = req.getLong("couponId");
		long companyId = req.getLong("companyId");
		long navId = req.getLong("navId");
		this.setCmpNavInfo(req);
		User loginUser = this.getLoginUser2(req);
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		String mail = info.getEmail();
		if (DataUtil.isEmpty(mail)) {// 如果没有email，用户填写email
			return "r:/epp/web/coupon_prvinputemail.do?companyId=" + companyId
					+ "&navId=" + navId + "&couponId=" + couponId;
		}
		return this.prvdownloadtomail(req, resp);
	}

	/**
	 * 下载优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String prvdownloadtomail(HkRequest req, HkResponse resp) {
		long couponId = req.getLong("couponId");
		long companyId = req.getLong("companyId");
		long navId = req.getLong("navId");
		User loginUser = this.getLoginUser2(req);
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			return null;
		}
		UserCoupon userCoupon = this.couponService.createUserCoupon(loginUser
				.getUserId(), couponId);
		if (userCoupon == null) {
			req.setSessionText(String.valueOf(Err.COUPON_REMAIN_EMPTY));
			return "r:/epp/web/coupon_view.do?couponId=" + couponId
					+ "&companyId=" + companyId + "&navId=" + navId;
		}
		long oid = userCoupon.getOid();
		// 发送优惠券信息到e-mail
		Date userEndTime = coupon.createEndTime(new Date());
		String content = req.getText("func.send_coupon_to_user.content", coupon
				.getName(), coupon.getContent(), userCoupon.getMcode(), sdf
				.format(userEndTime));
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		if (info.getEmail() == null) {
			return "r:/epp/web/coupon_view.do?couponId=" + couponId
					+ "&companyId=" + companyId + "&navId=" + navId;
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
				return "r:/epp/web/coupon_view.do?couponId=" + couponId
						+ "&companyId=" + companyId + "&navId=" + navId;
			}
		}
		return "r:/epp/web/coupon_prvusercoupon.do?oid=" + oid + "&sendok=1"
				+ "&companyId=" + companyId + "&navId=" + navId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String prvinputemail(HkRequest req, HkResponse resp) {
		this.setCmpNavInfo(req);
		long companyId = req.getLong("companyId");
		long navId = req.getLong("navId");
		int ch = req.getInt("ch");
		if (ch == 0) {
			long couponId = req.getLongAndSetAttr("couponId");
			Coupon coupon = this.couponService.getCoupon(couponId);
			req.setAttribute("coupon", coupon);
			return this.getWebPath("coupon/inputmail.jsp");
		}
		long couponId = req.getLong("couponId");
		String email = req.getString("email");
		int code = UserOtherInfo.validateEmail(email);
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/epp/web/coupon_prvinputemail.do?ch=0" + "&companyId="
					+ companyId + "&navId=" + navId;
		}
		User loginUser = this.getLoginUser2(req);
		try {
			this.userService.updateEmail(loginUser.getUserId(), email);
		}
		catch (EmailDuplicateException e) {
			req.setText(String.valueOf(Err.EMAIL_ALREADY_EXIST));
			return "/epp/web/coupon_prvinputemail.do?ch=0" + "&companyId="
					+ companyId + "&navId=" + navId;
		}
		return "r:/epp/web/coupon_prvdownloadtomail.do?couponId=" + couponId
				+ "&companyId=" + companyId + "&navId=" + navId;
	}

	/**
	 * 用户获得的优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-5
	 */
	public String prvusercoupon(HkRequest req, HkResponse resp)
			throws Exception {
		this.setCmpNavInfo(req);
		long oid = req.getLong("oid");
		UserCoupon userCoupon = this.couponService.getUserCoupon(oid);
		req.setAttribute("userCoupon", userCoupon);
		if (userCoupon == null) {
			return null;
		}
		Coupon coupon = this.couponService.getCoupon(userCoupon.getCouponId());
		req.setAttribute("coupon", coupon);
		req.setAttribute("endTime", coupon.createEndTime(userCoupon
				.getCreateTime()));
		return this.getWebPath("coupon/usercoupon.jsp");
	}

	/**
	 * 下载优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String wapprvdownload(HkRequest req, HkResponse resp) {
		long couponId = req.getLong("couponId");
		long companyId = req.getLong("companyId");
		long navId = req.getLong("navId");
		this.setCmpNavInfo(req);
		User loginUser = this.getLoginUser2(req);
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		String mail = info.getEmail();
		if (DataUtil.isEmpty(mail)) {// 如果没有email，用户填写email
			return "r:/epp/web/coupon_wapprvinputemail.do?companyId="
					+ companyId + "&navId=" + navId + "&couponId=" + couponId;
		}
		return this.wapprvdownloadtomail(req, resp);
	}

	/**
	 * 下载优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String wapprvdownloadtomail(HkRequest req, HkResponse resp) {
		long couponId = req.getLong("couponId");
		long companyId = req.getLong("companyId");
		long navId = req.getLong("navId");
		User loginUser = this.getLoginUser2(req);
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			return null;
		}
		UserCoupon userCoupon = this.couponService.createUserCoupon(loginUser
				.getUserId(), couponId);
		if (userCoupon == null) {
			req.setSessionText(String.valueOf(Err.COUPON_REMAIN_EMPTY));
			return "r:/epp/web/coupon_wapview.do?couponId=" + couponId
					+ "&companyId=" + companyId + "&navId=" + navId;
		}
		long oid = userCoupon.getOid();
		// 发送优惠券信息到e-mail
		Date userEndTime = coupon.createEndTime(new Date());
		String content = req.getText("func.send_coupon_to_user.content", coupon
				.getName(), coupon.getContent(), userCoupon.getMcode(), sdf
				.format(userEndTime));
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		if (info.getEmail() == null) {
			return "r:/epp/web/coupon_wapview.do?couponId=" + couponId
					+ "&companyId=" + companyId + "&navId=" + navId;
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
				return "r:/epp/web/coupon_wapview.do?couponId=" + couponId
						+ "&companyId=" + companyId + "&navId=" + navId;
			}
		}
		return "r:/epp/web/coupon_wapprvusercoupon.do?oid=" + oid + "&sendok=1"
				+ "&companyId=" + companyId + "&navId=" + navId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String wapprvinputemail(HkRequest req, HkResponse resp) {
		this.setCmpNavInfo(req);
		long companyId = req.getLong("companyId");
		long navId = req.getLong("navId");
		int ch = req.getInt("ch");
		if (ch == 0) {
			long couponId = req.getLongAndSetAttr("couponId");
			Coupon coupon = this.couponService.getCoupon(couponId);
			req.setAttribute("coupon", coupon);
			return this.getWapPath("coupon/inputmail.jsp");
		}
		long couponId = req.getLong("couponId");
		String email = req.getString("email");
		int code = UserOtherInfo.validateEmail(email);
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/epp/web/coupon_wapprvinputemail.do?ch=0" + "&companyId="
					+ companyId + "&navId=" + navId;
		}
		User loginUser = this.getLoginUser2(req);
		try {
			this.userService.updateEmail(loginUser.getUserId(), email);
		}
		catch (EmailDuplicateException e) {
			req.setText(String.valueOf(Err.EMAIL_ALREADY_EXIST));
			return "/epp/web/coupon_wapprvinputemail.do?ch=0" + "&companyId="
					+ companyId + "&navId=" + navId;
		}
		return "r:/epp/web/coupon_wapprvdownloadtomail.do?couponId=" + couponId
				+ "&companyId=" + companyId + "&navId=" + navId;
	}

	/**
	 * 用户获得的优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-5
	 */
	public String wapprvusercoupon(HkRequest req, HkResponse resp)
			throws Exception {
		this.setCmpNavInfo(req);
		long oid = req.getLong("oid");
		UserCoupon userCoupon = this.couponService.getUserCoupon(oid);
		req.setAttribute("userCoupon", userCoupon);
		if (userCoupon == null) {
			return null;
		}
		Coupon coupon = this.couponService.getCoupon(userCoupon.getCouponId());
		req.setAttribute("coupon", coupon);
		req.setAttribute("endTime", coupon.createEndTime(userCoupon
				.getCreateTime()));
		return this.getWapPath("coupon/usercoupon.jsp");
	}
}