package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.BizCircle;
import com.hk.bean.City;
import com.hk.bean.CmpComment;
import com.hk.bean.CmpProduct;
import com.hk.bean.CmpTip;
import com.hk.bean.Company;
import com.hk.bean.CompanyFeed;
import com.hk.bean.CompanyKind;
import com.hk.bean.CompanyKindUtil;
import com.hk.bean.CompanyPhoto;
import com.hk.bean.CompanyReview;
import com.hk.bean.CompanyUserScore;
import com.hk.bean.CompanyUserStatus;
import com.hk.bean.Country;
import com.hk.bean.HkObjArticle;
import com.hk.bean.IpCity;
import com.hk.bean.IpCityRange;
import com.hk.bean.IpCityUser;
import com.hk.bean.Laba;
import com.hk.bean.Pcity;
import com.hk.bean.Province;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BizCircleService;
import com.hk.svr.CmpCommentService;
import com.hk.svr.CmpProductService;
import com.hk.svr.CmpTipService;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.HkObjArticleService;
import com.hk.svr.IpCityService;
import com.hk.svr.LabaService;
import com.hk.svr.UserService;
import com.hk.svr.ZoneService;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.hk4.venue.action.CmpTipVo;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.IpZoneInfo;
import com.hk.web.pub.action.LabaParserCfg;
import com.hk.web.pub.action.LabaVo;

@Component("/e/cmp")
public class CompanyAction extends BaseAction {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private BizCircleService bizCircleService;

	@Autowired
	private CompanyPhotoService companyPhotoService;

	@Autowired
	private UserService userService;

	@Autowired
	private CmpCommentService cmpCommentService;

	@Autowired
	private HkObjArticleService hkObjArticleService;

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private LabaService labaService;

	@Autowired
	private CmpTipService cmpTipService;

	private int size = 20;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.wap(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String web2(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		User loginUser = this.getLoginUser(req);
		if (o == null) {
			return null;
		}
		boolean morereview = false;
		boolean morereflaba = false;
		boolean authed = false;
		boolean caneiteimg = false;
		boolean caneditcompany = false;
		boolean hasReview = false;
		if (o.getUserId() > 0) {
			authed = true;
		}
		if (loginUser != null) {
			if (authed) {
				if (loginUser.getUserId() == o.getUserId()) {
					caneiteimg = true;
					caneditcompany = true;
				}
			}
			else {
				caneiteimg = true;
				if (o.getCreaterId() == loginUser.getUserId()) {
					caneditcompany = true;
				}
			}
		}
		List<CompanyReview> reviewlist = null;
		if (loginUser != null) {
			reviewlist = this.companyService
					.getCompanyReviewListByCompanyIdNoUser(companyId, loginUser
							.getUserId(), 0, 21);
		}
		else {
			reviewlist = this.companyService.getCompanyReviewListByCompanyId(
					companyId, 0, 21);
		}
		List<CompanyPhoto> photolist = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, 0, 30);
		req.setAttribute("photolist", photolist);
		if (reviewlist.size() > 20) {
			morereview = true;
			reviewlist.remove(20);
		}
		if (loginUser != null) {
			CompanyUserScore companyUserScore = this.companyService
					.getCompanyUserScore(companyId, loginUser.getUserId());
			req.setAttribute("companyUserScore", companyUserScore);
			List<CompanyReview> myreviewlist = this.companyService
					.getUserCompanyReviewList(companyId, loginUser.getUserId(),
							0, 1);
			if (myreviewlist.size() > 0) {
				hasReview = true;
			}
			req.setAttribute("myreviewvolist", CompanyReviewVo.createVoList(
					myreviewlist, this.getUrlInfo(req)));
		}
		req.setAttribute("hasReview", hasReview);
		req.setAttribute("caneditcompany", caneditcompany);
		req.setAttribute("caneiteimg", caneiteimg);
		req.setAttribute("authed", authed);
		req.setAttribute("morereview", morereview);
		req.setAttribute("morereflaba", morereflaba);
		req.setAttribute("vo", new CompanyVo(o));
		req.setAttribute("companyreviewvolist", CompanyReviewVo.createVoList(
				reviewlist, this.getUrlInfo(req)));
		req.setAttribute("companyId", companyId);
		if (loginUser != null) {
			CompanyUserStatus companyUserStatus = this.companyService
					.getCompanyUserStatus(companyId, loginUser.getUserId());
			req.setAttribute("companyUserStatus", companyUserStatus);
		}
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(o.getKindId());
		req.setAttribute("companyKind", companyKind);
		List<HkObjArticle> articlelist = this.hkObjArticleService
				.getHkObjArticleListByOid(companyId, 0, 5);
		req.setAttribute("articlelist", articlelist);
		// 潮人榜数据
		Pcity pcity = ZoneUtil.getPcity(o.getPcityId());
		if (pcity != null) {
			String zoneName = DataUtil.filterZoneName(pcity.getName());
			IpCity ipCity = this.ipCityService.getIpCityByNameLike(zoneName);
			if (ipCity != null) {
				this.processUserModule(req, ipCity.getCityId());
			}
		}
		// 足迹产品
		List<CmpProduct> productlist = this.cmpProductService
				.getCmpProductList(companyId, 0, 0, 0, 3);
		req.setAttribute("productlist", productlist);
		// 附近的咖啡厅
		List<Company> nearbylist = this.companyService.getCompanyListNearBy(
				companyId, o.getParentKindId(), o.getPcityId(), o.getMarkerX(),
				o.getMarkerY(), 0, 8);
		req.setAttribute("nearbylist", nearbylist);
		// 这个地区的最新其他足迹
		List<Company> othercmplist = this.companyService
				.getCompanyListByPcityId(o.getPcityId(), 0, 7);
		// 找出当前的足迹，并从列表中删除
		Company removeObj = null;
		for (Company c : othercmplist) {
			if (c.getCompanyId() == companyId) {
				removeObj = c;
				break;
			}
		}
		if (removeObj != null) {
			othercmplist.remove(removeObj);
		}
		if (othercmplist.size() > 6) {
			othercmplist.remove(6);
		}
		req.setAttribute("othercmplist", othercmplist);
		req.setAttribute("cityId", o.getPcityId());
		return this.getWeb3Jsp("e/company.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String web(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		User loginUser = this.getLoginUser(req);
		if (o == null) {
			return null;
		}
		boolean morereview = false;
		boolean morereflaba = false;
		boolean authed = false;
		boolean caneiteimg = false;
		boolean caneditcompany = false;
		boolean hasReview = false;
		if (o.getUserId() > 0) {
			authed = true;
		}
		if (loginUser != null) {
			if (authed) {
				if (loginUser.getUserId() == o.getUserId()) {
					caneiteimg = true;
					caneditcompany = true;
				}
			}
			else {
				caneiteimg = true;
				if (o.getCreaterId() == loginUser.getUserId()) {
					caneditcompany = true;
				}
			}
		}
		List<CompanyReview> reviewlist = null;
		if (loginUser != null) {
			reviewlist = this.companyService
					.getCompanyReviewListByCompanyIdNoUser(companyId, loginUser
							.getUserId(), 0, 21);
		}
		else {
			reviewlist = this.companyService.getCompanyReviewListByCompanyId(
					companyId, 0, 21);
		}
		List<CompanyPhoto> photolist = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, 0, 30);
		req.setAttribute("photolist", photolist);
		if (reviewlist.size() > 20) {
			morereview = true;
			reviewlist.remove(20);
		}
		if (loginUser != null) {
			CompanyUserScore companyUserScore = this.companyService
					.getCompanyUserScore(companyId, loginUser.getUserId());
			req.setAttribute("companyUserScore", companyUserScore);
			List<CompanyReview> myreviewlist = this.companyService
					.getUserCompanyReviewList(companyId, loginUser.getUserId(),
							0, 1);
			if (myreviewlist.size() > 0) {
				hasReview = true;
			}
			req.setAttribute("myreviewvolist", CompanyReviewVo.createVoList(
					myreviewlist, this.getUrlInfo(req)));
		}
		req.setAttribute("hasReview", hasReview);
		req.setAttribute("caneditcompany", caneditcompany);
		req.setAttribute("caneiteimg", caneiteimg);
		req.setAttribute("authed", authed);
		req.setAttribute("morereview", morereview);
		req.setAttribute("morereflaba", morereflaba);
		req.setAttribute("vo", new CompanyVo(o));
		req.setAttribute("companyreviewvolist", CompanyReviewVo.createVoList(
				reviewlist, this.getUrlInfo(req)));
		req.setAttribute("companyId", companyId);
		if (loginUser != null) {
			CompanyUserStatus companyUserStatus = this.companyService
					.getCompanyUserStatus(companyId, loginUser.getUserId());
			req.setAttribute("companyUserStatus", companyUserStatus);
		}
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(o.getKindId());
		req.setAttribute("companyKind", companyKind);
		List<HkObjArticle> articlelist = this.hkObjArticleService
				.getHkObjArticleListByOid(companyId, 0, 5);
		req.setAttribute("articlelist", articlelist);
		// 潮人榜数据
		Pcity pcity = ZoneUtil.getPcity(o.getPcityId());
		if (pcity != null) {
			String zoneName = DataUtil.filterZoneName(pcity.getName());
			IpCity ipCity = this.ipCityService.getIpCityByNameLike(zoneName);
			if (ipCity != null) {
				this.processUserModule(req, ipCity.getCityId());
			}
		}
		// 足迹产品
		List<CmpProduct> productlist = this.cmpProductService
				.getCmpProductList(companyId, 0, 0, 0, 3);
		req.setAttribute("productlist", productlist);
		// 附近的咖啡厅
		List<Company> nearbylist = this.companyService.getCompanyListNearBy(
				companyId, o.getParentKindId(), o.getPcityId(), o.getMarkerX(),
				o.getMarkerY(), 0, 8);
		req.setAttribute("nearbylist", nearbylist);
		// 这个地区的最新其他足迹
		List<Company> othercmplist = this.companyService
				.getCompanyListByPcityId(o.getPcityId(), 0, 7);
		// 找出当前的足迹，并从列表中删除
		Company removeObj = null;
		for (Company c : othercmplist) {
			if (c.getCompanyId() == companyId) {
				removeObj = c;
				break;
			}
		}
		if (removeObj != null) {
			othercmplist.remove(removeObj);
		}
		if (othercmplist.size() > 6) {
			othercmplist.remove(6);
		}
		req.setAttribute("othercmplist", othercmplist);
		req.setAttribute("cityId", o.getPcityId());
		return this.getWebJsp("e/company.jsp");
	}

	private void processAllUserModule(HkRequest req) {
		List<User> userlist = this.userService.getUserListSortUserRecentUpdate(
				0, 8);
		req.setAttribute("listalluser", true);
		req.setAttribute("userlist", userlist);
	}

	private void processUserModule(HkRequest req, int ipCityId) {
		List<IpCityUser> list = this.userService
				.getIpCityUserListSortUserRecentUpdate(ipCityId, 0, 8);
		if (list.size() == 0) {
			this.processAllUserModule(req);
			return;
		}
		List<Long> idList = new ArrayList<Long>();
		for (IpCityUser o : list) {
			idList.add(o.getUserId());
		}
		List<User> userlist = this.userService.getUserListInId(idList);
		req.setAttribute("userlist", userlist);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String wap(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		User loginUser = this.getLoginUser(req);
		if (o == null) {
			return null;
		}
		boolean hasphoto = false;
		boolean morereflaba = false;
		boolean authed = false;
		boolean caneiteimg = false;
		boolean caneditcompany = false;
		boolean hasReview = false;
		if (o.getUserId() > 0) {
			authed = true;
		}
		if (loginUser != null) {
			if (authed) {
				if (loginUser.getUserId() == o.getUserId()) {
					caneiteimg = true;
					caneditcompany = true;
				}
			}
			else {
				caneiteimg = true;
				if (o.getCreaterId() == loginUser.getUserId()) {
					caneditcompany = true;
				}
			}
		}
		int photocount = this.companyPhotoService.countPhotoByCompanyIdNoLogo(
				companyId, o.getHeadPath());
		if (photocount > 0) {
			hasphoto = true;
		}
		// LabaServiceWrapper wrapper = new LabaServiceWrapper();
		List<Laba> reflabalist = labaService
				.getLabaListByCompanyIdFromCompanyRefLaba(companyId, 0, 6);
		LabaParserCfg cfg = this.getLabaParserCfg(req);
		List<LabaVo> labavolist = LabaVo.createVoList(reflabalist, cfg);
		if (loginUser != null) {
			CompanyUserScore companyUserScore = this.companyService
					.getCompanyUserScore(companyId, loginUser.getUserId());
			req.setAttribute("companyUserScore", companyUserScore);
		}
		req.setAttribute("hasReview", hasReview);
		req.setAttribute("labavolist", labavolist);
		req.setAttribute("caneditcompany", caneditcompany);
		req.setAttribute("caneiteimg", caneiteimg);
		req.setAttribute("authed", authed);
		req.setAttribute("morereflaba", morereflaba);
		req.setAttribute("hasphoto", hasphoto);
		req.setAttribute("vo", new CompanyVo(o));
		req.setAttribute("companyId", companyId);
		List<CmpComment> cmtlist = this.cmpCommentService.getCmpCommentList(
				companyId, 0, 4);
		if (cmtlist.size() == 4) {
			req.setAttribute("morecmt", true);
			cmtlist.remove(3);
		}
		List<CmpCommentVo> cmtvolist = CmpCommentVo.createVoList(cmtlist, this
				.getUrlInfo(req), false);
		req.setAttribute("cmtvolist", cmtvolist);
		if (loginUser != null) {
			CompanyUserStatus companyUserStatus = this.companyService
					.getCompanyUserStatus(companyId, loginUser.getUserId());
			req.setAttribute("companyUserStatus", companyUserStatus);
		}
		// 产品
		List<CmpProduct> productlist = this.cmpProductService
				.getCmpProductList(companyId, 0, 0, 0, 6);
		if (productlist.size() == 6) {
			req.setAttribute("hasmoreproduct", true);
			productlist.remove(5);
		}
		List<ProductVo> productvolist = ProductVo.createVoList(productlist,
				this.getShoppingCard(req));
		req.setAttribute("productvolist", productvolist);
		// 去过想去list数据
		long excludeUserId = 0;
		List<CmpTip> mycmptiplist = null;
		if (loginUser != null) {
			excludeUserId = loginUser.getUserId();
			mycmptiplist = this.cmpTipService
					.getCmpTipListByCompanyIdAndUserId(companyId, loginUser
							.getUserId(), 0, 2);
			req.setAttribute("mycmptiplist", mycmptiplist);
		}
		List<CmpTip> cmptiplist = this.cmpTipService
				.getCmpTipListByCompanyIdExcluedUserId(companyId,
						excludeUserId, 0, 21);
		if (cmptiplist.size() == 21) {
			req.setAttribute("more_tip", true);
			cmptiplist.remove(20);
		}
		if (mycmptiplist != null) {
			cmptiplist.addAll(mycmptiplist);
		}
		List<CmpTipVo> cmptipvolist = CmpTipVo
				.createVoList(cmptiplist, 0, true);
		req.setAttribute("cmptipvolist", cmptipvolist);
		req.setAttribute("dongflg", CmpTip.DONEFLG_DONE);
		return "/WEB-INF/page/e/company.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String viewbyname(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("v");
		Company o = this.companyService.getCompanyByName(name);
		if (o == null) {
			return null;
		}
		return "r:/e/cmp.do?companyId=" + o.getCompanyId();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cmt(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		SimplePage page = req.getSimplePage(size);
		List<CmpComment> list = cmpCommentService.getCmpCommentList(companyId,
				page.getBegin(), size);
		page.setListSize(list.size());
		List<CmpCommentVo> cmtvolist = CmpCommentVo.createVoList(list, this
				.getUrlInfo(req), false);
		req.setAttribute("company", company);
		req.setAttribute("cmtvolist", cmtvolist);
		req.setAttribute("companyId", companyId);
		return "/WEB-INF/page/e/cmt.jsp";
	}

	/**
	 * 附近动态
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String map(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		if (o == null) {
			return this.getNotFoundForward(resp);
		}
		double pointX = req.getDouble("pointX");
		double pointY = req.getDouble("pointY");
		int zoom = req.getInt("zoom", 12);
		if (zoom > 19) {
			zoom = 19;
		}
		if (zoom < 10) {
			zoom = 10;
		}
		if (pointX == 0) {
			pointX = o.getMarkerX();
		}
		if (pointY == 0) {
			pointY = o.getMarkerY();
		}
		req.setAttribute("zoom", zoom);
		req.setAttribute("pointX", pointX);
		req.setAttribute("pointY", pointY);
		req.setAttribute("markerX", o.getMarkerX());
		req.setAttribute("markerY", o.getMarkerY());
		req.setAttribute("companyId", companyId);
		req.setAttribute("vo", new CompanyVo(o));
		return "/WEB-INF/page/e/map.jsp";
	}

	/**
	 * 附近动态
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String feedlist(HkRequest req, HkResponse resp) throws Exception {
		String ip = req.getRemoteAddr();
		IpCityRange range = this.ipCityService.getIpCityRange(ip);
		List<CompanyFeed> list = null;
		SimplePage page = req.getSimplePage(size);
		if (range == null) {
			list = this.companyService
					.getCompanyFeedList(page.getBegin(), size);
		}
		else {
			list = this.companyService.getCompanyFeedListByCity(range
					.getCityId(), page.getBegin(), size);
			if (list.size() == 0) {// 如果没有数据,就取所有
				list = this.companyService.getCompanyFeedList(page.getBegin(),
						size);
			}
		}
		List<CompanyFeedVo> companyfeedvolist = CompanyFeedVo
				.createVoList(list);
		req.setAttribute("companyfeedvolist", companyfeedvolist);
		return "/WEB-INF/page/e/feedlist.jsp";
	}

	/**
	 * ip附近足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String nearby(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		IpZoneInfo ipZoneInfo = this.getIpZoneInfo(req);
		if (ipZoneInfo.isHasInfo()) {
			return "r:/e/cmp_list.do?cityId=" + ipZoneInfo.getCityId()
					+ "&ipsel=1";// ipsel是否是选择了城市1:是,页面就会显示本市被选中
		}
		if (loginUser != null) {// 找不到对应足迹，查找当前用户最后一次访问的城市或者省份的id
			User user = this.userService.getUser(loginUser.getUserId());
			// 参数ipsel是否是选择了城市1:是,页面就会显示本市被选中
			if (user.getPcityId() > 0) {
				return "r:/e/cmp_list.do?cityId=" + user.getPcityId()
						+ "&ipsel=1";
			}
		}
		return "r:/e/cmp_tosearchcity.do";// 没有找到相关地区,请用户输入查询
	}

	/**
	 * 地区分类足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list2(HkRequest req, HkResponse resp) throws Exception {
		// if (this.isPcBrowse(req)) {
		// return this.list2web(req, resp);
		// }
		return this.list2wap(req, resp);
	}

	/**
	 * wap浏览
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list2wap(HkRequest req, HkResponse resp) throws Exception {
		int cityId = req.getInt("cityId");
		String name = req.getString("name");
		if (cityId == 0 && DataUtil.isEmpty(name)) {
			req.setAttribute("listall", true);
		}
		int kindId = req.getInt("kindId");
		SimplePage page = req.getSimplePage(size);
		User loginUser = this.getLoginUser(req);
		List<Company> list = this.companyService.getCompanyListEx(kindId,
				cityId, name, null, null, page.getBegin(), size);
		page.setListSize(list.size());
		List<CompanyVo> volist = CompanyVo.createVoList(list);
		if (cityId == 0) {
			IpZoneInfo ipZoneInfo = new IpZoneInfo(req.getRemoteAddr());
			cityId = ipZoneInfo.getCityId();
		}
		if (cityId == 0 && loginUser != null) {
			User user = this.userService.getUser(loginUser.getUserId());
			cityId = user.getPcityId();
		}
		City city = ZoneUtil.getCity(cityId);
		req.setAttribute("companyvolist", volist);
		req.setAttribute("cityId", cityId);
		req.setAttribute("city", city);
		req.setAttribute("kindId", kindId);
		CompanyKind kind = CompanyKindUtil.getCompanyKind(kindId);
		req.setAttribute("kind", kind);
		req.setEncodeAttribute("name", name);
		return "/WEB-INF/page/e/list2.jsp";
	}

	/**
	 * 地区足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		int cityId = req.getIntAndSetAttr("cityId");
		int all = req.getIntAndSetAttr("all");
		if (cityId <= 0) {
			all = 1;
		}
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(size);
		List<Company> companylist = null;
		if (all == 0) {// 查看本城市
			if (DataUtil.isEmpty(name)) {
				companylist = this.companyService.getCompanyListByPcityId(
						cityId, page.getBegin(), page.getSize() + 1);
			}
			else {
				companylist = this.companyService
						.getCompanyListByPcityIdAndNameLike(cityId, name, page
								.getBegin(), page.getSize() + 1);
			}
		}
		else {// 全部
			if (DataUtil.isEmpty(name)) {
				companylist = this.companyService.getCompanyListByPcityId(0,
						page.getBegin(), page.getSize() + 1);
			}
			else {
				List<Long> idList = this.companyService
						.getCompanyIdListWithSearch(name, page.getBegin(), page
								.getSize() + 1);
				Map<Long, Company> map = this.companyService
						.getCompanyMapInId(idList);
				companylist = new ArrayList<Company>();
				for (Long l : idList) {
					Company o = map.get(l.longValue());
					if (o != null) {
						companylist.add(o);
					}
				}
			}
		}
		this.processListForPage(page, companylist);
		req.setEncodeAttribute("name", name);
		req.setAttribute("companylist", companylist);
		req.setReturnUrl("/index_changecity2.do");
		return this.getWapJsp("e/list.jsp");
	}

	/**
	 * 全部足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String all(HkRequest req, HkResponse resp) throws Exception {
		int cityId = req.getInt("cityId");
		int provinceId = req.getInt("provinceId");
		int kindId = req.getInt("kindId");
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(size);
		List<Company> list = this.companyService.getCompanyListEx(kindId,
				cityId, name, null, null, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		List<CompanyVo> volist = CompanyVo.createVoList(list);
		City city = ZoneUtil.getCity(cityId);
		Province province = ZoneUtil.getProvince(provinceId);
		req.setAttribute("companyvolist", volist);
		req.setAttribute("cityId", cityId);
		req.setAttribute("provinceId", provinceId);
		req.setAttribute("city", city);
		req.setAttribute("province", province);
		req.setAttribute("kindId", kindId);
		req.setEncodeAttribute("name", name);
		return "/WEB-INF/page/e/all.jsp";
	}

	/**
	 * 所在城市足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String citycmp(HkRequest req, HkResponse resp) throws Exception {
		int cityId = req.getInt("cityId");
		int provinceId = req.getInt("provinceId");
		City city = ZoneUtil.getCity(cityId);
		Province province = ZoneUtil.getProvince(provinceId);
		SimplePage page = req.getSimplePage(size);
		List<Company> list = this.companyService.getCompanyListByPcityId(
				cityId, page.getBegin(), size);
		page.setListSize(list.size());
		List<CompanyVo> volist = CompanyVo.createVoList(list);
		req.setAttribute("companyvolist", volist);
		req.setAttribute("city", city);
		req.setAttribute("cityId", cityId);
		req.setAttribute("province", province);
		req.setAttribute("provinceId", provinceId);
		req.reSetAttribute("ipsel");
		return "/WEB-INF/page/e/citycmp.jsp";
	}

	/**
	 * 到查询地区或城市页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tosearchcity(HkRequest req, HkResponse resp) throws Exception {
		List<Country> countrylist = ZoneUtil.getCountryList();
		req.setAttribute("countrylist", countrylist);
		req.reSetAttribute("fn");
		return "/WEB-INF/page/e/searchcity.jsp";
	}

	/**
	 * 附近足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String searchcity(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		String name = req.getString("name");
		if (!DataUtil.isEmpty(name)) {
			name = DataUtil.filterZoneName(name);
		}
		int countryId = req.getInt("countryId");
		City city = this.zoneService.getCityLike(name);
		int cityId = 0;
		if (city != null) {
			cityId = city.getCityId();
		}
		if (cityId > 0) {
			if (loginUser != null) {
				User user = this.userService.getUser(loginUser.getUserId());
				if (user.getPcityId() != cityId) {
					user.setPcityId(cityId);// 更新最后访问信息
					this.userService.updateUserPcityId(loginUser.getUserId(),
							cityId);
				}
				loginUser.setPcityId(cityId);
			}
		}
		if (cityId > 0) {
			return "r:/e/cmp_list.do?cityId=" + cityId;
		}
		boolean noresult = true;
		req.setAttribute("noresult", noresult);
		req.setAttribute("countryId", countryId);
		return "/WEB-INF/page/e/searchcity.jsp";
	}

	/**
	 * 到查询地区或城市页面,为查询商圈使用
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tosearchcityforbc(HkRequest req, HkResponse resp)
			throws Exception {
		String name = req.getString("name");
		if (name != null) {
			List<City> list = this.zoneService.getCityList(name);
			if (list.size() == 0) {// 如果城市列表中不存在,就到省表里面查询
				List<Province> plist = this.zoneService.getProvinceList(name);
				if (plist.size() > 0) {
					list = this.zoneService.getCityList(plist.iterator().next()
							.getProvinceId());
				}
			}
			req.setAttribute("list", list);
			req.setEncodeAttribute("name", name);
			req.setAttribute("search", true);
		}
		return "/WEB-INF/page/e/searchcityforbc.jsp";
	}

	/**
	 * 查询城市下的商圈列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String bclist(HkRequest req, HkResponse resp) throws Exception {
		int cityId = req.getInt("cityId");
		List<BizCircle> list = this.bizCircleService.getBizCircleList(null,
				cityId, 0);
		req.setAttribute("list", list);
		req.setAttribute("cityId", cityId);
		return "/WEB-INF/page/e/bclist.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String bccmplist(HkRequest req, HkResponse resp) throws Exception {
		int circleId = req.getInt("circleId");
		SimplePage page = req.getSimplePage(size);
		List<Company> list = this.companyService.getCompanyListByCircleId(
				circleId, page.getBegin(), size);
		page.setListSize(list.size());
		List<CompanyVo> volist = CompanyVo.createVoList(list);
		req.setAttribute("companyvolist", volist);
		return "/WEB-INF/page/e/bccmplist.jsp";
	}

	/**
	 * 我的足迹(个人创建的足迹)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String mycmplist(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		SimplePage page = req.getSimplePage(size);
		List<Company> list = this.companyService.getCompanyListByCreaterId(
				userId, page.getBegin(), size);
		page.setListSize(list.size());
		List<CompanyVo> companyvolist = CompanyVo.createVoList(list);
		req.setAttribute("companyvolist", companyvolist);
		req.setAttribute("userId", userId);
		return "/WEB-INF/page/e/mycmplist.jsp";
	}

	/**
	 * 企业评论
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String review(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		if (o == null) {
			return null;
		}
		SimplePage page = req.getSimplePage(size);
		List<CompanyReview> reviewlist = this.companyService
				.getCompanyReviewListByCompanyId(companyId, page.getBegin(),
						size);
		page.setListSize(reviewlist.size());
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			CompanyUserScore companyUserScore = this.companyService
					.getCompanyUserScore(companyId, loginUser.getUserId());
			req.setAttribute("companyUserScore", companyUserScore);
		}
		req.setAttribute("vo", new CompanyVo(o));
		req.setAttribute("reviewvolist", CompanyReviewVo.createVoList(
				reviewlist, this.getUrlInfo(req)));
		req.setAttribute("companyId", companyId);
		return "/WEB-INF/page/e/review.jsp";
	}

	/**
	 * 足迹相关喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String reflaba(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		if (o == null) {
			return null;
		}
		SimplePage page = req.getSimplePage(size);
		List<Laba> reflabalist = labaService
				.getLabaListByCompanyIdFromCompanyRefLaba(companyId, page
						.getBegin(), size);
		page.setListSize(reflabalist.size());
		List<LabaVo> labavolist = LabaVo.createVoList(reflabalist, this
				.getLabaParserCfg(req));
		req.setAttribute("labavolist", labavolist);
		req.setAttribute("vo", new CompanyVo(o));
		req.setAttribute("companyId", companyId);
		return "/WEB-INF/page/e/reflaba.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String s(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		int forpage = req.getIntAndSetAttr("forpage");
		if (!DataUtil.isEmpty(name)) {
			SimplePage page = req.getSimplePage(20);
			List<Company> list = this.companyService.getCompanyListWithSearch(
					name, page.getBegin(), page.getSize() + 1);
			this.processListForPage(page, list);
			if (list.size() == 1 && forpage == 0) {
				return "r:/e/cmp.do?companyId=" + list.get(0).getCompanyId();
			}
			req.setEncodeAttribute("name", name);
			req.setAttribute("list", list);
		}
		return this.getWapJsp("e/slist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tips(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		byte doneflg = req.getByteAndSetAttr("doneflg");
		SimplePage page = req.getSimplePage(20);
		List<CmpTip> list = this.cmpTipService.getCmpTipListByCompanyId(
				companyId, doneflg, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		List<CmpTipVo> cmptipvolist = CmpTipVo.createVoList(list, 0, true);
		req.setAttribute("cmptipvolist", cmptipvolist);
		return this.getWapJsp("e/tips/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String item(HkRequest req, HkResponse resp) throws Exception {
		long tipId = req.getLongAndSetAttr("tipId");
		CmpTip cmpTip = this.cmpTipService.getCmpTip(tipId);
		if (cmpTip == null) {
			return null;
		}
		User loginUser = this.getLoginUser(req);
		long loginUserId = 0;
		if (loginUser != null) {
			loginUserId = loginUser.getUserId();
		}
		CmpTipVo vo = CmpTipVo.createVo(cmpTip, loginUserId);
		req.setAttribute("vo", vo);
		long companyId = cmpTip.getCompanyId();
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		return this.getWapJsp("e/tips/item.jsp");
	}
}