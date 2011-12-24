package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpBulletin;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpBulletinService;
import com.hk.svr.pub.Err;

@Deprecated
// @Component("/epp/mgr/bulletin")
public class Sys_DelMgrBulletinAction extends EppBaseAction {

	@Autowired
	private CmpBulletinService cmpBulletinService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		int bulletinId = req.getIntAndSetAttr("bulletinId");
		CmpBulletin cmpBulletin = this.cmpBulletinService
				.getCmpBulletin(bulletinId);
		req.setAttribute("cmpBulletin", cmpBulletin);
		return this.getMgrJspPath(req, "bulletin/bulletin.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		SimplePage page = req.getSimplePage(20);
		List<com.hk.bean.CmpBulletin> list = cmpBulletinService
				.getCmpBulletinList(companyId, page.getBegin(), page.getSize());
		this.processListForPage(page, list);
		req.setAttribute("cmpbulletinlist", list);
		return this.getMgrJspPath(req, "bulletin/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tocreate(HkRequest req, HkResponse resp) throws Exception {
		return this.getMgrJspPath(req, "bulletin/create.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		String title = req.getString("title");
		String content = req.getString("content");
		com.hk.bean.CmpBulletin cmpBulletin = new com.hk.bean.CmpBulletin();
		cmpBulletin.setCompanyId(companyId);
		cmpBulletin.setTitle(DataUtil.toHtmlRow(title));
		cmpBulletin.setContent(DataUtil.toHtml(content));
		int code = cmpBulletin.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/epp/mgr/bulletin_tocreate.do";
		}
		this.cmpBulletinService.cretaeCmpBulletin(cmpBulletin);
		req.setSessionText("func.mgrsite.bulletin.create_ok");
		return "r:/epp/mgr/bulletin.do?companyId=" + companyId + "&bulletinId="
				+ cmpBulletin.getBulletinId();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toupdate(HkRequest req, HkResponse resp) throws Exception {
		int bulletinId = req.getIntAndSetAttr("bulletinId");
		CmpBulletin cmpBulletin = (CmpBulletin) req.getAttribute("cmpBulletin");
		if (cmpBulletin == null) {
			cmpBulletin = this.cmpBulletinService.getCmpBulletin(bulletinId);
		}
		req.setAttribute("cmpBulletin", cmpBulletin);
		return this.getMgrJspPath(req, "bulletin/update.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		String title = req.getString("title");
		String content = req.getString("content");
		int bulletinId = req.getInt("bulletinId");
		com.hk.bean.CmpBulletin cmpBulletin = this.cmpBulletinService
				.getCmpBulletin(bulletinId);
		if (cmpBulletin == null) {
			return "/epp/mgr/bulletin_toupdate.do";
		}
		cmpBulletin.setContent(DataUtil.toHtml(content));
		cmpBulletin.setTitle(DataUtil.toHtmlRow(title));
		req.setAttribute("cmpBulletin", cmpBulletin);
		int code = cmpBulletin.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/epp/mgr/bulletin_toupdate.do";
		}
		this.cmpBulletinService.updateCmpBulletin(cmpBulletin);
		return "r:/epp/mgr/bulletin.do?bulletinId=" + bulletinId
				+ "&companyId=" + req.getLong("companyId");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		int bulletinId = req.getInt("bulletinId");
		com.hk.bean.CmpBulletin o = this.cmpBulletinService
				.getCmpBulletin(bulletinId);
		if (o == null || o.getCompanyId() != companyId) {
			return "r:/epp/mgr/bulletin_list.do?companyId=" + companyId;
		}
		this.cmpBulletinService.deleteCmpBulletin(bulletinId);
		req.setSessionText("func.mgrsite.bulletin.delete_ok");
		return "r:/epp/mgr/bulletin_list.do?companyId=" + companyId;
	}
}