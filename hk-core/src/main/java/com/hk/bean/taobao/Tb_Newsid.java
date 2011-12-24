package com.hk.bean.taobao;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "tb_newsid")
public class Tb_Newsid {

	public Tb_Newsid() {
	}

	public Tb_Newsid(Date create_time) {
		this.setCreate_time(create_time);
	}

	@Id
	private long nid;

	@Column
	private Date create_time;

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
}