package iwant.web.api;

import iwant.bean.Project;
import iwant.svr.ProjectSvr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;
import halo.web.util.SimplePage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("/api/project")
public class ProjectAction extends BaseApiAction {

	@Autowired
	private ProjectSvr projectSvr;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long projectid = req.getLong("project");
		Project project = this.projectSvr.getProject(projectid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("project", project);
		APIUtil.writeData(resp, map, "vm/project.vm");
		return null;
	}

	public String list(HkRequest req, HkResponse resp) throws Exception {
		int page = req.getInt("page", 1);
		int size = req.getInt("size", 20);
		int did = req.getInt("did");
		if (size > 20) {
			size = 20;
		}
		SimplePage simplePage = new SimplePage(size, page);
		List<Project> plist = this.projectSvr.getProjectListByDid(did,
				simplePage.getBegin(), simplePage.getSize());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", plist);
		APIUtil.writeData(resp, map, "vm/projectlist.vm");
		return null;
	}

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