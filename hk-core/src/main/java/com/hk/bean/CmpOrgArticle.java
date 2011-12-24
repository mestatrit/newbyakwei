package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 企业相关文章
 * 
 * @author akwei
 */
@Table(name = "cmporgarticle")
public class CmpOrgArticle {

	public static final byte HIDETITLEFLG_N = 0;

	public static final byte HIDETITLEFLG_Y = 1;

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private long orgId;

	@Column
	private long navId;

	@Column
	private String title;

	@Column
	private Date createTime;

	/**
	 * 图片路径
	 */
	@Column
	private String path;

	/**
	 * 排序号，越大越靠前
	 */
	@Column
	private int orderflg;

	@Column
	private byte hideTitleflg;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getOrderflg() {
		return orderflg;
	}

	public void setOrderflg(int orderflg) {
		this.orderflg = orderflg;
	}

	public boolean isHideTitle() {
		if (this.hideTitleflg == HIDETITLEFLG_Y) {
			return true;
		}
		return false;
	}

	public byte getHideTitleflg() {
		return hideTitleflg;
	}

	public void setHideTitleflg(byte hideTitleflg) {
		this.hideTitleflg = hideTitleflg;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPic120() {
		return ImageConfig.getCmpOrgFile120Url(this.path);
	}

	public String getPic320() {
		return ImageConfig.getCmpOrgFile320Url(this.path);
	}

	public int validate() {
		String s = DataUtil.toText(this.title);
		if (DataUtil.isEmpty(s) || s.length() > 50) {
			return Err.CMPORGARTICLE_TITLE_ERROR;
		}
		return Err.SUCCESS;
	}

	public long getNavId() {
		return navId;
	}

	public void setNavId(long navId) {
		this.navId = navId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
}