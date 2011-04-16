package iwant.web.admin;

import iwant.web.BaseAction;
import iwant.web.admin.util.AdminUtil;

import org.springframework.stereotype.Component;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/sitemgrlogout")
public class LogoutAction extends BaseAction {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		AdminUtil.clearLoginAdmin(resp);
		return "rr:/mgr";
	}
}