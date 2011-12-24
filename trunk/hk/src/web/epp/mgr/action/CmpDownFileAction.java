package web.epp.mgr.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpDownFile;
import com.hk.bean.CmpNav;
import com.hk.bean.CmpOtherWebInfo;
import com.hk.bean.CmpSvrCnf;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpDownFileService;
import com.hk.svr.CmpNavService;
import com.hk.svr.CmpOtherWebInfoService;
import com.hk.svr.processor.CmpDownFileProcessor;
import com.hk.svr.pub.Err;

@Component("/epp/web/op/webadmin/cmpdownfile")
public class CmpDownFileAction extends EppBaseAction {

	@Autowired
	private CmpDownFileService cmpDownFileService;

	@Autowired
	private CmpDownFileProcessor cmpDownFileProcessor;

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
		List<CmpDownFile> list = this.cmpDownFileService
				.getCmpDownFileListByCompanyIdAndCmpNavOid(companyId, navoid,
						page.getBegin(), page.getSize() + 1);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmpdownfile/list.jsp");
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
		if (cmpSvrCnf == null || !cmpSvrCnf.isOpenFile()) {
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
			return this.getWebPath("admin/cmpdownfile/create.jsp");
		}
		CmpDownFile cmpDownFile = new CmpDownFile();
		cmpDownFile.setCompanyId(companyId);
		cmpDownFile.setCmpNavOid(navoid);
		cmpDownFile.setName(req.getHtmlRow("name"));
		int code = cmpDownFile.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		try {
			code = this.cmpDownFileProcessor.createCmpDownFile(cmpDownFile, req
					.getFile("f"));
			if (code != Err.SUCCESS) {
				if (code == Err.UPLOAD_FILE_SIZE_LIMIT) {
					return this.onError(req, code, new Object[] { "1M" },
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
		if (cmpSvrCnf == null || !cmpSvrCnf.isOpenFile()) {
			return null;
		}
		long navoid = req.getLongAndSetAttr("navoid");
		long companyId = req.getLong("companyId");
		long oid = req.getLongAndSetAttr("oid");
		CmpDownFile cmpDownFile = this.cmpDownFileService.getCmpDownFile(oid);
		req.setAttribute("cmpDownFile", cmpDownFile);
		CmpNav cmpNav = this.cmpNavService.getCmpNav(navoid);
		if (cmpNav == null) {
			return null;
		}
		req.setAttribute("cmpNav", cmpNav);
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/cmpdownfile/update.jsp");
		}
		cmpDownFile.setCompanyId(companyId);
		cmpDownFile.setCmpNavOid(navoid);
		cmpDownFile.setName(req.getHtmlRow("name"));
		int code = cmpDownFile.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		try {
			code = this.cmpDownFileProcessor.updateCmpDownFile(cmpDownFile, req
					.getFile("f"));
			if (code != Err.SUCCESS) {
				if (code == Err.UPLOAD_FILE_SIZE_LIMIT) {
					return this.onError(req, code, new Object[] { "1M" },
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
		CmpDownFile cmpDownFile = this.cmpDownFileService.getCmpDownFile(oid);
		if (cmpDownFile != null
				&& cmpDownFile.getCompanyId() == req.getLong("companyId")) {
			this.cmpDownFileProcessor.deleteCmpDownFile(oid);
			this.setDelSuccessMsg(req);
		}
		return null;
	}
}