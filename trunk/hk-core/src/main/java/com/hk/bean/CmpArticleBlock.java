package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 文章区块关系表
 * 
 * @author akwei
 */
@Table(name = "cmparticleblock")
public class CmpArticleBlock {

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private long blockId;

	@Column
	private long articleId;

	/**
	 * 页面类型,1:首页,2:二级页面,3:三级页面
	 */
	@Column
	private byte pageflg;

	@Column
	private long cmpNavOid;

	private CmpArticle cmpArticle;

	public CmpArticle getCmpArticle() {
		return cmpArticle;
	}

	public void setCmpArticle(CmpArticle cmpArticle) {
		this.cmpArticle = cmpArticle;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public void setBlockId(long blockId) {
		this.blockId = blockId;
	}

	public long getBlockId() {
		return blockId;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public byte getPageflg() {
		return pageflg;
	}

	public void setPageflg(byte pageflg) {
		this.pageflg = pageflg;
	}

	public long getCmpNavOid() {
		return cmpNavOid;
	}

	public void setCmpNavOid(long cmpNavOid) {
		this.cmpNavOid = cmpNavOid;
	}
}