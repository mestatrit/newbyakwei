package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户粉丝
 * 
 * @author akwei
 */
@Table(name = "tb_followed")
public class Tb_Followed {

	@Id
	private long oid;

	@Column
	private long userid;

	@Column
	private long fansid;

	private Tb_User fans;

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

	public long getFansid() {
		return fansid;
	}

	public void setFansid(long fansid) {
		this.fansid = fansid;
	}

	public Tb_User getFans() {
		return fans;
	}

	public void setFans(Tb_User fans) {
		this.fans = fans;
	}
}
