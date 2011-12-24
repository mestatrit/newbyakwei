package com.hk.web.company.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductSort;
import com.hk.bean.Company;
import com.hk.bean.OrderForm;
import com.hk.bean.OrderItem;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;
import com.hk.svr.CompanyService;
import com.hk.svr.OrderFormService;
import com.hk.web.company.action.OrderFormVo;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.IncorporatedOrder;

@Component("/e/op/orderform")
public class OpOrderFormAction extends BaseAction {

	@Autowired
	private OrderFormService orderFormService;

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		long companyId = req.getLongAndSetAttr("companyId");
		byte orderStatus = req.getByteAndSetAttr("orderStatus", (byte) -1);
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.orderFormService.countOrderFormByCompanyId(
				companyId, oid, orderStatus));
		List<OrderForm> orderformlist = this.orderFormService
				.getOrderFormListByCompanyId(companyId, oid, orderStatus, page
						.getBegin(), page.getSize());
		List<Long> idList = new ArrayList<Long>();
		for (OrderForm form : orderformlist) {
			idList.add(form.getUserId());
		}
		req.setAttribute("orderStatus", orderStatus);
		if (oid > 0) {
			req.setAttribute("oid", oid);
		}
		List<OrderFormVo> orderformvolist = OrderFormVo.createList(
				orderformlist, new IncorporatedOrder(req, companyId));
		req.setAttribute("orderformvolist", orderformvolist);
		req.setAttribute("op_func", 11);
		String return_url = req.getRequestURL().append("?").append(
				req.getQueryString()).toString();
		req.setReturnUrl(return_url);
		return this.getWeb3Jsp("e/orderform/orderformlist.jsp");
	}

	/**
	 * 订单审核通过
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String checkok(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		this.orderFormService.updateOrderFormStatus(oid,
				OrderForm.ORDERSTATUS_CHECKEOK);
		return null;
	}

	/**
	 * 订单审核通过
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String checkoutok(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		this.orderFormService.updateOrderFormStatus(oid,
				OrderForm.ORDERSTATUS_CHECKOUTOK);
		return null;
	}

	/**
	 * 订单审核通过
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String checkoutok2(HkRequest req, HkResponse resp) {
		long[] oid = req.getLongs("oid");
		if (oid != null) {
			this.orderFormService.batchUpdateOrderFormStatus(oid,
					OrderForm.ORDERSTATUS_CHECKOUTOK);
			req
					.setSessionText("view.company.orderform.accountorderscheckoutok");
		}
		return this.onSuccess2(req, "onaccountordersuccess", null);
	}

	/**
	 * 订单未通过审核
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String uncheck(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		this.orderFormService.updateOrderFormStatus(oid,
				OrderForm.ORDERSTATUS_UNCHECK);
		return null;
	}

	/**
	 * 取消订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String cancel(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		this.orderFormService.updateOrderFormStatus(oid,
				OrderForm.ORDERSTATUS_CANCEL);
		return null;
	}

	/**
	 * 查看订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String view(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		OrderForm order = this.orderFormService.getOrderForm(oid);
		List<OrderItem> itemlist = this.orderFormService
				.getOrderItemListByOrderId(oid);
		List<Long> idList = new ArrayList<Long>();
		for (OrderItem item : itemlist) {
			idList.add(item.getProductId());
		}
		Map<Long, CmpProduct> map = this.cmpProductService
				.getCmpProductMapInId(idList);
		for (OrderItem item : itemlist) {
			item.setCmpProduct(map.get(item.getProductId()));
		}
		req.setAttribute("itemlist", itemlist);
		req.setAttribute("order", order);
		req.reSetAttribute("companyId");
		double rebate = req.getDouble("rebate");
		if (rebate == 0) {
			rebate = 100;
		}
		req.setAttribute("rebate", rebate);
		return this.getWeb3Jsp("e/orderform/orderform.jsp");
	}

	/**
	 * 更新折扣相关信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String updateitemrebate(HkRequest req, HkResponse resp) {
		long itemId = req.getLong("itemId");
		double rebate = req.getDouble("rebate") / 100;
		this.orderFormService.updateItemRebate(itemId, rebate);
		OrderItem item = this.orderFormService.getOrderItem(itemId);
		long oid = item.getOrderId();
		double product_totalprice = item.getTotalPrice();
		List<OrderItem> itemlist = this.orderFormService
				.getOrderItemListByOrderId(oid);
		double price = 0;
		for (OrderItem o : itemlist) {
			price += o.getTotalPrice();
		}
		this.orderFormService.updateOrderFormPrice(oid, price);
		resp.sendHtml(product_totalprice + ";" + price);
		return null;
	}

	/**
	 * 更新折扣相关信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String updaterebate(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		double rebate = req.getDouble("rebate") / 100;
		List<OrderItem> itemlist = this.orderFormService
				.getOrderItemListByOrderId(oid);
		StringBuilder sb = new StringBuilder();
		for (OrderItem o : itemlist) {
			o.setRebate(rebate);
			this.orderFormService.updateItemRebate(o.getItemId(), rebate);
			sb.append(o.getTotalPrice());
			sb.append(":");
		}
		sb.deleteCharAt(sb.length() - 1);
		double price = 0;
		for (OrderItem o : itemlist) {
			price += o.getTotalPrice();
		}
		this.orderFormService.updateOrderFormPrice(oid, price);
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "orderform", "onupdaterebatesuccess",
				rebate * 100);
	}

	/**
	 * 产品列表 足迹》产品》分类》列表，左边放置产品分类，需要放入request中的数据companyKind parentKind
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String productlist(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		int sortId = req.getIntAndSetAttr("sortId");
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpProductService.countCmpProduct(companyId,
				sortId, 0));
		List<CmpProduct> productlist = this.cmpProductService
				.getCmpProductList(companyId, sortId, 0, page.getBegin(), page
						.getSize());
		req.setAttribute("productlist", productlist);
		Company company = this.companyService.getCompany(companyId);
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
		req.reSetAttribute("oid");
		return this.getWeb3Jsp("e/orderform/productlist2.jsp");
	}

	/**
	 * 产品列表 足迹》产品》分类》列表，左边放置产品分类，需要放入request中的数据companyKind parentKind
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String findproduct(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		String key = req.getString("key");
		int st = req.getIntAndSetAttr("st");
		int ajax = req.getInt("ajax");
		List<CmpProduct> list = null;
		if (st == Company.PSEARCHTYPE_NAME) {
			list = this.cmpProductService
					.getCmpProductListByCompanyIdAndNameForSell(companyId, key,
							0, 10);
		}
		else if (st == Company.PSEARCHTYPE_PNUM) {
			list = this.cmpProductService
					.getCmpProductListByCompanyIdAndPnumForSell(companyId, key,
							0, 10);
		}
		else {
			list = this.cmpProductService
					.getCmpProductListByCompanyIdAndShortNameForSell(companyId,
							key, 0, 10);
		}
		req.setAttribute("list", list);
		if (ajax == 1) {
			return this.getWeb3Jsp("e/orderform/product_data.jsp");
		}
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		// 分类列表
		List<CmpProductSort> cmpproductsortlist = this.cmpProductService
				.getCmpProductSortList(companyId);
		req.setAttribute("cmpproductsortlist", cmpproductsortlist);
		req.reSetAttribute("oid");
		return this.getWeb3Jsp("e/orderform/productlist.jsp");
	}

	/**
	 * 加菜处理
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String additem(HkRequest req, HkResponse resp) throws Exception {
		// long companyId = req.getLong("companyId");
		int pid = req.getInt("pid");
		long oid = req.getLong("oid");
		CmpProduct product = this.cmpProductService.getCmpProduct(pid);
		OrderItem item = new OrderItem();
		item.setProductId(pid);
		item.setName(product.getName());
		item.setPcount(1);
		item.setOrderId(oid);
		item.setPrice(product.getMoney());
		this.orderFormService.addOrderItem(item);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loaditemlist(HkRequest req, HkResponse resp) throws Exception {
		// long companyId = req.getLong("companyId");
		long oid = req.getLong("oid");
		List<OrderItem> itemlist = this.orderFormService
				.getOrderItemListByOrderIdForUpdate(oid);
		req.setAttribute("itemlist", itemlist);
		return this.getWeb3Jsp("e/orderform/item_data.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String help(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		// 分类列表
		List<CmpProductSort> cmpproductsortlist = this.cmpProductService
				.getCmpProductSortList(companyId);
		req.setAttribute("cmpproductsortlist", cmpproductsortlist);
		req.reSetAttribute("oid");
		return this.getWeb3Jsp("e/orderform/help.jsp");
	}

	/**
	 * 从订单中删除orderItem
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delitem(HkRequest req, HkResponse resp) throws Exception {
		long itemId = req.getLong("itemId");
		this.orderFormService.deleteItem(itemId);
		return null;
	}

	/**
	 * 从订单中减少orderItem
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String decreaseitem(HkRequest req, HkResponse resp) throws Exception {
		long itemId = req.getLong("itemId");
		this.orderFormService.decreaseItem(itemId, 1);
		OrderItem item = this.orderFormService.getOrderItem(itemId);
		if (item != null) {
			resp.sendHtml(item.getPcount());
		}
		else {
			resp.sendHtml(0);
		}
		return null;
	}

	/**
	 * 添加订单到合并订单数据中
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addorderformerge(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLong("companyId");
		long orderId = req.getLong("oid");
		IncorporatedOrder incorporatedOrder = new IncorporatedOrder(req,
				companyId);
		incorporatedOrder.addOrderId(orderId);
		incorporatedOrder.save(resp);
		resp.sendHtml("1");
		return null;
	}

	/**
	 * 从合并订单数据中移除订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delorderformerge(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLong("companyId");
		long orderId = req.getLong("oid");
		IncorporatedOrder incorporatedOrder = new IncorporatedOrder(req,
				companyId);
		incorporatedOrder.removeOrderId(orderId);
		incorporatedOrder.save(resp);
		resp.sendHtml("1");
		return null;
	}

	/**
	 * 从合并订单数据中移除订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String clearmerge(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		IncorporatedOrder incorporatedOrder = new IncorporatedOrder(req,
				companyId);
		incorporatedOrder.removeAllOrderId();
		incorporatedOrder.save(resp);
		return null;
	}

	/**
	 * 从合并订单数据中移除订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String removefromaccountorderlist(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLong("companyId");
		long orderId = req.getLong("oid");
		IncorporatedOrder incorporatedOrder = new IncorporatedOrder(req,
				companyId);
		incorporatedOrder.removeOrderId(orderId);
		incorporatedOrder.save(resp);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 *结算合并的订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toaccountorders(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		IncorporatedOrder incorporatedOrder = new IncorporatedOrder(req,
				companyId);
		List<OrderForm> orderformlist = this.orderFormService
				.getOrderFormListInId(companyId, incorporatedOrder
						.getOrderIdSet());
		double totalResult = 0;
		for (OrderForm orderForm : orderformlist) {
			totalResult += orderForm.getPrice();
		}
		req.setAttribute("totalResult", totalResult);
		req.setAttribute("orderformlist", orderformlist);
		req.setReturnUrl(ServletUtil.getReturnUrl(req));
		return this.getWeb3Jsp("e/orderform/accountorders.jsp");
	}
}