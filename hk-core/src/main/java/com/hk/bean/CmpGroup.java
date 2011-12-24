package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmpgroup", id = "cmpgroupid")
public class CmpGroup {
	public static final byte VALIDATEFLG_N = 0;

	public static final byte VALIDATEFLG_Y = 1;

	private long cmpgroupId;

	private long companyId;

	private byte validateflg;// 0:不开启验证 1:开启验证

	private String name;

	private String intro;

	private String logopath;

	private Date createTime;

	public int validate() {
		String s = DataUtil.toTextRow(name);
		if (DataUtil.isEmpty(s)) {
			return Err.CMPGROUP_NAME_ERROR;
		}
		s = DataUtil.toText(intro);
		if (!DataUtil.isEmpty(s) && s.length() > 200) {
			return Err.CMPGROUP_INTRO_ERROR;
		}
		if (this.validateflg != VALIDATEFLG_N
				&& this.validateflg != VALIDATEFLG_Y) {
			return Err.CMPGROUP_VALIDATEFLG_ERROR;
		}
		return Err.SUCCESS;
	}

	public boolean isOpenValidateflg() {
		if (this.validateflg == 1) {
			return true;
		}
		return false;
	}

	public long getCmpgroupId() {
		return cmpgroupId;
	}

	public void setCmpgroupId(long cmpgroupId) {
		this.cmpgroupId = cmpgroupId;
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

	public byte getValidateflg() {
		return validateflg;
	}

	public void setValidateflg(byte validateflg) {
		this.validateflg = validateflg;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getLogopath() {
		return logopath;
	}

	public void setLogopath(String logopath) {
		this.logopath = logopath;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}