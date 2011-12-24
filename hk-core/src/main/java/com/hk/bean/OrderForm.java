package com.hk.bean;

import java.util.Date;
import java.util.List;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * @author akwei
 */
@Table(name = "orderform", id = "oid")
public class OrderForm {
	public static final byte ORDERSTATUS_UNCHECK = 0;// 待审核

	public static final byte ORDERSTATUS_CHECKEOK = 1;// 已下单

	public static final byte ORDERSTATUS_CHECKOUTOK = 2;// 已结算

	public static final byte ORDERSTATUS_CANCEL = 3;// 已取消

	public static final byte ORDERSTATUS_NEEDCANCEL = 4;// 请求取消取消

	public static final byte OPTFLG_MEALINCMP = 1;// 餐厅就餐

	public static final byte OPTFLG_TAKEAWAY = 2;// 外卖送达

	@Id
	private long oid;

	@Column
	private long userId;

	@Column
	private String name;

	@Column
	private Date orderTime;

	/**
	 * 付款方式
	 */
	@Column
	private byte checkoutflg;

	@Column
	private Date createTime;

	/**
	 * 订单状态
	 */
	@Column
	private byte orderStatus;

	/**
	 * 总价
	 */
	@Column
	private double price;

	@Column
	private long companyId;

	@Column
	private String mobile;

	@Column
	private String content;

	@Column
	private byte optflg;// 订单享用方式

	/**
	 * 产品总数量
	 */
	@Column
	private int totalCount;

	private Company company;

	@Column
	private String tel;

	@Column
	private String tabledata;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static OrderForm getInstanceForCreate(long userId,
			List<OrderItem> list) {
		double price = 0;
		int totalCount = 0;
		for (OrderItem item : list) {
			price += item.getPcount() * item.getPrice();
			totalCount += item.getPcount();
		}
		OrderForm orderForm = new OrderForm();
		orderForm.setUserId(userId);
		orderForm.setOrderStatus(OrderForm.ORDERSTATUS_UNCHECK);
		orderForm.setPrice(price);
		orderForm.setTotalCount(totalCount);
		return orderForm;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(byte orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public byte getCheckoutflg() {
		return checkoutflg;
	}

	public void setCheckoutflg(byte checkoutflg) {
		this.checkoutflg = checkoutflg;
	}

	public boolean isCanceled() {
		if (this.orderStatus == ORDERSTATUS_CANCEL) {
			return true;
		}
		return false;
	}

	public boolean isNeedCancel() {
		if (this.orderStatus == ORDERSTATUS_NEEDCANCEL) {
			return true;
		}
		return false;
	}

	public boolean isUnChecked() {
		if (this.orderStatus == ORDERSTATUS_UNCHECK) {
			return true;
		}
		return false;
	}

	public boolean isCheckOk() {
		if (this.orderStatus == ORDERSTATUS_CHECKEOK) {
			return true;
		}
		return false;
	}

	public boolean isCheckoutOk() {
		if (this.orderStatus == ORDERSTATUS_CHECKOUTOK) {
			return true;
		}
		return false;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte getOptflg() {
		return optflg;
	}

	public void setOptflg(byte optflg) {
		this.optflg = optflg;
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.content);
		if (s != null && s.length() > 150) {
			return Err.ORDERFORM_ORDERTIME_ERROR;
		}
		return Err.SUCCESS;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTabledata() {
		return tabledata;
	}

	public void setTabledata(String tabledata) {
		this.tabledata = tabledata;
	}

	public long getTableId() {
		if (DataUtil.isEmpty(this.tabledata)) {
			return 0;
		}
		String[] s = this.tabledata.split(";");
		return Long.parseLong(s[0]);
	}

	public String getTableNum() {
		if (DataUtil.isEmpty(this.tabledata)) {
			return null;
		}
		String[] s = this.tabledata.split(";");
		if (s.length > 1) {
			return s[1];
		}
		return null;
	}
}