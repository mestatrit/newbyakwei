package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.svr.pub.ImageConfig;

@Table(name = "cmpproduct", id = "productid")
public class CmpProduct {

	public static final byte DELFLG_N = 0;

	public static final byte DELFLG_Y = 1;

	public static final byte SELLSTATUS_N = 0;

	public static final byte SELLSTATUS_Y = 1;

	@Id
	private long productId;

	@Column
	private String name;

	@Column
	private long companyId;

	@Column
	private int sortId;

	@Column
	private String intro;

	@Column
	private double money;

	@Column
	private double rebate;

	@Column
	private byte delflg;

	@Column
	private String headPath;

	@Column
	private int score;// 打分

	@Column
	private int scoreUserCount;// 打分人数

	@Column
	private byte sellStatus;// 销售状态

	@Column
	private int reviewCount;

	/**
	 * 设置产品显示顺序，从大到小排
	 */
	@Column
	private int orderflg;// 排序顺序字段

	/**
	 * 产品编号
	 */
	@Column
	private String pnum;

	/**
	 * 产品缩写
	 */
	@Column
	private String shortName;

	/**
	 * 联盟标识
	 */
	@Column
	private long uid;

	@Column
	private long cmpUnionKindId;

	@Column
	private int pcityId;

	@Column
	private byte cmppink;

	@Column
	private Date cmppinkTime;

	public void setPcityId(int pcityId) {
		this.pcityId = pcityId;
	}

	public int getPcityId() {
		return pcityId;
	}

	private CmpUnionKind cmpUnionKind;

	public void setCmpUnionKind(CmpUnionKind cmpUnionKind) {
		this.cmpUnionKind = cmpUnionKind;
	}

	public CmpUnionKind getCmpUnionKind() {
		return cmpUnionKind;
	}

	public int getOrderflg() {
		return orderflg;
	}

	public void setOrderflg(int orderflg) {
		this.orderflg = orderflg;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScoreUserCount() {
		return scoreUserCount;
	}

	public void setScoreUserCount(int scoreUserCount) {
		this.scoreUserCount = scoreUserCount;
	}

	/**
	 * 是否正在销售中
	 * 
	 * @return
	 */
	public boolean isSell() {
		if (this.sellStatus == SELLSTATUS_Y) {
			return true;
		}
		return false;
	}

	private CmpProductSort cmpProductSort;

	public byte getSellStatus() {
		return sellStatus;
	}

	public void setSellStatus(byte sellStatus) {
		this.sellStatus = sellStatus;
	}

	public void setCmpProductSort(CmpProductSort cmpProductSort) {
		this.cmpProductSort = cmpProductSort;
	}

	public CmpProductSort getCmpProductSort() {
		return cmpProductSort;
	}

	public byte getDelflg() {
		return delflg;
	}

	public void setDelflg(byte delflg) {
		this.delflg = delflg;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public int getSortId() {
		return sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getRebate() {
		return rebate;
	}

	public void setRebate(double rebate) {
		this.rebate = rebate;
	}

	public String getHead60() {
		return ImageConfig.getPic60Url(headPath);
	}

	public String getHead120() {
		return ImageConfig.getPic120Url(headPath);
	}

	public String getHead240() {
		return ImageConfig.getPic240Url(headPath);
	}

	public String getHead320() {
		return ImageConfig.getPic320Url(headPath);
	}

	public String getHead600() {
		return ImageConfig.getPic600Url(headPath);
	}

	public String getHead800() {
		return ImageConfig.getPic800Url(headPath);
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.name);
		if (DataUtil.isEmpty(s) || s.length() > 30) {
			return Err.CMPPRODUCT_NAME_ERROR;
		}
		if (sortId <= 0) {
			return Err.CMPPRODUCT_SORTID_ERROR;
		}
		if (companyId <= 0) {
			return Err.HKOBJID_ERROR;
		}
		s = DataUtil.toTextRow(this.intro);
		if (!DataUtil.isEmpty(s)) {
			if (s.length() > 200) {
				return Err.CMPPRODUCT_INTRO_LENGTH_TOO_LONG;
			}
		}
		if (this.money < 0) {
			return Err.CMPPRODUCT_MONEY_ERROR;
		}
		if (this.rebate < 0) {
			return Err.CMPPRODUCT_REBATE_ERROR;
		}
		s = DataUtil.toTextRow(this.pnum);
		if (s != null && s.length() > 10) {
			return Err.CMPPRODUCT_PNUM_ERROR;
		}
		s = DataUtil.toTextRow(this.shortName);
		if (s != null && s.length() > 10) {
			s = s.substring(0, 10);
			this.shortName = s;
		}
		return Err.SUCCESS;
	}

	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	/**
	 * web使用
	 * 
	 * @return
	 */
	public int getStarsLevel() {
		return HkSvrUtil.getStarsLevel(this.score, this.scoreUserCount);
	}

	/**
	 * wap使用
	 * 
	 * @return
	 */
	public String getStarsRate() {
		return HkSvrUtil.makeCssStarRate(this.score, this.scoreUserCount);
	}

	public String getPnum() {
		return pnum;
	}

	public void setPnum(String pnum) {
		this.pnum = pnum;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getCmpUnionKindId() {
		return cmpUnionKindId;
	}

	public void setCmpUnionKindId(long cmpUnionKindId) {
		this.cmpUnionKindId = cmpUnionKindId;
	}

	public byte getCmppink() {
		return cmppink;
	}

	public void setCmppink(byte cmppink) {
		this.cmppink = cmppink;
	}

	public Date getCmppinkTime() {
		return cmppinkTime;
	}

	public void setCmppinkTime(Date cmppinkTime) {
		this.cmppinkTime = cmppinkTime;
	}

	public boolean isPinkForCmp() {
		if (this.cmppink == CmpUtil.CMPPINK_Y) {
			return true;
		}
		return false;
	}

	public String getSimpleIntro() {
		return DataUtil.limitLength(this.intro, 60);
	}
}