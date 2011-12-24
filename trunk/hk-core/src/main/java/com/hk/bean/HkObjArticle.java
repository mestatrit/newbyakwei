package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "hkobjarticle", id = "articleid")
public class HkObjArticle {
	public static final byte CHECKFLG_N = 0;

	public static final byte CHECKFLG_FAIL = 1;

	public static final byte CHECKFLG_Y = 2;

	public static final byte AUTHORFLG_N = 0;

	public static final byte AUTHORFLG_Y = 1;

	private long articleId;

	private long userId;

	private long hkObjId;

	private String title;// 文章标题

	private String url;// 文章url

	private byte authorflg;// 原创标志0:非原创 1原创

	private byte checkflg;// 审核标志 0:未审核 1:审核不通过 2:审核通过

	private Date createTime;// 创建时间

	private String author;// 作者姓名

	private String email;// 可以联系的email

	private String tel;// 联系电话

	private String blog;// blog地址

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getHkObjId() {
		return hkObjId;
	}

	public void setHkObjId(long hkObjId) {
		this.hkObjId = hkObjId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public byte getAuthorflg() {
		return authorflg;
	}

	public void setAuthorflg(byte authorflg) {
		this.authorflg = authorflg;
	}

	public byte getCheckflg() {
		return checkflg;
	}

	public void setCheckflg(byte checkflg) {
		this.checkflg = checkflg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isNotCheck() {
		if (this.checkflg == CHECKFLG_N) {
			return true;
		}
		return false;
	}

	public boolean isCheckFail() {
		if (this.checkflg == CHECKFLG_FAIL) {
			return true;
		}
		return false;
	}

	public boolean isCheckOk() {
		if (this.checkflg == CHECKFLG_Y) {
			return true;
		}
		return false;
	}

	public int validate() {
		String s = DataUtil.toText(this.title);
		if (DataUtil.isEmpty(s) || s.length() > 20) {
			return Err.HKOBJARTICLE_TITLE_ERROR;
		}
		s = DataUtil.toText(this.url);
		if (DataUtil.isEmpty(s) || s.length() > 200) {
			return Err.HKOBJARTICLE_URL_ERROR;
		}
		s = DataUtil.toText(this.author);
		if (!DataUtil.isEmpty(s) && s.length() > 20) {
			return Err.HKOBJARTICLE_AUTHOR_ERROR;
		}
		s = DataUtil.toText(this.email);
		if (DataUtil.isEmpty(s) || s.length() > 50) {
			return Err.HKOBJARTICLE_EMAIL_ERROR;
		}
		s = DataUtil.toText(this.blog);
		if (!DataUtil.isEmpty(s) && s.length() > 100) {
			return Err.HKOBJARTICLE_BLOG_ERROR;
		}
		s = DataUtil.toText(this.tel);
		if (!DataUtil.isEmpty(s) && s.length() > 30) {
			return Err.HKOBJARTICLE_TEL_ERROR;
		}
		if (this.authorflg != HkObjArticle.AUTHORFLG_N
				&& this.authorflg != HkObjArticle.AUTHORFLG_Y) {
			return Err.HKOBJARTICLE_AUTHORFLG_ERROR;
		}
		if (this.userId < 1) {
			return Err.USERID_ERROR;
		}
		if (this.hkObjId < 1) {
			return Err.HKOBJID_ERROR;
		}
		return Err.SUCCESS;
	}
}