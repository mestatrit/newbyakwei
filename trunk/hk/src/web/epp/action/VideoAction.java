package web.epp.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpInfo;
import com.hk.bean.CmpNav;
import com.hk.bean.CmpVideo;
import com.hk.bean.Company;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpVideoService;

@Component("/epp/web/video")
public class VideoAction extends EppBaseAction {

	@Autowired
	private CmpVideoService cmpVideoService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		Company o = (Company) req.getAttribute("o");
		if (o.getCmpflg() == 0) {
			return this.web0(req, resp);
		}
		if (o.getCmpflg() == 1) {
			return this.web1(req, resp);
		}
		return null;
	}

	private String web0(HkRequest req, HkResponse resp) {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.web00(req, resp);
		}
		return null;
	}

	private String web1(HkRequest req, HkResponse resp) {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.web10(req, resp);
		}
		return null;
	}

	/**
	 * 模板0/0的文件列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-22
	 */
	private String web00(HkRequest req, HkResponse resp) {
		long navId = req.getLongAndSetAttr("navId");
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		CmpNav cmpNav = (CmpNav) req.getAttribute("cmpNav");
		if (cmpNav == null) {
			return null;
		}
		SimplePage page = req.getSimplePage(20);
		List<CmpVideo> list = this.cmpVideoService
				.getCmpVideoListByCompanyIdAndCmpNavOid(companyId, navId, page
						.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("cmpvideo/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-7-2
	 */
	private String web10(HkRequest req, HkResponse resp) {
		return null;
	}

	/**
	 * 观看视频
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-23
	 */
	public String view(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		CmpNav cmpNav = (CmpNav) req.getAttribute("cmpNav");
		if (cmpNav == null) {
			return null;
		}
		long navId = req.getLongAndSetAttr("navId");
		long companyId = req.getLong("companyId");
		long oid = req.getLongAndSetAttr("oid");
		CmpVideo cmpVideo = this.cmpVideoService.getCmpVideo(oid);
		req.setAttribute("cmpVideo", cmpVideo);
		List<CmpVideo> nextlist = this.cmpVideoService
				.getCmpVideoListByCompanyIdAndCmpNavOidForRange(companyId,
						navId, oid, 1, 3);
		List<CmpVideo> prelist = this.cmpVideoService
				.getCmpVideoListByCompanyIdAndCmpNavOidForRange(companyId,
						navId, oid, -1, 3);
		req.setAttribute("nextlist", nextlist);
		req.setAttribute("prelist", prelist);
		return this.getWebPath("cmpvideo/view.jsp");
	}
}