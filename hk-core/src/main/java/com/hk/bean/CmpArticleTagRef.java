package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 文章标签关联表
 * 
 * @author akwei
 */
@Table(name = "cmparticletagref")
public class CmpArticleTagRef {

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private long tagId;

	@Column
	private long articleId;

	private CmpArticle cmpArticle;

	private CmpArticleTag cmpArticleTag;

	public void setCmpArticleTag(CmpArticleTag cmpArticleTag) {
		this.cmpArticleTag = cmpArticleTag;
	}

	public CmpArticleTag getCmpArticleTag() {
		return cmpArticleTag;
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

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public CmpArticle getCmpArticle() {
		return cmpArticle;
	}

	public void setCmpArticle(CmpArticle cmpArticle) {
		this.cmpArticle = cmpArticle;
	}
}