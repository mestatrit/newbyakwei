package iwant.web.admin;

import iwant.bean.Category;
import iwant.bean.Project;
import iwant.bean.validate.ProjectValidate;
import iwant.dao.ProjectSearchCdn;
import iwant.svr.CategorySvr;
import iwant.svr.ProjectSvr;
import iwant.util.ActiveTypeCreater;
import iwant.util.BackUrl;
import iwant.util.BackUrlUtil;
import iwant.web.BaseAction;
import iwant.web.admin.util.AdminUtil;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cactus.util.DataUtil;
import cactus.util.DateUtil;
import cactus.web.action.HkRequest;
import cactus.web.action.HkResponse;
import cactus.web.util.SimplePage;

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
		if (catlist.isEmpty()) {
			return "r:/mgr/cat_create.do";
		}
		Category category = catlist.get(0);
		SimplePage page = req.getSimplePage(20);
		ProjectSearchCdn projectSearchCdn = new ProjectSearchCdn();
		projectSearchCdn.setCatid(category.getCatid());
		projectSearchCdn.setName(req.getStringRow("name"));
		projectSearchCdn.setCityid(AdminUtil.getLoginCityid(req));
		projectSearchCdn.setActiveType(ActiveTypeCreater.getActiveType(req
				.getIntAndSetAttr("active_flag")));
		List<Project> list = this.projectSvr.getProjectListByCdn(
				projectSearchCdn, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setAttribute("projectSearchCdn", projectSearchCdn);
		return this.getAdminPath("project/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		if (this.isForwardPage(req)) {
			List<Category> catlist = this.categorySvr.getCategoryListForAll();
			req.setAttribute("catlist", catlist);
			Category category = catlist.get(0);
			req.setAttribute("cat", category);
			req.setAttribute("op_project", true);
			return this.getAdminPath("project/create.jsp");
		}
		Project project = new Project();
		project.setName(req.getStringRow("name"));
		project.setAddr(req.getStringRow("addr", ""));
		project.setCatid(req.getInt("catid"));
		project.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		project.setDescr(req.getString("descr", ""));
		project.setMarkerx(req.getDouble("markerx"));
		project.setMarkery(req.getDouble("markery"));
		project.setTel(req.getString("tel", ""));
		List<String> errlist = ProjectValidate.validate(project);
		if (errlist.size() > 0) {
			return this.onErrorList(req, errlist, "createerr");
		}
		this.projectSvr.createProject(project);
		this.opCreateSuccess(req);
		return this.onSuccess(req, "createok", project.getProjectid());
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
			BackUrl backUrl = BackUrlUtil.getBackUrl(req, resp);
			backUrl.push(req.getString("back_url"));
			req.setAttribute("backUrl", backUrl);
			return this.getAdminPath("project/update.jsp");
		}
		project.setName(req.getStringRow("name"));
		project.setAddr(req.getStringRow("addr", ""));
		project.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		project.setDescr(req.getString("descr", ""));
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

	public String back(HkRequest req, HkResponse resp) throws Exception {
		BackUrl backUrl = BackUrlUtil.getBackUrl(req, resp);
		String url = backUrl.getLastUrl();
		if (DataUtil.isNotEmpty(url)) {
			return "r:" + url;
		}
		return "r:/mgr/project.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delete(HkRequest req, HkResponse resp) throws Exception {
		this.projectSvr.deleteProject(req.getLong("projectid"));
		this.opDeleteSuccess(req);
		return null;
	}

	public String view(HkRequest req, HkResponse resp) throws Exception {
		Project project = this.projectSvr.getProject(req
				.getLongAndSetAttr("projectid"));
		if (project == null) {
			return null;
		}
		req.setAttribute("project", project);
		BackUrl backUrl = BackUrlUtil.getBackUrl(req, resp);
		backUrl.push(req.getString("back_url"));
		req.setAttribute("backUrl", backUrl);
		return this.getAdminPath("project/view.jsp");
	}
}