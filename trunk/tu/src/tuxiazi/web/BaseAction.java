package tuxiazi.web;

import halo.web.action.Action;
import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

public class BaseAction implements Action {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}

	protected String getWebPath(String path) {
		return "/WEB-INF/web/" + path;
	}
}