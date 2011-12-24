package com.hk.web.company.action.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Coupon;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CouponService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/e/op/auth/coupon")
public class OpCouponAction extends BaseAction {

	@Autowired
	private CouponService couponService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		String name = req.getString("name");
		PageSupport page = req.getPageSupport(20);
		byte useflg = req.getByteAndSetAttr("useflg", (byte) -1);
		List<Coupon> list = this.couponService.getCouponListEx(companyId, name,
				useflg, page.getBegin(), page.getSize());
		req.setEncodeAttribute("name", name);
		req.setAttribute("list", list);
		return this.getWeb3Jsp("e/coupon/op/couponlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadcoupon(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("companyId");
		long couponId = req.getLongAndSetAttr("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		req.setAttribute("coupon", coupon);
		return this.getWeb3Jsp("e/coupon/op/edit_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		String name = req.getString("name");
		String content = req.getString("content");
		String remark = req.getString("remark");
		byte overdueflg = req.getByte("overdueflg");
		int amount = req.getInt("amount");
		int limitDay = req.getInt("limitDay");
		String time = req.getString("time");
		Date endTime = DataUtil.parseTime(time, "yyyy-MM-dd");
		if (endTime != null) {
			endTime = DataUtil.getEndDate(endTime);
		}
		long uid = this.getUidFromCompany(companyId);
		Coupon coupon = new Coupon();
		coupon.setUid(uid);
		coupon.setCompanyId(companyId);
		coupon.setName(name);
		coupon.setContent(content);
		coupon.setRemark(remark);
		coupon.setOverdueflg(overdueflg);
		coupon.setAmount(amount);
		Map<String, String> datamap = new HashMap<String, String>();
		if (coupon.isLimitDayStyle()) {
			datamap.put("limitday", String.valueOf(limitDay));
		}
		else {
			if (endTime != null) {
				datamap.put("endtime", String.valueOf(endTime.getTime()));
			}
		}
		coupon.setData(DataUtil.toJson(datamap));
		int code = coupon.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "couponerror", null);
		}
		this.couponService.createCoupon(coupon);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "couponok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long couponId = req.getLong("couponId");
		String name = req.getString("name");
		String content = req.getString("content");
		String remark = req.getString("remark");
		byte overdueflg = req.getByte("overdueflg");
		int amount = req.getInt("amount");
		int limitDay = req.getInt("limitDay");
		Date endTime = DataUtil.parseTime(req.getString("time"), "yyyy-MM-dd");
		if (endTime != null) {
			endTime = DataUtil.getEndDate(endTime);
		}
		Coupon coupon = this.couponService.getCoupon(couponId);
		coupon.setCompanyId(companyId);
		coupon.setName(name);
		coupon.setContent(content);
		coupon.setRemark(remark);
		coupon.setOverdueflg(overdueflg);
		coupon.setAmount(amount);
		Map<String, String> datamap = new HashMap<String, String>();
		if (coupon.isLimitDayStyle()) {
			datamap.put("limitday", String.valueOf(limitDay));
		}
		else {
			if (endTime != null) {
				datamap.put("endtime", String.valueOf(endTime.getTime()));
			}
		}
		coupon.setData(DataUtil.toJson(datamap));
		int code = coupon.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "couponerror", null);
		}
		this.couponService.updateCoupon(coupon);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "couponok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setunavailable(HkRequest req, HkResponse resp)
			throws Exception {
		long couponId = req.getLong("couponId");
		this.couponService.setUserflg(couponId, Coupon.USEFLG_UNAVAILABLE);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setavailable(HkRequest req, HkResponse resp) throws Exception {
		long couponId = req.getLong("couponId");
		this.couponService.setUserflg(couponId, Coupon.USEFLG_AVAILABLE);
		return null;
	}
}