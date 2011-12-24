package com.hk.svr.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.ShortUrl;
import com.hk.bean.ShortUrlData;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.ShortUrlService;

public class ShortUrlServiceImpl implements ShortUrlService {
	@Autowired
	private QueryManager manager;

	public void addShortUrlPcount(long urlId, int add) {
		Query query = this.manager.createQuery();
		query.setTable(ShortUrl.class);
		query.addField("pcount", add);
		query.where("urlid=?").setParam(urlId);
		query.update();
	}

	public synchronized void createShortUrl(ShortUrl shortUrl) {
		ShortUrlData shortUrlData = this.getShortUrlData();
		String shortKey = null;
		Query query = this.manager.createQuery();
		while (true) {
			shortKey = shortUrlData.getNextShortKey();
			if (query.count(ShortUrl.class, "shortkey=?",
					new Object[] { shortKey }) == 0) {
				shortUrl.setShortKey(shortKey);
				shortUrl.setCreateTime(new Date());
				query.updateObject(shortUrlData);
				query.insertObject(shortUrl);
				break;
			}
		}
	}

	public ShortUrl createShortUrl(String url) {
		ShortUrl shortUrl = this.getShortUrlByUrl(url);
		if (shortUrl != null) {
			return shortUrl;
		}
		shortUrl = new ShortUrl();
		shortUrl.setUrl(url);
		this.createShortUrl(shortUrl);
		return shortUrl;
	}

	public ShortUrl getShortUrlByUrl(String url) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(ShortUrl.class, "url=?", new Object[] { url });
	}

	private ShortUrlData getShortUrlData() {
		Query query = this.manager.createQuery();
		return query.getObjectById(ShortUrlData.class, 1);
	}

	public ShortUrl getShortUrlByShortKey(String shortKey) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(ShortUrl.class, "shortkey=?",
				new Object[] { shortKey });
	}

	public ShortUrl getShortUrlByShortKeyFromOld(String shortKey) {
		Query query = this.manager.createQuery();
		String sql = "select * from shorturl where shortkey=?";
		List<ShortUrl> list = query.listBySqlEx("ds1", sql, ShortUrl.class,
				shortKey);
		if (list.size() == 0) {
			return null;
		}
		return list.iterator().next();
	}
}