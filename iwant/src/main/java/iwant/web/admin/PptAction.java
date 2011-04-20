package iwant.web.admin;

import iwant.bean.Category;
import iwant.bean.MainPpt;
import iwant.bean.Ppt;
import iwant.bean.Project;
import iwant.bean.Slide;
import iwant.bean.enumtype.ActiveType;
import iwant.bean.validate.PptValidator;
import iwant.dao.MainPptSearchCdn;
import iwant.dao.PptSearchCdn;
import iwant.svr.CategorySvr;
import iwant.svr.PptSvr;
import iwant.svr.ProjectSvr;
import iwant.util.ActiveTypeCreater;
import iwant.util.BackUrl;
import iwant.util.BackUrlUtil;
import iwant.web.BaseAction;

import java.util.ArrayList;
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

	@Autowired
	private CategorySvr categorySvr;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		Project project = this.projectSvr.getProject(req
				.getLongAndSetAttr("projectid"));
		if (project == null) {
			return null;
		}
		req.setAttribute("op_project", true);
		req.setAttribute("project", project);
		SimplePage page = req.getSimplePage(20);
		PptSearchCdn pptSearchCdn = new PptSearchCdn();
		pptSearchCdn.setName(req.getStringRow("name"));
		List<Ppt> list = this.pptSvr.getPptListByCdn(project.getProjectid(),
				pptSearchCdn, page.getBegin(), page.getSize());
		req.setAttribute("pptSearchCdn", pptSearchCdn);
		req.setAttribute("list", list);
		MainPpt mainPpt = this.pptSvr.getMainPptByProjectid(project
				.getProjectid());
		req.setAttribute("mainPpt", mainPpt);
		return this.getAdminPath("ppt/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		long projectid = req.getLongAndSetAttr("projectid");
		Project project = this.projectSvr.getProject(projectid);
		List<Ppt> list = this.pptSvr.getPptListByProjectid(req
				.getLongAndSetAttr("projectid"), 0, 0);
		if (this.isForwardPage(req)) {
			req.setAttribute("op_project", true);
			req.setAttribute("project", project);
			if (list.isEmpty()) {
				return this.getAdminPath("ppt/createmain.jsp");
			}
			return this.getAdminPath("ppt/create.jsp");
		}
		if (project == null) {
			return null;
		}
		long pptid = 0;
		if (list.size() == 0) {
			MainPpt mainPpt = new MainPpt();
			mainPpt.setCatid(project.getCatid());
			mainPpt.setActive_flag(ActiveType.ACTIVE.getValue());
			mainPpt.setName(req.getStringRow("name"));
			mainPpt.setPic_path("");
			mainPpt.setProjectid(projectid);
			mainPpt.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
			List<String> errlist = PptValidator.validateMainPpt(mainPpt);
			if (!errlist.isEmpty()) {
				return this.onErrorList(req, errlist, "createerr");
			}
			this.pptSvr.createMainPpt(mainPpt);
			pptid = mainPpt.getPptid();
		}
		else {
			Ppt ppt = new Ppt();
			ppt.setName(req.getStringRow("name"));
			ppt.setPic_path("");
			ppt.setProjectid(projectid);
			ppt.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
			List<String> errlist = PptValidator.validate(ppt);
			if (!errlist.isEmpty()) {
				return this.onErrorList(req, errlist, "createerr");
			}
			this.pptSvr.createPpt(ppt);
			pptid = ppt.getPptid();
		}
		this.opCreateSuccess(req);
		return this.onSuccess(req, "createok", pptid);
	}

	public String update(HkRequest req, HkResponse resp) throws Exception {
		Ppt ppt = this.pptSvr.getPpt(req.getLongAndSetAttr("pptid"));
		if (this.isForwardPage(req)) {
			req.setAttribute("op_project", true);
			Project project = this.projectSvr.getProject(ppt.getProjectid());
			req.setAttribute("project", project);
			req.setAttribute("ppt", ppt);
			BackUrl backUrl = BackUrlUtil.getBackUrl(req, resp);
			backUrl.push(req.getString("back_url"));
			req.setAttribute("backUrl", backUrl);
			return this.getAdminPath("ppt/update.jsp");
		}
		ppt.setName(req.getStringRow("name"));
		List<String> errlist = PptValidator.validate(ppt);
		if (!errlist.isEmpty()) {
			return this.onErrorList(req, errlist, "updateerr");
		}
		this.pptSvr.updatePpt(ppt);
		this.opUpdateSuccess(req);
		return this.onSuccess(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delete(HkRequest req, HkResponse resp) throws Exception {
		this.pptSvr.deletePpt(req.getLong("pptid"));
		this.opDeleteSuccess(req);
		return null;
	}

	public String view(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("op_project", true);
		long pptid = req.getLongAndSetAttr("pptid");
		Ppt ppt = this.pptSvr.getPpt(pptid);
		if (ppt == null) {
			return null;
		}
		req.setAttribute("ppt", ppt);
		List<Slide> list = this.pptSvr.getSlideListByPptidOrdered(pptid);
		req.setAttribute("list", list);
		BackUrl backUrl = BackUrlUtil.getBackUrl(req, resp);
		backUrl.push(req.getString("back_url"));
		req.setAttribute("backUrl", backUrl);
		req.setAttribute("canaddslide", this.pptSvr.isCanAddSlide(pptid));
		return this.getAdminPath("ppt/view.jsp");
	}

	public String back(HkRequest req, HkResponse resp) throws Exception {
		BackUrl backUrl = BackUrlUtil.getBackUrl(req, resp);
		String url = backUrl.getLastUrl();
		if (DataUtil.isNotEmpty(url)) {
			return "r:" + url;
		}
		return "r:/mgr/ppt.do?projectid=" + req.getLong("projectid");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String mainlist(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("op_mianppt", true);
		List<Category> catlist = this.categorySvr.getCategoryListForAll();
		if (catlist.isEmpty()) {
			return null;
		}
		Category category = catlist.get(0);
		int catid = category.getCatid();
		SimplePage page = req.getSimplePage(20);
		int active_flag = req.getIntAndSetAttr("active_flag");
		MainPptSearchCdn mainPptSearchCdn = new MainPptSearchCdn();
		mainPptSearchCdn.setOrder("order_flag desc");
		mainPptSearchCdn.setCatid(catid);
		mainPptSearchCdn.setName(req.getStringRow("name"));
		mainPptSearchCdn.setActiveType(ActiveTypeCreater
				.getActiveType(active_flag));
		// 此集合多包含2条数据为了方便进行移动操作，所记录的当页中首条和最后一条可换位置的pptid
		List<MainPpt> list = this.pptSvr
				.getMainPptListOrderedByCdn(mainPptSearchCdn, true, page
						.getBegin() - 1, page.getSize() + 1);
		if (!list.isEmpty()) {
			List<Long> idList = new ArrayList<Long>();
			for (MainPpt o : list) {
				idList.add(o.getPptid());
			}
			if (page.getBegin() > 0) {
				list.remove(0);
			}
			req.setAttribute("idList", idList);
		}
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setAttribute("mainPptSearchCdn", mainPptSearchCdn);
		mainPptSearchCdn.setName(null);
		int count = this.pptSvr.countMainPptByCdn(mainPptSearchCdn);
		req.setAttribute("count", count);
		req.setAttribute("page_size", page.getSize());
		return this.getAdminPath("ppt/mainlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setactive(HkRequest req, HkResponse resp) throws Exception {
		long pptid = req.getLong("pptid");
		MainPpt mainPpt = this.pptSvr.getMainPpt(pptid);
		mainPpt.setActive_flag(ActiveType.ACTIVE.getValue());
		this.pptSvr.updateMainPpt(mainPpt);
		this.opUpdateSuccess(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setnotactive(HkRequest req, HkResponse resp) throws Exception {
		long pptid = req.getLong("pptid");
		MainPpt mainPpt = this.pptSvr.getMainPpt(pptid);
		mainPpt.setActive_flag(ActiveType.NOTACTIVE.getValue());
		this.pptSvr.updateMainPpt(mainPpt);
		this.opUpdateSuccess(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String chgmainpos(HkRequest req, HkResponse resp) throws Exception {
		long pptid = req.getLong("pptid");
		long pos_pptid = req.getLong("pos_pptid");
		MainPpt mainPpt = this.pptSvr.getMainPpt(pptid);
		MainPpt pos_mainPpt = this.pptSvr.getMainPpt(pos_pptid);
		long order_flag = mainPpt.getOrder_flag();
		mainPpt.setOrder_flag(pos_mainPpt.getOrder_flag());
		pos_mainPpt.setOrder_flag(order_flag);
		this.pptSvr.updateMainPpt(mainPpt);
		this.pptSvr.updateMainPpt(pos_mainPpt);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String chgmainposfirst(HkRequest req, HkResponse resp)
			throws Exception {
		long pptid = req.getLong("pptid");
		MainPpt mainPpt = this.pptSvr.getMainPpt(pptid);
		MainPptSearchCdn mainPptSearchCdn = new MainPptSearchCdn();
		mainPptSearchCdn.setCatid(req.getInt("catid"));
		mainPptSearchCdn.setOrder("order_flag desc");
		List<MainPpt> list = this.pptSvr.getMainPptListOrderedByCdn(
				mainPptSearchCdn, false, 0, 1);
		if (list.isEmpty()) {
			return null;
		}
		MainPpt pos_mainPpt = list.get(0);
		this.chgmainpos(mainPpt, pos_mainPpt);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String chgmainposlast(HkRequest req, HkResponse resp)
			throws Exception {
		long pptid = req.getLong("pptid");
		MainPpt mainPpt = this.pptSvr.getMainPpt(pptid);
		MainPptSearchCdn mainPptSearchCdn = new MainPptSearchCdn();
		mainPptSearchCdn.setCatid(req.getInt("catid"));
		mainPptSearchCdn.setOrder("order_flag asc");
		List<MainPpt> list = this.pptSvr.getMainPptListOrderedByCdn(
				mainPptSearchCdn, false, 0, 1);
		if (list.isEmpty()) {
			return null;
		}
		MainPpt pos_mainPpt = list.get(0);
		this.chgmainpos(mainPpt, pos_mainPpt);
		return null;
	}

	private String chgmainpos(MainPpt mainPpt, MainPpt pos_mainPpt)
			throws Exception {
		long order_flag = mainPpt.getOrder_flag();
		mainPpt.setOrder_flag(pos_mainPpt.getOrder_flag());
		pos_mainPpt.setOrder_flag(order_flag);
		this.pptSvr.updateMainPpt(mainPpt);
		this.pptSvr.updateMainPpt(pos_mainPpt);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setmain(HkRequest req, HkResponse resp) throws Exception {
		long pptid = req.getLong("pptid");
		this.pptSvr.changePptToMainPpt(pptid);
		req.setSessionText("op.change_mainppt.success");
		return null;
	}
}