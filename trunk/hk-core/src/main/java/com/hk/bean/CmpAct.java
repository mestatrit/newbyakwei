package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmpact", id = "actid")
public class CmpAct {
	/**
	 * 活动状态:运行中
	 */
	public static final byte ACTSTATUS_RUN = 0;

	/**
	 * 暂停
	 */
	public static final byte ACTSTATUS_PAUSE = 1;

	/**
	 * 管理方暂停
	 */
	public static final byte ACTSTATUS_ADMINPAUSE = 2;

	/**
	 * 作废
	 */
	public static final byte ACTSTATUS_INVALID = 3;

	/**
	 * 报名人员不需要审核
	 */
	public static final byte USERNEEDCHECKFLG_N = 0;

	/**
	 * 报名人员需要审核
	 */
	public static final byte USERNEEDCHECKFLG_Y = 1;

	/**
	 * 所有用户都可以报名
	 */
	public static final byte MEMBERLIMITFLG_N = 0;

	/**
	 * 只有会员可以报名
	 */
	public static final byte MEMBERLIMITFLG_Y = 1;

	@Id
	private long actId;

	@Column
	private long userId;

	/**
	 * 活动名称
	 */
	@Column
	private String name;

	/**
	 * 活动类型
	 */
	@Column
	private long kindId;

	/**
	 * 活动短信关键字
	 */
	@Column
	private String actKey;

	/**
	 * 活动介绍
	 */
	@Column
	private String intro;

	/**
	 * 开始时间
	 */
	@Column
	private Date beginTime;

	/**
	 * 结束时间
	 */
	@Column
	private Date endTime;

	/**
	 * 活动状态
	 */
	@Column
	private byte actStatus;

	/**
	 * 注意事项
	 */
	@Column
	private String spintro;

	/**
	 * 活动柜报名人员是否需要审核
	 */
	@Column
	private byte userNeedCheckflg;

	/**
	 * 地区
	 */
	@Column
	private int pcityId;

	/**
	 * 是否限制会员报名
	 */
	@Column
	private byte memberLimitflg;

	/**
	 * 费用
	 */
	@Column
	private double actCost;

	/**
	 * 人数限制
	 */
	@Column
	private int userLimitCount;

	/**
	 * 地址
	 */
	@Column
	private String addr;

	/**
	 * 足迹id
	 * 
	 * @return
	 */
	@Column
	private long companyId;

	/**
	 * 联盟id
	 * 
	 * @return
	 */
	@Column
	private long uid;

	/**
	 * 创建时间
	 * 
	 * @return
	 */
	@Column
	private Date createTime;

	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getKindId() {
		return kindId;
	}

	public void setKindId(long kindId) {
		this.kindId = kindId;
	}

	public String getActKey() {
		return actKey;
	}

	public void setActKey(String actKey) {
		this.actKey = actKey;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public byte getActStatus() {
		return actStatus;
	}

	public void setActStatus(byte actStatus) {
		this.actStatus = actStatus;
	}

	public String getSpintro() {
		return spintro;
	}

	public void setSpintro(String spintro) {
		this.spintro = spintro;
	}

	public int getPcityId() {
		return pcityId;
	}

	public void setPcityId(int pcityId) {
		this.pcityId = pcityId;
	}

	public int getUserLimitCount() {
		return userLimitCount;
	}

	public void setUserLimitCount(int userLimitCount) {
		this.userLimitCount = userLimitCount;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public byte getUserNeedCheckflg() {
		return userNeedCheckflg;
	}

	public void setUserNeedCheckflg(byte userNeedCheckflg) {
		this.userNeedCheckflg = userNeedCheckflg;
	}

	public byte getMemberLimitflg() {
		return memberLimitflg;
	}

	public void setMemberLimitflg(byte memberLimitflg) {
		this.memberLimitflg = memberLimitflg;
	}

	public double getActCost() {
		return actCost;
	}

	public void setActCost(double actCost) {
		this.actCost = actCost;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.name);
		if (DataUtil.isEmpty(s) || s.length() > 30) {
			return Err.CMPACT_NAME_ERROR;
		}
		if (this.kindId <= 0) {
			return Err.CMPACT_KINDID_ERROR;
		}
		s = DataUtil.toText(this.intro);
		if (s != null && s.length() > 3000) {
			return Err.CMPACT_INTRO_ERROR;
		}
		s = DataUtil.toText(spintro);
		if (s != null && s.length() > 100) {
			return Err.CMPACT_SPINTRO_ERROR;
		}
		if (this.beginTime == null) {
			return Err.CMPACT_BEGINTIME_ERROR;
		}
		if (this.endTime == null) {
			return Err.CMPACT_ENDTIME_ERROR;
		}
		if (this.beginTime.getTime() > this.endTime.getTime()) {
			return Err.CMPACT_TIME_ERROR;
		}
		s = DataUtil.toText(addr);
		if (DataUtil.isEmpty(s) || s.length() > 200) {
			return Err.CMPACT_ADDR_ERROR;
		}
		if (this.pcityId <= 0) {
			return Err.CMPACT_PCITYID_ERROR;
		}
		return Err.SUCCESS;
	}

	public boolean isPause() {
		if (this.actStatus == ACTSTATUS_PAUSE) {
			return true;
		}
		return false;
	}

	public boolean isAdminPause() {
		if (this.actStatus == ACTSTATUS_ADMINPAUSE) {
			return true;
		}
		return false;
	}

	public boolean isRun() {
		if (this.actStatus == ACTSTATUS_RUN) {
			return true;
		}
		return false;
	}

	public boolean isInvaild() {
		if (this.actStatus == ACTSTATUS_INVALID) {
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

	public boolean isOnlyMemberJoin() {
		if (this.memberLimitflg == MEMBERLIMITFLG_Y) {
			return true;
		}
		return false;
	}

	public boolean isUserNeedCheck() {
		if (this.userNeedCheckflg == USERNEEDCHECKFLG_Y) {
			return true;
		}
		return false;
	}

	public boolean isEnd() {
		if (this.endTime.getTime() < System.currentTimeMillis()) {
			return true;
		}
		return false;
	}
}