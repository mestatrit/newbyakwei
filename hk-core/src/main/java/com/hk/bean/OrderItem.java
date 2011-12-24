package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 订单明细
 * 
 * @author akwei
 */
@Table(name = "orderitem", id = "itemid")
public class OrderItem {
	@Id
	private long itemId;

	@Column
	private long productId;

	@Column
	private String name;

	/**
	 * 数量
	 */
	@Column
	private int pcount;

	/**
	 * 下单时单价
	 */
	@Column
	private double price;

	@Column
	private double rebate;

	@Column
	private long orderId;

	@Column
	private Date uptime;

	private CmpProduct cmpProduct;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getPcount() {
		return pcount;
	}

	public void setPcount(int pcount) {
		this.pcount = pcount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getRebate() {
		return rebate;
	}

	public void setRebate(double rebate) {
		this.rebate = rebate;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public double getTotalPrice() {
		return this.price * this.pcount * this.rebate;
	}

	public CmpProduct getCmpProduct() {
		return cmpProduct;
	}

	public void setCmpProduct(CmpProduct cmpProduct) {
		this.cmpProduct = cmpProduct;
	}

	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
}