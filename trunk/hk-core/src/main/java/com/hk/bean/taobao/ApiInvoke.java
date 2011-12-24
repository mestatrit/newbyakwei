package com.hk.bean.taobao;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "tb_api_invoke")
public class ApiInvoke {

	public static final byte TESTFLG_TEST = 1;

	public static final byte TESTFLG_PUBLICTEST = 2;

	public static final byte TESTFLG_PUBLIC = 3;

	@Id
	private int oid;

	@Column
	private int invoke_count;

	@Column
	private Date time;

	@Column
	private byte testflg;

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getInvoke_count() {
		return invoke_count;
	}

	public void setInvoke_count(int invokeCount) {
		invoke_count = invokeCount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public byte getTestflg() {
		return testflg;
	}

	public void setTestflg(byte testflg) {
		this.testflg = testflg;
	}
}