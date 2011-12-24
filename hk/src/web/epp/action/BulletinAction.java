package web.epp.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpBulletin;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpBulletinService;

@Component("/epp/bulletin")
public class BulletinAction extends EppBaseAction {

	@Autowired
	private CmpBulletinService cmpBulletinService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		int bulletinId = req.getIntAndSetAttr("bulletinId");
		CmpBulletin cmpBulletin = this.cmpBulletinService
				.getCmpBulletin(bulletinId);
		req.setAttribute("cmpBulletin", cmpBulletin);
		return this.getWapPath(req, "bulletin/bulletin.jsp");
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
		List<CmpBulletin> list = cmpBulletinService.getCmpBulletinList(
				companyId, page.getBegin(), page.getSize());
		this.processListForPage(page, list);
		req.setAttribute("cmpbulletinlist", list);
		return this.getWapPath(req, "bulletin/list.jsp");
	}
}