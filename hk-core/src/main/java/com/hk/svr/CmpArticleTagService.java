package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmpArticleTag;
import com.hk.bean.CmpArticleTagRef;

/**
 * 有关文章标签的所有逻辑操作
 * 
 * @author akwei
 */
public interface CmpArticleTagService {

	/**
	 * 创建文章标签,如果某企业已经有这个标签，就不创建
	 * 
	 * @param cmpArticleTag
	 * @return true:创建成功,false:已经存在，创建失败
	 *         2010-7-2
	 */
	boolean createCmpArticleTag(CmpArticleTag cmpArticleTag);

	void updateCmpArticleTagPinkflg(long companyId, long tagId, byte pinkflg);

	/**
	 * 创建文章标签关系表.如果companyId articleId tagId相等的数据存在的情况下，不再次创建
	 * 
	 * @param cmpArticleTagRef
	 * @return true:创建成功,false:已经存在，创建失败
	 *         2010-7-2
	 */
	boolean createCmpArticleTagRef(CmpArticleTagRef cmpArticleTagRef);

	CmpArticleTag getCmpArticleTag(long companyId, long tagId);

	/**
	 * 删除标签，并删除标签关联表中相关数据
	 * 
	 * @param tagId
	 *            2010-7-2
	 */
	void deleteCmpArticleTag(long companyId, long tagId);

	void deleteCmpArticleTagRefByCompanyIdAndOid(long companyId, long oid);

	void deleteCmpArticleTagRefByCompanyIdAndArticleId(long companyId,
			long articleId);

	/**
	 * 获取与企业标签关联的数据集合
	 * 
	 * @param companyId
	 * @param tagId
	 * @param begin
	 * @param size
	 * @return
	 *         2010-7-2
	 */
	List<CmpArticleTagRef> getCmpArticleTagRefListByCompanyIdAndTagId(
			long companyId, long tagId, int begin, int size);

	int countCmpArticleTagRefByCompanyIdAndTagId(long companyId, long tagId);

	List<CmpArticleTagRef> getCmpArticleTagRefListByCompanyIdAndArticleId(
			long companyId, long articleId, int begin, int size);

	Map<Long, CmpArticleTag> getCmpArticleTagMapByCompanyIdInId(long companyId,
			List<Long> idList);

	CmpArticleTag getCmpArticleTagByCompanyIdAndName(long companyId, String name);

	/**
	 * 查找企业的标签集合
	 * 
	 * @param companyId
	 * @param name 可为空，不为空时，模糊查询标签
	 * @param begin
	 * @param size
	 * @return
	 *         2010-7-4
	 */
	List<CmpArticleTag> getCmpArticleTagListByCompanyId(long companyId,
			String name, int begin, int size);

	List<CmpArticleTag> getCmpArticleTagListByCompanyIdForPink(long companyId,
			int begin, int size);
}