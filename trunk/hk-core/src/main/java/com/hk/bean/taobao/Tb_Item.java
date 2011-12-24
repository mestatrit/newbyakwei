package com.hk.bean.taobao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 商品表
 * 
 * @author akwei
 */
@Table(name = "tb_item")
public class Tb_Item {

	@Id
	private long itemid;

	/**
	 * 商品数字id
	 */
	@Column
	private long num_iid;

	/**
	 * 商品淘宝地址
	 */
	@Column
	private String detail_url;

	/**
	 * 商品标题
	 */
	@Column
	private String title;

	/**
	 * 卖家昵称
	 */
	@Column
	private String nick;

	/**
	 * 商品类型(fixed:一口价;auction:拍卖)注：取消团购
	 */
	@Column
	private String item_type;

	/**
	 * 商品分类id
	 */
	@Column
	private long cid;

	/**
	 * 商品主图片地址。淘宝图片格式有_60x60.jpg,_80x80.jpg、_120x120.jpg、_160x160.jpg、_b.jpg
	 * (220x220)
	 */
	@Column
	private String pic_url;

	/**
	 * 商品数量
	 */
	@Column
	private int num;

	/**
	 * 有效期,7或者14（默认是14天）
	 */
	@Column
	private int valid_thru;

	/**
	 * 上架时间（格式：yyyy-MM-dd HH:mm:ss）
	 */
	@Column
	private Date list_time;

	/**
	 * 架时间（格式：yyyy-MM-dd HH:mm:ss）
	 */
	@Column
	private Date delist_time;

	public static byte STUFF_STATUS_NEW = 1;

	public static byte STUFF_STATUS_UNUSED = 2;

	public static byte STUFF_STATUS_SECOND = 3;

	/**
	 * 商品新旧程度(全新:new(1)，闲置:unused(2)，二手：second(3))，数字对应火酷数据格式
	 */
	@Column
	private byte stuff_status;

	/**
	 * 商品所在地
	 */
	@Column
	private String location;

	/**
	 * 商品价格，格式：5.00；单位：元；精确到：分
	 */
	@Column
	private double price;

	/**
	 * 卖家承担
	 */
	public static byte FREIGHT_PAYER_SELLER = 0;

	/**
	 * 买家承担
	 */
	public static byte FREIGHT_PAYER_BUYER = 1;

	/**
	 * 运费承担方式,seller（卖家承担）(0)，buyer(买家承担）(1)
	 */
	@Column
	private byte freight_payer;

	/**
	 * 没有发票
	 */
	public static byte HAS_INVOICE_N = 0;

	/**
	 * 有发票
	 */
	public static byte HAS_INVOICE_Y = 1;

	/**
	 * 是否有发票,true/false,1/0
	 */
	@Column
	private byte has_invoice;

	/**
	 * 没有保修
	 */
	public static byte HAS_WARRANTY_N = 0;

	/**
	 * 有保修
	 */
	public static byte HAS_WARRANTY_Y = 1;

	/**
	 * 是否有保修,true/false,1/0
	 */
	@Column
	private byte has_warranty;

	/**
	 * 商品修改时间（格式：yyyy-MM-dd HH:mm:ss）
	 */
	@Column
	private Date modified;

	/**
	 * 商品所属卖家的信用等级数，1表示1心，2表示2心……，只有调用商品搜索:taobao.items.get和taobao.items.
	 * search的时候才能返回
	 */
	@Column
	private long score;

	/**
	 * 商品30天交易量，只有调用商品搜索:taobao.items.get和taobao.items.search的时候才能返回
	 */
	@Column
	private long volume;

	// /**
	// * 商品属性 格式：pid:vid;pid:vid
	// */
	// @Column
	// private String props;
	//
	// /**
	// *
	// 商品属性名称。标识着props内容里面的pid和vid所对应的名称。格式为：pid1:vid1:pid_name1:vid_name1;pid2:
	// * vid2:pid_name2:vid_name2……
	// */
	// @Column
	// private String props_name;
	/**
	 * 在火酷创建时间
	 */
	@Column
	private Date create_time;

	/**
	 * 淘宝产品id
	 */
	@Column
	private long product_id;

	/**
	 * 火酷最后更新时间
	 */
	@Column
	private Date last_modified;

	/**
	 * 最火的商品按照此字段排序
	 */
	@Column
	private int huo_status;

	public static final byte ONE_STATION_N = 0;

	public static final byte ONE_STATION_Y = 1;

	/**
	 * 是否淘1站商品
	 */
	@Column
	private byte one_station;

	/**
	 * 在火酷的点评数量
	 */
	@Column
	private int cmt_num;

	/**
	 * 商品提交人id
	 */
	@Column
	private long userid;

	/**
	 * 在火酷的总评分
	 */
	@Column
	private int hkscore;

	/**
	 * 打分总人数
	 */
	@Column
	private int hkscore_num;

	@Column
	private long sid;

	@Column
	private String click_url;

	@Column
	private String shop_click_url;

	@Column
	private int domainid;

	/**
	 * 淘宝客佣金
	 */
	@Column
	private double commission;

	/**
	 * 淘宝客佣金比率(实际比率要用淘宝数据/10000)
	 */
	@Column
	private double commission_rate;

	/**
	 * 未推荐到首页
	 */
	public static final byte HOME_CMD_FLG_N = 0;

	/**
	 * 已推荐到首页
	 */
	public static final byte HOME_CMD_FLG_Y = 1;

	@Column
	private byte home_cmd_flg;

	/**
	 * 累计成交量.注：返回的数据是30天内累计推广量
	 */
	@Column
	private int commission_num;

	/**
	 * 最高推广量
	 * 
	 * @return
	 *         2010-10-21
	 */
	public long getItemid() {
		return itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	public long getNum_iid() {
		return num_iid;
	}

	public void setNum_iid(long numIid) {
		num_iid = numIid;
	}

	public String getDetail_url() {
		return detail_url;
	}

	public void setDetail_url(String detailUrl) {
		detail_url = detailUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String itemType) {
		item_type = itemType;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String picUrl) {
		pic_url = picUrl;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getValid_thru() {
		return valid_thru;
	}

	public void setValid_thru(int validThru) {
		valid_thru = validThru;
	}

	public Date getList_time() {
		return list_time;
	}

	public void setList_time(Date listTime) {
		list_time = listTime;
	}

	public Date getDelist_time() {
		return delist_time;
	}

	public void setDelist_time(Date delistTime) {
		delist_time = delistTime;
	}

	public byte getStuff_status() {
		return stuff_status;
	}

	public void setStuff_status(byte stuffStatus) {
		stuff_status = stuffStatus;
	}

	public void setStuff_statusFromString(String v) {
		if (v.equals("new")) {
			stuff_status = STUFF_STATUS_NEW;
		}
		else if (v.equals("unused")) {
			stuff_status = STUFF_STATUS_UNUSED;
		}
		else {
			stuff_status = STUFF_STATUS_SECOND;
		}
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public byte getFreight_payer() {
		return freight_payer;
	}

	public void setFreight_payer(byte freightPayer) {
		freight_payer = freightPayer;
	}

	public void setFreight_payerFromString(String v) {
		if (v.equals("seller")) {
			this.freight_payer = FREIGHT_PAYER_SELLER;
		}
		else {
			this.freight_payer = FREIGHT_PAYER_BUYER;
		}
	}

	public byte getHas_invoice() {
		return has_invoice;
	}

	public void setHas_invoice(byte hasInvoice) {
		has_invoice = hasInvoice;
	}

	public byte getHas_warranty() {
		return has_warranty;
	}

	public void setHas_warranty(byte hasWarranty) {
		has_warranty = hasWarranty;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long productId) {
		product_id = productId;
	}

	public Date getLast_modified() {
		return last_modified;
	}

	public void setLast_modified(Date lastModified) {
		last_modified = lastModified;
	}

	public byte getOne_station() {
		return one_station;
	}

	public void setOne_station(byte oneStation) {
		one_station = oneStation;
	}

	public int getCmt_num() {
		return cmt_num;
	}

	public void setCmt_num(int cmtNum) {
		cmt_num = cmtNum;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public int getHkscore() {
		return hkscore;
	}

	public void setHkscore(int hkscore) {
		this.hkscore = hkscore;
	}

	public int getHkscore_num() {
		return hkscore_num;
	}

	public void setHkscore_num(int hkscoreNum) {
		hkscore_num = hkscoreNum;
	}

	public double getAvgScore() {
		return getStarsLevel(this.hkscore, this.hkscore_num);
	}

	public static double getStarsLevel(int score, int score_num) {
		if (score <= 0 || score_num <= 0) {
			return 0;
		}
		int t_int = score / score_num;// 获得整数部分
		BigDecimal a = new BigDecimal(score);
		double remain = Math
				.abs(a.divide(new BigDecimal(score_num), 1,
						RoundingMode.HALF_UP).add(new BigDecimal(-t_int))
						.doubleValue());// 小数部分
		if (remain <= 0.3) {
			return t_int;
		}
		if (remain > 0.3 && remain < 0.7) {
			return t_int + 0.5;
		}
		return t_int + 1;
	}

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public String getClick_url() {
		return click_url;
	}

	public void setClick_url(String clickUrl) {
		click_url = clickUrl;
	}

	public String getShop_click_url() {
		return shop_click_url;
	}

	public void setShop_click_url(String shopClickUrl) {
		shop_click_url = shopClickUrl;
	}

	public int getDomainid() {
		return domainid;
	}

	public void setDomainid(int domainid) {
		this.domainid = domainid;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public void setCommission_rate(double commissionRate) {
		commission_rate = commissionRate;
	}

	public double getCommission_rate() {
		return commission_rate;
	}

	public double getUserYouHuiPrice() {
		return new BigDecimal(this.commission).divide(new BigDecimal(2), 1,
				RoundingMode.FLOOR).doubleValue();
	}

	public int getHuo_status() {
		return huo_status;
	}

	public void setHuo_status(int huoStatus) {
		huo_status = huoStatus;
	}

	public byte getHome_cmd_flg() {
		return home_cmd_flg;
	}

	public void setHome_cmd_flg(byte homeCmdFlg) {
		home_cmd_flg = homeCmdFlg;
	}

	public boolean isHomeCmd() {
		if (this.home_cmd_flg == HOME_CMD_FLG_Y) {
			return true;
		}
		return false;
	}

	
	public int getCommission_num() {
		return commission_num;
	}

	
	public void setCommission_num(int commissionNum) {
		commission_num = commissionNum;
	}
}