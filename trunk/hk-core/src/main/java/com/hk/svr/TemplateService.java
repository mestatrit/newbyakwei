package com.hk.svr;

import java.util.List;
import com.hk.bean.CmpModule;
import com.hk.bean.CmpTemplate;

public interface TemplateService {
	void createCmpTemplate(long companyId, int templateId);

	void updateCmpModule(CmpModule cmpModule);

	boolean createCmpModule(CmpModule cmpModule);

	CmpTemplate getCmpTemplate(long companyId);

	List<CmpModule> getCmpModuleList(long companyId);

	List<CmpModule> getCmpModuleListIgnoreHide(long companyId);

	CmpModule getCmpModule(long sysId);
}