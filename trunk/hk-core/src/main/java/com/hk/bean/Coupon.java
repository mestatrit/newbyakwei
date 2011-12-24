package com.hk.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 优惠券可挂到分类，挂到地区，挂到足迹,通过扣除下载次数来使用
 * 
 * @author akwei
 */
@Table(name = "coupon", id = "couponid")
public class Coupon {

	private static char[] base = new char[] { '3', '0', '9', '1', '8', '2',
			'4', '6', '5', '7' };

	private static final Map<Integer, char[]> basemap = new HashMap<Integer, char[]>();
	static {
		List<String> list = new ArrayList<String>();
		// 把预先定义的字符放入一个集合当中
		for (int i = 0; i < base.length; i++) {
			list.add(String.valueOf(base[i]));
		}
		int k = 0;
		int size = 20;
		for (int i = 0; i < size; i++) {
			if (k >= base.length) {
				k = 0;
			}
			List<String> endlist = new ArrayList<String>(list.subList(0, k));
			List<String> reslist = new ArrayList<String>(list.subList(k, list
					.size()));
			reslist.addAll(endlist);
			k++;
			StringBuffer sb = new StringBuffer();
			for (String s : reslist) {
				sb.append(s);
			}
			char[] base_i = sb.toString().toCharArray();
			basemap.put(i, base_i);
		}
	}

	public static final byte USEFLG_AVAILABLE = 0;

	public static final byte USEFLG_UNAVAILABLE = 1;

	public static final byte OVERDUEFLG_DAY = 0;

	public static final byte OVERDUEFLG_TIME = 1;

	/**
	 * 挂接到足迹
	 */
	public static final byte SHOWFUNCFLG_CMP = 0;

	/**
	 * 挂接到分类
	 */
	public static final byte SHOWFUNCFLG_KIND = 1;

	/**
	 * 挂接到地区
	 */
	public static final byte SHOWFUNCFLG_ZONE = 2;

	@Id
	private long couponId;

	@Column
	private long userId;

	@Column
	private long companyId;

	/**
	 * 名称
	 */
	@Column
	private String name;

	/**
	 * 内容
	 */
	@Column
	private String content;

	/**
	 * 备注
	 */
	@Column
	private String remark;

	/**
	 * 创建时间
	 */
	@Column
	private Date createTime;

	/**
	 * 下载数量
	 */
	@Column
	private int dcount;

	/**
	 * 过期方式0:下载后剩余天数,1:设置过期时间
	 */
	@Column
	private byte overdueflg;

	/**
	 * 图片路径
	 */
	@Column
	private String picpath;

	/**
	 * 数量
	 */
	@Column
	private int amount;

	/**
	 * 0:正常使用,1:作废
	 */
	@Column
	private byte useflg;

	@Column
	private long uid;

	/**
	 * 展示地区
	 */
	@Column
	private int cityId;

	/**
	 * 存储json格式数据，包括过期天数或者是过期时间
	 */
	@Column
	private String data;

	/**
	 * 兑换暗号
	 */
	@Column
	private String codedata;

	@Column
	private byte cmppink;

	@Column
	private Date cmppinkTime;

	public void setCodedata(String codedata) {
		this.codedata = codedata;
	}

	public String getCodedata() {
		return codedata;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getCityId() {
		return cityId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isAvailable() {
		if (this.useflg == USEFLG_AVAILABLE) {
			return true;
		}
		return false;
	}

	public byte getUseflg() {
		return useflg;
	}

	public void setUseflg(byte useflg) {
		this.useflg = useflg;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getLimitDay() {
		if (this.getData() != null) {
			Map<String, String> map = DataUtil.getMapFromJson(this.getData());
			String v = map.get("limitday");
			if (v != null) {
				Integer t = Integer.parseInt(v);
				if (t != null) {
					return t;
				}
			}
			return 0;
		}
		return 0;
	}

	public int getDcount() {
		return dcount;
	}

	public void setDcount(int dcount) {
		this.dcount = dcount;
	}

	public byte getOverdueflg() {
		return overdueflg;
	}

	public void setOverdueflg(byte overdueflg) {
		this.overdueflg = overdueflg;
	}

	public Date getEndTime() {
		if (this.getData() != null) {
			Map<String, String> map = DataUtil.getMapFromJson(this.getData());
			String v = map.get("endtime");
			if (v != null) {
				Long t = Long.parseLong(v);
				if (t != null) {
					return new Date(t);
				}
			}
			return null;
		}
		return null;
	}

	public String getPicpath() {
		return picpath;
	}

	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getPic240() {
		return ImageConfig.getCouponPic240Url(this.picpath);
	}

	public boolean isRemain() {
		if (this.amount - this.dcount > 0) {
			return true;
		}
		return false;
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.name);
		if (DataUtil.isEmpty(s)) {
			return Err.COUPON_NAME_ERROR;
		}
		if (s.length() > 20) {
			return Err.COUPON_NAME_ERROR;
		}
		s = DataUtil.toText(this.content);
		if (!DataUtil.isEmpty(s)) {
			if (s.length() > 200) {
				return Err.COUPON_CONTENT_LENGTH_TOO_LONG;
			}
		}
		s = DataUtil.toText(this.remark);
		if (!DataUtil.isEmpty(s)) {
			if (s.length() > 200) {
				return Err.COUPON_REMARK_LENGTH_TOO_LONG;
			}
		}
		if (this.overdueflg == OVERDUEFLG_DAY) {
			if (this.getLimitDay() <= 0) {
				return Err.COUPON_LIMITDAY_ERROR;
			}
		}
		else {
			if (this.getEndTime() == null
					|| this.getEndTime().getTime() <= System
							.currentTimeMillis()) {
				return Err.COUPON_ENDTIME_ERROR;
			}
		}
		return Err.SUCCESS;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	/**
	 * 是否是下载后剩余天数的方式
	 * 
	 * @return
	 *         2010-5-4
	 */
	public boolean isLimitDayStyle() {
		if (this.overdueflg == OVERDUEFLG_DAY) {
			return true;
		}
		return false;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getH_0Pic() {
		return ImageConfig.getPhotoPicH_0Url(this.picpath);
	}

	public String getH_1Pic() {
		return ImageConfig.getPhotoPicH_1Url(this.picpath);
	}

	public String getH_2Pic() {
		return ImageConfig.getPhotoPicH_2Url(this.picpath);
	}

	public String toJsonData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", this.name);
		map.put("amount", String.valueOf(amount));
		map.put("companyid", String.valueOf(this.companyId));
		map.put("overdueflg", String.valueOf(this.overdueflg));
		map.put("data", this.data);
		if (remark != null) {
			map.put("remark", this.remark);
		}
		if (content != null) {
			map.put("content", this.content);
		}
		return DataUtil.toJson(map);
	}

	public Date createEndTime(Date date) {
		if (getOverdueflg() == Coupon.OVERDUEFLG_DAY) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, getLimitDay());
			return c.getTime();
		}
		return getEndTime();
	}

	private int[] getDataList() {
		String[] s = this.codedata.split(",");
		int[] arr = new int[s.length];
		int i = 0;
		for (String ss : s) {
			arr[i++] = Integer.parseInt(ss);
		}
		return arr;
	}

	private int[] getNextDataList() {
		// 获得最后使用的下标数组
		int[] arr = this.getDataList();
		// 此参数的意义是是否数组长度发生了变化
		boolean is_arr_length_changed = false;
		for (int i = arr.length - 1; i >= 0; i--) {
			int res = this.add(i, arr[i]);
			arr[i] = res;
			if (res == 0) {
				// 如果==0，说明下标已经到了最后一位,+1后会返回0，(类似于1+9=10,结果变为2位数，需要进位)
				is_arr_length_changed = true;
				// 变为0后，前一位下标肯定要进行变化，所以继续循环，把前一位下标也+1
				continue;
			}
			// 如果>0，说明不需要进位
			if (res > 0) {
				is_arr_length_changed = false;
				break;
			}
		}
		StringBuilder sb = new StringBuilder();
		// 如果int[]数组长度没有变化，则直接给data赋值
		if (!is_arr_length_changed) {
			for (int i = 0; i < arr.length; i++) {
				sb.append(arr[i]).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			this.codedata = sb.toString();
			return arr;
		}
		// 如果int[]数组长度改变了,那么就用一个新的数组，新数组的值为arr中的值，最后一位值为0
		int[] arr2 = new int[arr.length + 1];
		for (int i = 0; i < arr2.length; i++) {
			arr2[i] = 0;
			sb.append(arr2[i]).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		this.codedata = sb.toString();
		return arr2;
	}

	public String getNextShortKey() {
		int[] arr = this.getNextDataList();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			// sb.append(base[arr[i]]);
			char[] ch = basemap.get(i);
			sb.append(ch[arr[i]]);
		}
		return sb.toString();
	}

	private int add(int digits, int i) {
		char[] ch = basemap.get(digits);
		int res = (i + 1) % ch.length;
		return res;
	}

	public byte getCmppink() {
		return cmppink;
	}

	public void setCmppink(byte cmppink) {
		this.cmppink = cmppink;
	}

	public Date getCmppinkTime() {
		return cmppinkTime;
	}

	public void setCmppinkTime(Date cmppinkTime) {
		this.cmppinkTime = cmppinkTime;
	}

	public boolean isPinkForCmp() {
		if (this.cmppink == CmpUtil.CMPPINK_Y) {
			return true;
		}
		return false;
	}
}