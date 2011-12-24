package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "tb_answer_status")
public class Tb_Answer_Status {

	/**
	 * 支持
	 */
	public static final byte ANS_STATUS_SUPPORT = 0;

	/**
	 * 反对
	 */
	public static final byte ANS_STATUS_DISCMD = 1;

	@Id
	private long oid;

	@Column
	private long ansid;

	@Column
	private long userid;

	/**
	 * 对答案的支持或反对的状态
	 */
	@Column
	private byte ans_status;

	@Column
	private long aid;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getAnsid() {
		return ansid;
	}

	public void setAnsid(long ansid) {
		this.ansid = ansid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public byte getAns_status() {
		return ans_status;
	}

	public void setAns_status(byte ansStatus) {
		ans_status = ansStatus;
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}
}