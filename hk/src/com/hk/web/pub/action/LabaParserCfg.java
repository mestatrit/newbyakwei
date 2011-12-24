package com.hk.web.pub.action;

import com.hk.bean.UrlInfo;

public class LabaParserCfg {
	private String contextPath;

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	private long userId;

	private UrlInfo urlInfo;

	private boolean parseLongContent;

	private boolean parseContent = true;

	private boolean checkFav = true;

	private boolean forapi;

	private boolean formatRef;

	/**
	 * 是否去掉引用后面的内容，中文以(针对(为准)
	 */
	private boolean removeRefFlg;

	private byte labartflg;

	private int filterReplyCharSize;

	/**
	 * 检查是否已经引用过该喇叭
	 */
	private boolean checkUserRef;

	public LabaParserCfg() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 设置是否已检查经引用过该喇叭
	 * 
	 * @param checkUserRef
	 */
	public void setCheckUserRef(boolean checkUserRef) {
		this.checkUserRef = checkUserRef;
	}

	/**
	 * 检查是否已经引用过该喇叭
	 * 
	 * @return
	 */
	public boolean isCheckUserRef() {
		return checkUserRef;
	}

	public int getFilterReplyCharSize() {
		return filterReplyCharSize;
	}

	public void setFilterReplyCharSize(int filterReplyCharSize) {
		this.filterReplyCharSize = filterReplyCharSize;
	}

	public byte getLabartflg() {
		return labartflg;
	}

	public void setLabartflg(byte labartflg) {
		this.labartflg = labartflg;
	}

	public void setRemoveRefFlg(boolean removeRefFlg) {
		this.removeRefFlg = removeRefFlg;
	}

	/**
	 * 是否删除被引用的内容
	 * 
	 * @return
	 */
	public boolean isRemoveRefFlg() {
		return removeRefFlg;
	}

	public void setCheckFav(boolean checkFav) {
		this.checkFav = checkFav;
	}

	public boolean isCheckFav() {
		return checkFav;
	}

	public UrlInfo getUrlInfo() {
		return urlInfo;
	}

	public void setUrlInfo(UrlInfo urlInfo) {
		this.urlInfo = urlInfo;
	}

	public boolean isParseLongContent() {
		return parseLongContent;
	}

	public void setParseLongContent(boolean parseLongContent) {
		this.parseLongContent = parseLongContent;
	}

	public boolean isParseContent() {
		return parseContent;
	}

	public void setParseContent(boolean parseContent) {
		this.parseContent = parseContent;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public boolean isForapi() {
		return forapi;
	}

	public void setForapi(boolean forapi) {
		this.forapi = forapi;
	}

	public boolean isFormatRef() {
		return formatRef;
	}

	public void setFormatRef(boolean formatRef) {
		this.formatRef = formatRef;
	}
}