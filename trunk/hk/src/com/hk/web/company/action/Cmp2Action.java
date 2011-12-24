package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.BizCircle;
import com.hk.bean.City;
import com.hk.bean.CmpChildKind;
import com.hk.bean.CmpChildKindRef;
import com.hk.bean.CmpProduct;
import com.hk.bean.Company;
import com.hk.bean.CompanyKind;
import com.hk.bean.CompanyKindUtil;
import com.hk.bean.CompanyPhoto;
import com.hk.bean.CompanyReview;
import com.hk.bean.CompanyTag;
import com.hk.bean.CompanyTagRef;
import com.hk.bean.CompanyUserScore;
import com.hk.bean.CompanyUserStatus;
import com.hk.bean.HkObj;
import com.hk.bean.HkObjArticle;
import com.hk.bean.HkObjKindOrder;
import com.hk.bean.HkObjOrder;
import com.hk.bean.HkObjOrderDef;
import com.hk.bean.IpCity;
import com.hk.bean.IpCityUser;
import com.hk.bean.ParentKind;
import com.hk.bean.Pcity;
import com.hk.bean.User;
import com.hk.bean.ZoneAdmin;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BizCircleService;
import com.hk.svr.CmpProductService;
import com.hk.svr.CompanyKindService;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.CompanyTagService;
import com.hk.svr.HkObjArticleService;
import com.hk.svr.HkObjKindOrderService;
import com.hk.svr.HkObjOrderService;
import com.hk.svr.HkObjService;
import com.hk.svr.IpCityService;
import com.hk.svr.UserService;
import com.hk.svr.ZoneAdminService;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.user.UserVo;
import com.hk.web.user.UserVoBuilder;
import com.hk.web.util.HttpShoppingCard;
import com.hk.web.util.JspDataUtil;

@Component("/cmp")
public class Cmp2Action extends BaseAction {
	@Autowired
	CompanyService companyService;

	@Autowired
	private HkObjService hkObjService;

	@Autowired
	private HkObjKindOrderService hkObjKindOrderService;

	@Autowired
	private HkObjOrderService hkObjOrderService;

	@Autowired
	private HkObjArticleService hkObjArticleService;

	@Autowired
	private CompanyPhotoService companyPhotoService;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyKindService companyKindService;

	@Autowired
	private BizCircleService bizCircleService;

	@Autowired
	private CompanyTagService companyTagService;

	@Autowired
	private ZoneAdminService zoneAdminService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
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
		int size = 2;
		List<CompanyReview> reviewlist = null;
		if (loginUser != null) {
			reviewlist = this.companyService
					.getCompanyReviewListByCompanyIdNoUser(companyId, loginUser
							.getUserId(), 0, size + 1);
		}
		else {
			reviewlist = this.companyService.getCompanyReviewListByCompanyId(
					companyId, 0, 21);
		}
		if (reviewlist.size() == size + 1) {
			morereview = true;
			reviewlist.remove(size);
		}
		// 图片
		List<CompanyPhoto> photolist = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, 0, 30);
		req.setAttribute("photolist", photolist);
		if (o.getUserId() > 0) {
			authed = true;
		}
		if (loginUser != null) {
			if (authed) {
				if (loginUser.getUserId() == o.getUserId()) {
					caneditcompany = true;
				}
			}
			else {
				if (o.getCreaterId() == loginUser.getUserId()) {
					caneditcompany = true;
				}
				if (photolist.size() < 30) {
					caneiteimg = true;
				}
			}
			if (this.isAdminUser(req)) {
				caneditcompany = true;
			}
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
			String zoneName = pcity.getName();
			if (!DataUtil.isEmpty(zoneName)) {
				zoneName = DataUtil.filterZoneName(zoneName);
				IpCity ipCity = this.ipCityService
						.getIpCityByNameLike(zoneName);
				if (ipCity != null) {
					this.processUserModule(req, ipCity.getCityId());
				}
			}
		}
		// 足迹产品
		List<CmpProduct> productlist = this.cmpProductService
				.getCmpProductList(companyId, 0, 0, 0, 3);
		HttpShoppingCard card = this.getShoppingCard(req);
		List<ProductVo> productvolist = ProductVo.createVoList(productlist,
				card);
		req.setAttribute("productvolist", productvolist);
		req.setAttribute("cityId", o.getPcityId());
		JspDataUtil.loadCmpLeftData(req, o);
		// 喜欢这里的人，把自己排除
		List<CompanyUserScore> companyUserScoreList = this.companyService
				.getCompanyUserScoreListForLikeIt(companyId, 0, 6);
		CompanyUserScore r_CompanyUserScore = null;
		if (loginUser != null) {// 把自己排除
			for (CompanyUserScore companyUserScore : companyUserScoreList) {
				if (companyUserScore.getUserId() == loginUser.getUserId()) {
					r_CompanyUserScore = companyUserScore;
					break;
				}
			}
			if (r_CompanyUserScore != null) {
				companyUserScoreList.remove(r_CompanyUserScore);
			}
			if (companyUserScoreList.size() > 4) {
				companyUserScoreList.remove(companyUserScoreList.size() - 1);
			}
		}
		if (companyUserScoreList.size() > 4) {
			req.setAttribute("more_like_it_userlist", true);
		}
		// 只取4个
		companyUserScoreList = DataUtil.subList(companyUserScoreList, 0, 4);
		List<Long> userIdList = new ArrayList<Long>();
		for (CompanyUserScore companyUserScore : companyUserScoreList) {
			userIdList.add(companyUserScore.getUserId());
		}
		Map<Long, User> userMap = this.userService.getUserMapInId(userIdList);
		// 需要按照companyUserScoreList的顺序组装user
		List<User> like_it_userlist = new ArrayList<User>();
		for (CompanyUserScore companyUserScore : companyUserScoreList) {
			like_it_userlist.add(userMap.get(companyUserScore.getUserId()));
		}
		UserVoBuilder builder = new UserVoBuilder();
		builder.setLoginUser(loginUser);
		builder.setNeedFriend(true);
		List<UserVo> like_it_uservolist = UserVo.create(like_it_userlist,
				builder);
		req.setAttribute("like_it_uservolist", like_it_uservolist);
		req.reSetAttribute("cityId");
		if (companyKind != null) {
			req.setAttribute("parentId", companyKind.getParentId());
			req.setAttribute("parentKind", CompanyKindUtil
					.getParentKind(companyKind.getParentId()));
			// 相应分类列表
			List<CompanyKind> companyKindList = this.companyKindService
					.getCompanyKindList(companyKind.getParentId());
			req.setAttribute("companyKindList", companyKindList);
		}
		req.setAttribute("kindId", o.getKindId());
		String simple_descr = DataUtil.subString(DataUtil.toTextRow(o
				.getIntro()), 100);
		req.setAttribute("simple_descr", simple_descr);
		if (loginUser != null) {
			// 是否是地区管理员
			ZoneAdmin zoneAdmin = this.zoneAdminService.getZoneAdmin(loginUser
					.getUserId());
			if (zoneAdmin != null && zoneAdmin.getPcityId() == o.getPcityId()) {
				req.setAttribute("zoneAdminUser", true);
			}
		}
		return this.getWeb3Jsp("e/company.jsp");
	}

	/**
	 * 显示竞价数据
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String listforkind(HkRequest req, HkResponse resp) {
		int cityId = req.getInt("cityId");
		int kindId = req.getInt("kindId");
		CompanyKind kind = CompanyKindUtil.getCompanyKind(kindId);
		Pcity pcity = ZoneUtil.getPcity(cityId);
		String zone = null;
		if (pcity != null) {
			zone = pcity.getName();
		}
		else {// 全国
			zone = req.getText("view.zone_all");
		}
		req.setAttribute("cityId", cityId);
		req.setAttribute("kindId", kindId);
		req.setAttribute("kind", kind);
		req.setAttribute("zone", zone);
		// 竞价排名数据
		SimplePage page = req.getSimplePage(this.size20);
		List<HkObjKindOrder> orderlist = this.hkObjKindOrderService
				.getHkObjKindOrderList(kindId, cityId, page.getBegin(),
						this.size20);
		page.setListSize(orderlist.size());
		List<HkObjOrderListVo> hkordervolist = HkObjOrderListVo
				.createVoList(orderlist);
		req.setAttribute("hkordervolist", hkordervolist);
		req.reSetAttribute("showorder");
		return this.getWebJsp("e/listforkind.jsp");
	}

	/**
	 * 显示分类足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String klist(HkRequest req, HkResponse resp) {
		int pcityId = req.getIntAndSetAttr("pcityId");
		int kindId = req.getIntAndSetAttr("kindId");
		// 此分类中的足迹列表
		SimplePage page = req.getSimplePage(20);
		List<Company> cmplist = this.companyService.getCompanyList(0, kindId,
				pcityId, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, cmplist);
		req.setAttribute("cmplist", cmplist);
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(kindId);
		req.setAttribute("companyKind", companyKind);
		if (companyKind != null) {
			ParentKind parentKind = CompanyKindUtil.getParentKind(companyKind
					.getParentId());
			req.setAttribute("parentId", companyKind.getParentId());
			req.setAttribute("parentKind", parentKind);
			// 相应分类列表
			List<CompanyKind> companyKindList = this.companyKindService
					.getCompanyKindList(companyKind.getParentId());
			req.setAttribute("companyKindList", companyKindList);
		}
		// 小分类数据
		List<CmpChildKind> childkindlist = this.companyKindService
				.getCmpChildKindList(kindId);
		req.setAttribute("childkindlist", childkindlist);
		// 商圈数据
		Pcity pcity = ZoneUtil.getPcity(pcityId);
		if (pcity != null) {
			List<BizCircle> bizCircleList = bizCircleService
					.getBizCircleListForHasCmp(pcity.getOcityId());
			req.setAttribute("bizCircleList", bizCircleList);
		}
		return this.getWeb3Jsp("e/klist.jsp");
	}

	/**
	 * 显示商圈足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String bzcmplist(HkRequest req, HkResponse resp) {
		int parentId = req.getInt("parentId");
		int circleId = req.getInt("circleId");
		int kindId = req.getInt("kindId");
		// 此分类中的足迹列表
		SimplePage page = req.getSimplePage(20);
		List<Company> cmplist = this.bizCircleService.getCompanyList(circleId,
				kindId, parentId, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, cmplist);
		req.setAttribute("cmplist", cmplist);
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(kindId);
		ParentKind parentKind = CompanyKindUtil.getParentKind(parentId);
		req.setAttribute("circleId", circleId);
		req.setAttribute("kindId", kindId);
		req.setAttribute("parentId", parentId);
		req.setAttribute("companyKind", companyKind);
		req.setAttribute("parentKind", parentKind);
		// 相应分类列表
		List<CompanyKind> companyKindList = this.companyKindService
				.getCompanyKindList(parentId);
		req.setAttribute("companyKindList", companyKindList);
		// 商圈对象
		BizCircle bizCircle = this.bizCircleService.getBizCircle(circleId);
		req.setAttribute("bizCircle", bizCircle);
		if (bizCircle != null) {
			// 商圈数据
			List<BizCircle> bizCircleList = bizCircleService
					.getBizCircleListForHasCmp(bizCircle.getCityId());
			req.setAttribute("bizCircleList", bizCircleList);
			// 地区
			City city = ZoneUtil.getCity(bizCircle.getCityId());
			req.setAttribute("pcityId", city.getCityId());
		}
		return this.getWeb3Jsp("e/bzcmplist.jsp");
	}

	/**
	 * 显示商圈足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tagcmplist(HkRequest req, HkResponse resp) {
		int pcityId = req.getInt("pcityId");
		int tagId = req.getInt("tagId");
		req.setAttribute("pcityId", pcityId);
		req.setAttribute("tagId", tagId);
		// 标签数据
		CompanyTag companyTag = this.companyTagService.getCompanyTag(tagId);
		if (companyTag == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		req.setAttribute("companyTag", companyTag);
		// 此分类中的足迹列表
		SimplePage page = req.getSimplePage(20);
		List<CompanyTagRef> reflist = this.companyTagService
				.getCompanyTagRefListByTagIdAndCityId(tagId, pcityId, page
						.getBegin(), page.getSize() + 1);
		this.processListForPage(page, reflist);
		List<Long> idList = new ArrayList<Long>();
		for (CompanyTagRef r : reflist) {
			idList.add(r.getCompanyId());
		}
		Map<Long, Company> map = this.companyService.getCompanyMapInId(idList);
		List<Company> cmplist = new ArrayList<Company>();
		for (CompanyTagRef r : reflist) {
			cmplist.add(map.get(r.getCompanyId()));
		}
		req.setAttribute("cmplist", cmplist);
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(companyTag
				.getKindId());
		if (companyKind != null) {
			req.setAttribute("companyKind", companyKind);
			req.setAttribute("kindId", companyTag.getKindId());
			ParentKind parentKind = CompanyKindUtil.getParentKind(companyKind
					.getParentId());
			req.setAttribute("parentKind", parentKind);
			req.setAttribute("parentId", parentKind.getKindId());
			// 相应分类列表
			List<CompanyKind> companyKindList = this.companyKindService
					.getCompanyKindList(companyKind.getParentId());
			req.setAttribute("companyKindList", companyKindList);
		}
		// 商圈数据
		City city = ZoneUtil.getCity(pcityId);
		if (city != null) {
			List<BizCircle> bizCircleList = bizCircleService
					.getBizCircleListForHasCmp(city.getCityId());
			req.setAttribute("bizCircleList", bizCircleList);
		}
		return this.getWeb3Jsp("e/tagcmplist.jsp");
	}

	/**
	 * 显示小分类足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cklist(HkRequest req, HkResponse resp) {
		int pcityId = req.getInt("pcityId");
		int oid = req.getInt("oid");
		int kindId = req.getInt("kindId");
		req.setAttribute("pcityId", pcityId);
		req.setAttribute("oid", oid);
		req.setAttribute("kindId", kindId);
		// 标签数据
		CmpChildKind cmpChildKind = this.companyKindService
				.getCmpChildKind(oid);
		if (cmpChildKind == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		req.setAttribute("cmpChildKind", cmpChildKind);
		// 此分类中的足迹列表
		SimplePage page = req.getSimplePage(20);
		List<CmpChildKindRef> reflist = this.companyKindService
				.getCmpChildKindRefList(oid, pcityId, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, reflist);
		List<Long> idList = new ArrayList<Long>();
		for (CmpChildKindRef r : reflist) {
			idList.add(r.getCompanyId());
		}
		Map<Long, Company> map = this.companyService.getCompanyMapInId(idList);
		List<Company> cmplist = new ArrayList<Company>();
		for (CmpChildKindRef r : reflist) {
			cmplist.add(map.get(r.getCompanyId()));
		}
		req.setAttribute("cmplist", cmplist);
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(cmpChildKind
				.getKindId());
		if (companyKind != null) {
			req.setAttribute("companyKind", companyKind);
			req.setAttribute("kindId", cmpChildKind.getKindId());
			ParentKind parentKind = CompanyKindUtil.getParentKind(companyKind
					.getParentId());
			req.setAttribute("parentKind", parentKind);
			req.setAttribute("parentId", parentKind.getKindId());
			// 相应分类列表
			List<CompanyKind> companyKindList = this.companyKindService
					.getCompanyKindList(companyKind.getParentId());
			req.setAttribute("companyKindList", companyKindList);
		}
		// 商圈数据
		City city = ZoneUtil.getCity(pcityId);
		if (city != null) {
			List<BizCircle> bizCircleList = bizCircleService
					.getBizCircleListForHasCmp(city.getCityId());
			req.setAttribute("bizCircleList", bizCircleList);
		} // 小分类数据
		List<CmpChildKind> childkindlist = this.companyKindService
				.getCmpChildKindList(kindId);
		req.setAttribute("childkindlist", childkindlist);
		return this.getWeb3Jsp("e/cklist.jsp");
	}

	/**
	 * 显示大分类足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String pklist(HkRequest req, HkResponse resp) {
		int pcityId = req.getInt("pcityId");
		int parentId = req.getInt("parentId");
		// 此分类中的足迹列表
		SimplePage page = req.getSimplePage(20);
		List<Company> cmplist = this.companyService.getCompanyList(parentId, 0,
				pcityId, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, cmplist);
		req.setAttribute("cmplist", cmplist);
		ParentKind parentKind = ParentKind.getParentKind(parentId);
		req.setAttribute("pcityId", pcityId);
		req.setAttribute("parentId", parentId);
		req.setAttribute("parentKind", parentKind);
		// 相应分类列表
		List<CompanyKind> companyKindList = this.companyKindService
				.getCompanyKindList(parentId);
		req.setAttribute("companyKindList", companyKindList);
		// 商圈数据
		City city = ZoneUtil.getCity(pcityId);
		if (city != null) {
			List<BizCircle> bizCircleList = bizCircleService
					.getBizCircleListForHasCmp(city.getCityId());
			req.setAttribute("bizCircleList", bizCircleList);
		}
		return this.getWeb3Jsp("e/pklist.jsp");
	}

	/**
	 * 显示分类足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String listbyflg(HkRequest req, HkResponse resp) {
		int cityId = req.getInt("cityId");
		int flg = req.getInt("flg");
		int kindId = req.getInt("kindId");
		int showorder = req.getInt("showorder");
		List<Company> cmplist = null;
		int psize = this.size20;
		if (flg == 0) {// 推荐
			// 首页根据城市获取排名数据
			List<HkObjOrder> orderlist = this.hkObjOrderService
					.getHkObjOrderListForOrder(HkObjOrderDef.KIND_LEVEL_1,
							cityId, 0, 20);
			List<Long> idList = new ArrayList<Long>();
			for (HkObjOrder o : orderlist) {
				idList.add(o.getHkObjId());
			}
			Map<Long, HkObj> ordermap = this.hkObjService
					.getHkObjMapInId(idList);
			for (HkObjOrder o : orderlist) {
				o.setHkObj(ordermap.get(o.getHkObjId()));
			}
			req.setAttribute("fororder", true);
			req.setAttribute("showorder", showorder);
			req.setAttribute("orderlist", orderlist);
			return this.getWebJsp("pub/listbyflg.jsp");
		}
		else if (flg == 1) {// 最新
			cmplist = this.companyService.getCompanyList(0, kindId, cityId, 0,
					psize);
		}
		else if (flg == 2) {// 最火
			cmplist = this.companyService.getCompanyListForHot(kindId, cityId,
					0, psize);
		}
		else if (flg == 3) {// 最酷
			cmplist = this.companyService.getCompanyListForCool(kindId, cityId,
					0, psize);
		}
		req.setAttribute("cmplist", cmplist);
		return this.getWebJsp("pub/listbyflg.jsp");
	}

	private void processUserModule(HkRequest req, int ipCityId) {
		List<IpCityUser> list = this.userService
				.getIpCityUserListSortUserRecentUpdate(ipCityId, 0, 8);
		List<Long> idList = new ArrayList<Long>();
		for (IpCityUser o : list) {
			idList.add(o.getUserId());
		}
		List<User> userlist = this.userService.getUserListInId(idList);
		req.setAttribute("userlist", userlist);
	}

	/**
	 * 图片展示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String pic(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return this.getNotFoundForward(resp);
		}
		req.setAttribute("company", company);
		List<CompanyPhoto> list = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, 0, 30);
		User loginUser = this.getLoginUser(req);
		boolean authed = false;
		boolean caneiteimg = false;
		boolean caneditcompany = false;
		if (company.getUserId() > 0) {
			authed = true;
		}
		if (loginUser != null) {
			if (authed) {
				if (loginUser.getUserId() == company.getUserId()) {
					caneditcompany = true;
				}
			}
			else {
				if (company.getCreaterId() == loginUser.getUserId()) {
					caneditcompany = true;
				}
				if (list.size() < 30) {
					caneiteimg = true;
				}
			}
		}
		req.setAttribute("caneiteimg", caneiteimg);
		req.setAttribute("caneditcompany", caneditcompany);
		req.setAttribute("list", list);
		req.reSetAttribute("photoId");
		if (loginUser != null) {
			CompanyUserStatus companyUserStatus = this.companyService
					.getCompanyUserStatus(companyId, loginUser.getUserId());
			req.setAttribute("companyUserStatus", companyUserStatus);
		}
		List<CmpProduct> cmpproductlist = this.cmpProductService
				.getCmpProductList(companyId, 0, 0, 0, 6);
		req.setAttribute("cmpproductlist", cmpproductlist);
		return this.getWeb3Jsp("e/pic.jsp");
	}
}