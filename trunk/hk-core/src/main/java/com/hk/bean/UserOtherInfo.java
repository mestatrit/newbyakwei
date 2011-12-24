package com.hk.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "userotherinfo", id = "userid")
public class UserOtherInfo {

	public static final byte FIRST_ADD_N = 1;

	public static final byte FIRST_ADD_Y = 0;

	public static final byte MOBILE_NOT_BIND = 0;

	public static final byte MOBILE_BIND = 1;

	public static final byte USERSTATUS_Y = 0;

	/**
	 * 被禁止
	 */
	public static final byte USERSTATUS_STOP = 1;

	public static final byte USERSTATUS_CMPMEMBER = 2;

	public static final byte VALIDATEEMAIL_N = 0;

	public static final byte VALIDATEEMAIL_Y = 1;

	public static final byte USERFLG_NORMAL = 0;

	public static final byte USERFLG_CMPMEMBER = 1;

	public static final byte USERFLG_NOUSE = 2;

	@Id
	private long userId;// 用户id

	@Column
	private byte validateEmail;// email是否认证

	@Column
	private int score;// 积分

	@Column
	private String intro;// 个人介绍

	// /**
	// * 规则:sex;nickname;head;area;intro;pwd;birthday,0都代表没有设置过,1都代表设置过
	// */
	// @Column
	// private String firstAddInfo;//
	@Column
	private int pwdHash;// 密码哈希后

	@Column
	private String email;// 邮件地址

	@Column
	private String mobile;// 个人手机

	@Column
	private byte mobileBind;

	@Column
	private byte userStatus;

	@Column
	private int hkb;// 火酷币

	@Column
	private Date createTime;// 注册时间

	@Column
	private String name;// 姓名

	@Column
	private int birthdayMonth;

	@Column
	private int birthdayDate;

	@Column
	private String cityCode;// 城市区号

	@Column
	private String pwd;// 密码

	@Column
	private String prvWeb;// 个人站点

	/**
	 * 用户通过足迹报到等方式获得的点数
	 */
	@Column
	private int points;

	public String getPrvWeb() {
		return prvWeb;
	}

	public void setPrvWeb(String prvWeb) {
		this.prvWeb = prvWeb;
	}

	public boolean isMobileAlreadyBind() {
		if (this.mobileBind == UserOtherInfo.MOBILE_BIND) {
			return true;
		}
		return false;
	}

	public static byte getFIRST_ADD_N() {
		return FIRST_ADD_N;
	}

	public static byte getFIRST_ADD_Y() {
		return FIRST_ADD_Y;
	}

	public static byte getUSERSTATUS_Y() {
		return USERSTATUS_Y;
	}

	public static byte getUSERSTATUS_STOP() {
		return USERSTATUS_STOP;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd() {
		return pwd;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getHkb() {
		return hkb;
	}

	public void setHkb(int hkb) {
		this.hkb = hkb;
	}

	public int getPwdHash() {
		return pwdHash;
	}

	public void setPwdHash(int pwdHash) {
		this.pwdHash = pwdHash;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	// public int getCityId() {
	// return cityId;
	// }
	//
	// public void setCityId(int cityId) {
	// this.cityId = cityId;
	// }
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getValidateEmail() {
		return validateEmail;
	}

	public void setValidateEmail(byte validateEmail) {
		this.validateEmail = validateEmail;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public byte getMobileBind() {
		return mobileBind;
	}

	public void setMobileBind(byte mobileBind) {
		this.mobileBind = mobileBind;
	}

	public static byte getMOBILE_NOT_BIND() {
		return MOBILE_NOT_BIND;
	}

	public static byte getMOBILE_BIND() {
		return MOBILE_BIND;
	}

	public byte getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(byte userStatus) {
		this.userStatus = userStatus;
	}

	public boolean isInvalidate() {
		if (this.userStatus == USERSTATUS_Y) {
			return true;
		}
		return false;
	}

	public boolean isCmpMemberRegAndNoActive() {
		if (this.userStatus == USERSTATUS_CMPMEMBER) {
			return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBirthdayMonth() {
		return birthdayMonth;
	}

	public void setBirthdayMonth(int birthdayMonth) {
		this.birthdayMonth = birthdayMonth;
	}

	public int getBirthdayDate() {
		return birthdayDate;
	}

	public void setBirthdayDate(int birthdayDate) {
		this.birthdayDate = birthdayDate;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public boolean isAuthedMail() {
		if (this.validateEmail == UserOtherInfo.VALIDATEEMAIL_Y) {
			return true;
		}
		return false;
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.name);
		int a = validateName(this.name);
		if (a != Err.SUCCESS) {
			return a;
		}
		s = DataUtil.toTextRow(this.intro);
		if (!DataUtil.isEmpty(s)) {
			if (s.length() > 200) {
				return Err.USEROTHERINFO_INTRO_LENGTH_TOO_LONG;
			}
		}
		if (birthdayDate != 0 || birthdayMonth != 0) {
			if (birthdayDate < 1 || birthdayDate > 31) {
				return Err.USEROTHERINFO_BIRTHDAY_ERROR;
			}
			if (birthdayMonth < 1 || birthdayMonth > 12) {
				return Err.USEROTHERINFO_BIRTHDAY_ERROR;
			}
		}
		return Err.SUCCESS;
	}

	public List<Integer> validatelist() {
		List<Integer> list = new ArrayList<Integer>();
		String s = DataUtil.toTextRow(this.name);
		int a = validateName(this.name);
		if (a != Err.SUCCESS) {
			list.add(a);
		}
		s = DataUtil.toTextRow(this.intro);
		if (!DataUtil.isEmpty(s)) {
			if (s.length() > 200) {
				list.add(Err.USEROTHERINFO_INTRO_LENGTH_TOO_LONG);
			}
		}
		if (birthdayDate != 0 || birthdayMonth != 0) {
			if (birthdayDate < 1 || birthdayDate > 31) {
				list.add(Err.USEROTHERINFO_BIRTHDAY_ERROR);
			}
			if (birthdayMonth < 1 || birthdayMonth > 12) {
				list.add(Err.USEROTHERINFO_BIRTHDAY_ERROR);
			}
		}
		return list;
	}

	public static int validateEmail(String email) {
		if (!DataUtil.isLegalEmail(email)) {
			return Err.USEROTHERINFO_EMAIL_ERROR;
		}
		return Err.SUCCESS;
	}

	public static int validateName(String name) {
		if (DataUtil.isEmpty(name)) {
			return Err.SUCCESS;
		}
		String s = DataUtil.toTextRow(name);
		if (s.length() > 10 || s.length() < 2) {
			return Err.USER_NAME_ERROR;
		}
		if (!DataUtil.isNumberOrCharOrChinese(name)) {
			return Err.USER_NAME_ERROR;
		}
		return Err.SUCCESS;
	}

	public static int validateMobile(String mobile) {
		if (!DataUtil.isLegalMobile(mobile)) {
			return Err.MOBILE_ERROR;
		}
		return Err.SUCCESS;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isStop() {
		if (this.userStatus == USERSTATUS_STOP) {
			return true;
		}
		return false;
	}
}