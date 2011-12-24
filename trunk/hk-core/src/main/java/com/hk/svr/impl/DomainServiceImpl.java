package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.yuming.Domain;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.DomainService;

public class DomainServiceImpl implements DomainService {

	@Autowired
	private QueryManager manager;

	@Override
	public void createDomain(Domain domain) {
		Query query = this.manager.createQuery();
		int domainid = query.insertObject(domain).intValue();
		domain.setDomainid(domainid);
	}

	@Override
	public void deleteDomain(int domainid) {
		Query query = this.manager.createQuery();
		query.deleteById(Domain.class, domainid);
	}

	@Override
	public Domain getDomainById(int domainid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Domain.class, domainid);
	}

	@Override
	public Domain getDomainByName(String name) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(Domain.class, "name=?", new Object[] { name });
	}

	@Override
	public List<Domain> getDomainList() {
		Query query = this.manager.createQuery();
		return query.listEx(Domain.class, "domainid desc");
	}

	@Override
	public void updateDomain(Domain domain) {
		Query query = this.manager.createQuery();
		query.updateObject(domain);
	}
}