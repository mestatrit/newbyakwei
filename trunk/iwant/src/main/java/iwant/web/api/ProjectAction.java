package iwant.web.api;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import org.springframework.stereotype.Component;

@Component("/api/project")
public class ProjectAction extends BaseApiAction {

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String follow(HkRequest req, HkResponse resp) {
		APIUtil.writeSuccess(resp);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String unfollow(HkRequest req, HkResponse resp) {
		APIUtil.writeSuccess(resp);
		return null;
	}
}