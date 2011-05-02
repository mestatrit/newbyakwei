package action;

import cactus.web.action.Action;
import cactus.web.action.HkRequest;
import cactus.web.action.HkResponse;

public class ChildTestAction implements Action {

	private TestAction action;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.action.hello(req, resp);
	}
}