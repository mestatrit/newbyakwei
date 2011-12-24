package com.hk.svr.pub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hk.bean.ShortUrl;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.ShortUrlService;

/**
 * 功能为把内容中的url格式化， 如果有需求，就缩短url地址
 * 
 * @author akwei
 */
public class FmtUrlContent {
	/**
	 * 原来的内容
	 */
	private String content;

	/**
	 * 更改url后的内容
	 */
	private String fmtContent;

	/**
	 * 缩短的url与原url的key-value
	 */
	private final Map<String, String> urlMap = new HashMap<String, String>();

	public FmtUrlContent(String content, boolean needShort,
			String shortUrlDomain) {
		this.content = DataUtil.formatUrl(content);
		this.fmtContent = this.content;
		if (needShort) {
			List<String> urllist = DataUtil.parseUrl(this.content);
			String tmpurl = null;
			ShortUrlService shortUrlService = (ShortUrlService) HkUtil
					.getBean("shortUrlService");
			for (String url : urllist) {
				if (url.length() > 26 && url.indexOf(shortUrlDomain) == -1) {
					ShortUrl o = shortUrlService.createShortUrl(url);
					String shortUrlData = shortUrlDomain + "/"
							+ o.getShortKey();
					tmpurl = url.replaceAll("\\?", "\\\\?").replaceAll("\\$",
							"\\\\$");
					this.fmtContent = this.fmtContent.replaceAll(tmpurl,
							shortUrlData);
					urlMap.put(shortUrlData, url);
				}
			}
		}
	}

	/**
	 * 原来的内容
	 * 
	 * @return
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 格式化后的内容
	 * 
	 * @return
	 */
	public String getFmtContent() {
		return fmtContent;
	}

	/**
	 * 缩短后的url与原url的key-value
	 * 
	 * @return
	 */
	public Map<String, String> getUrlMap() {
		return urlMap;
	}
}