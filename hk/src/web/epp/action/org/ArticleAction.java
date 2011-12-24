package web.epp.action.org;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpFile;
import com.hk.bean.CmpNav;
import com.hk.bean.CmpOrgArticle;
import com.hk.bean.CmpOrgArticleContent;
import com.hk.bean.CmpOrgFile;
import com.hk.bean.CmpOrgNav;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpOrgArticleService;
import com.hk.svr.CmpOrgFileService;
import com.hk.svr.processor.CmpOrgArticleProcessor;
import com.hk.svr.processor.CmpOrgArticleUploadFileResult;
import com.hk.svr.pub.Err;

@Component("/epp/web/org/article")
public class ArticleAction extends EppBaseAction {

	@Autowired
	private CmpOrgArticleService cmpOrgArticleService;

	@Autowired
	private CmpOrgFileService cmpOrgFileService;

	@Autowired
	private CmpOrgArticleProcessor cmpOrgArticleProcessor;

	/**
	 * 文章那个列表页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-8
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		this.loadOrgInfo(req);
		long orgId = req.getLongAndSetAttr("orgId");
		long companyId = req.getLong("companyId");
		long navId = req.getLong("orgnavId");
		int listflg = req.getIntAndSetAttr("listflg");
		CmpOrgNav cmpOrgNav = (CmpOrgNav) req.getAttribute("cmpOrgNav");
		if (cmpOrgNav == null) {
			return null;
		}
		PageSupport page = null;
		if (listflg == 0 && cmpOrgNav.isArticleListWithImgShow()) {
			page = req.getPageSupport(20);
		}
		else {
			page = req.getPageSupport(20);
		}
		page.setTotalCount(this.cmpOrgArticleService
				.countCmpOrgArticleByCompanyIdAndNavId(companyId, navId, null));
		List<CmpOrgArticle> list = this.cmpOrgArticleService
				.getCmpOrgArticleListByCompanyIdAndNavId(companyId, navId,
						null, page.getBegin(), page.getSize());
		// 如果栏目为单页模式，就到文章单页
		if (cmpOrgNav.getReffunc() == CmpNav.REFFUNC_SINGLECONTENT) {
			if (list.size() == 0) {// 如果没有内容，就到没有内的页面
				return this.getWebPath("mod/2/0/org/article/list.jsp");
			}
			return "r:/edu/" + companyId + "/" + orgId + "/article/" + navId
					+ "/" + list.get(0).getOid() + ".html";
		}
		req.setAttribute("list", list);
		return this.getWebPath("mod/2/0/org/article/list.jsp");
	}

	/**
	 * 文章那个列表页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-8
	 */
	public String view(HkRequest req, HkResponse resp) throws Exception {
		this.loadOrgInfo(req);
		long oid = req.getLongAndSetAttr("oid");
		long orgId = req.getLong("orgId");
		long navId = req.getLong("orgnavId");
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		CmpOrgArticle article = this.cmpOrgArticleService.getCmpOrgArticle(
				companyId, oid);
		if (article == null) {
			return null;
		}
		req.setAttribute("article", article);
		CmpOrgArticleContent articleContent = this.cmpOrgArticleService
				.getCmpOrgArticleContent(companyId, oid);
		req.setAttribute("articleContent", articleContent);
		List<CmpOrgFile> list = this.cmpOrgFileService
				.getCmpOrgFileListByCompanyIdAndArticleOid(companyId, oid);
		CmpOrgFile topFile = null;
		for (int i = 0; i < list.size(); i++) {
			CmpOrgFile o = list.get(i);
			if (o.getTopflg() == CmpOrgFile.TOPFLG_Y) {
				topFile = list.remove(i);
				break;
			}
		}
		List<CmpOrgArticle> nextlist = this.cmpOrgArticleService
				.getCmpOrgArticleListByCompanyIdForNext(companyId, orgId,
						navId, oid, article.getOrderflg(), 1);
		req.setAttribute("nextlist", nextlist);
		req.setAttribute("topFile", topFile);
		req.setAttribute("list", list);
		return this.getWebPath("mod/2/0/org/article/view.jsp");
	}

	/**
	 * 创建文章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String create(HkRequest req, HkResponse resp) {
		this.loadOrgInfo(req);
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		long companyId = req.getLong("companyId");
		long navId = req.getLong("orgnavId");
		CmpOrgNav cmpOrgNav = (CmpOrgNav) req.getAttribute("cmpOrgNav");
		if (this.isForwardPage(req)) {
			return this.getWebPath("mod/2/0/org/article/create.jsp");
		}
		if (cmpOrgNav.getReffunc() == CmpNav.REFFUNC_SINGLECONTENT) {
			List<CmpOrgArticle> list = this.cmpOrgArticleService
					.getCmpOrgArticleListByCompanyIdAndNavId(companyId, navId,
							null, 0, 1);
			if (list.size() == 1) {
				return null;
			}
		}
		CmpOrgArticle article = new CmpOrgArticle();
		article.setCompanyId(companyId);
		article.setNavId(navId);
		article.setTitle(req.getHtmlRow("title"));
		article.setHideTitleflg(req.getByte("hideTitleflg"));
		article.setOrgId(req.getLong("orgId"));
		CmpOrgArticleContent articleContent = new CmpOrgArticleContent();
		articleContent.setContent(req.getHtmlWithoutBeginTrim("content"));
		int code = article.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		code = articleContent.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		int topIdx = req.getInt("topIdx", -1);
		try {
			CmpOrgArticleUploadFileResult cmpArticleUploadFileResult = this.cmpOrgArticleProcessor
					.createCmpOrgArticle(article, articleContent, req
							.getFiles(), topIdx);
			if (cmpArticleUploadFileResult != null) {
				StringBuilder sb = new StringBuilder();
				if (cmpArticleUploadFileResult.getFmtErrorNum() > 0) {
					sb.append(req.getText(String
							.valueOf(Err.IMG_UPLOAD_FMT_ERROR_NUM),
							cmpArticleUploadFileResult.getFmtErrorNum()));
					sb.append("<br/>");
				}
				if (cmpArticleUploadFileResult.getOutOfSizeErrorNum() > 0) {
					sb.append(req.getText(String
							.valueOf(Err.IMG_UPLOAD_OUTOFSIZE_ERROR_NUM),
							cmpArticleUploadFileResult.getOutOfSizeErrorNum(),
							2));
				}
				if (cmpArticleUploadFileResult.getFmtErrorNum() > 0
						|| cmpArticleUploadFileResult.getOutOfSizeErrorNum() > 0) {
					req.setSessionMessage(sb.toString());
				}
			}
			else {
				this.setOpFuncSuccessMsg(req);
			}
		}
		catch (Exception e) {// 文章保存，文件请重新上传
			req.setSessionText(String.valueOf(Err.UPLOAD_ERROR2));
			return this.onError(req, code, "createerror2", article.getOid());
		}
		return this.onSuccess2(req, "createok", article.getOid());
	}

	/**
	 * 修改文章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String update(HkRequest req, HkResponse resp) {
		this.loadOrgInfo(req);
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		long companyId = req.getLong("companyId");
		long oid = req.getLongAndSetAttr("oid");
		int ch = req.getInt("ch");
		CmpOrgArticle cmpArticle = this.cmpOrgArticleService.getCmpOrgArticle(
				companyId, oid);
		if (cmpArticle == null) {
			return null;
		}
		req.setAttribute("cmpOrgArticle", cmpArticle);
		CmpOrgArticleContent cmpArticleContent = this.cmpOrgArticleService
				.getCmpOrgArticleContent(companyId, oid);
		req.setAttribute("cmpOrgArticleContent", cmpArticleContent);
		if (ch == 0) {
			List<CmpOrgFile> list = this.cmpOrgFileService
					.getCmpOrgFileListByCompanyIdAndArticleOid(companyId, oid);
			req.setAttribute("list", list);
			return this.getWebPath("mod/2/0/org/article/update.jsp");
		}
		cmpArticle.setTitle(req.getHtmlRow("title"));
		cmpArticleContent.setContent(req.getHtmlWithoutBeginTrim("content"));
		cmpArticle.setHideTitleflg(req.getByte("hideTitleflg"));
		int code = cmpArticle.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		code = cmpArticleContent.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		int topIdx = req.getInt("topIdx", -1);
		try {
			CmpOrgArticleUploadFileResult cmpOrgArticleUploadFileResult = this.cmpOrgArticleProcessor
					.updateCmpOrgArticle(cmpArticle, cmpArticleContent, req
							.getFiles(), topIdx);
			if (cmpOrgArticleUploadFileResult != null) {
				StringBuilder sb = new StringBuilder();
				if (cmpOrgArticleUploadFileResult.getFmtErrorNum() > 0) {
					sb.append(req.getText(String
							.valueOf(Err.IMG_UPLOAD_FMT_ERROR_NUM),
							cmpOrgArticleUploadFileResult.getFmtErrorNum()));
					sb.append("<br/>");
				}
				if (cmpOrgArticleUploadFileResult.getOutOfSizeErrorNum() > 0) {
					sb.append(req.getText(String
							.valueOf(Err.IMG_UPLOAD_OUTOFSIZE_ERROR_NUM),
							cmpOrgArticleUploadFileResult
									.getOutOfSizeErrorNum(), 2));
				}
				if (cmpOrgArticleUploadFileResult.getFmtErrorNum() > 0
						|| cmpOrgArticleUploadFileResult.getOutOfSizeErrorNum() > 0) {
					req.setSessionMessage(sb.toString());
				}
			}
			else {
				this.setOpFuncSuccessMsg(req);
			}
		}
		catch (Exception e) {// 文章保存，文件请重新上传
			req.setSessionText(String.valueOf(Err.UPLOAD_ERROR2));
		}
		return this.onSuccess2(req, "updateok", cmpArticle.getOid());
	}

	/**
	 * 删除文章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		long oid = req.getLongAndSetAttr("oid");
		long companyId = req.getLong("companyId");
		long navId = req.getLong("orgnavId");
		CmpOrgArticle cmpOrgArticle = this.cmpOrgArticleService
				.getCmpOrgArticle(companyId, oid);
		if (cmpOrgArticle == null || cmpOrgArticle.getCompanyId() != companyId
				&& cmpOrgArticle.getNavId() != navId) {
			return null;
		}
		this.cmpOrgArticleProcessor.deleteCmpArticle(companyId, oid);
		req.setSessionText("view2.data.delete.ok");
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * 删除文章中的某个文件
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String delfile(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		long cmpArticleOid = req.getLongAndSetAttr("cmpArticleOid");
		long cmpFileOid = req.getLong("cmpFileOid");
		long companyId = req.getLong("companyId");
		this.cmpOrgArticleProcessor.deleteCmpArticleFile(companyId,
				cmpArticleOid, cmpFileOid);
		return null;
	}

	/**
	 * 设置图片文件为文章头图
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String prvsetmain(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		long cmpArticleOid = req.getLongAndSetAttr("cmpArticleOid");
		long cmpFileOid = req.getLong("cmpFileOid");
		long companyId = req.getLong("companyId");
		this.cmpOrgArticleProcessor.setMainFile(companyId, cmpArticleOid,
				cmpFileOid);
		return null;
	}

	/**
	 * 取消图片文件为文章头图
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String prvcancelmain(HkRequest req, HkResponse resp)
			throws Exception {
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		long cmpArticleOid = req.getLongAndSetAttr("cmpArticleOid");
		long companyId = req.getLong("companyId");
		CmpOrgArticle cmpOrgArticle = this.cmpOrgArticleService
				.getCmpOrgArticle(companyId, cmpArticleOid);
		if (cmpOrgArticle != null) {
			cmpOrgArticle.setPath(null);
			this.cmpOrgArticleService.updateCmpOrgArticle(cmpOrgArticle, null);
		}
		return null;
	}

	/**
	 * 设置图片文件置顶
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String prvsettop(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		long companyId = req.getLongAndSetAttr("companyId");
		long cmpArticleOid = req.getLongAndSetAttr("cmpArticleOid");
		long cmpFileOid = req.getLongAndSetAttr("cmpFileOid");
		List<CmpOrgFile> list = this.cmpOrgFileService
				.getCmpOrgFileListByCompanyIdAndArticleOid(companyId,
						cmpArticleOid);
		for (CmpOrgFile o : list) {
			if (o.isTopInFile()) {
				o.setTopflg(CmpFile.TOPFLG_N);
				this.cmpOrgFileService.updateCmpOrgFile(o);
			}
			if (o.getOid() == cmpFileOid) {
				o.setTopflg(CmpFile.TOPFLG_Y);
				this.cmpOrgFileService.updateCmpOrgFile(o);
			}
		}
		return null;
	}

	/**
	 * 取消图片文件置顶
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String prvcanceltop(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		long companyId = req.getLongAndSetAttr("companyId");
		long cmpArticleOid = req.getLongAndSetAttr("cmpArticleOid");
		List<CmpOrgFile> list = this.cmpOrgFileService
				.getCmpOrgFileListByCompanyIdAndArticleOid(companyId,
						cmpArticleOid);
		for (CmpOrgFile o : list) {
			if (o.isTopInFile()) {
				o.setTopflg(CmpFile.TOPFLG_N);
				this.cmpOrgFileService.updateCmpOrgFile(o);
			}
		}
		return null;
	}
}