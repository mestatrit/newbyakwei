package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 被推荐的广告
 * 
 * @author akwei
 */
@Table(name = "cmpadblock")
public class CmpAdBlock {

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private long blockId;

	@Column
	private long adid;

	/**
	 * 页面类型,1:首页,2:二级页面,3:三级页面
	 */
	@Column
	private byte pageflg;

	private CmpAd cmpAd;

	public void setCmpAd(CmpAd cmpAd) {
		this.cmpAd = cmpAd;
	}

	public CmpAd getCmpAd() {
		return cmpAd;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getBlockId() {
		return blockId;
	}

	public void setBlockId(long blockId) {
		this.blockId = blockId;
	}

	public long getAdid() {
		return adid;
	}

	public void setAdid(long adid) {
		this.adid = adid;
	}

	public byte getPageflg() {
		return pageflg;
	}

	public void setPageflg(byte pageflg) {
		this.pageflg = pageflg;
	}
}