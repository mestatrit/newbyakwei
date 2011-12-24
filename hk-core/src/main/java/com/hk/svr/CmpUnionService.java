package com.hk.svr;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.hk.bean.CmpUnion;
import com.hk.bean.CmpUnionBoard;
import com.hk.bean.CmpUnionCmdKind;
import com.hk.bean.CmpUnionKind;
import com.hk.bean.CmpUnionLink;
import com.hk.bean.CmpUnionOpUser;

public interface CmpUnionService {
	/**
	 * 创建集团数据,domain不能为空
	 * 
	 * @param cmpUnion
	 * @return 0:创建成功<br/>
	 *         Err.CMPUNION_DOMAIN_DUPLICATE :domain已经存在,创建失败<br/>
	 *         Err.CMPUNION_WEBNAME_DUPLICATE:webName已经存在,创建失败
	 */
	int createCmpUnion(CmpUnion cmpUnion);

	/**
	 * 更新集团数据,domain不能为空
	 * 
	 * @param cmpUnion
	 * @return 0:更新成功,1:webName已经存在,更新失败,2:domain已经存在,更新失败
	 */
	int updateCmpUnion(CmpUnion cmpUnion);

	CmpUnion getCmpUnion(long uid);

	CmpUnion getCmpUnionByDomain(String domain);

	CmpUnion getCmpUnionByWebName(String webName);

	void deleteCmpUnion(long uid);

	/**
	 * 创建分类，同时创建一个其它分类在同级，如果其它这个分类已经存在，就不创建其它分类。如果有parentId，则更新parent有子分类
	 * 
	 * @param cmpUnionKind
	 * @return true:创建成功,false:在uid相同的数据中存在重名创建失败
	 */
	boolean createCmpUnionKind(CmpUnionKind cmpUnionKind);

	/**
	 * @param cmpUnionKind
	 * @return true:更新成功,false:在uid相同的数据中存在重名更新失败
	 */
	boolean updateCmpUnionKind(CmpUnionKind cmpUnionKind);

	/**
	 * 如果分类有子分类，不能删除此分类，必须先删除子分类<br/>
	 * 删除后查看父分类中是否还有子分类，如果没有子分类，把父分类设置为没有子分类的标志<br/>
	 * 更新相关产品数据，把集团分类置为0
	 * 
	 * @param kindId
	 * @return false:有子分类，删除失败.true，删除成功
	 */
	boolean deleteCmpUnionKind(long kindId);

	/**
	 * @param uid
	 * @param begin
	 * @param size -1时忽略size,获取全部符合条件数据
	 * @return
	 */
	List<CmpUnionKind> getCmpUnionKindListByUid(long uid, int begin, int size);

	int countCmpUnionKindByUid(long uid, long parentId);

	List<CmpUnionKind> getCmpUnionKindListByUid(long uid, long parentId,
			int begin, int size);

	CmpUnionKind getCmpUnionKind(long kindId);

	void createCmpUnionOpUser(CmpUnionOpUser cmpUnionOpUser);

	void deleteCmpUnionOpUser(long uid, long userId);

	List<CmpUnionOpUser> getCmpUnionOpUserListByUid(long uid);

	/**
	 * @param name 模糊查询条件 为空时忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpUnion> getCmpUnionList(String name, int begin, int size);

	boolean isCmpUnionOpUser(long uid, long userId);

	void updateCmpUnionLogo(long uid, File file) throws Exception;

	/**
	 * 修改联盟首页显示顺序
	 * 
	 * @param uid
	 * @param data
	 * @throws Exception
	 */
	void updateCmpUnionData(long uid, String data);

	int countCmpUnionLikeName(int pcityId, String name);

	List<CmpUnion> getCmpUnionListLikeName(int pcityId, String name, int begin,
			int size);

	Map<Long, CmpUnionKind> getCmpUnionKindMapInId(List<Long> idList);

	void createCmpUnionBoard(CmpUnionBoard cmpUnionBoard);

	void updateCmpUnionBoard(CmpUnionBoard cmpUnionBoard);

	void deleteCmpUnionBoard(long boardId);

	int countCmpUnionBoardByUid(long uid);

	List<CmpUnionBoard> getCmpUnionBoardListByUid(long uid, int begin, int size);

	CmpUnionBoard getCmpUnionBoard(long boardId);

	void createCmpUnionLink(CmpUnionLink cmpUnionLink);

	void updateCmpUnionLink(CmpUnionLink cmpUnionLink);

	void deleteCmpUnionLink(long linkId);

	int countCmpUnionLinkByUid(long uid);

	List<CmpUnionLink> getCmpUnionLinkListByUid(long uid, int begin, int size);

	CmpUnionLink getCmpUnionLink(long linkId);

	/**
	 * 如果是顶级分类，不能进行推荐，只能推荐子分类
	 * 
	 * @param cmpUnionCmdKind
	 * @return true:推荐成功 ,false:推荐失败(分类不存在，或者是顶级分类，或者是已经创建过)
	 */
	boolean createCmpUnionCmdKind(CmpUnionCmdKind cmpUnionCmdKind);

	void deleteCmpUnionCmdKind(long uid, long kindId);

	/**
	 * @param uid
	 * @param begin
	 * @param size -1时忽略size
	 * @return
	 */
	List<CmpUnionCmdKind> getCmpUnionCmdKindListByUid(long uid, int begin,
			int size);

	/**
	 * 显示最最小子分类的数据，除掉其他
	 * 
	 * @param uid
	 * @return
	 */
	List<CmpUnionKind> getLastLevelCmpUnionKindListByUid(long uid);

	List<CmpUnionKind> getCmpUnionKindListInId(long uid, List<Long> idList);

	void updateCmpUnionStatus(long uid, byte status);

	void updateCmpUnionPcityIdData();
}