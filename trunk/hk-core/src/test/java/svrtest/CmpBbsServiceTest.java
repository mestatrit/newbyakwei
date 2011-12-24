package svrtest;

import com.hk.bean.CmpBbs;
import com.hk.bean.CmpBbsContent;
import com.hk.svr.CmpBbsService;

public class CmpBbsServiceTest extends HkServiceTest {

	private CmpBbsService cmpBbsService;

	public void setCmpBbsService(CmpBbsService cmpBbsService) {
		this.cmpBbsService = cmpBbsService;
	}

	public void testCreateCmpBbs() {
		CmpBbs cmpBbs = new CmpBbs();
		cmpBbs.setCompanyId(1);
		CmpBbsContent cmpBbsContent = new CmpBbsContent();
		this.cmpBbsService.createCmpBbs(cmpBbs, cmpBbsContent);
		if (cmpBbs.getBbsId() <= 0) {
			fail("bbsid <= 0");
		}
	}
}