package com.hk.web.hk4.venue.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.AuthCompany;
import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.web.pub.action.BaseAction;

/**
 * 用户进行足迹认证申请
 * 
 * @author akwei
 */
@Component("/h4/op/authcmp")
public class AuthCmpAction extends BaseAction {

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		if (this.isForwardPage(req)) {
			return this.getWeb4Jsp("authcmp/create.jsp");
		}
		User loginUser = this.getLoginUser(req);
		AuthCompany authCompany = new AuthCompany();
		authCompany.setUserId(loginUser.getUserId());
		authCompany.setCompanyId(companyId);
		authCompany.setName(req.getHtmlRow("name"));
		authCompany.setUsername(req.getHtmlRow("username"));
		authCompany.setTel(req.getHtmlRow("tel"));
		authCompany.setContent(req.getHtml("content"));
		authCompany.setCreateTime(new Date());
		authCompany.setMainStatus(AuthCompany.MAINSTATUS_UNCHECK);
		List<Integer> list = authCompany.validateList();
		if (list.size() > 0) {
			return this.onErrorList(req, list, "errlist");
		}
		this.companyService.createAuthCompany(authCompany);
		req.setSessionText("func.applyauthinfo_submit_ok");
		return this.onSuccess2(req, "createok", null);
	}
}