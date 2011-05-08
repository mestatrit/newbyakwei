package iwant.web.admin;

import iwant.web.BaseAction;
import iwant.web.admin.util.AdminUtil;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cactus.web.action.HkRequest;
import cactus.web.action.HkResponse;

@Lazy
@Component("/sitemgrlogout")
public class LogoutAction extends BaseAction {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		AdminUtil.clearLoginAdmin(resp);
		return "rr:/mgr";
	}
}