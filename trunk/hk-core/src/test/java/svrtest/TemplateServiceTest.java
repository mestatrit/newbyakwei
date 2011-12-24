package svrtest;

import java.util.List;
import com.hk.bean.CmpModule;
import com.hk.bean.CmpTemplate;
import com.hk.svr.TemplateService;

public class TemplateServiceTest extends HkServiceTest {
	private TemplateService templateService;

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public void testCreate() {
		long companyId = 1;
		int templateId = 1;
		this.templateService.createCmpTemplate(companyId, templateId);
		CmpTemplate cmpTemplate = this.templateService
				.getCmpTemplate(companyId);
		assertNotNull(cmpTemplate);
		assertEquals(companyId, cmpTemplate.getCompanyId());
		assertEquals(templateId, cmpTemplate.getTemplateId());
		List<CmpModule> list = this.templateService.getCmpModuleList(companyId);
		for (CmpModule o : list) {
			System.out.println(o.getSysId() + " | " + o.getCompanyId() + " | "
					+ o.getModuleId() + " | " + o.getTitle());
		}
		this.commit();
	}
}