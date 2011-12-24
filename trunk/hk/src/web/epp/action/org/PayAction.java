package web.epp.action.org;

import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/epp/web/org/pay")
public class PayAction extends EppBaseAction {

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		this.loadOrgInfo(req);
		return this.getWebPath("mod/2/0/org/pay/pay.jsp");
	}
}