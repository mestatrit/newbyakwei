package com.hk.svr.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpUnion;
import com.hk.bean.CmpUnionFeed;
import com.hk.bean.Company;
import com.hk.bean.Coupon;
import com.hk.bean.HkbLog;
import com.hk.bean.ObjPhoto;
import com.hk.bean.TmpData;
import com.hk.bean.User;
import com.hk.bean.UserCoupon;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.CmpUnionMessageService;
import com.hk.svr.CmpUnionService;
import com.hk.svr.CompanyService;
import com.hk.svr.CouponService;
import com.hk.svr.ObjPhotoService;
import com.hk.svr.TmpDataService;
import com.hk.svr.UserService;
import com.hk.svr.pub.CmpUnionMessageUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;

public class CouponProcessor extends BaseProcessor {

	@Autowired
	private CouponService couponService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpUnionService cmpUnionService;

	@Autowired
	private CmpUnionMessageService cmpUnionMessageService;

	@Autowired
	private UserService userService;

	@Autowired
	private TmpDataService tmpDataService;

	@Autowired
	private ObjPhotoService objPhotoService;

	public CreateCouponResult createCoupon(Coupon coupon, boolean checkHkb,
			String zoneName) {
		CreateCouponResult createCouponResult = new CreateCouponResult();
		int cityId = 0;
		if (!DataUtil.isEmpty(zoneName)) {
			ZoneResult zoneResult = this.findZone(zoneName);
			if (zoneResult == null) {
				createCouponResult.setErrorCode(Err.ZONE_NAME_ERROR);
				return createCouponResult;
			}
			// 在生表中找到，进行特殊处理
			if (zoneResult.getProvinceId() > 0) {
				createCouponResult.setErrorCode(Err.ZONE_NAME_ERROR);
				createCouponResult.setProvinceId(zoneResult.getProvinceId());
				// 保存优惠券数据到临时表中
				TmpData tmpData = this.tmpDataService
						.getLastTmpDataByUserIdAndDataType(coupon.getUserId(),
								TmpData.DATATYPE_COUPON);
				if (tmpData == null) {
					tmpData = new TmpData();
					tmpData.setUserId(coupon.getUserId());
					tmpData.setDatatype(TmpData.DATATYPE_COUPON);
					tmpData.setData(coupon.toJsonData());
					this.tmpDataService.createTmpData(tmpData);
				}
				createCouponResult.setOid(tmpData.getOid());
				return createCouponResult;
			}
			cityId = zoneResult.getCityId();
		}
		if (coupon.getCityId() <= 0) {
			coupon.setCityId(cityId);
		}
		int allhkb = 0;
		if (checkHkb) {
			UserOtherInfo info = this.userService.getUserOtherInfo(coupon
					.getUserId());
			allhkb = coupon.getDcount() * HkbConfig.getCouponDcount();
			int userhkb = info.getHkb();
			if (userhkb < allhkb) {
				createCouponResult.setErrorCode(Err.NOENOUGH_HKB);
				return createCouponResult;
			}
		}
		this.couponService.createCoupon(coupon);
		this.tmpDataService.deleteTmpDataByUserIdAndDataType(
				coupon.getUserId(), TmpData.DATATYPE_COUPON);
		this.couponCreated(coupon);
		if (checkHkb && allhkb != 0) {
			// 扣除hkb
			HkbLog hkbLog = HkbLog.create(coupon.getUserId(),
					HkLog.CREATECOUPON, coupon.getCouponId(), -allhkb);
			this.userService.addHkb(hkbLog);
		}
		this.couponCreated(coupon);
		createCouponResult.setErrorCode(Err.SUCCESS);
		return createCouponResult;
	}

	public CreateCouponResult createCoupon(Coupon coupon, boolean checkHkb) {
		CreateCouponResult createCouponResult = new CreateCouponResult();
		int allhkb = 0;
		if (checkHkb) {
			UserOtherInfo info = this.userService.getUserOtherInfo(coupon
					.getUserId());
			allhkb = coupon.getDcount() * HkbConfig.getCouponDcount();
			int userhkb = info.getHkb();
			if (userhkb < allhkb) {
				createCouponResult.setErrorCode(Err.NOENOUGH_HKB);
				return createCouponResult;
			}
		}
		this.couponService.createCoupon(coupon);
		this.couponCreated(coupon);
		if (checkHkb && allhkb != 0) {
			// 扣除hkb
			HkbLog hkbLog = HkbLog.create(coupon.getUserId(),
					HkLog.CREATECOUPON, coupon.getCouponId(), -allhkb);
			this.userService.addHkb(hkbLog);
		}
		this.couponCreated(coupon);
		createCouponResult.setErrorCode(Err.SUCCESS);
		return createCouponResult;
	}

	public void couponCreated(Coupon coupon) {
		this.createCmpUnionFeed(coupon);
	}

	private void createCmpUnionFeed(Coupon coupon) {
		long companyId = coupon.getCompanyId();
		if (companyId <= 0) {
			return;
		}
		Company company = this.companyService.getCompany(companyId);
		if (company == null || company.getUid() <= 0) {
			return;
		}
		CmpUnion cmpUnion = this.cmpUnionService.getCmpUnion(company.getUid());
		if (cmpUnion == null) {
			return;
		}
		CmpUnionFeed feed = new CmpUnionFeed();
		feed.setUid(cmpUnion.getUid());
		feed.setFeedflg(CmpUnionMessageUtil.FEED_CREATECOUPON);
		Map<String, String> map = new HashMap<String, String>();
		map.put("cmp", company.getName());
		map.put("pid", coupon.getCouponId() + "");
		map.put("name", coupon.getName());
		feed.setData(DataUtil.toJson(map));
		feed.setObjId(companyId);
		cmpUnionMessageService.createCmpUnionFeed(feed);
	}

	private synchronized Object updateCouponOrCreateUserCoupon(
			boolean updateCoupon, Object[] obj) {
		if (updateCoupon) {
			Coupon coupon = (Coupon) obj[0];
			boolean checkHkb = (Boolean) obj[1];
			String zoneName = (String) obj[2];
			return this.innerUpdateCoupon(coupon, checkHkb, zoneName);
		}
		long userId = (Long) obj[0];
		long couponId = (Long) obj[1];
		return this.couponService.createUserCoupon(userId, couponId);
	}

	public UserCoupon createUserCoupon(long userId, long couponId) {
		return (UserCoupon) this.updateCouponOrCreateUserCoupon(false,
				new Object[] { userId, couponId });
	}

	public UpdateCouponResult innerUpdateCoupon(Coupon coupon,
			boolean checkHkb, String zoneName) {
		UpdateCouponResult updateCouponResult = new UpdateCouponResult();
		int cityId = 0;
		if (!DataUtil.isEmpty(zoneName)) {
			ZoneResult zoneResult = this.findZone(zoneName);
			if (zoneResult == null) {
				updateCouponResult.setErrorCode(Err.ZONE_NAME_ERROR);
				return updateCouponResult;
			}
			// 在生表中找到，进行特殊处理
			if (zoneResult.getProvinceId() > 0) {
				updateCouponResult.setErrorCode(Err.ZONE_NAME_ERROR);
				updateCouponResult.setProvinceId(zoneResult.getProvinceId());
				return updateCouponResult;
			}
			cityId = zoneResult.getCityId();
		}
		if (coupon.getCityId() <= 0) {
			coupon.setCityId(cityId);
		}
		int hkb = 0;
		if (checkHkb) {
			Coupon old_coupon = this.couponService.getCoupon(coupon
					.getCouponId());
			UserOtherInfo info = this.userService.getUserOtherInfo(coupon
					.getUserId());
			int old_amount_hkb = old_coupon.getAmount()
					* HkbConfig.getCouponDcount();
			int amount_hkb = coupon.getAmount() * HkbConfig.getCouponDcount();
			hkb = amount_hkb - old_amount_hkb;
			if (hkb > 0) {
				// 增加了下载，判断当前hkb余额
				if (hkb > info.getHkb()) {
					// 余额不足
					updateCouponResult.setErrorCode(Err.NOENOUGH_HKB);
					return updateCouponResult;
				}
			}
		}
		int error = this.couponService.updateCoupon(coupon);
		if (error != Err.SUCCESS) {
			updateCouponResult.setErrorCode(error);
			return updateCouponResult;
		}
		if (checkHkb && hkb != 0) {
			HkbLog hkbLog = HkbLog.create(coupon.getUserId(),
					HkLog.CREATECOUPON, coupon.getCouponId(), -hkb);
			this.userService.addHkb(hkbLog);
		}
		updateCouponResult.setErrorCode(Err.SUCCESS);
		return updateCouponResult;
	}

	public UpdateCouponResult updateCoupon(Coupon coupon, boolean checkHkb,
			String zoneName) {
		return (UpdateCouponResult) this.updateCouponOrCreateUserCoupon(true,
				new Object[] { coupon, checkHkb, zoneName });
	}

	/**
	 * 用户获得的优惠券列表
	 * 
	 * @param userId
	 * @param buildCoupon true:组装优惠券数据,false:不组装
	 * @param begin
	 * @param size
	 * @return
	 *         2010-4-24
	 */
	public List<UserCoupon> getUserCouponListByUserId(long userId,
			boolean buildCoupon, int begin, int size) {
		List<UserCoupon> list = this.couponService.getUserCouponListByUserId(
				userId, begin, size);
		if (buildCoupon) {
			List<Long> idList = new ArrayList<Long>();
			for (UserCoupon o : list) {
				idList.add(o.getCouponId());
			}
			Map<Long, Coupon> map = this.couponService.getCouponMapInId(idList);
			for (UserCoupon o : list) {
				o.setCoupon(map.get(o.getCouponId()));
			}
		}
		return list;
	}

	public void updateCouponPicpath(long couponId, long photoId) {
		ObjPhoto photo = this.objPhotoService.getObjPhoto(photoId);
		if (photo != null) {
			Coupon coupon = this.couponService.getCoupon(couponId);
			if (coupon != null) {
				coupon.setPicpath(photo.getPath());
				this.couponService.updateCoupon(coupon);
			}
		}
	}

	public void updateCouponPicpath(Coupon coupon, File file)
			throws ImageException, NotPermitImageFormatException,
			OutOfSizeException {
		ObjPhoto objPhoto = new ObjPhoto();
		objPhoto.setUserId(coupon.getUserId());
		objPhoto.setCompanyId(coupon.getCompanyId());
		this.objPhotoService.createObjPhoto(objPhoto, file);
		coupon.setPicpath(objPhoto.getPath());
		this.couponService.updateCoupon(coupon);
	}

	/**
	 * 随即取满足发布条件的优惠券，先取推荐的，如果没有再取随机的
	 * 
	 * @param cityId
	 * @param size
	 * @return
	 *         2010-5-7
	 */
	public List<Coupon> getRandomUsefulCouponList(int cityId, int size) {
		int randomnum = DataUtil.getRandomNumber(9);
		boolean cityfirst = true;
		if (randomnum % 2 == 0) {
			cityfirst = false;
		}
		if (cityfirst) {// 先从城市取
			List<Coupon> list = this.getRandomUsefulCouponListByCityId(cityId,
					size);
			if (list.size() == 0) {
				list.addAll(this.getRandomUsefulCouponListByCityId(0, size));
			}
			return list;
		}
		List<Coupon> list = this.getRandomUsefulCouponListByCityId(0, size);
		if (list.size() == 0) {
			list.addAll(this.getRandomUsefulCouponListByCityId(cityId, size));
		}
		return list;
	}

	private List<Coupon> getRandomUsefulCouponListByCityId(int cityId, int size) {
		int count = this.couponService.countCouponByCityIdForUseful(cityId);
		int begin = DataUtil.getRandomPageBegin(count, size);
		return this.couponService.getCouponListByCityIdForUseful(cityId, begin,
				size);
	}

	public List<UserCoupon> getUserCouponListByCompanyId(long couponId,
			boolean buildUser, int begin, int size) {
		List<UserCoupon> list = this.couponService.getUserCouponListByCouponId(
				couponId, begin, size);
		if (buildUser) {
			this.buildUserCouponListUser(list);
		}
		return list;
	}

	public List<UserCoupon> getUserCouponListByCompanyIdAndMcode(long couponId,
			String mcode, boolean buildUser, int begin, int size) {
		List<UserCoupon> list = this.couponService
				.getUserCouponListByCouponIdAndMcode(couponId, mcode, begin,
						size);
		if (buildUser) {
			this.buildUserCouponListUser(list);
		}
		return list;
	}

	private void buildUserCouponListUser(List<UserCoupon> list) {
		List<Long> idList = new ArrayList<Long>();
		for (UserCoupon o : list) {
			idList.add(o.getUserId());
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (UserCoupon o : list) {
			o.setUser(map.get(o.getUserId()));
		}
	}
}