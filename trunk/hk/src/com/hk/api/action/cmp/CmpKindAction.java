package com.hk.api.action.cmp;

import java.util.List;

import org.apache.velocity.VelocityContext;

import com.hk.api.action.BaseApiAction;
import com.hk.bean.CompanyKind;
import com.hk.bean.CompanyKindUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

// @Component("/pubapi/cmpkind")
public class CmpKindAction extends BaseApiAction {

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		List<CompanyKind> list = CompanyKindUtil.getCompanKindList();
		VelocityContext context = new VelocityContext();
		context.put("list", list);
		this.write(resp, "vm/e/cmpkindlist.vm", context);
		return null;
	}
}