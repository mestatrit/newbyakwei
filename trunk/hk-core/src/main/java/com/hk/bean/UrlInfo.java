package com.hk.bean;

public class UrlInfo {
	private String userUrlClass;

	private String tagUrlClass;

	private String shortUrlClass;

	private String userUrl;

	private String companyUrl;

	private boolean rewriteUserUrl;

	private boolean rewriteTagUrl;

	private String tagUrl;

	private boolean userNewWin;

	private boolean tagNewWin;

	private boolean urlNewWin;

	private boolean needGwt;

	private String cmpListUrl;

	public void setCmpListUrl(String cmpListUrl) {
		this.cmpListUrl = cmpListUrl;
	}

	public String getCmpListUrl() {
		return cmpListUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setNeedGwt(boolean needGwt) {
		this.needGwt = needGwt;
	}

	public boolean isNeedGwt() {
		return needGwt;
	}

	public boolean isUserNewWin() {
		return userNewWin;
	}

	public void setUserNewWin(boolean userNewWin) {
		this.userNewWin = userNewWin;
	}

	public boolean isTagNewWin() {
		return tagNewWin;
	}

	public void setTagNewWin(boolean tagNewWin) {
		this.tagNewWin = tagNewWin;
	}

	public boolean isUrlNewWin() {
		return urlNewWin;
	}

	public void setUrlNewWin(boolean urlNewWin) {
		this.urlNewWin = urlNewWin;
	}

	public String getShortUrlClass() {
		return shortUrlClass;
	}

	public void setShortUrlClass(String shortUrlClass) {
		this.shortUrlClass = shortUrlClass;
	}

	public String getUserUrl() {
		return userUrl;
	}

	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}

	public String getTagUrl() {
		return tagUrl;
	}

	public void setTagUrl(String tagUrl) {
		this.tagUrl = tagUrl;
	}

	public String getUserUrlClass() {
		return userUrlClass;
	}

	public void setUserUrlClass(String userUrlClass) {
		this.userUrlClass = userUrlClass;
	}

	public String getTagUrlClass() {
		return tagUrlClass;
	}

	public void setTagUrlClass(String tagUrlClass) {
		this.tagUrlClass = tagUrlClass;
	}

	public boolean isRewriteUserUrl() {
		return rewriteUserUrl;
	}

	public void setRewriteUserUrl(boolean rewriteUserUrl) {
		this.rewriteUserUrl = rewriteUserUrl;
	}

	public boolean isRewriteTagUrl() {
		return rewriteTagUrl;
	}

	public void setRewriteTagUrl(boolean rewriteTagUrl) {
		this.rewriteTagUrl = rewriteTagUrl;
	}
}