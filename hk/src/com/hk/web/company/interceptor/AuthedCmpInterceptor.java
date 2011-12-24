package com.hk.web.company.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.Company;
import com.hk.frame.web.action.HkActionInvocation;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.interceptor.HkAbstractInterceptor;
import com.hk.svr.CompanyService;

/**
 * 拦截查看是否是认领过得企业，必须认领后才具有的功能
 * 
 * @author yuanwei
 */
public class AuthedCmpInterceptor extends HkAbstractInterceptor {
	@Autowired
	private CompanyService companyService;

	@Override
	public String doBefore(HkActionInvocation invocation) throws Exception {
		HkRequest req = invocation.getRequest();
		long companyId = req.getLong("companyId");
		if (companyId > 0) {
			Company o = this.companyService.getCompany(companyId);
			if (o == null) {
				return null;
			}
			if (o.getUserId() == 0) {
				return null;
			}
			if (o.isFreeze()) {
				req.setSessionText("func.company.cannotopforfreeze");
				return "r:/e/cmp.do?companyId=" + companyId;
			}
		}
		return super.doBefore(invocation);
	}
}