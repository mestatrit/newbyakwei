package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmpAdBlock;
import com.hk.bean.CmpArticleBlock;
import com.hk.bean.CmpPageBlock;
import com.hk.bean.CmpPageMod;

public interface CmpModService {

	void createCmpPageBlock(CmpPageBlock cmpPageBlock);

	void updateCmpPageBlock(CmpPageBlock cmpPageBlock);

	void deleteCmpPageBlock(long blockId);

	List<CmpPageBlock> getCmpPageBlockListByCompanyIdAndPageflg(long companyId,
			byte pageflg);

	List<CmpPageBlock> getCmpPageBlockListByCompanyIdAndPageModId(
			long companyId, int pageModId);

	CmpPageBlock getCmpPageBlock(long blockId);

	/**
	 * 当在同一企业同一页面级别时，不重复出现
	 * 
	 * @param cmpArticleBlock
	 * @return true:创建成功,false:已经存在同一页面级别，不能创建
	 *         2010-6-25
	 */
	boolean createCmpArticlePageBlock(CmpArticleBlock cmpArticleBlock,
			CmpPageMod cmpPageMod);

	boolean updateCmpArticlePageBlock(CmpArticleBlock cmpArticleBlock);

	void deleteCmpArticleBlock(long oid);

	void deleteCmpArticleBlockByCompanyIdAndArticleId(long companyId,
			long articleId);

	void delteCmpAdBlockByCompanyIdAndAdid(long companyId, long adid);

	/**
	 * 当在同一企业同一页面级别时，不重复出现
	 * 
	 * @param cmpAdBlock
	 * @return true:创建成功,false:已经存在同一页面级别，不能创建
	 *         2010-6-25
	 */
	boolean createCmpAdBlock(CmpAdBlock cmpAdBlock);

	void deleteCmpAdBlock(long oid);

	void deleteCmpAdBlockByCompanyIdAndAdid(long companyId, long adid);

	void updateCmpPageBlockOrderflg(long blockId, int orderflg);

	List<CmpAdBlock> getCmpAdBlockListByCompanyIdAndPageflg(long companyId,
			byte pageflg);

	List<CmpAdBlock> getCmpAdBlockListByCompanyIdAndBlockId(long companyId,
			long blockId, int begin, int size);

	List<CmpArticleBlock> getCmpArticleBlockListByCompanyIdAndPageflg(
			long companyId, byte pageflg);

	List<CmpArticleBlock> getCmpArticleBlockListByCompanyIdAndBlockId(
			long companyId, long blockId, boolean sortDesc, int begin, int size);

	CmpAdBlock getCmpAdBlock(long companyId, long adid, byte pageflg);

	CmpAdBlock getCmpAdBlock(long oid);

	CmpAdBlock getCmpAdBlock(long companyId, long adid, long blockId);

	CmpArticleBlock getCmpArticleBlock(long oid);

	CmpArticleBlock getCmpArticleBlock(long companyId, long articleId,
			long blockId);

	CmpArticleBlock getCmpArticleBlock(long companyId, long articleId,
			byte pageflg);

	Map<Long, CmpPageBlock> getCmpPageBlockInMap(List<Long> idList);

	int countCmpAdBlockByCompanyIdAndBlockId(long companyId, long blockId);

	int countCmpArticleBlockByCompanyIdAndBlockId(long companyId, long blockId);

	List<CmpArticleBlock> getCmpArticleBlockList();
}
