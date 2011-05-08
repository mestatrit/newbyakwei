package iwant.web.api;

import iwant.bean.Project;
import iwant.bean.User;
import iwant.svr.FollowProjectSvr;
import iwant.svr.ProjectSvr;
import iwant.svr.UserSvr;
import iwant.web.admin.util.Err;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cactus.util.DataUtil;
import cactus.web.action.HkRequest;
import cactus.web.action.HkResponse;

@Lazy
@Component("/api/project")
public class ProjectAction extends BaseApiAction {

	@Autowired
	private ProjectSvr projectSvr;

	@Autowired
	private FollowProjectSvr followProjectSvr;

	@Autowired
	private UserSvr userSvr;

	private Log log = LogFactory.getLog(ProjectAction.class);

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String follow(HkRequest req, HkResponse resp) {
		try {
			long projectid = req.getLong("projectid");
			String device_token = req.getStringRow("device_token");
			if (DataUtil.isEmpty(device_token)) {
				APIUtil.writeErr(resp, Err.DEVICE_TOKEN_ERR);
				return null;
			}
			User user = this.loadUser(device_token);
			Project project = this.projectSvr.getProject(projectid);
			if (project == null) {
				APIUtil.writeErr(resp, Err.PROJECT_NOT_EXIST);
				return null;
			}
			this.followProjectSvr.createFollow(user.getUserid(), projectid);
			APIUtil.writeSuccess(resp);
			return null;
		}
		catch (Exception e) {
			log.error(e.getMessage());
			APIUtil.writeErr(resp, Err.FOLLOWPROJECT_CREATE_ERR);
			return null;
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String unfollow(HkRequest req, HkResponse resp) {
		try {
			long projectid = req.getLong("projectid");
			String device_token = req.getStringRow("device_token");
			User user = this.userSvr.getUserByDevice_token(device_token);
			if (user == null) {
				APIUtil.writeErr(resp, Err.USER_NOT_EXIST);
				return null;
			}
			Project project = this.projectSvr.getProject(projectid);
			if (project == null) {
				APIUtil.writeErr(resp, Err.PROJECT_NOT_EXIST);
				return null;
			}
			this.followProjectSvr.deleteFollow(user.getUserid(), projectid);
			APIUtil.writeSuccess(resp);
			return null;
		}
		catch (Exception e) {
			log.error(e.getMessage());
			APIUtil.writeErr(resp, Err.FOLLOWPROJECT_CREATE_ERR);
			return null;
		}
	}
}