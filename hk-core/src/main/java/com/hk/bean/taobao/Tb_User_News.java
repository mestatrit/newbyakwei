package com.hk.bean.taobao;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "tb_user_news")
public class Tb_User_News {

	@Id
	private long nid;

	@Column
	private long userid;

	@Column
	private Date create_time;

	private Tb_News tbNews;

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
}