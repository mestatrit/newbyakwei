package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 用户名片信息
 * 
 * @author yuanwei
 */
@Table(name = "usercard", id = "userid")
public class UserCard {
	public static final byte CHGFLG_PUBLIC = 0;

	public static final byte CHGFLG_PROTECT = 1;

	private long userId;

	private String homeAddr;// 家庭地址

	private String homePostcode;// 家庭邮编

	private String workAddr;// 工作地址

	private String workPostcode;// 工作邮编

	private String intro;// 个人介绍,与个人信息中的不同步修改

	private String workplace;// 工作地方

	private String jobRank;// 职位

	private String anotherMobile;// 次要手机号

	private String homeTelphone;// 家庭电话

	private String email;// 不与个人信息同步

	private String qq;

	private String msn;

	private String gtalk;

	private String skype;

	private String workPlaceWeb;// 公司网址

	private byte completeinfo;// 0:不完整1:完整

	private byte chgflg;// 交换方式 0:普通交换1:谨慎交换

	private String nickName;

	private String name;// 同步更新userotherinfo

	public UserCard() {//
	}

	public String getSimpleName() {
		if (DataUtil.isEmpty(this.name)) {
			return this.nickName;
		}
		if (this.name.equals(this.nickName)) {
			return this.name;
		}
		return this.name + "(" + this.nickName + ")";
	}

	public UserCard(UserOtherInfo userOtherInfo, User user) {
		this.setFromUserOtherInfoAndUser(userOtherInfo, user);
	}

	public void setFromUserOtherInfoAndUser(UserOtherInfo userOtherInfo,
			User user) {
		setUserId(userOtherInfo.getUserId());
		setName(userOtherInfo.getName());
		setIntro(userOtherInfo.getIntro());
		setNickName(user.getNickName());
		setChgflg(CHGFLG_PUBLIC);
		checkComplete();
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChgflg(byte chgflg) {
		this.chgflg = chgflg;
	}

	public byte getChgflg() {
		return chgflg;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getHomeAddr() {
		return homeAddr;
	}

	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	public String getHomePostcode() {
		return homePostcode;
	}

	public void setHomePostcode(String homePostcode) {
		this.homePostcode = homePostcode;
	}

	public String getWorkAddr() {
		return workAddr;
	}

	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}

	public String getWorkPostcode() {
		return workPostcode;
	}

	public void setWorkPostcode(String workPostcode) {
		this.workPostcode = workPostcode;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getJobRank() {
		return jobRank;
	}

	public void setJobRank(String jobRank) {
		this.jobRank = jobRank;
	}

	public String getAnotherMobile() {
		return anotherMobile;
	}

	public void setAnotherMobile(String anotherMobile) {
		this.anotherMobile = anotherMobile;
	}

	public String getHomeTelphone() {
		return homeTelphone;
	}

	public void setHomeTelphone(String homeTelphone) {
		this.homeTelphone = homeTelphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getGtalk() {
		return gtalk;
	}

	public void setGtalk(String gtalk) {
		this.gtalk = gtalk;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getWorkPlaceWeb() {
		return workPlaceWeb;
	}

	public void setWorkPlaceWeb(String workPlaceWeb) {
		this.workPlaceWeb = workPlaceWeb;
	}

	public byte getCompleteinfo() {
		return completeinfo;
	}

	public void setCompleteinfo(byte completeinfo) {
		this.completeinfo = completeinfo;
	}

	/**
	 * 信息是否完整
	 * 
	 * @return
	 */
	public boolean isComplete() {
		if (this.completeinfo == 1) {
			return true;
		}
		return false;
	}

	public boolean isProtectedChange() {
		if (this.chgflg == CHGFLG_PROTECT) {
			return true;
		}
		return false;
	}

	public void checkComplete() {
		int sum = 0;
		if (homeAddr != null) {
			sum++;
		}
		if (homePostcode != null) {
			sum++;
		}
		if (workAddr != null) {
			sum++;
		}
		if (workPostcode != null) {
			sum++;
		}
		if (intro != null) {
			sum++;
		}
		if (workplace != null) {
			sum++;
		}
		if (jobRank != null) {
			sum++;
		}
		if (anotherMobile != null) {
			sum++;
		}
		if (homeTelphone != null) {
			sum++;
		}
		if (email != null) {
			sum++;
		}
		if (qq != null) {
			sum++;
		}
		if (msn != null) {
			sum++;
		}
		if (gtalk != null) {
			sum++;
		}
		if (skype != null) {
			sum++;
		}
		if (workPlaceWeb != null) {
			sum++;
		}
		if (sum >= 8) {
			this.completeinfo = 1;
		}
		else {
			this.completeinfo = 0;
		}
	}

	public int validate() {
		if (!DataUtil.isEmpty(homeAddr)) {
			if (DataUtil.toTextRow(homeAddr).length() > 100) {
				return Err.USERCARD_HOMEADDR_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isEmpty(homePostcode)) {
			if (DataUtil.toTextRow(homePostcode).length() > 10) {
				return Err.USERCARD_HOMEPOSTCODE_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isEmpty(workAddr)) {
			if (DataUtil.toTextRow(workAddr).length() > 100) {
				return Err.USERCARD_WORKADDR_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isEmpty(workPostcode)) {
			if (DataUtil.toTextRow(workPostcode).length() > 10) {
				return Err.USERCARD_WORKPOSTCODE_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isEmpty(intro)) {
			if (DataUtil.toText(intro).length() > 200) {
				return Err.USERCARD_INTRO_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isEmpty(workplace)) {
			if (DataUtil.toTextRow(workplace).length() > 50) {
				return Err.USERCARD_WORKPLACE_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isEmpty(jobRank)) {
			if (DataUtil.toTextRow(jobRank).length() > 20) {
				return Err.USERCARD_JOBRANK_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isEmpty(anotherMobile)) {
			if (DataUtil.toTextRow(anotherMobile).length() > 11) {
				return Err.USERCARD_ANOTHERMOBILE_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isEmpty(homeTelphone)) {
			if (DataUtil.toTextRow(homeTelphone).length() > 20) {
				return Err.USERCARD_HOMETELPHONE_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isEmpty(email)) {
			if (DataUtil.toTextRow(email).length() > 50) {
				return Err.USERCARD_EMAIL_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isEmpty(qq)) {
			if (DataUtil.toTextRow(qq).length() > 50) {
				return Err.USERCARD_QQ_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isEmpty(msn)) {
			if (DataUtil.toTextRow(msn).length() > 50) {
				return Err.USERCARD_MSN_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isEmpty(gtalk)) {
			if (DataUtil.toTextRow(gtalk).length() > 50) {
				return Err.USERCARD_GTALK_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isEmpty(skype)) {
			if (DataUtil.toTextRow(skype).length() > 50) {
				return Err.USERCARD_SKYPE_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isEmpty(workPlaceWeb)) {
			if (DataUtil.toTextRow(workPlaceWeb).length() > 50) {
				return Err.USERCARD_WORKPLACEWEB_LENGTH_TOO_LONG;
			}
		}
		return Err.SUCCESS;
	}
}