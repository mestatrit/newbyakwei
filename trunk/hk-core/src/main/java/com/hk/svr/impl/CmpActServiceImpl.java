package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.City;
import com.hk.bean.CmpAct;
import com.hk.bean.CmpActCmt;
import com.hk.bean.CmpActCost;
import com.hk.bean.CmpActIdData;
import com.hk.bean.CmpActKind;
import com.hk.bean.CmpActStepCost;
import com.hk.bean.CmpActUser;
import com.hk.bean.Pcity;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.CmpActService;
import com.hk.svr.ZoneService;
import com.hk.svr.pub.ZoneUtil;

public class CmpActServiceImpl implements CmpActService {
	@Autowired
	private QueryManager manager;

	public void createCmpAct(CmpAct cmpAct, List<CmpActCost> cmpActCostList,
			List<CmpActStepCost> cmpActStepCostList) {
		long actId = cmpAct.getActId();
		cmpAct.setCreateTime(new Date());
		Query query = manager.createQuery();
		query.insertObject(cmpAct);
		cmpAct.setActId(actId);
		if (cmpActCostList != null) {
			for (CmpActCost cost : cmpActCostList) {
				cost.setActId(actId);
				this.createCmpActCost(cost);
			}
		}
		if (cmpActStepCostList != null) {
			for (CmpActStepCost cost : cmpActStepCostList) {
				cost.setActId(actId);
				this.createCmpActStepCost(cost);
			}
		}
	}

	public void createCmpActCmt(CmpActCmt cmpActCmt) {
		this.validateCmpActCmt(cmpActCmt);
		Query query = manager.createQuery();
		query.insertObject(cmpActCmt);
	}

	public void createCmpActCost(CmpActCost cmpActCost) {
		this.validateCmpActCost(cmpActCost);
		Query query = manager.createQuery();
		query.insertObject(cmpActCost);
	}

	private void validateCmpActCost(CmpActCost cmpActCost) {
		if (cmpActCost.getActId() <= 0) {
			throw new RuntimeException("invalid actId [ "
					+ cmpActCost.getActId() + " ]");
		}
	}

	private void validateCmpActStepCost(CmpActStepCost cmpActStepCost) {
		if (cmpActStepCost.getActId() <= 0) {
			throw new RuntimeException("invalid actId [ "
					+ cmpActStepCost.getActId() + " ]");
		}
	}

	private void validateCmpActCmt(CmpActCmt cmpActCmt) {
		if (cmpActCmt.getActId() <= 0) {
			throw new RuntimeException("invalid actId [ "
					+ cmpActCmt.getActId() + " ]");
		}
	}

	public void createCmpActStepCost(CmpActStepCost cmpActStepCost) {
		this.validateCmpActStepCost(cmpActStepCost);
		Query query = manager.createQuery();
		query.insertObject(cmpActStepCost);
	}

	public void createCmpActUser(CmpActUser cmpActUser) {
		Query query = manager.createQuery();
		if (query.count(CmpActUser.class, "actid=? and userid=?", new Object[] {
				cmpActUser.getActId(), cmpActUser.getUserId() }) == 0) {
			query.insertObject(cmpActUser);
		}
	}

	public void deleteCmpActCmt(long cmtId) {
		Query query = manager.createQuery();
		query.deleteById(CmpActCmt.class, cmtId);
	}

	public void deleteCmpActCost(long costId) {
		Query query = manager.createQuery();
		query.deleteById(CmpActCost.class, costId);
	}

	public void deleteCmpActStepCost(long costId) {
		Query query = manager.createQuery();
		query.deleteById(CmpActStepCost.class, costId);
	}

	public void deleteCmpActUser(long actId, long userId) {
		Query query = manager.createQuery();
		query.delete(CmpActUser.class, "actid=? and userid=?", new Object[] {
				actId, userId });
	}

	public CmpActCost getActCost(long costId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpActCost.class, costId);
	}

	public CmpAct getCmpAct(long actId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpAct.class, actId);
	}

	public CmpActCmt getCmpActCmt(long cmtId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpActCmt.class, cmtId);
	}

	public List<CmpActCmt> getCmpActCmtListByActId(long actId, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpActCmt.class, "actid=?", new Object[] { actId },
				"cmtid desc", begin, size);
	}

	public CmpActStepCost getCmpActStepCost(long costId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpActStepCost.class, costId);
	}

	public void updateCmpAct(CmpAct cmpAct) {
		Query query = manager.createQuery();
		query.updateObject(cmpAct);
	}

	public void updateCmpActCost(CmpActCost cmpActCost) {
		this.validateCmpActCost(cmpActCost);
		Query query = manager.createQuery();
		query.updateObject(cmpActCost);
	}

	public void updateCmpActStepCost(CmpActStepCost cmpActStepCost) {
		this.validateCmpActStepCost(cmpActStepCost);
		Query query = manager.createQuery();
		query.updateObject(cmpActStepCost);
	}

	public void updateCmpActUserCheckflg(long actId, long userId, byte checkflg) {
		Query query = manager.createQuery();
		query.addField("checkflg", checkflg);
		query.update(CmpActUser.class, "actid=? and userid=?", new Object[] {
				actId, userId });
	}

	public List<CmpActKind> getCmpActKindList() {
		Query query = manager.createQuery();
		return query.listEx(CmpActKind.class);
	}

	public boolean createCmpActKind(CmpActKind cmpActKind) {
		CmpActKind o = this.getCmpActKindByName(cmpActKind.getName());
		if (o != null) {
			return false;
		}
		Query query = manager.createQuery();
		query.insertObject(cmpActKind);
		return true;
	}

	private CmpActKind getCmpActKindByName(String name) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpActKind.class, "name=?",
				new Object[] { name });
	}

	public boolean updateCmpActKind(CmpActKind cmpActKind) {
		CmpActKind o = this.getCmpActKindByName(cmpActKind.getName());
		if (o != null && o.getKindId() != cmpActKind.getKindId()) {
			return false;
		}
		Query query = manager.createQuery();
		query.updateObject(cmpActKind);
		return true;
	}

	public void deleteCmpActKind(long kindId) {
		Query query = manager.createQuery();
		query.deleteById(CmpActKind.class, kindId);
	}

	public CmpActKind getCmpActKind(long kindId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpActKind.class, kindId);
	}

	public List<CmpActCost> getCmpActCostListByActId(long actId) {
		Query query = manager.createQuery();
		return query.listEx(CmpActCost.class, "actid=?",
				new Object[] { actId }, "costid asc");
	}

	public List<CmpActStepCost> getCmpActStepCostListByActId(long actId) {
		Query query = manager.createQuery();
		return query.listEx(CmpActStepCost.class, "actid=?",
				new Object[] { actId }, "costid asc");
	}

	public int countCmpActByCompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.count(CmpAct.class, "companyid=?",
				new Object[] { companyId });
	}

	public List<CmpAct> getCmpActListByCompanyId(long companyId, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpAct.class, "companyid=?",
				new Object[] { companyId }, "actid desc", begin, size);
	}

	public CmpActIdData createCmpActIdData() {
		Query query = manager.createQuery();
		CmpActIdData data = new CmpActIdData();
		data.setCreateTime(new Date());
		long actId = query.insertObject(data).longValue();
		data.setActId(actId);
		return data;
	}

	public List<CmpAct> getCmpActListByCdn(long uid, String name,
			byte actStatus, int begin, int size) {
		StringBuilder sql = new StringBuilder("select * from cmpact where 1=1");
		List<Object> olist = new ArrayList<Object>();
		if (uid > 0) {
			sql.append(" and uid=?");
			olist.add(uid);
		}
		if (name != null) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		if (actStatus > -1) {
			sql.append(" and actstatus=?");
			olist.add(actStatus);
		}
		sql.append(" order by actid desc");
		Query query = manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				CmpAct.class, olist);
	}

	public List<CmpAct> getCmpActListForRun(long uid, int begin, int size) {
		Query query = manager.createQuery();
		if (uid > 0) {
			return query.listEx(CmpAct.class, "uid=?", new Object[] { uid },
					"actid desc", begin, size);
		}
		return query.listEx(CmpAct.class, "actid desc", begin, size);
	}

	public int countCmpActByCdn(long uid, String name, byte actStatus) {
		StringBuilder sql = new StringBuilder(
				"select count(*) from cmpact where 1=1");
		List<Object> olist = new ArrayList<Object>();
		if (uid > 0) {
			sql.append(" and uid=?");
			olist.add(uid);
		}
		if (name != null) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		if (actStatus > -1) {
			sql.append(" and actstatus=?");
			olist.add(actStatus);
		}
		Query query = manager.createQuery();
		return query.countBySql("ds1", sql.toString(), olist);
	}

	public int countCmpActUserByActId(long actId) {
		Query query = manager.createQuery();
		return query.count(CmpActUser.class, "actid=?", new Object[] { actId });
	}

	public CmpActUser getCmpActUserByActIdAndUserId(long actId, long userId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpActUser.class, "actid=? and userid=?",
				new Object[] { actId, userId });
	}

	public List<CmpActUser> getCmpActUserListByActId(long actId, byte checkflg,
			int begin, int size) {
		Query query = manager.createQuery();
		if (checkflg < 0) {
			return query.listEx(CmpActUser.class, "actid=?",
					new Object[] { actId }, "oid desc", begin, size);
		}
		return query.listEx(CmpActUser.class, "actid=? and checkflg=?",
				new Object[] { actId, checkflg }, "oid desc", begin, size);
	}

	public CmpActCost getCmpActCost(long costId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpActCost.class, costId);
	}

	public void updatepcityiddata() {
		ZoneService zoneService = (ZoneService) HkUtil.getBean("zoneService");
		Query query = manager.createQuery();
		List<CmpAct> list = query.listEx(CmpAct.class);
		for (CmpAct o : list) {
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
}