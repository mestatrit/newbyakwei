package com.hk.svr.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.CmpLink;
import com.hk.bean.Company;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpLinkService;

public class CmpLinkServiceImpl implements CmpLinkService {
	@Autowired
	private QueryManager manager;

	public void createCmpLink(CmpLink cmpLink) {
		CmpLink o = this.getCmpLink(cmpLink.getCompanyId(), cmpLink
				.getLinkCompanyId());
		if (o != null) {
			return;
		}
		Query query = this.manager.createQuery();
		query.addField("companyid", cmpLink.getCompanyId());
		query.addField("linkcompanyid", cmpLink.getLinkCompanyId());
		query.insert(CmpLink.class);
	}

	public void createCmpLink(long companyId, long linkCompanyId) {
		if (companyId == linkCompanyId) {
			return;
		}
		CmpLink cmpLink = new CmpLink();
		cmpLink.setCompanyId(companyId);
		cmpLink.setLinkCompanyId(linkCompanyId);
		this.createCmpLink(cmpLink);
	}

	public void deleteCmpLink(long companyId, long linkCompanyId) {
		Query query = this.manager.createQuery();
		query.delete(CmpLink.class, "companyid=? and linkcompanyid=?",
				new Object[] { companyId, linkCompanyId });
	}

	public CmpLink getCmpLink(long companyId, long linkCompanyId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpLink.class,
				"companyid=? and linkcompanyid=?", new Object[] { companyId,
						linkCompanyId });
	}

	public List<CmpLink> getCmpLinkList(long companyId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpLink.class, "companyid=?",
				new Object[] { companyId }, "sysid desc", begin, size);
	}

	public List<Company> getLindCompanyList(long companyId, int begin, int size) {
		Query query = this.manager.createQuery();
		String sql = "select c.* from company c,cmplink cl where c.companyid=cl.linkcompanyid and cl.companyid=? order by cl.linkId desc";
		return query.listBySql("ds1", sql, begin, size, Company.class,
				companyId);
	}
}