package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Act;
import com.hk.bean.ActSysNum;
import com.hk.bean.ActUser;
import com.hk.bean.InfoSmsPort;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.ActService;
import com.hk.svr.ActSysNumService;
import com.hk.svr.InfoSmsPortService;
import com.hk.svr.act.exception.DuplicateActNameException;
import com.hk.svr.user.exception.NoAvailableActSysNumException;
import com.hk.svr.user.exception.NoSmsPortException;

public class ActServiceImpl implements ActService {
	@Autowired
	private QueryManager manager;

	@Autowired
	private InfoSmsPortService infoSmsPortService;

	@Autowired
	private ActSysNumService actSysNumService;

	public synchronized void createAct(Act act) throws NoSmsPortException,
			DuplicateActNameException, NoAvailableActSysNumException {
		this.validateActName(act.getName());
		InfoSmsPort infoSmsPort = this.infoSmsPortService
				.getAvailableInfoSmsPort();
		ActSysNum actSysNum = this.actSysNumService.createAvailableActSysNum();
		act.setCreateTime(new Date());
		act.setPortId(infoSmsPort.getPortId());
		act.setActSysNumId(actSysNum.getSysId());
		Query query = manager.createQuery();
		query.addField("userid", act.getUserId());
		query.addField("name", act.getName());
		query.addField("begintime", act.getBeginTime());
		query.addField("endtime", act.getEndTime());
		query.addField("addr", act.getAddr());
		query.addField("intro", act.getIntro());
		query.addField("needcheck", act.getNeedCheck());
		query.addField("createtime", act.getCreateTime());
		query.addField("portid", act.getPortId());
		query.addField("actsysnumid", act.getActSysNumId());
		long id = query.insert(Act.class).longValue();
		act.setActId(id);
		// 修改活动号码的所有者和过期时间
		infoSmsPort.setUserId(act.getUserId());
		Calendar cal = Calendar.getInstance();
		cal.setTime(act.getEndTime());
		cal.add(Calendar.MONTH, 1);
		infoSmsPort.setOverTime(cal.getTime());
		infoSmsPort.setUsetype(InfoSmsPort.USETYPE_ACT);
		infoSmsPort.setActId(id);
		this.infoSmsPortService.updateInfoSmsPort(infoSmsPort);
		this.actSysNumService.updateActSysNumInfo(actSysNum.getSysId(), id,
				ActSysNum.SYSSTATUS_INUSE, ActSysNum.USETYPE_ACT, act
						.getEndTime());
		this.createActUser(id, act.getUserId(), ActUser.CHECKFLG_Y);
	}

	private Act getActByName(String name) {
		Query query = manager.createQuery();
		query.setTable(Act.class);
		query.where("name=?").setParam(name);
		return query.getObject(Act.class);
	}

	public void createActUser(long actId, long userId, byte checkflg) {
		Act act = this.getAct(actId);
		if (act == null) {
			return;
		}
		ActUser o = this.getActUser(actId, userId);
		if (o != null) {
			return;
		}
		o = new ActUser();
		o.setActId(actId);
		o.setUserId(userId);
		o.setCheckflg(checkflg);
		Query query = manager.createQuery();
		query.addField("actid", o.getActId());
		query.addField("userid", o.getUserId());
		query.addField("checkflg", o.getCheckflg());
		query.insert(ActUser.class);
	}

	public void joinAct(long actId, long userId) {
		Act act = this.getAct(actId);
		if (act == null) {
			return;
		}
		if (act.isActNeedCheck()) {
			this.createActUser(actId, userId, ActUser.CHECKFLG_N);
		}
		else {
			this.createActUser(actId, userId, ActUser.CHECKFLG_Y);
		}
	}

	public ActUser getActUser(long actId, long userId) {
		Query query = manager.createQuery();
		query.setTable(ActUser.class);
		query.where("actid=? and userid=?").setParam(actId).setParam(userId);
		return query.getObject(ActUser.class);
	}

	public void deleteActUser(long actId, long userId) {
		Query query = manager.createQuery();
		query.setTable(ActUser.class);
		query.where("actid=? and userid=?").setParam(actId).setParam(userId);
		query.delete();
	}

	public Act getAct(long actId) {
		Query query = manager.createQuery();
		return query.getObjectById(Act.class, actId);
	}

	public List<ActUser> getActUserList(long actId, int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(ActUser.class);
		query.where("actid=?").setParam(actId);
		query.orderByDesc("sysid");
		return query.list(begin, size, ActUser.class);
	}

	public List<ActUser> getActUserListForNoUser(long actId,
			List<Long> userIdList, int begin, int size) {
		if (userIdList.size() == 0) {
			return this.getActUserList(actId, begin, size);
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : userIdList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		Query query = manager.createQuery();
		String sql = "select * from actuser where actid=? and userid not in ("
				+ sb.toString() + ") order by sysid desc";
		return query.listBySql("ds1", sql, begin, size, ActUser.class, actId);
	}

	public List<ActUser> getActUserList(long actId) {
		Query query = manager.createQuery();
		return query.listEx(ActUser.class, "actid=?", new Object[] { actId },
				"sysid desc");
	}

	private void validateActName(String name) throws DuplicateActNameException {
		if (this.getActByName(name) != null) {
			throw new DuplicateActNameException("actname [ " + name
					+ " ] is already exist");
		}
	}

	public void updateAct(Act act) throws DuplicateActNameException {
		Act o = this.getAct(act.getActId());
		if (!o.getName().equals(act.getName())) {
			this.validateActName(act.getName());
		}
		Query query = manager.createQuery();
		query.setTable(Act.class);
		query.addField("userid", act.getUserId());
		query.addField("name", act.getName());
		query.addField("begintime", act.getBeginTime());
		query.addField("endtime", act.getEndTime());
		query.addField("addr", act.getAddr());
		query.addField("intro", act.getIntro());
		query.addField("needcheck", act.getNeedCheck());
		query.addField("createtime", act.getCreateTime());
		query.addField("portid", act.getPortId());
		query.addField("actsysnumid", act.getActSysNumId());
		query.where("actid=?").setParam(act.getActId());
		query.update();
		if (!act.isActNeedCheck()) {// 如果活动不需要审核，则把当前已经报名人员设置为审核通过
			query.setTable(ActUser.class);
			query.addField("checkflg", ActUser.CHECKFLG_Y);
			query.where("actid=?").setParam(act.getActId());
			query.update();
		}
	}

	public void updateActUser(ActUser actUser) {
		Query query = manager.createQuery();
		query.setTable(ActUser.class);
		query.addField("checkflg", actUser.getCheckflg());
		query.where("actid=? and userid=?").setParam(actUser.getActId())
				.setParam(actUser.getUserId());
		query.update();
	}

	public List<Act> getActListByJoinUserId(long userId, int begin, int size) {
		Query query = manager.createQuery();
		String sql = "select a.* from act a,actuser au where a.actid=au.actid and au.userid=? order by au.sysid desc";
		query.listBySql("ds1", sql, begin, size, Act.class, userId);
		return null;
	}

	public List<Long> getActIdListByJoinUserId(long userId, int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(ActUser.class).setShowFields("actid");
		query.where("userid=?").setParam(userId);
		query.orderByDesc("sysid");
		return query.list(begin, size, Long.class);
	}

	public Map<Long, Act> getActMapInId(List<Long> idList) {
		List<Act> list = this.getActListInId(idList);
		Map<Long, Act> map = new HashMap<Long, Act>();
		for (Act o : list) {
			map.put(o.getActId(), o);
		}
		return map;
	}

	public List<Act> getActListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<Act>();
		}
		Query query = manager.createQuery();
		StringBuilder sb = new StringBuilder();
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select * from act where actid in (" + sb.toString() + ")";
		return query.listBySqlEx("ds1", sql, Act.class);
	}
}