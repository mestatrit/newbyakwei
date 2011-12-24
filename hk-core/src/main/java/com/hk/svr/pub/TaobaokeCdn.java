package com.hk.svr.pub;

public class TaobaokeCdn {

	/**
	 * 商品标题中包含的关键字. 注意:查询时keyword,cid至少选择其中一个参数
	 */
	private String keyword;

	/**
	 * 商品所属分类id
	 */
	private String cid;

	/**
	 * 起始价格.传入价格参数时,需注意起始价格和最高价格必须一起传入,并且 start_price <= end_price
	 */
	private double start_price;

	/**
	 * 最高价格
	 */
	private double end_price;

	/**
	 * 是否自动发货
	 */
	private Boolean auto_send;

	/**
	 * 商品所在地
	 */
	private String area;

	/**
	 * 卖家信用: 1heart(一心) 2heart (两心) 3heart(三心) 4heart(四心) 5heart(五心)
	 * 1diamond(一钻) 2diamond(两钻) 3diamond(三钻) 4diamond(四钻) 5diamond(五钻)
	 * 1crown(一冠) 2crown(两冠) 3crown(三冠) 4crown(四冠) 5crown(五冠) 1goldencrown(一黄冠)
	 * 2goldencrown(二黄冠) 3goldencrown(三黄冠) 4goldencrown(四黄冠) 5goldencrown(五黄冠)
	 */
	private String start_credit;

	/**
	 * 可选值和start_credit一样.start_credit的值一定要小于end_credit的值.
	 */
	private String end_credit;

	/**
	 * 默认排序:default price_desc(价格从高到低) price_asc(价格从低到高) credit_desc(信用等级从高到低)
	 * commissionRate_desc(佣金比率从高到底) commissionRate_asc(佣金比率从低到高)
	 * commissionNum_desc(成交量成高到低) commissionNum_asc(成交量从低到高)
	 * commissionVolume_desc(总支出佣金从高到底) commissionVolume_asc(总支出佣金从低到高)
	 * delistTime_desc(商品下架时间从高到底) delistTime_asc(商品下架时间从低到高)
	 */
	private String sort;

	/**
	 * 是否查询消保卖家
	 */
	private Boolean guarantee;

	/**
	 * 起始佣金比率选项，如：1234表示12.34%
	 */
	private String start_commissionRate;

	/**
	 * 最高佣金比率选项，如：2345表示23.45%。注：要起始佣金比率和最高佣金比率一起设置才有效。
	 */
	private String end_commissionRate;

	/**
	 * 起始累计推广量选项.注：返回的数据是30天内累计推广量
	 */
	private String start_commissionNum;

	/**
	 * 最高累计推广量选项
	 */
	private String end_commissionNum;

	/**
	 * 累计推广量范围开始
	 */
	private String start_totalnum;

	/**
	 * 累计推广量范围结束
	 */
	private String end_totalnum;

	/**
	 * 可选 抵价券
	 */
	private String cash_coupon;

	/**
	 * 可选 支持VIP卡
	 */
	private String vip_card;

	/**
	 * 可选 海外商品
	 */
	private String overseas_item;

	/**
	 * 可选 7天退换
	 */
	private String sevendays_return;

	/**
	 * 可选 如实描述
	 */
	private String real_describe;

	/**
	 * 可选 30天维修
	 */
	private String onemonth_repair;

	/**
	 * 可选 货到付款
	 */
	private String cash_ondelivery;

	/**
	 * 可选 商城商品
	 */
	private String mall_item;

	/**
	 * 可选 结果页数
	 */
	private int page_no;

	/**
	 * 可选 每页返回结果数.最大每页40
	 */
	private int page_size;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCid() {
		return cid;
	}

	public double getStart_price() {
		return start_price;
	}

	public void setStart_price(double startPrice) {
		start_price = startPrice;
	}

	public double getEnd_price() {
		return end_price;
	}

	public void setEnd_price(double endPrice) {
		end_price = endPrice;
	}

	public Boolean getAuto_send() {
		return auto_send;
	}

	public void setAuto_send(Boolean autoSend) {
		auto_send = autoSend;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStart_credit() {
		return start_credit;
	}

	public void setStart_credit(String startCredit) {
		start_credit = startCredit;
	}

	public String getEnd_credit() {
		return end_credit;
	}

	public void setEnd_credit(String endCredit) {
		end_credit = endCredit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Boolean getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(Boolean guarantee) {
		this.guarantee = guarantee;
	}

	public String getStart_commissionRate() {
		return start_commissionRate;
	}

	public void setStart_commissionRate(String startCommissionRate) {
		start_commissionRate = startCommissionRate;
	}

	public String getEnd_commissionRate() {
		return end_commissionRate;
	}

	public void setEnd_commissionRate(String endCommissionRate) {
		end_commissionRate = endCommissionRate;
	}

	public void setStart_commissionNum(String startCommissionNum) {
		start_commissionNum = startCommissionNum;
	}

	public String getStart_commissionNum() {
		return start_commissionNum;
	}

	public void setCash_coupon(String cashCoupon) {
		cash_coupon = cashCoupon;
	}

	public String getCash_coupon() {
		return cash_coupon;
	}

	public String getVip_card() {
		return vip_card;
	}

	public void setVip_card(String vipCard) {
		vip_card = vipCard;
	}

	public String getOverseas_item() {
		return overseas_item;
	}

	public void setOverseas_item(String overseasItem) {
		overseas_item = overseasItem;
	}

	public String getSevendays_return() {
		return sevendays_return;
	}

	public void setSevendays_return(String sevendaysReturn) {
		sevendays_return = sevendaysReturn;
	}

	public String getReal_describe() {
		return real_describe;
	}

	public void setReal_describe(String realDescribe) {
		real_describe = realDescribe;
	}

	public String getOnemonth_repair() {
		return onemonth_repair;
	}

	public void setOnemonth_repair(String onemonthRepair) {
		onemonth_repair = onemonthRepair;
	}

	public String getCash_ondelivery() {
		return cash_ondelivery;
	}

	public void setCash_ondelivery(String cashOndelivery) {
		cash_ondelivery = cashOndelivery;
	}

	public String getMall_item() {
		return mall_item;
	}

	public void setMall_item(String mallItem) {
		mall_item = mallItem;
	}

	public int getPage_no() {
		return page_no;
	}

	public void setPage_no(int pageNo) {
		page_no = pageNo;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int pageSize) {
		page_size = pageSize;
	}

	public String getEnd_commissionNum() {
		return end_commissionNum;
	}

	public void setEnd_commissionNum(String endCommissionNum) {
		end_commissionNum = endCommissionNum;
	}

	public String getStart_totalnum() {
		return start_totalnum;
	}

	public void setStart_totalnum(String startTotalnum) {
		start_totalnum = startTotalnum;
	}

	public String getEnd_totalnum() {
		return end_totalnum;
	}

	public void setEnd_totalnum(String endTotalnum) {
		end_totalnum = endTotalnum;
	}

//	public String fmtCommissionRate(double n) {
//		double t=n/100*1000;
//		String v = String.valueOf(n);
//		n*1000/100;
//		0.1234;
//		1234
//		1.234=0.01234
//	}
}
