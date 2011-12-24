package com.hk.svr.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpUtil;
import com.hk.bean.Coupon;
import com.hk.bean.UserCoupon;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CouponService;
import com.hk.svr.pub.Err;

public class CouponServiceImpl implements CouponService {

	@Autowired
	private QueryManager manager;

	public void createCoupon(Coupon coupon) {
		coupon.setUseflg(Coupon.USEFLG_AVAILABLE);
		if (coupon.getCreateTime() == null) {
			coupon.setCreateTime(new Date());
		}
		coupon.setCmppinkTime(coupon.getCreateTime());
		String v = DataUtil.getRandom(4);
		char[] t = v.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char ch : t) {
			sb.append(ch).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		coupon.setCodedata(sb.toString());
		Query query = manager.createQuery();
		long couponId = query.insertObject(coupon).longValue();
		coupon.setCouponId(couponId);
	}

	public Coupon getCoupon(long couponId) {
		Query query = manager.createQuery();
		return query.getObjectById(Coupon.class, couponId);
	}

	public List<Coupon> getCouponListByCompanyId(long companyId, int begin,
			int size) {
		Query query = manager.createQuery();
		String sql = "select * from coupon where companyid=? and useflg=? and amount-dcount>0 order by couponid desc";
		return query.listBySql("ds1", sql, begin, size, Coupon.class,
				companyId, Coupon.USEFLG_AVAILABLE);
	}

	public List<Coupon> getCouponListByUid(long uid, int begin, int size) {
		Query query = manager.createQuery();
		String sql = "select * from coupon where uid=? and useflg=? and amount-dcount>0 order by couponid desc";
		return query.listBySql("ds1", sql, begin, size, Coupon.class, uid,
				Coupon.USEFLG_AVAILABLE);
	}

	public List<Coupon> getCouponListByCompanyIdAndUseFlg(long companyId,
			byte useflg, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(Coupon.class, "companyid=? and useflg=?",
				new Object[] { companyId, useflg }, "couponid desc", begin,
				size);
	}

	public List<Coupon> getCouponListEx(long companyId, String name,
			byte useflg, int begin, int size) {
		StringBuilder sql = new StringBuilder(
				"select * from coupon where companyid=?");
		List<Object> olist = new Vector<Object>();
		olist.add(companyId);
		if (useflg >= 0) {
			sql.append(" and useflg=?");
			olist.add(useflg);
		}
		if (!DataUtil.isEmpty(name)) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		sql.append(" order by couponid desc");
		Query query = manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				Coupon.class, olist);
	}

	public int updateCoupon(Coupon coupon) {
		int count = coupon.getAmount();
		// 修改后的总数量不能小于已下载数量
		if (coupon.getDcount() > count) {
			return Err.COUPON_AMOUNT_LESSTHAN_DCOUNT;
		}
		Query query = manager.createQuery();
		query.updateObjectExcept(coupon, new String[] { "codedata", "dcount" });
		return Err.SUCCESS;
	}

	public void setUserflg(long couponId, byte useflg) {
		Coupon o = this.getCoupon(couponId);
		o.setUseflg(useflg);
		Query query = manager.createQuery();
		query.addField("useflg", useflg);
		query.updateById(Coupon.class, couponId);
	}

	public UserCoupon createUserCoupon(long userId, long couponId) {
		Coupon coupon = this.getCoupon(couponId);
		if (coupon == null) {// 不存在的优惠券
			return null;
		}
		if (coupon.getDcount() == coupon.getAmount()) {
			return null;// 优惠券已经发完
		}
		UserCoupon userCoupon = new UserCoupon();
		userCoupon.setUserId(userId);
		userCoupon.setCouponId(couponId);
		userCoupon.setCreateTime(new Date());
		Query query = manager.createQuery();
		String mcode = null;
		while (true) {
			mcode = coupon.getNextShortKey();
			if (query.count(UserCoupon.class, "couponid=? and mcode=?",
					new Object[] { couponId, mcode }) == 0) {
				userCoupon.setMcode(mcode);
				long id = query.insertObject(userCoupon).longValue();
				userCoupon.setOid(id);
				// 更新优惠券剩余数量和暗号
				query.addField("codedata", coupon.getCodedata());
				query.addField("dcount", coupon.getDcount() + 1);
				query.updateById(Coupon.class, couponId);
				return userCoupon;
			}
		}
	}

	public UserCoupon getUserCoupon(long oid) {
		Query query = manager.createQuery();
		return query.getObjectById(UserCoupon.class, oid);
	}

	public void updateUid(long companyId, long uid) {
		Query query = this.manager.createQuery();
		query.addField("uid", uid);
		query.update(Coupon.class, "companyid=?", new Object[] { companyId });
	}

	public Map<Long, Coupon> getCouponMapInId(List<Long> idList) {
		List<Coupon> list = this.getCouponListInId(idList);
		Map<Long, Coupon> map = new HashMap<Long, Coupon>();
		for (Coupon o : list) {
			map.put(o.getCouponId(), o);
		}
		return map;
	}

	private List<Coupon> getCouponListInId(List<Long> idList) {
		Query query = this.manager.createQuery();
		// return query.listInId(Coupon.class, idList);
		return query.listInField(Coupon.class, null, null, "couponid", idList,
				null);
	}

	public List<UserCoupon> getUserCouponListByUserId(long userId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		return query.listEx(UserCoupon.class, "userid=?",
				new Object[] { userId }, "couponid desc", begin, size);
	}

	public List<Coupon> getCouponListByUserId(long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Coupon.class, "userid=?", new Object[] { userId },
				"couponid desc", begin, size);
	}

	public int countCouponByCityIdForUseful(int cityId) {
		Query query = this.manager.createQuery();
		return query.count(Coupon.class, "cityid=? and useflg=?", new Object[] {
				cityId, Coupon.USEFLG_AVAILABLE });
	}

	public List<Coupon> getCouponListByCityIdForUseful(int cityId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Coupon.class, "cityid=? and useflg=?",
				new Object[] { cityId, Coupon.USEFLG_AVAILABLE },
				"couponid desc", begin, size);
	}

	public List<Coupon> getCouponListByCompanyIdForUseful(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Coupon.class, "companyid=? and useflg=?",
				new Object[] { companyId, Coupon.USEFLG_AVAILABLE },
				"couponid desc", begin, size);
	}

	public List<Coupon> getCouponListByCompanyIdForUsefulForCmppink(
			long companyId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Coupon.class,
				"companyid=? and cmppink=? and useflg=?",
				new Object[] { companyId, CmpUtil.CMPPINK_Y,
						Coupon.USEFLG_AVAILABLE }, "cmppinktime desc", begin,
				size);
	}

	public void updateCouponCmppink(long couponId, byte cmppink) {
		Query query = this.manager.createQuery();
		query.addField("cmppink", cmppink);
		query.addField("cmppinktime", new Date());
		query.updateById(Coupon.class, couponId);
	}

	public List<Coupon> getCouponListByCompanyIdForAdmin(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Coupon.class, "companyid=?",
				new Object[] { companyId }, "couponid desc", begin, size);
	}

	public List<UserCoupon> getUserCouponListByCouponId(long couponId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(UserCoupon.class, "couponid=?",
				new Object[] { couponId }, "couponid desc", begin, size);
	}

	public List<UserCoupon> getUserCouponListByCouponIdAndMcode(long couponId,
			String mcode, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(UserCoupon.class, "couponid=? and mcode=?",
				new Object[] { couponId, mcode }, "couponid desc", begin, size);
	}

	public void updateUserCouponUseflg(long oid, byte useflg) {
		Query query = this.manager.createQuery();
		query.addField("useflg", useflg);
		query.updateById(UserCoupon.class, oid);
	}
}