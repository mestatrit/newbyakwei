package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 企业下属的机构,机构从属于企业
 * 
 * @author akwei
 */
@Table(name = "cmporg")
public class CmpOrg {

	public static final byte FLG_N = 0;

	public static final byte FLG_Y = 1;

	public static final byte USERINFOFLG_N = 0;

	public static final byte USERINFOFLG_Y = 1;

	public static final byte STYLEFLG_N = 0;

	public static final byte STYLEFLG_Y = 1;

	@Id
	private long orgId;

	@Column
	private long companyId;

	@Column
	private String name;

	@Column
	private long userId;

	/**
	 * 是有有效
	 */
	@Column
	private byte flg;

	/**
	 * 是否开启用户详细信息功能
	 */
	@Column
	private byte userinfoflg;

	@Column
	private String styleData;

	/**
	 * 是否可自定义颜色
	 */
	@Column
	private byte styleflg;

	/**
	 * 背景图片路径
	 */
	@Column
	private String path;

	@Column
	private int cityId;

	public void setStyleData(String styleData) {
		this.styleData = styleData;
	}

	public String getStyleData() {
		return styleData;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public byte getFlg() {
		return flg;
	}

	public void setFlg(byte flg) {
		this.flg = flg;
	}

	public boolean isAvailable() {
		if (this.flg == FLG_Y) {
			return true;
		}
		return false;
	}

	public byte getUserinfoflg() {
		return userinfoflg;
	}

	public void setUserinfoflg(byte userinfoflg) {
		this.userinfoflg = userinfoflg;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getBgUrl() {
		return ImageConfig.getCmpOrgBgUrl(this.path);
	}

	public byte getStyleflg() {
		return styleflg;
	}

	public void setStyleflg(byte styleflg) {
		this.styleflg = styleflg;
	}

	public boolean isOpenUserInfo() {
		if (this.userinfoflg == USERINFOFLG_Y) {
			return true;
		}
		return false;
	}

	public boolean isOpenStyle() {
		if (this.styleflg == STYLEFLG_Y) {
			return true;
		}
		return false;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 50)) {
			return Err.CMPORG_NAME_ERROR;
		}
		return Err.SUCCESS;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
}