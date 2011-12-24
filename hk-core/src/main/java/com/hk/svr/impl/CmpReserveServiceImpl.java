package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpActorReport;
import com.hk.bean.CmpReserve;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpReserveService;

public class CmpReserveServiceImpl implements CmpReserveService {

	@Autowired
	private QueryManager manager;

	public void createCmpReserve(CmpReserve cmpReserve) {
		if (cmpReserve.getCreateTime() == null) {
			cmpReserve.setCreateTime(new Date());
		}
		Query query = this.manager.createQuery();
		long reserveId = query.insertObject(cmpReserve).longValue();
		cmpReserve.setReserveId(reserveId);
	}

	public void deleteCmpReserve(long reserveId) {
		Query query = this.manager.createQuery();
		query.delete(CmpReserve.class, "reserveid=?",
				new Object[] { reserveId });
	}

	public CmpReserve getCmpReserve(long reserveId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpReserve.class, "reserveid=?",
				new Object[] { reserveId });
	}

	public List<CmpReserve> getCmpReserveListByCompanyIdAndUserId(
			long companyId, long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpReserve.class, "companyid=? and userid=?",
				new Object[] { companyId, userId }, "reserveid asc", begin,
				size);
	}

	public List<CmpReserve> getCmpReserveListByCompanyIdEx(long companyId,
			long actorId, byte reserveStatus, String username, String mobile,
			Date beginTime, Date endTime, int begin, int size) {
		StringBuilder sb = new StringBuilder("companyid=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		if (beginTime != null) {
			sb.append(" and reservetime>=?");
			olist.add(beginTime);
		}
		if (endTime != null) {
			sb.append(" and endtime<=?");
			olist.add(endTime);
		}
		if (actorId > 0) {
			sb.append(" and actorid=?");
			olist.add(actorId);
		}
		if (reserveStatus > 0) {
			sb.append(" and  reservestatus=?");
			olist.add(reserveStatus);
		}
		if (!DataUtil.isEmpty(username)) {
			sb.append(" and username like ?");
			olist.add("%" + username + "%");
		}
		if (!DataUtil.isEmpty(mobile)) {
			sb.append(" and mobile=?");
			olist.add(mobile);
		}
		Query query = this.manager.createQuery();
		return query.listExParamList(CmpReserve.class, sb.toString(), olist,
				"reservetime asc", begin, size);
	}

	public List<CmpReserve> getCmpReserveListByCompanyId(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpReserve.class, "companyid=?",
				new Object[] { companyId }, "reserveid asc", begin, size);
	}

	public List<CmpReserve> getCmpReserveListByCompanyIdAndActorId(
			long companyId, long actorId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpReserve.class, "companyid=? and actorid=?",
				new Object[] { companyId, actorId }, "reserveid asc", begin,
				size);
	}

	public void updateCmpReserve(CmpReserve cmpReserve) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpReserve);
	}

	public List<CmpReserve> getCmpReserveListByUserIdAndReserveStatus(
			long userId, byte reserveStatus, int begin, int size) {
		Query query = this.manager.createQuery();
		if (reserveStatus == CmpReserve.RESERVESTATUS_DEF) {
			return query.listEx(CmpReserve.class,
					"userid=? and reservestatus=? and reservetime>?",
					new Object[] { userId, reserveStatus, new Date() },
					"reserveid desc", begin, size);
		}
		return query.listEx(CmpReserve.class, "userid=? and reservestatus=?",
				new Object[] { userId, reserveStatus }, "reserveid desc",
				begin, size);
	}

	public List<CmpReserve> getCmpReserveListByUserIdForUnuse(long userId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpReserve.class,
				"userid=? and reservestatus=? and reservetime<?", new Object[] {
						userId, CmpReserve.RESERVESTATUS_DEF, new Date() },
				"reserveid desc", begin, size);
	}

	public int countCmpReserveByCompanyIdAndUserIdAndReserveDateForReserveOk(
			long companyId, long userId, Date beginReserveTime,
			Date endReserveTime) {
		Query query = this.manager.createQuery();
		return query
				.count(
						CmpReserve.class,
						"companyid=? and userid=? and reservestatus=? and reservetime>=? and reservetime<=?",
						new Object[] { companyId, userId,
								CmpReserve.RESERVESTATUS_DEF, beginReserveTime,
								endReserveTime });
	}

	public int countCmpReserveByCompanyIdAndUserIdForReserved(long companyId,
			long userId) {
		Query query = this.manager.createQuery();
		return query
				.count(
						CmpReserve.class,
						"companyid=? and userid=? and reservestatus=? and reservetime>?",
						new Object[] { companyId, userId,
								CmpReserve.RESERVESTATUS_DEF, new Date() });
	}

	public List<CmpReserve> getCmpReserveListByCompanyIdAndUserIdAndReserveStatus(
			long companyId, long userId, byte reserveStatus, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpReserve.class,
				"companyid=? and userid=? and reservestatus=?", new Object[] {
						companyId, userId, reserveStatus }, "reserveid desc",
				begin, size);
	}

	public int countCmpReserveByCompanyIdAndReserveStatus(long companyId,
			byte reserveStatus) {
		Query query = this.manager.createQuery();
		return query.count(CmpReserve.class, "companyid=? and reservestatus=?",
				new Object[] { companyId, reserveStatus });
	}

	public List<CmpActorReport> getCmpActorReportListByCompanyId(
			long companyId, Date beginTime, Date endTime) {
		Query query = this.manager.createQuery();
		String sql = "select actorid,count(*) from cmpreserve where companyid=? and reservestatus=? and endtime>=? and endtime<=? group by actorid";
		List<Object[]> olist = query.listdata("ds1", sql,
				new Object[] { companyId, CmpReserve.RESERVESTATUS_SUCCESS,
						beginTime, endTime });
		List<CmpActorReport> list = new ArrayList<CmpActorReport>();
		for (Object[] os : olist) {
			CmpActorReport cmpActorReport = new CmpActorReport();
			cmpActorReport.setActorId(Long.valueOf(os[0].toString()));
			cmpActorReport.setWorkCount(Integer.valueOf(os[1].toString()));
			list.add(cmpActorReport);
		}
		return list;
	}

	public int countCmpReserveByActorIdAndReserveStatus(long actorId,
			byte reserveStatus) {
		Query query = this.manager.createQuery();
		return query.count(CmpReserve.class, "actorid=? and reservestatus=?",
				new Object[] { actorId, reserveStatus });
	}
}