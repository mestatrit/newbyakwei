package svrtest;

import com.hk.bean.UserCoupon;
import com.hk.frame.util.P;
import com.hk.svr.CouponService;

public class CouponServiceTest extends HkServiceTest {

	private CouponService couponService;

	public void setCouponService(CouponService couponService) {
		this.couponService = couponService;
	}

	public void testCreateUserCoupon() {
		long userId = 1;
		long couponId = 9;
		UserCoupon userCoupon = this.couponService.createUserCoupon(userId,
				couponId);
		P.println(userCoupon.getMcode());
	}
}