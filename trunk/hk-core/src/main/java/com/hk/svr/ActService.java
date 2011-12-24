package com.hk.svr;

import java.util.List;
import java.util.Map;
import com.hk.bean.Act;
import com.hk.bean.ActUser;
import com.hk.svr.act.exception.DuplicateActNameException;
import com.hk.svr.user.exception.NoAvailableActSysNumException;
import com.hk.svr.user.exception.NoSmsPortException;

public interface ActService {
	/**
	 * 自动分配一个通道号码与信息台共用
	 * 
	 * @param act
	 * @throws NoSmsPortException
	 */
	void createAct(Act act) throws NoSmsPortException,
			DuplicateActNameException, NoAvailableActSysNumException;

	void updateAct(Act act) throws DuplicateActNameException;

	Act getAct(long actId);

	void createActUser(long actId, long userId, byte checkflg);

	void joinAct(long actId, long userId);

	ActUser getActUser(long actId, long userId);

	void updateActUser(ActUser actUser);

	void deleteActUser(long actId, long userId);

	List<ActUser> getActUserList(long actId, int begin, int size);

	List<ActUser> getActUserListForNoUser(long actId, List<Long> userIdList,
			int begin, int size);

	List<ActUser> getActUserList(long actId);

	/**
	 * 用户参加的活动
	 * 
	 * @param userId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Act> getActListByJoinUserId(long userId, int begin, int size);

	List<Long> getActIdListByJoinUserId(long userId, int begin, int size);

	Map<Long, Act> getActMapInId(List<Long> idList);
}