package web.epp.mgr.action;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpArticle;
import com.hk.bean.CmpFuncRef;
import com.hk.bean.CmpNav;
import com.hk.bean.CmpNavPageCssObj;
import com.hk.bean.CmpStudyKind;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpArticleService;
import com.hk.svr.CmpFuncService;
import com.hk.svr.CmpNavService;
import com.hk.svr.CmpStudyKindService;
import com.hk.svr.processor.CmpNavProcessor;
import com.hk.svr.pub.Err;

/**
 * 栏目管理
 * 
 * @author akwei
 */
@Component("/epp/web/op/webadmin/admincmpnav")
public class AdminCmpNavAction extends EppBaseAction {

	@Autowired
	private CmpNavService cmpNavService;

	@Autowired
	private CmpNavProcessor cmpNavProcessor;

	@Autowired
	private CmpArticleService cmpArticleService;

	@Autowired
	private CmpStudyKindService cmpStudyKindService;

	@Autowired
	private CmpFuncService cmpFuncService;

	/**
	 * 后台管理首页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-11
	 */
	public String index(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_1", 1);
		long companyId = req.getLong("companyId");
		return "r:/epp/web/op/webadmin/admincmpnav.do?companyId=" + companyId;
	}

	/**
	 * 地图修改
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-11
	 */
	public String setmap(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_8", 1);
		return this.getWebPath("admin/cmpnav/setmap.jsp");
	}

	/**
	 * 显示一级栏目列表
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_1", 1);
		long companyId = req.getLong("companyId");
		List<CmpNav> list = this.cmpNavProcessor
				.getCmpNavListByCompanyIdAndNlevel(companyId, CmpNav.NLEVEL_1,
						true);
		int i = 1;
		for (CmpNav o : list) {
			if (o.getOrderflg() <= 0) {
				o.setOrderflg(i);
				this.cmpNavService.updateCmpNavOrderflg(o.getOid(), i);
			}
			i++;
		}
		for (CmpNav o : list) {
			if (o.isDirectory()) {
				if (this.cmpNavService
						.countCmpNavByCompanyIdAndParentIdAndNlevel(companyId,
								o.getOid(), CmpNav.NLEVEL_2) > 0) {
					o.setHasChild(true);
				}
			}
		}
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmpnav/list.jsp");
	}

	/**
	 * 显示二级栏目列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-11
	 */
	public String list2(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_1", 1);
		long companyId = req.getLong("companyId");
		long parentId = req.getLongAndSetAttr("parentId");
		CmpNav cmpNav = this.cmpNavService.getCmpNav(parentId);
		req.setAttribute("cmpNav", cmpNav);
		if (cmpNav.isArticleList()) {
			SimplePage page = req.getSimplePage(20);
			List<CmpArticle> list = this.cmpArticleService
					.getCmpArticleListByCompanyIdAndCmpNavOid(companyId,
							parentId, null, page.getBegin(), page.getSize() + 1);
			this.processListForPage(page, list);
			req.setAttribute("list", list);
		}
		else if (cmpNav.isDirectory()) {
			List<CmpNav> list = this.cmpNavProcessor
					.getCmpNavListByCompanyIdAndParentId(companyId, parentId,
							true);
			int i = 1;
			for (CmpNav o : list) {
				if (o.getOrderflg() <= 0) {
					o.setOrderflg(i);
					this.cmpNavService.updateCmpNavOrderflg(o.getOid(), i);
				}
				i++;
			}
			req.setAttribute("list", list);
		}
		return this.getWebPath("admin/cmpnav/list2.jsp");
	}

	/**
	 * 添加栏目
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String create(HkRequest req, HkResponse resp) {
		req.setAttribute("active_1", 1);
		long companyId = req.getLong("companyId");
		int ch = req.getInt("ch");
		if (ch == 0) {
			List<CmpFuncRef> funcreflist = this.cmpFuncService
					.getCmpFuncRefListByCompanyId(companyId);
			req.setAttribute("funcreflist", funcreflist);
			return this.getWebPath("admin/cmpnav/create.jsp");
		}
		String name = req.getHtmlRow("name");
		int reffunc = req.getInt("reffunc");
		byte showflg = req.getByte("showflg");
		CmpNav cmpNav = new CmpNav();
		cmpNav.setCompanyId(companyId);
		cmpNav.setName(name);
		cmpNav.setNlevel(CmpNav.NLEVEL_1);
		cmpNav.setReffunc(reffunc);
		cmpNav.setShowflg(showflg);
		cmpNav.setApplyformflg(req.getByte("applyformflg"));
		cmpNav.setFileShowflg(req.getByte("fileShowflg"));
		cmpNav.setFileShowLink(req.getString("fileShowLink"));
		cmpNav.setTitle(req.getHtmlRow("title"));
		cmpNav.setIntro(req.getHtml("intro"));
		cmpNav.setBgflg(req.getByte("bgflg"));
		int code = cmpNav.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		if (reffunc == CmpNav.REFFUNC_BBS || reffunc == CmpNav.REFFUNC_PRODUCT) {
			CmpNav o = this.cmpNavService.getCmpNavByCompanyIdAndReffunc(
					companyId, reffunc);
			if (o != null) {
				if (reffunc == CmpNav.REFFUNC_BBS) {
					return this.onError(req, Err.CMPNAV_REFFUNC_BBS_EXIST,
							"createerror", null);
				}
				return this.onError(req, Err.CMPNAV_REFFUNC_PRODUCT_EXIST,
						"createerror", null);
			}
		}
		if (cmpNav.getFileShowLink() != null
				&& cmpNav.getFileShowLink().toLowerCase().startsWith("http://")) {
			cmpNav.setFileShowLink(cmpNav.getFileShowLink().substring(7));
		}
		File f = req.getFile("f");
		File bgFile = null;
		if (cmpNav.isBgSet()) {
			bgFile = req.getFile("bgf");
		}
		code = this.cmpNavProcessor.createCmpNav(cmpNav, f, bgFile);
		if (code != Err.SUCCESS) {
			if (code == Err.IMG_OUTOFSIZE_ERROR_FOR_SIZE) {
				return this.onError(req, code, new Object[] { "200k" },
						"createerror", null);
			}
			return this.onError(req, code, "createerror", null);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", cmpNav.getOid());
	}

	/**
	 * 添加栏目
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String create2(HkRequest req, HkResponse resp) {
		req.setAttribute("active_1", 1);
		long parentId = req.getLongAndSetAttr("parentId");
		long companyId = req.getLong("companyId");
		int ch = req.getInt("ch");
		if (ch == 0) {
			CmpNav parent = this.cmpNavService.getCmpNav(parentId);
			req.setAttribute("parent", parent);
			List<CmpFuncRef> funcreflist = this.cmpFuncService
					.getCmpFuncRefListByCompanyId(companyId);
			req.setAttribute("funcreflist", funcreflist);
			return this.getWebPath("admin/cmpnav/create2.jsp");
		}
		String name = req.getHtmlRow("name");
		int reffunc = req.getInt("reffunc");
		byte showflg = req.getByte("showflg");
		CmpNav cmpNav = new CmpNav();
		cmpNav.setCompanyId(companyId);
		cmpNav.setName(name);
		cmpNav.setNlevel(CmpNav.NLEVEL_2);
		cmpNav.setReffunc(reffunc);
		cmpNav.setShowflg(showflg);
		cmpNav.setParentId(parentId);
		cmpNav.setApplyformflg(req.getByte("applyformflg"));
		cmpNav.setTitle(req.getHtmlRow("title"));
		cmpNav.setIntro(req.getHtml("intro"));
		cmpNav.setUrl(req.getHtmlRow("url"));
		int code = cmpNav.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		if (reffunc == CmpNav.REFFUNC_BBS || reffunc == CmpNav.REFFUNC_PRODUCT) {
			CmpNav o = this.cmpNavService.getCmpNavByCompanyIdAndReffunc(
					companyId, reffunc);
			if (o != null) {
				if (reffunc == CmpNav.REFFUNC_BBS) {
					return this.onError(req, Err.CMPNAV_REFFUNC_BBS_EXIST,
							"createerror", null);
				}
				return this.onError(req, Err.CMPNAV_REFFUNC_PRODUCT_EXIST,
						"createerror", null);
			}
		}
		code = this.cmpNavProcessor.createCmpNav(cmpNav, null, req
				.getFile("bgf"));
		if (code != Err.SUCCESS) {
			if (code == Err.IMG_OUTOFSIZE_ERROR_FOR_SIZE) {
				return this.onError(req, Err.IMG_OUTOFSIZE_ERROR_FOR_SIZE,
						new Object[] { "200k" }, "createerror", null);
			}
			return this.onError(req, code, "createerror", null);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", cmpNav.getOid());
	}

	/**
	 * 修改栏目
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String update(HkRequest req, HkResponse resp) {
		req.setAttribute("active_1", 1);
		long oid = req.getLongAndSetAttr("oid");
		CmpNav cmpNav = this.cmpNavService.getCmpNav(oid);
		req.setAttribute("cmpNav", cmpNav);
		int ch = req.getInt("ch");
		long companyId = req.getLong("companyId");
		if (ch == 0) {
			if (cmpNav.getKindId() > 0) {
				CmpStudyKind cmpStudyKind = this.cmpStudyKindService
						.getCmpStudyKind(companyId, cmpNav.getKindId());
				req.setAttribute("cmpStudyKind", cmpStudyKind);
			}
			List<CmpFuncRef> funcreflist = this.cmpFuncService
					.getCmpFuncRefListByCompanyId(companyId);
			req.setAttribute("funcreflist", funcreflist);
			return this.getWebPath("admin/cmpnav/update.jsp");
		}
		String name = req.getHtmlRow("name");
		int reffunc = req.getInt("reffunc");
		byte showflg = req.getByte("showflg");
		cmpNav.setName(name);
		cmpNav.setReffunc(reffunc);
		cmpNav.setShowflg(showflg);
		cmpNav.setApplyformflg(req.getByte("applyformflg"));
		cmpNav.setFileShowflg(req.getByte("fileShowflg"));
		cmpNav.setFileShowLink(req.getString("fileShowLink"));
		cmpNav.setTitle(req.getHtmlRow("title"));
		cmpNav.setIntro(req.getHtml("intro"));
		cmpNav.setBgflg(req.getByte("bgflg"));
		int code = cmpNav.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		if (reffunc == CmpNav.REFFUNC_BBS || reffunc == CmpNav.REFFUNC_PRODUCT) {
			CmpNav o = this.cmpNavService.getCmpNavByCompanyIdAndReffunc(req
					.getLong("companyId"), reffunc);
			if (o != null && o.getOid() != cmpNav.getOid()) {
				if (reffunc == CmpNav.REFFUNC_BBS) {
					return this.onError(req, Err.CMPNAV_REFFUNC_BBS_EXIST,
							"updateerror", null);
				}
				return this.onError(req, Err.CMPNAV_REFFUNC_PRODUCT_EXIST,
						"updateerror", null);
			}
		}
		File bgFile = null;
		if (cmpNav.isBgSet()) {
			bgFile = req.getFile("bgf");
		}
		code = this.cmpNavProcessor.updateCmpNav(cmpNav, req.getFile("f"),
				bgFile);
		if (code != Err.SUCCESS) {
			if (code == Err.IMG_OUTOFSIZE_ERROR_FOR_SIZE) {
				return this.onError(req, Err.IMG_OUTOFSIZE_ERROR_FOR_SIZE,
						new Object[] { "200k" }, "updateerror", null);
			}
			return this.onError(req, code, "updateerror", null);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 添加栏目
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String update2(HkRequest req, HkResponse resp) {
		req.setAttribute("active_1", 1);
		long oid = req.getLongAndSetAttr("oid");
		long parentId = req.getLongAndSetAttr("parentId");
		CmpNav cmpNav = this.cmpNavService.getCmpNav(oid);
		req.setAttribute("cmpNav", cmpNav);
		int ch = req.getInt("ch");
		long companyId = req.getLong("companyId");
		if (ch == 0) {
			CmpNav parent = this.cmpNavService.getCmpNav(parentId);
			req.setAttribute("parent", parent);
			if (cmpNav.getKindId() > 0) {
				CmpStudyKind cmpStudyKind = this.cmpStudyKindService
						.getCmpStudyKind(companyId, cmpNav.getKindId());
				req.setAttribute("cmpStudyKind", cmpStudyKind);
			}
			List<CmpFuncRef> funcreflist = this.cmpFuncService
					.getCmpFuncRefListByCompanyId(companyId);
			req.setAttribute("funcreflist", funcreflist);
			return this.getWebPath("admin/cmpnav/update2.jsp");
		}
		String name = req.getHtmlRow("name");
		int reffunc = req.getInt("reffunc");
		byte showflg = req.getByte("showflg");
		cmpNav.setName(name);
		cmpNav.setReffunc(reffunc);
		cmpNav.setShowflg(showflg);
		cmpNav.setApplyformflg(req.getByte("applyformflg"));
		cmpNav.setTitle(req.getHtmlRow("title"));
		cmpNav.setIntro(req.getHtml("intro"));
		cmpNav.setUrl(req.getHtmlRow("url"));
		int code = cmpNav.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		if (reffunc == CmpNav.REFFUNC_BBS || reffunc == CmpNav.REFFUNC_PRODUCT) {
			CmpNav o = this.cmpNavService.getCmpNavByCompanyIdAndReffunc(req
					.getLong("companyId"), reffunc);
			if (o != null && o.getOid() != cmpNav.getOid()) {
				if (reffunc == CmpNav.REFFUNC_BBS) {
					return this.onError(req, Err.CMPNAV_REFFUNC_BBS_EXIST,
							"updateerror", null);
				}
				return this.onError(req, Err.CMPNAV_REFFUNC_PRODUCT_EXIST,
						"updateerror", null);
			}
		}
		this.cmpNavProcessor.updateCmpNav(cmpNav, null, req.getFile("bgf"));
		if (code != Err.SUCCESS) {
			if (code == Err.IMG_OUTOFSIZE_ERROR_FOR_SIZE) {
				return this.onError(req, Err.IMG_OUTOFSIZE_ERROR_FOR_SIZE,
						new Object[] { "200k" }, "updateerror", null);
			}
			return this.onError(req, code, "updateerror", null);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 删除栏目
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String del(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		this.cmpNavProcessor.deleteCmpNav(oid);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String delfile(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		this.cmpNavProcessor.deleteCmpNavFile(oid);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String delbgfile(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		this.cmpNavProcessor.deleteCmpNavBgFile(oid);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * 查看栏目
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String view(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		CmpNav cmpNav = this.cmpNavService.getCmpNav(oid);
		req.setAttribute("cmpNav", cmpNav);
		String url = CmpNavUtil.getAdminFuncUrl(cmpNav);
		if (url != null) {
			return "r:" + url;
		}
		resp.sendHtml("未定义链接");
		return null;
	}

	/**
	 * 更改栏目显示顺序
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String chgpos(HkRequest req, HkResponse resp) {
		req.setAttribute("active_1", 1);
		long oid = req.getLong("oid");
		long companyId = req.getLong("companyId");
		List<CmpNav> list = this.cmpNavService
				.getCmpNavListByCompanyIdAndNlevel(companyId, CmpNav.NLEVEL_1);
		this.cmpNavProcessor.moveUpCmpNavOrderPosition(oid, list);
		return "r:/epp/web/op/webadmin/admincmpnav.do?companyId=" + companyId;
	}

	/**
	 * 更改二级栏目显示顺序
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String chgpos2(HkRequest req, HkResponse resp) {
		req.setAttribute("active_1", 1);
		long oid = req.getLong("oid");
		long parentId = req.getLong("parentId");
		long companyId = req.getLong("companyId");
		List<CmpNav> list = this.cmpNavService
				.getCmpNavListByCompanyIdAndParentId(companyId, parentId);
		this.cmpNavProcessor.moveUpCmpNavOrderPosition(oid, list);
		return "r:/epp/web/op/webadmin/admincmpnav_list2.do?companyId="
				+ companyId + "&parentId=" + parentId;
	}

	/**
	 * 调整首页内容模块显示在中间的顺序
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-5-14
	 */
	public String movemiddleup(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long oid = req.getLong("oid");
		List<CmpNav> list = this.cmpNavService.getCmpNavListByCompanyIdForHome(
				companyId, CmpNav.HOMEPOS_MIDDLE);
		this.cmpNavProcessor.moveUpCmpNavInHomePage(oid, list);
		return null;
	}

	/**
	 * 调整首页内容模块显示在右边的顺序
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-5-14
	 */
	public String moverightup(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long oid = req.getLong("oid");
		List<CmpNav> list = this.cmpNavService.getCmpNavListByCompanyIdForHome(
				companyId, CmpNav.HOMEPOS_RIGHT);
		this.cmpNavProcessor.moveUpCmpNavInHomePage(oid, list);
		return null;
	}

	/**
	 * 调整首页内容模块显示顺序，以及是否显示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-5-14
	 */
	public String showinhome(HkRequest req, HkResponse resp) {
		req.setAttribute("active_9", 1);
		long companyId = req.getLong("companyId");
		int ch = req.getInt("ch");
		if (ch == 0) {
			List<CmpNav> list = this.cmpNavProcessor
					.getCmpNavListByCompanyIdForMap(companyId);
			req.setAttribute("list", list);
			List<CmpNav> homelist = this.cmpNavService
					.getCmpNavListByCompanyIdForHome(companyId);
			List<CmpNav> middle_navlist = this.getMiddleList(homelist);
			List<CmpNav> right_navlist = this.getRightList(homelist);
			req.setAttribute("middle_navlist", middle_navlist);
			req.setAttribute("right_navlist", right_navlist);
			return this.getWebPath("admin/cmpnav/showinhome.jsp");
		}
		long oid = req.getLong("oid");
		int flg = req.getInt("flg");
		if (flg == 0) {// 设置显示
			byte homepos = req.getByte("homepos");
			this.cmpNavService.setCmpNavShowInHome(companyId, oid, homepos);
		}
		else {// 设置不显示
			CmpNav cmpNav = this.cmpNavService.getCmpNav(oid);
			if (cmpNav != null) {
				cmpNav.setShowInHome(0);
				cmpNav.setHomepos((byte) 0);
				this.cmpNavService.updateCmpNav(cmpNav);
			}
		}
		return null;
	}

	/**
	 * 为栏目选择专业
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-11
	 */
	public String selkind(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long oid = req.getLongAndSetAttr("oid");
		CmpNav cmpNav = this.cmpNavService.getCmpNav(oid);
		req.setAttribute("cmpNav", cmpNav);
		if (this.isForwardPage(req)) {
			String name = req.getHtmlRow("name");
			long parentId = req.getLongAndSetAttr("parentId");
			CmpStudyKind parent = this.cmpStudyKindService.getCmpStudyKind(
					companyId, parentId);
			req.setAttribute("parent", parent);
			SimplePage page = req.getSimplePage(20);
			List<CmpStudyKind> list = this.cmpStudyKindService
					.getCmpStudyKindListByCompanyIdAndParentIdEx(companyId,
							parentId, name, page.getBegin(), page.getSize() + 1);
			this.processListForPage(page, list);
			req.setAttribute("list", list);
			return this.getWebPath("admin/cmpnav/selkind.jsp");
		}
		long kindId = req.getLong("kindId");
		if (cmpNav != null) {
			CmpStudyKind cmpStudyKind = this.cmpStudyKindService
					.getCmpStudyKind(companyId, kindId);
			if (cmpStudyKind == null) {
				return null;
			}
			cmpNav.setKindId(kindId);
			this.cmpNavService.updateCmpNav(cmpNav);
			this.setOpFuncSuccessMsg(req);
		}
		String return_url = req.getReturnUrl();
		if (DataUtil.isEmpty(return_url)) {
			return null;
		}
		return "r:" + return_url;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-1
	 */
	public String setcssdata(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		CmpNav cmpNav = this.cmpNavService.getCmpNav(oid);
		CmpNavPageCssObj cmpNavPageCssObj = new CmpNavPageCssObj(cmpNav
				.getCssData());
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpNav", cmpNav);
			req.setAttribute("cmpNavPageCssObj", cmpNavPageCssObj);
			return this.getWebPath("admin/cmpnav/cssdata.jsp");
		}
		cmpNavPageCssObj.setNavLinkColor(req.getHtmlRow("navLinkColor"));
		cmpNavPageCssObj.setLinkColor(req.getHtmlRow("linkColor"));
		cmpNavPageCssObj.setBgColor(req.getHtmlRow("bgColor"));
		cmpNav.setCssData(cmpNavPageCssObj.toJsonValue());
		this.cmpNavService.updateCmpNav(cmpNav);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "setok", null);
	}
}