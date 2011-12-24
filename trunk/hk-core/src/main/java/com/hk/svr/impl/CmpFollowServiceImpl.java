package com.hk.svr.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.CmpFollow;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpFollowService;

public class CmpFollowServiceImpl implements CmpFollowService {
	@Autowired
	private QueryManager manager;

	public void createCmpFollow(long userId, long companyId) {
		if (this.getCmpFollow(userId, companyId) == null) {
			Query query = this.manager.createQuery();
			query.addField("userid", userId);
			query.addField("companyid", companyId);
			query.insert(CmpFollow.class);
		}
	}

	public void deleteCmpFollw(long userId, long companyId) {
		Query query = this.manager.createQuery();
		query.delete(CmpFollow.class, "userid=? and companyid=?", new Object[] {
				userId, companyId });
	}

	public List<CmpFollow> getCmpFollowListByCompanyId(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpFollow.class, "companyid=?",
				new Object[] { companyId }, "sysid desc", begin, size);
	}

	public List<CmpFollow> getCmpFollowListByUserId(long userId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpFollow.class, "userId=?",
				new Object[] { userId }, "sysid desc", begin, size);
	}

	public CmpFollow getCmpFollow(long userId, long companyId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpFollow.class, "userid=? and companyid=?",
				new Object[] { userId, companyId });
	}
}