package com.hk.svr.laba.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hk.bean.IpCityRange;
import com.hk.bean.ShortUrl;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.IpCityService;

public class LabaInfo {
	private int cityId;

	private int rangeId;

	private String ip;

	private long userId;

	private final List<Long> userIdList = new ArrayList<Long>(1);

	private final List<Long> tagIdList = new ArrayList<Long>(1);

	private final List<Long> companyIdList = new ArrayList<Long>(1);

	private List<String> tagValueList;

	private List<String> nickNameList;

	private List<String> urlList = new ArrayList<String>(2);

	private List<ShortUrl> shortUrlList = new ArrayList<ShortUrl>(2);

	private String parsedContent;

	private int sendFrom;

	private String content;

	/**
	 * 被引用(转发)的喇叭的id
	 */
	private long refLabaId;

	/**
	 * 被回复的喇叭id
	 */
	private long replyLabaId;

	private Date createTime;

	private String longParsedContent;

	private List<String> companyNameList;

	boolean addLabaTagRef = true;

	private long productId;

	private long replyUserId;

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getProductId() {
		return productId;
	}

	public List<Long> getCompanyIdList() {
		return companyIdList;
	}

	public void addCompnayId(long companyId) {
		this.companyIdList.add(companyId);
	}

	public void setAddLabaTagRef(boolean addLabaTagRef) {
		this.addLabaTagRef = addLabaTagRef;
	}

	public boolean isAddLabaTagRef() {
		return addLabaTagRef;
	}

	public void setCompanyNameList(List<String> companyNameList) {
		this.companyNameList = companyNameList;
	}

	public List<String> getCompanyNameList() {
		return companyNameList;
	}

	public void setLongParsedContent(String longParsedContent) {
		this.longParsedContent = longParsedContent;
	}

	public String getLongParsedContent() {
		return longParsedContent;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public long getRefLabaId() {
		return refLabaId;
	}

	public void setRefLabaId(long refLabaId) {
		this.refLabaId = refLabaId;
	}

	public void setSendFrom(int sendFrom) {
		this.sendFrom = sendFrom;
	}

	public int getSendFrom() {
		return sendFrom;
	}

	public List<ShortUrl> getShortUrlList() {
		return shortUrlList;
	}

	public void addTagId(long tagId) {
		this.tagIdList.add(tagId);
	}

	public List<Long> getTagIdList() {
		return tagIdList;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public void setNickNameList(List<String> nickNameList) {
		this.nickNameList = nickNameList;
	}

	public void setTagValueList(List<String> tagValueList) {
		this.tagValueList = tagValueList;
	}

	public void addUserId(long userId) {
		if (!this.userIdList.contains(userId)) {
			this.userIdList.add(userId);
		}
	}

	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}

	public void addNickName(String nickName) {
		this.nickNameList.add(nickName);
	}

	public List<String> getNickNameList() {
		return nickNameList;
	}

	public List<String> getUrlList() {
		return urlList;
	}

	public List<String> getTagValueList() {
		return tagValueList;
	}

	public List<Long> getUserIdList() {
		return userIdList;
	}

	public String getParsedContent() {
		return parsedContent;
	}

	public void setParsedContent(String parsedContent) {
		this.parsedContent = parsedContent;
	}

	public void setIp(String ip) {
		this.ip = ip;
		if (ip != null) {
			IpCityService ipCityService = (IpCityService) HkUtil
					.getBean("ipCityService");
			IpCityRange range = ipCityService.getIpCityRange(ip);
			if (range != null) {
				rangeId = range.getRangeId();
				cityId = range.getCityId();
			}
		}
	}

	public String getIp() {
		return ip;
	}

	// public void initShortUrl(String ignore) {
	// ShortUrlService shortUrlService = (ShortUrlService) HkUtil
	// .getBean("shortUrlService");
	// List<String> removelist = new ArrayList<String>();
	// for (String url : urlList) {
	// if (url.length() <= 26) {
	// continue;
	// }
	// if (url.indexOf(ignore) == -1) {
	// ShortUrl o = shortUrlService.createShortUrl(url);
	// if (o != null) {
	// shortUrlList.add(o);
	// removelist.add(url);
	// }
	// }
	// }
	// urlList.removeAll(removelist);
	// }
	/**
	 * 如果喇叭内容除了人名，没有其他任何内容，不允许提交
	 * 
	 * @return
	 */
	public boolean isEmptyContent() {
		String s = this.parsedContent.replaceAll(DataUtil.nickNameRegx, "");
		if (DataUtil.isEmpty(s)) {
			return true;
		}
		return false;
	}

	public int getCityId() {
		return cityId;
	}

	public int getRangeId() {
		return rangeId;
	}

	public long getReplyLabaId() {
		return replyLabaId;
	}

	public void setReplyLabaId(long replyLabaId) {
		this.replyLabaId = replyLabaId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(long replyUserId) {
		this.replyUserId = replyUserId;
	}
}