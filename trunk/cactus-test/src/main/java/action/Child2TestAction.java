package action;

import cactus.web.action.HkRequest;
import cactus.web.action.HkResponse;

public class Child2TestAction extends TestAction {

	private TestAction testAction;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.testAction.hello2(req, resp);
	}
}
