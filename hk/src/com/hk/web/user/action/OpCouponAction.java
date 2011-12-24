package com.hk.web.user.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.City;
import com.hk.bean.Coupon;
import com.hk.bean.ObjPhoto;
import com.hk.bean.TmpData;
import com.hk.bean.User;
import com.hk.bean.UserCoupon;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.frame.util.mail.MailUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.Sms;
import com.hk.sms.SmsClient;
import com.hk.svr.CouponService;
import com.hk.svr.ObjPhotoService;
import com.hk.svr.TmpDataService;
import com.hk.svr.UserService;
import com.hk.svr.processor.CouponProcessor;
import com.hk.svr.processor.CreateCouponResult;
import com.hk.svr.processor.UpdateCouponResult;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.web.pub.action.BaseAction;

@Component("/op/coupon")
public class OpCouponAction extends BaseAction {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private CouponService couponService;

	@Autowired
	private CouponProcessor couponProcessor;

	@Autowired
	private TmpDataService tmpDataService;

	@Autowired
	private ObjPhotoService objPhotoService;

	@Autowired
	private UserService userService;

	@Autowired
	private MailUtil mailUtil;

	@Autowired
	private SmsClient smsClient;

	/**
	 * 我发布的优惠券列表
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(20);
		List<Coupon> list = this.couponService.getCouponListByUserId(loginUser
				.getUserId(), page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapJsp("coupon/list.jsp");
	}

	/**
	 * 发布优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-4
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		String zoneName = req.getString("zoneName");
		if (zoneName == null) {
			zoneName = (String) req.getSessionValue("zoneName");
			req.removeSessionvalue("zoneName");
			if (zoneName == null) {
				zoneName = this.getZoneNameFromIdP(req.getRemoteAddr());
			}
			if (zoneName == null) {
				City city = ZoneUtil.getCity(loginUser.getPcityId());
				if (city != null) {
					zoneName = city.getCity();
				}
			}
		}
		req.setAttribute("zoneName", zoneName);
		int ch = req.getInt("ch");
		if (ch == 0) {
			Coupon coupon = (Coupon) req.getAttribute("coupon");
			if (coupon == null) {
				long tmpoid = req.getLong("tmpoid");
				if (tmpoid > 0) {// 从临时数据中恢复
					TmpData tmpData = this.tmpDataService.getTmpData(tmpoid);
					if (tmpData != null
							&& tmpData.getDatatype() == TmpData.DATATYPE_COUPON) {
						Map<String, String> map = DataUtil
								.getMapFromJson(tmpData.getData());
						coupon = new Coupon();
						coupon.setCompanyId(Long.valueOf(map.get("companyid")));
						coupon.setAmount(Integer.valueOf(map.get("amount")));
						coupon.setRemark(map.get("remark"));
						coupon.setContent(map.get("content"));
						coupon.setData(map.get("data"));
						coupon.setName(map.get("name"));
						coupon.setOverdueflg(Byte
								.valueOf(map.get("overdueflg")));
						req.setAttribute("coupon", coupon);
					}
				}
			}
			return this.getWapJsp("coupon/create.jsp");
		}
		String name = req.getString("name");
		String content = req.getString("content");
		String remark = req.getString("remark");
		byte overdueflg = req.getByte("overdueflg");
		int amount = req.getInt("amount");
		int year = req.getInt("year");
		int month = req.getInt("month");
		int date = req.getInt("date");
		int limitDay = req.getInt("limitDay");
		long companyId = req.getLong("companyId");
		Coupon coupon = new Coupon();
		coupon.setUserId(loginUser.getUserId());
		coupon.setCompanyId(companyId);
		coupon.setName(DataUtil.toHtmlRow(name));
		coupon.setContent(DataUtil.toHtml(content));
		coupon.setRemark(DataUtil.toHtml(remark));
		coupon.setOverdueflg(overdueflg);
		coupon.setAmount(amount);
		Map<String, String> datamap = new HashMap<String, String>();
		if (coupon.isLimitDayStyle()) {
			datamap.put("limitday", String.valueOf(limitDay));
		}
		else {
			Date d = DataUtil.createDate(year, month, date);
			if (d != null) {
				datamap.put("endtime", String.valueOf(d.getTime()));
			}
		}
		coupon.setData(DataUtil.toJson(datamap));
		req.setAttribute("coupon", coupon);
		int code = coupon.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/op/coupon_create.do?ch=0";
		}
		CreateCouponResult createCouponResult = this.couponProcessor
				.createCoupon(coupon, true, zoneName);
		if (createCouponResult.getErrorCode() != Err.SUCCESS) {
			if (createCouponResult.getErrorCode() == Err.ZONE_NAME_ERROR) {
				if (createCouponResult.getProvinceId() > 0) {// 到省下的城市中
					req.setSessionText("view2.cannotfindcityandselect");
					return "r:/index_selcityfromprovince.do?provinceId="
							+ createCouponResult.getProvinceId()
							+ "&forsel=1"
							+ "&return_url="
							+ DataUtil
									.urlEncoder("/op/coupon_create.do?companyId="
											+ companyId
											+ "&tmpoid="
											+ createCouponResult.getOid());
				}
			}
			req.setText(String.valueOf(createCouponResult.getErrorCode()));
			return "/op/coupon_create.do?ch=0";
		}
		return "r:/coupon.do?couponId=" + coupon.getCouponId();
	}

	/**
	 * 更新优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-4
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		int ch = req.getInt("ch");
		long couponId = req.getLongAndSetAttr("couponId");
		Coupon coupon = (Coupon) req.getAttribute("coupon");
		if (coupon == null) {
			coupon = this.couponService.getCoupon(couponId);
			req.setAttribute("coupon", coupon);
		}
		if (coupon == null) {
			return null;
		}
		String zoneName = req.getString("zoneName");
		if (ch == 0) {
			if (zoneName == null) {
				zoneName = (String) req.getSessionValue("zoneName");
				req.removeSessionvalue("zoneName");
			}
			if (zoneName == null) {
				City city = ZoneUtil.getCity(coupon.getCityId());
				if (city != null) {
					zoneName = city.getCity();
				}
			}
			req.setAttribute("zoneName", zoneName);
			return this.getWapJsp("coupon/update.jsp");
		}
		User loginUser = this.getLoginUser(req);
		if (loginUser.getUserId() != coupon.getUserId()) {
			return "r:/coupon.do?couponId=" + coupon.getCouponId();
		}
		String name = req.getString("name");
		String content = req.getString("content");
		String remark = req.getString("remark");
		byte overdueflg = req.getByte("overdueflg");
		int amount = req.getInt("amount");
		int year = req.getInt("year");
		int month = req.getInt("month");
		int date = req.getInt("date");
		int limitDay = req.getInt("limitDay");
		coupon.setName(DataUtil.toHtmlRow(name));
		coupon.setContent(DataUtil.toHtml(content));
		coupon.setRemark(DataUtil.toHtml(remark));
		coupon.setOverdueflg(overdueflg);
		coupon.setAmount(amount);
		Map<String, String> datamap = new HashMap<String, String>();
		if (coupon.isLimitDayStyle()) {
			datamap.put("limitday", String.valueOf(limitDay));
		}
		else {
			Date d = DataUtil.createDate(year, month, date);
			if (d != null) {
				datamap.put("endtime", String.valueOf(d.getTime()));
			}
		}
		coupon.setData(DataUtil.toJson(datamap));
		req.setAttribute("coupon", coupon);
		int code = coupon.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/op/coupon_update.do?ch=0";
		}
		UpdateCouponResult updateCouponResult = this.couponProcessor
				.updateCoupon(coupon, true, zoneName);
		if (updateCouponResult.getErrorCode() != Err.SUCCESS) {
			if (updateCouponResult.getErrorCode() == Err.ZONE_NAME_ERROR) {
				if (updateCouponResult.getProvinceId() > 0) {// 到省下的城市中
					req.setSessionText("view2.cannotfindcityandselect");
					return "r:/index_selcityfromprovince.do?provinceId="
							+ updateCouponResult.getProvinceId()
							+ "&forsel=1"
							+ "&return_url="
							+ DataUtil
									.urlEncoder("/op/coupon_update.do?couponId="
											+ couponId);
				}
			}
			req.setText(String.valueOf(updateCouponResult.getErrorCode()));
			return "/op/coupon_update.do?ch=0";
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/coupon.do?couponId=" + coupon.getCouponId();
	}

	/**
	 * 更新优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-4
	 */
	public String setunavailable(HkRequest req, HkResponse resp)
			throws Exception {
		long couponId = req.getLong("couponId");
		User loginUser = this.getLoginUser(req);
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon != null && coupon.getUserId() == loginUser.getUserId()) {
			this.couponService.setUserflg(couponId, Coupon.USEFLG_UNAVAILABLE);
			this.setOpFuncSuccessMsg(req);
		}
		return "r:/op/coupon_list.do";
	}

	/**
	 * 优惠券管理
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-4
	 */
	public String list(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(20);
		List<Coupon> list = this.couponService.getCouponListByUserId(loginUser
				.getUserId(), page.getBegin(), page.getSize() + 1);
		req.setAttribute("list", list);
		return this.getWapJsp("coupon/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String selcouponpic(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(20);
		List<ObjPhoto> list = this.objPhotoService.getObjPhotoListByUserId(
				loginUser.getUserId(), page.getBegin(), page.getSize() + 1);
		req.reSetAttribute("couponId");
		req.setAttribute("list", list);
		return this.getWapJsp("coupon/piclist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String selpic(HkRequest req, HkResponse resp) {
		long couponId = req.getLong("couponId");
		long photoId = req.getLong("photoId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		User loginUser = this.getLoginUser(req);
		if (coupon.getUserId() != loginUser.getUserId()) {
			return null;
		}
		this.couponProcessor.updateCouponPicpath(couponId, photoId);
		return "r:/op/coupon_update.do?couponId=" + couponId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String uploadpic(HkRequest req, HkResponse resp) {
		long couponId = req.getLong("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		User loginUser = this.getLoginUser(req);
		if (coupon.getUserId() != loginUser.getUserId()) {
			return null;
		}
		File f = req.getFile("f");
		String returnpath = "r:/op/coupon_selcouponpic.do?couponId=" + couponId;
		if (f != null) {
			try {
				this.couponProcessor.updateCouponPicpath(coupon, f);
				req.setSessionText("view2.setpicforcouponprizeok");
			}
			catch (ImageException e) {
				req.setSessionText(String.valueOf(Err.IMG_UPLOAD_ERROR));
				return returnpath;
			}
			catch (NotPermitImageFormatException e) {
				req.setSessionText(String.valueOf(Err.IMG_FMT_ERROR));
				return returnpath;
			}
			catch (OutOfSizeException e) {
				req.setSessionText(String.valueOf(Err.IMG_OUTOFSIZE_ERROR),
						"2M");
				return returnpath;
			}
		}
		return "r:/op/coupon_update.do?couponId=" + couponId;
	}

	/**
	 * 下载优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String download(HkRequest req, HkResponse resp) {
		long couponId = req.getLong("couponId");
		if (req.getString("tomobile") != null) {
			return this.downloadtomobile(req, resp);
		}
		User loginUser = this.getLoginUser(req);
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		String mail = info.getEmail();
		if (DataUtil.isEmpty(mail)) {// 如果没有email，用户填写email
			return "r:/op/coupon_inputemail.do?couponId=" + couponId;
		}
		return this.downloadtomail(req, resp);
	}

	/**
	 * 下载优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String downloadtomail(HkRequest req, HkResponse resp) {
		long couponId = req.getLong("couponId");
		User loginUser = this.getLoginUser(req);
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			return null;
		}
		UserCoupon userCoupon = this.couponService.createUserCoupon(loginUser
				.getUserId(), couponId);
		if (userCoupon == null) {
			req.setSessionText(String.valueOf(Err.COUPON_REMAIN_EMPTY));
			return "r:/coupon.do?couponId=" + couponId;
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
			return "r:/coupon.do?couponId=" + couponId;
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
				return "r:/coupon.do?couponId=" + couponId;
			}
		}
		return "r:/op/coupon_usercoupon.do?oid=" + oid + "&sendok=1";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String downloadtomobile(HkRequest req, HkResponse resp) {
		long couponId = req.getLong("couponId");
		User loginUser = this.getLoginUser(req);
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			return null;
		}
		// 发送优惠券信息到手机(仅支持移动手机用户)
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		// 如果手机未绑定，用户须上行短信获取优惠券
		if (!info.isMobileAlreadyBind()) {
			req.setSessionText(String
					.valueOf(Err.USEROTHERINFO_MOBILE_NOT_BIND));
			return "r:/coupon_notbind.do?couponId=" + couponId + "&err="
					+ Err.USEROTHERINFO_MOBILE_NOT_BIND;
		}
		// 如果手机已绑定但为非移动号码，用户须上行短信获取优惠券
		if (!DataUtil.isCmpMobile(info.getMobile())) {
			return "r:/coupon_notbind.do?couponId=" + couponId + "&err="
					+ Err.MOBILE_CMP_NOT_MOBILE;
		}
		UserCoupon userCoupon = this.couponService.createUserCoupon(loginUser
				.getUserId(), couponId);
		if (userCoupon == null) {
			req.setSessionText(String.valueOf(Err.COUPON_REMAIN_EMPTY));
			return "r:/coupon.do?couponId=" + couponId + "&err="
					+ Err.COUPON_REMAIN_EMPTY;
		}
		long oid = userCoupon.getOid();
		// 移动号码，直接发送
		Sms sms = new Sms();
		sms.setMobile(info.getMobile());
		Date endTime = coupon.createEndTime(userCoupon.getCreateTime());
		sms.setContent(DataUtil.toText(req
				.getText("func.sms.send_coupon_to_user.content", coupon
						.getName(), coupon.getContent(), userCoupon.getMcode(),
						sdf.format(endTime))));
		try {
			smsClient.send(sms);
		}
		catch (Exception e) {// 重发一次
			smsClient.sendIgnoreError(sms);
		}
		return "r:/op/coupon_usercoupon.do?oid=" + oid + "&sendok=2";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String inputemail(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		if (ch == 0) {
			long couponId = req.getLongAndSetAttr("couponId");
			Coupon coupon = this.couponService.getCoupon(couponId);
			req.setAttribute("coupon", coupon);
			return this.getWapJsp("coupon/inputmail.jsp");
		}
		long couponId = req.getLong("couponId");
		String email = req.getString("email");
		int code = UserOtherInfo.validateEmail(email);
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/op/coupon_inputemail.do?ch=0";
		}
		User loginUser = this.getLoginUser(req);
		try {
			this.userService.updateEmail(loginUser.getUserId(), email);
		}
		catch (EmailDuplicateException e) {
			req.setText(String.valueOf(Err.EMAIL_ALREADY_EXIST));
			return "/op/coupon_inputemail.do?ch=0";
		}
		return "r:/op/coupon_downloadtomail.do?couponId=" + couponId;
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
	public String usercoupon(HkRequest req, HkResponse resp) throws Exception {
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
		return this.getWapJsp("coupon/usercoupon.jsp");
	}
}