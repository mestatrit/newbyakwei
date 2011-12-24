package com.hk.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.pub.ImageConfig;
import com.hk.svr.pub.ZoneUtil;

@Table(name = "company", id = "companyid")
public class Company {

	public static final byte PRODUCTATTRFLG_N = 0;

	public static final byte PRODUCTATTRFLG_Y = 1;

	/**
	 * 普通企业网站类型
	 */
	public static final byte CMPFLG_NORMAL = 0;

	/**
	 * 电子商务类型
	 */
	public static final byte CMPFLG_E_COMMERCE = 1;

	/**
	 * 教育类型
	 */
	public static final byte CMPFLG_EDU = 2;

	/**
	 * 美发服务预约
	 */
	public static final byte CMPFLG_HAIRDRESSING = 3;

	public static final byte STOPFLG_N = 0;

	public static final byte STOPFLG_Y = 1;

	public static final byte FREEZEFLG_N = 0;// 正常

	public static final byte FREEZEFLG_Y = 1;// 冻结

	public static final byte PSEARCHTYPE_NAME = 0;

	public static final byte PSEARCHTYPE_PNUM = 1;

	public static final byte PSEARCHTYPE_SHORTNAME = 2;

	// public static final byte COMPANYSTATUS_FREEZE = -2;// 冻结
	// public static final byte COMPANYSTATUS_CANCELAUTH = -1;// 解除认领使用，不属于企业状态
	public static final byte COMPANYSTATUS_CHECKFAIL = -1;// 审核不通过

	public static final byte COMPANYSTATUS_UNCHECK = 0;// 未审核(未查看)

	public static final byte COMPANYSTATUS_CHECKED = 1;// 审核通过

	public static final byte COMPANYSTATUS_NORMAL = 2;// 开发中

	@Id
	private long companyId;

	@Column
	private String name;// 企业名称

	@Column
	private String tel;// 企业电话

	@Column
	private int kindId;// 企业分类

	@Column
	private String addr;// 地址

	@Column
	private String headPath;// 企业头像

	@Column
	private Date createTime;// 创建时间

	@Column
	private long userId;// 所有者id 是企业主人

	@Column
	private long createrId;// 创建者

	@Column
	private int totalScore;// 总分

	@Column
	private int totalVote;// 打分人数

	@Column
	private int reviewCount;// 评论数量

	@Column
	private String intro;// 企业介绍

	@Column
	private double markerX;

	@Column
	private double markerY;

	@Column
	private String traffic;// 交通

	/**
	 * 企业状态 -1:冻结（删除相关一切并扣除1个地皮）, 0（未审核），1（已审核）、2（开发中）
	 */
	@Column
	private byte companyStatus;

	@Column
	private int totalMoney;

	@Column
	private byte freezeflg;// 0:正常1:冻结

	@Column
	private byte stopflg;// 0:正常1:停业

	@Column
	private String logopath;

	@Column
	private double price;// 消费

	@Column
	private int parentKindId;// 父分类id

	@Column
	private int hkb;

	@Column
	private double rebate;// 折扣,没有就为0

	@Column
	private int money;// 企业充值余额

	@Column
	private int pcityId;// 新城市表的cityId

	@Column
	private long uid;// 联盟id

	/**
	 * 地主id
	 */
	@Column
	private long mayorUserId;

	/**
	 * 联盟中的分类id
	 */
	@Column
	private long unionKindId;

	/**
	 * 企业搜索名称方式
	 */
	@Column
	private byte psearchType;

	/**
	 * 报到数量
	 */
	@Column
	private int checkInCount;

	/**
	 * 企业网站类型
	 * 参照 {@link CmpInfo#CMPFLG_E_COMMERCE} {@link CmpInfo#CMPFLG_NORMAL}
	 */
	@Column
	private byte cmpflg;

	/**
	 * 企业产品属性套件是否开启(网站开启后有效)
	 */
	@Column
	private byte productattrflg;

	/**
	 * 第二个logo
	 */
	@Column
	private String logo2path;

	/**
	 * 分店名称，没有就为空
	 */
	@Column
	private String sname;

	@Column
	private int workCount;

	public void setCheckInCount(int checkInCount) {
		this.checkInCount = checkInCount;
	}

	public int getCheckInCount() {
		return checkInCount;
	}

	public ParentKind getParentKind() {
		return CompanyKindUtil.getParentKind(this.parentKindId);
	}

	public CompanyKind getCompanyKind() {
		return CompanyKindUtil.getCompanyKind(this.kindId);
	}

	public Pcity getPcity() {
		Pcity pcity = ZoneUtil.getPcity(this.pcityId);
		return pcity;
	}

	public int getPcityId() {
		return pcityId;
	}

	public void setPcityId(int pcityId) {
		this.pcityId = pcityId;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public double getRebate() {
		return rebate;
	}

	public void setRebate(double rebate) {
		this.rebate = rebate;
	}

	public int getHkb() {
		return hkb;
	}

	public void setHkb(int hkb) {
		this.hkb = hkb;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getLogopath() {
		return logopath;
	}

	public void setLogopath(String logopath) {
		this.logopath = logopath;
	}

	public byte getStopflg() {
		return stopflg;
	}

	public void setStopflg(byte stopflg) {
		this.stopflg = stopflg;
	}

	public boolean isStop() {
		if (this.stopflg == STOPFLG_Y) {
			return true;
		}
		return false;
	}

	public byte getFreezeflg() {
		return freezeflg;
	}

	public void setFreezeflg(byte freezeflg) {
		this.freezeflg = freezeflg;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public int getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}

	public void setCompanyStatus(byte companyStatus) {
		this.companyStatus = companyStatus;
	}

	public byte getCompanyStatus() {
		return companyStatus;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getTotalVote() {
		return totalVote;
	}

	public void setTotalVote(int totalVote) {
		this.totalVote = totalVote;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(long createrId) {
		this.createrId = createrId;
	}

	public String getKindData() {
		CompanyKind k = CompanyKindUtil.getCompanyKind(kindId);
		if (k != null) {
			return k.getName();
		}
		return null;
	}

	public String getLogo80() {
		if (this.logopath == null || "".equals(this.logopath)) {
			return null;
		}
		return ImageConfig.getCompanyHead80(this.logopath);
	}

	public String getLogoPic() {
		return ImageConfig.getCompanyHead(this.logopath);
	}

	public String getLogo2Pic() {
		return ImageConfig.getCompanyHead2(this.logo2path);
	}

	public String getLogo48() {
		if (this.logopath == null || "".equals(this.logopath)) {
			return null;
		}
		return ImageConfig.getCompanyHead48(this.logopath);
	}

	public String getHead60() {
		return ImageConfig.getPic60Url(headPath);
	}

	public String getHead240() {
		return ImageConfig.getPic240Url(headPath);
	}

	public String getHead320() {
		return ImageConfig.getPic320Url(headPath);
	}

	public static boolean isStatus(byte status) {
		return DataUtil.isInElements(status, new Object[] {
				COMPANYSTATUS_UNCHECK, COMPANYSTATUS_CHECKED,
				COMPANYSTATUS_CHECKFAIL, COMPANYSTATUS_NORMAL });
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public double getMarkerX() {
		return markerX;
	}

	public void setMarkerX(double markerX) {
		this.markerX = markerX;
	}

	public double getMarkerY() {
		return markerY;
	}

	public void setMarkerY(double markerY) {
		this.markerY = markerY;
	}

	public boolean isCheckSuccess() {
		if (this.companyStatus == COMPANYSTATUS_CHECKED) {
			return true;
		}
		return false;
	}

	public boolean isCheckFail() {
		if (this.companyStatus == COMPANYSTATUS_CHECKFAIL) {
			return true;
		}
		return false;
	}

	public boolean isUnCheck() {
		if (this.companyStatus == COMPANYSTATUS_UNCHECK) {
			return true;
		}
		return false;
	}

	public String getZoneName() {
		Pcity pcity = ZoneUtil.getPcity(this.pcityId);
		if (pcity != null) {
			return pcity.getName();
		}
		return null;
	}

	public boolean isFreeze() {
		if (this.freezeflg == FREEZEFLG_Y) {
			return true;
		}
		return false;
	}

	/**
	 * @param needPcityId 是否需要验证pcityId
	 * @return
	 *         2010-4-26
	 */
	public int validate(boolean needPcityId) {
		if (this.createrId < 1) {
			return Err.USERID_ERROR;
		}
		if (this.userId < 0) {
			return Err.USERID_ERROR;
		}
		String name = DataUtil.toTextRow(getName());
		if (DataUtil.isEmpty(name)) {
			return Err.COMPANY_NAME_ERROR;
		}
		if (name.length() > 30) {
			return Err.COMPANY_NAME_ERROR;
		}
		if (!HkValidate.validateLength(this.sname, true, 30)) {
			return Err.COMPANY_SNAME_ERROR;
		}
		String addr = DataUtil.toText(getAddr());
		if (addr != null && addr.length() > 200) {
			return Err.COMPANY_ADDR_LEN_TOOLONG;
		}
		String tel = DataUtil.toTextRow(getTel());
		if (tel != null) {
			if (tel.length() < 8 || tel.length() > 30) {
				return Err.COMPANY_TEL_LEN_TOOLONG;
			}
		}
		String intro = DataUtil.toText(this.intro);
		if (!DataUtil.isEmpty(intro)) {
			if (intro.length() > 2000) {
				return Err.COMPANY_INTRO_LEN_TOOLONG;
			}
		}
		String traffic = DataUtil.toText(this.traffic);
		if (!DataUtil.isEmpty(traffic)) {
			if (traffic.length() > 200) {
				return Err.COMPANY_TRAFFIC_ERROR;
			}
		}
		if (needPcityId && this.pcityId <= 0) {
			return Err.ZONE_ERROR;
		}
		return Err.SUCCESS;
	}

	public List<Integer> validateList() {
		List<Integer> list = new ArrayList<Integer>();
		if (this.createrId < 1) {
			list.add(Err.USERID_ERROR);
		}
		if (this.userId < 0) {
			list.add(Err.USERID_ERROR);
		}
		String name = DataUtil.toTextRow(getName());
		if (DataUtil.isEmpty(name)) {
			list.add(Err.COMPANY_NAME_ERROR);
		}
		if (!DataUtil.isEmpty(name) && name.length() > 30) {
			list.add(Err.COMPANY_NAME_ERROR);
		}
		if (!HkValidate.validateLength(this.sname, true, 30)) {
			list.add(Err.COMPANY_SNAME_ERROR);
		}
		String addr = DataUtil.toText(getAddr());
		if (addr != null && addr.length() > 200) {
			list.add(Err.COMPANY_ADDR_LEN_TOOLONG);
		}
		String tel = DataUtil.toTextRow(getTel());
		if (tel != null) {
			if (tel.length() < 8 || tel.length() > 30) {
				list.add(Err.COMPANY_TEL_LEN_TOOLONG);
			}
		}
		String intro = DataUtil.toText(this.intro);
		if (!DataUtil.isEmpty(intro)) {
			if (intro.length() > 2000) {
				list.add(Err.COMPANY_INTRO_LEN_TOOLONG);
			}
		}
		String traffic = DataUtil.toText(this.traffic);
		if (!DataUtil.isEmpty(traffic)) {
			if (traffic.length() > 200) {
				list.add(Err.COMPANY_TRAFFIC_ERROR);
			}
		}
		if (this.pcityId <= 0) {
			list.add(Err.ZONE_ERROR);
		}
		return list;
	}

	/**
	 * 认证审核时，需要对分类进行验证，查看是否选择了分类
	 * 
	 * @return
	 *         2010-8-12
	 */
	public List<Integer> validateList2() {
		List<Integer> errlist = this.validateList();
		if (parentKindId <= 0 || kindId <= 0) {
			errlist.add(Err.COMPANY_KIND_ERROR);
		}
		return errlist;
	}

	public int getParentKindId() {
		return parentKindId;
	}

	public void setParentKindId(int parentKindId) {
		this.parentKindId = parentKindId;
	}

	public double getRmb() {
		return HkbConfig.getRmbFromHkb(this.hkb);
	}

	public int getCityId() {
		Pcity pcity = getPcity();
		if (pcity != null) {
			return pcity.getOcityId();
		}
		return 0;
	}

	public int getProvinceId() {
		Pcity pcity = getPcity();
		if (pcity != null) {
			return pcity.getProvinceId();
		}
		return 0;
	}

	public int getStarsLevel() {
		return HkSvrUtil.getStarsLevel(totalScore, totalVote);
	}

	public byte getPsearchType() {
		return psearchType;
	}

	public void setPsearchType(byte psearchType) {
		this.psearchType = psearchType;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getUnionKindId() {
		return unionKindId;
	}

	public void setUnionKindId(long unionKindId) {
		this.unionKindId = unionKindId;
	}

	public long getMayorUserId() {
		return mayorUserId;
	}

	public void setMayorUserId(long mayorUserId) {
		this.mayorUserId = mayorUserId;
	}

	public boolean isMoreIntro() {
		String s = DataUtil.toTextRow(intro);
		if (s == null) {
			return false;
		}
		if (s.length() > 35) {
			return true;
		}
		return false;
	}

	public String getIntroRow() {
		return DataUtil.toHtmlRow(DataUtil.toTextRow(this.intro));
	}

	public String getSimpleIntro() {
		String s = DataUtil.toTextRow(intro);
		if (s == null) {
			return null;
		}
		if (s.length() > 40) {
			s = DataUtil.limitLength(s, 40);
			return DataUtil.toHtmlRow(s);
		}
		return this.intro;
	}

	public String getSimpleTraffic() {
		String s = DataUtil.toTextRow(this.traffic);
		if (DataUtil.isEmpty(s)) {
			return null;
		}
		if (s.length() > 45) {
			s = DataUtil.limitLength(s, 45);
			return DataUtil.toHtmlRow(s);
		}
		return this.traffic;
	}

	public boolean isMoreTraffic() {
		String s = DataUtil.toTextRow(traffic);
		if (s == null) {
			return false;
		}
		if (s.length() > 45) {
			return true;
		}
		return false;
	}

	public String toJsonData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", this.name);
		map.put("tel", this.tel);
		map.put("addr", this.addr);
		return DataUtil.toJson(map);
	}

	public byte getCmpflg() {
		return cmpflg;
	}

	public void setCmpflg(byte cmpflg) {
		this.cmpflg = cmpflg;
	}

	public boolean isCmpFlgE_COMMERCE() {
		if (this.cmpflg == CMPFLG_E_COMMERCE) {
			return true;
		}
		return false;
	}

	public boolean isCmpFlgEnterprise() {
		if (this.cmpflg == CMPFLG_NORMAL) {
			return true;
		}
		return false;
	}

	public byte getProductattrflg() {
		return productattrflg;
	}

	public void setProductattrflg(byte productattrflg) {
		this.productattrflg = productattrflg;
	}

	public boolean isOpenProductattrflg() {
		if (this.productattrflg == PRODUCTATTRFLG_Y) {
			return true;
		}
		return false;
	}

	public boolean isCmpEdu() {
		if (this.cmpflg == CMPFLG_EDU) {
			return true;
		}
		return false;
	}

	public boolean isCmpHairDressing() {
		if (this.cmpflg == CMPFLG_HAIRDRESSING) {
			return true;
		}
		return false;
	}

	public String getLogo2path() {
		return logo2path;
	}

	public void setLogo2path(String logo2path) {
		this.logo2path = logo2path;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getWorkCount() {
		return workCount;
	}

	public void setWorkCount(int workCount) {
		this.workCount = workCount;
	}
}