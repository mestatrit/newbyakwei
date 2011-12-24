package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.ImageConfig;

/**
 * 企业网站相关文件
 * 
 * @author akwei
 */
@Table(name = "cmporgfile")
public class CmpOrgFile {

	public static final byte TOPFLG_N = 0;

	public static final byte TOPFLG_Y = 1;

	@Id
	private long oid;

	/**
	 * 关联机构文章id
	 */
	@Column
	private long articleOid;

	@Column
	private long orgId;

	@Column
	private long companyId;

	@Column
	private String path;

	/**
	 * 是否是置顶文件
	 */
	@Column
	private byte topflg;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getArticleOid() {
		return articleOid;
	}

	public void setArticleOid(long articleOid) {
		this.articleOid = articleOid;
	}

	public byte getTopflg() {
		return topflg;
	}

	public void setTopflg(byte topflg) {
		this.topflg = topflg;
	}

	public boolean isTopInFile() {
		if (this.topflg == TOPFLG_Y) {
			return true;
		}
		return false;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getPic60() {
		return ImageConfig.getCmpOrgFile60Url(this.path);
	}

	public String getPic120() {
		return ImageConfig.getCmpOrgFile120Url(this.path);
	}

	public String getPic240() {
		return ImageConfig.getCmpOrgFile240Url(this.path);
	}

	public String getPic320() {
		return ImageConfig.getCmpOrgFile320Url(this.path);
	}

	public String getPic600() {
		return ImageConfig.getCmpOrgFile600Url(this.path);
	}

	public String getCmpFilePic800() {
		return ImageConfig.getCmpOrgFile800Url(this.path);
	}
}