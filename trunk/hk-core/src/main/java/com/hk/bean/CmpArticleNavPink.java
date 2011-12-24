package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.P;

/**
 * 栏目下推荐的文章
 * 
 * @author akwei
 */
@Table(name = "cmparticlenavpink")
public class CmpArticleNavPink {

	@Id
	private long oid;

	@Column
	private long articleId;

	@Column
	private long companyId;

	@Column
	private long navId;

	/**
	 * 显示文章的段落标记 0:不限制,1:显示第一段2:显示前2段
	 */
	@Column
	private int pflg;

	/**
	 * 顺序号
	 */
	@Column
	private int orderflg;

	private CmpArticle cmpArticle;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
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

	public long getNavId() {
		return navId;
	}

	public void setNavId(long navId) {
		this.navId = navId;
	}

	public CmpArticle getCmpArticle() {
		return cmpArticle;
	}

	public void setCmpArticle(CmpArticle cmpArticle) {
		this.cmpArticle = cmpArticle;
	}

	public int getPflg() {
		return pflg;
	}

	public void setPflg(int pflg) {
		this.pflg = pflg;
	}

	public int getOrderflg() {
		return orderflg;
	}

	public void setOrderflg(int orderflg) {
		this.orderflg = orderflg;
	}

	public String getContent() {
		if (this.cmpArticle != null) {
			if (this.cmpArticle.getCmpArticleContent() != null) {
				String content = this.cmpArticle.getCmpArticleContent()
						.getContent();
				content = content.replaceAll("(<br/>)+{2,}", "<br/>");
				int fromIndex = 0;
				for (int i = 0; i < pflg; i++) {
					fromIndex = content.indexOf("<br/>", fromIndex);
					if (fromIndex != -1) {
						fromIndex += 5;
					}
				}
				if (fromIndex <= 0) {
					return content;
				}
				if (fromIndex <= content.length() - 1) {
					content = content.substring(0, fromIndex);
					int last_idx = content.lastIndexOf("<br/>");
					if (last_idx + 5 == content.length()) {
						return content.substring(0, last_idx);
					}
					return content;
				}
				return content;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		String v = "aaaaa<br/>bbbbbb<br/>ccccc<br/><br/><br/>dddddd";
		// int pflg = 3;
		// int fromIndex = 0;
		// for (int i = 0; i < pflg; i++) {
		// fromIndex = v.indexOf("<br/>", fromIndex);
		// if (fromIndex != -1) {
		// fromIndex += 5;
		// }
		// }
		// if (fromIndex <= v.length() - 1) {
		// P.println(v.substring(0, fromIndex));
		// }
		// else {
		// P.println(v);
		// }
		v = v.replaceAll("(<br/>)+{2,}", "<br/>");
		P.println(v);
	}
}