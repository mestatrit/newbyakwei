package com.hk.bean;

import java.util.Calendar;
import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;

@Table(name = "laba", id = "labaid")
public class Laba {
	public static final int SENDFROM_WAP = 0;

	public static final int SENDFROM_SMS = 1;

	public static final int SENDFROM_WEB = 2;

	public static final int SENDFROM_MSN = 3;

	public static final int SENDFROM_BOSEE = 4;

	@Id
	private long labaId;// id

	@Column
	private long userId;// 用户id

	@Column
	private String content;// 喇叭内容

	@Column
	private Date createTime;// 喇叭发表时间

	@Column
	private int replyCount;

	@Column
	private int sendFrom;

	@Column
	private String ip;

	@Column
	private int cityId;

	@Column
	private int rangeId;

	@Column
	private long refLabaId;// 被引用的labaid

	@Column
	private int refcount;

	@Column
	private String longContent;

	@Column
	private long productId;

	/**
	 * 被回应的喇叭id
	 */
	@Column
	private long mainLabaId;

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getProductId() {
		return productId;
	}

	public void setLongContent(String longContent) {
		this.longContent = longContent;
	}

	public String getLongContent() {
		return longContent;
	}

	public void setRefcount(int refcount) {
		this.refcount = refcount;
	}

	public int getRefcount() {
		return refcount;
	}

	public void setRefLabaId(long refLabaId) {
		this.refLabaId = refLabaId;
	}

	public long getRefLabaId() {
		return refLabaId;
	}

	public boolean isReplyMe() {
		return false;
	}

	/**
	 * @param replyMe
	 */
	public void setReplyMe(boolean replyMe) {//
	}

	public User getUser() {
		UserService userService = (UserService) HkUtil.getBean("userService");
		return userService.getUser(userId);
	}

	public long getLabaId() {
		return labaId;
	}

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public int getSendFrom() {
		return sendFrom;
	}

	public void setSendFrom(int sendFrom) {
		this.sendFrom = sendFrom;
	}

	public byte getSENDFROM_WAP() {
		return SENDFROM_WAP;
	}

	public byte getSENDFROM_SMS() {
		return SENDFROM_SMS;
	}

	public byte getSENDFROM_WEB() {
		return SENDFROM_WEB;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getRangeId() {
		return rangeId;
	}

	public void setRangeId(int rangeId) {
		this.rangeId = rangeId;
	}

	public byte getSENDFROM_MSN() {
		return SENDFROM_MSN;
	}

	public byte getSENDFROM_BOSEE() {
		return SENDFROM_BOSEE;
	}

	public static int validateContent(String content) {
		String s = DataUtil.toTextRow(content);
		if (DataUtil.isEmpty(s)) {
			return Err.LABA_CONTENT_ERROR;
		}
		if (s.toLowerCase().indexOf("http://") != -1
				|| s.toLowerCase().indexOf("https://") != -1) {// 如果有url，则去除url计算字数
			String cont = DataUtil.replaceAllUrl(s, "");
			if (cont.length() > 500) {
				return Err.LABA_CONTENT_LEN_TOOLONG;
			}
		}
		else {
			if (s.length() > 500) {
				return Err.LABA_CONTENT_LEN_TOOLONG;
			}
		}
		return Err.SUCCESS;
	}

	public long getTime() {
		return this.createTime.getTime();
	}

	public boolean isCreateAtToday() {
		Calendar now = Calendar.getInstance();
		Calendar date_Calendar = Calendar.getInstance();
		date_Calendar.setTime(this.createTime);
		int n_year = now.get(Calendar.YEAR);
		int n_month = now.get(Calendar.MONTH);
		int n_date = now.get(Calendar.DATE);
		int d_year = date_Calendar.get(Calendar.YEAR);
		int d_month = date_Calendar.get(Calendar.MONTH);
		int d_date = date_Calendar.get(Calendar.DATE);
		if (n_year == d_year && n_month == d_month && n_date == d_date) {
			return true;
		}
		return false;
	}

	public long getMainLabaId() {
		return mainLabaId;
	}

	public void setMainLabaId(long mainLabaId) {
		this.mainLabaId = mainLabaId;
	}
}