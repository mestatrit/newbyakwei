package iwant.web.admin;

import iwant.bean.Category;
import iwant.bean.Project;
import iwant.bean.validate.ProjectValidate;
import iwant.dao.ProjectSearchCdn;
import iwant.svr.CategorySvr;
import iwant.svr.ProjectSvr;
import iwant.util.ActiveTypeCreater;
import iwant.web.BaseAction;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

/**
 * 项目管理
 * 
 * @author akwei
 */
@Component("/mgr/project")
public class ProjectAction extends BaseAction {

	@Autowired
	private CategorySvr categorySvr;

	@Autowired
	private ProjectSvr projectSvr;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("op_project", true);
		List<Category> catlist = this.categorySvr.getCategoryListForAll();
		req.setAttribute("catlist", catlist);
		Category category = catlist.get(0);
		SimplePage page = req.getSimplePage(20);
		ProjectSearchCdn projectSearchCdn = new ProjectSearchCdn();
		projectSearchCdn.setCatid(category.getCatid());
		projectSearchCdn.setName(req.getString("name"));
		projectSearchCdn.setActiveType(ActiveTypeCreater.getActiveType(req
				.getIntAndSetAttr("active_flag")));
		List<Project> list = this.projectSvr.getProjectListByCdn(
				projectSearchCdn, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setAttribute("projectSearchCdn", projectSearchCdn);
		return this.getAdminPath("project/list.jsp");
	}

	public String create(HkRequest req, HkResponse resp) throws Exception {
		if (this.isForwardPage(req)) {
			req.setAttribute("op_project", true);
			return this.getAdminPath("project/create.jsp");
		}
		Project project = new Project();
		project.setName(req.getString("name"));
		project.setAddr(req.getString("addr"));
		project.setCatid(req.getInt("catid"));
		project.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		project.setDescr(req.getString("descr"));
		project.setMarkerx(req.getDouble("markerx"));
		project.setMarkery(req.getDouble("markery"));
		project.setTel(req.getString("tel"));
		List<String> errlist = ProjectValidate.validate(project);
		if (errlist.size() > 0) {
			return this.onErrorList(req, errlist, "createerr");
		}
		this.projectSvr.createProject(project);
		this.opCreateSuccess(req);
		return this.onSuccess(req, "createok", null);
	}

	public String update(HkRequest req, HkResponse resp) throws Exception {
		Project project = this.projectSvr.getProject(req
				.getLongAndSetAttr("projectid"));
		if (project == null) {
			return null;
		}
		req.setAttribute("project", project);
		if (this.isForwardPage(req)) {
			req.setAttribute("op_project", true);
			return this.getAdminPath("project/create.jsp");
		}
		project.setName(req.getString("name"));
		project.setAddr(req.getString("addr"));
		project.setCatid(req.getInt("catid"));
		project.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		project.setDescr(req.getString("descr"));
		project.setMarkerx(req.getDouble("markerx"));
		project.setMarkery(req.getDouble("markery"));
		project.setTel(req.getString("tel"));
		List<String> errlist = ProjectValidate.validate(project);
		if (errlist.size() > 0) {
			return this.onErrorList(req, errlist, "updateerr");
		}
		this.projectSvr.updateProject(project);
		this.opUpdateSuccess(req);
		return this.onSuccess(req, "updateok", null);
	}

	public String delete(HkRequest req, HkResponse resp) throws Exception {
		this.projectSvr.deleteProject(req.getLong("projectid"));
		this.opDeleteSuccess(req);
		return null;
	}
}