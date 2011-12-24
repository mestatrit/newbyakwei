package com.hk.web.shorturl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.ShortUrl;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.ShortUrlService;
import com.hk.web.pub.action.BaseAction;

@Component("/shorturl")
public class ShortUrlAction extends BaseAction {

	@Autowired
	private ShortUrlService shortUrlService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String key = req.getString("key");
		ShortUrl shortUrl = this.shortUrlService.getShortUrlByShortKey(key);
		if (shortUrl != null) {
			if (!ServletUtil.isPc(req)) {// 如果不是pc访问,到页面提示转换链接访问
				req.setAttribute("shortUrl", shortUrl);
				req.setEncodeAttribute("url", shortUrl.getUrl());
				return "/WEB-INF/page/shorturl/urltip.jsp";
			}
			this.shortUrlService.addShortUrlPcount(shortUrl.getUrlId(), 1);
			String l = shortUrl.getUrl().toLowerCase();
			if (!l.startsWith("http://") && !l.startsWith("https://")) {
				return "r:http://" + shortUrl.getUrl().replaceAll("&amp;", "&");
			}
			return "r:" + shortUrl.getUrl().replaceAll("&amp;", "&");
		}
		return null;
	}
}