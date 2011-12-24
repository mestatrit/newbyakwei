package web.pub.util;

public class UrlInfo {
	private String userUrl;

	private String tagUrl;

	private String companyUrl;

	private boolean needGwt;

	public boolean isNeedGwt() {
		return needGwt;
	}

	public void setNeedGwt(boolean needGwt) {
		this.needGwt = needGwt;
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

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
}