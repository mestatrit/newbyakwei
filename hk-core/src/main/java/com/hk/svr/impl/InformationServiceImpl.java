package com.hk.svr.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.InfoSmsPort;
import com.hk.bean.Information;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.InfoSmsPortService;
import com.hk.svr.InformationService;
import com.hk.svr.user.exception.NoSmsPortException;

public class InformationServiceImpl implements InformationService {
	@Autowired
	private QueryManager manager;

	@Autowired
	private InfoSmsPortService infoSmsPortService;

	public synchronized void createInformation(Information information,
			int month) throws NoSmsPortException {
		InfoSmsPort o = this.infoSmsPortService.getAvailableInfoSmsPort();
		Date date = new Date();
		information.setPortId(o.getPortId());
		information.setCreateTime(date);
		information.setBeginTime(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		Date endTime = cal.getTime();
		information.setEndTime(endTime);
		Query query = this.manager.createQuery();
		query.addField("userid", information.getUserId());
		query.addField("portid", information.getPortId());
		query.addField("name", information.getName());
		query.addField("tag", information.getTag());
		query.addField("createtime", information.getCreateTime());
		query.addField("usestatus", information.getUseStatus());
		query.addField("begintime", information.getBeginTime());
		query.addField("endtime", information.getEndTime());
		query.addField("intro", information.getIntro());
		long id = query.insert(Information.class).longValue();
		information.setInfoId(id);
		// 修改信息台号码的所有者和过期时间
		o.setUserId(information.getUserId());
		o.setOverTime(endTime);
		this.infoSmsPortService.updateInfoSmsPort(o);
	}

	public Information getInformation(long infoId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Information.class, infoId);
	}

	public Information getInformationByPortId(long portId) {
		Query query = this.manager.createQuery();
		query.setTable(Information.class);
		query.where("portid=?").setParam(portId);
		return query.getObject(Information.class);
	}

	public List<Information> getInformationList(long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(Information.class);
		query.where("userid=?").setParam(userId);
		return query.list(begin, size, Information.class);
	}

	public void changeUseStatus(long infoId, byte useStatus) {
		Query query = this.manager.createQuery();
		query.setTable(Information.class);
		query.addField("usestatus", useStatus);
		query.where("infoid=?").setParam(infoId);
		query.update();
	}

	public List<Information> getInformationListForEndTime(Date minTime,
			Date maxTime, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(Information.class);
		query.where("endtime>=? and endtime<=?").setParam(minTime).setParam(
				maxTime);
		return query.list(begin, size, Information.class);
	}

	public void updateInformation(Information information) {
		Query query = this.manager.createQuery();
		query.setTable(Information.class);
		query.addField("userid", information.getUserId());
		query.addField("portid", information.getPortId());
		query.addField("name", information.getName());
		query.addField("tag", information.getTag());
		query.addField("createtime", information.getCreateTime());
		query.addField("usestatus", information.getUseStatus());
		query.addField("begintime", information.getBeginTime());
		query.addField("endtime", information.getEndTime());
		query.addField("intro", information.getIntro());
		query.where("infoid=?").setParam(information.getPortId());
		query.update();
	}
}