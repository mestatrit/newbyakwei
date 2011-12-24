package web.epp.mgr.action;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpUtil;
import com.hk.bean.Coupon;
import com.hk.bean.ObjPhoto;
import com.hk.bean.User;
import com.hk.bean.UserCoupon;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CouponService;
import com.hk.svr.ObjPhotoService;
import com.hk.svr.processor.CouponProcessor;
import com.hk.svr.processor.CreateCouponResult;
import com.hk.svr.processor.UpdateCouponResult;
import com.hk.svr.pub.Err;

@Component("/epp/web/op/webadmin/coupon")
public class CouponAction extends EppBaseAction {

	@Autowired
	private CouponProcessor couponProcessor;

	@Autowired
	private CouponService couponService;

	@Autowired
	private ObjPhotoService objPhotoService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_13", 1);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<Coupon> list = this.couponService
				.getCouponListByCompanyIdForAdmin(companyId, page.getBegin(),
						page.getSize() + 1);
		req.setAttribute("list", list);
		return this.getWebPath("admin/coupon/list.jsp");
	}

	/**
	 * 发布优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_13", 1);
		User loginUser = this.getLoginUser2(req);
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("admin/coupon/create.jsp");
		}
		int limitDay = req.getInt("limitDay");
		long companyId = req.getLong("companyId");
		Coupon coupon = new Coupon();
		coupon.setUserId(loginUser.getUserId());
		coupon.setCompanyId(companyId);
		coupon.setName(req.getHtmlRow("name"));
		coupon.setContent(req.getHtml("content"));
		coupon.setRemark(DataUtil.toHtml(req.getHtml("remark")));
		coupon.setOverdueflg(req.getByte("overdueflg"));
		coupon.setAmount(req.getInt("amount"));
		coupon.setCityId(req.getInt("pcityId"));
		Map<String, String> datamap = new HashMap<String, String>();
		if (coupon.isLimitDayStyle()) {
			datamap.put("limitday", String.valueOf(limitDay));
		}
		else {
			String date = req.getString("endTime");
			Date d = DataUtil.parseTime(date, "yyyy-MM-dd");
			if (d != null) {
				datamap.put("endtime", String.valueOf(d.getTime()));
			}
		}
		coupon.setData(DataUtil.toJson(datamap));
		int code = coupon.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		CreateCouponResult createCouponResult = this.couponProcessor
				.createCoupon(coupon, false, null);
		if (createCouponResult.getErrorCode() != Err.SUCCESS) {
			return this.onError(req, createCouponResult.getErrorCode(),
					"createerror", null);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * 修改优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_13", 1);
		User loginUser = this.getLoginUser2(req);
		long couponId = req.getLongAndSetAttr("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		req.setAttribute("coupon", coupon);
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("admin/coupon/update.jsp");
		}
		int limitDay = req.getInt("limitDay");
		long companyId = req.getLong("companyId");
		coupon.setUserId(loginUser.getUserId());
		coupon.setCompanyId(companyId);
		coupon.setName(req.getHtmlRow("name"));
		coupon.setContent(req.getHtml("content"));
		coupon.setRemark(DataUtil.toHtml(req.getHtml("remark")));
		coupon.setOverdueflg(req.getByte("overdueflg"));
		coupon.setAmount(req.getInt("amount"));
		coupon.setCityId(req.getInt("pcityId"));
		Map<String, String> datamap = new HashMap<String, String>();
		if (coupon.isLimitDayStyle()) {
			datamap.put("limitday", String.valueOf(limitDay));
		}
		else {
			String date = req.getString("endTime");
			Date d = DataUtil.parseTime(date, "yyyy-MM-dd");
			if (d != null) {
				datamap.put("endtime", String.valueOf(d.getTime()));
			}
		}
		coupon.setData(DataUtil.toJson(datamap));
		int code = coupon.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		UpdateCouponResult updateCouponResult = this.couponProcessor
				.updateCoupon(coupon, false, null);
		if (updateCouponResult.getErrorCode() != Err.SUCCESS) {
			return this.onError(req, updateCouponResult.getErrorCode(),
					"updateerror", null);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 作废优惠券发布
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String setunused(HkRequest req, HkResponse resp) throws Exception {
		long couponId = req.getLong("couponId");
		this.couponService.setUserflg(couponId, Coupon.USEFLG_UNAVAILABLE);
		return null;
	}

	/**
	 * 推荐优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String setcmppink(HkRequest req, HkResponse resp) throws Exception {
		long couponId = req.getLong("couponId");
		this.couponService.updateCouponCmppink(couponId, CmpUtil.CMPPINK_Y);
		this.setPinkSuccessMsg(req);
		return null;
	}

	/**
	 * 取消优惠券推荐
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String delcmppink(HkRequest req, HkResponse resp) throws Exception {
		long couponId = req.getLong("couponId");
		this.couponService.updateCouponCmppink(couponId, CmpUtil.CMPPINK_N);
		this.setPinkSuccessMsg(req);
		return null;
	}

	/**
	 * 优惠券选择图片（与宝箱奖品图片是一个图片库）
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String selcouponpic(HkRequest req, HkResponse resp) {
		req.reSetAttribute("navoid");
		req.setAttribute("active_13", 1);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<ObjPhoto> list = this.objPhotoService.getObjPhotoListByCompanyId(
				companyId, page.getBegin(), page.getSize() + 1);
		req.reSetAttribute("couponId");
		req.setAttribute("list", list);
		return this.getWebPath("admin/coupon/prizepic.jsp");
	}

	/**
	 * 设置优惠券图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String selpic(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long couponId = req.getLong("couponId");
		long photoId = req.getLong("photoId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon.getCompanyId() != companyId) {
			return null;
		}
		this.couponProcessor.updateCouponPicpath(couponId, photoId);
		return null;
	}

	/**
	 * 上传新的图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String uploadpic(HkRequest req, HkResponse resp) {
		long couponId = req.getLong("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		long companyId = req.getLong("companyId");
		if (coupon.getCompanyId() != companyId) {
			return null;
		}
		File f = req.getFile("f");
		if (f != null) {
			try {
				this.couponProcessor.updateCouponPicpath(coupon, f);
				req.setSessionText("view2.setpicforcouponprizeok");
			}
			catch (ImageException e) {
				return this.onError(req, Err.IMG_UPLOAD_ERROR, "uploaderror",
						null);
			}
			catch (NotPermitImageFormatException e) {
				return this
						.onError(req, Err.IMG_FMT_ERROR, "uploaderror", null);
			}
			catch (OutOfSizeException e) {
				return this.onError(req, Err.IMG_OUTOFSIZE_ERROR,
						new Object[] { "2M" }, "uploaderror", null);
			}
		}
		return this.onSuccess2(req, "uploadok", null);
	}

	/**
	 * 优惠券兑换管理
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String uclist(HkRequest req, HkResponse resp) {
		long couponId = req.getLongAndSetAttr("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		req.setAttribute("coupon", coupon);
		String mcode = req.getStringAndSetAttr("mcode");
		SimplePage page = req.getSimplePage(20);
		List<UserCoupon> list = null;
		if (DataUtil.isEmpty(mcode)) {
			list = this.couponProcessor.getUserCouponListByCompanyId(couponId,
					true, page.getBegin(), page.getSize() + 1);
		}
		else {
			list = this.couponProcessor.getUserCouponListByCompanyIdAndMcode(
					couponId, mcode, true, page.getBegin(), page.getSize() + 1);
		}
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.reSetAttribute("navoid");
		return this.getWebPath("admin/coupon/uclist.jsp");
	}

	/**
	 * 设置优惠券已兑换
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setused(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		UserCoupon userCoupon = this.couponService.getUserCoupon(oid);
		if (userCoupon == null) {
			return null;
		}
		Coupon coupon = this.couponService.getCoupon(userCoupon.getCouponId());
		long companyId = req.getLong("companyId");
		if (coupon.getCompanyId() == companyId) {
			this.couponService
					.updateUserCouponUseflg(oid, UserCoupon.USERFLG_Y);
			this.setOpFuncSuccessMsg(req);
		}
		return null;
	}
}