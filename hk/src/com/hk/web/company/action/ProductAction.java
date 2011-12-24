package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductPhoto;
import com.hk.bean.CmpProductReview;
import com.hk.bean.CmpProductSort;
import com.hk.bean.CmpProductTag;
import com.hk.bean.CmpProductTagRef;
import com.hk.bean.CmpProductUserScore;
import com.hk.bean.CmpProductUserStatus;
import com.hk.bean.Company;
import com.hk.bean.CompanyKind;
import com.hk.bean.CompanyKindUtil;
import com.hk.bean.ParentKind;
import com.hk.bean.User;
import com.hk.bean.ZoneAdmin;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;
import com.hk.svr.CompanyService;
import com.hk.svr.UserService;
import com.hk.svr.ZoneAdminService;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.user.UserVo;
import com.hk.web.user.UserVoBuilder;
import com.hk.web.util.HttpShoppingCard;

@Component("/product")
public class ProductAction extends BaseAction {

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	@Autowired
	private ZoneAdminService zoneAdminService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		int pid = req.getIntAndSetAttr("pid");
		User loginUser = this.getLoginUser(req);
		// 点评
		SimplePage page = req.getSimplePage(20);
		List<CmpProductReview> cmpproductreviewlist = this.cmpProductService
				.getCmpProductReviewListByProductId(pid, page.getBegin(), page
						.getSize() + 1);
		if (cmpproductreviewlist.size() == page.getSize() + 1) {
			req.setAttribute("hasmore", true);
			cmpproductreviewlist.remove(cmpproductreviewlist.size() - 1);
		}
		List<CmpProductReviewVo> cmpproductreviewvolist = CmpProductReviewVo
				.createVoList(cmpproductreviewlist, this.getUrlInfoWeb(req),
						true, false);
		req.setAttribute("cmpproductreviewvolist", cmpproductreviewvolist);
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(pid);
		if (cmpProduct == null) {
			return this.getNotFoundForward(resp);
		}
		Company company = this.companyService.getCompany(cmpProduct
				.getCompanyId());
		req.setAttribute("company", company);
		if (loginUser != null) {
			if (company.getUserId() > 0) {
				if (loginUser.getUserId() == company.getUserId()) {
					req.setAttribute("canmgrcmp", true);
				}
			}
			else {
				if (loginUser.getUserId() == company.getCreaterId()) {
					req.setAttribute("canmgrcmp", true);
				}
			}
		}
		req.setAttribute("cmpProduct", cmpProduct);
		List<CmpProductPhoto> photolist = this.cmpProductService
				.getCmpProductPhotoListByProductId(pid, 0, 30);
		req.setAttribute("photolist", photolist);
		if (photolist.size() < 30 && loginUser != null
				&& company.getUserId() == 0) {
			req.setAttribute("canuploadphoto", true);
		}
		// 喜欢这个产品的人
		List<CmpProductUserScore> cmpProductUserScoreList = this.cmpProductService
				.getCmpProductUserScoreGoodListByProductId(pid, 0, 9);
		List<Long> idList = new ArrayList<Long>();
		for (CmpProductUserScore status : cmpProductUserScoreList) {
			idList.add(status.getUserId());
		}
		Map<Long, User> usermap = this.userService.getUserMapInId(idList);
		for (CmpProductUserScore status : cmpProductUserScoreList) {
			status.setUser(usermap.get(status.getUserId()));
		}
		req.setAttribute("cmpProductUserScoreList", cmpProductUserScoreList);
		// 还有那些足迹有这个产品
		CmpProductTag tag = this.cmpProductService
				.getCmpProductTagByName(cmpProduct.getName());
		if (tag != null) {
			List<CmpProductTagRef> tagreflist = this.cmpProductService
					.getCmpProductTagRefListByTagId(tag.getTagId(), 0, 6);
			tagreflist = this.filterCmpProductTagRefList(req, tagreflist,
					cmpProduct);
			idList = new ArrayList<Long>();
			for (CmpProductTagRef ref : tagreflist) {
				idList.add(ref.getCompanyId());
			}
			Map<Long, Company> cmpmap = this.companyService
					.getCompanyMapInId(idList);
			for (CmpProductTagRef ref : tagreflist) {
				ref.setCompany(cmpmap.get(ref.getCompanyId()));
			}
			req.setAttribute("tagreflist", tagreflist);
		}
		if (loginUser != null) {
			// 是否收藏
			if (this.cmpProductService.isFavProduct(loginUser.getUserId(), pid)) {
				req.setAttribute("hasfav", true);
			}
			CmpProductUserStatus userStatus = this.cmpProductService
					.getCmpProductUserStatus(loginUser.getUserId(), pid);
			req.setAttribute("userStatus", userStatus);
			// 是否是地区管理员
			ZoneAdmin zoneAdmin = this.zoneAdminService.getZoneAdmin(loginUser
					.getUserId());
			if (zoneAdmin != null
					&& zoneAdmin.getPcityId() == company.getPcityId()) {
				req.setAttribute("zoneAdminUser", true);
			}
		}
		HttpShoppingCard card = this.getShoppingCard(req);
		req.setAttribute("addtocard", card.isHasProduct(pid));
		return this.getWeb3Jsp("e/product/product.jsp");
	}

	private List<CmpProductTagRef> filterCmpProductTagRefList(HkRequest req,
			List<CmpProductTagRef> list, CmpProduct cmpProduct) {
		// 移除本店
		int idx = -1;
		int i = 0;
		for (CmpProductTagRef cmpProductTagRef : list) {
			if (cmpProductTagRef.getCompanyId() == cmpProduct.getCompanyId()) {
				idx = i;
				break;
			}
			i++;
		}
		if (idx != -1) {
			list.remove(i);
		}
		if (list.size() > 4) {
			req.setAttribute("moretagref", true);
			return list.subList(0, 4);
		}
		return list;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String wap(HkRequest req, HkResponse resp) throws Exception {
		long pid = req.getLongAndSetAttr("pid");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(pid);
		if (cmpProduct == null) {
			return this.getNotFoundForward(resp);
		}
		req.setAttribute("cmpProduct", cmpProduct);
		Company company = this.companyService.getCompany(cmpProduct
				.getCompanyId());
		req.setAttribute("company", company);
		HttpShoppingCard card = this.getShoppingCard(req);
		req.setAttribute("addtocard", card.isHasProduct(pid));
		List<CmpProductPhoto> photolist = this.cmpProductService
				.getCmpProductPhotoListByProductId(pid, 0, 2);
		if (photolist.size() == 2) {
			req.setAttribute("morephoto", true);
		}
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			// 是否收藏
			if (this.cmpProductService.isFavProduct(loginUser.getUserId(), pid)) {
				req.setAttribute("hasfav", true);
			}
			CmpProductUserStatus userStatus = this.cmpProductService
					.getCmpProductUserStatus(loginUser.getUserId(), pid);
			req.setAttribute("userStatus", userStatus);
			if (company.getUserId() > 0) {
				if (loginUser.getUserId() == company.getUserId()) {
					req.setAttribute("canmgrcmp", true);
				}
			}
			else {
				if (loginUser.getUserId() == company.getCreaterId()) {
					req.setAttribute("canmgrcmp", true);
				}
			}
		}
		return this.getWapJsp("e/product/product.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String photo(HkRequest req, HkResponse resp) throws Exception {
		long pid = req.getLongAndSetAttr("pid");
		SimplePage page = req.getSimplePage(1);
		List<CmpProductPhoto> photolist = this.cmpProductService
				.getCmpProductPhotoListByProductId(pid, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, photolist);
		req.setAttribute("photolist", photolist);
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(pid);
		if (cmpProduct == null) {
			return null;
		}
		if (req.getInt("skip") == 1
				&& photolist.size() > 0
				&& photolist.iterator().next().getPath().equals(
						cmpProduct.getHeadPath())) {
			return "r:/product_photo.do?pid=" + pid + "&page="
					+ (page.getPage() + 1);
		}
		req.setAttribute("cmpProduct", cmpProduct);
		return this.getWapJsp("e/product/photo.jsp");
	}

	/**
	 * 喜欢这个产品的人
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String user(HkRequest req, HkResponse resp) throws Exception {
		int pid = req.getIntAndSetAttr("pid");
		SimplePage page = req.getSimplePage(20);
		List<CmpProductUserScore> cmpProductUserScoreList = this.cmpProductService
				.getCmpProductUserScoreGoodListByProductId(pid,
						page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, cmpProductUserScoreList);
		List<Long> idList = new ArrayList<Long>();
		for (CmpProductUserScore status : cmpProductUserScoreList) {
			idList.add(status.getUserId());
		}
		Map<Long, User> usermap = this.userService.getUserMapInId(idList);
		List<User> userlist = new ArrayList<User>();
		for (CmpProductUserScore status : cmpProductUserScoreList) {
			userlist.add(usermap.get(status.getUserId()));
		}
		UserVoBuilder builder = new UserVoBuilder();
		builder.setNeedLaba(true);
		builder.setNeedFriend(true);
		builder.setNeedCheckFollowMe(true);
		builder.setLoginUser(this.getLoginUser(req));
		List<UserVo> uservolist = UserVo.create(userlist, builder);
		req.setAttribute("uservolist", uservolist);
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(pid);
		req.setAttribute("cmpProduct", cmpProduct);
		return this.getWeb3Jsp("e/product/userlist.jsp");
	}

	/**
	 * 还有这个产品的其他足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cmp(HkRequest req, HkResponse resp) throws Exception {
		int pid = req.getIntAndSetAttr("pid");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(pid);
		CmpProductTag tag = this.cmpProductService
				.getCmpProductTagByName(cmpProduct.getName());
		if (tag == null) {
			return this.getNotFoundForward(resp);
		}
		SimplePage page = req.getSimplePage(20);
		List<CmpProductTagRef> tagreflist = this.cmpProductService
				.getCmpProductTagRefListByTagId(tag.getTagId(),
						page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, tagreflist);
		List<Long> idList = new ArrayList<Long>();
		for (CmpProductTagRef ref : tagreflist) {
			idList.add(ref.getCompanyId());
		}
		List<Company> cmplist = this.companyService.getCompanyListInId(idList,
				null);
		req.setAttribute("cmplist", cmplist);
		return this.getWeb3Jsp("e/product/cmplist.jsp");
	}

	/**
	 * 产品列表 足迹》产品》分类》列表，左边放置产品分类，需要放入request中的数据companyKind parentKind
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		int sortId = req.getIntAndSetAttr("sortId");
		SimplePage page = req.getSimplePage(20);
		List<CmpProduct> productlist = this.cmpProductService
				.getCmpProductList(companyId, sortId, 0, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, productlist);
		this.processPage(req, productlist, page);
		List<ProductVo> productvolist = ProductVo.createVoList(productlist,
				this.getShoppingCard(req));
		req.setAttribute("productvolist", productvolist);
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return this.getNotFoundForward(resp);
		}
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(company
				.getKindId());
		ParentKind parentKind = CompanyKindUtil.getParentKind(company
				.getParentKindId());
		req.setAttribute("companyKind", companyKind);
		req.setAttribute("parentKind", parentKind);
		req.setAttribute("company", company);
		req.setAttribute("pcityId", company.getPcityId());
		// 当前分类（如果有就显示）
		if (sortId > 0) {
			CmpProductSort cmpProductSort = this.cmpProductService
					.getCmpProductSort(sortId);
			req.setAttribute("cmpProductSort", cmpProductSort);
		}
		// 分类列表
		List<CmpProductSort> cmpproductsortlist = this.cmpProductService
				.getCmpProductSortList(companyId);
		req.setAttribute("cmpproductsortlist", cmpproductsortlist);
		return this.getWeb3Jsp("e/product/productlist.jsp");
	}

	/**
	 * 产品列表 足迹》产品》分类》列表，左边放置产品分类，需要放入request中的数据companyKind parentKind
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String listwap(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		int sortId = req.getIntAndSetAttr("sortId");
		SimplePage page = req.getSimplePage(20);
		List<CmpProduct> productlist = this.cmpProductService
				.getCmpProductList(companyId, sortId, 0, page.getBegin(), page
						.getSize() + 1);
		this.processPage(req, productlist, page);
		List<ProductVo> productvolist = ProductVo.createVoList(productlist,
				this.getShoppingCard(req));
		req.setAttribute("productvolist", productvolist);
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return this.getNotFoundForward(resp);
		}
		req.setAttribute("company", company);
		// 当前分类（如果有就显示）
		if (sortId > 0) {
			CmpProductSort cmpProductSort = this.cmpProductService
					.getCmpProductSort(sortId);
			req.setAttribute("cmpProductSort", cmpProductSort);
		}
		// 分类列表
		List<CmpProductSort> cmpproductsortlist = this.cmpProductService
				.getCmpProductSortList(companyId);
		req.setAttribute("cmpproductsortlist", cmpproductsortlist);
		return this.getWapJsp("e/product/productlist.jsp");
	}

	/**
	 * ajax加载足迹的其他产品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String listforpage(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		int sortId = req.getIntAndSetAttr("sortId");
		int pid = req.getIntAndSetAttr("pid");
		SimplePage page = req.getSimplePage(6);
		List<CmpProduct> list = this.cmpProductService.getCmpProductList(
				companyId, sortId, pid, page.getBegin(), page.getSize() + 1);
		req.setAttribute("list", list);
		this.processPage(req, list, page);
		return this.getWeb3Jsp("e/product/listforpage.jsp");
	}

	/**
	 * 预订产品，购物车内的物品必须是同一个足迹的，否则会提示用户，让用户选择更换足迹或者是取消预订<br/>
	 * 如果更换足迹，则清空购物车，把新的产品放入购物车
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addtocard(HkRequest req, HkResponse resp) throws Exception {
		int pid = req.getInt("pid");
		int chgflg = req.getInt("chgflg");// 是否强制清空购物车 1强制清空
		CmpProduct product = this.cmpProductService.getCmpProduct(pid);
		if (product == null) {
			return null;
		}
		HttpShoppingCard card = this.getShoppingCard(req);
		if (chgflg == 0 && card.getCompanyId() != 0
				&& card.getCompanyId() != product.getCompanyId()) {
			resp.sendHtml(-1);
			return null;
		}
		if (card.getCompanyId() != 0
				&& card.getCompanyId() != product.getCompanyId()) {
			card.clean();
		}
		card.setCompanyId(product.getCompanyId());
		card.addProduct(pid, true, 1);
		card.saveShoppingCard(resp);
		resp.sendHtml(1);// 预订成功
		return null;
	}

	/**
	 * 预订产品，购物车内的物品必须是同一个足迹的，否则会提示用户，让用户选择更换足迹或者是取消预订<br/>
	 * 如果更换足迹，则清空购物车，把新的产品放入购物车
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addtocardwap(HkRequest req, HkResponse resp) throws Exception {
		int pid = req.getInt("pid");
		int chgflg = req.getInt("chgflg");// 是否强制清空购物车 1强制清空
		CmpProduct product = this.cmpProductService.getCmpProduct(pid);
		if (product == null) {
			return null;
		}
		HttpShoppingCard card = this.getShoppingCard(req);
		if (chgflg == 0 && card.getCompanyId() != 0
				&& card.getCompanyId() != product.getCompanyId()) {
			resp.sendHtml(-1);
			return null;
		}
		if (card.getCompanyId() != 0
				&& card.getCompanyId() != product.getCompanyId()) {
			card.clean();
		}
		card.setCompanyId(product.getCompanyId());
		card.addProduct(pid, true, 1);
		card.saveShoppingCard(resp);
		return "r:/product_wap.do?pid=" + pid;
	}
}