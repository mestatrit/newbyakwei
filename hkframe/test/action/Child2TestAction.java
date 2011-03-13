package action;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

public class Child2TestAction extends TestAction {

	private TestAction testAction;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.testAction.hello2(req, resp);
	}
}
