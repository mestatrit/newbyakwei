package com.hk.api.action.cmp;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpLinkService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;

// @Component("/pubapi/protect/cmp/mgrcmplink/mgr")
public class MgrCmpLinkAction extends BaseApiAction {

	@Autowired
	private CmpLinkService cmpLinkService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String create(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long linkCompanyId = req.getLong("linkCompanyId");
		if (HkSvrUtil.isNotCompany(companyId)
				|| HkSvrUtil.isNotCompany(linkCompanyId)) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		this.cmpLinkService.createCmpLink(companyId, linkCompanyId);
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}

	public String delete(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long linkCompanyId = req.getLong("linkCompanyId");
		this.cmpLinkService.deleteCmpLink(companyId, linkCompanyId);
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}
}