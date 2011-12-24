package com.hk.api.action.cmp;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.api.util.SessionKey;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpFollowService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;

// @Component("/pubapi/protect/cmpfollow")
public class ProtectCmpFollowAction extends BaseApiAction {

	@Autowired
	private CmpFollowService cmpFollowService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String create(HkRequest req, HkResponse resp) throws Exception {
		SessionKey o = this.getSessionKey(req);
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		this.cmpFollowService.createCmpFollow(o.getUserId(), companyId);
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}

	public String delete(HkRequest req, HkResponse resp) throws Exception {
		SessionKey o = this.getSessionKey(req);
		long companyId = req.getLong("companyId");
		this.cmpFollowService.deleteCmpFollw(o.getUserId(), companyId);
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}
}