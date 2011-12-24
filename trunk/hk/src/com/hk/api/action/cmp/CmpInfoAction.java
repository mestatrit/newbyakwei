package com.hk.api.action.cmp;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.bean.CmpInfo;
import com.hk.bean.CmpUnion;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpInfoService;
import com.hk.svr.CmpUnionService;

// @Component("/pubapi/cmpinfo")
public class CmpInfoAction extends BaseApiAction {

	@Autowired
	private CmpInfoService cmpInfoService;

	@Autowired
	private CmpUnionService cmpUnionService;

	public String cmprequestinfo(HkRequest req, HkResponse resp)
			throws Exception {
		String domain = req.getString("domain");
		CmpInfo cmpInfo = this.cmpInfoService.getCmpInfoByDomain(domain);
		VelocityContext context = new VelocityContext();
		if (cmpInfo == null) {
			CmpUnion cmpUnoin = this.cmpUnionService
					.getCmpUnionByDomain(domain);
			if (cmpUnoin != null) {
				context.put("cmpUnion", cmpUnoin);
			}
		}
		else {
			context.put("cmpInfo", cmpInfo);
		}
		this.write(resp, "vm/e/cmprequestinfo.vm", context);
		return null;
	}

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		CmpInfo o = null;
		if (companyId > 0) {
			o = this.cmpInfoService.getCmpInfo(companyId);
		}
		else {
			String domain = req.getString("domain");
			o = this.cmpInfoService.getCmpInfoByDomain(domain);
		}
		// if (o == null) {
		// o = new CmpInfo();
		// o.setCompanyId(companyId);
		// }
		VelocityContext context = new VelocityContext();
		context.put("o", o);
		this.write(resp, "vm/e/cmpinfo.vm", context);
		return null;
	}
}