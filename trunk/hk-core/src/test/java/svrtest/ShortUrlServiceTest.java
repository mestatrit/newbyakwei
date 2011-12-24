package svrtest;

import java.util.List;

import com.hk.bean.ShortUrl;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.P;
import com.hk.svr.ShortUrlService;

public class ShortUrlServiceTest extends HkServiceTest {
	private ShortUrlService shortUrlService;

	public void setShortUrlService(ShortUrlService shortUrlService) {
		this.shortUrlService = shortUrlService;
	}

	public void testCreate() {
		ShortUrl shortUrl = null;
		String content = "测试  http://static.youku.com/v1.0.0091/v/swf/qplayer.swf?VideoIDS=XMTQyODMyMDg4&embedid=MTIzLjEyMS4yMjEuMTA3AjM1NzA4MDIyAnd3dy41Z21lLmNvbQIvc3BhY2UtMjUtZG8tYmxvZy1pZC05MzM4Ni5odG1s&showAd=0 http://www.163.com www.163.com http://www.bosee.cn/yuanwei http://tubewall.webuda.com/twitesee/";
		List<String> urllist = DataUtil.parseUrl(content);
		for (String url : urllist) {
			shortUrl = this.shortUrlService.createShortUrl(url);
			P.println(shortUrl.getShortKey());
		}
		this.commit();
	}
}