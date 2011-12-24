package com.hk.bean;

import java.util.Calendar;
import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 具体角色人物表(预定类别)
 * 
 * @author akwei
 */
@Table(name = "cmpactor")
public class CmpActor {

	/**
	 * 正常运行
	 */
	public static byte ACTORSTATUS_RUN = 1;

	/**
	 * 休假
	 */
	public static byte ACTORSTATUS_HOLIDAY = 2;

	/**
	 * 暂停服务
	 */
	public static byte ACTORSTATUS_PAUSE = 3;

	/**
	 * 暂不接受预约
	 */
	public static byte ACTORSTATUS_REFUSE = 4;

	/**
	 * 不可预约
	 */
	public static byte RESERVEFLG_N = 0;

	/**
	 * 可预约
	 */
	public static byte RESERVEFLG_Y = 1;

	public static byte GENDER_MALE = 1;

	public static byte GENDER_FEMALE = 2;

	@Id
	private long actorId;

	@Column
	private long companyId;

	@Column
	private long roleId;

	@Column
	private String name;

	@Column
	private byte actorStatus;

	@Column
	private String picPath;

	@Column
	private String intro;

	/**
	 * 公休的日期,用","分隔,(星期一，。。。。)
	 */
	@Column
	private String workDay;

	@Column
	private byte reserveflg;

	@Column
	private byte gender;

	/**
	 * 服务成功总数量
	 */
	@Column
	private int workCount;

	@Column
	private int score;

	@Column
	private int scoreUserNum;

	private CmpActorRole cmpActorRole;

	public void setCmpActorRole(CmpActorRole cmpActorRole) {
		this.cmpActorRole = cmpActorRole;
	}

	public CmpActorRole getCmpActorRole() {
		return cmpActorRole;
	}

	public long getActorId() {
		return actorId;
	}

	public void setActorId(long actorId) {
		this.actorId = actorId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getActorStatus() {
		return actorStatus;
	}

	public void setActorStatus(byte actorStatus) {
		this.actorStatus = actorStatus;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getPic60Url() {
		return ImageConfig.getCmpActorPic60Url(this.picPath);
	}

	public String getPic320Url() {
		return ImageConfig.getCmpActorPic320Url(this.picPath);
	}

	public String getPic500Url() {
		return ImageConfig.getCmpActorPic500Url(this.picPath);
	}

	public String getPic240Url() {
		return ImageConfig.getCmpActorPic240Url(this.picPath);
	}

	public String getPic150Url() {
		return ImageConfig.getCmpActorPic150Url(this.picPath);
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 20)) {
			return Err.CMPACTOR_NAME_ERROR;
		}
		if (!HkValidate.validateLength(this.intro, true, 300)) {
			return Err.CMPACTOR_INTRO_ERROR;
		}
		if (this.gender != GENDER_MALE || this.gender != GENDER_MALE) {
			return Err.CMPACTOR_GENDER_ERROR;
		}
		return Err.SUCCESS;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public void setWorkDay(String workDay) {
		this.workDay = workDay;
	}

	public String getWorkDay() {
		return workDay;
	}

	/**
	 * 当天是否是工作日
	 * 
	 * @return
	 *         2010-7-28
	 */
	public boolean isOnWorkDay() {
		return this.isOnWorkDay(new Date());
	}

	/**
	 * 查看这个时间是否是工作日
	 * 
	 * @param date
	 * @return
	 *         2010-8-6
	 */
	public boolean isOnWorkDay(Date date) {
		if (DataUtil.isEmpty(this.workDay)) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (this.workDay.indexOf(String.valueOf(day)) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * 是否是工作状态
	 * 
	 * @return
	 *         2010-7-28
	 */
	public boolean isOnWork() {
		if (this.actorStatus == ACTORSTATUS_RUN) {
			return true;
		}
		return false;
	}

	public byte getReserveflg() {
		return reserveflg;
	}

	public void setReserveflg(byte reserveflg) {
		this.reserveflg = reserveflg;
	}

	/**
	 * 是否是一个可以预约的人员
	 * 
	 * @return
	 *         2010-8-15
	 */
	public boolean isActorReserve() {
		if (this.reserveflg == RESERVEFLG_Y) {
			return true;
		}
		return false;
	}

	public byte getGender() {
		return gender;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public int getWorkCount() {
		return workCount;
	}

	public void setWorkCount(int workCount) {
		this.workCount = workCount;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public int getScoreUserNum() {
		return scoreUserNum;
	}

	public void setScoreUserNum(int scoreUserNum) {
		this.scoreUserNum = scoreUserNum;
	}
}