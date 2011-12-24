package com.hk.svr;

import java.util.List;

import com.hk.bean.yuming.Domain;

public interface DomainService {

	void createDomain(Domain domain);

	void updateDomain(Domain domain);

	void deleteDomain(int domainid);

	List<Domain> getDomainList();

	Domain getDomainById(int domainid);

	Domain getDomainByName(String name);
}