package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "tb_user_ask")
public class Tb_User_Ask {

	@Id
	private long oid;

	@Column
	private long aid;

	@Column
	private long userid;

	/**
	 * 用户提出的问题
	 */
	public static final byte ASKFLG_ASK = 0;

	/**
	 * 用户回答的问题
	 */
	public static final byte ASKFLG_ANSWER = 1;

	/**
	 * 数据逻辑类型
	 */
	@Column
	private byte askflg;

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

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public byte getAskflg() {
		return askflg;
	}

	public void setAskflg(byte askflg) {
		this.askflg = askflg;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}
}