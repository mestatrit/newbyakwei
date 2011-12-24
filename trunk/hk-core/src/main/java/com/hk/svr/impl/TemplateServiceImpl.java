package com.hk.svr.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.CmpModule;
import com.hk.bean.CmpTemplate;
import com.hk.bean.Template;
import com.hk.bean.TmlModule;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.TemplateService;
import com.hk.svr.pub.TemplateUtil;

public class TemplateServiceImpl implements TemplateService {
	@Autowired
	private QueryManager manager;

	public void createCmpTemplate(long companyId, int templateId) {
		CmpTemplate cmpTemplate = this.getCmpTemplate(companyId);
		Query query = manager.createQuery();
		if (cmpTemplate == null) {
			cmpTemplate = new CmpTemplate();
			cmpTemplate.setCompanyId(companyId);
			cmpTemplate.setTemplateId(templateId);
			query.addField("companyid", cmpTemplate.getCompanyId());
			query.addField("templateid", cmpTemplate.getTemplateId());
			query.insert(CmpTemplate.class);
		}
		else {
			cmpTemplate.setTemplateId(templateId);
			query.addField("templateid", cmpTemplate.getTemplateId());
			query.update(CmpTemplate.class, "companyid=?",
					new Object[] { companyId });
		}
		this.createCmpModules(companyId, templateId);
	}

	private void createCmpModules(long companyId, int templateId) {
		this.deleteCmpModuleByCompanyId(companyId);
		Template template = TemplateUtil.geTemplate(templateId);
		List<TmlModule> mlist = template.getTmlModuleList();
		Query query = manager.createQuery();
		for (TmlModule tm : mlist) {
			CmpModule cmpModule = new CmpModule();
			cmpModule.setCompanyId(companyId);
			cmpModule.setModuleId(tm.getModuleId());
			cmpModule.setTitle(tm.getTitle());
			cmpModule.setTemplateId(templateId);
			query.addField("companyid", cmpModule.getCompanyId());
			query.addField("moduleid", cmpModule.getModuleId());
			query.addField("title", cmpModule.getTitle());
			query.addField("intro", cmpModule.getIntro());
			query.addField("templateid", cmpModule.getTemplateId());
			query.addField("showflg", cmpModule.getShowflg());
			query.insert(CmpModule.class);
		}
	}

	private void deleteCmpModuleByCompanyId(long companyId) {
		Query query = manager.createQuery();
		query
				.delete(CmpModule.class, "companyid=?",
						new Object[] { companyId });
	}

	public void updateCmpModule(CmpModule cmpModule) {
		Query query = manager.createQuery();
		query.addField("moduleid", cmpModule.getModuleId());
		query.addField("title", cmpModule.getTitle());
		query.addField("intro", cmpModule.getIntro());
		query.addField("showflg", cmpModule.getShowflg());
		query.update(CmpModule.class, "sysid=? and companyid=?", new Object[] {
				cmpModule.getSysId(), cmpModule.getCompanyId() });
	}

	public boolean createCmpModule(CmpModule cmpModule) {
		Query query = manager.createQuery();
		if (query.count(CmpModule.class, "companyid=? and moduleid=?",
				new Object[] { cmpModule.getCompanyId(),
						cmpModule.getModuleId() }) > 0) {
			return false;
		}
		query.addField("companyid", cmpModule.getCompanyId());
		query.addField("templateid", cmpModule.getTemplateId());
		query.addField("moduleid", cmpModule.getModuleId());
		query.addField("title", cmpModule.getTitle());
		query.addField("intro", cmpModule.getIntro());
		query.addField("showflg", cmpModule.getShowflg());
		long id = query.insert(CmpModule.class).longValue();
		cmpModule.setSysId(id);
		return true;
	}

	public CmpTemplate getCmpTemplate(long companyId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpTemplate.class, companyId);
	}

	public List<CmpModule> getCmpModuleList(long companyId) {
		Query query = manager.createQuery();
		return query.listEx(CmpModule.class, "companyid=?",
				new Object[] { companyId }, "sysid asc");
	}

	public List<CmpModule> getCmpModuleListIgnoreHide(long companyId) {
		Query query = manager.createQuery();
		return query.listEx(CmpModule.class, "companyid=? and showflg=?",
				new Object[] { companyId, CmpModule.SHOWFLG_Y }, "sysid asc");
	}

	public CmpModule getCmpModule(long sysId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpModule.class, sysId);
	}
}