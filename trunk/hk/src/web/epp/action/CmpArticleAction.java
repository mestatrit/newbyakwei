package web.epp.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpArticle;
import com.hk.bean.CmpArticleContent;
import com.hk.bean.CmpArticleNavPink;
import com.hk.bean.CmpArticleTag;
import com.hk.bean.CmpArticleTagRef;
import com.hk.bean.CmpFile;
import com.hk.bean.CmpInfo;
import com.hk.bean.CmpNav;
import com.hk.bean.CmpOrgStudyAd;
import com.hk.bean.CmpProduct;
import com.hk.bean.CmpStudyKind;
import com.hk.bean.Company;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpArticleService;
import com.hk.svr.CmpArticleTagService;
import com.hk.svr.CmpFileService;
import com.hk.svr.CmpOrgStudyAdService;
import com.hk.svr.CmpProductService;
import com.hk.svr.CmpStudyKindService;
import com.hk.svr.processor.CmpArticleProcessor;
import com.hk.svr.processor.CmpArticleSearchResult;

@Component("/epp/web/cmparticle")
public class CmpArticleAction extends EppBaseAction {

	@Autowired
	private CmpArticleService cmpArticleService;

	@Autowired
	private CmpFileService cmpFileService;

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private CmpArticleProcessor cmpArticleProcessor;

	@Autowired
	private CmpArticleTagService cmpArticleTagService;

	@Autowired
	private CmpOrgStudyAdService cmpOrgStudyAdService;

	@Autowired
	private CmpStudyKindService cmpStudyKindService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		Company o = (Company) req.getAttribute("o");
		if (o.getCmpflg() == 0) {
			return this.web0(req, resp);
		}
		if (o.getCmpflg() == 2) {
			return this.web2(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-1
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long navId = req.getLongAndSetAttr("navId");
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		CmpNav cmpNav = (CmpNav) req.getAttribute("cmpNav");
		if (cmpNav == null) {
			return null;
		}
		SimplePage page = req.getSimplePage(20);
		List<CmpArticle> list = this.cmpArticleService
				.getCmpArticleListByCompanyIdAndCmpNavOid(companyId, navId,
						null, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("mod/0/1/cmparticle/list2.jsp");
	}

	public String web0(HkRequest req, HkResponse resp) throws Exception {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.web00(req, resp);
		}
		if (tmlflg == 1) {
			return this.web01(req, resp);
		}
		return null;
	}

	public String web2(HkRequest req, HkResponse resp) throws Exception {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.web20(req, resp);
		}
		return null;
	}

	/**
	 * 文章推荐列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-5
	 */
	public String web01(HkRequest req, HkResponse resp) throws Exception {
		long navId = req.getLongAndSetAttr("navId");
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		CmpNav cmpNav = (CmpNav) req.getAttribute("cmpNav");
		if (cmpNav == null) {
			return null;
		}
		if (cmpNav.isArticleSingle()) {
			List<CmpArticle> list = this.cmpArticleService
					.getCmpArticleListByCompanyIdAndCmpNavOid(companyId, navId,
							null, 0, 1);
			if (list.size() > 0) {
				// return "r:/epp/web/cmparticle_view.do?companyId=" + companyId
				// + "&oid=" + list.get(0).getOid() + "&navId="
				// + list.get(0).getCmpNavOid();
				CmpArticle cmpArticle = list.get(0);
				return "r:/article/" + companyId + "/"
						+ cmpArticle.getCmpNavOid() + "/" + cmpArticle.getOid()
						+ ".html";
			}
		}
		List<CmpArticleNavPink> navpinklist = this.cmpArticleProcessor
				.getCmpArticleNavPinkByCompanyIdAndNavId(companyId, navId,
						true, true);
		if (navpinklist.size() == 1) {
			CmpArticleNavPink cmpArticleNavPink = navpinklist.get(0);
			CmpArticle cmpArticle = cmpArticleNavPink.getCmpArticle();
			if (cmpArticle != null) {
				return "r:/article/" + companyId + "/"
						+ cmpArticle.getCmpNavOid() + "/" + cmpArticle.getOid()
						+ ".html";
			}
		}
		// 没有头图的文章
		List<CmpArticleNavPink> notpiclist = new ArrayList<CmpArticleNavPink>();
		// 有头图的文章
		List<CmpArticleNavPink> haspiclist = new ArrayList<CmpArticleNavPink>();
		for (CmpArticleNavPink o : navpinklist) {
			if (o.getCmpArticle() != null
					&& DataUtil.isEmpty(o.getCmpArticle().getFilepath())) {
				notpiclist.add(o);
			}
			else {
				haspiclist.add(o);
			}
		}
		req.setAttribute("notpiclist", notpiclist);
		req.setAttribute("haspiclist", haspiclist);
		List<Long> idList = new ArrayList<Long>();
		for (CmpArticleNavPink o : navpinklist) {
			idList.add(o.getArticleId());
		}
		List<CmpArticle> otherlist = this.cmpArticleService
				.getCmpArticleListByCompanyIdAndCmpNavOidNotInId(companyId,
						navId, idList, 0, 11);
		if (otherlist.size() == 11) {
			req.setAttribute("more_article", true);
			otherlist.remove(10);
		}
		req.setAttribute("otherlist", otherlist);
		return this.getWebPath("mod/0/1/cmparticle/list.jsp");
	}

	/**
	 * 文章列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-5
	 */
	public String web00(HkRequest req, HkResponse resp) throws Exception {
		long navId = req.getLongAndSetAttr("navId");
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		CmpNav cmpNav = (CmpNav) req.getAttribute("cmpNav");
		if (cmpNav == null) {
			return null;
		}
		SimplePage page = null;
		if (cmpNav.isArticleListWithImgShow()) {
			page = req.getSimplePage(12);
		}
		else {
			page = req.getSimplePage(20);
		}
		List<CmpArticle> list = this.cmpArticleService
				.getCmpArticleListByCompanyIdAndCmpNavOid(companyId, navId,
						null, page.getBegin(), page.getSize() + 1);
		if (cmpNav.isArticleSingle()) {
			if (list.size() > 0) {
				// return "r:/epp/web/cmparticle_view.do?companyId=" + companyId
				// + "&oid=" + list.get(0).getOid() + "&navId="
				// + list.get(0).getCmpNavOid();
				CmpArticle cmpArticle = list.get(0);
				return "r:/article/" + companyId + "/"
						+ cmpArticle.getCmpNavOid() + "/" + cmpArticle.getOid()
						+ ".html";
			}
		}
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("cmparticle/list.jsp");
	}

	/**
	 * 文章列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-5
	 */
	public String web20(HkRequest req, HkResponse resp) throws Exception {
		long navId = req.getLongAndSetAttr("navId");
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		CmpNav cmpNav = (CmpNav) req.getAttribute("cmpNav");
		if (cmpNav == null) {
			return null;
		}
		PageSupport page = null;
		if (cmpNav.isArticleListWithImgShow()) {
			page = req.getPageSupport(15);
		}
		else {
			page = req.getPageSupport(10);
		}
		page.setTotalCount(this.cmpArticleService
				.countCmpArticleListByCompanyIdAndCmpNavOid(companyId, navId,
						null));
		List<CmpArticle> list = this.cmpArticleService
				.getCmpArticleListByCompanyIdAndCmpNavOid(companyId, navId,
						null, page.getBegin(), page.getSize());
		if (cmpNav.isArticleSingle()) {
			if (list.size() > 0) {
				return "r:/epp/web/cmparticle_view.do?companyId=" + companyId
						+ "&oid=" + list.get(0).getOid() + "&navId="
						+ list.get(0).getCmpNavOid();
			}
		}
		if (cmpNav.getKindId() > 0) {
			CmpStudyKind cmpStudyKind = this.cmpStudyKindService
					.getCmpStudyKind(companyId, cmpNav.getKindId());
			List<CmpOrgStudyAd> studyadlist = null;
			if (cmpStudyKind.getKlevel() == 1) {
				studyadlist = this.cmpOrgStudyAdService
						.getCmpOrgStudyAdListByCompanyIdAndKindId(companyId,
								cmpNav.getKindId(), 0, 11);
			}
			else if (cmpStudyKind.getKlevel() == 2) {
				studyadlist = this.cmpOrgStudyAdService
						.getCmpOrgStudyAdListByCompanyIdAndKindId2(companyId,
								cmpNav.getKindId(), 0, 11);
			}
			else {
				studyadlist = this.cmpOrgStudyAdService
						.getCmpOrgStudyAdListByCompanyIdAndKindId3(companyId,
								cmpNav.getKindId(), 0, 11);
			}
			if (studyadlist.size() == 11) {
				req.setAttribute("more_studyad", true);
				studyadlist.remove(10);
			}
			req.setAttribute("studyadlist", studyadlist);
		}
		req.setAttribute("list", list);
		return this.getWebPath("mod/2/0/cmparticle/list.jsp");
	}

	/**
	 * wap 文章列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String wap(HkRequest req, HkResponse resp) throws Exception {
		long navId = req.getLongAndSetAttr("navId");
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		CmpNav cmpNav = (CmpNav) req.getAttribute("cmpNav");
		if (cmpNav == null) {
			return null;
		}
		SimplePage page = null;
		if (cmpNav.isArticleListWithImgShow()) {
			page = req.getSimplePage(10);
		}
		else {
			page = req.getSimplePage(20);
		}
		List<CmpArticle> list = this.cmpArticleService
				.getCmpArticleListByCompanyIdAndCmpNavOid(companyId, navId,
						null, page.getBegin(), page.getSize() + 1);
		if (cmpNav.isArticleSingle()) {
			if (list.size() > 0) {
				return "r:/epp/web/cmparticle_wapview.do?companyId="
						+ companyId + "&oid=" + list.get(0).getOid()
						+ "&navId=" + list.get(0).getCmpNavOid();
			}
		}
		if (list.size() == 1 && req.getInt("needlist") == 0) {
			return "r:/epp/web/cmparticle_wapview.do?companyId=" + companyId
					+ "&oid=" + list.get(0).getOid() + "&navId="
					+ list.get(0).getCmpNavOid();
		}
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapPath("cmparticle/list.jsp");
	}

	/**
	 * 文章内容查看
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-18
	 */
	public String view(HkRequest req, HkResponse resp) throws Exception {
		Company company = (Company) req.getAttribute("o");
		// 根据企业网站类型选择模板
		if (company.getCmpflg() == 0) {
			return this.view0(req, resp);
		}
		if (company.getCmpflg() == 1) {
			return this.view1(req, resp);
		}
		if (company.getCmpflg() == 2) {
			return this.view20(req, resp);
		}
		return null;
	}

	/**
	 * 根据网站模板选择页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-21
	 */
	public String view0(HkRequest req, HkResponse resp) throws Exception {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.view00(req, resp);
		}
		if (tmlflg == 1) {
			return this.view01(req, resp);
		}
		return null;
	}

	/**
	 * 根据网站模板选择页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-21
	 */
	public String view1(HkRequest req, HkResponse resp) throws Exception {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.view10(req, resp);
		}
		return null;
	}

	public String view2(HkRequest req, HkResponse resp) throws Exception {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.view20(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-18
	 */
	public String view10(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		long companyId = req.getLong("companyId");
		CmpArticle cmpArticle = this.cmpArticleService.getCmpArticle(oid);
		if (cmpArticle == null) {
			return null;
		}
		req.setAttribute("cmpArticle", cmpArticle);
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
		// 查看是否关联了产品，如果关联了产品，就取产品数据
		if (cmpArticle.getProductId() > 0) {
			CmpProduct cmpProduct = this.cmpProductService
					.getCmpProduct(cmpArticle.getProductId());
			req.setAttribute("cmpProduct", cmpProduct);
		}
		return this.getWebPath("mod/1/0/cmparticle/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-18
	 */
	public String view20(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		long navId = req.getLong("navId");
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		CmpArticle cmpArticle = this.cmpArticleService.getCmpArticle(oid);
		if (cmpArticle == null) {
			return null;
		}
		req.setAttribute("cmpArticle", cmpArticle);
		CmpArticleContent cmpArticleContent = this.cmpArticleService
				.getCmpArticleContent(oid);
		req.setAttribute("cmpArticleContent", cmpArticleContent);
		List<CmpArticleTagRef> tagreflist = this.cmpArticleProcessor
				.getCmpArticleTagRefListByCompanyIdAndArticleId(companyId, oid,
						true, 0, 3);
		req.setAttribute("tagreflist", tagreflist);
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
		CmpNav cmpNav = (CmpNav) req.getAttribute("cmpNav");
		if (cmpNav != null) {
			if (!cmpNav.isArticleSingle()) {
				List<CmpArticle> before_cmparticle_list = this.cmpArticleService
						.getCmpArticleListByCompanyIdForRange(companyId, navId,
								oid, cmpArticle.getOrderflg(), -1, 3);
				List<CmpArticle> after_cmparticle_list = this.cmpArticleService
						.getCmpArticleListByCompanyIdForRange(companyId, navId,
								oid, cmpArticle.getOrderflg(), 1, 3);
				req.setAttribute("before_cmparticle_list",
						before_cmparticle_list);
				req
						.setAttribute("after_cmparticle_list",
								after_cmparticle_list);
			}
		}
		return this.getWebPath("mod/2/0/cmparticle/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-18
	 */
	public String view01(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		long navId = req.getLong("navId");
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		CmpArticle cmpArticle = this.cmpArticleService.getCmpArticle(oid);
		if (cmpArticle == null) {
			return null;
		}
		req.setAttribute("cmpArticle", cmpArticle);
		CmpArticleContent cmpArticleContent = this.cmpArticleService
				.getCmpArticleContent(oid);
		req.setAttribute("cmpArticleContent", cmpArticleContent);
		List<CmpArticleTagRef> tagreflist = this.cmpArticleProcessor
				.getCmpArticleTagRefListByCompanyIdAndArticleId(companyId, oid,
						true, 0, 3);
		req.setAttribute("tagreflist", tagreflist);
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
		CmpNav cmpNav = (CmpNav) req.getAttribute("cmpNav");
		if (cmpNav != null) {
			if (!cmpNav.isArticleSingle()) {
				List<CmpArticle> before_cmparticle_list = this.cmpArticleService
						.getCmpArticleListByCompanyIdForRange(companyId, navId,
								oid, cmpArticle.getOrderflg(), -1, 3);
				List<CmpArticle> after_cmparticle_list = this.cmpArticleService
						.getCmpArticleListByCompanyIdForRange(companyId, navId,
								oid, cmpArticle.getOrderflg(), 1, 3);
				req.setAttribute("before_cmparticle_list",
						before_cmparticle_list);
				req
						.setAttribute("after_cmparticle_list",
								after_cmparticle_list);
			}
		}
		return this.getWebPath("mod/0/1/cmparticle/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-18
	 */
	public String view00(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		long navId = req.getLong("navId");
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		CmpArticle cmpArticle = this.cmpArticleService.getCmpArticle(oid);
		if (cmpArticle == null) {
			return null;
		}
		req.setAttribute("cmpArticle", cmpArticle);
		CmpArticleContent cmpArticleContent = this.cmpArticleService
				.getCmpArticleContent(oid);
		req.setAttribute("cmpArticleContent", cmpArticleContent);
		List<CmpArticleTagRef> tagreflist = this.cmpArticleProcessor
				.getCmpArticleTagRefListByCompanyIdAndArticleId(companyId, oid,
						true, 0, 3);
		req.setAttribute("tagreflist", tagreflist);
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
		CmpNav cmpNav = (CmpNav) req.getAttribute("cmpNav");
		if (cmpNav != null) {
			if (!cmpNav.isArticleSingle()) {
				List<CmpArticle> before_cmparticle_list = this.cmpArticleService
						.getCmpArticleListByCompanyIdForRange(companyId, navId,
								oid, cmpArticle.getOrderflg(), -1, 3);
				List<CmpArticle> after_cmparticle_list = this.cmpArticleService
						.getCmpArticleListByCompanyIdForRange(companyId, navId,
								oid, cmpArticle.getOrderflg(), 1, 3);
				req.setAttribute("before_cmparticle_list",
						before_cmparticle_list);
				req
						.setAttribute("after_cmparticle_list",
								after_cmparticle_list);
			}
		}
		return this.getWebPath("cmparticle/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-18
	 */
	public String next(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		long companyId = req.getLong("companyId");
		long navId = req.getLong("navId");
		CmpArticle cmpArticle = this.cmpArticleService.getCmpArticle(oid);
		if (cmpArticle == null) {
			return null;
		}
		List<CmpArticle> after_cmparticle_list = this.cmpArticleService
				.getCmpArticleListByCompanyIdForRange(companyId, navId, oid,
						cmpArticle.getOrderflg(), 1, 1);
		if (after_cmparticle_list.size() > 0) {
			CmpArticle o = after_cmparticle_list.get(0);
			// return "r:/epp/web/cmparticle_view.do?companyId=" + companyId
			// + "&oid=" + o.getOid() + "&navId=" + navId;
			return "r:http://" + req.getServerName() + "/article/" + companyId
					+ "/" + navId + "/" + o.getOid() + ".html";
		}
		// return "r:/epp/web/cmparticle.do?companyId=" + companyId + "&navId="
		// + navId;
		return "r:http://" + req.getServerName() + "/articles/" + companyId
				+ "/" + navId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-18
	 */
	public String wapview(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		CmpArticle cmpArticle = this.cmpArticleService.getCmpArticle(oid);
		if (cmpArticle == null) {
			return null;
		}
		req.setAttribute("cmpArticle", cmpArticle);
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
		return this.getWapPath("cmparticle/view.jsp");
	}

	public String search2(HkRequest req, HkResponse resp) throws Exception {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.search20(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-6
	 */
	public String search20(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		String key = req.getHtmlRow("key");
		PageSupport page = req.getPageSupport(20);
		req.setEncodeAttribute("key", key);
		CmpArticleSearchResult cmpArticleSearchResult = this.cmpArticleProcessor
				.searchCmpArticleListByComapnyIdAndKey(companyId, key, page
						.getBegin(), page.getSize() + 1);
		if (cmpArticleSearchResult == null) {
			return "http://" + req.getServerName();
		}
		page.setTotalCount(cmpArticleSearchResult.getTotalCount());
		List<CmpArticle> list = cmpArticleSearchResult.getCmpArticles();
		req.setAttribute("list", list);
		return this.getWebPath("mod/2/0/cmparticle/search.jsp");
	}

	/**
	 * 全文搜索文章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-18
	 */
	public String search(HkRequest req, HkResponse resp) throws Exception {
		Company o = (Company) req.getAttribute("o");
		if (o.getCmpflg() == 2) {
			return this.search2(req, resp);
		}
		return null;
	}

	/**
	 * 标签关系数据(文章)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String tagarticle(HkRequest req, HkResponse resp) {
		long tagId = req.getLongAndSetAttr("tagId");
		long companyId = req.getLong("companyId");
		CmpArticleTag cmpArticleTag = this.cmpArticleTagService
				.getCmpArticleTag(companyId, tagId);
		if (cmpArticleTag == null) {
			return null;
		}
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpArticleTagService
				.countCmpArticleTagRefByCompanyIdAndTagId(companyId, tagId));
		List<CmpArticleTagRef> tagreflist = this.cmpArticleProcessor
				.getCmpArticleTagRefListByCompanyIdAndTagId(companyId, tagId,
						true, page.getBegin(), page.getSize() + 1);
		req.setAttribute("cmpArticleTag", cmpArticleTag);
		req.setAttribute("tagreflist", tagreflist);
		return this.getWebPath("mod/2/0/cmparticle/tagreflist.jsp");
	}

	/**
	 * 标签关系数据(文章)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String idx(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long begin = System.currentTimeMillis();
		this.cmpArticleProcessor
				.indexCmpArticleListByComapnyIdAndKey(companyId);
		long end = System.currentTimeMillis();
		resp.sendHtml("index ok [ " + (end - begin) + " ms ]");
		return null;
	}
}