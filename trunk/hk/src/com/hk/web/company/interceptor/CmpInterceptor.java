package com.hk.web.company.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.frame.web.action.HkActionInvocation;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.interceptor.HkAbstractInterceptor;
import com.hk.svr.CompanyService;
import com.hk.web.util.HkWebUtil;

public class CmpInterceptor extends HkAbstractInterceptor {
	@Autowired
	private CompanyService companyService;

	@Override
	public String doBefore(HkActionInvocation invocation) throws Exception {
		HkRequest req = invocation.getRequest();
		User loginUser = HkWebUtil.getLoginUser(req);
		long companyId = invocation.getRequest().getLong("companyId");
		if (companyId > 0) {
			Company o = this.companyService.getCompany(companyId);
			if (o == null) {
				return null;
			}
			if (o.isFreeze()) {
				req.setSessionText("func.company.cannotopforfreeze");
				return "r:/e/cmp.do?companyId=" + companyId;
			}
			// 如果企业已经被认领或者试用
			if (o.getUserId() > 0) {
				if (loginUser.getUserId() != o.getUserId()) {
					// 如果是管理员,可以进入
					if (!HkWebUtil.isAdminUser(invocation.getRequest())) {// 不合法进入
						req
								.setSessionMessage(req
										.getText("func.when_authed_company_nopower_edit"));
						return "r:/e/cmp.do?companyId=" + companyId;
					}
				}
			}
		}
		return super.doBefore(invocation);
	}
}