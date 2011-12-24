package com.hk.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 宝箱可设置虚拟物品，只有管理员才能设置是否有虚拟物品，是虚拟物品的箱子，不能开副产品.管理员可以设置是否让其他箱子开副产品
 * 
 * @author akwei
 */
@Table(name = "box", id = "boxid")
public class Box {

	public static final byte BOX_STATUS_NORMAL = 0;

	public static final byte BOX_STATUS_PAUSE = 1;

	public static final byte BOX_STATUS_STOP = 2;

	public static final byte BOX_OPENTYPE_SMSANDWEB = 0;

	public static final byte BOX_OPENTYPE_SMS = 1;

	public static final byte BOX_OPENTYPE_WEB = 2;

	public static final byte CHECKFLG_TMP = 0;

	public static final byte CHECKFLG_UNCHECK = 1;

	public static final byte CHECKFLG_CHECKFAIL = 2;

	public static final byte CHECKFLG_CHECKOK = 3;

	public static final byte PINKFLG_N = 0;

	/**
	 * 精华
	 */
	public static final byte PINKFLG_PINK = 1;

	/**
	 * 置顶
	 */
	public static final byte PINKFLG_TOP = 2;

	/**
	 * 不是虚拟
	 */
	public static final byte VIRTUALFLG_N = 0;

	/**
	 * 虚拟
	 */
	public static final byte VIRTUALFLG_Y = 1;

	/**
	 * 不具有副产品开启功能
	 */
	public static final byte OTHERPRIZEFLG_N = 0;

	/**
	 * 具有副产品开启功能
	 */
	public static final byte OTHERPRIZEFLG_Y = 1;

	@Id
	private long boxId;

	@Column
	private long userId;

	@Column
	private int totalCount;

	@Column
	private String name;

	@Column
	private byte boxStatus;

	@Column
	private String boxKey;

	@Column
	private Date beginTime;

	@Column
	private Date endTime;

	@Column
	private Date createTime;

	@Column
	private int openCount;

	@Column
	private int boxType;

	@Column
	private int precount;

	/**
	 * 开箱频率设置
	 */
	@Column
	private byte pretype;

	@Column
	private String intro;

	@Column
	private byte opentype;

	@Column
	private long companyId;

	/**
	 * 联盟标识
	 */
	@Column
	private long uid;

	@Column
	private byte checkflg;// 0:未完成 1:未审核 2:审核不通过 3:审核通过

	@Column
	private String path;

	@Column
	private int cityId;

	@Column
	private byte pinkflg;

	/**
	 * 是否开虚拟物品
	 */
	@Column
	private byte virtualflg;

	/**
	 * 是否具有副产品获得功能
	 */
	@Column
	private byte otherPrizeflg;

	@Column
	private byte cmppink;

	@Column
	private Date cmppinkTime;

	public void setPinkflg(byte pinkflg) {
		this.pinkflg = pinkflg;
	}

	public byte getPinkflg() {
		return pinkflg;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public byte getCheckflg() {
		return checkflg;
	}

	public void setCheckflg(byte checkflg) {
		this.checkflg = checkflg;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public byte getOpentype() {
		return opentype;
	}

	public void setOpentype(byte opentype) {
		this.opentype = opentype;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getIntro() {
		return intro;
	}

	public int getBoxType() {
		return boxType;
	}

	public void setBoxType(int boxType) {
		this.boxType = boxType;
	}

	public long getBoxId() {
		return boxId;
	}

	public void setBoxId(long boxId) {
		this.boxId = boxId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getBoxStatus() {
		return boxStatus;
	}

	public void setBoxStatus(byte boxStatus) {
		this.boxStatus = boxStatus;
	}

	public String getBoxKey() {
		return boxKey;
	}

	public void setBoxKey(String boxKey) {
		this.boxKey = boxKey;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getOpenCount() {
		return openCount;
	}

	public void setOpenCount(int openCount) {
		this.openCount = openCount;
	}

	public int getPrecount() {
		return precount;
	}

	public void setPrecount(int precount) {
		this.precount = precount;
	}

	public byte getPretype() {
		return pretype;
	}

	public void setPretype(byte pretype) {
		this.pretype = pretype;
	}

	public int validate(List<String> notExistList) {
		if (getUserId() == 0) {
			return Err.USERID_ERROR;
		}
		if (DataUtil.isEmpty(getName())
				|| DataUtil.toText(getName()).length() > 15) {
			return Err.BOX_NAME_ERROR;
		}
		if (getTotalCount() <= 0) {
			return Err.BOX_TOTALCOUNT_OUT_OF_LENGTH;
		}
		// if (checkNowTime) {
		// long now = System.currentTimeMillis();
		// if (getBeginTime() == null || getBeginTime().getTime() < now) {
		// return Err.BOX_BEGINTIME_ERROR;
		// }
		// if (getEndTime() == null || getEndTime().getTime() < now) {
		// return Err.BOX_ENDTIME_ERROR;
		// }
		// }
		if (getBeginTime().getTime() >= getEndTime().getTime()) {
			return Err.BOX_TIME_ERROR;
		}
		int code = this.validateBoxKey(notExistList);
		if (code != Err.SUCCESS) {
			return code;
		}
		if (getIntro() != null && getIntro().length() > 500) {
			return Err.BOX_CONTENT_LENG_TOO_LONG;
		}
		// if (this.boxId > 0 && this.boxType == 0) {
		// return Err.BOX_BOXTYPE_ERROR;
		// }
		if (this.pretype != 0) {
			if (this.precount == 0) {
				return Err.BOX_PRECOUNT_ERROR;
			}
		}
		return Err.SUCCESS;
	}

	public int validateBoxKey(List<String> notExistList) {
		String key = DataUtil.toText(boxKey);
		if (!DataUtil.isEmpty(boxKey)) {
			if (key.length() > 10 || key.length() <= 2) {
				return Err.BOX_BOXKEY_DATA_ERROR;
			}
			// if (!key.startsWith("bx")) {
			// return Err.BOX_BOXKEY_DATA_ERROR;
			// }
			for (String s : notExistList) {
				if (key.equals(s)) {
					return Err.BOX_BOXKEY_IS_SYS_KEY;
				}
			}
		}
		return 0;
	}

	public boolean isExpired() {
		if (this.endTime.getTime() < System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

	public boolean isStop() {
		if (this.boxStatus == BOX_STATUS_STOP) {
			return true;
		}
		return false;
	}

	public boolean isPause() {
		if (this.boxStatus == BOX_STATUS_PAUSE) {
			return true;
		}
		return false;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getPic() {
		return ImageConfig.getPhotoPicH_0Url(this.path);
	}

	public String getSimpleIntro() {
		if (DataUtil.isEmpty(intro)) {
			return null;
		}
		return DataUtil.limitLength(intro, 70);
	}

	public boolean isUnChecked() {
		if (this.checkflg == CHECKFLG_UNCHECK) {
			return true;
		}
		return false;
	}

	public String toJsonData() {
		Map<String, String> map = new HashMap<String, String>();
		if (name != null) {
			map.put("name", this.name);
		}
		map.put("totalcount", String.valueOf(this.totalCount));
		map.put("boxkey", this.boxKey);
		if (beginTime != null) {
			map.put("begintime", String.valueOf(this.beginTime.getTime()));
		}
		if (endTime != null) {
			map.put("endtime", String.valueOf(this.endTime.getTime()));
		}
		map.put("precount", String.valueOf(this.precount));
		map.put("pretype", String.valueOf(this.pretype));
		if (intro != null) {
			map.put("intro", this.intro);
		}
		map.put("opentype", String.valueOf(this.opentype));
		map.put("companyid", String.valueOf(this.companyId));
		map.put("virtualflg", String.valueOf(this.virtualflg));
		return DataUtil.toJson(map);
	}

	public boolean isPink() {
		if (this.pinkflg == PINKFLG_PINK) {
			return true;
		}
		return false;
	}

	public boolean isTop() {
		if (this.pinkflg == PINKFLG_TOP) {
			return true;
		}
		return false;
	}

	public byte getVirtualflg() {
		return virtualflg;
	}

	public void setVirtualflg(byte virtualflg) {
		this.virtualflg = virtualflg;
	}

	public boolean isVirtual() {
		if (this.virtualflg == VIRTUALFLG_Y) {
			return true;
		}
		return false;
	}

	public byte getOtherPrizeflg() {
		return otherPrizeflg;
	}

	public void setOtherPrizeflg(byte otherPrizeflg) {
		this.otherPrizeflg = otherPrizeflg;
	}

	public boolean isOtherPrize() {
		if (this.otherPrizeflg == OTHERPRIZEFLG_Y) {
			return true;
		}
		return false;
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
}