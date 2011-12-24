package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpTable;
import com.hk.bean.OrderForm;
import com.hk.bean.OrderFormUserInfo;
import com.hk.bean.OrderItem;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.OrderFormService;

public class OrderFormServiceImpl implements OrderFormService {
	@Autowired
	private QueryManager manager;

	public void createOrderForm(OrderForm orderForm,
			List<OrderItem> orderItemList) {
		orderForm.setCreateTime(new Date());
		Query query = this.manager.createQuery();
		long oid = query.insertObject(orderForm).longValue();
		orderForm.setOid(oid);
		for (OrderItem item : orderItemList) {
			item.setOrderId(oid);
			this.createOrderItem(item);
		}
	}

	public void addOrderItem(OrderItem orderItem) {
		long oid = orderItem.getOrderId();
		OrderForm form = this.getOrderForm(oid);
		if (form == null) {
			return;
		}
		OrderItem o = this.getOrderItem(oid, orderItem.getProductId());
		if (o == null) {
			this.createOrderItem(orderItem);
		}
		else {
			o.setPcount(o.getPcount() + orderItem.getPcount());
			o.setUptime(new Date());
			this.updateOrderItem(o);
		}
		this.updateOrderFormData(oid);
	}

	private OrderItem getOrderItem(long oid, long productId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(OrderItem.class, "orderid=? and productid=?",
				new Object[] { oid, productId });
	}

	private void createOrderItem(OrderItem orderItem) {
		orderItem.setRebate(1);
		orderItem.setUptime(new Date());
		Query query = this.manager.createQuery();
		query.insertObject(orderItem);
	}

	public List<OrderForm> getOrderFormListByUserId(long userId, long oid,
			byte orderStatus, int begin, int size) {
		Query query = this.manager.createQuery();
		StringBuilder sql = new StringBuilder(
				"select * from orderform where userid=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(userId);
		if (oid > -1) {
			sql.append(" and oid=?");
			olist.add(oid);
		}
		if (orderStatus > -1) {
			sql.append(" and orderstatus=?");
			olist.add(orderStatus);
		}
		sql.append(" order by oid desc");
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				OrderForm.class, olist);
	}

	public int countOrderFormByUserId(long userId, long oid, byte orderStatus) {
		Query query = this.manager.createQuery();
		StringBuilder sql = new StringBuilder(
				"select count(*) from orderform where userid=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(userId);
		if (oid > -1) {
			sql.append(" and oid=?");
			olist.add(oid);
		}
		if (orderStatus > -1) {
			sql.append(" and orderstatus=?");
			olist.add(orderStatus);
		}
		return query.countBySql("ds1", sql.toString(), olist);
	}

	public List<OrderForm> getOrderFormListByCompanyId(long companyId,
			long oid, byte orderStatus, int begin, int size) {
		Query query = this.manager.createQuery();
		StringBuilder sql = new StringBuilder(
				"select * from orderform where companyid=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		if (oid > 0) {
			sql.append(" and oid=?");
			olist.add(oid);
		}
		if (orderStatus > -1) {
			sql.append(" and orderstatus=?");
			olist.add(orderStatus);
		}
		sql.append(" order by oid desc");
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				OrderForm.class, olist);
	}

	public int countOrderFormByCompanyId(long companyId, long oid,
			byte orderStatus) {
		Query query = this.manager.createQuery();
		StringBuilder sql = new StringBuilder(
				"select count(*) from orderform where companyid=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		if (oid > 0) {
			sql.append(" and oid=?");
			olist.add(oid);
		}
		if (orderStatus > -1) {
			sql.append(" and orderstatus=?");
			olist.add(orderStatus);
		}
		return query.countBySql("ds1", sql.toString(), olist);
	}

	public void updateOrderFormStatus(long oid, byte status) {
		Query query = this.manager.createQuery();
		query.addField("orderstatus", status);
		query.updateById(OrderForm.class, oid);
	}

	public void batchUpdateOrderFormStatus(long[] oid, byte status) {
		for (long l : oid) {
			this.updateOrderFormStatus(l, status);
		}
	}

	public OrderForm getOrderForm(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(OrderForm.class, oid);
	}

	public List<OrderFormUserInfo> getOrderFormUserInfoListByUserId(long userId) {
		Query query = this.manager.createQuery();
		return query.listEx(OrderFormUserInfo.class, "userid=?",
				new Object[] { userId }, "defflg desc,oid desc");
	}

	public boolean createOrderFormUserInfo(OrderFormUserInfo orderFormUserInfo) {
		Query query = this.manager.createQuery();
		if (DataUtil.isEmpty(orderFormUserInfo.getTitle())) {
			int count = this.countOrderFormUserInfoByUserId(orderFormUserInfo
					.getUserId());
			orderFormUserInfo.setTitle("我的联系方式" + (count + 1));
			if (count == 0) {
				orderFormUserInfo.setDefflg(OrderFormUserInfo.DEFFLG_MAIN);
			}
		}
		long oid = query.insertObject(orderFormUserInfo).longValue();
		orderFormUserInfo.setOid(oid);
		return true;
	}

	public void updateOrderFormUserInfo(OrderFormUserInfo orderFormUserInfo) {
		Query query = this.manager.createQuery();
		query.updateObject(orderFormUserInfo);
	}

	public int countOrderFormUserInfoByUserId(long userId) {
		Query query = this.manager.createQuery();
		return query.count(OrderFormUserInfo.class, "userid=?",
				new Object[] { userId });
	}

	public OrderFormUserInfo getOrderFormUserInfo(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(OrderFormUserInfo.class, oid);
	}

	public void setMainUserInfo(long oid) {
		Query query = this.manager.createQuery();
		query.addField("defflg", OrderFormUserInfo.DEFFLG_MAIN);
		query.updateById(OrderFormUserInfo.class, oid);
	}

	public void updateOrderItem(OrderItem orderItem) {
		Query query = this.manager.createQuery();
		query.updateObject(orderItem);
	}

	public OrderItem getOrderItem(long itemId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(OrderItem.class, itemId);
	}

	public List<OrderItem> getOrderItemListByOrderId(long orderId) {
		Query query = this.manager.createQuery();
		return query.listEx(OrderItem.class, "orderid=?",
				new Object[] { orderId }, "itemid desc");
	}

	public List<OrderItem> getOrderItemListByOrderIdForUpdate(long orderId) {
		Query query = this.manager.createQuery();
		return query.listEx(OrderItem.class, "orderid=?",
				new Object[] { orderId }, "uptime desc");
	}

	public void updateItemRebate(long itemId, double rebate) {
		Query query = this.manager.createQuery();
		query.addField("rebate", rebate);
		query.updateById(OrderItem.class, itemId);
	}

	public void updateOrderFormPrice(long oid, double price) {
		Query query = this.manager.createQuery();
		query.addField("price", price);
		query.updateById(OrderForm.class, oid);
	}

	public void deleteItem(long itemId) {
		OrderItem item = this.getOrderItem(itemId);
		if (item == null) {
			return;
		}
		Query query = this.manager.createQuery();
		query.deleteById(OrderItem.class, itemId);
		this.updateOrderFormData(item.getOrderId());
	}

	public void decreaseItem(long itemId) {
		OrderItem item = this.getOrderItem(itemId);
		if (item == null) {
			return;
		}
		if (item.getPcount() > 0) {
			item.setPcount(item.getPcount() - 1);
		}
	}

	public void decreaseItem(long itemId, int count) {
		OrderItem item = this.getOrderItem(itemId);
		if (item == null) {
			return;
		}
		item.setPcount(item.getPcount() - count);
		if (item.getPcount() > 0) {
			this.updateOrderItem(item);
		}
		else {
			this.deleteItem(itemId);
		}
		this.updateOrderFormData(item.getOrderId());
	}

	private void updateOrderFormData(long oid) {
		// 更新订单数据
		List<OrderItem> itemlist = this.getOrderItemListByOrderId(oid);
		double price = 0;
		int totalCount = 0;
		for (OrderItem item : itemlist) {
			price += item.getPrice() * item.getPcount();
			totalCount += item.getPcount();
		}
		Query query = this.manager.createQuery();
		query.addField("price", price);
		query.addField("totalcount", totalCount);
		query.updateById(OrderForm.class, oid);
	}

	public void updateOrderFormTableData(long oid, CmpTable cmpTable) {
		Query query = this.manager.createQuery();
		query.addField("tabledata", cmpTable.getTableId() + ";"
				+ cmpTable.getTableNum());
		query.updateById(OrderForm.class, oid);
	}

	public List<OrderForm> getOrderFormListInId(long companyId,
			Collection<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<OrderForm>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from orderform where companyid=? and oid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), OrderForm.class,
				companyId);
	}
}