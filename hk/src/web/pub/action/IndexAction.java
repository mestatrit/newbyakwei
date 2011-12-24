package web.pub.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpAd;
import com.hk.bean.CmpArticle;
import com.hk.bean.CmpContact;
import com.hk.bean.CmpFile;
import com.hk.bean.CmpFrLink;
import com.hk.bean.CmpHomePicAd;
import com.hk.bean.CmpInfo;
import com.hk.bean.CmpNav;
import com.hk.bean.CmpPageBlock;
import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductSort;
import com.hk.bean.CmpProductSortFile;
import com.hk.bean.Company;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpAdService;
import com.hk.svr.CmpArticleService;
import com.hk.svr.CmpContactService;
import com.hk.svr.CmpFileService;
import com.hk.svr.CmpFrLinkService;
import com.hk.svr.CmpHomePicAdService;
import com.hk.svr.CmpModService;
import com.hk.svr.CmpNavService;
import com.hk.svr.CmpProductService;
import com.hk.svr.CmpProductSortFileService;

/**
 * 企业网站首页
 * 
 * @author akwei
 */
@Component("/epp/index")
public class IndexAction extends EppBaseAction {

	@Autowired
	private CmpNavService cmpNavService;

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private CmpContactService cmpContactService;

	@Autowired
	private CmpArticleService cmpArticleService;

	@Autowired
	private CmpFileService cmpFileService;

	@Autowired
	private CmpAdService cmpAdService;

	@Autowired
	private CmpHomePicAdService cmpHomePicAdService;

	@Autowired
	private CmpProductSortFileService cmpProductSortFileService;

	@Autowired
	private CmpModService cmpModService;

	@Autowired
	private CmpFrLinkService cmpFrLinkService;

	/**
	 * 根据标示来判断是进入wap或者web
	 */
	public String execute(HkRequest req, HkResponse resp) {
		if (ServletUtil.isWap(req) || req.getInt("wapflg") == 1) {
			return this.wap(req, resp);
		}
		return this.web(req, resp);
	}

	/**
	 * web版首页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-20
	 */
	public String web(HkRequest req, HkResponse resp) {
		Company company = (Company) req.getAttribute("o");
		// CmpInfo cmpInfo=(CmpInfo)req.getAttribute("cmpInfo");
		if (company.getCmpflg() == 0) {
			return this.web0(req, resp);
		}
		else if (company.getCmpflg() == 1) {
			return this.web1(req, resp);
		}
		else if (company.getCmpflg() == 2) {
			return this.web2(req, resp);
		}
		else if (company.isCmpHairDressing()) {
			return this.web3(req, resp);
		}
		return null;
	}

	/**
	 * web版首页(企业0模板)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-20
	 */
	private String web0(HkRequest req, HkResponse resp) {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.web00(req, resp);
		}
		if (tmlflg == 1) {
			return this.web01(req, resp);
		}
		return null;
	}

	/**
	 * web版首页(企业0模板)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-20
	 */
	private String web00(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		List<CmpNav> navlist = this.cmpNavService
				.getCmpNavListByCompanyIdForHome(companyId);
		List<CmpNav> middle_navlist = this.getMiddleList(navlist);
		List<CmpNav> right_navlist = this.getRightList(navlist);
		req.setAttribute("middle_navlist", middle_navlist);
		req.setAttribute("right_navlist", right_navlist);
		// 推荐的产品
		List<CmpProduct> cmpproductlist = this.cmpProductService
				.getCmpProductListByCompanyIdForCmppink(companyId, 0, 6);
		req.setAttribute("cmpproductlist", cmpproductlist);
		if (cmpproductlist.size() > 0) {
			// 查找哪个导航挂接了产品
			CmpNav product_CmpNav = this.cmpNavService
					.getCmpNavByCompanyIdAndReffunc(companyId,
							CmpNav.REFFUNC_PRODUCT);
			if (product_CmpNav == null) {
				cmpproductlist.clear();
			}
			else {
				req.setAttribute("product_cmpnavid", product_CmpNav.getOid());
			}
		}
		// 在线联系
		List<CmpContact> cmpcontactlist = this.cmpContactService
				.getCmpContactListByCompanyId(companyId);
		req.setAttribute("cmpcontactlist", cmpcontactlist);
		// 首页导航信息
		CmpNav top_cmpNav = this.cmpNavService.getHomeCmpNav(companyId);
		req.setAttribute("top_cmpNav", top_cmpNav);
		// 首页推荐文章
		CmpArticle home_cmparticle = this.cmpArticleService
				.getCmpArticleForHomepink(companyId);
		if (home_cmparticle != null) {
			CmpFile topcmpfile = this.getTopCmpFile(companyId, home_cmparticle
					.getOid());
			req.setAttribute("topcmpfile", topcmpfile);
			req.setAttribute("home_cmparticle", home_cmparticle);
		}
		// 广告
		List<CmpAd> cmpadlist = this.cmpAdService.getCmpAdListByCompanyId(
				companyId, 0, 0, 5);
		req.setAttribute("cmpadlist", cmpadlist);
		return this.getWebPath("index/index.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-7-30
	 */
	private String web01(HkRequest req, HkResponse resp) {
		return this.getWebPath("mod/0/1/index/index.jsp");
	}

	/**
	 * web版首页(企业0模板)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-20
	 */
	private String web1(HkRequest req, HkResponse resp) {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.web10(req, resp);
		}
		return null;
	}

	/**
	 * web版首页(教育)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-20
	 */
	private String web2(HkRequest req, HkResponse resp) {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.web20(req, resp);
		}
		return null;
	}

	/**
	 * web版首页(教育)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-20
	 */
	private String web3(HkRequest req, HkResponse resp) {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.web30(req, resp);
		}
		return null;
	}

	/**
	 * web版首页(企业2/0模板)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-20
	 */
	private String web30(HkRequest req, HkResponse resp) {
		return this.getWebPath("mod/3/0/index/index.jsp");
	}

	/**
	 * web版首页(企业2/0模板)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-20
	 */
	@SuppressWarnings("unchecked")
	private String web20(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		req.reSetAttribute("needRefresh");
		// 获取页面区块数据
		List<CmpPageBlock> cmppageblocklist = this.cmpModService
				.getCmpPageBlockListByCompanyIdAndPageflg(companyId, (byte) 1);
		req.setAttribute("cmppageblocklist", cmppageblocklist);
		for (CmpPageBlock o : cmppageblocklist) {
			if (o.getPageModId() == 11) {// 友情链接
				List<CmpFrLink> cmpfrlinklist = (ArrayList<CmpFrLink>) req
						.getAttribute("cmpfrlinklist");
				if (cmpfrlinklist == null) {
					cmpfrlinklist = this.cmpFrLinkService
							.getCmpFrLinkListByCompanyId(companyId);
					req.setAttribute("cmpfrlinklist", cmpfrlinklist);
				}
			}
			if (o.getPageModId() == 10) {
				// 合作伙伴暂时没有功能
			}
		}
		return this.getWebPath("mod/2/0/index/index.jsp");
	}

	/**
	 * web版首页(企业1/0模板)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-20
	 */
	private String web10(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		List<CmpHomePicAd> cmphomepicadlist = this.cmpHomePicAdService
				.getCmpHomePicAdListByCompanyId(companyId, 0, 4);
		req.setAttribute("cmphomepicadlist", cmphomepicadlist);
		// 最小分类的推荐
		List<CmpProductSort> sortlist = this.cmpProductService
				.getCmpProductSortListByCompanyIdAndChildflg(companyId,
						CmpProductSort.CHILDFLG_N);
		List<CmpProductSortVo> cmpproductsortvolist = new ArrayList<CmpProductSortVo>();
		// 所有分类广告
		List<CmpProductSortFile> cmpproductsortfilelist = this.cmpProductSortFileService
				.getCmpProductSortFileListByCompanyId(companyId);
		List<Long> articleIdList = new ArrayList<Long>();
		for (CmpProductSort o : sortlist) {
			// 加载广告
			List<CmpProductSortFile> list = new ArrayList<CmpProductSortFile>();
			for (CmpProductSortFile file : cmpproductsortfilelist) {
				if (file.getSortId() == o.getSortId()) {
					list.add(file);
				}
			}
			CmpProductSortVo vo = new CmpProductSortVo();
			vo.setCmpProductSort(o);
			// 加载产品
			vo.setPinkList(this.cmpProductService
					.getCmpProductListByCompanyIdAndSortIdForCmppink(companyId,
							o.getSortId(), 0, 7));
			vo.setCmpProductSortFileList(list);
			// 加载推荐文章
			vo.setPinkCmpArticleList(this.cmpArticleService
					.getCmpArticleListByCompanyIdAndSortIdForCmppink(companyId,
							o.getSortId(), 0, 8));
			for (CmpArticle article : vo.getPinkCmpArticleList()) {
				articleIdList.add(article.getOid());
			}
			cmpproductsortvolist.add(vo);
		}
		List<CmpArticle> pinkarticlelist = this.cmpArticleService
				.getCmpArticleListByCompanyIdAndNotInIdForCmppink(companyId,
						articleIdList, 0, 10);
		req.setAttribute("pinkarticlelist", pinkarticlelist);
		req.setAttribute("cmpproductsortvolist", cmpproductsortvolist);
		return this.getWebPath("mod/1/0/index/index.jsp");
	}

	private CmpFile getTopCmpFile(long companyId, long oid) {
		List<CmpFile> home_cmparticle_file_list = this.cmpFileService
				.getCmpFileListByCompanyIdAndArticleOid(companyId, oid);
		boolean hastopimgfile = false;
		// 先取设置的头图
		for (CmpFile o : home_cmparticle_file_list) {
			if (o.isTopInFile() && o.isImageShow()) {
				return o;
			}
		}
		// 如果没有设置头图，就找第一张图片
		if (!hastopimgfile) {
			for (CmpFile o : home_cmparticle_file_list) {
				if (o.isImageShow()) {
					return o;
				}
			}
		}
		return null;
	}

	/**
	 * web版首页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-20
	 */
	public String wap(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		List<CmpNav> navlist = this.cmpNavService
				.getCmpNavListByCompanyIdAndNlevel(companyId, CmpNav.NLEVEL_1);
		for (CmpNav o : navlist) {
			if (o.isHomeNav()) {
				navlist.remove(o);
				break;
			}
		}
		req.setAttribute("navlist", navlist);
		return this.getWapPath("index.jsp");
	}
}