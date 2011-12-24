package com.hk.svr.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.City;
import com.hk.bean.CmpTip;
import com.hk.bean.Company;
import com.hk.bean.CompanyPhoto;
import com.hk.bean.CompanyUserStatus;
import com.hk.bean.Feed;
import com.hk.bean.FeedInfo;
import com.hk.bean.IpCityRange;
import com.hk.bean.Province;
import com.hk.bean.TmpData;
import com.hk.bean.User;
import com.hk.bean.UserCmpTip;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpProductService;
import com.hk.svr.CmpTipService;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.FeedService;
import com.hk.svr.IpCityService;
import com.hk.svr.TmpDataService;
import com.hk.svr.UpdateCompanyResult;
import com.hk.svr.UserService;
import com.hk.svr.ZoneService;
import com.hk.svr.pub.CheckInPointConfig;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

public class CompanyProcessor extends BaseProcessor {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private FeedService feedService;

	@Autowired
	private CmpTipService cmpTipService;

	@Autowired
	private UserService userService;

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private TmpDataService tmpDataService;

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private CompanyPhotoService companyPhotoService;

	public List<Company> getCompanyListWithSearch(int pcityId, String key,
			int begin, int size) {
		List<Long> cmpidList = this.companyService.getCompanyIdListWithSearch(
				pcityId, key, begin, size);
		Map<Long, Company> map = this.companyService
				.getCompanyMapInId(cmpidList);
		List<Company> list = new ArrayList<Company>();
		for (Long l : cmpidList) {
			if (map.containsKey(l)) {
				list.add(map.get(l));
			}
		}
		return list;
	}

	public void createCompany(Company company, String ip) {
		this.companyService.createCompany(company, null);
		this.onCompanyCreated(company, ip);
	}

	public void deleteCompany(long companyId) {
		this.deleteCompanyFeed(companyId);
		this.companyService.deleteCompany(companyId);
	}

	private void deleteCompanyFeed(long companyId) {
		List<Long> idList = this.feedService
				.getFeedIdListFromFeedInfoByObjIdAndFeedType(companyId,
						Feed.FEEDTYPE_BECOME_MAYOR);
		idList.addAll(this.feedService
				.getFeedIdListFromFeedInfoByObjIdAndFeedType(companyId,
						Feed.FEEDTYPE_WRITETIPS));
		idList.addAll(this.feedService
				.getFeedIdListFromFeedInfoByObjIdAndFeedType(companyId,
						Feed.FEEDTYPE_GETBADGE));
		idList.addAll(this.feedService
				.getFeedIdListFromFeedInfoByObjIdAndFeedType(companyId,
						Feed.FEEDTYPE_CREATEVENUE));
		for (Long l : idList) {
			this.feedService.deleteFeed(l);
		}
	}

	public UpdateCompanyResult updateCompanyForWap(Company company,
			String zoneName, int oldPcityId) {
		UpdateCompanyResult updateCompanyResult = new UpdateCompanyResult();
		int cityId = 0;
		if (DataUtil.isEmpty(zoneName)) {
			updateCompanyResult.setErrorCode(Err.ZONE_NAME_ERROR);
			return updateCompanyResult;
		}
		if (cityId <= 0) {
			String _zoneName = DataUtil.filterZoneName(zoneName);
			City city = this.zoneService.getCityLike(_zoneName);
			if (city == null) {
				updateCompanyResult.setErrorCode(Err.ZONE_NAME_ERROR);
				// 到省表中查找
				Province province = this.zoneService.getProvinceLike(_zoneName);
				if (province == null) {
					updateCompanyResult.setErrorCode(Err.ZONE_NAME_ERROR);
					return updateCompanyResult;
				}
				updateCompanyResult.setErrorCode(Err.ZONE_NAME_ERROR);
				updateCompanyResult.setProvinceId(province.getProvinceId());
				return updateCompanyResult;
			}
			cityId = city.getCityId();
		}
		company.setPcityId(cityId);
		this.updateCompany(company, oldPcityId);
		updateCompanyResult.setErrorCode(Err.SUCCESS);
		return updateCompanyResult;
	}

	public CreateCompanyResult createCompanyForWap(Company company,
			String zoneName, String ip) {
		CreateCompanyResult createCompanyResult = new CreateCompanyResult();
		int cityId = 0;
		if (DataUtil.isEmpty(zoneName)) {
			createCompanyResult.setErrorCode(Err.ZONE_NAME_ERROR);
			return createCompanyResult;
		}
		if (cityId <= 0) {
			String _zoneName = DataUtil.filterZoneName(zoneName);
			City city = this.zoneService.getCityLike(_zoneName);
			if (city == null) {
				createCompanyResult.setErrorCode(Err.ZONE_NAME_ERROR);
				// 到省表中查找
				Province province = this.zoneService.getProvinceLike(_zoneName);
				if (province == null) {
					createCompanyResult.setErrorCode(Err.ZONE_NAME_ERROR);
					return createCompanyResult;
				}
				TmpData tmpData = this.tmpDataService
						.getLastTmpDataByUserIdAndDataType(company
								.getCreaterId(), TmpData.DATATYPE_CMP);
				if (tmpData == null) {
					tmpData = new TmpData();
					tmpData.setUserId(company.getCreaterId());
					tmpData.setDatatype(TmpData.DATATYPE_CMP);
					tmpData.setData(company.toJsonData());
					this.tmpDataService.createTmpData(tmpData);
				}
				else {
					tmpData.setData(company.toJsonData());
					this.tmpDataService.updateTmpData(tmpData);
				}
				createCompanyResult.setOid(tmpData.getOid());
				createCompanyResult.setErrorCode(Err.ZONE_NAME_ERROR);
				createCompanyResult.setProvinceId(province.getProvinceId());
				return createCompanyResult;
			}
			cityId = city.getCityId();
		}
		company.setPcityId(cityId);
		this.createCompany(company, ip);
		this.tmpDataService.deleteTmpDataByUserIdAndDataType(company
				.getCreaterId(), TmpData.DATATYPE_CMP);
		createCompanyResult.setErrorCode(Err.SUCCESS);
		return createCompanyResult;
	}

	public UpdateCompanyResult updateCompany(Company company, int oldPcityId) {
		UpdateCompanyResult updateCompanyResult = new UpdateCompanyResult();
		Company old = this.companyService.getCompany(company.getCompanyId());
		if (!old.getName().equals(company.getName())) {
			updateCompanyResult.setNameChanged(true);
		}
		this.companyService.updateCompany(company);
		this.onCompanyUpdated(company, updateCompanyResult);
		if (oldPcityId != company.getPcityId()) {
			this.cmpProductService.updateCmpProductPcityIdByCompanyId(company
					.getCompanyId(), company.getPcityId());
		}
		return updateCompanyResult;
	}

	public void updateHeadPath(long companyId, long photoId) {
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		if (companyPhoto == null) {
			return;
		}
		this.companyService.updateHeadPath(companyId, companyPhoto.getPath());
	}

	public void onCompanyCreated(Company company, String ip) {
		long userId = company.getCreaterId();
		this.userService.addPoints(userId, CheckInPointConfig
				.getCompanyCreate());
		Feed feed = new Feed();
		feed.setUserId(userId);
		feed.setCityId(company.getPcityId());
		feed.setCreateTime(new Date());
		feed.setFeedType(Feed.FEEDTYPE_CREATEVENUE);
		if (ip != null) {
			IpCityRange ipCityRange = this.ipCityService.getIpCityRange(ip);
			if (ipCityRange != null) {
				feed.setRangeId(ipCityRange.getRangeId());
				feed.setIpNumber(DataUtil.parseIpNumber(ip));
			}
		}
		Map<String, String> map = new HashMap<String, String>();
		User user = this.userService.getUser(feed.getUserId());
		map.put("nickname", user.getNickName());
		map.put("headpath", user.getHeadPath());
		map.put("companyid", String.valueOf(company.getCompanyId()));
		map.put("cmpname", company.getName());
		feed.setData(DataUtil.toJson(map));
		FeedInfo feedInfo = feed.createFeedInfo();
		feedInfo.setObjId(company.getCompanyId());
		this.feedService.createFeed(feed, feedInfo);
	}

	public void onCompanyUpdated(Company company,
			UpdateCompanyResult updateCompanyResult) {
		if (updateCompanyResult.isNameChanged()) {
			List<Long> idList = this.feedService
					.getFeedIdListFromFeedInfoByObjIdAndFeedType(company
							.getCompanyId(), Feed.FEEDTYPE_BECOME_MAYOR);
			List<Feed> list = this.feedService.getFeedListInId(idList);
			for (Feed feed : list) {
				Map<String, String> map = DataUtil.getMapFromJson(feed
						.getData());
				map.put("cmpname", company.getName());
				feed.setData(DataUtil.toJson(map));
				this.feedService.updateFeed(feed);
			}
			List<CmpTip> cmpTipList = this.cmpTipService
					.getAllCmpTipListByCompanyId(company.getCompanyId());
			for (CmpTip o : cmpTipList) {
				this.processOnCmpTipUpdated(o);
			}
			// 更新usertips
			List<UserCmpTip> userCmpTipList = this.cmpTipService
					.getUserCmpTipListByCompanyId(company.getCompanyId());
			for (UserCmpTip userCmpTip : userCmpTipList) {
				Map<String, String> datamap = DataUtil
						.getMapFromJson(userCmpTip.getData());
				datamap.put("cmpname", company.getName());
				userCmpTip.setData(DataUtil.toJson(datamap));
				this.cmpTipService.updateUserCmpTipCompanyName(userCmpTip);
			}
		}
	}

	public List<CompanyUserStatus> getCompanyUserStatusListByCompanyIdAndUserStatus(
			long companyId, byte userStatus, boolean buildUser, int begin,
			int size) {
		List<CompanyUserStatus> list = this.companyService
				.getCompanyUserStatusListByCompanyIdAndUserStatus(companyId,
						userStatus, begin, size);
		if (buildUser) {
			this.buildCompanyUserStatusUser(list);
		}
		return list;
	}

	public List<CompanyUserStatus> getCompanyUserStatusListByCompanyIdAndDoneStatus(
			long companyId, byte doneStatus, boolean buildUser, int begin,
			int size) {
		List<CompanyUserStatus> list = this.companyService
				.getCompanyUserStatusListByCompanyIdAndDoneStatus(companyId,
						doneStatus, begin, size);
		if (buildUser) {
			this.buildCompanyUserStatusUser(list);
		}
		return list;
	}

	private void buildCompanyUserStatusUser(List<CompanyUserStatus> list) {
		List<Long> idList = new ArrayList<Long>();
		for (CompanyUserStatus o : list) {
			idList.add(o.getUserId());
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (CompanyUserStatus o : list) {
			o.setUser(map.get(o.getUserId()));
		}
	}

	/**
	 * 上传企业logo
	 * 
	 * @param companyId
	 * @param file 图片文件
	 * @return 参照 {@link Err#IMG_UPLOAD_ERROR} {@link Err#IMG_FMT_ERROR}
	 *         {@link Err#IMG_OUTOFSIZE_ERROR} {@link Err#UPLOAD_ERROR}
	 *         {@link Err#SUCCESS} 2010-5-20
	 */
	public int uploadLogo(Company company, File file, File file2) {
		if (file != null && DataUtil.isBigger(file, 200)) {
			return Err.COMPANY_LOGO_FILE_BIGGER_ERROR;
		}
		if (file2 != null && DataUtil.isBigger(file2, 200)) {
			return Err.COMPANY_LOGO2_FILE_BIGGER_ERROR;
		}
		try {
			if (file != null && !DataUtil.isImage(file)) {
				return Err.COMPANY_LOGO_FILE_MUST_IMG_ERROR;
			}
		}
		catch (IOException e) {
			return Err.COMPANY_LOGO_FILE_UPLOAD_ERROR;
		}
		try {
			if (file2 != null && !DataUtil.isImage(file2)) {
				return Err.COMPANY_LOGO2_FILE_MUST_IMG_ERROR;
			}
		}
		catch (IOException e) {
			return Err.COMPANY_LOGO2_FILE_UPLOAD_ERROR;
		}
		String dbPath = ImageConfig
				.getCompanyHeadDbPath(company.getCompanyId());
		String headPath = ImageConfig.getCompanyHeadUploadPath(dbPath);
		if (file != null) {
			try {
				DataUtil.copyFile(file, headPath, "logo.jpg");
			}
			catch (IOException e1) {
				return Err.COMPANY_LOGO_FILE_UPLOAD_ERROR;
			}
		}
		if (file2 != null) {
			try {
				DataUtil.copyFile(file2, headPath, "logo2.jpg");
			}
			catch (IOException e1) {
				return Err.COMPANY_LOGO2_FILE_UPLOAD_ERROR;
			}
		}
		if (file != null) {
			company.setLogopath(dbPath);
		}
		if (file2 != null) {
			company.setLogo2path(dbPath);
		}
		if (file != null || file2 != null) {
			this.companyService.updateCompany(company);
		}
		// try {
		// new JMagickUtil(file, 1);
		// this.companyService.updateLogo(companyId, file, file2);
		// }
		// catch (ImageException e) {
		// return Err.IMG_UPLOAD_ERROR;
		// }
		// catch (NotPermitImageFormatException e) {
		// return Err.IMG_FMT_ERROR;
		// }
		// catch (OutOfSizeException e) {
		// return Err.IMG_OUTOFSIZE_ERROR;
		// }
		// catch (IOException e) {
		// return Err.UPLOAD_ERROR;
		// }
		return Err.SUCCESS;
	}
}