package com.hk.svr.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Box;
import com.hk.bean.BoxPrize;
import com.hk.bean.City;
import com.hk.bean.CmpUnion;
import com.hk.bean.CmpUnionFeed;
import com.hk.bean.Company;
import com.hk.bean.Feed;
import com.hk.bean.HkbLog;
import com.hk.bean.IpCityRange;
import com.hk.bean.ObjPhoto;
import com.hk.bean.Province;
import com.hk.bean.TmpData;
import com.hk.bean.User;
import com.hk.bean.UserBoxPrize;
import com.hk.bean.UserEquipment;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.BoxOpenResult;
import com.hk.svr.BoxService;
import com.hk.svr.CmpUnionMessageService;
import com.hk.svr.CmpUnionService;
import com.hk.svr.CompanyService;
import com.hk.svr.FeedService;
import com.hk.svr.IpCityService;
import com.hk.svr.ObjPhotoService;
import com.hk.svr.TmpDataService;
import com.hk.svr.UserService;
import com.hk.svr.ZoneService;
import com.hk.svr.box.exception.BoxKeyDuplicateException;
import com.hk.svr.box.exception.PrizeCountMoreThanBoxCountException;
import com.hk.svr.box.exception.PrizeTotalCountException;
import com.hk.svr.equipment.HandleEquipmentProcessor;
import com.hk.svr.pub.CheckInPointConfig;
import com.hk.svr.pub.CmpUnionMessageUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkbConfig;

public class BoxProcessor {

	@Autowired
	private BoxService boxService;

	@Autowired
	private UserService userService;

	@Autowired
	private FeedService feedService;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpUnionService cmpUnionService;

	@Autowired
	private CmpUnionMessageService cmpUnionMessageService;

	@Autowired
	private HandleEquipmentProcessor handleEquipmentProcessor;

	@Autowired
	private ObjPhotoService objPhotoService;

	@Autowired
	private TmpDataService tmpDataService;

	@Autowired
	private ZoneService zoneService;

	public List<UserBoxPrize> getUserBoxPrizeListByBoxId(long boxId,
			boolean buildBoxPrize, boolean buildUser, int begin, int size) {
		List<UserBoxPrize> list = this.boxService.getUserBoxPrizeListByBoxId(
				boxId, begin, size);
		this.buildUserBoxPrizeList(list, buildBoxPrize, buildUser);
		return list;
	}

	public List<UserBoxPrize> getUserBoxPrizeListByBoxIdAndPrizeNumAndPrizePwd(
			long boxId, String prizeNum, String prizePwd,
			boolean buildBoxPrize, boolean buildUser, int begin, int size) {
		List<UserBoxPrize> list = this.boxService
				.getUserBoxPrizeListByBoxIdAndNumAndPwd(boxId, prizeNum,
						prizePwd, begin, size);
		this.buildUserBoxPrizeList(list, buildBoxPrize, buildUser);
		return list;
	}

	private void buildUserBoxPrizeList(List<UserBoxPrize> list,
			boolean buildBoxPrize, boolean buildUser) {
		if (buildBoxPrize) {
			List<Long> idList = new ArrayList<Long>();
			for (UserBoxPrize o : list) {
				idList.add(o.getPrizeId());
			}
			Map<Long, BoxPrize> map = this.boxService
					.getBoxPrizeMapInId(idList);
			for (UserBoxPrize o : list) {
				o.setBoxPrize(map.get(o.getPrizeId()));
			}
		}
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (UserBoxPrize o : list) {
				idList.add(o.getUserId());
			}
			Map<Long, User> map = this.userService.getUserMapInId(idList);
			for (UserBoxPrize o : list) {
				o.setUser(map.get(o.getUserId()));
			}
		}
	}

	public List<UserBoxPrize> getUserBoxPrizeListByBoxIdAndDrawflg(long boxId,
			byte drawflg, boolean buildBoxPrize, boolean buildUser, int begin,
			int size) {
		List<UserBoxPrize> list = this.boxService
				.getUserBoxPrizeListByBoxIdAndDrawflg(boxId, drawflg, begin,
						size);
		this.buildUserBoxPrizeList(list, buildBoxPrize, buildUser);
		return list;
	}

	/**
	 * 获得用户获得的奖品
	 * 
	 * @param userId
	 * @param buildBoxPrize true:组装奖品
	 * @param begin
	 * @param size
	 * @return
	 *         2010-4-24
	 */
	public List<UserBoxPrize> getUserBoxPrizeListByUserId(long userId,
			boolean buildBoxPrize, int begin, int size) {
		List<UserBoxPrize> list = this.boxService.getUserBoxPrizeListByUserId(
				userId, begin, size);
		if (buildBoxPrize) {
			List<Long> idList = new ArrayList<Long>();
			for (UserBoxPrize o : list) {
				idList.add(o.getPrizeId());
			}
			Map<Long, BoxPrize> map = this.boxService
					.getBoxPrizeMapInId(idList);
			for (UserBoxPrize o : list) {
				o.setBoxPrize(map.get(o.getPrizeId()));
			}
		}
		return list;
	}

	public UpdateBoxResult updateBox(Box box, String zoneName,
			int oldTotalCount, int newTotalCount, boolean checkHkb)
			throws BoxKeyDuplicateException,
			PrizeCountMoreThanBoxCountException {
		int cityId = 0;
		UpdateBoxResult updateBoxResult = new UpdateBoxResult();
		if (!DataUtil.isEmpty(zoneName)) {
			String _zoneName = DataUtil.filterZoneName(zoneName);
			City city = this.zoneService.getCityLike(_zoneName);
			if (city == null) {
				updateBoxResult.setErrorCode(Err.ZONE_NAME_ERROR);
				// 到省表中查找
				Province province = this.zoneService.getProvinceLike(_zoneName);
				if (province == null) {
					updateBoxResult.setErrorCode(Err.ZONE_NAME_ERROR);
					return updateBoxResult;
				}
				updateBoxResult.setErrorCode(Err.ZONE_NAME_ERROR);
				updateBoxResult.setProvinceId(province.getProvinceId());
				return updateBoxResult;
			}
			cityId = city.getCityId();
		}
		if (box.getCityId() <= 0) {
			box.setCityId(cityId);
		}
		this.boxService.updateBox(box);
		int remainBoxCount = oldTotalCount - newTotalCount;
		if (checkHkb) {
			// 检查箱子数量是否变化，如果有变化，就更新相应的酷币值
			if (remainBoxCount != 0) {
				int addHkb = remainBoxCount;
				HkbLog hkbLog = HkbLog.create(box.getUserId(),
						HkbConfig.CREATEBOX, box.getBoxId(), addHkb);
				this.userService.addHkb(hkbLog);
			}
		}
		updateBoxResult.setErrorCode(Err.SUCCESS);
		return updateBoxResult;
	}

	/**
	 * 需要地区查找，如果找不到，会返回找不到地区的错误代码，如果在省表中找到，会返回省的id，以及错误代码
	 * 
	 * @param box
	 * @param zoneName
	 * @param saveBoxDataInTmp 当找不到地区的时候是否存储临时数据
	 * @return
	 * @throws BoxKeyDuplicateException
	 *             2010-4-24
	 */
	public synchronized CreateBoxResult createTmpBox(Box box,
			boolean saveBoxDataInTmp, String zoneName, boolean checkHkb)
			throws BoxKeyDuplicateException {
		int cityId = 0;
		CreateBoxResult createBoxResult = new CreateBoxResult();
		if (!DataUtil.isEmpty(zoneName)) {
			String _zoneName = DataUtil.filterZoneName(zoneName);
			City city = this.zoneService.getCityLike(_zoneName);
			if (city == null) {
				createBoxResult.setErrorCode(Err.ZONE_NAME_ERROR);
				// 到省表中查找
				Province province = this.zoneService.getProvinceLike(_zoneName);
				if (province == null) {
					createBoxResult.setErrorCode(Err.ZONE_NAME_ERROR);
					return createBoxResult;
				}
				TmpData tmpData = this.tmpDataService
						.getLastTmpDataByUserIdAndDataType(box.getUserId(),
								TmpData.DATATYPE_BOX);
				if (tmpData == null) {
					tmpData = new TmpData();
					tmpData.setUserId(box.getUserId());
					tmpData.setDatatype(TmpData.DATATYPE_BOX);
					tmpData.setData(box.toJsonData());
					this.tmpDataService.createTmpData(tmpData);
				}
				createBoxResult.setOid(tmpData.getOid());
				createBoxResult.setErrorCode(Err.ZONE_NAME_ERROR);
				createBoxResult.setProvinceId(province.getProvinceId());
				return createBoxResult;
			}
			cityId = city.getCityId();
		}
		if (checkHkb) {
			UserOtherInfo userOtherInfo = this.userService.getUserOtherInfo(box
					.getUserId());
			if (userOtherInfo.getHkb() < box.getTotalCount()) {
				createBoxResult.setErrorCode(Err.BOX_NOENOUGH_HKB_CREATEBOX);
				return createBoxResult;
			}
		}
		if (box.getCityId() <= 0) {
			box.setCityId(cityId);
		}
		this.boxService.createTmpBox(box);
		this.boxCreated(box);
		this.tmpDataService.deleteTmpDataByUserIdAndDataType(box.getUserId(),
				TmpData.DATATYPE_BOX);
		if (checkHkb) {
			int addHkb = -box.getTotalCount();
			HkbLog hkbLog = HkbLog.create(box.getUserId(), HkbConfig.CREATEBOX,
					box.getBoxId(), addHkb);
			this.userService.addHkb(hkbLog);
		}
		createBoxResult.setErrorCode(Err.SUCCESS);
		return createBoxResult;
	}

	public void updateBoxPrizePhoto(Box box, BoxPrize boxPrize, File file)
			throws ImageException, NotPermitImageFormatException,
			OutOfSizeException {
		if (file != null) {
			ObjPhoto objPhoto = new ObjPhoto();
			objPhoto.setUserId(box.getUserId());
			objPhoto.setCompanyId(box.getCompanyId());
			this.objPhotoService.createObjPhoto(objPhoto, file);
			boxPrize.setPath(objPhoto.getPath());
			this.boxService.updateBoxPrizePath(boxPrize.getPrizeId(), objPhoto
					.getPath());
			this.updateBoxPhoto(box, objPhoto.getPath());
		}
	}

	public void updateBoxPrizePhoto(Box box, long prizeId, long photoId) {
		ObjPhoto objPhoto = this.objPhotoService.getObjPhoto(photoId);
		if (objPhoto == null) {
			return;
		}
		this.boxService.updateBoxPrizePath(prizeId, objPhoto.getPath());
		this.updateBoxPhoto(box, objPhoto.getPath());
	}

	private void updateBoxPhoto(Box box, String path) {
		if (box.getPath() == null) {
			box.setPath(path);
			try {
				this.boxService.updateBox(box);
			}
			catch (BoxKeyDuplicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (PrizeCountMoreThanBoxCountException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建宝箱奖品，并设置宝箱图片
	 * 
	 * @param box
	 * @param boxPrize
	 * @param file
	 * @throws PrizeTotalCountException
	 * @throws ImageException
	 * @throws NotPermitImageFormatException
	 * @throws OutOfSizeException
	 *             2010-4-18
	 */
	public void createBoxPrize(BoxPrize boxPrize)
			throws PrizeTotalCountException {
		this.boxService.createBoxPrize(boxPrize);
	}

	/**
	 * 删除宝箱奖品，并更新宝箱图片
	 * 
	 * @param box
	 * @param prizeId
	 *            2010-4-18
	 */
	public void deleteBoxPrize(Box box, BoxPrize boxPrize) {
		// 如果删除的奖品图片与宝箱图片一致，就更新宝箱图片
		this.boxService.deleteBoxPrize(boxPrize.getPrizeId());
		if (boxPrize.getPath() != null && box.getPath() != null
				&& boxPrize.getPath().equals(box.getPath())) {
			List<BoxPrize> list = this.boxService.getBoxPrizeListByBoxId(box
					.getBoxId());
			for (BoxPrize o : list) {
				if (o.getPath() != null) {
					box.setPath(o.getPath());
					try {
						this.boxService.updateBox(box);
					}
					catch (BoxKeyDuplicateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (PrizeCountMoreThanBoxCountException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 开箱子，并且获得随机道具
	 * 
	 * @param userId
	 * @param boxId
	 * @param ip
	 * @return
	 *         2010-4-18
	 */
	public BoxProccessorOpenResult openBox2(long userId, int pcityId, Box box,
			String ip) {
		long boxId = box.getBoxId();
		int boxCityId = box.getCityId();
		BoxProccessorOpenResult boxProccessorOpenResult = new BoxProccessorOpenResult();
		if (boxCityId > 0) {
			if (pcityId != boxCityId) {
				boxProccessorOpenResult.setErrorCode(Err.OPENBOX_CITY_ERROR);
				return boxProccessorOpenResult;
			}
		}
		UserOtherInfo userOtherInfo = this.userService.getUserOtherInfo(userId);
		if (userOtherInfo.getPoints() < CheckInPointConfig.getOpenBoxPoints()) {
			boxProccessorOpenResult.setNoEnoughPoints(true);
			return boxProccessorOpenResult;
		}
		BoxOpenResult boxOpenResult = this.boxService.openBox2(userId, boxId);
		boxProccessorOpenResult.setBoxOpenResult(boxOpenResult);
		if (boxOpenResult.getErrorCode() == 0) {
			// 如果支持副产品，可以开副产品
			if (box.isOtherPrize()) {
				UserEquipment userEquipment = this.handleEquipmentProcessor
						.processGet(userId);
				boxOpenResult.setUserEquipment(userEquipment);
			}
			this.userService.addPoints(userId, -CheckInPointConfig
					.getOpenBoxPoints());
			this.boxOpened(boxOpenResult, userId, boxId, ip);
		}
		return boxProccessorOpenResult;
	}

	private void boxCreated(Box box) {
		this.createCmpUnionFeed(box);
	}

	/**
	 * 如果足迹已经加入某个联盟，创建足迹在联盟中的动态
	 * 
	 * @param box
	 */
	private void createCmpUnionFeed(Box box) {
		long companyId = box.getCompanyId();
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
		box.setUid(cmpUnion.getUid());
		CmpUnionFeed feed = new CmpUnionFeed();
		feed.setUid(cmpUnion.getUid());
		feed.setFeedflg(CmpUnionMessageUtil.FEED_CREATEBOX);
		Map<String, String> map = new HashMap<String, String>();
		map.put("cmp", company.getName());
		map.put("boxid", box.getBoxId() + "");
		map.put("name", box.getName());
		feed.setData(DataUtil.toJson(map));
		feed.setObjId(companyId);
		cmpUnionMessageService.createCmpUnionFeed(feed);
	}

	private void boxOpened(BoxOpenResult boxOpenResult, long userId,
			long boxId, String ip) {
		this.feed(boxOpenResult.getUserBoxPrize(), userId, boxId, ip);
	}

	private void feed(UserBoxPrize userBoxPrize, long userId, long boxId,
			String ip) {
		Feed feed = new Feed();
		feed.setUserId(userId);
		feed.setCreateTime(new Date());
		Map<String, String> map = new HashMap<String, String>();
		map.put("usernickname", this.userService.getUser(userId).getNickName());
		map.put("boxid", String.valueOf(boxId));
		map.put("boxname", this.boxService.getBox(boxId).getName());
		if (userBoxPrize != null) {
			map.put("prizeid", String.valueOf(userBoxPrize.getPrizeId()));
			map.put("prizename", this.boxService.getBoxPrize(
					userBoxPrize.getPrizeId()).getName());
		}
		feed.setData(DataUtil.toJson(map));
		feed.setFeedType(Feed.FEEDTYPE_OPENBOX);
		if (ip != null) {
			feed.setIpNumber(DataUtil.parseIpNumber(ip));
			IpCityRange range = this.ipCityService.getIpCityRange(ip);
			if (range != null) {
				feed.setRangeId(range.getRangeId());
				feed.setCityId(range.getCityId());
			}
		}
		this.feedService.createFeed(feed, null);
	}
}