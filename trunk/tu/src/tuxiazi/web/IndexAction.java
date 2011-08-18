package tuxiazi.web;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import org.springframework.stereotype.Component;

@Component("/index")
public class IndexAction extends BaseAction {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.getWebPath("index/index.jsp");
	}
}