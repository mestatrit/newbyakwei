package com.hk.svr.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.ActSysNum;
import com.hk.bean.ChgCardAct;
import com.hk.bean.ChgCardActUser;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.ActSysNumService;
import com.hk.svr.ChgCardActService;
import com.hk.svr.UserCardService;
import com.hk.svr.user.exception.NoAvailableActSysNumException;

public class ChgCardActServiceImpl implements ChgCardActService {
	@Autowired
	private QueryManager manager;

	@Autowired
	private UserCardService userCardService;

	@Autowired
	private ActSysNumService actSysNumService;

	public void tmpcreateSysnum(ActSysNum actSysNum) {
		Query query = this.manager.createQuery();
		query.addField("sysnum", actSysNum.getSysnum());
		query.addField("actid", actSysNum.getActId());
		query.addField("sysstatus", actSysNum.getSysstatus());
		query.addField("overtime", actSysNum.getOverTime());
		query.insert(ActSysNum.class);
	}

	public synchronized void createChgCardAct(ChgCardAct chgCardAct)
			throws NoAvailableActSysNumException {
		ActSysNum o = this.actSysNumService.createAvailableActSysNum();
		chgCardAct.setCreateTime(new Date());
		Date end = this.getEntTime(chgCardAct.getCreateTime(), chgCardAct
				.getPersistHour());
		chgCardAct.setEndTime(end);
		chgCardAct.setSysnum(o.getSysnum());
		Query query = this.manager.createQuery();
		query.addField("userid", chgCardAct.getUserId());
		query.addField("name", chgCardAct.getName());
		query.addField("persisthour", chgCardAct.getPersistHour());
		query.addField("chgflg", chgCardAct.getChgflg());
		query.addField("createtime", chgCardAct.getCreateTime());
		query.addField("endtime", chgCardAct.getEndTime());
		query.addField("actstatus", chgCardAct.getActStatus());
		query.addField("sysnum", chgCardAct.getSysnum());
		long actId = query.insert(ChgCardAct.class).longValue();// 活动创建
		chgCardAct.setActId(actId);
		// 修改随机暗号的状态和过期时间
		this.actSysNumService.updateActSysNumInfo(o.getSysId(), actId,
				ActSysNum.SYSSTATUS_INUSE, ActSysNum.USETYPE_CARD, chgCardAct
						.getEndTime());
		// 创建人自动加入活动
		this.createChgCardActUser(actId, chgCardAct.getUserId());
	}

	public List<ActSysNum> getInUseActSysNumList(int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(ActSysNum.class);
		query.where("sysstatus=? and overtime<?").setParam(
				ActSysNum.SYSSTATUS_INUSE).setParam(new Date());
		return query.list(begin, size, ActSysNum.class);
	}

	public ChgCardAct getChgCardAct(long actId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(ChgCardAct.class, actId);
	}

	public void updateChgCardAct(ChgCardAct chgCardAct) {
		Date end = this.getEntTime(chgCardAct.getCreateTime(), chgCardAct
				.getPersistHour());
		chgCardAct.setEndTime(end);
		Query query = this.manager.createQuery();
		query.setTable(ChgCardAct.class);
		query.addField("name", chgCardAct.getName());
		query.addField("userid", chgCardAct.getUserId());
		query.addField("persisthour", chgCardAct.getPersistHour());
		query.addField("chgflg", chgCardAct.getChgflg());
		query.addField("createtime", chgCardAct.getCreateTime());
		query.addField("endtime", chgCardAct.getEndTime());
		query.addField("actstatus", chgCardAct.getActStatus());
		query.where("actid=?").setParam(chgCardAct.getActId());
		query.update();
		if (!chgCardAct.isOver()) {
			// 如果没有过期,修改暗号的过期时间
			ActSysNum o = this.actSysNumService.getActSysNumBySysNum(chgCardAct
					.getSysnum());
			if (o != null) {
				this.actSysNumService.updateActSysNumInfo(o.getSysId(),
						chgCardAct.getActId(), ActSysNum.SYSSTATUS_INUSE,
						ActSysNum.USETYPE_CARD, chgCardAct.getEndTime());
			}
		}
	}

	public void createChgCardActUser(long actId, long userId) {
		Query query = this.manager.createQuery();
		if (query.count(ChgCardActUser.class, "actid=? and userid=?",
				new Object[] { actId, userId }) == 0) {
			// query.setTable(ChgCardActUser.class);
			query.addField("actid", actId);
			query.addField("userid", userId);
			query.insert(ChgCardActUser.class);
		}
		ChgCardAct o = this.getChgCardAct(actId);
		if (!o.isProtectedChange()) {// 如果是谨慎交换名片，就不到自己的名片库中写数据
			this.createAllActUserCard(actId, userId);
		}
	}

	/**
	 * 与活动中所有人交换名片
	 * 
	 * @param actId
	 * @param userId
	 */
	private void createAllActUserCard(long actId, long userId) {
		List<ChgCardActUser> list = this.getChgCardActUserList(actId);
		for (ChgCardActUser o : list) {
			if (o.getUserId() != userId) {
				this.userCardService.createMyUserCard(o.getUserId(), userId);
				this.userCardService.createMyUserCard(userId, o.getUserId());
			}
		}
	}

	public List<ChgCardActUser> getChgCardActUserList(long actId) {
		Query query = this.manager.createQuery();
		return query.listEx(ChgCardActUser.class, "actid=?",
				new Object[] { actId });
	}

	public void deleteChgCardActUser(long actId, long userId) {
		Query query = this.manager.createQuery();
		query.setTable(ChgCardActUser.class);
		query.where("actid=? and userid=?").setParam(actId).setParam(userId);
		query.delete();
	}

	private Date getEntTime(Date beginTime, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(beginTime);
		c.add(Calendar.HOUR_OF_DAY, hour);
		return c.getTime();
	}

	public List<ChgCardAct> getChgCardActListByJoinUserId(long userId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		String sql = "select a.* from chgcardact a,chgcardactuser au where au.userid=? and a.actid=au.actid order by au.sysid desc";
		return query.listBySql("ds1", sql, begin, size, ChgCardAct.class,
				userId);
	}

	public List<ChgCardActUser> getChgCardActUserList(String key, long actId,
			int begin, int size) {
		if (key == null) {
			return this.getChgCardActUserList(actId, begin, size);
		}
		String sql = "select au.* from chgcardactuser au, usercard c where au.actid=? and au.userid=c.userid and (name like ? or nickname like ?) order by au.sysid desc";
		Query query = this.manager.createQuery();
		return query.listBySql("ds1", sql, begin, size, ChgCardActUser.class,
				actId, "%" + key + "%", "%" + key + "%");
	}

	public List<ChgCardActUser> getChgCardActUserList(long actId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		query.setTable(ChgCardActUser.class);
		query.where("actid=?").setParam(actId);
		query.orderByDesc("sysid");
		return query.list(begin, size, ChgCardActUser.class);
	}

	public ChgCardActUser getChgCardActUser(long actId, long userId) {
		Query query = this.manager.createQuery();
		query.setTable(ChgCardActUser.class);
		query.where("actid=? and userid=?").setParam(actId).setParam(userId);
		return query.getObject(ChgCardActUser.class);
	}
}