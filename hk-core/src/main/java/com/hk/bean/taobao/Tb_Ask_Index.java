package com.hk.bean.taobao;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "tb_ask_index")
public class Tb_Ask_Index {

	public static final byte FLG_ALLASK = 0;

	public static final byte FLG_RESOLVEASK = 1;

	public static final byte FLG_ALLASK_DEL = 2;

	@Id
	private long oid;

	@Column
	private long aid;

	@Column
	private byte flg;

	@Column
	private Date create_time;

	private Tb_Ask tbAsk;

	public void setTbAsk(Tb_Ask tbAsk) {
		this.tbAsk = tbAsk;
	}

	public Tb_Ask getTbAsk() {
		return tbAsk;
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public byte getFlg() {
		return flg;
	}

	public void setFlg(byte flg) {
		this.flg = flg;
	}
}