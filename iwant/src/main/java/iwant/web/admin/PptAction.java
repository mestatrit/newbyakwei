package iwant.web.admin;

import iwant.bean.MainPpt;
import iwant.bean.Ppt;
import iwant.bean.Project;
import iwant.bean.enumtype.ActiveType;
import iwant.bean.validate.PptValidator;
import iwant.svr.PptSvr;
import iwant.svr.ProjectSvr;
import iwant.web.BaseAction;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/mgr/ppt")
public class PptAction extends BaseAction {

	@Autowired
	private ProjectSvr projectSvr;

	@Autowired
	private PptSvr pptSvr;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(20);
		List<Ppt> list = this.pptSvr.getPptListByProjectid(req
				.getLongAndSetAttr("projectid"), page.getBegin(), page
				.getSize());
		req.setAttribute("list", list);
		return this.getAdminPath("ppt/list.jsp");
	}

	public String create(HkRequest req, HkResponse resp) throws Exception {
		long projectid = req.getLongAndSetAttr("projectid");
		Project project = this.projectSvr.getProject(projectid);
		if (this.isForwardPage(req)) {
			req.setAttribute("project", project);
			return this.getAdminPath("ppt/create.jsp");
		}
		List<Ppt> list = this.pptSvr.getPptListByProjectid(req
				.getLongAndSetAttr("projectid"), 0, 0);
		if (project == null) {
			return null;
		}
		if (list.size() == 0) {
			MainPpt mainPpt = new MainPpt();
			mainPpt.setCatid(project.getCatid());
			mainPpt.setActive_flag(ActiveType.ACTIVE.getValue());
			mainPpt.setName(req.getString("name"));
			mainPpt.setProjectid(projectid);
			mainPpt.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
			List<String> errlist = PptValidator.validateMainPpt(mainPpt);
			if (!errlist.isEmpty()) {
				return this.onErrorList(req, errlist, "createerr");
			}
			this.pptSvr.createMainPpt(mainPpt);
		}
		else {
			Ppt ppt = new Ppt();
			ppt.setName(req.getString("name"));
			ppt.setProjectid(projectid);
			ppt.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
			List<String> errlist = PptValidator.validate(ppt);
			if (!errlist.isEmpty()) {
				return this.onErrorList(req, errlist, "createerr");
			}
			this.pptSvr.createPpt(ppt);
		}
		this.opCreateSuccess(req);
		return this.onSuccess(req, "createok", null);
	}

	public String update(HkRequest req, HkResponse resp) throws Exception {
		Ppt ppt = this.pptSvr.getPpt(req.getLongAndSetAttr("pptid"));
		if (this.isForwardPage(req)) {
			req.setAttribute("ppt", ppt);
			return this.getAdminPath("ppt/update.jsp");
		}
		ppt.setName(req.getString("name"));
		ppt.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		List<String> errlist = PptValidator.validate(ppt);
		if (!errlist.isEmpty()) {
			return this.onErrorList(req, errlist, "updateerr");
		}
		this.pptSvr.updatePpt(ppt);
		this.opUpdateSuccess(req);
		return this.onSuccess(req, "updateok", null);
	}

	public String delete(HkRequest req, HkResponse resp) throws Exception {
		this.pptSvr.deletePpt(req.getLong("pptid"));
		this.opDeleteSuccess(req);
		return null;
	}
}