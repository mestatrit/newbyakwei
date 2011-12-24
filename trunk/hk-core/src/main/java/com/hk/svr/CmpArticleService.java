package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmpArticle;
import com.hk.bean.CmpArticleContent;
import com.hk.bean.CmpArticleGroup;
import com.hk.bean.CmpArticleNavPink;

/**
 * 企业相关文章逻辑
 * 
 * @author akwei
 */
public interface CmpArticleService {

	void createCmpArticle(CmpArticle cmpArticle,
			CmpArticleContent cmpArticleContent);

	/**
	 * @param cmpArticle
	 * @param cmpArticleContent 为null时，不更新此对象
	 *            2010-6-17
	 */
	void updateCmpArticle(CmpArticle cmpArticle,
			CmpArticleContent cmpArticleContent);

	void deleteCmpArticle(long companyId, long oid);

	/**
	 * @param companyId
	 * @param cmpNavOid
	 * @param title 文章标题，为空时不参与查询，不为空时可以模糊搜索
	 * @param begin
	 * @param size
	 * @return
	 *         2010-6-28
	 */
	List<CmpArticle> getCmpArticleListByCompanyIdAndCmpNavOid(long companyId,
			long cmpNavOid, String title, int begin, int size);

	List<CmpArticle> getCmpArticleListByCompanyIdAndCmpNavOidNotInId(
			long companyId, long cmpNavOid, List<Long> idList, int begin,
			int size);

	int countCmpArticleListByCompanyIdAndCmpNavOid(long companyId,
			long cmpNavOid, String title);

	/**
	 * @param companyId
	 * @param title 文章标题，为空时不参与查询，不为空时可以模糊搜索
	 * @param begin
	 * @param size
	 * @return
	 *         2010-6-28
	 */
	List<CmpArticle> getCmpArticleListByCompanyId(long companyId, String title,
			int begin, int size);

	/**
	 * 为全文搜索进行的文章查询，按照id倒排
	 * 
	 * @param companyId
	 * @param begin
	 * @param size
	 * @return
	 *         2010-7-6
	 */
	List<CmpArticle> getCmpArticleListByCompanyIdForIdx(long companyId,
			int begin, int size);

	List<CmpArticle> getCmpArticleListByCompanyIdAndCmpNavOidForCmppink(
			long companyId, long cmpNavOid, int begin, int size);

	CmpArticle getCmpArticle(long oid);

	CmpArticleContent getCmpArticleContent(long oid);

	void setCmpArticleCmppink(long oid, byte cmppink);

	/**
	 * 当删除导航的时候，会把导航下的数据中导航id置为0，并取消推荐
	 * 
	 * @param companyId
	 * @param cmpNavOid
	 *            2010-5-18
	 */
	void setCmpArticleUnknown(long companyId, long cmpNavOid);

	void setCmpArticleHomePink(long oid, byte homepink);

	/**
	 * 获得推荐到首页的文章
	 * 
	 * @param companyId
	 * @return
	 *         2010-5-18
	 */
	CmpArticle getCmpArticleForHomepink(long companyId);

	/**
	 * 获取某个特定文章附近的其他文章
	 * 
	 * @param companyId
	 * @param cmpNavOid 导航id
	 * @param oid 特定文章id
	 * @param orderflg 特定文章的顺序号
	 * @param range >0时获取特定文章之后的其他文章，<=0时获取特定文章之前的其他文章
	 * @param size
	 * @return
	 *         2010-5-27
	 */
	List<CmpArticle> getCmpArticleListByCompanyIdForRange(long companyId,
			long cmpNavOid, long oid, int orderflg, int range, int size);

	void updateCmpArticleOrderflg(long oid, int orderflg);

	/**
	 * 获取推荐的某产品分类的文章
	 * 
	 * @param companyId
	 * @param sortId
	 * @param begin
	 * @param size
	 * @return
	 *         2010-6-18
	 */
	List<CmpArticle> getCmpArticleListByCompanyIdAndSortIdForCmppink(
			long companyId, int sortId, int begin, int size);

	/**
	 * 企业推荐的文章，除去指定的id
	 * 
	 * @param companyId
	 * @param idList
	 * @param begin
	 * @param size
	 * @return
	 *         2010-6-21
	 */
	List<CmpArticle> getCmpArticleListByCompanyIdAndNotInIdForCmppink(
			long companyId, List<Long> idList, int begin, int size);

	/**
	 * 创建文章分组，如果在某个栏目下重名，创建失败
	 * 
	 * @param cmpArticleGroup
	 * @return true:创建成功,false:重名，创建失败
	 *         2010-6-21
	 */
	boolean createCmpArticleGroup(CmpArticleGroup cmpArticleGroup);

	/**
	 * 更新文章组，如果在某个栏目下重名，更新失败
	 * 
	 * @param cmpArticleGroup
	 * @return true:更新成功,false:重名，更新失败
	 *         2010-6-21
	 */
	boolean updateCmpArticleGroup(CmpArticleGroup cmpArticleGroup);

	/**
	 * 删除文章组，相关文章groupid值为0
	 * 
	 * @param groupId
	 *            2010-6-21
	 */
	void deleteCmpArticleGroup(long groupId);

	/**
	 * 获得某个栏目的分组集合
	 * 
	 * @param companyId
	 * @param cmpNavOid
	 * @return
	 *         2010-7-2
	 */
	List<CmpArticleGroup> getCmpArticleGroupListByCompanyIdAndCmpNavOid(
			long companyId, long cmpNavOid);

	Map<Long, CmpArticleGroup> getCmpArticleGroupMapByCompanyIdInId(
			long companyId, List<Long> idList);

	/**
	 * 获得某个企业栏目分组的文章集合
	 * 
	 * @param companyId 企业id
	 * @param cmpNavOid 栏目id
	 * @param groupId 分组id
	 * @param begin
	 * @param size
	 * @return
	 *         2010-7-2
	 */
	List<CmpArticle> getCmpArticleListByCompanyIdAndCmpNavOidAndGroupId(
			long companyId, long cmpNavOid, long groupId, int begin, int size);

	List<CmpArticle> getCmpArticleListByCompanyIdAndGroupId(long companyId,
			long groupId, int begin, int size);

	/**
	 * 获得分组对象
	 * 
	 * @param groupId
	 * @return
	 *         2010-7-2
	 */
	CmpArticleGroup getCmpArticleGroup(long groupId);

	/**
	 * 获得某企业中指定id的文章列表
	 * 
	 * @param companyId
	 * @param idList
	 * @return
	 *         2010-7-2
	 */
	Map<Long, CmpArticle> getCmpArticleMapByCompanyIdAndInId(long companyId,
			List<Long> idList);

	Map<Long, CmpArticleContent> getCmpArticleContentMapInId(List<Long> idList);

	void createCmpArticleNavPink(CmpArticleNavPink cmpArticleNavPink);

	void updateCmpArticleNavPink(CmpArticleNavPink cmpArticleNavPink);

	void deleteCmpArticleNavPink(long companyId, long oid);

	void deleteCmpArticleNavPinkByCompanyIdAndNavId(long companyId, long navId);

	CmpArticleNavPink getCmpArticleNavPink(long companyId, long oid);

	CmpArticleNavPink getCmpArticleNavPinkByCompanyIdAndArticleId(
			long companyId, long articleId);

	List<CmpArticleNavPink> getCmpArticleNavPinkByCompanyIdAndNavId(
			long companyId, long navId);
}