package com.hk.bean;

import java.util.ArrayList;
import java.util.List;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;
import com.hk.svr.pub.ZoneUtil;

@Table(name = "user", id = "userid")
public class User {

	public static final byte SEX_MALE = 1;

	public static final byte SEX_FEMALE = 2;

	public static final byte HEAD_SETTED_N = 0;

	public static final byte HEAD_SETTED_Y = 1;

	@Id
	private long userId;// 用户id

	@Column
	private String nickName;// 昵称

	@Column
	private String headPath;// 头像地址

	@Column
	private byte headflg;// 是否有头像 0:没有 1:设置过

	@Column
	private String domain;

	@Column
	private int friendCount;

	@Column
	private int fansCount;

	/**
	 * 原来的老城市表，准备废弃
	 */
	@Column
	private int cityId;

	@Column
	private byte sex;

	/**
	 * 新城市表，包括直辖市与普通城市不包括区
	 */
	@Column
	private int pcityId;

	public int getCityId() {
		return cityId;
	}

	public Pcity getPcity() {
		return ZoneUtil.getPcity(this.pcityId);
	}

	public String getZoneDescr() {
		StringBuilder sb = new StringBuilder();
		if (cityId > 0) {
			City city = ZoneUtil.getCity(cityId);
			if (city != null) {
				Province province = ZoneUtil.getProvince(city.getProvinceId());
				if (province != null) {
					sb.append(province.getProvince()).append(" ").append(
							city.getCity());
					return sb.toString();
				}
			}
		}
		return null;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public int getFriendCount() {
		return friendCount;
	}

	public void setFriendCount(int friendCount) {
		this.friendCount = friendCount;
	}

	public int getFansCount() {
		return fansCount;
	}

	public void setFansCount(int fansCount) {
		this.fansCount = fansCount;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public static byte getHEAD_SETTED_N() {
		return HEAD_SETTED_N;
	}

	public static byte getHEAD_SETTED_Y() {
		return HEAD_SETTED_Y;
	}

	public boolean isheadSetted() {
		if (this.headflg == HEAD_SETTED_Y) {
			return true;
		}
		return false;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public String getHeadPath() {
		return headPath;
	}

	public byte getHeadflg() {
		return headflg;
	}

	public void setHeadflg(byte headflg) {
		this.headflg = headflg;
	}

	public String getHead32Pic() {
		return ImageConfig.getHead32PicUrl(headPath, sex);
	}

	public String getHead48Pic() {
		return ImageConfig.getHead48PicUrl(headPath, sex);
	}

	public String getHead80Pic() {
		return ImageConfig.getHead80PicUrl(headPath, sex);
	}

	public UserOtherInfo getUserOtherInfo() {
		UserService userService = (UserService) HkUtil.getBean("userService");
		return userService.getUserOtherInfo(userId);
	}

	public static int validateNickName(String nickName) {
		if (nickName == null || "".equals(nickName)) {
			return Err.NICKNAME_ERROR;
		}
		if (nickName.length() > 10 || nickName.length() < 2) {
			return Err.NICKNAME_ERROR;
		}
		if (DataUtil.isNumber(nickName)) {
			return Err.NICKNAME_ERROR;
		}
		if (!DataUtil.isNumberOrCharOrChinese(nickName)) {
			return Err.NICKNAME_ERROR;
		}
		return Err.SUCCESS;
	}

	public static int validateReg(String input, String password) {
		if (!DataUtil.isLegalEmail(input)) {
			return Err.EMAIL_ERROR;
		}
		return validatePassword(password);
	}

	public static int validateReg2(String email, String mobile, byte sex,
			String password) {
		if (DataUtil.isEmpty(email)) {
			return Err.EMAIL_ERROR;
		}
		if (!DataUtil.isLegalEmail(email)) {
			return Err.EMAIL_ERROR;
		}
		if (!DataUtil.isEmpty(mobile) && !DataUtil.isLegalMobile(mobile)) {
			return Err.MOBILE_ERROR;
		}
		if (sex != SEX_FEMALE && sex != SEX_MALE) {
			return Err.SEX_ERROR;
		}
		return validatePassword(password);
	}

	public static int validatePassword(String password) {
		if (!DataUtil.isLegalPassword(password)) {
			return Err.PASSWORD_DATA_ERROR;
		}
		return Err.SUCCESS;
	}

	public boolean isHasSex() {
		if (this.sex != SEX_FEMALE && this.sex != SEX_MALE) {
			return false;
		}
		return true;
	}

	public int validate() {
		if (!DataUtil.isInElements(this.sex, new Object[] { SEX_FEMALE,
				SEX_MALE })) {
			return Err.USEROTHERINFO_SEX_ERROR;
		}
		return Err.SUCCESS;
	}

	public List<Integer> validateList() {
		List<Integer> list = new ArrayList<Integer>();
		int e = validateNickName(this.nickName);
		if (e != Err.SUCCESS) {
			list.add(e);
		}
		if (!DataUtil.isInElements(this.sex, new Object[] { SEX_FEMALE,
				SEX_MALE })) {
			list.add(Err.USEROTHERINFO_SEX_ERROR);
		}
		return list;
	}

	public String getLocation() {
		City city = ZoneUtil.getCity(cityId);
		if (city != null) {
			return ZoneUtil.getProvince(city.getProvinceId()).getProvince()
					+ " " + city.getCity();
		}
		return null;
	}

	public int getPcityId() {
		return pcityId;
	}

	public void setPcityId(int pcityId) {
		this.pcityId = pcityId;
	}
}