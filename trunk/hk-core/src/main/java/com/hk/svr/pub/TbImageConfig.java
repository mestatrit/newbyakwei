package com.hk.svr.pub;

import java.util.Calendar;

import com.hk.bean.taobao.Tb_User;

public class TbImageConfig {

	public static final String IMG_H48 = "h48.jpg";

	public static final String IMG_H60 = "h60.jpg";

	public static final String IMG_H80 = "h80.jpg";

	public static final String IMG_H180 = "h180.jpg";

	private static String headRootPath;

	private static String headCurrentUploadRootPath;

	private static String headUrl;

	public static String getHeadUploadPath(String dbPath) {
		return getHeadRootPath() + dbPath;
	}

	public static String getHeadDbPath(long id) {
		return getHeadCurrentUploadRootPath() + "/" + getHeadSaveToDBPath(id)
				+ "/";
	}

	public static String getHeadSaveToDBPath(long id) {
		Calendar createDate = Calendar.getInstance();
		String year = String.valueOf(createDate.get(Calendar.YEAR));
		String month = String.valueOf(createDate.get(Calendar.MONTH) + 1);
		String date = String.valueOf(createDate.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(createDate.get(Calendar.HOUR_OF_DAY));
		String min = String.valueOf(createDate.get(Calendar.HOUR_OF_DAY));
		return headCurrentUploadRootPath + "/" + year + "/" + month + "/"
				+ date + "/" + hour + "/" + min + "/" + id + "/";
	}

	public static String getHead48PicUrl(String headPath, byte gender) {
		if (headPath == null) {
			if (gender == Tb_User.GENDER_FEMALE) {
				return getHeadUrl() + "/defhead/fe" + IMG_H48;
			}
			else if (gender == Tb_User.GENDER_MALE) {
				return getHeadUrl() + "/defhead/" + IMG_H48;
			}
			return getHeadUrl() + "/defhead/o" + IMG_H48;
		}
		return getHeadUrl() + headPath + IMG_H48;
	}

	public static String getHead80PicUrl(String headPath, byte gender) {
		if (headPath == null) {
			if (gender == Tb_User.GENDER_FEMALE) {
				return getHeadUrl() + "/defhead/fe" + IMG_H80;
			}
			else if (gender == Tb_User.GENDER_MALE) {
				return getHeadUrl() + "/defhead/" + IMG_H80;
			}
			return getHeadUrl() + "/defhead/o" + IMG_H80;
		}
		return getHeadUrl() + headPath + IMG_H80;
	}

	public static String getHead180PicUrl(String headPath, byte gender) {
		if (headPath == null) {
			if (gender == Tb_User.GENDER_FEMALE) {
				return getHeadUrl() + "/defhead/fe" + IMG_H180;
			}
			else if (gender == Tb_User.GENDER_MALE) {
				return getHeadUrl() + "/defhead/" + IMG_H180;
			}
			return getHeadUrl() + "/defhead/o" + IMG_H180;
		}
		return getHeadUrl() + headPath + IMG_H180;
	}

	public static String getHeadRootPath() {
		return headRootPath;
	}

	public void setHeadRootPath(String headRootPath) {
		TbImageConfig.headRootPath = headRootPath;
	}

	public static String getHeadCurrentUploadRootPath() {
		return headCurrentUploadRootPath;
	}

	public void setHeadCurrentUploadRootPath(String headCurrentUploadRootPath) {
		TbImageConfig.headCurrentUploadRootPath = headCurrentUploadRootPath;
	}

	public static String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		TbImageConfig.headUrl = headUrl;
	}
}