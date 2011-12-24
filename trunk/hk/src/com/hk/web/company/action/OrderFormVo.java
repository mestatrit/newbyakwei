package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;

import com.hk.bean.OrderForm;
import com.hk.web.util.IncorporatedOrder;

public class OrderFormVo {
	private OrderForm orderForm;

	/**
	 * 是否被选中
	 */
	private boolean checked;

	public OrderFormVo(OrderForm orderForm) {
		this.orderForm = orderForm;
	}

	public static List<OrderFormVo> createList(List<OrderForm> list,
			IncorporatedOrder incorporatedOrder) {
		List<OrderFormVo> volist = new ArrayList<OrderFormVo>();
		for (OrderForm orderForm : list) {
			OrderFormVo vo = new OrderFormVo(orderForm);
			if (incorporatedOrder.containOrderId(orderForm.getOid())) {
				vo.setChecked(true);
			}
			volist.add(vo);
		}
		return volist;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isChecked() {
		return checked;
	}

	public OrderForm getOrderForm() {
		return orderForm;
	}
}