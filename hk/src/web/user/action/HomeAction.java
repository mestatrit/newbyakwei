package web.user.action;

import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/epp/home")
public class HomeAction extends EppBaseAction {

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}
}