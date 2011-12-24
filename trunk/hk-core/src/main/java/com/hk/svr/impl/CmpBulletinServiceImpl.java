package com.hk.svr.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.CmpBulletin;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpBulletinService;

public class CmpBulletinServiceImpl implements CmpBulletinService {
	@Autowired
	private QueryManager manager;

	public void cretaeCmpBulletin(CmpBulletin cmpBulletin) {
		cmpBulletin.setCreateTime(new Date());
		Query query = manager.createQuery();
		query.addField("companyid", cmpBulletin.getCompanyId());
		query.addField("title", cmpBulletin.getTitle());
		query.addField("content", cmpBulletin.getContent());
		query.addField("createtime", cmpBulletin.getCreateTime());
		cmpBulletin.setBulletinId(query.insert(CmpBulletin.class).intValue());
	}

	public void deleteCmpBulletin(int bulletinId) {
		Query query = manager.createQuery();
		query.deleteById(CmpBulletin.class, bulletinId);
	}

	public CmpBulletin getCmpBulletin(int bulletinId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpBulletin.class, bulletinId);
	}

	public List<CmpBulletin> getCmpBulletinList(long companyId, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpBulletin.class, "companyid=?",
				new Object[] { companyId }, "bulletinid desc", begin, size);
	}

	public void updateCmpBulletin(CmpBulletin cmpBulletin) {
		Query query = manager.createQuery();
		query.addField("companyid", cmpBulletin.getCompanyId());
		query.addField("title", cmpBulletin.getTitle());
		query.addField("content", cmpBulletin.getContent());
		query.addField("createtime", cmpBulletin.getCreateTime());
		query.update(CmpBulletin.class, "bulletinid=?",
				new Object[] { cmpBulletin.getBulletinId() });
	}
}