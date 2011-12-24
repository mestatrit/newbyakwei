package web.epp.mgr.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpArticle;
import com.hk.bean.CmpArticleBlock;
import com.hk.bean.CmpArticleContent;
import com.hk.bean.CmpArticleGroup;
import com.hk.bean.CmpArticleNavPink;
import com.hk.bean.CmpArticleTag;
import com.hk.bean.CmpArticleTagRef;
import com.hk.bean.CmpFile;
import com.hk.bean.CmpNav;
import com.hk.bean.CmpPageBlock;
import com.hk.bean.CmpPageMod;
import com.hk.bean.CmpProduct;
import com.hk.bean.CmpUtil;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkValidate;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpArticleService;
import com.hk.svr.CmpArticleTagService;
import com.hk.svr.CmpFileService;
import com.hk.svr.CmpModService;
import com.hk.svr.CmpNavService;
import com.hk.svr.CmpProductService;
import com.hk.svr.processor.CmpArticleProcessor;
import com.hk.svr.processor.CmpArticleUploadFileResult;
import com.hk.svr.processor.CmpModProcessor;
import com.hk.svr.pub.CmpPageModUtil;
import com.hk.svr.pub.Err;

/**
 * 企业文章
 * 
 * @author akwei
 */
@Component("/epp/web/op/webadmin/cmparticle")
public class CmpArticleAction extends EppBaseAction {

	@Autowired
	private CmpNavService cmpNavService;

	@Autowired
	private CmpArticleService cmpArticleService;

	@Autowired
	private CmpArticleProcessor cmpArticleProcessor;

	@Autowired
	private CmpFileService cmpFileService;

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private CmpModService cmpModService;

	@Autowired
	private CmpModProcessor cmpModProcessor;

	@Autowired
	private CmpArticleTagService cmpArticleTagService;

	/**
	 * 某个栏目的文章列表,如果该栏目
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long navoid = req.getLongAndSetAttr("navoid");
		long companyId = req.getLong("companyId");
		CmpNav cmpNav = this.cmpNavService.getCmpNav(navoid);
		if (cmpNav == null) {
			return null;
		}
		req.setAttribute("cmpNav", cmpNav);
		// 单条内容直接到创建或者预览页面
		if (cmpNav.isArticleSingle()) {
			List<CmpArticle> list = this.cmpArticleService
					.getCmpArticleListByCompanyIdAndCmpNavOid(companyId,
							navoid, null, 0, 1);
			if (list.size() == 0) {
				return "r:/epp/web/op/webadmin/cmparticle_create.do?companyId="
						+ companyId + "&navoid=" + navoid;
			}
			return "r:/epp/web/op/webadmin/cmparticle_view.do?companyId="
					+ companyId + "&oid=" + list.get(0).getOid() + "&navoid="
					+ navoid;
		}
		String title = req.getHtmlRow("title");
		SimplePage page = req.getSimplePage(20);
		List<CmpArticle> list = this.cmpArticleProcessor
				.getCmpArticleListByCompanyIdAndCmpNavOid(companyId, navoid,
						true, true, title, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setEncodeAttribute("title", title);
		return this.getWebPath("admin/cmparticle/list.jsp");
	}

	/**
	 * 所有文章的列表，本页是为了推荐首页图片文章使用
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-18
	 */
	public String alllist(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_15", 1);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		String title = req.getHtmlRow("title");
		List<CmpArticle> list = this.cmpArticleService
				.getCmpArticleListByCompanyId(companyId, title,
						page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmparticle/alllist.jsp");
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
		long companyId = req.getLong("companyId");
		long navoid = req.getLongAndSetAttr("navoid");
		int ch = req.getInt("ch");
		CmpNav cmpNav = this.cmpNavService.getCmpNav(navoid);
		if (cmpNav == null) {
			return null;
		}
		req.setAttribute("cmpNav", cmpNav);
		if (ch == 0) {
			List<CmpArticleGroup> grouplist = this.cmpArticleService
					.getCmpArticleGroupListByCompanyIdAndCmpNavOid(companyId,
							navoid);
			req.setAttribute("grouplist", grouplist);
			return this.getWebPath("admin/cmparticle/create.jsp");
		}
		if (cmpNav.isArticleSingle()) {
			List<CmpArticle> cmpArticles = this.cmpArticleService
					.getCmpArticleListByCompanyIdAndCmpNavOid(companyId,
							navoid, null, 0, 1);
			if (cmpArticles.size() == 1) {
				return null;
			}
		}
		CmpArticle cmpArticle = new CmpArticle();
		cmpArticle.setCompanyId(companyId);
		cmpArticle.setCmpNavOid(navoid);
		cmpArticle.setGroupId(req.getLong("groupId"));
		cmpArticle.setTitle(req.getHtmlRow("title"));
		cmpArticle.setHideTitleflg(req.getByte("hideTitleflg"));
		CmpArticleContent cmpArticleContent = new CmpArticleContent();
		cmpArticleContent.setContent(req.getHtmlWithoutBeginTrim("content"));
		int code = cmpArticle.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		code = cmpArticleContent.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		int topIdx = req.getInt("topIdx", -1);
		String tagdata = req.getHtmlRow("tagdata");
		List<String> tagDataList = new ArrayList<String>();
		if (!DataUtil.isEmpty(tagdata)) {
			tagdata = tagdata.replaceAll("，", ",");
			String[] data = tagdata.split(",");
			for (String s : data) {
				if (HkValidate.validateEmptyAndLength(s, true, 20)) {
					tagDataList.add(s);
				}
			}
		}
		try {
			CmpArticleUploadFileResult cmpArticleUploadFileResult = this.cmpArticleProcessor
					.createCmpArticle(cmpArticle, cmpArticleContent, req
							.getFiles(), topIdx, tagDataList);
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
		}
		return this.onSuccess2(req, "createok", cmpArticle.getOid());
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
		long navoid = req.getLongAndSetAttr("navoid");
		long companyId = req.getLong("companyId");
		long oid = req.getLongAndSetAttr("oid");
		int ch = req.getInt("ch");
		CmpArticle cmpArticle = this.cmpArticleService.getCmpArticle(oid);
		if (cmpArticle == null) {
			return null;
		}
		req.setAttribute("cmpArticle", cmpArticle);
		CmpArticleContent cmpArticleContent = this.cmpArticleService
				.getCmpArticleContent(oid);
		req.setAttribute("cmpArticleContent", cmpArticleContent);
		CmpNav cmpNav = this.cmpNavService.getCmpNav(cmpArticle.getCmpNavOid());
		req.setAttribute("cmpNav", cmpNav);
		if (ch == 0) {
			List<CmpArticleGroup> grouplist = this.cmpArticleService
					.getCmpArticleGroupListByCompanyIdAndCmpNavOid(companyId,
							navoid);
			req.setAttribute("grouplist", grouplist);
			List<CmpFile> list = this.cmpFileService
					.getCmpFileListByCompanyIdAndArticleOid(cmpArticle
							.getCompanyId(), oid);
			List<CmpArticleTagRef> tagreflist = this.cmpArticleProcessor
					.getCmpArticleTagRefListByCompanyIdAndArticleId(companyId,
							oid, true, 0, 3);
			StringBuilder sb = new StringBuilder();
			for (CmpArticleTagRef ref : tagreflist) {
				if (ref.getCmpArticleTag() != null) {
					sb.append(ref.getCmpArticleTag().getName()).append(",");
				}
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			req.setAttribute("tagdata", sb.toString());
			req.setAttribute("list", list);
			return this.getWebPath("admin/cmparticle/update.jsp");
		}
		cmpArticle.setGroupId(req.getLong("groupId"));
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
		String tagdata = req.getHtmlRow("tagdata");
		List<String> tagDataList = new ArrayList<String>();
		if (!DataUtil.isEmpty(tagdata)) {
			tagdata = tagdata.replaceAll("，", ",");
			String[] data = tagdata.split(",");
			for (String s : data) {
				if (HkValidate.validateEmptyAndLength(s, true, 20)) {
					tagDataList.add(s);
				}
			}
		}
		try {
			CmpArticleUploadFileResult cmpArticleUploadFileResult = this.cmpArticleProcessor
					.updateCmpArticle(cmpArticle, cmpArticleContent, req
							.getFiles(), topIdx, tagDataList);
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
		}
		return this.onSuccess2(req, "updateok", cmpArticle.getOid());
	}

	/**
	 * 文章预览
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String view(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		long oid = req.getLongAndSetAttr("oid");
		long companyId = req.getLong("companyId");
		CmpArticle cmpArticle = this.cmpArticleService.getCmpArticle(oid);
		if (cmpArticle == null) {
			return null;
		}
		req.setAttribute("cmpArticle", cmpArticle);
		CmpNav cmpNav = this.cmpNavService.getCmpNav(cmpArticle.getCmpNavOid());
		req.setAttribute("cmpNav", cmpNav);
		CmpArticleContent cmpArticleContent = this.cmpArticleService
				.getCmpArticleContent(oid);
		req.setAttribute("cmpArticleContent", cmpArticleContent);
		List<CmpFile> list = this.cmpFileService
				.getCmpFileListByCompanyIdAndArticleOid(companyId, oid);
		CmpFile topCmpFile = null;
		for (int i = 0; i < list.size(); i++) {
			CmpFile o = list.get(i);
			if (o.getTopflg() == CmpFile.TOPFLG_Y) {
				topCmpFile = list.remove(i);
				break;
			}
		}
		req.setAttribute("topCmpFile", topCmpFile);
		req.setAttribute("list", list);
		List<CmpArticleTagRef> tagreflist = this.cmpArticleProcessor
				.getCmpArticleTagRefListByCompanyIdAndArticleId(companyId, oid,
						true, 0, 3);
		if (cmpArticle.getPage1BlockId() > 0) {
			CmpArticleBlock cmpArticleBlock = this.cmpModService
					.getCmpArticleBlock(companyId, oid, cmpArticle
							.getPage1BlockId());
			if (cmpArticleBlock != null) {
				CmpPageBlock cmpPageBlock = this.cmpModService
						.getCmpPageBlock(cmpArticleBlock.getBlockId());
				req.setAttribute("cmpPageBlock", cmpPageBlock);
				req.setAttribute("cmpArticleBlock", cmpArticleBlock);
			}
		}
		CmpArticleNavPink cmpArticleNavPink = this.cmpArticleService
				.getCmpArticleNavPinkByCompanyIdAndArticleId(companyId, oid);
		req.setAttribute("cmpArticleNavPink", cmpArticleNavPink);
		req.setAttribute("tagreflist", tagreflist);
		return this.getWebPath("admin/cmparticle/view.jsp");
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
		long oid = req.getLongAndSetAttr("oid");
		long companyId = req.getLong("companyId");
		CmpArticle cmpArticle = this.cmpArticleService.getCmpArticle(oid);
		if (cmpArticle == null) {
			return null;
		}
		this.cmpArticleProcessor.deleteCmpArticle(companyId, oid);
		req.setSessionText("view2.data.delete.ok");
		if (req.getInt("ajax") == 1) {
			return null;
		}
		return "r:/epp/web/op/webadmin/admincmpnav_view.do?companyId="
				+ companyId + "&oid=" + cmpArticle.getCmpNavOid();
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
		long cmpArticleOid = req.getLongAndSetAttr("cmpArticleOid");
		long cmpFileOid = req.getLong("cmpFileOid");
		this.cmpArticleProcessor
				.deleteCmpArticleFile(cmpArticleOid, cmpFileOid);
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
	public String setmain(HkRequest req, HkResponse resp) throws Exception {
		long cmpArticleOid = req.getLongAndSetAttr("cmpArticleOid");
		long cmpFileOid = req.getLong("cmpFileOid");
		this.cmpArticleProcessor.setMainFile(cmpArticleOid, cmpFileOid);
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
	public String cancelmain(HkRequest req, HkResponse resp) throws Exception {
		long cmpArticleOid = req.getLongAndSetAttr("cmpArticleOid");
		CmpArticle cmpArticle = this.cmpArticleService
				.getCmpArticle(cmpArticleOid);
		if (cmpArticle != null) {
			cmpArticle.setFilepath(null);
			this.cmpArticleService.updateCmpArticle(cmpArticle, null);
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
	public String settop(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		long cmpArticleOid = req.getLongAndSetAttr("cmpArticleOid");
		long cmpFileOid = req.getLongAndSetAttr("cmpFileOid");
		List<CmpFile> list = this.cmpFileService
				.getCmpFileListByCompanyIdAndArticleOid(companyId,
						cmpArticleOid);
		for (CmpFile o : list) {
			if (o.isTopInFile()) {
				o.setTopflg(CmpFile.TOPFLG_N);
				this.cmpFileService.updateCmpFile(o);
			}
			if (o.getOid() == cmpFileOid) {
				o.setTopflg(CmpFile.TOPFLG_Y);
				this.cmpFileService.updateCmpFile(o);
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
	public String canceltop(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		long cmpArticleOid = req.getLongAndSetAttr("cmpArticleOid");
		List<CmpFile> list = this.cmpFileService
				.getCmpFileListByCompanyIdAndArticleOid(companyId,
						cmpArticleOid);
		for (CmpFile o : list) {
			if (o.isTopInFile()) {
				o.setTopflg(CmpFile.TOPFLG_N);
				this.cmpFileService.updateCmpFile(o);
			}
		}
		return null;
	}

	/**
	 * 推荐文章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String setcmppink(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.cmpArticleService.setCmpArticleCmppink(oid, CmpUtil.CMPPINK_Y);
		this.setPinkSuccessMsg(req);
		return null;
	}

	/**
	 * 取消推荐
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String delcmppink(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.cmpArticleService.setCmpArticleCmppink(oid, CmpUtil.CMPPINK_N);
		this.setDelPinkSuccessMsg(req);
		return null;
	}

	/**
	 * 推荐文章到首页，只能推荐一个
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String sethomepink(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		long companyId = req.getLong("companyId");
		CmpArticle cmpArticle = this.cmpArticleService
				.getCmpArticleForHomepink(companyId);
		if (cmpArticle != null) {
			this.cmpArticleService.setCmpArticleHomePink(cmpArticle.getOid(),
					CmpArticle.HOMEPINK_N);
		}
		this.cmpArticleService
				.setCmpArticleHomePink(oid, CmpArticle.HOMEPINK_Y);
		this.setPinkSuccessMsg(req);
		return null;
	}

	/**
	 * 取消推荐到首页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String delhomepink(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.cmpArticleService
				.setCmpArticleHomePink(oid, CmpArticle.HOMEPINK_N);
		this.setDelPinkSuccessMsg(req);
		return null;
	}

	/**
	 * 文章排序，设置顺序号
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String setorderflg(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		int orderflg = req.getInt("orderflg");
		this.cmpArticleService.updateCmpArticleOrderflg(oid, orderflg);
		req.setSessionText("epp.cmparticle.orderflg.set.success");
		return this.onSuccess2(req, "setorderflgok", null);
	}

	/**
	 * 设置与产品的关联
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-17
	 */
	public String setproductref(HkRequest req, HkResponse resp)
			throws Exception {
		long navoid = req.getLongAndSetAttr("navoid");
		CmpNav cmpNav = this.cmpNavService.getCmpNav(navoid);
		if (cmpNav == null) {
			return null;
		}
		req.setAttribute("cmpNav", cmpNav);
		long oid = req.getLongAndSetAttr("oid");
		CmpArticle cmpArticle = this.cmpArticleService.getCmpArticle(oid);
		if (cmpArticle == null) {
			return null;
		}
		req.setAttribute("cmpArticle", cmpArticle);
		long companyId = req.getLong("companyId");
		if (this.isForwardPage(req)) {
			int sortId = req.getIntAndSetAttr("sortId");
			String name = req.getString("name");
			req.setEncodeAttribute("name", name);
			SimplePage page = req.getSimplePage(20);
			List<CmpProduct> list = this.cmpProductService
					.getCmpProductListByCompanyIdEx(companyId, sortId, name,
							page.getBegin(), page.getSize() + 1);
			this.processListForPage(page, list);
			req.setAttribute("list", list);
			if (cmpArticle.getProductId() > 0) {
				CmpProduct cmpProduct = this.cmpProductService
						.getCmpProduct(cmpArticle.getProductId());
				req.setAttribute("cmpProduct", cmpProduct);
			}
			return this.getWebPath("admin/cmparticle/setproductref.jsp");
		}
		long productId = req.getLong("productId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {
			return null;
		}
		cmpArticle.setProductId(productId);
		cmpArticle.setSortId(cmpProduct.getSortId());
		this.cmpArticleService.updateCmpArticle(cmpArticle, null);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * 删除与产品的关联
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-17
	 */
	public String delproductref(HkRequest req, HkResponse resp)
			throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		CmpArticle cmpArticle = this.cmpArticleService.getCmpArticle(oid);
		if (cmpArticle == null) {
			return null;
		}
		cmpArticle.setProductId(0);
		cmpArticle.setSortId(0);
		this.cmpArticleService.updateCmpArticle(cmpArticle, null);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * 推荐文章到页面模块
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String selblock(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		long companyId = req.getLong("companyId");
		req.reSetAttribute("page");
		CmpArticle cmpArticle = this.cmpArticleService.getCmpArticle(oid);
		if (cmpArticle == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			List<CmpPageBlock> blocklist = this.cmpModProcessor
					.getCmpPageBlockListByCompanyIdAndPageflg(companyId, true,
							(byte) 1);
			req.setAttribute("blocklist", blocklist);
			byte pageflg = req.getByteAndSetAttr("pageflg", (byte) 1);
			CmpArticleBlock cmpArticleBlock = this.cmpModService
					.getCmpArticleBlock(companyId, oid, pageflg);
			if (cmpArticleBlock != null) {
				CmpPageBlock cmpPageBlock = this.cmpModService
						.getCmpPageBlock(cmpArticleBlock.getBlockId());
				req.setAttribute("cmpArticleBlock", cmpArticleBlock);
				req.setAttribute("cmpPageBlock", cmpPageBlock);
			}
			return this.getWebPath("admin/cmparticle/selblock.jsp");
		}
		long blockId = req.getLong("blockId");
		CmpPageBlock cmpPageBlock = this.cmpModService.getCmpPageBlock(blockId);
		if (cmpPageBlock == null) {
			return null;
		}
		CmpPageMod cmpPageMod = CmpPageModUtil.getCmpPageMod(cmpPageBlock
				.getPageModId());
		if (cmpPageMod == null) {
			return null;
		}
		CmpArticleBlock cmpArticleBlock = new CmpArticleBlock();
		cmpArticleBlock.setCompanyId(companyId);
		cmpArticleBlock.setArticleId(oid);
		cmpArticleBlock.setPageflg((byte) 1);
		cmpArticleBlock.setBlockId(blockId);
		cmpArticleBlock.setCmpNavOid(cmpArticle.getCmpNavOid());
		if (this.cmpModProcessor.createCmpArticlePageBlock(cmpArticleBlock,
				cmpPageMod)) {
			this.setOpFuncSuccessMsg(req);
			return null;
		}
		resp.sendHtml(1);
		req.setSessionText(String.valueOf(Err.CMPARTICLEBLOCK_DUPLICATE_ERROR));
		return null;
	}

	/**
	 * 取消页面文章推荐
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String delarticleblock(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		this.cmpModProcessor.deleteCmpAdBlock(oid);
		return null;
	}

	/**
	 * 取消页面文章推荐
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String delarticleblock2(HkRequest req, HkResponse resp) {
		long blockId = req.getLong("blockId");
		long oid = req.getLong("oid");
		long companyId = req.getLong("companyId");
		CmpArticleBlock cmpArticleBlock = this.cmpModService
				.getCmpArticleBlock(companyId, oid, blockId);
		if (cmpArticleBlock != null) {
			this.cmpModProcessor
					.deleteCmpArticleBlock(cmpArticleBlock.getOid());
		}
		return null;
	}

	/**
	 * 创建栏目中文章分组
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String creategroup(HkRequest req, HkResponse resp) {
		long navoid = req.getLongAndSetAttr("navoid");
		long companyId = req.getLong("companyId");
		if (this.isForwardPage(req)) {
			CmpNav cmpNav = this.cmpNavService.getCmpNav(navoid);
			if (cmpNav == null) {
				return null;
			}
			req.setAttribute("cmpNav", cmpNav);
			return this.getWebPath("admin/cmparticle/creategroup.jsp");
		}
		CmpArticleGroup cmpArticleGroup = new CmpArticleGroup();
		cmpArticleGroup.setCmpNavOid(navoid);
		cmpArticleGroup.setCompanyId(companyId);
		cmpArticleGroup.setName(req.getHtmlRow("name"));
		int code = cmpArticleGroup.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		if (this.cmpArticleService.createCmpArticleGroup(cmpArticleGroup)) {
			return this.onSuccess2(req, "createok", null);
		}
		return this.onError(req, Err.CMPARTICLEGROUP_NAME_DUPLICATE,
				"createerror", null);
	}

	/**
	 * 修改文章分组
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String updategroup(HkRequest req, HkResponse resp) {
		long groupId = req.getLongAndSetAttr("groupId");
		CmpArticleGroup cmpArticleGroup = this.cmpArticleService
				.getCmpArticleGroup(groupId);
		if (cmpArticleGroup == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			long navoid = req.getLongAndSetAttr("navoid");
			CmpNav cmpNav = this.cmpNavService.getCmpNav(navoid);
			if (cmpNav == null) {
				return null;
			}
			req.setAttribute("cmpNav", cmpNav);
			req.setAttribute("cmpArticleGroup", cmpArticleGroup);
			return this.getWebPath("admin/cmparticle/updategroup.jsp");
		}
		cmpArticleGroup.setName(req.getHtmlRow("name"));
		int code = cmpArticleGroup.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		if (this.cmpArticleService.updateCmpArticleGroup(cmpArticleGroup)) {
			return this.onSuccess2(req, "updateok", null);
		}
		return this.onError(req, Err.CMPARTICLEGROUP_NAME_DUPLICATE,
				"updateerror", null);
	}

	/**
	 * 删除文章分组
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String delgroup(HkRequest req, HkResponse resp) {
		long groupId = req.getLongAndSetAttr("groupId");
		long companyId = req.getLong("companyId");
		CmpArticleGroup cmpArticleGroup = this.cmpArticleService
				.getCmpArticleGroup(groupId);
		if (cmpArticleGroup == null) {
			return null;
		}
		if (cmpArticleGroup.getCompanyId() == companyId) {
			this.cmpArticleService.deleteCmpArticleGroup(groupId);
			this.setDelSuccessMsg(req);
		}
		return null;
	}

	/**
	 * 文章分组列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String grouplist(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long navoid = req.getLongAndSetAttr("navoid");
		CmpNav cmpNav = this.cmpNavService.getCmpNav(navoid);
		if (cmpNav == null) {
			return null;
		}
		req.setAttribute("cmpNav", cmpNav);
		List<CmpArticleGroup> list = this.cmpArticleService
				.getCmpArticleGroupListByCompanyIdAndCmpNavOid(companyId,
						navoid);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmparticle/grouplist.jsp");
	}

	/**
	 * 所有文章的标签列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String taglist(HkRequest req, HkResponse resp) {
		req.setAttribute("active_30", 1);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		String name = req.getHtmlRow("name");
		req.setEncodeAttribute("name", name);
		List<CmpArticleTag> list = this.cmpArticleTagService
				.getCmpArticleTagListByCompanyId(companyId, name, page
						.getBegin(), page.getSize() + 1);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmparticle/taglist.jsp");
	}

	/**
	 * 推荐标签
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String setpink(HkRequest req, HkResponse resp) {
		long tagId = req.getLong("tagId");
		long companyId = req.getLong("companyId");
		CmpArticleTag cmpArticleTag = this.cmpArticleTagService
				.getCmpArticleTag(companyId, tagId);
		if (cmpArticleTag == null) {
			return null;
		}
		this.cmpArticleTagService.updateCmpArticleTagPinkflg(companyId, tagId,
				CmpArticleTag.PINKFLG_Y);
		return null;
	}

	/**
	 * 取消推荐标签
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String delpink(HkRequest req, HkResponse resp) {
		long tagId = req.getLong("tagId");
		long companyId = req.getLong("companyId");
		CmpArticleTag cmpArticleTag = this.cmpArticleTagService
				.getCmpArticleTag(companyId, tagId);
		if (cmpArticleTag == null) {
			return null;
		}
		this.cmpArticleTagService.updateCmpArticleTagPinkflg(companyId, tagId,
				CmpArticleTag.PINKFLG_N);
		return null;
	}

	/**
	 * 删除标签
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String deltag(HkRequest req, HkResponse resp) {
		long tagId = req.getLong("tagId");
		long companyId = req.getLong("companyId");
		this.cmpArticleTagService.deleteCmpArticleTag(companyId, tagId);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String createcmparticlenavpink(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long oid = req.getLongAndSetAttr("oid");
		CmpArticle cmpArticle = this.cmpArticleService.getCmpArticle(oid);
		if (cmpArticle == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpArticle", cmpArticle);
			return this
					.getWebPath("admin/cmparticle/createcmparticlenavpink.jsp");
		}
		CmpArticleNavPink cmpArticleNavPink = this.cmpArticleService
				.getCmpArticleNavPinkByCompanyIdAndArticleId(companyId, oid);
		if (cmpArticleNavPink != null) {
			this.cmpArticleService.deleteCmpArticleNavPink(companyId,
					cmpArticleNavPink.getOid());
		}
		cmpArticleNavPink = new CmpArticleNavPink();
		cmpArticleNavPink.setArticleId(oid);
		cmpArticleNavPink.setCompanyId(companyId);
		cmpArticleNavPink.setNavId(cmpArticle.getCmpNavOid());
		cmpArticleNavPink.setPflg(req.getInt("pflg"));
		this.cmpArticleService.createCmpArticleNavPink(cmpArticleNavPink);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String delcmparticlenavpink(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long oid = req.getLong("oid");
		CmpArticleNavPink cmpArticleNavPink = this.cmpArticleService
				.getCmpArticleNavPink(companyId, oid);
		if (cmpArticleNavPink == null
				|| cmpArticleNavPink.getCompanyId() != companyId) {
			return null;
		}
		this.cmpArticleService.deleteCmpArticleNavPink(companyId, oid);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String cmparticlenavpinklist(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long navoid = req.getLongAndSetAttr("navoid");
		CmpNav cmpNav = this.cmpNavService.getCmpNav(navoid);
		if (cmpNav == null) {
			return null;
		}
		req.setAttribute("cmpNav", cmpNav);
		List<CmpArticleNavPink> list = this.cmpArticleProcessor
				.getCmpArticleNavPinkByCompanyIdAndNavId(companyId, navoid,
						true, false);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmparticle/cmparticlenavpinklist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String updatecmparticlenavpinkorderflg(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long oid = req.getLong("oid");
		CmpArticleNavPink cmpArticleNavPink = this.cmpArticleService
				.getCmpArticleNavPink(companyId, oid);
		if (cmpArticleNavPink == null
				|| cmpArticleNavPink.getCompanyId() != companyId) {
			return null;
		}
		cmpArticleNavPink.setOrderflg(req.getInt("orderflg"));
		this.cmpArticleService.updateCmpArticleNavPink(cmpArticleNavPink);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "setorderflgok", null);
	}
}