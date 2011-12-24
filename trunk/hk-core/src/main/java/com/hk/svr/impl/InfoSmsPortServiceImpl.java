package com.hk.svr.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.InfoSmsPort;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.InfoSmsPortService;
import com.hk.svr.user.exception.NoSmsPortException;

public class InfoSmsPortServiceImpl implements InfoSmsPortService {
	@Autowired
	private QueryManager manager;

	public void createSmsPort(InfoSmsPort smsPort) {
		Query query = this.manager.createQuery();
		query.setTable(InfoSmsPort.class);
		query.where("portnumber=?").setParam(smsPort.getPortNumber());
		if (query.count() == 0) {
			query.addField("portnumber", smsPort.getPortNumber());
			query.addField("userid", smsPort.getUserId());
			query.addField("level", smsPort.getLevel());
			query.addField("overtime", smsPort.getOverTime());
			query.addField("usetype", smsPort.getUsetype());
			query.addField("actid", smsPort.getActId());
			query.insert(InfoSmsPort.class);
		}
	}

	public void clearUserId(long portId) {
		Query query = this.manager.createQuery();
		query.setTable(InfoSmsPort.class);
		query.addField("userid", 0);
		query.where("portid=?").setParam(portId);
		query.update();
	}

	// public void createUserSmsPort(long userId, long portId, Date overTime) {
	// Query query = this.manager.createQuery();
	// query.setTable(InfoSmsPort.class);
	// query.addField("userid", userId);
	// query.addField("overtime", overTime);
	// query.where("portid=?").setParam(portId);
	// query.update();
	// }
	public InfoSmsPort getSmsPort(String portNumber) {
		Query query = this.manager.createQuery();
		query.setTable(InfoSmsPort.class);
		query.where("portnumber=?").setParam(portNumber);
		return query.getObject(InfoSmsPort.class);
	}

	public List<InfoSmsPort> getUserSmsPortList(long userId) {
		Query query = this.manager.createQuery();
		return query.listEx(InfoSmsPort.class, "userid=?",
				new Object[] { userId });
	}

	public synchronized InfoSmsPort getAvailableInfoSmsPort()
			throws NoSmsPortException {
		Query query = this.manager.createQuery();
		query.setTable(InfoSmsPort.class);
		query.where("userid=?").setParam(0);
		int count = query.count();
		int begin = DataUtil.getRandomNumber(count);
		int size = 1;
		query.setTable(InfoSmsPort.class);
		query.where("userid=?").setParam(0);
		query.orderByAsc("portid");
		List<InfoSmsPort> list = query.list(begin, size, InfoSmsPort.class);
		if (list.size() == 0) {
			throw new NoSmsPortException("no sms port");
		}
		InfoSmsPort o = list.iterator().next();
		if (o == null) {
			throw new NoSmsPortException("no sms port");
		}
		return o;
	}

	public void updateInfoSmsPort(InfoSmsPort infoSmsPort) {
		Query query = this.manager.createQuery();
		query.setTable(InfoSmsPort.class);
		query.addField("portnumber", infoSmsPort.getPortNumber());
		query.addField("userid", infoSmsPort.getUserId());
		query.addField("level", infoSmsPort.getLevel());
		query.addField("overtime", infoSmsPort.getOverTime());
		query.addField("usetype", infoSmsPort.getUsetype());
		query.addField("actid", infoSmsPort.getActId());
		query.where("portid=?").setParam(infoSmsPort.getPortId());
		query.update();
	}

	public InfoSmsPort getInfoSmsPort(long portId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(InfoSmsPort.class, portId);
	}
}