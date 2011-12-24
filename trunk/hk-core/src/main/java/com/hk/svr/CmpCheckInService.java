package com.hk.svr;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hk.bean.CheckInResult;
import com.hk.bean.CmpCheckInUser;
import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.Company;
import com.hk.bean.Mayor;
import com.hk.bean.UserCmpPoint;
import com.hk.bean.UserDateCheckInCmp;
import com.hk.bean.UserLastCheckIn;

public interface CmpCheckInService {

	/**
	 * 到足迹报到，创建到足迹报到的人与足迹报到日志<br/>
	 * 报到时，每天每个地方只有2次的有效报到，其余为无效报到(出了获得无效徽章外，其他徽章都根据有效统计)
	 * 
	 * @param cmpCheckInUserLog
	 * @param forceInvalid
	 *            是否是强制无效报到
	 * @return @see CheckInResult
	 */
	CheckInResult checkIn(CmpCheckInUserLog cmpCheckInUserLog,
			boolean forceInvalid, Company company);

	/**
	 * 统计足迹签到过的人数
	 * 
	 * @param companyId
	 * @return
	 */
	int countCmpCheckInUserByCompanyId(long companyId);

	/**
	 * 统计个人在足迹的签到次数
	 * 
	 * @param companyId
	 * @param userId
	 * @return
	 */
	int countCmpCheckInUserLogByCompanyIdAndUserId(long companyId, long userId);

	List<CmpCheckInUser> getCmpCheckInUserListByCompanyId(long companyId,
			int begin, int size);

	/**
	 * 用户在某足迹某天的报到次数
	 * 
	 * @param companyId
	 * @param userId
	 * @param date
	 * @return
	 */
	int countEffectCmpCheckInUserLogByCompanyIdAndUserId(long companyId,
			long userId, Date date);

	int countEffectCmpCheckInUserLogByCompanyIdAndUserId(long companyId,
			long userId, Date begin, Date end);

	int countEffectCmpCheckInUserLogByCompanyIdAndUserId(long companyId,
			long userId);

	int countEffectNightlyCmpCheckInUserLogByCompanyAndUserId(long companyId,
			long userId, Date date);

	int countEffectCmpCheckInUserLogByUserId(long userId);

	/**
	 * 某个日期，用户在某个足夜晚迹报到的次数
	 * 
	 * @param companyId
	 * @param userId
	 * @param date
	 * @return
	 */
	int countCmpCheckInUserLogByCompanyIdAndUserIdForOneNight(long companyId,
			long userId, Date date);

	/**
	 * 夜晚报到数统计
	 * 
	 * @param userId
	 * @param begin
	 * @param end
	 * @return
	 */
	int countCmpCheckInUserLogByUserIdForNight(long userId);

	List<CmpCheckInUserLog> getCmpCheckInUserLogListByUserId(long userId,
			int begin, int size);

	List<CmpCheckInUserLog> getCmpCheckInUserLogListInId(List<Long> idList);

	Map<Long, CmpCheckInUserLog> getCmpCheckInUserLogMapInId(List<Long> idList);

	int countCmpCheckInUserByUserId(long userId);

	/**
	 * 统计某个夜晚用户到过的地方
	 * 
	 * @param userId
	 * @param date
	 * @return
	 */
	int countNigghtlyCmpCheckInUserByUserId(long userId, Date date);

	int countCmpCheckInUserByCompanyIdAndSex(long companyId, byte sex,
			Date begin, Date end);

	CmpCheckInUser getCmpCheckInUser(long companyId, long userId);

	/**
	 * 某短时间内某人有效报到数量
	 * 
	 * @param userId
	 * @param date
	 * @return
	 */
	int countEffectCmpCheckInUserLogByUserId(long userId, Date begin, Date end);

	/**
	 * 某个时间段内用户有效报到的不同地方的数量
	 * 
	 * @param userId
	 * @param begin
	 * @param end
	 * @return
	 */
	int countEffectCmpCheckInUserLogByUserIdForDiffCompanyId(long userId,
			Date begin, Date end);

	int countEffectCmpCheckInUserLogByParentIdAndUserId(int parentId,
			long userId);

	int countEffectCmpCheckInUserLogByKindIdAndUserId(int kindId, long userId);

	/**
	 * 统计某个分类中用户报到的足迹的数量
	 * 
	 * @param kindId
	 * @param userId
	 * @return
	 */
	int countEffectCmpCheckInUserLogByKindIdAndUserIdDiffCompanyId(int kindId,
			long userId);

	int countEffectCmpCheckInUserLogByGroupIdAndUserId(long groupId, long userId);

	int countMayorByUserId(long userId);

	List<Mayor> getMayorListByUserId(long userId, int begin, int size);

	/**
	 * 根据地区或者全部，查看有效报到的数据
	 * 
	 * @param pcityId
	 *            为0则忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpCheckInUserLog> getEffectCmpCheckInUserLogListByPcityId(
			int pcityId, Date min, Date max, int begin, int size);

	/**
	 * 获得报到记录
	 * 
	 * @param pcityId 城市id, 为0则忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 *         2010-4-23
	 */
	List<CmpCheckInUserLog> getEffectCmpCheckInUserLogListByPcityId(
			int pcityId, int begin, int size);

	int countEffectCmpCheckinUserLogBycompanyId(long companyId);

	/**
	 * 删除地主关系时，需要更新足迹中mayoruserid
	 * 
	 * @param mayorId
	 */
	void deleteMayor(long mayorId);

	Mayor getMayor(long mayorId);

	/**
	 * 最近大家都在哪
	 * 
	 * @param idList
	 * @param begin
	 * @param size
	 * @return
	 *         2010-4-10
	 */
	List<UserLastCheckIn> getUserLastCheckInListInIdList(List<Long> idList);

	UserLastCheckIn getUserLastCheckIn(long userId);

	/**
	 * 初始化数据使用，业务中暂时不使用
	 * 
	 * @return
	 *         2010-4-10
	 */
	List<CmpCheckInUser> getCheckInUserList();

	void updateUserLastCheckIn(UserLastCheckIn userLastCheckIn);

	List<CmpCheckInUser> getCmpCheckInUserListByUserId(long userId, int begin,
			int size);

	CmpCheckInUserLog getLastCmpCheckInUserLogByUserId(long userId);

	/**
	 * 按照id倒排
	 * 
	 * @param pcityId 为0可忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 *         2010-4-12
	 */
	List<Mayor> getMayorListByPcityId(int pcityId, int begin, int size);

	List<Mayor> getMayorListAll();

	void updateMayor(Mayor mayor);

	List<UserLastCheckIn> getUserLastCheckInListInId(List<Long> idList);

	Map<Long, UserLastCheckIn> getUserLastCheckInMapInId(List<Long> idList);

	UserCmpPoint getUserCmpPointByUserIdAndCompanyId(long userId, long companyId);

	void updateUserCmpPoints(UserCmpPoint userCmpPoint);

	void addUserCmpPoints(long userId, long companyId, int add);

	/**
	 * 查看用户是否是地主，在不是地主的情况下，如果符合地主条件就成为地主
	 * 
	 * @param userId
	 * @param companyId
	 * @param company 足迹对象
	 * @param forceMayor 强制成为地主
	 * @param ip ip地址
	 * @return -1:不是地主0:仍然是地主,1:新地主
	 *         2010-4-14
	 */
	int checkMayor(long userId, Company company, String ip, boolean forceMayor);

	/**
	 * 是否能报到
	 * 
	 * @param userId
	 * @param companyId
	 * @return true:可以报到,false:2此报到时间间隔太短，不能报到
	 *         2010-4-14
	 */
	boolean canCheckIn(long userId, long companyId);

	/**
	 * 创建或更新 对象
	 * 
	 * @param userDateCheckInCmp
	 *            2010-4-25
	 */
	void saveUserDateCheckInCmp(UserDateCheckInCmp userDateCheckInCmp);

	UserDateCheckInCmp getUserDateCheckInCmp(long userId);
}