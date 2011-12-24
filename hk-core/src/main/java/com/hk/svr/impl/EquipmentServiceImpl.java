package com.hk.svr.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Equipment;
import com.hk.bean.UserCmpEnjoy;
import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquPoint;
import com.hk.bean.UserEquipment;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.EquipmentService;
import com.hk.svr.pub.EquipmentConfig;

public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	private QueryManager manager;

	public boolean useEquipmentToUser(long enjoyUserId,
			UserEquipment userEquipment) {
		long eid = userEquipment.getEid();
		Equipment equipment = EquipmentConfig.getEquipment(eid);
		if (!equipment.isForUser()) {
			return false;
		}
		UserEquEnjoy userEquEnjoy = new UserEquEnjoy();
		userEquEnjoy.setUserId(userEquipment.getUserId());
		userEquEnjoy.setEnjoyUserId(enjoyUserId);
		userEquEnjoy.setUeid(userEquipment.getOid());
		this.createUserEquEnjoy(userEquEnjoy);
		userEquipment.setEnjoyUserId(enjoyUserId);
		userEquipment.setUseflg(UserEquipment.USEFLG_Y);
		this.updateUserEquipment(userEquipment);
		return true;
	}

	public boolean useEquipmentToCmp(long companyId, UserEquipment userEquipment) {
		long eid = userEquipment.getEid();
		Equipment equipment = EquipmentConfig.getEquipment(eid);
		if (!equipment.isForCmp()) {
			return false;
		}
		UserCmpEnjoy userCmpEnjoy = new UserCmpEnjoy();
		userCmpEnjoy.setCompanyId(companyId);
		userCmpEnjoy.setUserId(userEquipment.getUserId());
		userCmpEnjoy.setUeid(userEquipment.getOid());
		userCmpEnjoy.setCreateTime(new Date());
		Query query = this.manager.createQuery();
		query.insertObject(userCmpEnjoy);
		userEquipment.setCompanyId(companyId);
		userEquipment.setUseflg(UserEquipment.USEFLG_Y);
		this.updateUserEquipment(userEquipment);
		return true;
	}

	public void createUserEquEnjoy(UserEquEnjoy userEquEnjoy) {
		Query query = this.manager.createQuery();
		if (userEquEnjoy.getCreateTime() == null) {
			userEquEnjoy.setCreateTime(new Date());
		}
		userEquEnjoy.setOid(query.insertObject(userEquEnjoy).longValue());
	}

	public void createUserEquipment(UserEquipment userEquipment) {
		Query query = this.manager.createQuery();
		long id = query.insertObject(userEquipment).longValue();
		userEquipment.setOid(id);
	}

	private void deleteUserEquEnjoy(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(UserEquEnjoy.class, oid);
	}

	private void updateUserEquipment(UserEquipment userEquipment) {
		Query query = this.manager.createQuery();
		query.updateObject(userEquipment);
	}

	public List<UserEquEnjoy> getUserEquEnjoyListByEnjoyUserId(
			long enjoyUserId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(UserEquEnjoy.class, "enjoyuserid=?",
				new Object[] { enjoyUserId }, "oid asc", begin, size);
	}

	public List<UserEquEnjoy> getUserEquEnjoyListByUserId(long userId) {
		Query query = this.manager.createQuery();
		return query.listEx(UserEquEnjoy.class, "userid=?",
				new Object[] { userId });
	}

	public UserEquipment getNotUseUserEquipmentByUserIdAndEid(long userId,
			long eid) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(UserEquipment.class,
				"userid=? and eid=? and useflg=?", new Object[] { userId, eid,
						UserEquipment.USEFLG_N });
	}

	public List<UserEquipment> getUserEquipmentListByUserId(long userId,
			byte useflg, int begin, int size) {
		Query query = this.manager.createQuery();
		if (size <= 0) {
			return query.listEx(UserEquipment.class, "userid=? and useflg=?",
					new Object[] { userId, useflg }, "oid desc");
		}
		return query.listEx(UserEquipment.class, "userid=? and useflg=?",
				new Object[] { userId, useflg }, "oid desc", begin, size);
	}

	public void destoryUserEquEnjoy(UserEquEnjoy userEquEnjoy,
			UserEquipment userEquipment) {
		if (userEquEnjoy.getForceEid() <= 0) {
			UserEquipment o = this.getUserEquipment(userEquEnjoy.getUeid());
			o.setTouchflg(UserEquipment.TOUCHFLG_Y);
			o.setUseflg(UserEquipment.USEFLG_Y);
			this.updateUserEquipment(o);
		}
		if (userEquipment != null) {
			userEquipment.setTouchflg(UserEquipment.TOUCHFLG_Y);
			userEquipment.setUseflg(UserEquipment.USEFLG_Y);
			this.updateUserEquipment(userEquipment);
		}
		this.deleteUserEquEnjoy(userEquEnjoy.getOid());
	}

	public void saveUserEquPoint(UserEquPoint userEquPoint) {
		UserEquPoint o = this.getUserEquPoint(userEquPoint.getUserId());
		Query query = this.manager.createQuery();
		if (o == null) {
			query.insertObject(userEquPoint);
		}
		else {
			query.updateObject(userEquPoint);
		}
	}

	public UserEquPoint getUserEquPoint(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserEquPoint.class, userId);
	}

	public void deleteUserEquPoint(long userId) {
		Query query = this.manager.createQuery();
		query.deleteById(UserEquPoint.class, userId);
	}

	public List<UserCmpEnjoy> getUserCmpEnjoyListByUserIdAndCompanyId(
			long userId, long companyId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(UserCmpEnjoy.class, "userid=? and companyid=?",
				new Object[] { userId, companyId }, "oid asc", begin, size);
	}

	public UserEquipment getUserEquipment(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserEquipment.class, oid);
	}

	public void destoryUserCmpEnjoy(UserCmpEnjoy userCmpEnjoy,
			UserEquipment userEquipment) {
		this.deleteUserCmpEnjoy(userCmpEnjoy.getOid());
		userEquipment.setTouchflg(UserEquipment.TOUCHFLG_Y);
		userEquipment.setUseflg(UserEquipment.USEFLG_Y);
		this.updateUserEquipment(userEquipment);
	}

	private void deleteUserCmpEnjoy(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(UserCmpEnjoy.class, oid);
	}
}