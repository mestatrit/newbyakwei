package com.hk.api.action;

import org.apache.velocity.VelocityContext;

import com.hk.api.util.APIUtil;
import com.hk.api.util.SessionKey;
import com.hk.bean.ApiUser;
import com.hk.bean.Company;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.VelocityUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.LabaParserCfg;

public abstract class BaseApiAction extends BaseAction {
	protected int getSize(HkRequest req) {
		int size = req.getInt("size");
		if (size > 20) {
			size = 20;
		}
		if (size <= 0) {
			size = 10;
		}
		return size;
	}

	protected String getUser_key(HkRequest req) {
		return req.getString("user_key");
	}

	protected void write(HkResponse resp, String vmpath, VelocityContext context) {
		try {
			resp.sendXML2(VelocityUtil.writeToString(vmpath, context));
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected ApiUser getApiUser(HkRequest req) {
		return (ApiUser) req.getAttribute(APIUtil.APIUSER_ATTR);
	}

	protected SessionKey getSessionKey(HkRequest req) {
		return APIUtil.getSessionKey(req);
	}

	protected LabaParserCfg getApiLabaParserCfg() {
		LabaParserCfg cfg = new LabaParserCfg();
		cfg.setCheckFav(true);
		cfg.setForapi(true);
		return cfg;
	}

	protected boolean checkCompanyAuthStatus(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		Company o = companyService.getCompany(companyId);
		if (o == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return false;
		}
		else if (o.getUserId() <= 0) {
			APIUtil.sendFailRespStatus(resp, Err.COMPANY_NOT_AUTH);
			return false;
		}
		return true;
	}
}