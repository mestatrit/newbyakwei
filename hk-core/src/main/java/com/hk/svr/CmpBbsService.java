package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmpBbs;
import com.hk.bean.CmpBbsContent;
import com.hk.bean.CmpBbsDel;
import com.hk.bean.CmpBbsKind;
import com.hk.bean.CmpBbsReply;
import com.hk.bean.CmpBbsReplyDel;
import com.hk.bean.CmpMyBbs;

/**
 * 企业论坛逻辑操作
 * 
 * @author akwei
 */
public interface CmpBbsService {

	/**
	 * 创建企业分类
	 * 
	 * @param cmpBbsKind
	 * @return false:有重名创建失败,true:创建成功
	 *         2010-5-9
	 */
	boolean createCmpBbsKind(CmpBbsKind cmpBbsKind);

	void createCmpBbs(CmpBbs cmpBbs, CmpBbsContent cmpBbsContent);

	/**
	 * 添加回复，帖子回复数量+1,最后回复人，回复时间更新
	 * 
	 * @param cmpBbsReply
	 * @param cmpBbs
	 *            2010-5-9
	 */
	void createCmpBbsReply(CmpBbsReply cmpBbsReply, CmpBbs cmpBbs);

	/**
	 * @param cmpBbsKind
	 * @return false:有重名更新失败,true:更新成功
	 *         2010-5-9
	 */
	boolean updateCmpBbsKind(CmpBbsKind cmpBbsKind);

	/**
	 * 更新bbs
	 * 
	 * @param cmpBbs
	 * @param cmpBbsContent 可以为空，为空则不更新此数据
	 *            2010-5-9
	 */
	void updateCmpBbs(CmpBbs cmpBbs, CmpBbsContent cmpBbsContent);

	void updateCmpBbsReply(CmpBbsReply cmpBbsReply);

	/**
	 * 删除帖子，以及帖子回复
	 * 
	 * @param bbsId
	 *            2010-5-9
	 */
	void deleteCmpBbs(long bbsId);

	/**
	 * 删除回复，帖子回复数量-1,最后回复人，回复时间更新
	 * 
	 * @param bbsId
	 * @param replyId
	 *            2010-5-9
	 */
	void deleteCmpBbsReply(long bbsId, long replyId);

	CmpBbsReply getCmpBbsReply(long replyId);

	CmpBbs getCmpBbs(long bbsId);

	CmpBbsContent getCmpBbsContent(long bbsId);

	List<CmpBbs> getCmpBbsListByKindId(long kindId, int begin, int size);

	List<CmpBbs> getCmpBbsListByCompanyId(long companyId, int begin, int size);

	List<CmpBbs> getCmpBbsListInId(List<Long> idList);

	Map<Long, CmpBbs> getCmpBbsMapInId(List<Long> idList);

	List<CmpMyBbs> getCmpMyBbsListByCompanyIdAndUserIdAndBbsflg(long companyId,
			long userId, byte bbsflg, int begin, int size);

	List<CmpBbsReply> getCmpBbsReplieListByBbsId(long bbsId, int begin, int size);

	// 管理员炸弹相关
	/**
	 * 炸掉帖子
	 * 
	 * @param bbsId
	 * @param opuserId 管理员id
	 *            2010-5-9
	 */
	void bombCmpBbs(long bbsId, long opuserId);

	/**
	 * 炸掉回复
	 * 
	 * @param replyId
	 * @param opuserId 管理员id
	 *            2010-5-9
	 */
	void bombCmpBbsReply(long replyId, long opuserId);

	void recoverBombCmpBbs(long bbsId);

	void recoverBombCmpBbsReply(long replyId);

	CmpBbsDel getCmpBbsDel(long bbsId);

	CmpBbsReplyDel getCmpBbsReplyDel(long replyId);

	List<CmpBbsKind> getCmpBbsKindListByCompanyId(long companyId);

	CmpBbsKind getCmpBbsKind(long kindId);

	void updateCmpBbsPicpath(long bbsId, long photoId, String path);

	/**
	 * 删除分类,分类下的帖子分类设为0
	 * 
	 * @param kindId
	 *            2010-5-12
	 */
	void deleteCmpBbsKind(long kindId);

	void setCmpBbsCmppink(long bbsId, byte cmppink);

	/**
	 * 清除回复中的图片信息
	 * 
	 * @param bbsId
	 *            2010-5-24
	 */
	void clearCmpBbsPic(long bbsId);

	void clearCmpBbsReplyPic(long replyId);

	List<CmpBbsDel> getCmpBbsDelListByCompanyId(long companyId, int begin,
			int size);

	List<CmpBbsReplyDel> getCmpBbsReplyDelListByCompanyId(long companyId,
			int begin, int size);

	void deleteCmpBbsDel(long bbsId);

	void deleteCmpBbsReplyDel(long bbsId, long replyId);
}