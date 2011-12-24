package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 专栏作家，后台审核后 成功
 * 
 * @author akwei
 */
@Table(name = "authorapply", id = "oid")
public class AuthorApply {
	public static final byte CHECKFLG_N = 0;

	public static final byte CHECKFLG_FAIL = 1;

	public static final byte CHECKFLG_Y = 2;

	private long oid;

	private long userId;

	private String name;// 姓名

	private String tel;// 也可能是手机

	private String email;

	private String blog;// blog地址

	private byte checkflg;// 0:未审核 1:不通过 2:通过

	private Date createTime;

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
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
		String s = DataUtil.toText(this.name);
		if (DataUtil.isEmpty(s) || s.length() > 20) {
			return Err.AUTHORAPPLY_NAME_ERROR;
		}
		s = DataUtil.toText(this.email);
		if (DataUtil.isEmpty(s) || s.length() > 50) {
			return Err.AUTHORAPPLY_EMAIL_ERROR;
		}
		s = DataUtil.toText(this.blog);
		if (!DataUtil.isEmpty(s) && s.length() > 100) {
			return Err.AUTHORAPPLY_BLOG_ERROR;
		}
		s = DataUtil.toText(this.tel);
		if (DataUtil.isEmpty(s) || s.length() > 30) {
			return Err.AUTHORAPPLY_TEL_ERROR;
		}
		s = DataUtil.toText(this.content);
		if (DataUtil.isEmpty(s) || s.length() > 500) {
			return Err.AUTHORAPPLY_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}
}