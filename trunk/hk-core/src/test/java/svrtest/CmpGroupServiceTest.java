package svrtest;

import com.hk.bean.CmpGroup;
import com.hk.bean.CmpGroupSmsPort;
import com.hk.svr.CmpGroupService;
import com.hk.svr.pub.Err;

public class CmpGroupServiceTest extends HkServiceTest {
	CmpGroupService cmpGroupService;

	public void setCmpGroupService(CmpGroupService cmpGroupService) {
		this.cmpGroupService = cmpGroupService;
	}

	public void test_createCmpGroup() {
		String name = "asd";
		// String name=null;
		long companyId = 1;
		byte validateflg = CmpGroup.VALIDATEFLG_N;
		CmpGroup o = new CmpGroup();
		o.setCompanyId(companyId);
		o.setName(name);
		o.setValidateflg(validateflg);
		int code = o.validate();
		if (code != Err.SUCCESS) {
			fail("code [ " + code + " ]");
		}
		if (!this.cmpGroupService.createCmpGroup(o)) {
			fail("name [ " + name + " ] duplicate");
		}
		CmpGroupSmsPort cmpGroupSmsPort = this.cmpGroupService
				.getCmpGroupSmsPort(o.getCmpgroupId());
		assertNotNull(cmpGroupSmsPort);
	}
}