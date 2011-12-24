package com.hk.svr.processor;

import java.util.List;

import com.hk.bean.CmpArticle;

/**
 * 全文搜索结果
 * 
 * @author akwei
 */
public class CmpArticleSearchResult {

	/**
	 * 查询出的结果总数量
	 */
	private int totalCount;

	/**
	 * 结果集合
	 */
	private List<CmpArticle> cmpArticles;

	/**
	 * 查询出的结果总数量
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 结果集合
	 */
	public List<CmpArticle> getCmpArticles() {
		return cmpArticles;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setCmpArticles(List<CmpArticle> cmpArticles) {
		this.cmpArticles = cmpArticles;
	}
}