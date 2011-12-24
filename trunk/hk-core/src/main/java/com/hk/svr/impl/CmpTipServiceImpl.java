package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.City;
import com.hk.bean.CmpTip;
import com.hk.bean.CmpTipDel;
import com.hk.bean.CmpTipIdData;
import com.hk.bean.Pcity;
import com.hk.bean.UserCmpTip;
import com.hk.bean.UserCmpTipDel;
import com.hk.bean.UserCmpTipIdData;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.CmpTipService;
import com.hk.svr.ZoneService;
import com.hk.svr.pub.ZoneUtil;

public class CmpTipServiceImpl implements CmpTipService {

	@Autowired
	private QueryManager manager;

	public void createCmpTip(CmpTip cmpTip) {
		if (cmpTip.getCreateTime() == null) {
			cmpTip.setCreateTime(new Date());
		}
		Query query = this.manager.createQuery();
		CmpTipIdData data = new CmpTipIdData();
		data.setCreateTime(new Date());
		data.setTipId(query.insertObject(data).longValue());
		cmpTip.setTipId(data.getTipId());
		query.insertObject(cmpTip);
	}

	public void createUserCmpTip(UserCmpTip userCmpTip) {
		Query query = this.manager.createQuery();
		UserCmpTip o = this.getUserCmpTipByUserIdAndTipId(userCmpTip
				.getUserId(), userCmpTip.getTipId());
		CmpTip cmpTip = this.getCmpTip(userCmpTip.getTipId());
		if (cmpTip == null) {
			return;
		}
		if (userCmpTip.isDone() && cmpTip.getUserId() == userCmpTip.getUserId()) {
			cmpTip.setDoneflg(CmpTip.DONEFLG_DONE);
			cmpTip.setShowflg(CmpTip.SHOWFLG_Y);
			query.updateObject(cmpTip);
		}
		if (o != null) {
			o.setDoneflg(userCmpTip.getDoneflg());
			query.updateObject(o);
		}
		else {
			UserCmpTipIdData data = new UserCmpTipIdData();
			data.setCreateTime(new Date());
			data.setOid(query.insertObject(data).longValue());
			userCmpTip.setOid(data.getOid());
			query.insertObject(userCmpTip);
		}
	}

	public CmpTip deleteCmpTip(long tipId) {
		Query query = this.manager.createQuery();
		CmpTip cmpTip = this.getCmpTip(tipId);
		query.deleteById(CmpTip.class, tipId);
		query.delete(UserCmpTip.class, "tipid=?", new Object[] { tipId });
		return cmpTip;
	}

	public void deleteUserCmpTip(long oid) {
		Query query = this.manager.createQuery();
		UserCmpTip userCmpTip = query.getObjectById(UserCmpTip.class, oid);
		if (userCmpTip == null) {
			return;
		}
		query.deleteById(UserCmpTip.class, oid);
		query.delete(CmpTip.class, "tipid=?", new Object[] { userCmpTip
				.getTipId() });
		query.delete(UserCmpTip.class, "tipid=?", new Object[] { userCmpTip
				.getTipId() });
	}

	public List<CmpTip> getCmpTipListByCompanyId(long companyId, byte doneflg,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpTip.class, "companyid=? and doneflg=?",
				new Object[] { companyId, doneflg }, "tipid desc", begin, size);
	}

	public List<CmpTip> getCmpTipListByCompanyIdExcluedUserId(long companyId,
			long excludeUserId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpTip.class,
				"companyid=? and doneflg=? and userid!=?", new Object[] {
						companyId, CmpTip.DONEFLG_DONE, excludeUserId },
				"tipid desc", begin, size);
	}

	public List<CmpTip> getCmpTipListByCompanyIdAndUserId(long companyId,
			long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpTip.class,
				"companyid=? and doneflg=? and userid=?", new Object[] {
						companyId, CmpTip.DONEFLG_DONE, userId }, "tipid desc",
				begin, size);
	}

	public List<CmpTip> getCmpTipListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<CmpTip>();
		}
		StringBuffer sql = new StringBuffer(
				"select * from cmptip where tipid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), CmpTip.class);
	}

	public Map<Long, CmpTip> getCmpTipMapInId(List<Long> idList) {
		List<CmpTip> list = this.getCmpTipListInId(idList);
		Map<Long, CmpTip> map = new HashMap<Long, CmpTip>();
		for (CmpTip cmpTip : list) {
			map.put(cmpTip.getTipId(), cmpTip);
		}
		return map;
	}

	public List<UserCmpTip> getUserCmpTipDoneListByUserId(long userId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(UserCmpTip.class, "userid=? and doneflg=?",
				new Object[] { userId, CmpTip.DONEFLG_DONE }, "oid desc",
				begin, size);
	}

	public List<UserCmpTip> getUserCmpTipToDoListByUserId(long userId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(UserCmpTip.class, "userid=? and doneflg=?",
				new Object[] { userId, CmpTip.DONEFLG_TODO }, "oid desc",
				begin, size);
	}

	public List<UserCmpTip> getUserCmpTipListByUserIdInTipId(long userId,
			List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<UserCmpTip>();
		}
		StringBuffer sql = new StringBuffer(
				"select * from usercmptip where userid=? and tipid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), UserCmpTip.class, userId);
	}

	public Map<Long, UserCmpTip> getUserCmpTipMapByUserIdInTipId(long userId,
			List<Long> idList) {
		List<UserCmpTip> list = this.getUserCmpTipListByUserIdInTipId(userId,
				idList);
		Map<Long, UserCmpTip> map = new HashMap<Long, UserCmpTip>();
		for (UserCmpTip userCmpTip : list) {
			map.put(userCmpTip.getTipId(), userCmpTip);
		}
		return map;
	}

	public CmpTip getCmpTip(long tipId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpTip.class, tipId);
	}

	public UserCmpTip getUserCmpTipByUserIdAndTipId(long userId, long tipId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(UserCmpTip.class, "userid=? and tipid=?",
				new Object[] { userId, tipId });
	}

	public UserCmpTip deleteUserCmpTipByUserIdAndTipId(long userId, long tipId) {
		Query query = this.manager.createQuery();
		UserCmpTip userCmpTip = query.getObjectEx(UserCmpTip.class,
				"userid=? and tipid=?", new Object[] { userId, tipId });
		query.delete(UserCmpTip.class, "userid=? and tipid=?", new Object[] {
				userId, tipId });
		return userCmpTip;
	}

	public int countUserCmpTipDone(long userId) {
		Query query = this.manager.createQuery();
		return query.count(UserCmpTip.class, "userid=? and doneflg=?",
				new Object[] { userId, CmpTip.DONEFLG_DONE });
	}

	public int countUserCmpTipDonefromToDo(long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void updatepcityiddata() {
		ZoneService zoneService = (ZoneService) HkUtil.getBean("zoneService");
		Query query = this.manager.createQuery();
		List<UserCmpTip> list = query.listEx(UserCmpTip.class);
		for (UserCmpTip o : list) {
			Pcity pcity = ZoneUtil.getPcityOld(o.getPcityId());
			if (pcity != null) {
				String name = DataUtil.filterZoneName(pcity.getNameOld());
				City city = zoneService.getCityLike(name);
				if (city != null) {
					o.setPcityId(city.getCityId());
					query.updateObject(o);
				}
			}
		}
	}

	public List<UserCmpTip> getUserCmpTipDoneListByPcityId(int pcityId,
			long excludeUserId, int begin, int size) {
		Query query = this.manager.createQuery();
		if (excludeUserId > 0) {
			return query.listEx(UserCmpTip.class,
					"pcityid=? and doneflg=? and userid!=?", new Object[] {
							pcityId, CmpTip.DONEFLG_DONE, excludeUserId },
					"oid desc", begin, size);
		}
		return query.listEx(UserCmpTip.class, "pcityid=? and doneflg=?",
				new Object[] { pcityId, CmpTip.DONEFLG_DONE }, "oid desc",
				begin, size);
	}

	public List<UserCmpTip> getUserCmpTipToDoListByPcityId(int pcityId,
			long excludeUserId, int begin, int size) {
		Query query = this.manager.createQuery();
		if (excludeUserId > 0) {
			return query.listEx(UserCmpTip.class,
					"pcityid=? and doneflg=? and userid!=?", new Object[] {
							pcityId, CmpTip.DONEFLG_TODO, excludeUserId },
					"oid desc", begin, size);
		}
		return query.listEx(UserCmpTip.class, "pcityid=? and doneflg=?",
				new Object[] { pcityId, CmpTip.DONEFLG_TODO }, "oid desc",
				begin, size);
	}

	public void updateCmpTip(CmpTip cmpTip) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpTip);
		if (cmpTip != null) {
			UserCmpTip userCmpTip = this.getUserCmpTipByUserIdAndTipId(cmpTip
					.getUserId(), cmpTip.getTipId());
			if (userCmpTip != null) {
				userCmpTip.setDoneflg(cmpTip.getDoneflg());
				this.updateUserCmpTip(userCmpTip);
			}
		}
	}

	public List<UserCmpTip> getUserCmpTipListByTipId(long tipId) {
		Query query = this.manager.createQuery();
		return query
				.listEx(UserCmpTip.class, "tipid=?", new Object[] { tipId });
	}

	public void updateUserCmpTip(UserCmpTip userCmpTip) {
		Query query = this.manager.createQuery();
		query.updateObject(userCmpTip);
	}

	public List<UserCmpTip> getUserCmpTipListByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(UserCmpTip.class, "companyid=?",
				new Object[] { companyId });
	}

	public List<CmpTip> getAllCmpTipListByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpTip.class, "companyid=?",
				new Object[] { companyId });
	}

	public void updateUserCmpTipCompanyName(UserCmpTip userCmpTip) {
		this.updateUserCmpTip(userCmpTip);
	}

	public void bombCmpTip(CmpTipDel cmpTipDel) {
		Query query = this.manager.createQuery();
		if (query.getObjectEx(CmpTipDel.class, "tipid=?",
				new Object[] { cmpTipDel.getTipId() }) != null) {
			return;
		}
		query.insertObject(cmpTipDel);
		List<UserCmpTip> list = this.getUserCmpTipListByTipId(cmpTipDel
				.getTipId());
		for (UserCmpTip o : list) {
			UserCmpTipDel userCmpTipDel = new UserCmpTipDel(o);
			if (query.getObjectById(UserCmpTipDel.class, o.getOid()) == null) {
				query.insertObject(userCmpTipDel);
			}
		}
		this.deleteCmpTip(cmpTipDel.getTipId());
	}

	public List<CmpTipDel> getCmpTipDelList(long opuserId, int begin, int size) {
		Query query = this.manager.createQuery();
		if (opuserId > 0) {
			return query.listEx(CmpTipDel.class, "opuserid=?",
					new Object[] { opuserId }, "optime desc", begin, size);
		}
		return query.listEx(CmpTipDel.class, "optime desc", begin, size);
	}

	public CmpTip recoverCmpTip(long tipId) {
		Query query = this.manager.createQuery();
		CmpTipDel cmpTipDel = query.getObjectById(CmpTipDel.class, tipId);
		if (cmpTipDel == null) {
			return null;
		}
		CmpTip cmpTip = new CmpTip();
		cmpTip.setTipId(cmpTipDel.getTipId());
		cmpTip.setCompanyId(cmpTipDel.getCompanyId());
		cmpTip.setContent(cmpTipDel.getContent());
		cmpTip.setCreateTime(cmpTipDel.getCreateTime());
		cmpTip.setDoneflg(cmpTipDel.getDoneflg());
		cmpTip.setShowflg(cmpTipDel.getShowflg());
		cmpTip.setUserId(cmpTipDel.getUserId());
		query.insertObject(cmpTip);
		List<UserCmpTipDel> list = query.listEx(UserCmpTipDel.class, "tipid=?",
				new Object[] { cmpTipDel.getTipId() });
		for (UserCmpTipDel o : list) {
			UserCmpTip userCmpTip = new UserCmpTip();
			userCmpTip.setOid(o.getOid());
			userCmpTip.setCompanyId(o.getCompanyId());
			userCmpTip.setCreateTime(o.getCreateTime());
			userCmpTip.setData(o.getData());
			userCmpTip.setDoneflg(o.getDoneflg());
			userCmpTip.setPcityId(o.getPcityId());
			userCmpTip.setUserId(o.getUserId());
			userCmpTip.setTipId(o.getTipId());
			query.insertObject(userCmpTip);
		}
		query.deleteById(CmpTipDel.class, cmpTipDel.getTipId());
		query.delete(UserCmpTipDel.class, "tipid=?", new Object[] { cmpTipDel
				.getTipId() });
		return cmpTip;
	}
}