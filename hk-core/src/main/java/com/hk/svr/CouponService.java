package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.Coupon;
import com.hk.bean.UserCoupon;

public interface CouponService {

	void createCoupon(Coupon coupon);

	/**
	 * 修改优惠券信息，如果当前总数小于剩余总数，就返回相应错误代码
	 * 
	 * @param coupon
	 * @return Err.COUPON_AMOUNT_LESSTHAN_REMAIN,Err.SUCCESS
	 *         2010-5-4
	 */
	int updateCoupon(Coupon coupon);

	/**
	 * 获得正常使用的优惠券，可用数量不为0
	 * 
	 * @param companyId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Coupon> getCouponListByCompanyId(long companyId, int begin, int size);

	List<Coupon> getCouponListByCompanyIdForAdmin(long companyId, int begin,
			int size);

	/**
	 * 获得联盟的可用优惠券(没有过期，数量不为0)
	 * 
	 * @param uid
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Coupon> getCouponListByUid(long uid, int begin, int size);

	List<Coupon> getCouponListEx(long companyId, String name, byte useflg,
			int begin, int size);

	Coupon getCoupon(long couponId);

	void setUserflg(long couponId, byte useflg);

	UserCoupon createUserCoupon(long userId, long couponId);

	UserCoupon getUserCoupon(long oid);

	void updateUid(long companyId, long uid);

	List<UserCoupon> getUserCouponListByUserId(long userId, int begin, int size);

	List<UserCoupon> getUserCouponListByCouponId(long couponId, int begin,
			int size);

	List<UserCoupon> getUserCouponListByCouponIdAndMcode(long couponId,
			String mcode, int begin, int size);

	Map<Long, Coupon> getCouponMapInId(List<Long> idList);

	List<Coupon> getCouponListByUserId(long userId, int begin, int size);

	/**
	 * 获得正在使用的优惠券
	 * 
	 * @param cityId 发布地区id
	 * @param begin
	 * @param size
	 * @return
	 *         2010-5-7
	 */
	List<Coupon> getCouponListByCityIdForUseful(int cityId, int begin, int size);

	/**
	 * 获得企业推荐的正在发行的优惠券
	 * 
	 * @param companyId
	 * @param begin
	 * @param size
	 * @return
	 *         2010-5-17
	 */
	List<Coupon> getCouponListByCompanyIdForUsefulForCmppink(long companyId,
			int begin, int size);

	/**
	 * 获得企业正在发行的优惠券
	 * 
	 * @param companyId
	 * @param begin
	 * @param size
	 * @return
	 *         2010-5-17
	 */
	List<Coupon> getCouponListByCompanyIdForUseful(long companyId, int begin,
			int size);

	/**
	 * 统计正在使用的优惠券
	 * 
	 * @param cityId 发布地区id
	 * @return
	 *         2010-5-7
	 */
	int countCouponByCityIdForUseful(int cityId);

	void updateCouponCmppink(long couponId, byte cmppink);

	void updateUserCouponUseflg(long oid, byte useflg);
}