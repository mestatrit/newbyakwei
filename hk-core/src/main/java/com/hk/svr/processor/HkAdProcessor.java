package com.hk.svr.processor;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.HkAd;
import com.hk.bean.HkbLog;
import com.hk.bean.TmpData;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.svr.HkAdService;
import com.hk.svr.TmpDataService;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.pub.ImageConfig;

public class HkAdProcessor extends BaseProcessor {

	@Autowired
	private HkAdService hkAdService;

	@Autowired
	private UserService userService;

	@Autowired
	private TmpDataService tmpDataService;

	public CreateHkAdResult createHkAd(HkAd hkAd, File file, String adData,
			String zoneName) throws IOException {
		if (!hkAd.isImageShow()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("addata", adData);
			hkAd.setData(DataUtil.toJson(map));
		}
		CreateHkAdResult r = new CreateHkAdResult();
		int cityId = 0;
		if (!DataUtil.isEmpty(zoneName)) {
			ZoneResult zoneResult = this.findZone(zoneName);
			if (zoneResult == null) {
				r.setErrorCode(Err.ZONE_NAME_ERROR);
				return r;
			}
			// 在生表中找到，进行特殊处理
			if (zoneResult.getProvinceId() > 0) {
				r.setErrorCode(Err.ZONE_NAME_ERROR);
				r.setProvinceId(zoneResult.getProvinceId());
				// 保存优惠券数据到临时表中
				TmpData tmpData = this.tmpDataService
						.getLastTmpDataByUserIdAndDataType(hkAd.getUserId(),
								TmpData.DATATYPE_HKAD);
				if (tmpData != null) {
					this.tmpDataService.deleteTmpData(tmpData.getOid());
				}
				tmpData = new TmpData();
				tmpData.setUserId(hkAd.getUserId());
				tmpData.setDatatype(TmpData.DATATYPE_HKAD);
				tmpData.setData(hkAd.toJsonData());
				this.tmpDataService.createTmpData(tmpData);
				r.setOid(tmpData.getOid());
				return r;
			}
			cityId = zoneResult.getCityId();
		}
		if (file == null && hkAd.isImageShow()) {
			r.setErrorCode(Err.HKAD_FILE_ERROR);
			return r;
		}
		UserOtherInfo info = this.userService
				.getUserOtherInfo(hkAd.getUserId());
		int totalhkb = hkAd.getTotalViewCount() * HkbConfig.getViewHkAd();
		if (totalhkb < info.getHkb()) {
			r.setRemainHkb(info.getHkb());
			r.setErrorCode(Err.HKAD_NOENOUGH_HKB_VIEW);
			return r;
		}
		hkAd.setCityId(cityId);
		this.hkAdService.createHkAd(hkAd);
		this.tmpDataService.deleteTmpDataByUserIdAndDataType(hkAd.getUserId(),
				TmpData.DATATYPE_HKAD);
		this.savePath(hkAd, file);
		if (totalhkb > 0) {
			HkbLog hkbLog = HkbLog.create(hkAd.getUserId(), HkLog.CREATEHKAD,
					hkAd.getOid(), -totalhkb);
			this.userService.addHkb(hkbLog);
		}
		r.setErrorCode(Err.SUCCESS);
		return r;
	}

	private void savePath(HkAd hkAd, File file) throws IOException {
		if (file != null && hkAd.isImageShow()) {
			String dbPath = ImageConfig.getHkAdPicDbPath(hkAd.getOid());
			String filePath = ImageConfig.getHkAdPicFilePath(dbPath);
			DataUtil.copyFile(file, filePath, "h.jpg");
			Map<String, String> map = new HashMap<String, String>();
			map.put("imgpath", dbPath);
			hkAd.setData(DataUtil.toJson(map));
			this.hkAdService.updateHkAd(hkAd);
		}
	}

	public UpdateHkAdResult updateHkAd(HkAd hkAd, File file, String adData,
			String zoneName) throws IOException {
		UpdateHkAdResult r = new UpdateHkAdResult();
		int cityId = 0;
		if (!DataUtil.isEmpty(zoneName)) {
			ZoneResult zoneResult = this.findZone(zoneName);
			if (zoneResult == null) {
				r.setErrorCode(Err.ZONE_NAME_ERROR);
				return r;
			}
			// 在生表中找到，进行特殊处理
			if (zoneResult.getProvinceId() > 0) {
				r.setErrorCode(Err.ZONE_NAME_ERROR);
				r.setProvinceId(zoneResult.getProvinceId());
				return r;
			}
			cityId = zoneResult.getCityId();
		}
		HkAd old = this.hkAdService.getHkAd(hkAd.getOid());
		// 差额显示数据
		int amount = hkAd.getTotalViewCount() - old.getTotalViewCount();
		// 如果增加了显示次数
		if (amount > 0) {
			UserOtherInfo info = this.userService.getUserOtherInfo(hkAd
					.getUserId());
			// 如果增加的差额酷币大于当前余额，就不能更新
			if (info.getHkb() < amount * HkbConfig.getViewHkAd()) {
				r.setRemainHkb(info.getHkb());
				r.setErrorCode(Err.HKAD_NOENOUGH_HKB_VIEW);
				return r;
			}
		}
		hkAd.setCityId(cityId);
		if (file == null && hkAd.isImageShow()) {
			r.setErrorCode(Err.HKAD_FILE_ERROR);
			return r;
		}
		if (!hkAd.isImageShow()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("addata", adData);
			hkAd.setData(DataUtil.toJson(map));
		}
		int err = this.hkAdService.updateHkAd(hkAd);
		this.savePath(hkAd, file);
		if (err == Err.SUCCESS && amount != 0) {
			HkbLog hkbLog = HkbLog.create(hkAd.getUserId(), HkLog.CREATEHKAD,
					hkAd.getOid(), -amount * HkbConfig.getViewHkAd());
			this.userService.addHkb(hkbLog);
		}
		r.setErrorCode(err);
		return r;
	}

	public void deleteHkAd(long oid) {
		HkAd o = this.hkAdService.getHkAd(oid);
		if (o == null) {
			return;
		}
		int remain = o.getTotalViewCount() - o.getViewCount();
		if (remain > 0) {
			HkbLog hkbLog = HkbLog.create(o.getUserId(), HkLog.HKAD_REMAIN, 0,
					remain * HkbConfig.getViewHkAd());
			this.userService.addHkb(hkbLog);
		}
		this.hkAdService.deleteHkAd(oid);
	}

	/**
	 * 随即取满足发布条件的优惠券，先取推荐的，如果没有再取随机的
	 * 
	 * @param cityId
	 * @param size
	 * @return
	 *         2010-5-7
	 */
	public List<HkAd> getRandomUsefulHkAdList(int cityId, String viewerId,
			int size) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DATE);
		int udate = Integer.valueOf(new StringBuilder(year).append(month)
				.append(date).toString());
		List<Long> idList = this.hkAdService
				.getHkAdIdListByViewerIdAndUdateForViewOk(viewerId, udate, 3);
		int randomnum = DataUtil.getRandomNumber(9);
		boolean cityfirst = true;
		byte showflg = HkAd.SHOWFLG_IMG;
		if (randomnum % 2 == 0) {
			cityfirst = false;
			showflg = HkAd.SHOWFLG_CHAR;
		}
		if (cityfirst) {// 先从城市取
			List<HkAd> list = this.getRandomUsefulHkAdListByCityId(cityId,
					idList, showflg, size);
			if (list.size() == 0) {
				list.addAll(this.getRandomUsefulHkAdListByCityId(0, idList,
						showflg, size));
			}
			return list;
		}
		List<HkAd> list = this.getRandomUsefulHkAdListByCityId(0, idList,
				showflg, size);
		if (list.size() == 0) {
			list.addAll(this.getRandomUsefulHkAdListByCityId(cityId, idList,
					showflg, size));
		}
		return list;
	}

	private List<HkAd> getRandomUsefulHkAdListByCityId(int cityId,
			List<Long> idList, byte showflg, int size) {
		int count = this.hkAdService.countHkAdByCityIdForUsefulNotInId(cityId,
				showflg, idList);
		int begin = DataUtil.getRandomPageBegin(count, size);
		return this.hkAdService.getHkAdListByCityIdForUsefulNotInId(cityId,
				showflg, idList, begin, size);
	}
}