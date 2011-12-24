package com.hk.api.action.cmp;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.bean.Company;
import com.hk.bean.CompanyKind;
import com.hk.bean.CompanyKindUtil;
import com.hk.bean.Pcity;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;

// @Component("/pubapi/cmp")
public class CompanyAction extends BaseApiAction {

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		if (o == null) {
			APIUtil.sendRespStatus(resp, "fail", Err.NOOBJECT_ERROR);
			return null;
		}
		VelocityContext context = new VelocityContext();
		CompanyKind kind = CompanyKindUtil.getCompanyKind(o.getKindId());
		context.put("o", o);
		context.put("kind", kind);
		if (o.getPcityId() > 0) {
			Pcity pcity = ZoneUtil.getPcity(o.getPcityId());
			context.put("pcity", pcity);
		}
		this.write(resp, "vm/e/company.vm", context);
		return null;
	}

	public String checkstatus(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		if (o == null) {
			APIUtil.sendRespStatus(resp, "fail", Err.NOOBJECT_ERROR);
			return null;
		}
		VelocityContext context = new VelocityContext();
		if (o.getCompanyStatus() >= Company.COMPANYSTATUS_CHECKED) {
			context.put("checkstatus", true);
		}
		else {
			context.put("checkstatus", false);
		}
		this.write(resp, "vm/e/companycheckstatus.vm", context);
		return null;
	}
}