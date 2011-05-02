package action;

import org.springframework.stereotype.Component;

import cactus.web.action.Action;
import cactus.web.action.HkRequest;
import cactus.web.action.HkResponse;

@Component("/test")
public class TestAction implements Action {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		System.out.println("test action");
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String hello(HkRequest req, HkResponse resp) throws Exception {
		System.out.println("test action [ hello ]");
		return "1.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String hello2(HkRequest req, HkResponse resp) throws Exception {
		System.out.println("test action [ hello2 ]");
		return "2.jsp";
	}
}