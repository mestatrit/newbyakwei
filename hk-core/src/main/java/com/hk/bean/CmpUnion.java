package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

@Table(name = "cmpunion", id = "uid")
public class CmpUnion {
	public static final byte UNIONSTATUS_RUN = 0;

	public static final byte UNIONSTATUS_LIMIT = 1;

	public static final byte UNIONSTATUS_STOP = 2;

	/**
	 * 不允许创建商户
	 */
	public static final byte CMPCREATEFLG_N = 0;

	/**
	 * 允许创建商户
	 */
	public static final byte CMPCREATEFLG_Y = 1;

	@Id
	private long uid;

	@Column
	private String name;

	@Column
	private String webName;

	@Column
	private String domain;

	@Column
	private String addr;

	@Column
	private double marketX;

	@Column
	private double marketY;

	@Column
	private String intro;

	@Column
	private String logo;

	@Column
	private String traffic;

	/**
	 * 首页模块显示顺序
	 */
	@Column
	private String data;

	@Column
	private Date createTime;

	@Column
	private int pcityId;

	@Column
	private byte cmpcreateflg;

	@Column
	private byte unionStatus;

	public void setCmpcreateflg(byte cmpcreateflg) {
		this.cmpcreateflg = cmpcreateflg;
	}

	public byte getCmpcreateflg() {
		return cmpcreateflg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public double getMarketX() {
		return marketX;
	}

	public void setMarketX(double marketX) {
		this.marketX = marketX;
	}

	public double getMarketY() {
		return marketY;
	}

	public void setMarketY(double marketY) {
		this.marketY = marketY;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.name);
		if (s == null || s.length() > 20) {
			return Err.CMPUNION_NAME_ERROR;
		}
		s = DataUtil.toTextRow(this.addr);
		if (s != null && s.length() > 200) {
			return Err.CMPUNION_ADDR_ERROR;
		}
		s = DataUtil.toText(this.intro);
		if (s != null && s.length() > 500) {
			return Err.CMPUNION_INTRO_ERROR;
		}
		s = DataUtil.toText(this.traffic);
		if (s != null && s.length() > 200) {
			return Err.CMPUNION_TRAFFIC_ERROR;
		}
		if (this.webName != null) {
			if (!DataUtil.isNumberAndChar(this.webName)) {
				return Err.CMPUNION_WEBNAME_ERROR;
			}
		}
		if (this.pcityId <= 0) {
			return Err.CMPUNION_ZONE_ERROR;
		}
		return Err.SUCCESS;
	}

	public String getLogo48Pic() {
		return ImageConfig.getUnionLogo48Url(this.logo);
	}

	public String getLogo80Pic() {
		return ImageConfig.getUnionLogo80Url(this.logo);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getPcityId() {
		return pcityId;
	}

	public void setPcityId(int pcityId) {
		this.pcityId = pcityId;
	}

	public byte getUnionStatus() {
		return unionStatus;
	}

	public void setUnionStatus(byte unionStatus) {
		this.unionStatus = unionStatus;
	}

	public boolean isRun() {
		if (this.unionStatus == UNIONSTATUS_RUN) {
			return true;
		}
		return false;
	}

	public boolean isStop() {
		if (this.unionStatus == UNIONSTATUS_STOP) {
			return true;
		}
		return false;
	}

	public boolean isLimit() {
		if (this.unionStatus == UNIONSTATUS_LIMIT) {
			return true;
		}
		return false;
	}

	public boolean isCanCreateCmp() {
		if (this.cmpcreateflg == CMPCREATEFLG_Y) {
			return true;
		}
		return false;
	}
}