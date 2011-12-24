package com.hk.svr;

import com.hk.bean.ShortUrl;

public interface ShortUrlService {
	ShortUrl getShortUrlByUrl(String url);

	void addShortUrlPcount(long urlId, int add);

	ShortUrl createShortUrl(String url);

	ShortUrl getShortUrlByShortKey(String shortKey);

	ShortUrl getShortUrlByShortKeyFromOld(String shortKey);
}