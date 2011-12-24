package com.hk.api.action.cmp;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.bean.CmpTemplate;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;

// @Component("/pubapi/cmptemplate")
public class CmpTemplateAction extends BaseApiAction {

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		CmpTemplate o = this.companyService.getCmpTemplate(companyId);
		if (o == null) {
			o = new CmpTemplate();
			o.setCompanyId(companyId);
			o.setTemplateId(1);
		}
		VelocityContext context = new VelocityContext();
		context.put("o", o);
		this.write(resp, "vm/e/cmptemplate.vm", context);
		return null;
	}
}