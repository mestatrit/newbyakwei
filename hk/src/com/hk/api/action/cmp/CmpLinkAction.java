package com.hk.api.action.cmp;

import java.util.List;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.bean.Company;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpLinkService;

// @Component("/pubapi/cmplink")
public class CmpLinkAction extends BaseApiAction {

	@Autowired
	private CmpLinkService cmpLinkService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		int size = this.getSize(req);
		SimplePage page = req.getSimplePage(size);
		List<Company> list = this.cmpLinkService.getLindCompanyList(companyId,
				page.getBegin(), size);
		VelocityContext context = new VelocityContext();
		context.put("list", list);
		this.write(resp, "vm/e/cmplist.vm", context);
		return null;
	}
}