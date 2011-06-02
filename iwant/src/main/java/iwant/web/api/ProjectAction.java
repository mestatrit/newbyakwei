package iwant.web.api;

import iwant.bean.Ppt;
import iwant.bean.PptTimeline;
import iwant.bean.Project;
import iwant.bean.User;
import iwant.bean.enumtype.ReadFlagType;
import iwant.svr.FollowProjectSvr;
import iwant.svr.PptSvr;
import iwant.svr.PptTimelineSvr;
import iwant.svr.ProjectSvr;
import iwant.svr.UserSvr;
import iwant.svr.exception.FollowProjectAlreadyExistException;
import iwant.web.admin.util.Err;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dev3g.cactus.util.DataUtil;
import com.dev3g.cactus.web.action.HkRequest;
import com.dev3g.cactus.web.action.HkResponse;

@Component("/api/project")
public class ProjectAction extends BaseApiAction {

	@Autowired
	private ProjectSvr projectSvr;

	@Autowired
	private FollowProjectSvr followProjectSvr;

	@Autowired
	private UserSvr userSvr;

	@Autowired
	private PptSvr pptSvr;

	@Autowired
	private PptTimelineSvr pptTimelineSvr;

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
			try {
				this.followProjectSvr.createFollow(user.getUserid(), projectid);
				List<Ppt> pptlist = this.pptSvr.getPptListByProjectid(
						projectid, 0, 20);
				for (Ppt o : pptlist) {
					PptTimeline pptTimeline = new PptTimeline();
					pptTimeline.setUserid(user.getUserid());
					pptTimeline.setCreatetime(new Date());
					pptTimeline.setPptid(o.getPptid());
					pptTimeline.setRead_flag(ReadFlagType.NOTREAD.getValue());
					pptTimeline.setReadtime(new Date());
					pptTimelineSvr.createPptTimeline(pptTimeline);
				}
			}
			catch (FollowProjectAlreadyExistException e) {
			}
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