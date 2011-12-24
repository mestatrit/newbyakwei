package com.hk.web.company.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.Company;
import com.hk.frame.web.action.HkActionInvocation;
import com.hk.frame.web.interceptor.HkAbstractInterceptor;
import com.hk.svr.CompanyService;

public class CmpStatusCheckSuccessInterceptor extends HkAbstractInterceptor {
	@Autowired
	private CompanyService companyService;

	@Override
	public String doBefore(HkActionInvocation invocation) throws Exception {
		long companyId = invocation.getRequest().getLong("companyId");
		if (companyId > 0) {
			Company o = this.companyService.getCompany(companyId);
			if (o == null) {
				return null;
			}
			if (!o.isCheckSuccess()) {
				return null;
			}
		}
		return super.doBefore(invocation);
	}
}