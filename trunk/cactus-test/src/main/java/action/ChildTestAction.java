package action;

import com.dev3g.cactus.web.action.Action;
import com.dev3g.cactus.web.action.HkRequest;
import com.dev3g.cactus.web.action.HkResponse;

public class ChildTestAction implements Action {

	private TestAction action;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.action.hello(req, resp);
	}
}