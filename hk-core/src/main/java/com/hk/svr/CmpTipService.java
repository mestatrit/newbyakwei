package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmpTip;
import com.hk.bean.CmpTipDel;
import com.hk.bean.UserCmpTip;

/**
 * 创建用户推荐内容，此推荐内容其他用户可以添加为已完成，事件日程
 * 
 * @author akwei
 */
public interface CmpTipService {

	/**
	 * 创建tip
	 * 
	 * @param cmpTip
	 */
	void createCmpTip(CmpTip cmpTip);

	/**
	 * 修改tip数据，并修改所有引用过此tip的数据
	 * 
	 * @param cmpTip
	 */
	void updateCmpTip(CmpTip cmpTip);

	/**
	 * 删除tip，会把其他用户添加的done todo一并删除
	 * 
	 * @param tipId
	 */
	CmpTip deleteCmpTip(long tipId);

	/**
	 * 创建用户tip日志，如果已经存在相同tipid的数据，则更新此数据相应值
	 * 
	 * @param userCmpTip
	 * @see {@link UserCmpTip}
	 */
	void createUserCmpTip(UserCmpTip userCmpTip);

	void updateUserCmpTip(UserCmpTip userCmpTip);

	/**
	 * @param idList
	 *            tipId集合
	 * @return
	 */
	List<CmpTip> getCmpTipListInId(List<Long> idList);

	/**
	 * @param idList
	 *            tipId集合
	 * @return
	 */
	Map<Long, CmpTip> getCmpTipMapInId(List<Long> idList);

	/**
	 * 获取已经完成的事件集合
	 * 
	 * @param userId
	 * @param pcityId
	 *            城市id
	 * @param begin
	 * @param size
	 * @return
	 */
	List<UserCmpTip> getUserCmpTipDoneListByUserId(long userId, int begin,
			int size);

	List<UserCmpTip> getUserCmpTipListByTipId(long tipId);

	List<UserCmpTip> getUserCmpTipListByCompanyId(long companyId);

	/**
	 * 获取事件日程集合
	 * 
	 * @param userId
	 * @param pcityId
	 *            城市id
	 * @param begin
	 * @param size
	 * @return
	 */
	List<UserCmpTip> getUserCmpTipToDoListByUserId(long userId, int begin,
			int size);

	List<UserCmpTip> getUserCmpTipListByUserIdInTipId(long userId,
			List<Long> idList);

	List<UserCmpTip> getUserCmpTipDoneListByPcityId(int pcityId,
			long excludeUserId, int begin, int size);

	List<UserCmpTip> getUserCmpTipToDoListByPcityId(int pcityId,
			long excludeUserId, int begin, int size);

	Map<Long, UserCmpTip> getUserCmpTipMapByUserIdInTipId(long userId,
			List<Long> idList);

	/**
	 * 获得某个足迹tip
	 * 
	 * @param companyId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpTip> getCmpTipListByCompanyId(long companyId, byte doneflg,
			int begin, int size);

	/**
	 * 获得出去某个用户之外的所有done tip
	 * 
	 * @param companyId
	 * @param excludeUserId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpTip> getCmpTipListByCompanyIdExcluedUserId(long companyId,
			long excludeUserId, int begin, int size);

	/**
	 * 只获取某个用户的done tip
	 * 
	 * @param companyId
	 * @param userId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpTip> getCmpTipListByCompanyIdAndUserId(long companyId, long userId,
			int begin, int size);

	/**
	 * 获得所有tip
	 * 
	 * @param companyId
	 * @return
	 */
	List<CmpTip> getAllCmpTipListByCompanyId(long companyId);

	CmpTip getCmpTip(long tipId);

	UserCmpTip getUserCmpTipByUserIdAndTipId(long userId, long tipId);

	UserCmpTip deleteUserCmpTipByUserIdAndTipId(long userId, long tipId);

	int countUserCmpTipDone(long userId);

	int countUserCmpTipDonefromToDo(long userId);

	void updatepcityiddata();

	/**
	 * 更改tip中足迹名称
	 * 
	 * @param userCmpTip
	 */
	void updateUserCmpTipCompanyName(UserCmpTip userCmpTip);

	/**
	 * 炸掉对应的tip，并从tip表中删除
	 * 
	 * @param cmpTipDel
	 *            2010-4-8
	 */
	void bombCmpTip(CmpTipDel cmpTipDel);

	/**
	 * @param opuserId
	 *            为0 可忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 *         2010-4-8
	 */
	List<CmpTipDel> getCmpTipDelList(long opuserId, int begin, int size);

	CmpTip recoverCmpTip(long tipId);
}