package com.hk.web.company.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductSort;
import com.hk.bean.CmpUnion;
import com.hk.bean.CmpUnionKind;
import com.hk.bean.Company;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;
import com.hk.svr.CmpUnionService;
import com.hk.svr.CompanyService;
import com.hk.svr.processor.CmpProductProcessor;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/e/op/product/op")
public class OpCmpProductAction extends BaseAction {

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpUnionService cmpUnionService;

	@Autowired
	private CmpProductProcessor cmpProductProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delproductweb(HkRequest req, HkResponse resp) {
		int pid = req.getInt("pid");
		this.cmpProductService.deleteCmpProduct(pid);
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "del_product");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delproduct(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		int pid = req.getInt("pid");
		this.cmpProductService.deleteCmpProduct(pid);
		req.setText("op.exeok");
		// CmpProduct o = this.cmpProductService.getCmpProduct(pid);
		// if (o != null) {
		// o.setDelflg(CmpProduct.DELFLG_Y);
		// try {
		// this.cmpProductService.updateProduct(o);
		// }
		// catch (ProductNameDuplicateException e) {//
		// }
		// req.setText("op.exeok");
		// }
		return "r:/e/op/product/op_productlist.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editproductweb(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		int pid = req.getInt("pid");
		int sortId = req.getInt("sortId");
		String name = req.getString("name");
		double money = req.getDouble("money");
		double rebate = req.getDouble("rebate");
		String intro = req.getString("intro");
		String pnum = req.getString("pnum");
		String shortName = req.getString("shortName");
		CmpProduct o = this.cmpProductService.getCmpProduct(pid);
		o.setCompanyId(companyId);
		o.setSortId(sortId);
		o.setName(DataUtil.toHtmlRow(name));
		o.setMoney(money);
		o.setRebate(rebate);
		o.setIntro(DataUtil.toHtmlRow(intro));
		o.setShortName(DataUtil.toHtmlRow(shortName));
		o.setPnum(DataUtil.toHtmlRow(pnum));
		int code = o.validate();
		req.setAttribute("o", o);
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "edit_product");
		}
		if (this.cmpProductService.updateProduct(o)) {
			this.setOpFuncSuccessMsg(req);
			return this.initSuccess(req, "edit_product");
		}
		return this.initError(req, Err.CMPPORDUCTSORT_NAME_DUPLICATE,
				"edit_product");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editproduct(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		int pid = req.getInt("pid");
		int sortId = req.getInt("sortId");
		String name = req.getString("name");
		double money = req.getDouble("money");
		double rebate = req.getDouble("rebate");
		String intro = req.getString("intro");
		CmpProduct o = this.cmpProductService.getCmpProduct(pid);
		o.setCompanyId(companyId);
		o.setSortId(sortId);
		o.setName(DataUtil.toHtmlRow(name));
		o.setMoney(money);
		o.setRebate(rebate);
		o.setIntro(DataUtil.toHtmlRow(intro));
		int code = o.validate();
		req.setAttribute("o", o);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/e/op/product/op_toeditproduct.do";
		}
		if (this.cmpProductService.updateProduct(o)) {
			req.setSessionText("op.exeok");
			return "r:/e/op/product/op_viewproduct.do?companyId=" + companyId
					+ "&pid=" + o.getProductId();
		}
		req.setText("func.cmpproduct.name.duplicate");
		return "/e/op/product/op_toeditproduct.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditproduct(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		int pid = req.getInt("pid");
		CmpProduct o = (CmpProduct) req.getAttribute("o");
		if (o == null) {
			o = this.cmpProductService.getCmpProduct(pid);
		}
		List<CmpProductSort> sortlist = this.cmpProductService
				.getCmpProductSortList(companyId);
		req.setAttribute("o", o);
		req.setAttribute("companyId", companyId);
		req.setAttribute("pid", pid);
		req.setAttribute("sortlist", sortlist);
		return "/WEB-INF/page/e/product/op/editproduct.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String product(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		int pid = req.getInt("pid");
		CmpProduct o = (CmpProduct) req.getAttribute("o");
		if (o == null) {
			o = this.cmpProductService.getCmpProduct(pid);
		}
		req.setAttribute("o", o);
		req.setAttribute("companyId", companyId);
		req.setAttribute("pid", pid);
		List<CmpProductSort> sortlist = this.cmpProductService
				.getCmpProductSortList(companyId);
		req.setAttribute("sortlist", sortlist);
		return this.getWeb3Jsp("e/product/op/product_data.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String viewproduct(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		int pid = req.getInt("pid");
		CmpProduct o = this.cmpProductService.getCmpProduct(pid);
		CmpProductSort sort = this.cmpProductService.getCmpProductSort(o
				.getSortId());
		req.setAttribute("o", o);
		req.setAttribute("sort", sort);
		req.setAttribute("companyId", companyId);
		req.setAttribute("pid", pid);
		return "/WEB-INF/page/e/product/op/product.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toaddproduct(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		List<CmpProductSort> sortlist = this.cmpProductService
				.getCmpProductSortList(companyId);
		req.setAttribute("sortlist", sortlist);
		req.setAttribute("companyId", companyId);
		return "/WEB-INF/page/e/product/op/addproduct.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addproductweb(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		int sortId = req.getInt("sortId");
		String name = req.getString("name");
		double money = req.getDouble("money");
		double rebate = req.getDouble("rebate");
		String intro = req.getString("intro");
		String pnum = req.getString("pnum");
		String shortName = req.getString("shortName");
		long uid = 0;
		Company company = companyService.getCompany(companyId);
		if (company.getUid() >= 0) {
			CmpUnion cmpUnion = cmpUnionService.getCmpUnion(company.getUid());
			if (cmpUnion != null) {
				uid = cmpUnion.getUid();
			}
		}
		CmpProduct o = new CmpProduct();
		o.setUid(uid);
		o.setCompanyId(companyId);
		o.setSortId(sortId);
		o.setName(DataUtil.toHtmlRow(name));
		o.setMoney(money);
		o.setRebate(rebate);
		o.setIntro(DataUtil.toHtmlRow(intro));
		o.setShortName(DataUtil.toHtmlRow(shortName));
		o.setPnum(DataUtil.toHtmlRow(pnum));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "add_product");
		}
		if (uid > 0) {
			o.setCmpUnionKindId(company.getUnionKindId());
		}
		if (this.cmpProductProcessor.createProduct(o)) {
			this.setOpFuncSuccessMsg(req);
			return this.initSuccess(req, "add_product");
		}
		return this.initError(req, Err.CMPPORDUCTSORT_NAME_DUPLICATE,
				"add_product");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addproduct(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		int sortId = req.getInt("sortId");
		String name = req.getString("name");
		double money = req.getDouble("money");
		double rebate = req.getDouble("rebate");
		String intro = req.getString("intro");
		String pnum = req.getString("pnum");
		String shortName = req.getString("shortName");
		long uid = 0;
		Company company = companyService.getCompany(companyId);
		if (company.getUid() >= 0) {
			CmpUnion cmpUnion = cmpUnionService.getCmpUnion(company.getUid());
			if (cmpUnion != null) {
				uid = cmpUnion.getUid();
			}
		}
		CmpProduct o = new CmpProduct();
		o.setUid(uid);
		o.setCompanyId(companyId);
		o.setSortId(sortId);
		o.setName(DataUtil.toHtmlRow(name));
		o.setMoney(money);
		o.setRebate(rebate);
		o.setIntro(DataUtil.toHtmlRow(intro));
		o.setShortName(DataUtil.toHtmlRow(shortName));
		o.setPnum(DataUtil.toHtmlRow(pnum));
		int code = o.validate();
		req.setAttribute("o", o);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/e/op/product/op_toaddproduct.do";
		}
		if (uid > 0) {
			o.setCmpUnionKindId(company.getUnionKindId());
		}
		if (this.cmpProductProcessor.createProduct(o)) {
			req.setSessionText("op.exeok");
			return "r:/e/op/product/op_viewproduct.do?companyId=" + companyId
					+ "&pid=" + o.getProductId();
		}
		req.setText("func.cmpproduct.name.duplicate");
		return "/e/op/product/op_toaddproduct.do";
	}

	/**
	 * 产品日常管理
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String productlistweb2(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		String s_name = req.getString("s_name");
		int s_sortId = req.getInt("s_sortId");
		byte s_sellStatus = req.getByte("s_sellStatus", (byte) -1);
		int size = 20;
		// SimplePage page = req.getSimplePage(size);
		PageSupport page = req.getPageSupport(size);
		page.setTotalCount(this.cmpProductService.countCmpProduct(companyId,
				s_name, s_sortId, s_sellStatus));
		List<CmpProduct> list = this.cmpProductService.getCmpProductList(
				companyId, s_name, s_sortId, s_sellStatus, page.getBegin(),
				page.getSize());
		List<CmpProductSort> sortlist = this.cmpProductService
				.getCmpProductSortList(companyId);
		Map<Integer, CmpProductSort> map = new HashMap<Integer, CmpProductSort>();
		for (CmpProductSort o : sortlist) {
			map.put(o.getSortId(), o);
		}
		for (CmpProduct o : list) {
			o.setCmpProductSort(map.get(o.getSortId()));
		}
		req.setAttribute("sortlist", sortlist);
		req.setAttribute("list", list);
		req.setAttribute("companyId", companyId);
		req.setAttribute("s_sortId", s_sortId);
		req.setAttribute("s_sellStatus", s_sellStatus);
		req.setAttribute("s_name", s_name);
		req.setEncodeAttribute("s_name", s_name);
		req.setAttribute("op_func", 10);
		return this.getWeb3Jsp("e/product/op/productlist2.jsp");
	}

	/**
	 * 产品日常管理
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String productlistweb3(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		String s_name = req.getString("s_name");
		int s_sortId = req.getInt("s_sortId");
		byte s_sellStatus = req.getByte("s_sellStatus", (byte) -1);
		int size = 20;
		PageSupport page = req.getPageSupport(size);
		page.setTotalCount(this.cmpProductService.countCmpProduct(companyId,
				s_name, s_sortId, s_sellStatus));
		List<CmpProduct> list = this.cmpProductService.getCmpProductList(
				companyId, s_name, s_sortId, s_sellStatus, page.getBegin(),
				page.getSize());
		List<Long> idList = new ArrayList<Long>();
		for (CmpProduct cmpProduct : list) {
			idList.add(cmpProduct.getCmpUnionKindId());
		}
		Map<Long, CmpUnionKind> map = this.cmpUnionService
				.getCmpUnionKindMapInId(idList);
		for (CmpProduct cmpProduct : list) {
			cmpProduct.setCmpUnionKind(map.get(cmpProduct.getCmpUnionKindId()));
		}
		req.setAttribute("list", list);
		req.setAttribute("companyId", companyId);
		req.setAttribute("s_sortId", s_sortId);
		req.setAttribute("s_sellStatus", s_sellStatus);
		req.setAttribute("s_name", s_name);
		req.setEncodeAttribute("s_name", s_name);
		req.setAttribute("op_func", 19);
		return this.getWeb3Jsp("e/product/op/productlist3.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String selcmpunionkind(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long productId = req.getLong("productId");
		long kindId = req.getLong("kindId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		CmpUnionKind cmpUnionKind = this.cmpUnionService
				.getCmpUnionKind(kindId);
		if (cmpUnionKind.isHasChild()) {
			return null;
		}
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {
			return null;
		}
		if (cmpUnionKind.getUid() != company.getUid()) {
			return null;
		}
		cmpProduct.setCmpUnionKindId(kindId);
		this.cmpProductService.updateProduct(cmpProduct);
		req.setSessionText("func.company.product.selcmpunionkindok");
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toselcmpunionkind(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long productId = req.getLongAndSetAttr("productId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {
			return null;
		}
		req.setAttribute("cmpProduct", cmpProduct);
		Company company = this.companyService.getCompany(companyId);
		long uid = company.getUid();
		long parentId = req.getLongAndSetAttr("parentId");
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpUnionService.countCmpUnionKindByUid(uid,
				parentId));
		List<CmpUnionKind> list = this.cmpUnionService
				.getCmpUnionKindListByUid(uid, parentId, page.getBegin(), page
						.getSize());
		req.setAttribute("list", list);
		List<CmpUnionKind> list2 = new ArrayList<CmpUnionKind>();
		this.loadCmpUnionKindList(list2, parentId);
		req.setAttribute("list2", list2);
		return this.getWeb3Jsp("e/product/op/selcmpunionkind.jsp");
	}

	/**
	 * 产品添加或修改
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String productlistweb(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		String s_name = req.getString("s_name");
		int s_sortId = req.getInt("s_sortId");
		int size = 20;
		PageSupport page = req.getPageSupport(size);
		page.setTotalCount(this.cmpProductService.countCmpProduct(companyId,
				s_name, s_sortId, (byte) -1));
		List<CmpProduct> list = this.cmpProductService.getCmpProductList(
				companyId, s_name, s_sortId, (byte) -1, page.getBegin(), page
						.getSize());
		List<CmpProductSort> sortlist = this.cmpProductService
				.getCmpProductSortList(companyId);
		Map<Integer, CmpProductSort> map = new HashMap<Integer, CmpProductSort>();
		for (CmpProductSort o : sortlist) {
			map.put(o.getSortId(), o);
		}
		for (CmpProduct o : list) {
			o.setCmpProductSort(map.get(o.getSortId()));
		}
		List<Long> idList = new ArrayList<Long>();
		for (CmpProduct cmpProduct : list) {
			if (cmpProduct.getCmpUnionKindId() > 0) {
				idList.add(cmpProduct.getCmpUnionKindId());
			}
		}
		Map<Long, CmpUnionKind> kindmap = this.cmpUnionService
				.getCmpUnionKindMapInId(idList);
		for (CmpProduct cmpProduct : list) {
			cmpProduct.setCmpUnionKind(kindmap.get(cmpProduct
					.getCmpUnionKindId()));
		}
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("uid", company.getUid());
		req.setAttribute("sortlist", sortlist);
		req.setAttribute("list", list);
		req.setAttribute("companyId", companyId);
		req.setAttribute("s_sortId", s_sortId);
		req.setAttribute("s_name", s_name);
		req.setEncodeAttribute("s_name", s_name);
		req.setAttribute("op_func", 2);
		return this.getWeb3Jsp("e/product/op/productlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String productlist(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		String name = req.getString("name");
		int sortId = req.getInt("sortId");
		int size = 20;
		SimplePage page = req.getSimplePage(size);
		List<CmpProduct> list = this.cmpProductService.getCmpProductList(
				companyId, name, sortId, (byte) -1, page.getBegin(), size);
		List<CmpProductSort> sortlist = this.cmpProductService
				.getCmpProductSortList(companyId);
		req.setAttribute("sortlist", sortlist);
		req.setAttribute("list", list);
		req.setAttribute("companyId", companyId);
		req.setAttribute("sortId", sortId);
		req.setEncodeAttribute("name", name);
		return "/WEB-INF/page/e/product/op/productlist.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addsortweb(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		String name = req.getString("name");
		CmpProductSort o = new CmpProductSort();
		o.setName(DataUtil.toHtmlRow(name));
		o.setCompanyId(companyId);
		int code = o.validate();
		req.setAttribute("o", o);
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "add_sort");
		}
		this.cmpProductService.createCmpProductSort(o);
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "add_sort");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addsort(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		String name = req.getString("name");
		CmpProductSort o = new CmpProductSort();
		o.setName(DataUtil.toHtmlRow(name));
		o.setCompanyId(companyId);
		int code = o.validate();
		req.setAttribute("o", o);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/e/op/product/op_sortlist.do";
		}
		this.cmpProductService.createCmpProductSort(o);
		req.setSessionText("op.exeok");
		return "r:/e/op/product/op_sortlist.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditsort(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		int sortId = req.getInt("sortId");
		CmpProductSort o = (CmpProductSort) req.getAttribute("o");
		if (o == null) {
			o = this.cmpProductService.getCmpProductSort(sortId);
		}
		req.setAttribute("o", o);
		req.setAttribute("companyId", companyId);
		req.setAttribute("sortId", sortId);
		return "/WEB-INF/page/e/product/op/editsort.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editsortweb(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		int sortId = req.getInt("sortId");
		String name = req.getString("name");
		CmpProductSort o = this.cmpProductService.getCmpProductSort(sortId);
		o.setName(DataUtil.toHtmlRow(name));
		o.setCompanyId(companyId);
		int code = o.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "edit_sort");
		}
		this.cmpProductService.updateCmpProductSort(o);
		return this.initSuccess(req, "edit_sort");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editsort(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		int sortId = req.getInt("sortId");
		String name = req.getString("name");
		CmpProductSort o = this.cmpProductService.getCmpProductSort(sortId);
		o.setName(DataUtil.toHtmlRow(name));
		o.setCompanyId(companyId);
		int code = o.validate();
		req.setAttribute("o", o);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/e/op/product/op_sortlist.do";
		}
		this.cmpProductService.updateCmpProductSort(o);
		req.setSessionText("op.exeok");
		return "r:/e/op/product/op_sortlist.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delsortweb(HkRequest req, HkResponse resp) throws Exception {
		int sortId = req.getInt("sortId");
		this.cmpProductService.deleteCmpPorductSort(sortId);
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "del_sort");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delsort(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		int sortId = req.getInt("sortId");
		this.cmpProductService.deleteCmpPorductSort(sortId);
		req.setSessionText("op.exeok");
		return "r:/e/op/product/op_sortlist.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sortlistweb(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		List<CmpProductSort> list = this.cmpProductService
				.getCmpProductSortList(companyId);
		req.setAttribute("list", list);
		req.setAttribute("companyId", companyId);
		this.setCompanyInfo(req);
		req.setAttribute("op_func", 1);// 页面显示当前激活项
		return this.getWeb3Jsp("e/product/op/sortlist.jsp");
	}

	private void setCompanyInfo(HkRequest req) {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sortlist(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		List<CmpProductSort> list = this.cmpProductService
				.getCmpProductSortList(companyId);
		req.setAttribute("list", list);
		req.setAttribute("companyId", companyId);
		return "/WEB-INF/page/e/product/op/sortlist.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String stopsell(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long productId = req.getLong("productId");
		this.cmpProductService.stopSell(productId);
		req.setSessionText("func.cmpproduct.stopsell");
		return "r:/e/op/product/op_productlistweb2.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String runsell(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long productId = req.getLong("productId");
		this.cmpProductService.runSell(productId);
		req.setSessionText("func.cmpproduct.runsell");
		return "r:/e/op/product/op_productlistweb2.do?companyId=" + companyId;
	}
}