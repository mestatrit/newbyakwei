package com.hk.web.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.OrderFormUserInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.OrderFormService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/op/orderformuser")
public class OrderFormUserAction extends BaseAction {
	@Autowired
	private OrderFormService orderFormService;

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
	public String toupdatewap(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		OrderFormUserInfo o = (OrderFormUserInfo) req.getAttribute("o");
		if (o == null) {
			o = this.orderFormService.getOrderFormUserInfo(oid);
		}
		req.setAttribute("o", o);
		req.reSetAttribute("fromorderform");
		return this.getWapJsp("orderformuserinfo/update.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatewap(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		String name = req.getString("name");
		String email = req.getString("email");
		String tel = req.getString("tel");
		String mobile = req.getString("mobile");
		String title = req.getString("title");
		OrderFormUserInfo o = this.orderFormService.getOrderFormUserInfo(oid);
		o.setName(DataUtil.toHtmlRow(name));
		o.setEmail(DataUtil.toHtmlRow(email));
		o.setMobile(DataUtil.toHtmlRow(mobile));
		o.setTel(DataUtil.toHtmlRow(tel));
		o.setTitle(DataUtil.toHtmlRow(title));
		req.setAttribute("o", o);
		req.reSetAttribute("fromorderform");
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/op/orderformuser_toupdatewap.do";
		}
		this.orderFormService.updateOrderFormUserInfo(o);
		if (req.getInt("fromorderform") == 1) {
			return "r:/op/orderform_cfmwap.do";
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/op/orderformuser_wap.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setdef(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		OrderFormUserInfo info = this.orderFormService
				.getOrderFormUserInfo(oid);
		if (info != null
				&& info.getUserId() == this.getLoginUser(req).getUserId()) {
			this.orderFormService.setMainUserInfo(oid);
		}
		if (req.getInt("fromorderform") == 1) {
			return "r:/op/orderform_cfmwap.do";
		}
		return "r:/op/orderformuser_wap.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tocreatewap(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("fromorderform");
		return this.getWapJsp("orderformuserinfo/create.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createwap(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		String email = req.getString("email");
		String tel = req.getString("tel");
		String mobile = req.getString("mobile");
		String title = req.getString("title");
		OrderFormUserInfo o = new OrderFormUserInfo();
		o.setUserId(this.getLoginUser(req).getUserId());
		o.setName(DataUtil.toHtmlRow(name));
		o.setEmail(DataUtil.toHtmlRow(email));
		o.setMobile(DataUtil.toHtmlRow(mobile));
		o.setTel(DataUtil.toHtmlRow(tel));
		o.setTitle(DataUtil.toHtmlRow(title));
		req.setAttribute("o", o);
		req.reSetAttribute("fromorderform");
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/op/orderformuser_tocreatewap.do";
		}
		this.orderFormService.createOrderFormUserInfo(o);
		if (req.getInt("fromorderform") == 1) {
			this.orderFormService.setMainUserInfo(o.getOid());
			return "r:/op/orderform_cfmwap.do";
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/op/orderformuser_wap.do";
	}
}