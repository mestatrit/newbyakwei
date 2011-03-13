package action;

import com.hk.frame.web.action.Action;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

public class ChildTestAction implements Action {

	private TestAction action;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.action.hello(req, resp);
	}
}