package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 文章标签表
 * 
 * @author akwei
 */
@Table(name = "cmparticletag")
public class CmpArticleTag {

	public static final byte PINKFLG_N = 0;

	public static final byte PINKFLG_Y = 1;

	@Id
	private long tagId;

	@Column
	private long companyId;

	@Column
	private String name;

	/**
	 * 推荐标示0:为推荐,1:推荐
	 */
	@Column
	private byte pinkflg;

	@Column
	private Date pinktime;

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getPinkflg() {
		return pinkflg;
	}

	public void setPinkflg(byte pinkflg) {
		this.pinkflg = pinkflg;
	}

	public boolean isPink() {
		if (this.pinkflg == PINKFLG_Y) {
			return true;
		}
		return false;
	}

	public Date getPinktime() {
		return pinktime;
	}

	public void setPinktime(Date pinktime) {
		this.pinktime = pinktime;
	}
}