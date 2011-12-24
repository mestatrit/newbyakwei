package com.hk.svr.impl;

import java.text.DecimalFormat;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.UserSmsPort;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.UserSmsPortService;

public class UserSmsPortServiceImpl implements UserSmsPortService {
	@Autowired
	private QueryManager manager;

	public void batchCreateUserSmsPort(int portLen, int size) {
		UserSmsPort o = this.getLast();
		int nbegin = 100001;
		if (o != null) {
			int n = Integer.parseInt(o.getPort()) + 1;
			if (nbegin < n) {
				nbegin = n;
			}
		}
		int end = nbegin + size;
		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < portLen; k++) {
			sb.append("0");
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		for (int i = nbegin; i < end; i++) {
			if (String.valueOf(nbegin).length() > portLen) {
				break;
			}
			String port = df.format(i);
			UserSmsPort userSmsPort = new UserSmsPort();
			userSmsPort.setPort(port);
			this.create(userSmsPort);
		}
	}

	private void create(UserSmsPort userSmsPort) {
		Query query = manager.createQuery();
		query.addField("port", userSmsPort.getPort());
		query.addField("userid", userSmsPort.getUserId());
		query.insert(UserSmsPort.class);
	}

	private UserSmsPort getLast() {
		Query query = this.manager.createQuery();
		query.setTable(UserSmsPort.class);
		query.orderByDesc("sysid");
		return query.getObject(UserSmsPort.class);
	}

	public synchronized UserSmsPort makeAvailableUserSmsPort(long userId) {
		UserSmsPort o2 = this.getUserSmsPortByUserId(userId);
		if (o2 != null) {
			return o2;
		}
		Query query = manager.createQuery();
		query.setTable(UserSmsPort.class);
		query.where("userid=?").setParam(0);
		query.orderByAsc("sysid");
		UserSmsPort o = query.getObject(UserSmsPort.class);
		o.setUserId(userId);
		this.updateUserSmsPort(o);
		return o;
	}

	public void updateUserSmsPort(UserSmsPort userSmsPort) {
		Query query = manager.createQuery();
		query.setTable(UserSmsPort.class);
		query.addField("port", userSmsPort.getPort());
		query.addField("userid", userSmsPort.getUserId());
		query.where("sysid=?").setParam(userSmsPort.getSysId());
		query.update();
	}

	public UserSmsPort getUserSmsPortByUserId(long userId) {
		Query query = manager.createQuery();
		query.setTable(UserSmsPort.class);
		query.where("userid=?").setParam(userId);
		return query.getObject(UserSmsPort.class);
	}

	public UserSmsPort getUserSmsPortByPort(String port) {
		Query query = manager.createQuery();
		query.setTable(UserSmsPort.class);
		query.where("port=?").setParam(port);
		return query.getObject(UserSmsPort.class);
	}
}