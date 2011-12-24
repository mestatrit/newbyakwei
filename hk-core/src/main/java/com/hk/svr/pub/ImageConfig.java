package com.hk.svr.pub;

import java.io.File;
import java.util.Calendar;

import com.hk.bean.User;
import com.hk.frame.util.P;

public class ImageConfig {

	public static final String IMG_H32 = "h32.jpg";

	public static final String IMG_H48 = "h48.jpg";

	public static final String IMG_H80 = "h80.jpg";

	public static final String IMG_H57 = "h57.jpg";

	public static final String IMG_H300 = "h300.jpg";

	public static final String IMG_H60 = "h60.jpg";

	public static final String IMG_H120 = "h120.jpg";

	public static final String IMG_H150 = "h150.jpg";

	public static final String IMG_H180 = "h180.jpg";

	public static final String IMG_H320 = "h320.jpg";

	public static final String IMG_H240 = "h240.jpg";

	public static final String IMG_H640 = "h640.jpg";

	public static final String IMG_H500 = "h500.jpg";

	public static final String IMG_H600 = "h600.jpg";

	public static final String IMG_H800 = "h800.jpg";

	public static final String RARFILE = "f.rar";

	public static final String FLASHFILE = "f.flv";

	private static String headRootPath;

	private static String headCurrentUploadRootPath;

	private static String headUrl;

	private static String companyHeadCurrentUploadRootPath;

	private static String companyHeadUrl;

	public static String companyHeadRootPath;

	private static String userPicCurrentUploadPath;

	private static String userPicRootPath;

	private static String userPicUrl;

	private static String couponPicCurrentUploadPath;

	private static String couponPicRootPath;

	private static String couponPicUrl;

	private static String unionLogoRootPath;

	private static String unionLogoUrl;

	private static String unionLogoCurrentUploadPath;

	/**
	 * 上传时的临时目录，当用户完成切图后，需要删除目录下的图片
	 */
	private static String tempUploadRootPath;

	/**
	 * 临时目录的url
	 */
	private static String tempUploadPicUrl;

	private static String tempUploadCurrentUploadPath;

	private static String badgeRootPath;

	private static String badgeCurrentUploadRootPath;

	private static String badgeUrl;

	private static String boxPrizeRootPath;

	private static String boxPrizeCurrentUploadRootPath;

	private static String boxPrizeUrl;

	private static String photoPicCurrentUploadPath;

	private static String photoPicRootPath;

	private static String photoPicUrl;

	private static String cmpFileCurrentUploadPath;

	private static String cmpFileRootPath;

	private static String cmpfileUrl;

	private static String cmpNavCurrentUploadPath;

	private static String cmpNavRootPath;

	private static String cmpNavUrl;

	private static String cmpAdPicCurrentUploadPath;

	private static String cmpAdPicRootPath;

	private static String cmpAdPicUrl;

	private static String cmpHomePicAdRootPath;

	private static String cmpHomePicAdCurrentUploadPath;

	private static String cmpHomePicAdUrl;

	private static String cmpProductSortPicCurrentUploadPath;

	private static String cmpProductSortPicRootPath;

	private static String cmpProductSortPicUrl;

	private static String cmpDownFileRootPath;

	private static String cmpDownFileCurrentUploadPath;

	private static String cmpDownFileUrl;

	private static String cmpVideoRootPath;

	private static String cmpVideoCurrentUploadPath;

	private static String cmpVideoUrl;

	private static String cmpIdxRootPath;

	private static String cmpOrgFileCurrentUploadPath;

	private static String cmpOrgFileRootPath;

	private static String cmpOrgFileUrl;

	private static String cmpOrgBgCurrentUploadPath;

	private static String cmpOrgBgRootPath;

	private static String cmpOrgBgUrl;

	private static String cmpActorPicRootPath;

	private static String cmpActorPicCurrentUploadPath;

	private static String cmpActorPicUrl;

	public static String getCmpActorPic60Url(String dbPath) {
		return getCmpActorPicUrl() + dbPath + IMG_H60;
	}

	public static String getCmpActorPic150Url(String dbPath) {
		return getCmpActorPicUrl() + dbPath + IMG_H150;
	}

	public static String getCmpActorPic240Url(String dbPath) {
		return getCmpActorPicUrl() + dbPath + IMG_H240;
	}

	public static String getCmpActorPic320Url(String dbPath) {
		return getCmpActorPicUrl() + dbPath + IMG_H320;
	}

	public static String getCmpActorPic500Url(String dbPath) {
		return getCmpActorPicUrl() + dbPath + IMG_H500;
	}

	public static String getCmpActorPic600Url(String dbPath) {
		return getCmpActorPicUrl() + dbPath + IMG_H600;
	}

	public static String getCmpActorPic800Url(String dbPath) {
		return getCmpActorPicUrl() + dbPath + IMG_H600;
	}

	public static String getCmpActorPicFilePath(String dbPath) {
		return getCmpActorPicRootPath() + dbPath;
	}

	public static String getCmpActorPicDbPath(long companyId, String picName) {
		Calendar createDate = Calendar.getInstance();
		String year = String.valueOf(createDate.get(Calendar.YEAR));
		String month = String.valueOf(createDate.get(Calendar.MONTH) + 1);
		String date = String.valueOf(createDate.get(Calendar.DAY_OF_MONTH));
		return getCmpActorPicCurrentUploadPath() + "/"
				+ getSeparator(String.valueOf(companyId)) + "/" + year + "/"
				+ month + "/" + date + "/" + picName + "/";
	}

	public void setCmpActorPicCurrentUploadPath(
			String cmpActorPicCurrentUploadPath) {
		ImageConfig.cmpActorPicCurrentUploadPath = cmpActorPicCurrentUploadPath;
	}

	public void setCmpActorPicRootPath(String cmpActorPicRootPath) {
		ImageConfig.cmpActorPicRootPath = cmpActorPicRootPath;
	}

	public void setCmpActorPicUrl(String cmpActorPicUrl) {
		ImageConfig.cmpActorPicUrl = cmpActorPicUrl;
	}

	public static String getCmpActorPicCurrentUploadPath() {
		return cmpActorPicCurrentUploadPath;
	}

	public static String getCmpActorPicRootPath() {
		return cmpActorPicRootPath;
	}

	public static String getCmpActorPicUrl() {
		return cmpActorPicUrl;
	}

	public static String getCmpOrgBgUrl(String dbPath) {
		return getCmpOrgBgUrl() + dbPath + "h.jpg";
	}

	public static String getCmpOrgBgFilePath(String dbPath) {
		return getCmpOrgBgRootPath() + dbPath;
	}

	public static String getCmpOrgBgDbPath(long companyId, long orgId) {
		return getCmpOrgBgCurrentUploadPath() + "/"
				+ getSeparator(companyId + "") + "/" + getSeparator(orgId + "")
				+ "/";
	}

	public static String getCmpOrgBgCurrentUploadPath() {
		return cmpOrgBgCurrentUploadPath;
	}

	public static String getCmpOrgBgRootPath() {
		return cmpOrgBgRootPath;
	}

	public static String getCmpOrgBgUrl() {
		return cmpOrgBgUrl;
	}

	public void setCmpOrgBgCurrentUploadPath(String cmpOrgBgCurrentUploadPath) {
		ImageConfig.cmpOrgBgCurrentUploadPath = cmpOrgBgCurrentUploadPath;
	}

	public void setCmpOrgBgRootPath(String cmpOrgBgRootPath) {
		ImageConfig.cmpOrgBgRootPath = cmpOrgBgRootPath;
	}

	public void setCmpOrgBgUrl(String cmpOrgBgUrl) {
		ImageConfig.cmpOrgBgUrl = cmpOrgBgUrl;
	}

	public static String getCmpOrgFileCurrentUploadPath() {
		return cmpOrgFileCurrentUploadPath;
	}

	public void setCmpOrgFileCurrentUploadPath(
			String cmpOrgFileCurrentUploadPath) {
		ImageConfig.cmpOrgFileCurrentUploadPath = cmpOrgFileCurrentUploadPath;
	}

	public static String getCmpOrgFileRootPath() {
		return cmpOrgFileRootPath;
	}

	public void setCmpOrgFileRootPath(String cmpOrgFileRootPath) {
		ImageConfig.cmpOrgFileRootPath = cmpOrgFileRootPath;
	}

	public static String getCmpOrgFileUrl() {
		return cmpOrgFileUrl;
	}

	public void setCmpOrgFileUrl(String cmpOrgFileUrl) {
		ImageConfig.cmpOrgFileUrl = cmpOrgFileUrl;
	}

	public static String getCmpOrgFile60Url(String dbPath) {
		return getCmpOrgFileUrl() + dbPath + IMG_H60;
	}

	public static String getCmpOrgFile120Url(String dbPath) {
		return getCmpOrgFileUrl() + dbPath + IMG_H120;
	}

	public static String getCmpOrgFile240Url(String dbPath) {
		return getCmpOrgFileUrl() + dbPath + IMG_H240;
	}

	public static String getCmpOrgFile320Url(String dbPath) {
		return getCmpOrgFileUrl() + dbPath + IMG_H320;
	}

	public static String getCmpOrgFile600Url(String dbPath) {
		return getCmpOrgFileUrl() + dbPath + IMG_H600;
	}

	public static String getCmpOrgFile800Url(String dbPath) {
		return getCmpOrgFileUrl() + dbPath + IMG_H800;
	}

	public static String getCmpOrgFileFilePath(String dbPath) {
		return getCmpOrgFileRootPath() + dbPath;
	}

	public static String getCmpOrgFileDbPath(long companyId, long orgId,
			String photoName) {
		Calendar createDate = Calendar.getInstance();
		String year = String.valueOf(createDate.get(Calendar.YEAR));
		String month = String.valueOf(createDate.get(Calendar.MONTH) + 1);
		String date = String.valueOf(createDate.get(Calendar.DAY_OF_MONTH));
		return getCmpFileCurrentUploadPath() + "/"
				+ getSeparator(String.valueOf(companyId)) + "/"
				+ getSeparator(String.valueOf(orgId)) + "/" + year + "/"
				+ month + "/" + date + "/" + photoName + "/";
	}

	public void setCmpIdxRootPath(String cmpIdxRootPath) {
		ImageConfig.cmpIdxRootPath = cmpIdxRootPath;
	}

	public static String getCmpIdxRootPath() {
		return cmpIdxRootPath;
	}

	public static String getCmpIdxDir(long companyId) {
		String cmpIdxCurrentPath = "/a";
		return getCmpIdxRootPath() + cmpIdxCurrentPath + "/"
				+ getSeparator(companyId + "");
	}

	public void setCmpVideoCurrentUploadPath(String cmpVideoCurrentUploadPath) {
		ImageConfig.cmpVideoCurrentUploadPath = cmpVideoCurrentUploadPath;
	}

	public void setCmpVideoRootPath(String cmpVideoRootPath) {
		ImageConfig.cmpVideoRootPath = cmpVideoRootPath;
	}

	public void setCmpVideoUrl(String cmpVideoUrl) {
		ImageConfig.cmpVideoUrl = cmpVideoUrl;
	}

	public static String getCmpVideoCurrentUploadPath() {
		return cmpVideoCurrentUploadPath;
	}

	public static String getCmpVideoRootPath() {
		return cmpVideoRootPath;
	}

	public static String getCmpVideoUrl() {
		return cmpVideoUrl;
	}

	public static String getCmpVideoFilePath(String dbPath) {
		return getCmpVideoRootPath() + dbPath;
	}

	public static String getCmpDownFileFilePath(String dbPath) {
		return getCmpDownFileRootPath() + dbPath;
	}

	public static String getCmpVideoSaveToDBPath(long companyid, String fileName) {
		Calendar createDate = Calendar.getInstance();
		String year = String.valueOf(createDate.get(Calendar.YEAR));
		String month = String.valueOf(createDate.get(Calendar.MONTH) + 1);
		String date = String.valueOf(createDate.get(Calendar.DAY_OF_MONTH));
		return getCmpVideoCurrentUploadPath() + "/"
				+ getSeparator(String.valueOf(companyid)) + "/" + year + "/"
				+ month + "/" + date + "/" + fileName + "/";
	}

	public static String getCmpDownFileSaveToDBPath(long companyid,
			String fileName) {
		Calendar createDate = Calendar.getInstance();
		String year = String.valueOf(createDate.get(Calendar.YEAR));
		String month = String.valueOf(createDate.get(Calendar.MONTH) + 1);
		String date = String.valueOf(createDate.get(Calendar.DAY_OF_MONTH));
		return getCmpDownFileCurrentUploadPath() + "/"
				+ getSeparator(String.valueOf(companyid)) + "/" + year + "/"
				+ month + "/" + date + "/" + fileName + "/";
	}

	public static String getCmpDownFileCurrentUploadPath() {
		return cmpDownFileCurrentUploadPath;
	}

	public static String getCmpDownFileRootPath() {
		return cmpDownFileRootPath;
	}

	public static String getCmpDownFileUrl() {
		return cmpDownFileUrl;
	}

	public void setCmpDownFileCurrentUploadPath(
			String cmpDownFileCurrentUploadPath) {
		ImageConfig.cmpDownFileCurrentUploadPath = cmpDownFileCurrentUploadPath;
	}

	public void setCmpDownFileRootPath(String cmpDownFileRootPath) {
		ImageConfig.cmpDownFileRootPath = cmpDownFileRootPath;
	}

	public void setCmpDownFileUrl(String cmpDownFileUrl) {
		ImageConfig.cmpDownFileUrl = cmpDownFileUrl;
	}

	public static String getcmpProductSortPicFilePath(String dbPath) {
		return getCmpProductSortPicRootPath() + dbPath;
	}

	public static String getCmpProductSortPicSaveToDBPath(int sortId,
			String photoName) {
		return getCmpProductSortPicCurrentUploadPath() + "/"
				+ getSeparator(String.valueOf(sortId)) + "/" + photoName + "/";
	}

	public static String getCmpProductSortPicCurrentUploadPath() {
		return cmpProductSortPicCurrentUploadPath;
	}

	public static String getCmpProductSortPicRootPath() {
		return cmpProductSortPicRootPath;
	}

	public static String getCmpProductSortPicUrl() {
		return cmpProductSortPicUrl;
	}

	public static String getCmpProductSortPicUrl(String dbPath) {
		return getCmpProductSortPicUrl() + dbPath + "h.jpg";
	}

	public void setCmpProductSortPicCurrentUploadPath(
			String cmpProductSortPicCurrentUploadPath) {
		ImageConfig.cmpProductSortPicCurrentUploadPath = cmpProductSortPicCurrentUploadPath;
	}

	public void setCmpProductSortPicRootPath(String cmpProductSortPicRootPath) {
		ImageConfig.cmpProductSortPicRootPath = cmpProductSortPicRootPath;
	}

	public void setCmpProductSortPicUrl(String cmpProductSortPicUrl) {
		ImageConfig.cmpProductSortPicUrl = cmpProductSortPicUrl;
	}

	public static String getCmpHomePicAdUrl(String dbPath) {
		return ImageConfig.getCmpHomePicAdUrl() + dbPath + "h.jpg";
	}

	public static String getCmpHomePicAdFilePath(String dbPath) {
		return getCmpHomePicAdRootPath() + dbPath;
	}

	public static String getCmpHomePicAdSaveToDBPath(long companyId,
			String photoName) {
		return getCmpHomePicAdCurrentUploadPath() + "/"
				+ getSeparator(String.valueOf(companyId)) + "/" + photoName
				+ "/";
	}

	public void setCmpHomePicAdCurrentUploadPath(
			String cmpHomePicAdCurrentUploadPath) {
		ImageConfig.cmpHomePicAdCurrentUploadPath = cmpHomePicAdCurrentUploadPath;
	}

	public void setCmpHomePicAdRootPath(String cmpHomePicAdRootPath) {
		ImageConfig.cmpHomePicAdRootPath = cmpHomePicAdRootPath;
	}

	public void setCmpHomePicAdUrl(String cmpHomePicAdUrl) {
		ImageConfig.cmpHomePicAdUrl = cmpHomePicAdUrl;
	}

	public static String getCmpHomePicAdCurrentUploadPath() {
		return cmpHomePicAdCurrentUploadPath;
	}

	public static String getCmpHomePicAdRootPath() {
		return cmpHomePicAdRootPath;
	}

	public static String getCmpHomePicAdUrl() {
		return cmpHomePicAdUrl;
	}

	public static String getCmpAdPicUrl(String dbPath) {
		return ImageConfig.getCmpAdPicUrl() + dbPath + "h.jpg";
	}

	public static String getCmpAdPicFilePath(String dbPath) {
		return getCmpAdPicRootPath() + dbPath;
	}

	public static String getCmpAdPicSaveToDBPath(long companyId,
			String photoName) {
		return getCmpAdPicCurrentUploadPath() + "/"
				+ getSeparator(String.valueOf(companyId)) + "/" + photoName
				+ "/";
	}

	public void setCmpAdPicCurrentUploadPath(String cmpAdPicCurrentUploadPath) {
		ImageConfig.cmpAdPicCurrentUploadPath = cmpAdPicCurrentUploadPath;
	}

	public void setCmpAdPicRootPath(String cmpAdPicRootPath) {
		ImageConfig.cmpAdPicRootPath = cmpAdPicRootPath;
	}

	public static String getCmpAdPicRootPath() {
		return cmpAdPicRootPath;
	}

	public void setCmpAdPicUrl(String cmpAdPicUrl) {
		ImageConfig.cmpAdPicUrl = cmpAdPicUrl;
	}

	public static String getCmpAdPicCurrentUploadPath() {
		return cmpAdPicCurrentUploadPath;
	}

	public static String getCmpAdPicUrl() {
		return cmpAdPicUrl;
	}

	public static String getCmpFilePic60Url(String dbPath) {
		return ImageConfig.getCmpfileUrl() + dbPath + IMG_H60;
	}

	public static String getCmpFileFlashUrl(String dbPath) {
		return ImageConfig.getCmpfileUrl() + dbPath + "h.swf";
	}

	public static String getCmpFilePic120Url(String dbPath) {
		return ImageConfig.getCmpfileUrl() + dbPath + IMG_H120;
	}

	public static String getCmpFilePic120Url2(String dbPath) {
		if (dbPath == null) {
			return ImageConfig.getUserPicUrl() + "/defhead/nopic120.jpg";
		}
		return ImageConfig.getCmpfileUrl() + dbPath + IMG_H120;
	}

	public static String getCmpFilePic240Url(String dbPath) {
		return ImageConfig.getCmpfileUrl() + dbPath + IMG_H240;
	}

	public static String getCmpFilePic320Url(String dbPath) {
		return ImageConfig.getCmpfileUrl() + dbPath + IMG_H320;
	}

	public static String getCmpFilePic600Url(String dbPath) {
		return ImageConfig.getCmpfileUrl() + dbPath + IMG_H600;
	}

	public static String getCmpFilePic800Url(String dbPath) {
		return ImageConfig.getCmpfileUrl() + dbPath + IMG_H800;
	}

	public void setCmpNavCurrentUploadPath(String cmpNavCurrentUploadPath) {
		ImageConfig.cmpNavCurrentUploadPath = cmpNavCurrentUploadPath;
	}

	public void setCmpNavRootPath(String cmpNavRootPath) {
		ImageConfig.cmpNavRootPath = cmpNavRootPath;
	}

	public void setCmpNavUrl(String cmpNavUrl) {
		ImageConfig.cmpNavUrl = cmpNavUrl;
	}

	public static String getCmpNavCurrentUploadPath() {
		return cmpNavCurrentUploadPath;
	}

	public static String getCmpNavRootPath() {
		return cmpNavRootPath;
	}

	public static String getCmpNavUrl() {
		return cmpNavUrl;
	}

	public static String getCmpNavFilePath(String dbPath) {
		return getCmpNavRootPath() + dbPath;
	}

	public static String getCmpNavDbPath(long id) {
		return getCmpNavCurrentUploadPath() + "/" + getSeparator(id + "") + "/";
	}

	public static String getCmpNavBgPicUrl(String dbPath) {
		return ImageConfig.getCmpNavUrl() + dbPath + "bg.jpg";
	}

	public static String getCmpNavPicHUrl(String dbPath) {
		return ImageConfig.getCmpNavUrl() + dbPath + "h.jpg";
	}

	public static String getCmpNavPicSUrl(String dbPath) {
		return ImageConfig.getCmpNavUrl() + dbPath + "s.jpg";
	}

	public static String getCmpNavFlashHUrl(String dbPath) {
		return ImageConfig.getCmpNavUrl() + dbPath + "h.swf";
	}

	public void setCmpFileCurrentUploadPath(String cmpFileCurrentUploadPath) {
		ImageConfig.cmpFileCurrentUploadPath = cmpFileCurrentUploadPath;
	}

	public void setCmpFileRootPath(String cmpFileRootPath) {
		ImageConfig.cmpFileRootPath = cmpFileRootPath;
	}

	public void setCmpfileUrl(String cmpfileUrl) {
		ImageConfig.cmpfileUrl = cmpfileUrl;
	}

	public static String getCmpFileCurrentUploadPath() {
		return cmpFileCurrentUploadPath;
	}

	public static String getCmpFileRootPath() {
		return cmpFileRootPath;
	}

	public static String getCmpfileUrl() {
		return cmpfileUrl;
	}

	public static String getCmpFileFilePath(String dbPath) {
		return getCmpFileRootPath() + dbPath;
	}

	public static String getCmpFileDbPath(String oid, String photoName) {
		Calendar createDate = Calendar.getInstance();
		String year = String.valueOf(createDate.get(Calendar.YEAR));
		String month = String.valueOf(createDate.get(Calendar.MONTH) + 1);
		String date = String.valueOf(createDate.get(Calendar.DAY_OF_MONTH));
		return getCmpFileCurrentUploadPath() + "/" + year + "/" + month + "/"
				+ date + "/" + getSeparator(oid) + "/" + photoName + "/";
	}

	public void setPhotoPicCurrentUploadPath(String photoPicCurrentUploadPath) {
		ImageConfig.photoPicCurrentUploadPath = photoPicCurrentUploadPath;
	}

	public void setPhotoPicRootPath(String photoPicRootPath) {
		ImageConfig.photoPicRootPath = photoPicRootPath;
	}

	public void setPhotoPicUrl(String photoPicUrl) {
		ImageConfig.photoPicUrl = photoPicUrl;
	}

	public static String getPhotoPicCurrentUploadPath() {
		return photoPicCurrentUploadPath;
	}

	public static String getPhotoPicRootPath() {
		return photoPicRootPath;
	}

	public static String getPhotoPicUrl() {
		return photoPicUrl;
	}

	public static String getBoxPrizeCurrentUploadRootPath() {
		return boxPrizeCurrentUploadRootPath;
	}

	public static String getBoxPrizeRootPath() {
		return boxPrizeRootPath;
	}

	public static String getBoxPrizeUrl() {
		return boxPrizeUrl;
	}

	public void setBoxPrizeRootPath(String boxPrizeRootPath) {
		ImageConfig.boxPrizeRootPath = boxPrizeRootPath;
	}

	public void setBoxPrizeUrl(String boxPrizeUrl) {
		ImageConfig.boxPrizeUrl = boxPrizeUrl;
	}

	public void setBoxPrizeCurrentUploadRootPath(
			String boxPrizeCurrentUploadRootPath) {
		ImageConfig.boxPrizeCurrentUploadRootPath = boxPrizeCurrentUploadRootPath;
	}

	public void setBadgeCurrentUploadRootPath(String badgeCurrentUploadRootPath) {
		ImageConfig.badgeCurrentUploadRootPath = badgeCurrentUploadRootPath;
	}

	public void setBadgeRootPath(String badgeRootPath) {
		ImageConfig.badgeRootPath = badgeRootPath;
	}

	public void setBadgeUrl(String badgeUrl) {
		ImageConfig.badgeUrl = badgeUrl;
	}

	public static String getBadgeCurrentUploadRootPath() {
		return badgeCurrentUploadRootPath;
	}

	public static String getBadgeRootPath() {
		return badgeRootPath;
	}

	public static String getBadgeUrl() {
		return badgeUrl;
	}

	public static String getbadgeDbPath(long id) {
		return getBadgeCurrentUploadRootPath() + "/"
				+ getSeparator(String.valueOf(id)) + "/";
	}

	public static String getFileInServerPath(String picurl) {
		String picpath = picurl.replaceFirst(ImageConfig.getTempUploadPicUrl(),
				"");
		int idx = picpath.lastIndexOf("/");
		String fileName = picpath.substring(idx + 1);
		String filePath = ImageConfig.getTempUploadFilePath();
		String name = filePath + fileName;
		return name;
	}

	public static String getTempUploadPicUrl() {
		return tempUploadPicUrl;
	}

	public static String getTempCutFilePath() {
		return ImageConfig.getTempUploadFilePath() + "/range/";
	}

	public static String getTempUploadFilePath() {
		return getTempUploadRootPath()
				+ ImageConfig.tempUploadCurrentUploadPath + "/";
	}

	public static String getTempUploadRootPath() {
		return tempUploadRootPath;
	}

	public void setTempUploadCurrentUploadPath(
			String tempUploadCurrentUploadPath) {
		ImageConfig.tempUploadCurrentUploadPath = tempUploadCurrentUploadPath;
	}

	public static String getTempUploadPic(String fileName) {
		return ImageConfig.tempUploadPicUrl + tempUploadCurrentUploadPath + "/"
				+ fileName;
	}

	public void setTempUploadPicUrl(String tempUploadPicUrl) {
		ImageConfig.tempUploadPicUrl = tempUploadPicUrl;
	}

	public void setTempUploadRootPath(String tempUploadRootPath) {
		ImageConfig.tempUploadRootPath = tempUploadRootPath;
	}

	public void setUserPicCurrentUploadPath(String userPicCurrentUploadPath) {
		ImageConfig.userPicCurrentUploadPath = userPicCurrentUploadPath;
	}

	public void setUserPicRootPath(String userPicRootPath) {
		ImageConfig.userPicRootPath = userPicRootPath;
	}

	public void setUserPicUrl(String userPicUrl) {
		ImageConfig.userPicUrl = userPicUrl;
	}

	public static String getUserPicCurrentUploadPath() {
		return userPicCurrentUploadPath;
	}

	public static String getUserPicRootPath() {
		return userPicRootPath;
	}

	public static String getUserPicUrl() {
		return userPicUrl;
	}

	public void setCompanyHeadCurrentUploadRootPath(
			String companyHeadCurrentUploadRootPath) {
		ImageConfig.companyHeadCurrentUploadRootPath = companyHeadCurrentUploadRootPath;
	}

	public void setCompanyHeadRootPath(String companyHeadRootPath) {
		ImageConfig.companyHeadRootPath = companyHeadRootPath;
	}

	public void setCompanyHeadUrl(String companyHeadUrl) {
		ImageConfig.companyHeadUrl = companyHeadUrl;
	}

	public void setHeadUrl(String headUrl) {
		ImageConfig.headUrl = headUrl;
	}

	public static String getCompanyHeadCurrentUploadRootPath() {
		return companyHeadCurrentUploadRootPath;
	}

	public static String getCompanyHeadUrl() {
		return companyHeadUrl;
	}

	public static String getCompanyHeadRootPath() {
		return companyHeadRootPath;
	}

	public static String getIMG_H32() {
		return IMG_H32;
	}

	public static String getIMG_H48() {
		return IMG_H48;
	}

	public static String getIMG_H80() {
		return IMG_H80;
	}

	public static String getHeadUrl() {
		return headUrl;
	}

	public static String getCompanyHeadDbPath(long id) {
		return getCompanyHeadCurrentUploadRootPath() + "/"
				+ getSeparator(id + "") + "/";
	}

	public static String getCompanyHead48(String headPath) {
		return getCompanyHeadUrl() + headPath + IMG_H48;
	}

	public static String getCompanyHead80(String headPath) {
		return getCompanyHeadUrl() + headPath + IMG_H80;
	}

	public static String getCompanyHead(String headPath) {
		return getCompanyHeadUrl() + headPath + "logo.jpg";
	}

	public static String getCompanyHead2(String headPath) {
		return getCompanyHeadUrl() + headPath + "logo2.jpg";
	}

	public static String getCompanyBgPicUrl(String dbPath) {
		return getCompanyHeadUrl() + dbPath + "bg.jpg";
	}

	public static String getBadge57PicUrl(String headPath) {
		return ImageConfig.getBadgeUrl() + headPath + IMG_H57;
	}

	public static String getBadge300PicUrl(String headPath) {
		return ImageConfig.getBadgeUrl() + headPath + IMG_H300;
	}

	public static String getHead48PicUrl(String headPath, byte sex) {
		if (headPath == null) {
			if (sex == User.SEX_FEMALE) {
				return ImageConfig.getHeadUrl() + "/defhead/fe" + IMG_H48;
			}
			else if (sex == User.SEX_MALE) {
				return ImageConfig.getHeadUrl() + "/defhead/" + IMG_H48;
			}
			return ImageConfig.getHeadUrl() + "/defhead/o" + IMG_H48;
		}
		return ImageConfig.getHeadUrl() + headPath + IMG_H48;
	}

	public static String getHead32PicUrl(String headPath, byte sex) {
		if (headPath == null) {
			if (sex == User.SEX_FEMALE) {
				return ImageConfig.getHeadUrl() + "/defhead/fe" + IMG_H32;
			}
			else if (sex == User.SEX_MALE) {
				return ImageConfig.getHeadUrl() + "/defhead/" + IMG_H32;
			}
			return ImageConfig.getHeadUrl() + "/defhead/o" + IMG_H32;
		}
		return ImageConfig.getHeadUrl() + headPath + IMG_H32;
	}

	public static String getHead80PicUrl(String headPath, byte sex) {
		if (headPath == null) {
			if (sex == User.SEX_FEMALE) {
				return ImageConfig.getHeadUrl() + "/defhead/fe" + IMG_H80;
			}
			else if (sex == User.SEX_MALE) {
				return ImageConfig.getHeadUrl() + "/defhead/" + IMG_H80;
			}
			return ImageConfig.getHeadUrl() + "/defhead/o" + IMG_H80;
		}
		return ImageConfig.getHeadUrl() + headPath + IMG_H80;
	}

	public static String getHead180PicUrl(String headPath, byte sex) {
		if (headPath == null) {
			if (sex == User.SEX_FEMALE) {
				return ImageConfig.getHeadUrl() + "/defhead/fe" + IMG_H180;
			}
			else if (sex == User.SEX_MALE) {
				return ImageConfig.getHeadUrl() + "/defhead/" + IMG_H180;
			}
			return ImageConfig.getHeadUrl() + "/defhead/o" + IMG_H180;
		}
		return ImageConfig.getHeadUrl() + headPath + IMG_H180;
	}

	/**
	 * 60px 图片
	 * 
	 * @param path
	 * @return
	 *         2010-4-18
	 */
	public static String getBoxPrizeH_0PicUrl(String path) {
		return getBoxPrizeUrl() + path + "h_0.jpg";
	}

	/**
	 * 320px 图片
	 * 
	 * @param path
	 * @return
	 *         2010-4-18
	 */
	public static String getBoxPrizeH_1PicUrl(String path) {
		return getBoxPrizeUrl() + path + "h_1.jpg";
	}

	public static String getHeadCurrentUploadRootPath() {
		return headCurrentUploadRootPath;
	}

	public void setHeadCurrentUploadRootPath(String headCurrentUploadRootPath) {
		ImageConfig.headCurrentUploadRootPath = headCurrentUploadRootPath;
	}

	public static String getHeadRootPath() {
		return headRootPath;
	}

	public void setHeadRootPath(String headRootPath) {
		ImageConfig.headRootPath = headRootPath;
	}

	public static String getHeadUploadPath(String dbPath) {
		return getHeadRootPath() + dbPath;
	}

	public static String getBoxPrizeUploadPath(String dbPath) {
		return getBoxPrizeRootPath() + dbPath;
	}

	public static String getBadgeUploadPath(String dbPath) {
		return getBadgeRootPath() + dbPath;
	}

	public static String getCompanyHeadUploadPath(String dbPath) {
		return getCompanyHeadRootPath() + dbPath;
	}

	public static String getHeadDbPath(long id) {
		return getHeadCurrentUploadRootPath() + "/" + getSeparator(id + "")
				+ "/";
	}

	public static String getBoxPrizeDbPath(long id) {
		return getBoxPrizeCurrentUploadRootPath() + "/"
				+ getSeparator(String.valueOf(id)) + "/";
	}

	/**
	 * 目前此算法只支持到9位数，需要扩展
	 * 
	 * @param s
	 * @return
	 *         2010-7-6
	 */
	private static String getSeparator(String s) {
		int tlen = 9 - s.length();
		StringBuilder para = new StringBuilder();
		for (int i = 0; i < tlen; i++) {
			para.append("0");
		}
		para.append(s);
		StringBuilder path = new StringBuilder(para.substring(0, 3));
		path.append("/");
		path.append(para.substring(3, 6));
		path.append("/");
		path.append(para.substring(6, 9));
		return path.toString();
	}

	public static void main(String[] args) {
		P.println(getSeparator("12345678910123456789"));
	}

	public static void makeDirs(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static String getBoxPrizePicSaveToDBPath(String oid, String photoName) {
		Calendar createDate = Calendar.getInstance();
		String year = String.valueOf(createDate.get(Calendar.YEAR));
		String month = String.valueOf(createDate.get(Calendar.MONTH) + 1);
		String date = String.valueOf(createDate.get(Calendar.DAY_OF_MONTH));
		return getPhotoPicCurrentUploadPath() + "/" + year + "/" + month + "/"
				+ date + "/" + getSeparator(oid) + "/" + photoName + "/";
	}

	public static String getPicSaveToDBPath(String oid, String photoName) {
		Calendar createDate = Calendar.getInstance();
		String year = String.valueOf(createDate.get(Calendar.YEAR));
		String month = String.valueOf(createDate.get(Calendar.MONTH) + 1);
		String date = String.valueOf(createDate.get(Calendar.DAY_OF_MONTH));
		return userPicCurrentUploadPath + "/" + year + "/" + month + "/" + date
				+ "/" + getSeparator(oid) + "/" + photoName + "/";
	}

	public static String getCouponPicSaveToDBPath(String oid) {
		// Calendar createDate = Calendar.getInstance();
		// String year = String.valueOf(createDate.get(Calendar.YEAR));
		// String month = String.valueOf(createDate.get(Calendar.MONTH) + 1);
		// String date = String.valueOf(createDate.get(Calendar.DAY_OF_MONTH));
		// return couponPicCurrentUploadPath + "/" + year + "/" + month + "/"
		// + date + "/" + getSeparator(oid) + "/" + photoName + "/";
		return couponPicCurrentUploadPath + "/" + getSeparator(oid) + "/";
	}

	public static String getUnionLogoSaveToDBPath(String oid) {
		return getUnionLogoCurrentUploadPath() + "/" + getSeparator(oid) + "/";
	}

	public static String getBoxPrizePicUploadPath(String dbPath) {
		return getPhotoPicRootPath() + dbPath;
	}

	public static String getPicUploadPath(String dbPath) {
		return userPicRootPath + dbPath;
	}

	public static String getCouponPicFilePath(String dbPath) {
		return couponPicRootPath + dbPath;
	}

	public static String getUnionLogoFilePath(String dbPath) {
		return unionLogoRootPath + dbPath;
	}

	public static String getPhotoPicH_0Url(String dbPath) {
		return ImageConfig.getPhotoPicUrl() + dbPath + "h_0.jpg";
	}

	public static String getPhotoPicH_1Url(String dbPath) {
		return ImageConfig.getPhotoPicUrl() + dbPath + "h_1.jpg";
	}

	public static String getPhotoPicH_2Url(String dbPath) {
		return ImageConfig.getPhotoPicUrl() + dbPath + "h_2.jpg";
	}

	public static String getPic60Url(String dbPath) {
		if (dbPath == null) {
			return ImageConfig.getUserPicUrl() + "/defhead/" + IMG_H60;
		}
		return ImageConfig.getUserPicUrl() + dbPath + IMG_H60;
	}

	public static String getPic120Url(String dbPath) {
		if (dbPath == null) {
			return ImageConfig.getUserPicUrl() + "/defhead/" + IMG_H120;
		}
		return ImageConfig.getUserPicUrl() + dbPath + IMG_H120;
	}

	public static String getPic240Url(String dbPath) {
		if (dbPath == null) {
			return ImageConfig.getUserPicUrl() + "/defhead/" + IMG_H240;
		}
		return ImageConfig.getUserPicUrl() + dbPath + IMG_H240;
	}

	public static String getPic320Url(String dbPath) {
		if (dbPath == null) {
			return ImageConfig.getUserPicUrl() + "/defhead/" + IMG_H320;
		}
		return ImageConfig.getUserPicUrl() + dbPath + IMG_H320;
	}

	/**
	 * 显示的为600的图片
	 * 
	 * @param dbPath
	 * @return
	 *         2010-8-16
	 */
	public static String getPic600Url(String dbPath) {
		if (dbPath == null) {
			return ImageConfig.getUserPicUrl() + "/defhead/" + IMG_H640;
		}
		return ImageConfig.getUserPicUrl() + dbPath + IMG_H640;
	}

	public static String getPic800Url(String dbPath) {
		if (dbPath == null) {
			return ImageConfig.getUserPicUrl() + "/defhead/" + IMG_H800;
		}
		return ImageConfig.getUserPicUrl() + dbPath + IMG_H800;
	}

	public static String getCouponPic240Url(String dbPath) {
		return ImageConfig.getCouponPicUrl() + dbPath + IMG_H240;
	}

	public static String getUnionLogo48Url(String dbPath) {
		return ImageConfig.getUnionLogoUrl() + dbPath + IMG_H48;
	}

	public static String getUnionLogo80Url(String dbPath) {
		return ImageConfig.getUnionLogoUrl() + dbPath + IMG_H80;
	}

	public static String getCouponPic320Url(String dbPath) {
		return ImageConfig.getCouponPicUrl() + dbPath + IMG_H320;
	}

	public static String getCouponPicCurrentUploadPath() {
		return couponPicCurrentUploadPath;
	}

	public void setCouponPicCurrentUploadPath(String couponPicCurrentUploadPath) {
		ImageConfig.couponPicCurrentUploadPath = couponPicCurrentUploadPath;
	}

	public static String getCouponPicRootPath() {
		return couponPicRootPath;
	}

	public void setCouponPicRootPath(String couponPicRootPath) {
		ImageConfig.couponPicRootPath = couponPicRootPath;
	}

	public static String getCouponPicUrl() {
		return couponPicUrl;
	}

	public void setCouponPicUrl(String couponPicUrl) {
		ImageConfig.couponPicUrl = couponPicUrl;
	}

	public static String getUnionLogoRootPath() {
		return unionLogoRootPath;
	}

	public void setUnionLogoRootPath(String unionLogoRootPath) {
		ImageConfig.unionLogoRootPath = unionLogoRootPath;
	}

	public static String getUnionLogoUrl() {
		return unionLogoUrl;
	}

	public void setUnionLogoUrl(String unionLogoUrl) {
		ImageConfig.unionLogoUrl = unionLogoUrl;
	}

	public static String getUnionLogoCurrentUploadPath() {
		return unionLogoCurrentUploadPath;
	}

	public void setUnionLogoCurrentUploadPath(String unionLogoCurrentUploadPath) {
		ImageConfig.unionLogoCurrentUploadPath = unionLogoCurrentUploadPath;
	}

	private static String hkAdPicCurrentUploadPath;

	private static String hkAdPicRootPath;

	private static String hkAdPicUrl;

	public void setHkAdPicCurrentUploadPath(String hkAdPicCurrentUploadPath) {
		ImageConfig.hkAdPicCurrentUploadPath = hkAdPicCurrentUploadPath;
	}

	public void setHkAdPicRootPath(String hkAdPicRootPath) {
		ImageConfig.hkAdPicRootPath = hkAdPicRootPath;
	}

	public void setHkAdPicUrl(String hkAdPicUrl) {
		ImageConfig.hkAdPicUrl = hkAdPicUrl;
	}

	public static String getHkAdPicCurrentUploadPath() {
		return hkAdPicCurrentUploadPath;
	}

	public static String getHkAdPicRootPath() {
		return hkAdPicRootPath;
	}

	public static String getHkAdPicUrl() {
		return hkAdPicUrl;
	}

	public static String getHkAdPicDbPath(long id) {
		return getHkAdPicCurrentUploadPath() + "/"
				+ getSeparator(String.valueOf(id)) + "/";
	}

	public static String getHkAdPicFilePath(String dbPath) {
		return getHkAdPicRootPath() + dbPath;
	}

	public static String getHkAdPicUrl(String dbPath) {
		return getHkAdPicUrl() + dbPath + "h.jpg";
	}
	
	

}