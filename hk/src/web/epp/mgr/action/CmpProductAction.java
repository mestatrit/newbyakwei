package web.epp.mgr.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductAttr;
import com.hk.bean.CmpProductPhoto;
import com.hk.bean.CmpProductSort;
import com.hk.bean.CmpProductSortAttr;
import com.hk.bean.CmpProductSortAttrModule;
import com.hk.bean.CmpProductSortAttrObject;
import com.hk.bean.CmpProductSortFile;
import com.hk.bean.CmpUtil;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;
import com.hk.svr.CmpProductSortFileService;
import com.hk.svr.CompanyService;
import com.hk.svr.processor.CmpProductProcessor;
import com.hk.svr.processor.UploadCmpProductPhotoResult;
import com.hk.svr.pub.Err;

/**
 * 产品
 * 
 * @author akwei
 */
@Component("/epp/web/op/webadmin/cmpproduct")
public class CmpProductAction extends EppBaseAction {

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private CmpProductProcessor cmpProductProcessor;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpProductSortFileService cmpProductSortFileService;

	public String execute(HkRequest req, HkResponse resp) {
		// req.setAttribute("active_11", 1);
		req.reSetAttribute("navoid");
		long companyId = req.getLong("companyId");
		int sortId = req.getIntAndSetAttr("sortId");
		SimplePage page = req.getSimplePage(20);
		List<CmpProduct> list = this.cmpProductProcessor
				.getCmpProductListForAdmin(companyId, null, sortId, (byte) -1,
						true, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		if (sortId > 0) {
			CmpProductSort cmpProductSort = this.cmpProductService
					.getCmpProductSort(sortId);
			req.setAttribute("cmpProductSort", cmpProductSort);
		}
		return this.getWebPath("admin/cmpproduct/list.jsp");
	}

	/**
	 * 企业产品分类列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String kindlist(HkRequest req, HkResponse resp) {
		req.reSetAttribute("navoid");
		req.setAttribute("active_10", 1);
		long companyId = req.getLong("companyId");
		int parentId = req.getIntAndSetAttr("parentId");
		List<CmpProductSort> list = this.cmpProductService
				.getCmpProductSortListByParentId(companyId, parentId);
		req.setAttribute("list", list);
		if (parentId > 0) {
			CmpProductSort parent = this.cmpProductService
					.getCmpProductSort(parentId);
			req.setAttribute("parent", parent);
			List<CmpProductSort> parentlist = this.cmpProductService
					.getCmpProductSortInId(companyId, parent.getParentIds());
			req.setAttribute("parentlist", parentlist);
			req.setAttribute("end_parentlist_idx", parentlist.size() - 1);
		}
		return this.getWebPath("admin/cmpproduct/kindlist.jsp");
	}

	/**
	 * 添加企业产品分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String createkind(HkRequest req, HkResponse resp) {
		req.reSetAttribute("navoid");
		req.setAttribute("active_10", 1);
		int ch = req.getInt("ch");
		if (ch == 0) {
			int parentId = req.getIntAndSetAttr("parentId");
			CmpProductSort parent = this.cmpProductService
					.getCmpProductSort(parentId);
			req.setAttribute("parent", parent);
			return this.getWebPath("admin/cmpproduct/createkind.jsp");
		}
		long companyId = req.getLong("companyId");
		String name = req.getHtmlRow("name");
		CmpProductSort cmpProductSort = new CmpProductSort();
		cmpProductSort.setCompanyId(companyId);
		cmpProductSort.setParentId(req.getInt("parentId"));
		cmpProductSort.setName(name);
		int code = cmpProductSort.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpProductService.createCmpProductSort(cmpProductSort);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * 修改企业产品分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String updatekind(HkRequest req, HkResponse resp) {
		req.reSetAttribute("navoid");
		req.setAttribute("active_10", 1);
		int ch = req.getInt("ch");
		int sortId = req.getIntAndSetAttr("sortId");
		CmpProductSort cmpProductSort = this.cmpProductService
				.getCmpProductSort(sortId);
		req.setAttribute("cmpProductSort", cmpProductSort);
		if (ch == 0) {
			int parentId = req.getIntAndSetAttr("parentId");
			CmpProductSort parent = this.cmpProductService
					.getCmpProductSort(parentId);
			req.setAttribute("parent", parent);
			return this.getWebPath("admin/cmpproduct/updatekind.jsp");
		}
		cmpProductSort.setName(req.getHtmlRow("name"));
		int code = cmpProductSort.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpProductService.updateCmpProductSort(cmpProductSort);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 删除企业产品分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String delkind(HkRequest req, HkResponse resp) {
		int sortId = req.getInt("sortId");
		this.cmpProductService.deleteCmpPorductSort(sortId);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * 添加企业产品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String create(HkRequest req, HkResponse resp) {
		req.reSetAttribute("navoid");
		req.setAttribute("active_11", 1);
		long companyId = req.getLong("companyId");
		List<CmpProductSort> sortlist = this.cmpProductService
				.getCmpProductSortList(companyId);
		req.setAttribute("sortlist", sortlist);
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("admin/cmpproduct/create.jsp");
		}
		Company company = this.companyService.getCompany(companyId);
		CmpProduct cmpProduct = new CmpProduct();
		cmpProduct.setPcityId(company.getPcityId());
		cmpProduct.setCompanyId(companyId);
		cmpProduct.setSortId(req.getInt("sortId"));
		cmpProduct.setName(req.getHtmlRow("name"));
		cmpProduct.setIntro(req.getHtml("intro"));
		cmpProduct.setMoney(req.getDouble("money"));
		cmpProduct.setRebate(req.getDouble("rebate"));
		int code = cmpProduct.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		if (!this.cmpProductProcessor.createProduct(cmpProduct)) {
			return this.onError(req, Err.CMPPRODUCT_NAME_DUPLICATE,
					"createerror", null);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * 修改企业产品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String update(HkRequest req, HkResponse resp) {
		req.reSetAttribute("navoid");
		req.setAttribute("active_11", 1);
		int ch = req.getInt("ch");
		long productId = req.getLongAndSetAttr("productId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		req.setAttribute("cmpProduct", cmpProduct);
		long companyId = req.getLong("companyId");
		List<CmpProductSort> sortlist = this.cmpProductService
				.getCmpProductSortList(companyId);
		req.setAttribute("sortlist", sortlist);
		if (ch == 0) {
			return this.getWebPath("admin/cmpproduct/update.jsp");
		}
		cmpProduct.setSortId(req.getInt("sortId"));
		cmpProduct.setName(req.getHtmlRow("name"));
		cmpProduct.setIntro(req.getHtml("intro"));
		cmpProduct.setMoney(req.getDouble("money"));
		cmpProduct.setRebate(req.getDouble("rebate"));
		int code = cmpProduct.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		if (!this.cmpProductService.updateProduct(cmpProduct)) {
			return this.onError(req, Err.CMPPRODUCT_NAME_DUPLICATE,
					"updateerror", null);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 删除企业产品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long productId = req.getLong("productId");
		this.cmpProductService.deleteCmpProduct(productId);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * 企业推荐产品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String setcmppink(HkRequest req, HkResponse resp) throws Exception {
		long productId = req.getLong("productId");
		this.cmpProductService.updateCmpProductCmppink(productId,
				CmpUtil.CMPPINK_Y);
		this.setPinkSuccessMsg(req);
		return null;
	}

	/**
	 * 取消企业推荐产品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String delcmppink(HkRequest req, HkResponse resp) throws Exception {
		long productId = req.getLong("productId");
		this.cmpProductService.updateCmpProductCmppink(productId,
				CmpUtil.CMPPINK_N);
		this.setDelPinkSuccessMsg(req);
		return null;
	}

	/**
	 * 产品图片列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-16
	 */
	public String piclist(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_11", 1);
		long productId = req.getLongAndSetAttr("productId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {
			return "r:/epp/web/op/webadmin/cmpproduct.do?companyId="
					+ req.getLong("companyId") + "&navoid="
					+ req.getLong("navoid");
		}
		req.setAttribute("head", cmpProduct.getHeadPath());
		req.setAttribute("cmpProduct", cmpProduct);
		List<CmpProductPhoto> list = this.cmpProductService
				.getCmpProductPhotoListByProductId(productId, 0, 30);
		req.setAttribute("list", list);
		req.reSetAttribute("companyId");
		return this.getWebPath("admin/cmpproduct/pic/list.jsp");
	}

	/**
	 * 最多只能上传30张
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String uploadpic(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long productId = req.getLong("productId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {// 不存在的产品
			return null;
		}
		File[] files = req.getFiles();
		User loginUser = this.getLoginUser2(req);
		String return_path = "r:/epp/web/op/webadmin/cmpproduct_piclist.do?companyId="
				+ companyId + "&productId=" + productId;
		UploadCmpProductPhotoResult uploadCmpProductPhotoResult = this.cmpProductProcessor
				.uploadCmpProductPhoto(cmpProduct, loginUser.getUserId(), files);
		if (!uploadCmpProductPhotoResult.isCanUpload()) {
			req
					.setSessionText(String
							.valueOf(Err.CMPPRODUCTPHOTO_OUT_OF_LIMIT));
			return return_path;
		}
		if (uploadCmpProductPhotoResult.getErrornum() > 0) {
			req.setSessionText("funct.pic.upload.error.info",
					uploadCmpProductPhotoResult.getErrornum());
		}
		return return_path;
	}

	/**
	 * 设置产品头图
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sethead(HkRequest req, HkResponse resp) {
		long oid = req.getInt("oid");
		CmpProductPhoto photo = this.cmpProductService.getCmpProductPhoto(oid);
		if (photo == null) {
			return null;
		}
		long productId = req.getLong("productId");
		this.cmpProductService.updateCmpProductHeadPath(productId, photo
				.getPath());
		req.setSessionText("op.setheadok");
		return null;
	}

	/**
	 * 删除图片 如果被删除的图片是头图，就更新产品头图
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delpic(HkRequest req, HkResponse resp) {
		long oid = req.getInt("oid");
		long productId = req.getLong("productId");
		this.cmpProductProcessor.deletePhoto(productId, oid);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * 产品排序，设置顺序号
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String setorderflg(HkRequest req, HkResponse resp) throws Exception {
		long productId = req.getLong("productId");
		int orderflg = req.getInt("orderflg");
		this.cmpProductService.updateCmpProductOrderflg(productId, orderflg);
		req.setSessionText("epp.cmpproduct.orderflg.set.success");
		return this.onSuccess2(req, "setorderflgok", null);
	}

	/**
	 * 产品分类属性模板设置
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String saveattrmodule(HkRequest req, HkResponse resp) {
		if (!this.isCmpInfoOpenProductattrflg(req)) {
			return null;
		}
		int sortId = req.getIntAndSetAttr("sortId");
		req.reSetAttribute("parentId");
		CmpProductSort cmpProductSort = this.cmpProductService
				.getCmpProductSort(sortId);
		req.setAttribute("cmpProductSort", cmpProductSort);
		CmpProductSortAttrModule cmpProductSortAttrModule = this.cmpProductService
				.getCmpProductSortAttrModule(sortId);
		if (cmpProductSortAttrModule != null) {
			CmpProductSortAttrObject cmpProductSortAttrObject = new CmpProductSortAttrObject(
					cmpProductSortAttrModule.getAttrName());
			req.setAttribute("cmpProductSortAttrObject",
					cmpProductSortAttrObject);
		}
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/cmpproduct/savesortattrmodule.jsp");
		}
		CmpProductSortAttrObject cmpProductSortAttrObject = new CmpProductSortAttrObject();
		cmpProductSortAttrObject.setAttr1Name(req.getHtmlRow("attr1Name"));
		cmpProductSortAttrObject.setAttr2Name(req.getHtmlRow("attr2Name"));
		cmpProductSortAttrObject.setAttr3Name(req.getHtmlRow("attr3Name"));
		cmpProductSortAttrObject.setAttr4Name(req.getHtmlRow("attr4Name"));
		cmpProductSortAttrObject.setAttr5Name(req.getHtmlRow("attr5Name"));
		cmpProductSortAttrObject.setAttr6Name(req.getHtmlRow("attr6Name"));
		cmpProductSortAttrObject.setAttr7Name(req.getHtmlRow("attr7Name"));
		cmpProductSortAttrObject.setAttr8Name(req.getHtmlRow("attr8Name"));
		cmpProductSortAttrObject.setAttr9Name(req.getHtmlRow("attr9Name"));
		cmpProductSortAttrObject.setAttr1Pink(req.getBoolean("attr1Pink"));
		cmpProductSortAttrObject.setAttr2Pink(req.getBoolean("attr2Pink"));
		cmpProductSortAttrObject.setAttr3Pink(req.getBoolean("attr3Pink"));
		cmpProductSortAttrObject.setAttr4Pink(req.getBoolean("attr4Pink"));
		cmpProductSortAttrObject.setAttr5Pink(req.getBoolean("attr5Pink"));
		cmpProductSortAttrObject.setAttr6Pink(req.getBoolean("attr6Pink"));
		cmpProductSortAttrObject.setAttr7Pink(req.getBoolean("attr7Pink"));
		cmpProductSortAttrObject.setAttr8Pink(req.getBoolean("attr8Pink"));
		cmpProductSortAttrObject.setAttr9Pink(req.getBoolean("attr9Pink"));
		int errpos = cmpProductSortAttrObject.getValidateErrorPos();
		if (errpos != 0) {
			return this.onError(req,
					Err.CMPPRODUCTSORTATTRMODULE_ATTRNAME_ERROR, "saveerror",
					errpos);
		}
		if (cmpProductSortAttrModule == null) {
			cmpProductSortAttrModule = new CmpProductSortAttrModule();
			cmpProductSortAttrModule.setCompanyId(req.getLong("companyId"));
			cmpProductSortAttrModule.setSortId(sortId);
			cmpProductSortAttrModule.setAttrName(cmpProductSortAttrObject
					.toJson());
			this.cmpProductService
					.createCmpProductSortAttrModule(cmpProductSortAttrModule);
		}
		else {
			cmpProductSortAttrModule.setAttrName(cmpProductSortAttrObject
					.toJson());
			this.cmpProductService
					.updateCmpProductSortAttrModule(cmpProductSortAttrModule);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "saveok", null);
	}

	/**
	 *创建产品分类属性模板属性数据设置
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String sortattrvalue(HkRequest req, HkResponse resp) {
		if (!this.isCmpInfoOpenProductattrflg(req)) {
			return null;
		}
		int sortId = req.getIntAndSetAttr("sortId");
		int attrflg = req.getIntAndSetAttr("attrflg");
		req.reSetAttribute("parentId");
		CmpProductSort cmpProductSort = this.cmpProductService
				.getCmpProductSort(sortId);
		req.setAttribute("cmpProductSort", cmpProductSort);
		CmpProductSortAttrModule cmpProductSortAttrModule = this.cmpProductService
				.getCmpProductSortAttrModule(sortId);
		if (cmpProductSortAttrModule != null) {
			CmpProductSortAttrObject cmpProductSortAttrObject = new CmpProductSortAttrObject(
					cmpProductSortAttrModule.getAttrName());
			req.setAttribute("cmpProductSortAttrObject",
					cmpProductSortAttrObject);
			req.setAttribute("attrName", cmpProductSortAttrObject
					.getAttr(String.valueOf(attrflg)));
		}
		List<CmpProductSortAttr> list = this.cmpProductService
				.getCmpProductSortAttrListBySortIdAndAttrflg(sortId, attrflg);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmpproduct/sortattrvalue.jsp");
	}

	/**
	 *创建产品分类属性模板属性数据设置
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String createsortattrvalue(HkRequest req, HkResponse resp) {
		if (!this.isCmpInfoOpenProductattrflg(req)) {
			return null;
		}
		long sortId = req.getLongAndSetAttr("sortId");
		long companyId = req.getLong("companyId");
		int attrflg = req.getInt("attrflg");
		CmpProductSortAttr cmpProductSortAttr = new CmpProductSortAttr();
		cmpProductSortAttr.setAttrflg(attrflg);
		cmpProductSortAttr.setCompanyId(companyId);
		cmpProductSortAttr.setSortId(sortId);
		cmpProductSortAttr.setName(req.getHtmlRow("name"));
		int code = cmpProductSortAttr.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpProductService.createCmpProductSortAttr(cmpProductSortAttr);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * 修改产品分类属性模板属性数据设置
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String updatesortattrvalue(HkRequest req, HkResponse resp) {
		if (!this.isCmpInfoOpenProductattrflg(req)) {
			return null;
		}
		req.reSetAttribute("sortId");
		req.reSetAttribute("parentId");
		req.reSetAttribute("attrflg");
		long attrId = req.getLongAndSetAttr("attrId");
		CmpProductSortAttr cmpProductSortAttr = this.cmpProductService
				.getCmpProductSortAttr(attrId);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpProductSortAttr", cmpProductSortAttr);
			return this.getWebPath("admin/cmpproduct/updatesortattrvalue.jsp");
		}
		cmpProductSortAttr.setName(req.getHtmlRow("name"));
		int code = cmpProductSortAttr.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpProductService.updateCmpProductSortAttr(cmpProductSortAttr);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 删除属性值
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String delsortattrvalue(HkRequest req, HkResponse resp) {
		if (!this.isCmpInfoOpenProductattrflg(req)) {
			return null;
		}
		long attrId = req.getLongAndSetAttr("attrId");
		this.cmpProductService.deleteCmpProductSortAttr(attrId);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * 产品分类属性模板设置
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String saveproductattr(HkRequest req, HkResponse resp) {
		if (!this.isCmpInfoOpenProductattrflg(req)) {
			return null;
		}
		long productId = req.getLongAndSetAttr("productId");
		long companyId = req.getLong("companyId");
		req.reSetAttribute("navoid");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {
			return null;
		}
		CmpProductAttr cmpProductAttr = this.cmpProductService
				.getCmpProductAttr(productId);
		int sortId = cmpProduct.getSortId();
		CmpProductSort cmpProductSort = this.cmpProductService
				.getCmpProductSort(sortId);
		CmpProductSortAttrModule cmpProductSortAttrModule = this.cmpProductService
				.getCmpProductSortAttrModule(sortId);
		if (cmpProductSortAttrModule != null) {
			CmpProductSortAttrObject cmpProductSortAttrObject = new CmpProductSortAttrObject(
					cmpProductSortAttrModule.getAttrName());
			req.setAttribute("cmpProductSortAttrObject",
					cmpProductSortAttrObject);
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpProductAttr", cmpProductAttr);
			req.setAttribute("cmpProductSort", cmpProductSort);
			req.setAttribute("cmpProduct", cmpProduct);
			List<CmpProductSortAttr> attrlist = this.cmpProductService
					.getCmpProductSortAttrListBySortId(sortId);
			req.setAttribute("attrlist", attrlist);
			return this.getWebPath("admin/cmpproduct/saveproductattr.jsp");
		}
		boolean add = false;
		if (cmpProductAttr == null) {
			cmpProductAttr = new CmpProductAttr();
			cmpProductAttr.setProductId(productId);
			cmpProductAttr.setCompanyId(companyId);
			add = true;
		}
		cmpProductAttr.setAttr1(req.getLong("attr1"));
		cmpProductAttr.setAttr2(req.getLong("attr2"));
		cmpProductAttr.setAttr3(req.getLong("attr3"));
		cmpProductAttr.setAttr4(req.getLong("attr4"));
		cmpProductAttr.setAttr5(req.getLong("attr5"));
		cmpProductAttr.setAttr6(req.getLong("attr6"));
		cmpProductAttr.setAttr7(req.getLong("attr7"));
		cmpProductAttr.setAttr8(req.getLong("attr8"));
		cmpProductAttr.setAttr9(req.getLong("attr9"));
		if (add) {
			this.cmpProductService.createCmpProductAttr(cmpProductAttr);
		}
		else {
			this.cmpProductService.updateCmpProductAttr(cmpProductAttr);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "saveok", null);
	}

	private boolean isCmpInfoOpenProductattrflg(HkRequest req) {
		Company o = (Company) req.getAttribute("o");
		if (o == null) {
			return false;
		}
		return o.isOpenProductattrflg();
	}

	/**
	 * 设置分类在首页的广告图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-17
	 */
	public String sortpic(HkRequest req, HkResponse resp) throws Exception {
		int sortId = req.getIntAndSetAttr("sortId");
		req.reSetAttribute("parentId");
		long companyId = req.getLongAndSetAttr("companyId");
		CmpProductSort cmpProductSort = this.cmpProductService
				.getCmpProductSort(sortId);
		List<CmpProductSortFile> list = this.cmpProductSortFileService
				.getCmpProductSortFileListByCompanyIdAndSortId(companyId,
						sortId, 0, 2);
		req.setAttribute("list", list);
		req.setAttribute("cmpProductSort", cmpProductSort);
		return this.getWebPath("admin/cmpproduct/sortpic.jsp");
	}

	/**
	 * 设置分类在首页的广告图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-17
	 */
	public String createsortpic(HkRequest req, HkResponse resp) {
		int sortId = req.getIntAndSetAttr("sortId");
		long companyId = req.getLong("companyId");
		long parentId = req.getLongAndSetAttr("parentId");
		List<CmpProductSortFile> list = this.cmpProductSortFileService
				.getCmpProductSortFileListByCompanyIdAndSortId(companyId,
						sortId, 0, 2);
		if (list.size() >= 2) {
			return "r:/epp/web/op/webadmin/cmpproduct_sortpic.do?companyId="
					+ companyId + "&sortId=" + sortId + "&parentId=" + parentId;
		}
		CmpProductSort cmpProductSort = this.cmpProductService
				.getCmpProductSort(sortId);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpProductSort", cmpProductSort);
			return this.getWebPath("admin/cmpproduct/createsortpic.jsp");
		}
		CmpProductSortFile cmpProductSortFile = new CmpProductSortFile();
		cmpProductSortFile.setName(req.getHtmlRow("name"));
		cmpProductSortFile.setCompanyId(companyId);
		cmpProductSortFile.setUrl(req.getHtmlRow("url"));
		cmpProductSortFile.setSortId(sortId);
		int code = cmpProductSortFile.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		try {
			if (cmpProductSortFile.getUrl().toLowerCase().startsWith("http://")) {
				cmpProductSortFile.setUrl(cmpProductSortFile.getUrl()
						.substring(7));
			}
			code = this.cmpProductProcessor.createCmpProductSortFile(
					cmpProductSortFile, req.getFile("f"));
			if (code != Err.SUCCESS) {
				if (code == Err.IMG_OUTOFSIZE_ERROR) {
					return this.onError(req, code, new Object[] { "100K" },
							"createerror", null);
				}
				return this.onError(req, code, "createerror", null);
			}
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, "createok", null);
		}
		catch (IOException e) {
			return this.onError(req, Err.IMG_UPLOAD_ERROR, "createerror", null);
		}
	}

	/**
	 * 设置分类在首页的广告图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-17
	 */
	public String updatesortpic(HkRequest req, HkResponse resp) {
		int sortId = req.getIntAndSetAttr("sortId");
		long oid = req.getLongAndSetAttr("oid");
		req.reSetAttribute("parentId");
		CmpProductSortFile cmpProductSortFile = this.cmpProductSortFileService
				.getCmpProductSortFile(oid);
		CmpProductSort cmpProductSort = this.cmpProductService
				.getCmpProductSort(sortId);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpProductSortFile", cmpProductSortFile);
			req.setAttribute("cmpProductSort", cmpProductSort);
			return this.getWebPath("admin/cmpproduct/updatesortpic.jsp");
		}
		cmpProductSortFile.setName(req.getHtmlRow("name"));
		cmpProductSortFile.setUrl(req.getHtmlRow("url"));
		int code = cmpProductSortFile.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		try {
			if (cmpProductSortFile.getUrl().toLowerCase().startsWith("http://")) {
				cmpProductSortFile.setUrl(cmpProductSortFile.getUrl()
						.substring(7));
			}
			code = this.cmpProductProcessor.updateCmpProductSortFile(
					cmpProductSortFile, req.getFile("f"));
			if (code != Err.SUCCESS) {
				if (code == Err.IMG_OUTOFSIZE_ERROR) {
					return this.onError(req, code, new Object[] { "100K" },
							"updateerror", null);
				}
				return this.onError(req, code, "updateerror", null);
			}
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, "updateok", null);
		}
		catch (IOException e) {
			return this.onError(req, Err.IMG_UPLOAD_ERROR, "updateerror", null);
		}
	}

	/**
	 * 删除分类在首页的广告图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-17
	 */
	public String delsortpic(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		long companyId = req.getLong("companyId");
		CmpProductSortFile file = this.cmpProductSortFileService
				.getCmpProductSortFile(oid);
		if (file != null && file.getCompanyId() == companyId) {
			this.cmpProductProcessor.deleteCmpProductSortFile(oid);
			this.setDelSuccessMsg(req);
		}
		return null;
	}
}