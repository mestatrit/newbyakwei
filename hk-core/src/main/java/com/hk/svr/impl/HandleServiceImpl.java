package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.HandleCheckInUser;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.HandleService;

public class HandleServiceImpl implements HandleService {

	@Autowired
	private QueryManager manager;

	public void createHandleCheckInUser(HandleCheckInUser handleCheckInUser) {
		Query query = this.manager.createQuery();
		if (query.count(HandleCheckInUser.class, "userid=? and companyid=?",
				new Object[] { handleCheckInUser.getUserId(),
						handleCheckInUser.getCompanyId() }) == 0) {
			query.insertObject(handleCheckInUser);
		}
	}

	public void deleteHandleCheckInUser(long userId, long companyId) {
		Query query = this.manager.createQuery();
		query.delete(HandleCheckInUser.class, "userid=? and companyid=?",
				new Object[] { userId, companyId });
	}

	public List<HandleCheckInUser> getHandleCheckInUserList(int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(HandleCheckInUser.class, "oid asc", begin, size);
	}
}