package com.hk.web.user.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpOrderTable;
import com.hk.bean.CmpProduct;
import com.hk.bean.CmpTable;
import com.hk.bean.CmpTablePhoto;
import com.hk.bean.CmpTablePhotoSet;
import com.hk.bean.Company;
import com.hk.bean.OrderForm;
import com.hk.bean.OrderFormUserInfo;
import com.hk.bean.OrderItem;
import com.hk.bean.ShoppingProduct;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;
import com.hk.svr.CmpTableService;
import com.hk.svr.CompanyService;
import com.hk.svr.OrderFormService;
import com.hk.svr.pub.Err;
import com.hk.web.company.action.ProductVo;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HttpShoppingCard;

@Component("/op/orderform")
public class OrderFormAction extends BaseAction {
	@Autowired
	private OrderFormService orderFormService;

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private CmpTableService cmpTableService;

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String key = req.getString("key");
		long oid = -1;
		if (!DataUtil.isEmpty(key)) {
			oid = req.getLong("key", -1);
		}
		byte orderStatus = req.getByteAndSetAttr("orderStatus", (byte) -1);
		long userId = this.getLoginUser(req).getUserId();
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.orderFormService.countOrderFormByUserId(userId,
				oid, orderStatus));
		List<OrderForm> orderformlist = this.orderFormService
				.getOrderFormListByUserId(userId, oid, orderStatus, page
						.getBegin(), page.getSize());
		List<Long> idList = new ArrayList<Long>();
		for (OrderForm order : orderformlist) {
			idList.add(order.getCompanyId());
		}
		Map<Long, Company> map = this.companyService.getCompanyMapInId(idList);
		for (OrderForm order : orderformlist) {
			order.setCompany(map.get(order.getCompanyId()));
		}
		req.setAttribute("orderformlist", orderformlist);
		req.setEncodeAttribute("key", key);
		req.setAttribute("op_func", 12);
		req.setReturnUrl(ServletUtil.getReturnUrl(req));
		return this.getWeb3Jsp("orderform/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String wap(HkRequest req, HkResponse resp) throws Exception {
		String key = req.getString("key");
		long oid = -1;
		if (!DataUtil.isEmpty(key)) {
			oid = req.getLong("key", -1);
		}
		byte orderStatus = req.getByteAndSetAttr("orderStatus", (byte) -1);
		long userId = this.getLoginUser(req).getUserId();
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.orderFormService.countOrderFormByUserId(userId,
				oid, orderStatus));
		List<OrderForm> orderformlist = this.orderFormService
				.getOrderFormListByUserId(userId, oid, orderStatus, page
						.getBegin(), page.getSize());
		List<Long> idList = new ArrayList<Long>();
		for (OrderForm order : orderformlist) {
			idList.add(order.getCompanyId());
		}
		Map<Long, Company> map = this.companyService.getCompanyMapInId(idList);
		for (OrderForm order : orderformlist) {
			order.setCompany(map.get(order.getCompanyId()));
		}
		req.setAttribute("orderformlist", orderformlist);
		req.setEncodeAttribute("key", key);
		return this.getWapJsp("orderform/orderformlist.jsp");
	}

	/**
	 * 确认订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String cfm(HkRequest req, HkResponse resp) {
		long userId = this.getLoginUser(req).getUserId();
		HttpShoppingCard httpShoppingCard = this.getShoppingCard(req);
		Collection<ShoppingProduct> c = httpShoppingCard
				.getShoppingProductList();
		List<ProductVo> productvolist = ProductVo.createVoList(c);
		double totalPrice = 0;
		for (ProductVo vo : productvolist) {
			totalPrice += vo.getCmpProduct().getMoney() * vo.getCount();
		}
		req.setAttribute("totalPrice", totalPrice);
		req.setAttribute("productvolist", productvolist);
		List<OrderFormUserInfo> infolist = this.orderFormService
				.getOrderFormUserInfoListByUserId(userId);
		if (infolist.size() > 0) {
			req.setAttribute("maininfo", infolist.iterator().next());
		}
		req.setAttribute("infolist", infolist);
		this.loadTimeList(req);
		return this.getWeb3Jsp("orderform/cfm.jsp");
	}

	/**
	 * 确认订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String cfmwap(HkRequest req, HkResponse resp) {
		long userId = this.getLoginUser(req).getUserId();
		HttpShoppingCard httpShoppingCard = this.getShoppingCard(req);
		Collection<ShoppingProduct> c = httpShoppingCard
				.getShoppingProductList();
		List<ProductVo> productvolist = ProductVo.createVoList(c);
		double totalPrice = 0;
		for (ProductVo vo : productvolist) {
			totalPrice += vo.getCmpProduct().getMoney() * vo.getCount();
		}
		req.setAttribute("totalPrice", totalPrice);
		req.setAttribute("productvolist", productvolist);
		List<OrderFormUserInfo> infolist = this.orderFormService
				.getOrderFormUserInfoListByUserId(userId);
		if (infolist.size() > 0) {
			req.setAttribute("maininfo", infolist.iterator().next());
		}
		req.setAttribute("infolist", infolist);
		this.loadTimeList(req);
		return this.getWapJsp("orderform/cfm.jsp");
	}

	/**
	 * 加载联系信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String loaduserinfo2(HkRequest req, HkResponse resp) {
		long userId = this.getLoginUser(req).getUserId();
		long oid = req.getLong("oid");
		OrderFormUserInfo info = this.orderFormService
				.getOrderFormUserInfo(oid);
		if (info == null || info.getUserId() != userId) {
			return null;
		}
		req.setAttribute("info", info);
		return this.getWeb3Jsp("orderform/edituserinfo_inc2.jsp");
	}

	/**
	 * 加载联系信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String loaduserinfo(HkRequest req, HkResponse resp) {
		long userId = this.getLoginUser(req).getUserId();
		int needmain = req.getInt("needmain");
		List<OrderFormUserInfo> infolist = this.orderFormService
				.getOrderFormUserInfoListByUserId(userId);
		req.setAttribute("infolist", infolist);
		if (needmain == 1 && infolist.size() > 0) {
			req.setAttribute("maininfo", infolist.iterator().next());
		}
		return this.getWeb3Jsp("orderform/edituserinfo_inc.jsp");
	}

	/**
	 * 加载联系信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String loadmainuserinfo(HkRequest req, HkResponse resp) {
		long userId = this.getLoginUser(req).getUserId();
		List<OrderFormUserInfo> infolist = this.orderFormService
				.getOrderFormUserInfoListByUserId(userId);
		if (infolist.size() > 0) {
			req.setAttribute("info", infolist.iterator().next());
		}
		return this.getWeb3Jsp("orderform/userinfo_inc.jsp");
	}

	/**
	 * 确认订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String createuserinfo(HkRequest req, HkResponse resp) {
		long userId = this.getLoginUser(req).getUserId();
		String name = req.getString("name");
		String mobile = req.getString("mobile");
		String tel = req.getString("tel");
		String email = req.getString("email");
		OrderFormUserInfo info = new OrderFormUserInfo();
		info.setUserId(userId);
		info.setName(DataUtil.toHtmlRow(name));
		info.setMobile(DataUtil.toHtmlRow(mobile));
		info.setTel(DataUtil.toHtmlRow(tel));
		info.setEmail(DataUtil.toHtmlRow(email));
		int code = info.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, -1, null, "userinfo",
					"onuserinfoerror", null);
		}
		this.orderFormService.createOrderFormUserInfo(info);
		req.setAttribute("info", info);
		req.setAttribute("oid", info.getOid());
		return this.getWeb3Jsp("orderform/userinfo_inc.jsp");
	}

	/**
	 * 确认订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String updateuserinfo(HkRequest req, HkResponse resp) {
		long userId = this.getLoginUser(req).getUserId();
		long oid = req.getLong("oid");
		String name = req.getString("name");
		String mobile = req.getString("mobile");
		OrderFormUserInfo info = this.orderFormService
				.getOrderFormUserInfo(oid);
		info.setName(DataUtil.toHtmlRow(name));
		info.setMobile(DataUtil.toHtmlRow(mobile));
		int code = info.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, -1, null, "userinfo",
					"onuserinfoerror", null);
		}
		if (userId == info.getUserId()) {
			this.orderFormService.updateOrderFormUserInfo(info);
		}
		req.setAttribute("info", info);
		req.setAttribute("oid", oid);
		return this.getWeb3Jsp("orderform/userinfo_inc.jsp");
	}

	/**
	 * 创建订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String create(HkRequest req, HkResponse resp) {
		long userId = this.getLoginUser(req).getUserId();
		long oid = req.getLong("info_oid");
		if (oid <= 0) {
			return "r:/op/orderform_cfm.do";
		}
		HttpShoppingCard card = this.getShoppingCard(req);
		Collection<ShoppingProduct> list = card.getShoppingProductList();
		List<Long> idList = new ArrayList<Long>();
		for (ShoppingProduct o : list) {
			idList.add(o.getProductId());
		}
		Map<Long, CmpProduct> map = this.cmpProductService
				.getCmpProductMapInId(idList);
		List<OrderItem> itemList = new ArrayList<OrderItem>();
		List<Long> pidIdList = new ArrayList<Long>();
		for (ShoppingProduct o : list) {
			OrderItem item = new OrderItem();
			CmpProduct product = map.get(o.getProductId());
			item.setName(product.getName());
			item.setPcount(o.getCount());
			item.setPrice(product.getMoney());
			item.setProductId(product.getProductId());
			itemList.add(item);
			pidIdList.add(o.getProductId());
		}
		OrderForm orderForm = OrderForm.getInstanceForCreate(userId, itemList);
		OrderFormUserInfo info = this.orderFormService
				.getOrderFormUserInfo(oid);
		if (info == null) {
			// return "r:/op/orderform_cfm.do";
			return null;
		}
		String content = req.getString("content");
		byte optflg = 1;
		String date = req.getString("date");
		String time = req.getString("time1");
		String time2 = req.getString("time2");
		SimpleDateFormat sdf = null;
		if (!DataUtil.isEmpty(time2)) {
			time = time2;
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
		else {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
		}
		try {
			Date orderTime = sdf.parse(date + " " + time);
			orderForm.setOrderTime(orderTime);
		}
		catch (ParseException e) {
			return this.initError(req, Err.ORDERFORM_ORDERTIME_ERROR, -1, null,
					"order", "oncreateordererror", null);
		}
		orderForm.setMobile(DataUtil.toHtmlRow(info.getMobile()));
		orderForm.setName(info.getName());
		orderForm.setCompanyId(card.getCompanyId());
		orderForm.setOptflg(optflg);
		orderForm.setContent(DataUtil.toHtmlRow(content));
		int code = orderForm.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, -1, null, "order",
					"oncreateordererror", null);
		}
		this.orderFormService.setMainUserInfo(oid);
		this.orderFormService.createOrderForm(orderForm, itemList);
		// 添加用户买过的产品
		this.cmpProductService.createUserProduct(userId, pidIdList, card
				.getCompanyId());
		long companyId = card.getCompanyId();
		card.clean();
		card.saveShoppingCard(resp);
		return this.initSuccess(req, "order", "oncreateordersuccess", orderForm
				.getOid()
				+ ":" + companyId);
		// return "r:/op/orderform_createok.do?oid=" + orderForm.getOid()
		// + "&companyId=" + companyId;
	}

	/**
	 * 创建订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String createwap(HkRequest req, HkResponse resp) {
		long userId = this.getLoginUser(req).getUserId();
		long oid = req.getLong("info_oid");
		if (oid <= 0) {
			return "r:/op/orderform_cfmwap.do";
		}
		HttpShoppingCard card = this.getShoppingCard(req);
		Collection<ShoppingProduct> list = card.getShoppingProductList();
		List<Long> idList = new ArrayList<Long>();
		for (ShoppingProduct o : list) {
			idList.add(o.getProductId());
		}
		Map<Long, CmpProduct> map = this.cmpProductService
				.getCmpProductMapInId(idList);
		List<OrderItem> itemList = new ArrayList<OrderItem>();
		List<Long> pidIdList = new ArrayList<Long>();
		for (ShoppingProduct o : list) {
			OrderItem item = new OrderItem();
			CmpProduct product = map.get(o.getProductId());
			item.setName(product.getName());
			item.setPcount(o.getCount());
			item.setPrice(product.getMoney());
			item.setProductId(product.getProductId());
			itemList.add(item);
			pidIdList.add(o.getProductId());
		}
		OrderForm orderForm = OrderForm.getInstanceForCreate(userId, itemList);
		OrderFormUserInfo info = this.orderFormService
				.getOrderFormUserInfo(oid);
		if (info == null) {
			req.setSessionText("func.input_orderformuserinfo");
			return "r:/op/orderform_cfmwap.do";
		}
		String content = req.getString("content");
		byte optflg = 1;
		String date = req.getString("date");
		String time = req.getString("time");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date orderTime = sdf.parse(date + " " + time);
			orderForm.setOrderTime(orderTime);
		}
		catch (ParseException e) {
			req.setText(String.valueOf(Err.ORDERFORM_ORDERTIME_ERROR));
			return "/op/orderform_cfmwap.do";
		}
		orderForm.setMobile(info.getMobile());
		orderForm.setName(info.getName());
		orderForm.setCompanyId(card.getCompanyId());
		orderForm.setOptflg(optflg);
		orderForm.setContent(DataUtil.toHtmlRow(content));
		int code = orderForm.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/op/orderform_cfmwap.do";
		}
		this.orderFormService.setMainUserInfo(oid);
		this.orderFormService.createOrderForm(orderForm, itemList);
		// 添加用户买过的产品
		this.cmpProductService.createUserProduct(userId, pidIdList, card
				.getCompanyId());
		long companyId = card.getCompanyId();
		card.clean();
		card.saveShoppingCard(resp);
		return "r:/op/orderform_createokwap.do?oid=" + orderForm.getOid()
				+ "&companyId=" + companyId;
	}

	/**
	 * 订单生成成功
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String createok(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		OrderForm orderForm = this.orderFormService.getOrderForm(oid);
		if (orderForm == null) {
			return this.getNotFoundForward(resp);
		}
		Company company = this.companyService.getCompany(orderForm
				.getCompanyId());
		req.setAttribute("company", company);
		req.setAttribute("orderForm", orderForm);
		req.reSetAttribute("companyId");
		return this.getWeb3Jsp("/orderform/createok.jsp");
	}

	/**
	 * 订单生成成功
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String createokwap(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		OrderForm orderForm = this.orderFormService.getOrderForm(oid);
		if (orderForm == null) {
			return this.getNotFoundForward(resp);
		}
		Company company = this.companyService.getCompany(orderForm
				.getCompanyId());
		req.setAttribute("company", company);
		req.setAttribute("orderForm", orderForm);
		req.reSetAttribute("companyId");
		return this.getWapJsp("/orderform/createok.jsp");
	}

	/**
	 * 取消订单，如果订单已经审核通过，用户不能直接取消，状态改为请求取消<br/>
	 * 如果未审核，用户可以直接取消订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String cancel(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		OrderForm orderForm = this.orderFormService.getOrderForm(oid);
		if (orderForm == null) {
			return null;
		}
		if (orderForm.getUserId() == this.getLoginUser(req).getUserId()) {
			if (orderForm.isUnChecked()) {
				this.orderFormService.updateOrderFormStatus(oid,
						OrderForm.ORDERSTATUS_CANCEL);
				resp.sendHtml(1);
			}
			else {
				this.orderFormService.updateOrderFormStatus(oid,
						OrderForm.ORDERSTATUS_NEEDCANCEL);
				resp.sendHtml(2);
			}
		}
		return null;
	}

	/**
	 * 取消订单，如果订单已经审核通过，用户不能直接取消，状态改为请求取消<br/>
	 * 如果未审核，用户可以直接取消订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String cancelwap(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		OrderForm orderForm = this.orderFormService.getOrderForm(oid);
		if (orderForm == null) {
			return null;
		}
		if (orderForm.getUserId() == this.getLoginUser(req).getUserId()) {
			if (orderForm.isUnChecked()) {
				this.orderFormService.updateOrderFormStatus(oid,
						OrderForm.ORDERSTATUS_CANCEL);
				req.setSessionText("func.orderform.cancelok");
			}
			else {
				this.orderFormService.updateOrderFormStatus(oid,
						OrderForm.ORDERSTATUS_NEEDCANCEL);
				req.setSessionText("func.orderform.cancelok");
			}
		}
		return "r:/op/orderform_viewwap.do?oid=" + oid;
	}

	/**
	 * 用户选择台面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String tablelist(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		OrderForm orderForm = this.orderFormService.getOrderForm(oid);
		if (orderForm == null) {
			return this.getNotFoundForward(resp);
		}
		Company company = this.companyService.getCompany(orderForm
				.getCompanyId());
		req.setAttribute("company", company);
		this.loadTableListData(req);
		long companyId = req.getLongAndSetAttr("companyId");
		int num = req.getIntAndSetAttr("num");
		long sortId = req.getLongAndSetAttr("sortId");
		List<CmpTable> list = this.cmpTableService
				.getCmpTableByCompanyIdAndBestPersonNum(companyId, sortId, num);
		req.setAttribute("list", list);
		String return_url = req.getRequestURL().append("?").append(
				req.getQueryString()).toString();
		req.setReturnUrl(return_url);
		return this.getWeb3Jsp("orderform/tablelist.jsp");
	}

	/**
	 * 台面单页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String table(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long tableId = req.getLongAndSetAttr("tableId");
		req.reSetAttribute("oid");
		CmpTable cmpTable = this.cmpTableService.getCmpTable(tableId);
		if (cmpTable == null) {
			return this.getNotFoundForward(resp);
		}
		if (cmpTable.getCompanyId() != companyId) {
			return this.getNotFoundForward(resp);
		}
		req.setAttribute("cmpTable", cmpTable);
		if (cmpTable.getSetId() > 0) {
			CmpTablePhotoSet cmpTablePhotoSet = this.cmpTableService
					.getCmpTablePhotoSet(cmpTable.getSetId());
			req.setAttribute("cmpTablePhotoSet", cmpTablePhotoSet);
			List<CmpTablePhoto> photolilst = this.cmpTableService
					.getCmpTablePhotoListBySetId(cmpTable.getSetId());
			req.setAttribute("photolist", photolilst);
		}
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		this.loadTableListData(req);
		return this.getWeb3Jsp("orderform/table.jsp");
	}

	/**
	 * 订单页
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
		req.setAttribute("order", order);
		req.setAttribute("itemlist", itemlist);
		return this.getWeb3Jsp("orderform/orderform.jsp");
	}

	/**
	 * 订单页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String viewwap(HkRequest req, HkResponse resp) {
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
		req.setAttribute("order", order);
		req.setAttribute("itemlist", itemlist);
		return this.getWapJsp("orderform/orderform.jsp");
	}

	/**
	 * 台面单页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String ordertable(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long tableId = req.getLong("tableId");
		long oid = req.getLong("oid");
		int personNum = req.getInt("num");
		CmpTable cmpTable = this.cmpTableService.getCmpTable(tableId);
		OrderForm orderForm = this.orderFormService.getOrderForm(oid);
		if (orderForm == null) {
			return null;
		}
		if (orderForm.isCheckOk() || orderForm.isCheckoutOk()) {
			return null;
		}
		if (cmpTable.getCompanyId() != companyId) {
			return null;
		}
		if (!cmpTable.isCanNetBooked()) {
			return null;
		}
		CmpOrderTable cmpOrderTable = new CmpOrderTable();
		cmpOrderTable.setTableId(tableId);
		cmpOrderTable.setObjstatus(CmpOrderTable.OBJSTATUS_BOOKED);
		cmpOrderTable.setCompanyId(companyId);
		cmpOrderTable.setName(orderForm.getName());
		cmpOrderTable.setPersonNum(personNum);
		if (orderForm.getMobile() != null) {
			cmpOrderTable.setTel(orderForm.getMobile());
		}
		else {
			cmpOrderTable.setTel(orderForm.getTel());
		}
		cmpOrderTable.setBeginTime(orderForm.getOrderTime());
		cmpOrderTable.setObjstatus(CmpOrderTable.OBJSTATUS_BOOKED);
		String time = req.getString("time");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = sdf.format(orderForm.getOrderTime());
		sdf.applyPattern("yyyy-MM-dd HH:mm");
		try {
			Date endTime = sdf.parse(datestr + " " + time);
			cmpOrderTable.setEndTime(endTime);
		}
		catch (ParseException e) {// 解析失败
			return this.onError(req, Err.TIME_ERROR, "onordertableerror", null);
		}
		int code = cmpOrderTable.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "onordertableerror", null);
		}
		this.cmpTableService.createCmpOrderTable(cmpOrderTable);
		this.orderFormService.updateOrderFormTableData(oid, cmpTable);
		req.setSessionText("func.ordertable.ok");
		return this.onSuccess2(req, "onordertablesuccess", null);
	}

	private void loadTimeList(HkRequest req) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		int date = c.get(Calendar.DATE);
		List<String> timelist = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
		while (date == c.get(Calendar.DATE)) {
			timelist.add(sdf.format(c.getTime()));
			c.add(Calendar.MINUTE, 30);
		}
		req.setAttribute("timelist", timelist);
	}
}