package com.hk.svr;

import java.util.Collection;
import java.util.List;

import com.hk.bean.CmpTable;
import com.hk.bean.OrderForm;
import com.hk.bean.OrderFormUserInfo;
import com.hk.bean.OrderItem;

public interface OrderFormService {
	void createOrderForm(OrderForm orderForm, List<OrderItem> orderItemList);

	/**
	 * 更新订单状态
	 * 
	 * @param oid
	 * @param status
	 */
	void updateOrderFormStatus(long oid, byte status);

	/**
	 * 批量更新订单状态
	 * 
	 * @param oid
	 * @param status
	 */
	void batchUpdateOrderFormStatus(long[] oid, byte status);

	/**
	 * 用户查看订单
	 * 
	 * @param userId
	 * @param oid 订单号,为负数时忽略此条件
	 * @param orderStatus 为负数时，忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<OrderForm> getOrderFormListByUserId(long userId, long oid,
			byte orderStatus, int begin, int size);

	/**
	 * 用户查看订单
	 * 
	 * @param userId
	 * @param oid 为负数时忽略次提交键
	 * @param orderStatus 为负数时，忽略此条件
	 * @return
	 */
	int countOrderFormByUserId(long userId, long oid, byte orderStatus);

	/**
	 * @param oid 订单号， 为0时，忽略此条件
	 * @param orderStatus 订单状态，为负数时，忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<OrderForm> getOrderFormListByCompanyId(long companyId, long oid,
			byte orderStatus, int begin, int size);

	/**
	 * @param companyId
	 * @param oid 订单号， 为0时，忽略此条件
	 * @param orderStatus 订单状态，为负数时，忽略此条件
	 * @return
	 */
	int countOrderFormByCompanyId(long companyId, long oid, byte orderStatus);

	OrderForm getOrderForm(long oid);

	List<OrderFormUserInfo> getOrderFormUserInfoListByUserId(long userId);

	/**
	 * 最多只能保存5条个人信息
	 * 
	 * @param orderFormUserInfo
	 * @return true保存成功 ,false,超过5条，不能保存
	 */
	boolean createOrderFormUserInfo(OrderFormUserInfo orderFormUserInfo);

	void updateOrderFormUserInfo(OrderFormUserInfo orderFormUserInfo);

	OrderFormUserInfo getOrderFormUserInfo(long oid);

	void setMainUserInfo(long oid);

	void updateOrderItem(OrderItem orderItem);

	OrderItem getOrderItem(long itemId);

	List<OrderItem> getOrderItemListByOrderId(long orderId);

	List<OrderItem> getOrderItemListByOrderIdForUpdate(long orderId);

	void updateItemRebate(long itemId, double rebate);

	void updateOrderFormPrice(long oid, double price);

	void addOrderItem(OrderItem orderItem);

	void deleteItem(long itemId);

	/**
	 * 从订单中减少产品
	 * 
	 * @param itemId
	 * @param count 要减少的数量 为正数
	 */
	void decreaseItem(long itemId, int count);

	void updateOrderFormTableData(long oid, CmpTable cmpTable);

	List<OrderForm> getOrderFormListInId(long companyId, Collection<Long> idList);
}