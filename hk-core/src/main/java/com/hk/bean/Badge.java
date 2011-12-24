package com.hk.bean;

import java.util.Map;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.JsonUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 徽章对象
 * 
 * @author akwei
 */
@Table(name = "badge")
public class Badge {

	public static final byte CYCLE_WEEK = 0;

	public static final byte CYCLE_MONTH = 1;

	public static final byte STOPFLG_N = 0;

	public static final byte STOPFLG_Y = 1;

	/**
	 * 不限制
	 */
	public static final byte LIMITFLG_N = 0;

	/**
	 * 限制
	 */
	public static final byte LIMITFLG_Y = 1;

	/**
	 * 系统设置
	 */
	public static final byte LIMITFLG_SYS = 2;

	public static final byte LIMITFLG_INVITE = 3;

	/**
	 * 冒险家，到过10个不同的地方
	 */
	public static final int LIMITSYS_RULEFLG_ADVENTURER = 1001;

	/**
	 * 探险家，到过25个不同的地方
	 */
	public static final int LIMITSYS_RULEFLG_EXPLORER = 1002;

	/**
	 * 超级巨星，到过50个不同的地方
	 */
	public static final int LIMITSYS_RULEFLG_SUPERSTAR = 1003;

	/**
	 * 跟3个异性一起到达某地
	 */
	public static final int LIMITSYS_RULEFLG_PLAYA_PLEASE = 1004;

	/**
	 * 一晚上到过4个不同的地方
	 */
	public static final int LIMITSYS_RULEFLG_CRUNKED = 1005;

	/**
	 * 一晚上在同一个地方报到3次
	 */
	public static final int LIMITSYS_RULEFLG_LOCAL = 1006;

	/**
	 * 1个月报到30次
	 */
	public static final int LIMITSYS_RULEFLG_SUPER_USER = 1007;

	/**
	 * 12个小时内有10个不同地方的报到
	 */
	public static final int LIMITSYS_RULEFLG_OVER_SHARE = 1008;

	/**
	 * 连续4个晚上都在某处
	 */
	public static final int LIMITSYS_RULEFLG_BENDER = 1009;

	/**
	 * 晚上3点以后还在出没
	 */
	public static final int LIMITSYS_RULEFLG_SCHOOL_NIGHT = 1010;

	/**
	 * 同时拥有10个地主席位
	 */
	public static final int LIMITSYS_RULEFLG_SUPER_MAYOR = 1011;

	/**
	 *30天之内到过10个体育馆
	 */
	public static final int LIMITSYS_RULEFLG_GYM_RAT = 1012;

	/**
	 *报到过3个KTV
	 */
	public static final int LIMITSYS_RULEFLG_DONOT_STOP_BELIEVIN = 1013;

	/**
	 *发现了3个新地方
	 */
	public static final int LIMITSYS_RULEFLG_CREATE_3_VENUE = 1014;

	/**
	 * 足迹周期类型
	 */
	public static final int RULEFLG_CMP_CYCLE_CHECKIN_NUM = 1;

	/**
	 * 足迹报到次数
	 */
	public static final int RULEFLG_CMP_CHECKIN_NUM = 2;

	/**
	 * 足迹的分类报到次数
	 */
	public static final int RULEFLG_CMPKIND_CHECKIN_NUM = 3;

	/**
	 * 足迹的子分类报到次数
	 */
	public static final int RULEFLG_CMPCHILDKIND_CHECKIN_NUM = 4;

	/**
	 * 足迹组的报到次数
	 */
	public static final int RULEFLG_CMPGROUP_CHECKIN_NUM = 5;

	@Id
	private long badgeId;

	@Column
	private String name;

	/**
	 * 徽章图片路径
	 */
	@Column
	private String path;

	/**
	 * 徽章规则介绍
	 */
	@Column
	private String intro;

	/**
	 * 徽章规则数据
	 */
	@Column
	private String ruleData;

	/**
	 * 徽章类型 {@link Badge.LIMITFLG_Y}{@link Badge.LIMITFLG_N}
	 */
	@Column
	private byte limitflg;

	/**
	 * 规则类型，对于限制类型使用({@link Badge.LIMITFLG_Y} )
	 */
	@Column
	private int ruleflg;

	/**
	 * 足迹id
	 */
	@Column
	private long companyId;

	/**
	 * 火酷系统足迹组的id
	 */
	@Column
	private long groupId;

	/**
	 * 分类id
	 */
	@Column
	private int parentKindId;

	/**
	 * 子分类id
	 */
	@Column
	private int kindId;

	/**
	 * 是否停用
	 */
	@Column
	private byte stopflg;

	private Company company;

	private CmpAdminGroup cmpAdminGroup;

	private ParentKind parentKind;

	private CompanyKind companyKind;

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public byte getLimitflg() {
		return limitflg;
	}

	public void setLimitflg(byte limitflg) {
		this.limitflg = limitflg;
	}

	public String getRuleData() {
		return ruleData;
	}

	public void setRuleData(String ruleData) {
		this.ruleData = ruleData;
	}

	public long getBadgeId() {
		return badgeId;
	}

	public void setBadgeId(long badgeId) {
		this.badgeId = badgeId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public byte getStopflg() {
		return stopflg;
	}

	public void setStopflg(byte stopflg) {
		this.stopflg = stopflg;
	}

	public int getRuleflg() {
		return ruleflg;
	}

	public void setRuleflg(int ruleflg) {
		this.ruleflg = ruleflg;
	}

	public String getPic300() {
		return ImageConfig.getBadge300PicUrl(this.path);
	}

	public String getPic57() {
		return ImageConfig.getBadge57PicUrl(this.path);
	}

	public boolean isLimit() {
		if (this.limitflg == LIMITFLG_Y) {
			return true;
		}
		return false;
	}

	public boolean isNoLimit() {
		if (this.limitflg == LIMITFLG_N) {
			return true;
		}
		return false;
	}

	public boolean isSysLimit() {
		if (this.limitflg == LIMITFLG_SYS) {
			return true;
		}
		return false;
	}

	private Map<String, String> tmp_map = null;

	private String getDataFromMap(String key) {
		if (this.ruleData == null) {
			return null;
		}
		if (tmp_map == null) {
			tmp_map = JsonUtil.getMapFromJson(this.ruleData);
		}
		return tmp_map.get(key);
	}

	public int getNum() {
		String s = this.getDataFromMap("checkinnum");
		if (s != null) {
			return Integer.valueOf(s);
		}
		return 0;
	}

	public int getCycle() {
		String s = this.getDataFromMap("cycle");
		if (s != null) {
			return Integer.valueOf(s);
		}
		return 0;
	}

	public boolean isRuleCycle() {
		if (this.ruleflg == RULEFLG_CMP_CYCLE_CHECKIN_NUM) {
			return true;
		}
		return false;
	}

	public boolean isRuleCheckin() {
		if (this.ruleflg == RULEFLG_CMP_CHECKIN_NUM) {
			return true;
		}
		return false;
	}

	public boolean isRuleCmpKind() {
		if (this.ruleflg == RULEFLG_CMPKIND_CHECKIN_NUM) {
			return true;
		}
		return false;
	}

	public boolean isRuleCmpChildKind() {
		if (this.ruleflg == RULEFLG_CMPCHILDKIND_CHECKIN_NUM) {
			return true;
		}
		return false;
	}

	public boolean isRuleCmpGroup() {
		if (this.ruleflg == RULEFLG_CMPGROUP_CHECKIN_NUM) {
			return true;
		}
		return false;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CmpAdminGroup getCmpAdminGroup() {
		return cmpAdminGroup;
	}

	public void setCmpAdminGroup(CmpAdminGroup cmpAdminGroup) {
		this.cmpAdminGroup = cmpAdminGroup;
	}

	public ParentKind getParentKind() {
		return parentKind;
	}

	public void setParentKind(ParentKind parentKind) {
		this.parentKind = parentKind;
	}

	public CompanyKind getCompanyKind() {
		return companyKind;
	}

	public void setCompanyKind(CompanyKind companyKind) {
		this.companyKind = companyKind;
	}

	public int getParentKindId() {
		return parentKindId;
	}

	public void setParentKindId(int parentKindId) {
		this.parentKindId = parentKindId;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}

	public boolean isCycleWeek() {
		if (this.getCycle() == CYCLE_WEEK) {
			return true;
		}
		return false;
	}

	public boolean isCycleMonth() {
		if (this.getCycle() == CYCLE_MONTH) {
			return true;
		}
		return false;
	}

	public String getCompanyName() {
		return this.getDataFromMap("companyname");
	}

	public String getGroupName() {
		return this.getDataFromMap("groupname");
	}

	public String getParentKindName() {
		return this.getDataFromMap("parentname");
	}

	public String getKindName() {
		return this.getDataFromMap("kindname");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInvite() {
		if (this.limitflg == LIMITFLG_INVITE) {
			return true;
		}
		return false;
	}

	public int validate() {
		String s = DataUtil.toTextRow(name);
		if (DataUtil.isEmpty(s) || s.length() > 20) {
			return Err.BADGE_NAME_ERROR;
		}
		s = DataUtil.toTextRow(intro);
		if (s != null && s.length() > 300) {
			return Err.BADGE_INTRO_ERROR;
		}
		return Err.SUCCESS;
	}
}