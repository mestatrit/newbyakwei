package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.VipUser;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.VipUserService;

public class VipUserServiceImpl implements VipUserService {
	@Autowired
	private QueryManager manager;

	public void createVipUser(VipUser vipUser) {
		Query query = this.manager.createQuery();
		long oid = query.insertObject(vipUser).longValue();
		vipUser.setOid(oid);
	}

	public void deleteVipUser(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(VipUser.class, oid);
	}

	public List<VipUser> getVipUserList(int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(VipUser.class, "oid desc", begin, size);
	}

	public VipUser getVipUserByUserId(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(VipUser.class, "userid=?",
				new Object[] { userId });
	}
}