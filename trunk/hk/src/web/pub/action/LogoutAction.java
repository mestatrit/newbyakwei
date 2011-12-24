package web.pub.action;

import org.springframework.stereotype.Component;
import web.pub.util.WebUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.web.util.HkWebUtil;

@Component("/epp/logout")
public class LogoutAction extends EppBaseAction {

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		HkWebUtil.setWapCookieConfig(resp);
		WebUtil.userLogout(req, resp);
		return "r:/m";
	}

	public String web(HkRequest req, HkResponse resp) throws Exception {
		HkWebUtil.setWapCookieConfig(resp);
		WebUtil.userLogout(req, resp);
		return "r:http://" + req.getServerName();
	}
}