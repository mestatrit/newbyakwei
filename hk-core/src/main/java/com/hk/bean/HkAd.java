package com.hk.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 火酷广告，包括文字和图片广告
 * 
 * @author jyy
 */
@Table(name = "hkad")
public class HkAd {

	/**
	 * 暂停使用
	 */
	public static final byte USEFLG_N = 0;

	/**
	 * 使用中
	 */
	public static final byte USEFLG_Y = 1;

	/**
	 * 文字广告
	 */
	public static final byte SHOWFLG_CHAR = 0;

	/**
	 * 图片广告
	 */
	public static final byte SHOWFLG_IMG = 1;

	@Id
	private long oid;

	@Column
	private long userId;

	/**
	 * 广告类型0:文字,1:图片
	 */
	@Column
	private byte showflg;

	/**
	 * json格式数据，可以使文字广告内容或者是图片广告路径
	 */
	@Column
	private String data;

	/**
	 * 创建时间
	 */
	@Column
	private Date createTime;

	/**
	 * 消耗方式
	 */
	@Column
	private byte expendflg;

	/**
	 * 现有展现次数
	 */
	@Column
	private int viewCount;

	/**
	 * 页面预设总共展现次数
	 */
	@Column
	private int totalViewCount;

	/**
	 * 名称
	 */
	@Column
	private String name;

	/**
	 * 使用状态
	 */
	@Column
	private byte useflg;

	/**
	 * 广告链接
	 */
	@Column
	private String href;

	/**
	 * 展示地区
	 */
	@Column
	private int cityId;

	private String adData;

	public void setAdData(String adData) {
		this.adData = adData;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getCityId() {
		return cityId;
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

	public byte getShowflg() {
		return showflg;
	}

	public void setShowflg(byte showflg) {
		this.showflg = showflg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte getExpendflg() {
		return expendflg;
	}

	public void setExpendflg(byte expendflg) {
		this.expendflg = expendflg;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getTotalViewCount() {
		return totalViewCount;
	}

	public void setTotalViewCount(int totalViewCount) {
		this.totalViewCount = totalViewCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getUseflg() {
		return useflg;
	}

	public void setUseflg(byte useflg) {
		this.useflg = useflg;
	}

	public boolean isImageShow() {
		if (this.showflg == SHOWFLG_IMG) {
			return true;
		}
		return false;
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.name);
		if (DataUtil.isEmpty(s) || s.length() > 30) {
			return Err.HKAD_NAME_ERROR;
		}
		if (totalViewCount <= 0) {
			return Err.HKAD_TOTALVIEWCOUNT_ERROR;
		}
		s = DataUtil.toTextRow(this.href);
		if (DataUtil.isEmpty(s) || s.length() > 200) {
			return Err.HKAD_HREF_ERROR;
		}
		s = DataUtil.toText(this.adData);
		if (DataUtil.isEmpty(s) || s.length() > 500) {
			return Err.HKAD_ADDATA_ERROR;
		}
		return Err.SUCCESS;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String toJsonData() {
		Map<String, String> map = new HashMap<String, String>();
		if (name != null) {
			map.put("name", this.name);
		}
		map.put("showflg", String.valueOf(showflg));
		map.put("totalviewcount", String.valueOf(totalViewCount));
		map.put("expendflg", String.valueOf(expendflg));
		if (href != null) {
			map.put("href", href);
		}
		if (this.data != null) {
			map.put("data", this.data);
		}
		return DataUtil.toJson(map);
	}

	public boolean isPause() {
		if (this.useflg == USEFLG_N) {
			return true;
		}
		return false;
	}

	public String getImgUrl() {
		if (this.data == null) {
			return null;
		}
		Map<String, String> map = DataUtil.getMapFromJson(this.data);
		String path = map.get("imgpath");
		if (path != null) {
			return ImageConfig.getHkAdPicUrl(path);
		}
		return null;
	}

	public String getAdData() {
		if (this.adData != null) {
			return this.adData;
		}
		if (this.data == null) {
			return null;
		}
		Map<String, String> map = DataUtil.getMapFromJson(this.data);
		return map.get("addata");
	}
}