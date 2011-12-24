package com.hk.web.coupon.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.City;
import com.hk.bean.Company;
import com.hk.bean.Coupon;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.CouponService;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.pub.action.BaseAction;

@Component("/coupon")
public class CouponAction extends BaseAction {

	@Autowired
	private CouponService couponService;

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long couponId = req.getLongAndSetAttr("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			return null;
		}
		if (coupon.getCompanyId() > 0) {
			Company company = this.companyService.getCompany(coupon
					.getCompanyId());
			req.setAttribute("company", company);
		}
		req.setAttribute("coupon", coupon);
		return this.getWapJsp("coupon/coupon.jsp");
	}

	/**
	 * pc显示优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-8
	 */
	public String web(HkRequest req, HkResponse resp) throws Exception {
		long couponId = req.getLongAndSetAttr("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			return null;
		}
		if (coupon.getCompanyId() > 0) {
			Company company = this.companyService.getCompany(coupon
					.getCompanyId());
			req.setAttribute("company", company);
		}
		req.setAttribute("coupon", coupon);
		return this.getWeb4Jsp("coupon/coupon.jsp");
	}

	/**
	 * 当手机未绑定或者不是移动手机的时候，展示的页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-6
	 */
	public String notbind(HkRequest req, HkResponse resp) throws Exception {
		long couponId = req.getLongAndSetAttr("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			return null;
		}
		req.setAttribute("coupon", coupon);
		req.reSetAttribute("err");
		return this.getWapJsp("coupon/notbind.jsp");
	}

	/**
	 * 优惠券列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-6
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		int route = req.getIntAndSetAttr("route");
		SimplePage page = req.getSimplePage(20);
		int cityId = req.getIntAndSetAttr("cityId");
		List<Coupon> list = this.couponService.getCouponListByCityIdForUseful(
				cityId, page.getBegin(), page.getSize() + 1);
		if (route == 1 && list.size() == 0) {
			return "r:/coupon_list.do";
		}
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		if (cityId > 0) {
			City city = ZoneUtil.getCity(cityId);
			req.setAttribute("city", city);
		}
		return this.getWapJsp("coupon/couponlist.jsp");
	}
}