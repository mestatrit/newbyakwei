package com.hk.svr;

import java.util.Date;
import java.util.List;

import com.hk.bean.CmpActorSpTime;

public interface CmpActorSpTimeService {

	void createCmpActorSpTime(CmpActorSpTime cmpActorSpTime);

	void updateCmpActorSpTime(CmpActorSpTime cmpActorSpTime);

	void deleteCmpActorSpTime(long companyId, long oid);

	void deleteCmpActorSpTimeByCompanyIdAndActorId(long companyId, long actorId);

	CmpActorSpTime getCmpActorSpTime(long companyId, long oid);

	/**
	 * @param companyId
	 * @param actorId 人员id，<=0时不参与查询
	 * @param spflg
	 * @param beginTime 登记开始时间，为空时不参与查询
	 * @param endTime 登记结束时间，为空时不参与查询
	 * @param begin
	 * @param size
	 * @return
	 *         2010-7-27
	 */
	List<CmpActorSpTime> getCmpActorSpTimeListByCompanyIdEx(long companyId,
			long actorId, byte spflg, Date beginTime, Date endTime, int begin,
			int size);
}