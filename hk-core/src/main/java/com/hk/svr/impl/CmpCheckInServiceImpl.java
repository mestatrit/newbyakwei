package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CheckInResult;
import com.hk.bean.CmpCheckInUser;
import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.Company;
import com.hk.bean.Mayor;
import com.hk.bean.UserCmpPoint;
import com.hk.bean.UserDateCheckInCmp;
import com.hk.bean.UserLastCheckIn;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpCheckInService;

class NightlyDate {

	private Date begin;

	private Date end;

	public NightlyDate(Date date, int nightCheckInHourBegin,
			int nightCheckInHourEnd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (hour == nightCheckInHourBegin || hour <= nightCheckInHourEnd) {
			if (hour == nightCheckInHourBegin) {
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				begin = cal.getTime();
				cal.add(Calendar.DATE, 1);
				cal.set(Calendar.HOUR_OF_DAY, nightCheckInHourEnd);
				end = cal.getTime();
			}
			else {
				cal.add(Calendar.DATE, -1);
				cal.set(Calendar.HOUR_OF_DAY, nightCheckInHourBegin);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				begin = cal.getTime();
				cal.add(Calendar.DATE, 1);
				cal.set(Calendar.HOUR_OF_DAY, nightCheckInHourEnd);
				end = cal.getTime();
			}
		}
	}

	public Date getBegin() {
		return begin;
	}

	public Date getEnd() {
		return end;
	}
}

public class CmpCheckInServiceImpl implements CmpCheckInService {

	@Autowired
	private QueryManager manager;

	private int nightCheckInHourBegin;

	private int nightCheckInHourEnd;

	/**
	 * 成为地主的最小报到积分
	 */
	private int minMayorPoints = 8;

	public List<CmpCheckInUserLog> getCmpCheckInUserLogListByUserId(
			long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpCheckInUserLog.class, "userid=?",
				new Object[] { userId }, "logid desc", begin, size);
	}

	public List<CmpCheckInUserLog> getEffectCmpCheckInUserLogListByPcityId(
			int pcityId, Date min, Date max, int begin, int size) {
		Query query = this.manager.createQuery();
		if (pcityId > 0) {
			return query
					.listEx(
							CmpCheckInUserLog.class,
							"pcityid=? and effectflg=? and createtime>=? and createtime<=?",
							new Object[] { pcityId,
									CmpCheckInUserLog.EFFECTFLG_Y, min, max },
							"logid desc", begin, size);
		}
		return query.listEx(CmpCheckInUserLog.class,
				"effectflg=? and createtime>=? and createtime<=?",
				new Object[] { CmpCheckInUserLog.EFFECTFLG_Y, min, max },
				"logid desc", begin, size);
	}

	public int countCmpCheckInUserByCompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.count(CmpCheckInUser.class, "companyid=?",
				new Object[] { companyId });
	}

	public int countCmpCheckInUserLogByCompanyIdAndUserId(long companyId,
			long userId) {
		Query query = manager.createQuery();
		return query.count(CmpCheckInUserLog.class, "companyid=? and userid=?",
				new Object[] { companyId, userId });
	}

	public List<CmpCheckInUser> getCmpCheckInUserListByCompanyId(
			long companyId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpCheckInUser.class, "companyid=?",
				new Object[] { companyId }, "uptime desc", begin, size);
	}

	public int countEffectCmpCheckInUserLogByCompanyIdAndUserId(long companyId,
			long userId, Date date) {
		Date min = DataUtil.getDate(date);
		Date max = DataUtil.getEndDate(date);
		Query query = manager.createQuery();
		return query
				.count(
						CmpCheckInUserLog.class,
						"companyid=? and userid=? and createtime>=? and createtime<=? and effectflg=?",
						new Object[] { companyId, userId, min, max,
								CmpCheckInUserLog.EFFECTFLG_Y });
	}

	public int countEffectCmpCheckInUserLogByCompanyIdAndUserId(long companyId,
			long userId, Date begin, Date end) {
		Query query = manager.createQuery();
		return query
				.count(
						CmpCheckInUserLog.class,
						"companyid=? and userid=? and createtime>=? and createtime<=? and effectflg=?",
						new Object[] { companyId, userId, begin, end,
								CmpCheckInUserLog.EFFECTFLG_Y });
	}

	public int countEffectNightlyCmpCheckInUserLogByCompanyAndUserId(
			long companyId, long userId, Date date) {
		NightlyDate nightlyDate = new NightlyDate(date,
				this.nightCheckInHourBegin, this.nightCheckInHourEnd);
		Date begin = nightlyDate.getBegin();
		Date end = nightlyDate.getEnd();
		if (begin != null && end != null) {
			Query query = manager.createQuery();
			return query
					.count(
							CmpCheckInUserLog.class,
							"companyid=? and userid=? and createtime>=? and createtime<=? and effectflg=? and nightflg=?",
							new Object[] { companyId, userId, begin, end,
									CmpCheckInUserLog.EFFECTFLG_Y,
									CmpCheckInUserLog.NIGHTFLG_Y });
		}
		return 0;
	}

	public int countEffectCmpCheckInUserLogByCompanyIdAndUserId(long companyId,
			long userId) {
		Query query = manager.createQuery();
		return query.count(CmpCheckInUserLog.class,
				"companyid=? and userid=? and effectflg=?", new Object[] {
						companyId, userId, CmpCheckInUserLog.EFFECTFLG_Y });
	}

	public int countEffectCmpCheckinUserLogBycompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.count(CmpCheckInUserLog.class,
				"companyid=? and effectflg=?", new Object[] { companyId,
						CmpCheckInUserLog.EFFECTFLG_Y });
	}

	public int countEffectCmpCheckInUserLogByUserId(long userId) {
		Query query = manager.createQuery();
		return query.count(CmpCheckInUserLog.class, "userid=? and effectflg=?",
				new Object[] { userId, CmpCheckInUserLog.EFFECTFLG_Y });
	}

	public void setNightCheckInHourBegin(int nightCheckInHourBegin) {
		this.nightCheckInHourBegin = nightCheckInHourBegin;
	}

	public void setNightCheckInHourEnd(int nightCheckInHourEnd) {
		this.nightCheckInHourEnd = nightCheckInHourEnd;
	}

	public int countCmpCheckInUserLogByUserIdForNight(long userId) {
		Query query = this.manager.createQuery();
		return query.count(CmpCheckInUserLog.class, "userid=? and nightflg=?",
				new Object[] { userId, CmpCheckInUserLog.NIGHTFLG_Y });
	}

	/**
	 * 如果可以报到，就返回实际对象，如果不能报到，就返回null，
	 * 
	 * @param cmpCheckInUserLog
	 * @return 此对象可以是最后一次在这里报到的对象，也可是是新创建的对象代表第一次报到
	 *         2010-4-13
	 */
	private CmpCheckInUser makeCmpCheckInUserOnCmp(
			CmpCheckInUserLog cmpCheckInUserLog) {
		long companyId = cmpCheckInUserLog.getCompanyId();
		long userId = cmpCheckInUserLog.getUserId();
		Query query = manager.createQuery();
		CmpCheckInUser cmpCheckInUser = query.getObjectEx(CmpCheckInUser.class,
				"companyid=? and userid=?", new Object[] { companyId, userId });
		if (cmpCheckInUser == null) {
			cmpCheckInUser = new CmpCheckInUser();
			cmpCheckInUser.setCompanyId(companyId);
			cmpCheckInUser.setUserId(userId);
			cmpCheckInUser.setUptime(cmpCheckInUserLog.getCreateTime());
			cmpCheckInUser.setSex(cmpCheckInUserLog.getSex());
			cmpCheckInUser.setEffectCount(0);
		}
		else {
			// if (cmpCheckInUser.getEffectCount() != 0) {//
			// 有效报到为0的情况下，本次报到为有效报到
			// // 2次报到时间差不能小于5分钟
			// long last_time = cmpCheckInUser.getUptime().getTime();//
			// 最后一次有效报到的时间
			// long now_time = cmpCheckInUserLog.getCreateTime().getTime();//
			// 当前报到时间
			// if (now_time - last_time < this.time_diff) {// 不能进行报到
			// return null;
			// }
			// }
			cmpCheckInUser.setSex(cmpCheckInUserLog.getSex());
			cmpCheckInUser.setUptime(cmpCheckInUserLog.getCreateTime());
		}
		return cmpCheckInUser;
	}

	private void createOrUpdateCmpCheckInUser(CmpCheckInUser cmpCheckInUser) {
		Query query = manager.createQuery();
		if (cmpCheckInUser.getOid() > 0) {
			query.updateObject(cmpCheckInUser);
		}
		else {
			cmpCheckInUser.setOid(query.insertObject(cmpCheckInUser)
					.longValue());
		}
	}

	public CheckInResult checkIn(CmpCheckInUserLog cmpCheckInUserLog,
			boolean forceInvalid, int maxCheckInCount) {
		CheckInResult r = new CheckInResult();
		long companyId = cmpCheckInUserLog.getCompanyId();
		long userId = cmpCheckInUserLog.getUserId();
		if (cmpCheckInUserLog.getCreateTime() == null) {
			cmpCheckInUserLog.setCreateTime(new Date());
		}
		CmpCheckInUser cmpCheckInUser = this
				.makeCmpCheckInUserOnCmp(cmpCheckInUserLog);
		if (cmpCheckInUser == null) {
			r.setCheckInTimeTooShort(true);
			r.setCheckInSuccess(false);
			return r;
		}
		// 有效报到数量
		if (forceInvalid) {// 强制无效报到
			cmpCheckInUserLog.setEffectflg(CmpCheckInUserLog.EFFECTFLG_N);
		}
		else {
			int count_effect = this
					.countEffectCmpCheckInUserLogByCompanyIdAndUserId(
							companyId, userId, cmpCheckInUserLog
									.getCreateTime());
			if (count_effect < maxCheckInCount) {// 有效报到小于maxCheckInCount时，本次报到为有效报到
				cmpCheckInUserLog.setEffectflg(CmpCheckInUserLog.EFFECTFLG_Y);
				cmpCheckInUser
						.setEffectCount(cmpCheckInUser.getEffectCount() + 1);
			}
			else {// 无效报到
				cmpCheckInUserLog.setEffectflg(CmpCheckInUserLog.EFFECTFLG_N);
			}
		}
		this.checkNightCheckIn(cmpCheckInUserLog);
		Query query = manager.createQuery();
		cmpCheckInUserLog.setLogId(query.insertObject(cmpCheckInUserLog)
				.longValue());
		if (cmpCheckInUserLog.isEffective()) {
			r.setCheckInSuccess(true);
		}
		this.createOrUpdateCmpCheckInUser(cmpCheckInUser);
		return r;
	}

	private void checkNightCheckIn(CmpCheckInUserLog cmpCheckInUserLog) {
		Calendar c = Calendar.getInstance();
		c.setTime(cmpCheckInUserLog.getCreateTime());
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if (hour >= this.nightCheckInHourBegin
				|| hour <= this.nightCheckInHourEnd) {// 是否是深夜报到
			cmpCheckInUserLog.setNightflg(CmpCheckInUserLog.NIGHTFLG_Y);
		}
	}

	public CheckInResult checkIn(CmpCheckInUserLog cmpCheckInUserLog,
			boolean forceInvalid, Company company) {
		return this.checkIn(cmpCheckInUserLog, forceInvalid, 2);
	}

	public int countCmpCheckInUserByUserId(long userId) {
		Query query = this.manager.createQuery();
		return query.count(CmpCheckInUser.class, "userid=?",
				new Object[] { userId });
	}

	public int countCmpCheckInUserByCompanyIdAndSex(long companyId, byte sex,
			Date begin, Date end) {
		Query query = this.manager.createQuery();
		return query.count(CmpCheckInUser.class,
				"companyid=? and sex=? and uptime>=? and uptime<=?",
				new Object[] { companyId, sex, begin, end });
	}

	public CmpCheckInUser getCmpCheckInUser(long companyId, long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpCheckInUser.class,
				"companyid=? and userid=?", new Object[] { companyId, userId });
	}

	public int countNigghtlyCmpCheckInUserByUserId(long userId, Date date) {
		NightlyDate nightlyDate = new NightlyDate(date,
				this.nightCheckInHourBegin, this.nightCheckInHourEnd);
		Date begin = nightlyDate.getBegin();
		Date end = nightlyDate.getEnd();
		if (begin != null && end != null) {
			Query query = this.manager.createQuery();
			return query.count(CmpCheckInUser.class,
					"userid=? and uptime>=? and uptime<=?", new Object[] {
							userId, begin, end });
		}
		return 0;
	}

	public int countCmpCheckInUserLogByCompanyIdAndUserIdForOneNight(
			long companyId, long userId, Date date) {
		NightlyDate nightlyDate = new NightlyDate(date,
				this.nightCheckInHourBegin, this.nightCheckInHourEnd);
		Date begin = nightlyDate.getBegin();
		Date end = nightlyDate.getEnd();
		if (begin != null && end != null) {
			Query query = this.manager.createQuery();
			return query
					.count(
							CmpCheckInUserLog.class,
							"companyid=? and userid=? and nightflg=? and createtime>=? and createtime<=?",
							new Object[] { companyId, userId,
									CmpCheckInUserLog.NIGHTFLG_Y, begin, end });
		}
		return 0;
	}

	public int countEffectCmpCheckInUserLogByUserIdInMonth(long userId,
			Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		Date begin = cal.getTime();
		begin = DataUtil.getDate(begin);
		cal.setTime(begin);
		cal.add(Calendar.MONTH, 1);
		Date end = cal.getTime();
		Query query = this.manager.createQuery();
		return query.count(CmpCheckInUserLog.class,
				"userid=? and effectflg=? and createtime>=? and createtime<=?",
				new Object[] { userId, CmpCheckInUserLog.EFFECTFLG_Y, begin,
						end });
	}

	public int countEffectCmpCheckInUserLogByUserId(long userId, Date begin,
			Date end) {
		Query query = this.manager.createQuery();
		return query.count(CmpCheckInUserLog.class,
				"userid=? and effectflg=? and createtime>=? and createtime<=?",
				new Object[] { userId, CmpCheckInUserLog.EFFECTFLG_Y, begin,
						end });
	}

	public int countEffectCmpCheckInUserLogByUserIdForDiffCompanyId(
			long userId, Date begin, Date end) {
		String sql = "select count(distinct companyid) from cmpcheckinuserlog c where userid=? and effectflg=? and createtime>=? and createtime<=?";
		Query query = this.manager.createQuery();
		List<Object[]> list = query.listdata("ds1", sql, userId,
				CmpCheckInUserLog.EFFECTFLG_Y, begin, end);
		if (list.size() == 0) {
			return 0;
		}
		Object[] objs = list.get(0);
		return Integer.valueOf(objs[0].toString());
	}

	public int countEffectCmpCheckInUserLogByGroupIdAndUserId(long groupId,
			long userId) {
		Query query = this.manager.createQuery();
		return query.count(CmpCheckInUserLog.class,
				"groupid=? and userid=? and effectflg=?", new Object[] {
						groupId, userId, CmpCheckInUserLog.EFFECTFLG_Y });
	}

	public int countEffectCmpCheckInUserLogByKindIdAndUserId(int kindId,
			long userId) {
		Query query = this.manager.createQuery();
		return query.count(CmpCheckInUserLog.class,
				"kindid=? and userid=? and effectflg=?", new Object[] { kindId,
						userId, CmpCheckInUserLog.EFFECTFLG_Y });
	}

	public int countEffectCmpCheckInUserLogByParentIdAndUserId(int parentId,
			long userId) {
		Query query = this.manager.createQuery();
		return query.count(CmpCheckInUserLog.class,
				"parentid=? and userid=? and effectflg=?", new Object[] {
						parentId, userId, CmpCheckInUserLog.EFFECTFLG_Y });
	}

	public int countEffectCmpCheckInUserLogByKindIdAndUserIdDiffCompanyId(
			int kindId, long userId) {
		Query query = this.manager.createQuery();
		String sql = "select count(distinct companyid) from cmpcheckinuserlog where kindid=? and userid=? and effectflg=?";
		List<Object[]> list = query.listdata("ds1", sql, kindId, userId,
				CmpCheckInUserLog.EFFECTFLG_Y);
		if (list.size() == 0) {
			return 0;
		}
		Object[] objs = list.get(0);
		return Integer.valueOf(objs[0].toString());
	}

	private void createMayor(Mayor mayor) {
		Query query = this.manager.createQuery();
		if (query.count(Mayor.class, "companyid=? and userid=?", new Object[] {
				mayor.getCompanyId(), mayor.getUserId() }) == 0) {
			query.insertObject(mayor);
		}
	}

	public int countMayorByUserId(long userId) {
		Query query = this.manager.createQuery();
		return query.count(Company.class, "mayoruserid=?",
				new Object[] { userId });
	}

	public List<Mayor> getMayorListByUserId(long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Mayor.class, "userid=?", new Object[] { userId },
				"mayorid desc", begin, size);
	}

	public void deleteMayor(long mayorId) {
		Query query = this.manager.createQuery();
		Mayor mayor = this.getMayor(mayorId);
		if (mayor == null) {
			return;
		}
		query.deleteById(Mayor.class, mayorId);
	}

	public Mayor getMayor(long mayorId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Mayor.class, mayorId);
	}

	public List<UserLastCheckIn> getUserLastCheckInListInIdList(
			List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<UserLastCheckIn>();
		}
		StringBuilder sb = new StringBuilder(
				"select * from userlastcheckin where userid in (");
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1).append(")");
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sb.toString(), UserLastCheckIn.class);
	}

	public List<CmpCheckInUserLog> getCmpCheckInUserLogListInId(
			List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<CmpCheckInUserLog>();
		}
		StringBuilder sb = new StringBuilder(
				"select * from cmpcheckinuserlog where logid in (");
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1).append(") order by logid desc");
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sb.toString(), CmpCheckInUserLog.class);
	}

	public List<CmpCheckInUser> getCheckInUserList() {
		Query query = this.manager.createQuery();
		return query.listEx(CmpCheckInUser.class);
	}

	public void updateUserLastCheckIn(UserLastCheckIn userLastCheckIn) {
		Query query = this.manager.createQuery();
		UserLastCheckIn o = query.getObjectById(UserLastCheckIn.class,
				userLastCheckIn.getUserId());
		if (o == null) {
			query.insertObject(userLastCheckIn);
		}
		else {
			o.setData(userLastCheckIn.getData());
			query.updateObject(o);
		}
	}

	public UserLastCheckIn getUserLastCheckIn(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserLastCheckIn.class, userId);
	}

	public List<CmpCheckInUser> getCmpCheckInUserListByUserId(long userId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpCheckInUser.class, "userid=?",
				new Object[] { userId }, "uptime desc", begin, size);
	}

	public CmpCheckInUserLog getLastCmpCheckInUserLogByUserId(long userId) {
		Query query = this.manager.createQuery();
		return query.getObject(CmpCheckInUserLog.class, "userid=?",
				new Object[] { userId }, "logid desc");
	}

	public List<Mayor> getMayorListByPcityId(int pcityId, int begin, int size) {
		Query query = this.manager.createQuery();
		if (pcityId > 0) {
			return query.listEx(Mayor.class, "pcityid=?",
					new Object[] { pcityId }, "mayorid desc", begin, size);
		}
		return query.listEx(Mayor.class, "mayorid desc", begin, size);
	}

	public List<Mayor> getMayorListAll() {
		Query query = this.manager.createQuery();
		return query.listEx(Mayor.class);
	}

	public void updateMayor(Mayor mayor) {
		Query query = this.manager.createQuery();
		query.updateObject(mayor);
	}

	public List<UserLastCheckIn> getUserLastCheckInListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<UserLastCheckIn>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from userlastcheckin where userid in(");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), UserLastCheckIn.class);
	}

	public Map<Long, UserLastCheckIn> getUserLastCheckInMapInId(
			List<Long> idList) {
		List<UserLastCheckIn> list = this
				.getUserLastCheckInListInIdList(idList);
		Map<Long, UserLastCheckIn> map = new HashMap<Long, UserLastCheckIn>();
		for (UserLastCheckIn o : list) {
			map.put(o.getUserId(), o);
		}
		return map;
	}

	public Map<Long, CmpCheckInUserLog> getCmpCheckInUserLogMapInId(
			List<Long> idList) {
		List<CmpCheckInUserLog> list = this
				.getCmpCheckInUserLogListInId(idList);
		Map<Long, CmpCheckInUserLog> map = new HashMap<Long, CmpCheckInUserLog>();
		for (CmpCheckInUserLog o : list) {
			map.put(o.getLogId(), o);
		}
		return map;
	}

	public UserCmpPoint getUserCmpPointByUserIdAndCompanyId(long userId,
			long companyId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(UserCmpPoint.class,
				"userid=? and companyid=?", new Object[] { userId, companyId });
	}

	public void updateUserCmpPoints(UserCmpPoint userCmpPoint) {
		Query query = this.manager.createQuery();
		query.updateObject(userCmpPoint);
	}

	public void saveUserCmpPoints(UserCmpPoint userCmpPoint) {
		Query query = this.manager.createQuery();
		UserCmpPoint o = this.getUserCmpPointByUserIdAndCompanyId(userCmpPoint
				.getUserId(), userCmpPoint.getCompanyId());
		if (o != null) {
			this.updateUserCmpPoints(userCmpPoint);
		}
		else {
			userCmpPoint.setOid(query.insertObject(userCmpPoint).longValue());
		}
	}

	public void addUserCmpPoints(long userId, long companyId, int add) {
		Query query = this.manager.createQuery();
		UserCmpPoint userCmpPoint = this.getUserCmpPointByUserIdAndCompanyId(
				userId, companyId);
		if (userCmpPoint != null) {
			userCmpPoint.setPoints(userCmpPoint.getPoints() + add);
			this.updateUserCmpPoints(userCmpPoint);
		}
		else {
			userCmpPoint = new UserCmpPoint();
			userCmpPoint.setUserId(userId);
			userCmpPoint.setCompanyId(companyId);
			userCmpPoint.setPoints(add);
			if (userCmpPoint.getPoints() < 0) {
				userCmpPoint.setPoints(0);
			}
			userCmpPoint.setOid(query.insertObject(userCmpPoint).longValue());
		}
	}

	public int checkMayor(long userId, Company company, String ip,
			boolean forceMayor) {
		if (company.getMayorUserId() == userId) {
			return 0;
		}
		if (forceMayor) {
			this.deleteMayorByCompanyId(company.getCompanyId());
			Mayor mayor = new Mayor();
			mayor.setUserId(userId);
			mayor.setCompanyId(company.getCompanyId());
			mayor.setPcityId(company.getPcityId());
			this.createMayor(mayor);
			return 1;
		}
		UserCmpPoint userCmpPoint = this.getUserCmpPointByUserIdAndCompanyId(
				userId, company.getCompanyId());
		if (userCmpPoint == null) {
			return -1;
		}
		// 当大于最小值是才有资格成为地主
		if (userCmpPoint.getPoints() >= this.minMayorPoints) {
			boolean cannewmayor = false;
			UserCmpPoint topUserCmpPoint = this
					.getTopUserCmpPointExcludeUserId(company.getCompanyId(),
							userId);// 查看除本人之外的当前最高分
			if (topUserCmpPoint != null) {
				// 如果比当前最高分高出3分，就成为新地主
				if (userCmpPoint.getPoints() - topUserCmpPoint.getPoints() >= 3) {
					cannewmayor = true;
				}
			}
			else {// 如果没有其他人最高分，就成为地主
				cannewmayor = true;
			}
			if (cannewmayor) {
				this.deleteMayorByCompanyId(company.getCompanyId());
				Mayor mayor = new Mayor();
				mayor.setUserId(userId);
				mayor.setCompanyId(company.getCompanyId());
				mayor.setPcityId(company.getPcityId());
				this.createMayor(mayor);
				return 1;
			}
		}
		return -1;
	}

	private UserCmpPoint getTopUserCmpPointExcludeUserId(long companyId,
			long userId) {
		Query query = this.manager.createQuery();
		return query.getObject(UserCmpPoint.class, "companyid=? and userid!=?",
				new Object[] { companyId, userId }, "points desc");
	}

	private void deleteMayorByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		query.delete(Mayor.class, "companyid=?", new Object[] { companyId });
	}

	public boolean canCheckIn(long userId, long companyId) {
		CmpCheckInUserLog cmpCheckInUserLog = new CmpCheckInUserLog();
		cmpCheckInUserLog.setUserId(userId);
		cmpCheckInUserLog.setCompanyId(companyId);
		cmpCheckInUserLog.setCreateTime(new Date());
		CmpCheckInUser cmpCheckInUser = this
				.makeCmpCheckInUserOnCmp(cmpCheckInUserLog);
		if (cmpCheckInUser != null) {
			return true;
		}
		return false;
	}

	public List<CmpCheckInUserLog> getEffectCmpCheckInUserLogListByPcityId(
			int pcityId, int begin, int size) {
		Query query = this.manager.createQuery();
		if (pcityId > 0) {
			return query.listEx(CmpCheckInUserLog.class,
					"pcityid=? and effectflg=?", new Object[] { pcityId,
							CmpCheckInUserLog.EFFECTFLG_Y }, "logid desc",
					begin, size);
		}
		return query.listEx(CmpCheckInUserLog.class, "effectflg=?",
				new Object[] { CmpCheckInUserLog.EFFECTFLG_Y }, "logid desc",
				begin, size);
	}

	public void saveUserDateCheckInCmp(UserDateCheckInCmp userDateCheckInCmp) {
		UserDateCheckInCmp o = this.getUserDateCheckInCmp(userDateCheckInCmp
				.getUserId());
		Query query = this.manager.createQuery();
		if (o == null) {
			query.insertObject(userDateCheckInCmp);
		}
		else {
			query.updateObject(userDateCheckInCmp);
		}
	}

	public UserDateCheckInCmp getUserDateCheckInCmp(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserDateCheckInCmp.class, userId);
	}
}