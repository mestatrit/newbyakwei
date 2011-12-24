package web.epp.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpInfo;
import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductAttr;
import com.hk.bean.CmpProductAttrValue;
import com.hk.bean.CmpProductPhoto;
import com.hk.bean.CmpProductSort;
import com.hk.bean.CmpProductSortAttr;
import com.hk.bean.CmpProductSortAttrModule;
import com.hk.bean.CmpProductSortAttrObject;
import com.hk.bean.Company;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;

@Component("/epp/web/product")
public class ProductAction extends EppBaseAction {

	@Autowired
	private CmpProductService cmpProductService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		Company company = (Company) req.getAttribute("o");
		if (company.getCmpflg() == 0) {
			return this.list0(req, resp);
		}
		else if (company.getCmpflg() == 1) {
			return this.list1(req, resp);
		}
		return null;
	}

	private String list0(HkRequest req, HkResponse resp) throws Exception {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.list00(req, resp);
		}
		return null;
	}

	private String list1(HkRequest req, HkResponse resp) throws Exception {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.list10(req, resp);
		}
		return null;
	}

	/**
	 * 模板0/0产品列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-18
	 */
	private String list00(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long companyId = req.getLong("companyId");
		int sortId = req.getIntAndSetAttr("sortId");
		SimplePage page = req.getSimplePage(20);
		List<CmpProduct> list = null;
		if (sortId == 0) {
			list = this.cmpProductService.getCmpProductListByCompanyId(
					companyId, page.getBegin(), page.getSize() + 1);
		}
		else {
			list = this.cmpProductService
					.getCmpProductListByCompanyIdAndSortId(companyId, sortId,
							page.getBegin(), page.getSize() + 1);
		}
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.reSetAttribute("navId");
		this.loadLeftProductSortList(req, companyId, sortId);
		return this.getWebPath("product/list.jsp");
	}

	/**
	 * 模板1/0产品列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-18
	 */
	private String list10(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		int sortId = req.getIntAndSetAttr("sortId");
		PageSupport pageSupport = req.getPageSupport(15);
		List<CmpProduct> list = null;
		if (sortId == 0) {
			pageSupport.setTotalCount(this.cmpProductService
					.countCmpProductListByCompanyId(companyId));
			list = this.cmpProductService.getCmpProductListByCompanyId(
					companyId, pageSupport.getBegin(), pageSupport.getSize());
		}
		else {
			pageSupport
					.setTotalCount(this.cmpProductService
							.countCmpProductListByCompanyIdAndSortId(companyId,
									sortId));
			list = this.cmpProductService
					.getCmpProductListByCompanyIdAndSortId(companyId, sortId,
							pageSupport.getBegin(), pageSupport.getSize());
		}
		req.setAttribute("list", list);
		CmpProductSort cmpProductSort = this.cmpProductService
				.getCmpProductSort(sortId);
		if (cmpProductSort != null) {
			List<CmpProductSort> parentlist = this.cmpProductService
					.getCmpProductSortInId(companyId, cmpProductSort
							.getParentIds());
			req.setAttribute("parentlist", parentlist);
			req.setAttribute("end_parentlist_idx", parentlist.size() - 1);
			req.setAttribute("cmpProductSort", cmpProductSort);
		}
		return this.getWebPath("mod/1/0/product/list.jsp");
	}

	private void buildChildren(CmpProductSort cmpProductSort,
			List<CmpProductSort> list) {
		List<CmpProductSort> olist = new ArrayList<CmpProductSort>();
		for (CmpProductSort o : list) {
			if (o.getParentId() == cmpProductSort.getSortId()) {
				olist.add(o);
			}
		}
		cmpProductSort.setChildren(olist);
	}

	private void loadLeftProductSortList(HkRequest req, long companyId,
			int sortId) {
		List<CmpProductSort> productsortlist = this.cmpProductService
				.getCmpProductSortList(companyId);
		List<CmpProductSort> l_1_list = new ArrayList<CmpProductSort>();
		List<CmpProductSort> l_2_list = new ArrayList<CmpProductSort>();
		List<CmpProductSort> l_3_list = new ArrayList<CmpProductSort>();
		for (CmpProductSort o : productsortlist) {
			if (o.getNlevel() == 1) {
				l_1_list.add(o);
			}
			else if (o.getNlevel() == 2) {
				l_2_list.add(o);
			}
			else if (o.getNlevel() == 3) {
				l_3_list.add(o);
			}
		}
		for (CmpProductSort l_2 : l_2_list) {
			this.buildChildren(l_2, l_3_list);
		}
		for (CmpProductSort l_1 : l_1_list) {
			this.buildChildren(l_1, l_2_list);
		}
		req.setAttribute("l_1_list", l_1_list);
		// 导航使用
		if (sortId > 0) {
			CmpProductSort cmpProductSort = this.cmpProductService
					.getCmpProductSort(sortId);
			if (cmpProductSort != null) {
				req.setAttribute("cmpProductSort", cmpProductSort);
				List<CmpProductSort> parentsortlist = this.cmpProductService
						.getCmpProductSortInId(companyId, cmpProductSort
								.getParentIds());
				req.setAttribute("parentsortlist", parentsortlist);
			}
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String wap(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(6);
		List<CmpProduct> list = this.cmpProductService
				.getCmpProductListByCompanyId(companyId, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapPath("product/list.jsp");
	}

	/**
	 * 下一个单页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String next(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long productId = req.getLong("productId");
		long navId = req.getLong("navId");
		int sortId = req.getInt("sortId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {
			return null;
		}
		List<CmpProduct> list = null;
		if (sortId > 0) {
			list = this.cmpProductService
					.getCmpProductListByCompanyIdAndSortIdForRange(companyId,
							sortId, productId, cmpProduct.getOrderflg(), 1, 1);
		}
		else {
			list = this.cmpProductService.getCmpProductListByCompanyIdForRange(
					companyId, productId, cmpProduct.getOrderflg(), 1, 1);
		}
		if (list.size() > 0) {
			CmpProduct product = list.get(0);
			// return "r:/epp/web/product_view.do?companyId=" + companyId
			// + "&navId=" + navId + "&productId="
			// + product.getProductId() + "&sortId=" + sortId;
			return "r:http://" + req.getServerName() + "/product/" + companyId
					+ "/" + navId + "/" + sortId + "/" + product.getProductId()
					+ ".html";
		}
		// 看到最后一个了，就到列表页
		// return "r:/epp/web/product.do?companyId=" + companyId + "&navId="
		// + navId + "&sortId=" + sortId;
		return "r:http://" + req.getServerName() + "/products/" + companyId
				+ "/" + navId + "/sort/" + sortId;
	}

	/**
	 * 上一个单页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String pre(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long productId = req.getLong("productId");
		long navId = req.getLong("navId");
		int sortId = req.getInt("sortId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {
			return null;
		}
		List<CmpProduct> list = null;
		if (sortId > 0) {
			list = this.cmpProductService
					.getCmpProductListByCompanyIdAndSortIdForRange(companyId,
							sortId, productId, cmpProduct.getOrderflg(), -1, 1);
		}
		else {
			list = this.cmpProductService.getCmpProductListByCompanyIdForRange(
					companyId, productId, cmpProduct.getOrderflg(), -1, 1);
		}
		if (list.size() > 0) {
			CmpProduct product = list.get(0);
			// return "r:/epp/web/product_view.do?companyId=" + companyId
			// + "&navId=" + navId + "&productId="
			// + product.getProductId() + "&sortId=" + sortId;
			return "r:http://" + req.getServerName() + "/product/" + companyId
					+ "/" + navId + "/" + sortId + "/" + product.getProductId()
					+ ".html";
		}
		// 看到最后一个了，就到列表页
		// return "r:/epp/web/product.do?companyId=" + companyId + "&navId="
		// + navId + "&sortId=" + sortId;
		return "r:http://" + req.getServerName() + "/products/" + companyId
				+ "/" + navId + "/sort/" + sortId;
	}

	/**
	 * 产品单页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String view(HkRequest req, HkResponse resp) throws Exception {
		Company o = (Company) req.getAttribute("o");
		if (o.getCmpflg() == 0) {
			return this.view0(req, resp);
		}
		if (o.getCmpflg() == 1) {
			return this.view1(req, resp);
		}
		return null;
	}

	/**
	 * 产品单页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String view00(HkRequest req, HkResponse resp) throws Exception {
		this.loadProductInfo(req, resp);
		CmpProduct cmpProduct = (CmpProduct) req.getAttribute("cmpProduct");
		if (cmpProduct == null) {
			return null;
		}
		long productId = req.getLong("productId");
		List<CmpProductPhoto> photolist = this.cmpProductService
				.getCmpProductPhotoListByProductId(productId, 0, 2);
		req.setAttribute("photolist", photolist);
		return this.getWebPath("product/view.jsp");
	}

	/**
	 * 产品单页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String viewpic(HkRequest req, HkResponse resp) throws Exception {
		this.loadProductInfo(req, resp);
		CmpProduct cmpProduct = (CmpProduct) req.getAttribute("cmpProduct");
		if (cmpProduct == null) {
			return null;
		}
		long productId = req.getLong("productId");
		SimplePage page = req.getSimplePage(1);
		List<CmpProductPhoto> photolist = this.cmpProductService
				.getCmpProductPhotoListByProductId(productId, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, photolist);
		if (photolist.isEmpty()) {
			return null;
		}
		// int nh = req.getInt("nh");
		// //表示不显示头像图片，跳过
		// if(nh==1){
		// if(cmpProduct.getHeadPath()!=null &&
		// cmpProduct.getHeadPath().equals(anObject))
		// }
		req.setAttribute("photolist", photolist);
		return this.getWebPath("product/viewpic.jsp");
	}

	/**
	 * 产品图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	private void loadProductInfo(HkRequest req, HkResponse resp) {
		this.setCmpNavInfo(req);
		long productId = req.getLongAndSetAttr("productId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {
			return;
		}
		if (cmpProduct.getSortId() > 0) {
			CmpProductSort cmpProductSort = this.cmpProductService
					.getCmpProductSort(cmpProduct.getSortId());
			req.setAttribute("cmpProductSort", cmpProductSort);
		}
		req.setAttribute("cmpProduct", cmpProduct);
		req.setAttribute("sortId", cmpProduct.getSortId());
		long companyId = req.getLong("companyId");
		this.loadLeftProductSortList(req, companyId, cmpProduct.getSortId());
		int sortId = req.getIntAndSetAttr("sortId");
		List<CmpProduct> before_other_product_list = null;
		List<CmpProduct> after_other_product_list = null;
		if (sortId > 0) {
			before_other_product_list = this.cmpProductService
					.getCmpProductListByCompanyIdAndSortIdForRange(companyId,
							sortId, productId, cmpProduct.getOrderflg(), -1, 3);
			after_other_product_list = this.cmpProductService
					.getCmpProductListByCompanyIdAndSortIdForRange(companyId,
							sortId, productId, cmpProduct.getOrderflg(), 1, 3);
		}
		else {
			before_other_product_list = this.cmpProductService
					.getCmpProductListByCompanyIdForRange(companyId, productId,
							cmpProduct.getOrderflg(), -1, 3);
			after_other_product_list = this.cmpProductService
					.getCmpProductListByCompanyIdForRange(companyId, productId,
							cmpProduct.getOrderflg(), 1, 3);
		}
		req
				.setAttribute("before_other_product_list",
						before_other_product_list);
		req.setAttribute("after_other_product_list", after_other_product_list);
	}

	public String view0(HkRequest req, HkResponse resp) throws Exception {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.view00(req, resp);
		}
		return null;
	}

	public String view1(HkRequest req, HkResponse resp) throws Exception {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.view10(req, resp);
		}
		return null;
	}

	/**
	 * 产品单页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String view10(HkRequest req, HkResponse resp) throws Exception {
		long productId = req.getLongAndSetAttr("productId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {
			return null;
		}
		req.setAttribute("cmpProduct", cmpProduct);
		if (cmpProduct.getSortId() > 0) {
			CmpProductSort cmpProductSort = this.cmpProductService
					.getCmpProductSort(cmpProduct.getSortId());
			req.setAttribute("cmpProductSort", cmpProductSort);
		}
		req.setAttribute("sortId", cmpProduct.getSortId());
		// 图片
		List<CmpProductPhoto> photolist = this.cmpProductService
				.getCmpProductPhotoListByProductId(productId, 0, 30);
		req.setAttribute("photolist", photolist);
		long companyId = req.getLong("companyId");
		CmpProductSortAttrModule cmpProductSortAttrModule = this.cmpProductService
				.getCmpProductSortAttrModule(cmpProduct.getSortId());
		if (cmpProductSortAttrModule != null) {
			CmpProductSortAttrObject cmpProductSortAttrObject = new CmpProductSortAttrObject(
					cmpProductSortAttrModule.getAttrName());
			req.setAttribute("cmpProductSortAttrObject",
					cmpProductSortAttrObject);
			CmpProductAttr cmpProductAttr = this.cmpProductService
					.getCmpProductAttr(productId);
			List<Long> idList = new ArrayList<Long>();
			idList.add(cmpProductAttr.getAttr1());
			idList.add(cmpProductAttr.getAttr2());
			idList.add(cmpProductAttr.getAttr3());
			idList.add(cmpProductAttr.getAttr4());
			idList.add(cmpProductAttr.getAttr5());
			idList.add(cmpProductAttr.getAttr6());
			idList.add(cmpProductAttr.getAttr7());
			idList.add(cmpProductAttr.getAttr8());
			idList.add(cmpProductAttr.getAttr9());
			List<CmpProductSortAttr> list = this.cmpProductService
					.getCmpProductSortAttrListByCompanyIdAndSortIdAndInId(
							companyId, cmpProduct.getSortId(), idList);
			CmpProductAttrValue cmpProductAttrValue = new CmpProductAttrValue();
			for (CmpProductSortAttr o : list) {
				if (o.getAttrflg() == 1) {
					cmpProductAttrValue.setAttr1(o);
				}
				else if (o.getAttrflg() == 2) {
					cmpProductAttrValue.setAttr2(o);
				}
				else if (o.getAttrflg() == 3) {
					cmpProductAttrValue.setAttr3(o);
				}
				else if (o.getAttrflg() == 4) {
					cmpProductAttrValue.setAttr4(o);
				}
				else if (o.getAttrflg() == 5) {
					cmpProductAttrValue.setAttr5(o);
				}
				else if (o.getAttrflg() == 6) {
					cmpProductAttrValue.setAttr6(o);
				}
				else if (o.getAttrflg() == 7) {
					cmpProductAttrValue.setAttr7(o);
				}
				else if (o.getAttrflg() == 8) {
					cmpProductAttrValue.setAttr8(o);
				}
				else if (o.getAttrflg() == 9) {
					cmpProductAttrValue.setAttr9(o);
				}
			}
			req.setAttribute("cmpProductAttrValue", cmpProductAttrValue);
		}
		return this.getWebPath("mod/1/0/product/view.jsp");
	}

	/**
	 * 产品单页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String viewwap(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long productId = req.getLongAndSetAttr("productId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {
			return null;
		}
		req.setAttribute("cmpProduct", cmpProduct);
		return this.getWapPath("product/view.jsp");
	}

	/**
	 *产品搜索(电子商务模板用)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String searchname(HkRequest req, HkResponse resp) throws Exception {
		String key = req.getHtmlRow("key");
		req.setEncodeAttribute("key", key);
		PageSupport pageSupport = req.getPageSupport(15);
		long companyId = req.getLong("companyId");
		pageSupport.setTotalCount(this.cmpProductService
				.countCmpProductListByCompanyIdEx(companyId, 0, key));
		List<CmpProduct> list = this.cmpProductService
				.getCmpProductListByCompanyIdEx(companyId, 0, key, pageSupport
						.getBegin(), pageSupport.getSize());
		req.setAttribute("list", list);
		return this.getWebPath("mod/1/0/product/search.jsp");
	}
}