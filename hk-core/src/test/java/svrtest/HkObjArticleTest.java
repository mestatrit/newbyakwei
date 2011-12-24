package svrtest;

import com.hk.bean.HkObjArticle;
import com.hk.svr.HkObjArticleService;

public class HkObjArticleTest extends HkServiceTest {
	private HkObjArticleService hkObjArticleService;

	public void setHkObjArticleService(HkObjArticleService hkObjArticleService) {
		this.hkObjArticleService = hkObjArticleService;
	}

	public void testcreateHkObjArticle() {
		long userId = 1;
		long hkObjId = 1;
		String title = "哈哈哈";// 文章标题
		String url = "http://www.akwei.com";// 文章url
		byte authorflg = 1;// 原创标志0:非原创 1原创
		String author = "akwei";// 作者姓名
		String email = "ak478288@163.com";// 可以联系的email
		String tel = "909090";// 联系电话
		String blog = "http://www.akwei.com";// blog地址
		HkObjArticle o = new HkObjArticle();
		o.setHkObjId(hkObjId);
		o.setUrl(url);
		o.setUserId(userId);
		o.setTitle(title);
		o.setAuthor(author);
		o.setAuthorflg(authorflg);
		o.setEmail(email);
		o.setTel(tel);
		o.setBlog(blog);
		this.hkObjArticleService.createHkObjArticle(o);
		HkObjArticle oo = this.hkObjArticleService.getHkObjArticle(o
				.getArticleId());
		assertNotNull(oo);
	}
}