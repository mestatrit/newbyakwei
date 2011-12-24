package com.hk.svr.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_Admin;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.Tb_AdminService;

public class Tb_AdminServiceImpl implements Tb_AdminService {

	@Autowired
	private QueryManager manager;

	@Override
	public void createTb_Admin(Tb_Admin tbAdmin) {
		Query query = this.manager.createQuery();
		if (query.count(Tb_Admin.class, "userid=?", new Object[] { tbAdmin
				.getUserid() }) == 0) {
			query.insertObject(tbAdmin);
		}
	}

	@Override
	public void deleteTb_Admin(long oid) {
		this.manager.createQuery().deleteById(Tb_Admin.class, oid);
	}

	@Override
	public void deleteTb_AdminByUserid(long userid) {
		this.manager.createQuery().delete(Tb_Admin.class, "userid=?",
				new Object[] { userid });
	}

	@Override
	public Tb_Admin getTb_AdminByUserid(long userid) {
		return this.manager.createQuery().getObjectEx(Tb_Admin.class,
				"userid=?", new Object[] { userid });
	}

	@Override
	public void updateTb_Admin(Tb_Admin tbAdmin) {
		this.manager.createQuery().updateObject(tbAdmin);
	}
}