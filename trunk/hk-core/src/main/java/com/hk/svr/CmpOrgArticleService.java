package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpOrgArticle;
import com.hk.bean.CmpOrgArticleContent;

/**
 * 企业相关文章逻辑
 * 
 * @author akwei
 */
public interface CmpOrgArticleService {

	void createCmpOrgArticle(CmpOrgArticle cmpOrgArticle,
			CmpOrgArticleContent cmpOrgArticleContent);

	/**
	 * @param cmpArticle
	 * @param cmpArticleContent 为null时，不更新此对象
	 *            2010-6-17
	 */
	void updateCmpOrgArticle(CmpOrgArticle cmpOrgArticle,
			CmpOrgArticleContent cmpOrgArticleContent);

	void deleteCmpOrgArticle(long companyId, long oid);

	/**
	 * @param companyId
	 * @param cmpNavOid
	 * @param title 文章标题，为空时不参与查询，不为空时可以模糊搜索
	 * @param begin
	 * @param size
	 * @return
	 *         2010-6-28
	 */
	List<CmpOrgArticle> getCmpOrgArticleListByCompanyIdAndNavId(long companyId,
			long navId, String title, int begin, int size);

	int countCmpOrgArticleByCompanyIdAndNavId(long companyId, long navId,
			String title);

	CmpOrgArticle getCmpOrgArticle(long companyId, long oid);

	CmpOrgArticleContent getCmpOrgArticleContent(long companyId, long oid);

	/**
	 * 当删除导航的时候，会把导航下的数据中导航id置为0，并取消推荐
	 * 
	 * @param companyId
	 * @param cmpNavOid
	 *            2010-5-18
	 */
	void setCmpOrgArticleUnknown(long companyId, long navId);

	void updateCmpOrgArticleOrderflg(long companyId, long oid, int orderflg);

	List<CmpOrgArticle> getCmpOrgArticleListByCompanyIdForNext(long companyId,
			long orgId, long navId, long oid, int orderflg, int size);
}