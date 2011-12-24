package web.epp.mgr.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpNav;
import com.hk.bean.CmpOtherWebInfo;
import com.hk.bean.CmpSvrCnf;
import com.hk.bean.CmpVideo;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpNavService;
import com.hk.svr.CmpOtherWebInfoService;
import com.hk.svr.CmpVideoService;
import com.hk.svr.processor.CmpVideoProcessor;
import com.hk.svr.pub.Err;

@Component("/epp/web/op/webadmin/cmpvideo")
public class CmpVideoAction extends EppBaseAction {

	@Autowired
	private CmpVideoService cmpVideoService;

	@Autowired
	private CmpVideoProcessor cmpVideoProcessor;

	@Autowired
	private CmpNavService cmpNavService;

	@Autowired
	private CmpOtherWebInfoService cmpOtherWebInfoService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long navoid = req.getLongAndSetAttr("navoid");
		long companyId = req.getLong("companyId");
		CmpNav cmpNav = this.cmpNavService.getCmpNav(navoid);
		if (cmpNav == null) {
			return null;
		}
		req.setAttribute("cmpNav", cmpNav);
		SimplePage page = req.getSimplePage(20);
		List<CmpVideo> list = this.cmpVideoService
				.getCmpVideoListByCompanyIdAndCmpNavOid(companyId, navoid, page
						.getBegin(), page.getSize() + 1);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmpvideo/list.jsp");
	}

	/**
	 * 上传文件
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-22
	 */
	public String create(HkRequest req, HkResponse resp) {
		CmpSvrCnf cmpSvrCnf = (CmpSvrCnf) req.getAttribute("cmpSvrCnf");
		if (cmpSvrCnf == null || !cmpSvrCnf.isOpenVideo()) {
			return null;
		}
		long navoid = req.getLongAndSetAttr("navoid");
		long companyId = req.getLong("companyId");
		CmpNav cmpNav = this.cmpNavService.getCmpNav(navoid);
		if (cmpNav == null) {
			return null;
		}
		req.setAttribute("cmpNav", cmpNav);
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/cmpvideo/create.jsp");
		}
		CmpVideo cmpVideo = new CmpVideo();
		cmpVideo.setCompanyId(companyId);
		cmpVideo.setName(req.getHtmlRow("name"));
		cmpVideo.setCmpNavOid(navoid);
		cmpVideo.setHtml(req.getString("html"));
		cmpVideo.setIntro(req.getHtml("intro"));
		int code = cmpVideo.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		try {
			code = this.cmpVideoProcessor.createCmpVideo(cmpVideo, req
					.getFile("f"));
			if (code != Err.SUCCESS) {
				if (code == Err.UPLOAD_FILE_SIZE_LIMIT) {
					return this.onError(req, code, new Object[] { "50M" },
							"createerror", null);
				}
				if (code == Err.CMPOTHERWEBINFO_NO_FILESIZE) {
					CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
							.getCmpOtherWebInfo(companyId);
					long remain = 0;
					if (cmpOtherWebInfo != null) {
						remain = cmpOtherWebInfo.getRemainFileSize();
					}
					return this.onError(req, code,
							new Object[] { (remain / 1024) + "M" },
							"createerror", null);
				}
				return this.onError(req, code, "createerror", null);
			}
			return this.onSuccess2(req, "createok", null);
		}
		catch (IOException e) {
			return this.onError(req, Err.UPLOAD_ERROR, "createerror", null);
		}
	}

	/**
	 * 更新文件
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-22
	 */
	public String update(HkRequest req, HkResponse resp) {
		CmpSvrCnf cmpSvrCnf = (CmpSvrCnf) req.getAttribute("cmpSvrCnf");
		if (cmpSvrCnf == null || !cmpSvrCnf.isOpenVideo()) {
			return null;
		}
		long navoid = req.getLongAndSetAttr("navoid");
		long companyId = req.getLong("companyId");
		long oid = req.getLongAndSetAttr("oid");
		CmpVideo cmpVideo = this.cmpVideoService.getCmpVideo(oid);
		req.setAttribute("cmpVideo", cmpVideo);
		CmpNav cmpNav = this.cmpNavService.getCmpNav(navoid);
		if (cmpNav == null) {
			return null;
		}
		req.setAttribute("cmpNav", cmpNav);
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/cmpvideo/update.jsp");
		}
		cmpVideo.setCompanyId(companyId);
		cmpVideo.setName(req.getHtmlRow("name"));
		cmpVideo.setCmpNavOid(navoid);
		cmpVideo.setHtml(req.getString("html"));
		cmpVideo.setIntro(req.getHtml("intro"));
		int code = cmpVideo.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		try {
			code = this.cmpVideoProcessor.updateCmpVideo(cmpVideo, req
					.getFile("f"));
			if (code != Err.SUCCESS) {
				if (code == Err.UPLOAD_FILE_SIZE_LIMIT) {
					return this.onError(req, code, new Object[] { "50M" },
							"updateerror", null);
				}
				if (code == Err.CMPOTHERWEBINFO_NO_FILESIZE) {
					CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
							.getCmpOtherWebInfo(companyId);
					long remain = 0;
					if (cmpOtherWebInfo != null) {
						remain = cmpOtherWebInfo.getRemainFileSize();
					}
					return this.onError(req, code,
							new Object[] { (remain / 1024) + "M" },
							"updateerror", null);
				}
				return this.onError(req, code, "updateerror", null);
			}
			return this.onSuccess2(req, "updateok", null);
		}
		catch (IOException e) {
			return this.onError(req, Err.UPLOAD_ERROR, "updateerror", null);
		}
	}

	/**
	 * 删除
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-22
	 */
	public String del(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		CmpVideo cmpVideo = this.cmpVideoService.getCmpVideo(oid);
		if (cmpVideo != null
				&& cmpVideo.getCompanyId() == req.getLong("companyId")) {
			this.cmpVideoProcessor.deleteCmpVideo(oid);
			this.setDelSuccessMsg(req);
		}
		return null;
	}
}