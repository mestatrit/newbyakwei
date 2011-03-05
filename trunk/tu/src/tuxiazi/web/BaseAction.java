package tuxiazi.web;

import com.hk.frame.web.action.Action;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

public class BaseAction implements Action {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}

	protected String getWebPath(String path) {
		return "/WEB-INF/web/" + path;
	}
}