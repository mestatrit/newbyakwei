package com.hk.svr;

import java.util.Date;
import java.util.List;

import com.hk.bean.CmpActorReport;
import com.hk.bean.CmpReserve;

/**
 * 企业预约服务，以及对人员的预约服务
 * 
 * @author akwei
 */
public interface CmpReserveService {

	void createCmpReserve(CmpReserve cmpReserve);

	void updateCmpReserve(CmpReserve cmpReserve);

	void deleteCmpReserve(long reserveId);

	CmpReserve getCmpReserve(long reserveId);

	/**
	 * @param companyId
	 * @param actorId 店员id.<=0时不参与查询
	 * @param reserveStatus 预约订单条件,<=0时不参与查询
	 * @param username 预约人姓名。为空时不参与查询
	 * @param mobile 预约人手机号。为空时不参与查询
	 * @param beginTime 预约开始时间
	 * @param endTime 预约结束时间
	 * @param begin
	 * @param size
	 * @return
	 *         2010-8-20
	 */
	List<CmpReserve> getCmpReserveListByCompanyIdEx(long companyId,
			long actorId, byte reserveStatus, String username, String mobile,
			Date beginTime, Date endTime, int begin, int size);

	/**
	 * 查询某企业中某个店员的预约订单列表
	 * 
	 * @param companyId
	 * @param actorId
	 * @param begin
	 * @param size
	 * @return
	 *         2010-7-26
	 */
	List<CmpReserve> getCmpReserveListByCompanyIdAndActorId(long companyId,
			long actorId, int begin, int size);

	/**
	 * 查询用户在某企业中的预约订单列表
	 * 
	 * @param companyId
	 * @param userId
	 * @param begin
	 * @param size
	 * @return
	 *         2010-7-26
	 */
	List<CmpReserve> getCmpReserveListByCompanyIdAndUserId(long companyId,
			long userId, int begin, int size);

	List<CmpReserve> getCmpReserveListByCompanyIdAndUserIdAndReserveStatus(
			long companyId, long userId, byte reserveStatus, int begin, int size);

	/**
	 * 根据时间统计成功的预约数量
	 * 
	 * @param companyId
	 * @param userId
	 * @param beginReserveTime
	 * @param endReserveTime
	 * @return
	 *         2010-8-17
	 */
	int countCmpReserveByCompanyIdAndUserIdAndReserveDateForReserveOk(
			long companyId, long userId, Date beginReserveTime,
			Date endReserveTime);

	/**
	 * 根据用户与预约状态获取预约数据，状态为有效预约时，获取的是未处理的有效预约（未过期）
	 * 
	 * @param userId
	 * @param reserveStatus
	 * @param begin
	 * @param size
	 * @return
	 *         2010-8-19
	 */
	List<CmpReserve> getCmpReserveListByUserIdAndReserveStatus(long userId,
			byte reserveStatus, int begin, int size);

	/**
	 * 获取作废的预约
	 * 
	 * @param userId
	 * @param begin
	 * @param size
	 * @return
	 *         2010-8-19
	 */
	List<CmpReserve> getCmpReserveListByUserIdForUnuse(long userId, int begin,
			int size);

	/**
	 * 获取用户在某足迹已经创建的预约的数量(未过期，未处理的预约)
	 * 
	 * @param companyId
	 * @param userId
	 * @return
	 *         2010-8-18
	 */
	int countCmpReserveByCompanyIdAndUserIdForReserved(long companyId,
			long userId);

	int countCmpReserveByCompanyIdAndReserveStatus(long companyId,
			byte reserveStatus);

	List<CmpActorReport> getCmpActorReportListByCompanyId(long companyId,
			Date beginTime, Date endTime);

	int countCmpReserveByActorIdAndReserveStatus(long actorId,
			byte reserveStatus);
}