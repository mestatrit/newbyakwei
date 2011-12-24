package svrtest;

import java.util.List;
import com.hk.bean.Company;
import com.hk.bean.CompanyTag;
import com.hk.svr.CompanyService;
import com.hk.svr.CompanyTagService;

public class CompanyTagServiceTest extends HkServiceTest {
	private CompanyTagService companyTagService;

	private CompanyService companyService;

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setCompanyTagService(CompanyTagService companyTagService) {
		this.companyTagService = companyTagService;
	}

	public void testGetHotList() throws Exception {
		List<CompanyTag> list = this.companyTagService.getCompanyTagListForHot(
				2, 1, 0, 10);
		assertNotNull(list);
	}

	public void ttestCreateCompanyTagRef() {
		int tagId = 1;
		long companyId = 1;
		Company company = companyService.getCompany(companyId);
		this.companyTagService.createCompanyTagRef(companyId, tagId, company
				.getPcityId());
	}
}