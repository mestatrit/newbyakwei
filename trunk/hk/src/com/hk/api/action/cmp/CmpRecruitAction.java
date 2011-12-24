package com.hk.api.action.cmp;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.bean.CmpRecruit;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.pub.Err;

// @Component("/pubapi/cmprecruit")
public class CmpRecruitAction extends BaseApiAction {

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		CmpRecruit o = this.companyService.getCmpRecruit(companyId);
		if (o == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		VelocityContext context = new VelocityContext();
		context.put("o", o);
		this.write(resp, "vm/e/cmprecruit.vm", context);
		return null;
	}
}