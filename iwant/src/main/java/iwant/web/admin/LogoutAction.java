package iwant.web.admin;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;
import iwant.web.BaseAction;
import iwant.web.admin.util.AdminUtil;

import org.springframework.stereotype.Component;

@Component("/sitemgrlogout")
public class LogoutAction extends BaseAction {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		AdminUtil.clearLoginAdminUser(req, resp);
		return "r:/sitemgrlogin.do";
	}
}