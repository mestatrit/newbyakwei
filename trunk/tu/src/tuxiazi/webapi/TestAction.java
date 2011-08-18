package tuxiazi.webapi;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import org.springframework.stereotype.Component;

@Component("/api/test")
public class TestAction extends BaseApiAction {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}
}