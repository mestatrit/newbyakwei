package com.hk.svr;

import java.util.List;
import com.hk.bean.ActSysNum;
import com.hk.bean.ChgCardAct;
import com.hk.bean.ChgCardActUser;
import com.hk.svr.user.exception.NoAvailableActSysNumException;

public interface ChgCardActService {
	/**
	 * 创建活动，分配随机暗号,号码用完，活动创建失败，抛出没有可用号码异常
	 * 
	 * @param chgCardAct
	 * @throws NoAvailableActSysNumException
	 */
	void createChgCardAct(ChgCardAct chgCardAct)
			throws NoAvailableActSysNumException;

	ChgCardAct getChgCardAct(long actId);

	void updateChgCardAct(ChgCardAct chgCardAct);

	/**
	 * 加入换名片活动，如果是公开交换名片就添加到自己的名片簿中
	 * 
	 * @param actId
	 * @param userId
	 */
	void createChgCardActUser(long actId, long userId);

	void deleteChgCardActUser(long actId, long userId);

	/**
	 * 获得过期的暗号列表
	 * 
	 * @param begin
	 * @param size
	 * @return
	 */
	List<ActSysNum> getInUseActSysNumList(int begin, int size);

	List<ChgCardAct> getChgCardActListByJoinUserId(long userId, int begin,
			int size);

	/**
	 * @param key可以是name or nickName 可以为空,查询所有
	 * @param actId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<ChgCardActUser> getChgCardActUserList(String key, long actId,
			int begin, int size);

	ChgCardActUser getChgCardActUser(long actId, long userId);

	List<ChgCardActUser> getChgCardActUserList(long actId);
}