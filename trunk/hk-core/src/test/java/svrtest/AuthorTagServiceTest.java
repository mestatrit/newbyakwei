package svrtest;

import com.hk.bean.AuthorApply;
import com.hk.bean.AuthorTag;
import com.hk.bean.UserAuthorTag;
import com.hk.svr.AuthorTagService;

public class AuthorTagServiceTest extends HkServiceTest {
	private AuthorTagService authorTagService;

	public void setAuthorTagService(AuthorTagService authorTagService) {
		this.authorTagService = authorTagService;
	}

	public void ttestcreateUserAuthorTag() {
		long userId = 1;
		String name = "程序员";
		long tagId = this.authorTagService.createUserAuthorTag(userId, name);
		AuthorTag authorTag = this.authorTagService.getAuthorTag(tagId);
		assertNotNull(authorTag);
		UserAuthorTag userAuthorTag = this.authorTagService
				.getUserAuthorTagByTagIdAndUserId(tagId, userId);
		assertNotNull(userAuthorTag);
		userId = 2;
		long tagId2 = this.authorTagService.createUserAuthorTag(userId, name);
		assertSame(tagId, tagId2);
		userAuthorTag = this.authorTagService.getUserAuthorTagByTagIdAndUserId(
				tagId2, userId);
		assertNotNull(userAuthorTag);
	}

	public void testcreateAuthorApply() {
		long userId = 1;
		String name = "akwei";// 姓名
		String tel = "12334535";// 也可能是手机
		String email = "ak478288@163.com";
		String blog = null;// blog地址
		AuthorApply o = new AuthorApply();
		o.setUserId(userId);
		o.setName(name);
		o.setTel(tel);
		o.setEmail(email);
		o.setBlog(blog);
		this.authorTagService.createAuthorApply(o);
		AuthorApply apply = this.authorTagService
				.getAuthorApplyByUserId(userId);
		assertNotNull(apply);
	}
}