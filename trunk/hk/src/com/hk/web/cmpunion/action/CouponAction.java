package com.hk.web.cmpunion.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Company;
import com.hk.bean.Coupon;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.CouponService;
import com.hk.web.pub.action.BaseAction;

@Component("/union/coupon")
public class CouponAction extends BaseAction {
	@Autowired
	private CouponService couponService;

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long couponId = req.getLongAndSetAttr("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			return this.getNotFoundForward(resp);
		}
		long companyId = coupon.getCompanyId();
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		req.setAttribute("coupon", coupon);
		return this.getUnionWapJsp("coupon/coupon.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLong("uid");
		SimplePage page = req.getSimplePage(20);
		List<Coupon> list = this.couponService.getCouponListByUid(uid, page
				.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getUnionWapJsp("coupon/couponlist.jsp");
	}
}