package com.hk.api.action.cmp;

import java.util.List;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.bean.Coupon;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CouponService;
import com.hk.svr.pub.Err;

// @Component("/pubapi/coupon")
public class CouponAction extends BaseApiAction {

	@Autowired
	private CouponService couponService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long couponId = req.getLong("couponId");
		Coupon coupon = this.couponService.getCoupon(couponId);
		if (coupon == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		VelocityContext context = new VelocityContext();
		context.put("o", coupon);
		this.write(resp, "vm/e/coupon.vm", context);
		return null;
	}

	public String listforcmpex(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		byte useflg = req.getByte("useflg");
		String name = req.getString("name");
		int size = this.getSize(req);
		SimplePage page = req.getSimplePage(size);
		List<Coupon> list = this.couponService.getCouponListEx(companyId, name,
				useflg, page.getBegin(), size);
		VelocityContext context = new VelocityContext();
		context.put("list", list);
		this.write(resp, "vm/e/couponlist.vm", context);
		return null;
	}

	public String listforcmp(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		int size = this.getSize(req);
		SimplePage page = req.getSimplePage(size);
		List<Coupon> list = this.couponService.getCouponListByCompanyId(
				companyId, page.getBegin(), size);
		VelocityContext context = new VelocityContext();
		context.put("list", list);
		this.write(resp, "vm/e/couponlist.vm", context);
		return null;
	}
}