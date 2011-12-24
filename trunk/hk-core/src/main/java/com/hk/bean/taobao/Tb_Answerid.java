package com.hk.bean.taobao;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "tb_answerid")
public class Tb_Answerid {

	@Id
	private long ansid;

	@Column
	private Date create_time;

	public Tb_Answerid() {
	}

	public Tb_Answerid(Date createTime) {
		create_time = createTime;
	}

	public void setAnsid(long ansid) {
		this.ansid = ansid;
	}

	public long getAnsid() {
		return ansid;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}
}