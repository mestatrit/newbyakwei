package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 企业服务(预定类别)
 * 
 * @author akwei
 */
@Table(name = "cmpsvr")
public class CmpSvr {

	@Id
	private long svrId;

	@Column
	private long companyId;

	@Column
	private String name;

	@Column
	private long photosetId;

	@Column
	private double price;

	/**
	 * 服务时间长，单位为分钟
	 */
	@Column
	private int svrmin;

	@Column
	private String intro;

	@Column
	private long kindId;

	public long getSvrId() {
		return svrId;
	}

	public void setSvrId(long svrId) {
		this.svrId = svrId;
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

	public long getPhotosetId() {
		return photosetId;
	}

	public void setPhotosetId(long photosetId) {
		this.photosetId = photosetId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getSvrmin() {
		return svrmin;
	}

	public void setSvrmin(int svrmin) {
		this.svrmin = svrmin;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 50)) {
			return Err.CMPSVR_NAME_ERROR;
		}
		if (!HkValidate.validateLength(this.intro, true, 500)) {
			return Err.CMPSVR_INTRO_ERROR;
		}
		return Err.SUCCESS;
	}

	public long getKindId() {
		return kindId;
	}

	public void setKindId(long kindId) {
		this.kindId = kindId;
	}
}