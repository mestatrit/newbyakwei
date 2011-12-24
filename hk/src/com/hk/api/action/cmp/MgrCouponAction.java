package com.hk.api.action.cmp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.bean.Coupon;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CouponService;
import com.hk.svr.processor.CouponProcessor;
import com.hk.svr.pub.Err;

// @Component("/pubapi/protect/cmp/mgrcoupon/mgr")
public class MgrCouponAction extends BaseApiAction {

	@Autowired
	private CouponService couponService;

	@Autowired
	private CouponProcessor couponProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String create(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		String name = req.getString("name");
		String content = req.getString("content");
		String remark = req.getString("remark");
		byte overdueflg = req.getByte("overdueflg");
		int amount = req.getInt("amount");
		int year = req.getInt("year");
		int month = req.getInt("month");
		int date = req.getInt("date");
		int limitDay = req.getInt("limitDay");
		long uid = this.getUidFromCompany(companyId);
		Coupon o = new Coupon();
		o.setUid(uid);
		o.setCompanyId(companyId);
		o.setName(DataUtil.toHtmlRow(name));
		o.setContent(DataUtil.toHtml(content));
		o.setRemark(DataUtil.toHtml(remark));
		o.setOverdueflg(overdueflg);
		o.setAmount(amount);
		Map<String, String> datamap = new HashMap<String, String>();
		if (o.isLimitDayStyle()) {
			datamap.put("limitday", String.valueOf(limitDay));
		}
		else {
			Date d = DataUtil.createDate(year, month, date);
			if (d != null) {
				datamap.put("endtime", String.valueOf(d.getTime()));
			}
		}
		o.setData(DataUtil.toJson(datamap));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			APIUtil.sendFailRespStatus(resp, code);
			return null;
		}
		this.couponProcessor.createCoupon(o, false);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("couponId", o.getCouponId());
		map.put("createcoupon", true);
		APIUtil.sendSuccessRespStatus(resp, map);
		return null;
	}

	public String update(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long couponId = req.getLong("couponId");
		Coupon o = this.couponService.getCoupon(couponId);
		if (o == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
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
		o.setCompanyId(companyId);
		o.setName(DataUtil.toHtmlRow(name));
		o.setContent(DataUtil.toHtml(content));
		o.setRemark(DataUtil.toHtml(remark));
		o.setOverdueflg(overdueflg);
		o.setAmount(amount);
		Map<String, String> datamap = new HashMap<String, String>();
		if (o.isLimitDayStyle()) {
			datamap.put("limitday", String.valueOf(limitDay));
		}
		else {
			Date d = DataUtil.createDate(year, month, date);
			if (d != null) {
				datamap.put("endtime", String.valueOf(d.getTime()));
			}
		}
		o.setData(DataUtil.toJson(datamap));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			APIUtil.sendFailRespStatus(resp, code);
			return null;
		}
		this.couponProcessor.updateCoupon(o, false, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("couponId", o.getCouponId());
		map.put("createcoupon", true);
		APIUtil.sendSuccessRespStatus(resp, map);
		return null;
	}

	public String setunavailable(HkRequest req, HkResponse resp) {
		long couponId = req.getLong("couponId");
		Coupon o = this.couponService.getCoupon(couponId);
		if (o == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		this.couponService.setUserflg(couponId, Coupon.USEFLG_UNAVAILABLE);
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}

	public String setavailable(HkRequest req, HkResponse resp) {
		long couponId = req.getLong("couponId");
		Coupon o = this.couponService.getCoupon(couponId);
		if (o == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		this.couponService.setUserflg(couponId, Coupon.USEFLG_AVAILABLE);
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}
}