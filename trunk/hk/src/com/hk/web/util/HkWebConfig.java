package com.hk.web.util;

import java.util.Set;

import org.springframework.beans.factory.InitializingBean;

public class HkWebConfig implements InitializingBean {

	private static boolean devMode;

	private static String cookie_domain;

	private static String pvtpwd;

	private static String webDomain;

	private static String version;

	private static long sysFollowUserId = 3;

	private static boolean loginValidateFollow;

	// private static String userDomain;
	// private static String squareDomain;
	// private static String ipSquareDomain;
	// private static String citySquareDomain;
	// private static String nearBySquareDomain;
	// private static String boxDomain;
	private static String shortUrlDomain;

	private static String contextPath;

	private static String replyImg;

	private static String retweetImg;

	private static String retweet2Img;

	private static String dmImg;

	private static String starGreyImg;

	private static String starImg;

	private static String googleApiKey;

	private static String delImg;

	private static int nightCheckInHourBegin;

	private static int nightCheckInHourEnd;

	private static Set<String> protected_domainSet;

	public void setProtected_domainSet(Set<String> protectedDomainSet) {
		protected_domainSet = protectedDomainSet;
	}

	/**
	 * 查看是否用户自定义域名是系统关键字
	 * 
	 * @param key
	 * @return
	 */
	public static boolean isUserDomainIncludeKey(String key) {
		if (protected_domainSet == null) {
			return false;
		}
		for (String s : protected_domainSet) {
			if (s.equalsIgnoreCase(key)) {
				return true;
			}
		}
		return false;
	}

	public void setNightCheckInHourBegin(int nightCheckInHourBegin) {
		HkWebConfig.nightCheckInHourBegin = nightCheckInHourBegin;
	}

	public void setNightCheckInHourEnd(int nightCheckInHourEnd) {
		HkWebConfig.nightCheckInHourEnd = nightCheckInHourEnd;
	}

	public static int getNightCheckInHourBegin() {
		return nightCheckInHourBegin;
	}

	public static int getNightCheckInHourEnd() {
		return nightCheckInHourEnd;
	}

	public void setDelImg(String delImg) {
		HkWebConfig.delImg = delImg;
	}

	public static String getDelImg() {
		return delImg;
	}

	public void setGoogleApiKey(String googleApiKey) {
		HkWebConfig.googleApiKey = googleApiKey;
	}

	public static String getGoogleApiKey() {
		return googleApiKey;
	}

	public void setRetweet2Img(String retweet2Img) {
		HkWebConfig.retweet2Img = retweet2Img;
	}

	public static String getRetweet2Img() {
		return retweet2Img;
	}

	public void setStarImg(String starImg) {
		HkWebConfig.starImg = starImg;
	}

	public static String getStarImg() {
		return starImg;
	}

	public static String getReplyImg() {
		return replyImg;
	}

	public void setReplyImg(String replyImg) {
		HkWebConfig.replyImg = replyImg;
	}

	public static String getRetweetImg() {
		return retweetImg;
	}

	public void setRetweetImg(String retweetImg) {
		HkWebConfig.retweetImg = retweetImg;
	}

	public static String getDmImg() {
		return dmImg;
	}

	public void setDmImg(String dmImg) {
		HkWebConfig.dmImg = dmImg;
	}

	public static String getStarGreyImg() {
		return starGreyImg;
	}

	public void setStarGreyImg(String starGreyImg) {
		HkWebConfig.starGreyImg = starGreyImg;
	}

	public void setContextPath(String contextPath) {
		HkWebConfig.contextPath = contextPath;
	}

	public void setShortUrlDomain(String shortUrlDomain) {
		HkWebConfig.shortUrlDomain = shortUrlDomain;
	}

	public static String getContextPath() {
		return contextPath;
	}

	public static String getShortUrlDomain() {
		return shortUrlDomain;
	}

	// public static String getUserDomain() {
	// return userDomain;
	// }
	// public void setUserDomain(String userDomain) {
	// HkWebConfig.userDomain = userDomain;
	// }
	// public static String getSquareDomain() {
	// return squareDomain;
	// }
	//
	// public void setSquareDomain(String squareDomain) {
	// HkWebConfig.squareDomain = squareDomain;
	// }
	// public static String getIpSquareDomain() {
	// return ipSquareDomain;
	// }
	//
	// public void setIpSquareDomain(String ipSquareDomain) {
	// HkWebConfig.ipSquareDomain = ipSquareDomain;
	// }
	// public static String getCitySquareDomain() {
	// return citySquareDomain;
	// }
	//
	// public void setCitySquareDomain(String citySquareDomain) {
	// HkWebConfig.citySquareDomain = citySquareDomain;
	// }
	// public static String getNearBySquareDomain() {
	// return nearBySquareDomain;
	// }
	// public void setNearBySquareDomain(String nearBySquareDomain) {
	// HkWebConfig.nearBySquareDomain = nearBySquareDomain;
	// }
	// public static String getBoxDomain() {
	// return boxDomain;
	// }
	//
	// public void setBoxDomain(String boxDomain) {
	// HkWebConfig.boxDomain = boxDomain;
	// }
	public void setLoginValidateFollow(boolean loginValidateFollow) {
		HkWebConfig.loginValidateFollow = loginValidateFollow;
	}

	public void setSysFollowUserId(long sysFollowUserId) {
		HkWebConfig.sysFollowUserId = sysFollowUserId;
	}

	public static long getSysFollowUserId() {
		return sysFollowUserId;
	}

	public static boolean isLoginValidateFollow() {
		return loginValidateFollow;
	}

	public void setVersion(String version) {
		HkWebConfig.version = version;
	}

	public static String getVersion() {
		return version;
	}

	public void setWebDomain(String webDomain) {
		HkWebConfig.webDomain = webDomain;
	}

	public static String getWebDomain() {
		return webDomain;
	}

	public void setPvtpwd(String pvtpwd) {
		HkWebConfig.pvtpwd = pvtpwd;
	}

	public static String getPvtpwd() {
		return pvtpwd;
	}

	public void setCookie_domain(String cookie_domain) {
		HkWebConfig.cookie_domain = cookie_domain;
	}

	public static String getCookie_domain() {
		return cookie_domain;
	}

	public void afterPropertiesSet() throws Exception {
		HkWebConfig.dmImg = HkWebConfig.getContextPath() + "/"
				+ HkWebConfig.getDmImg();
		HkWebConfig.replyImg = HkWebConfig.getContextPath() + "/"
				+ HkWebConfig.getReplyImg();
		HkWebConfig.retweetImg = HkWebConfig.getContextPath() + "/"
				+ HkWebConfig.getRetweetImg();
		HkWebConfig.starGreyImg = HkWebConfig.getContextPath() + "/"
				+ HkWebConfig.getStarGreyImg();
		HkWebConfig.starImg = HkWebConfig.getContextPath() + "/"
				+ HkWebConfig.getStarImg();
		HkWebConfig.delImg = HkWebConfig.getContextPath() + "/"
				+ HkWebConfig.delImg;
	}

	public static boolean isDevMode() {
		return devMode;
	}

	public void setDevMode(boolean devMode) {
		HkWebConfig.devMode = devMode;
	}
}