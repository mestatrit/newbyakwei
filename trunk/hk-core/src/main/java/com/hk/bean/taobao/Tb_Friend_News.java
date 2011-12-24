package com.hk.bean.taobao;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "tb_friend_news")
public class Tb_Friend_News {

	@Id
	private long oid;

	@Column
	private long userid;

	/**
	 * 动态的所有者
	 */
	@Column
	private long news_userid;

	@Column
	private long nid;

	@Column
	private Date create_time;

	private Tb_News tbNews;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getNid() {
		return nid;
	}

	public void setNid(long nid) {
		this.nid = nid;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public Tb_News getTbNews() {
		return tbNews;
	}

	public void setTbNews(Tb_News tbNews) {
		this.tbNews = tbNews;
	}

	public long getNews_userid() {
		return news_userid;
	}

	public void setNews_userid(long newsUserid) {
		news_userid = newsUserid;
	}
}
