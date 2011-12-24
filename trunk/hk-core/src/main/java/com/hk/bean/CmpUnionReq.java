package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Table;

/**
 * 请求
 * 
 * @author akwei
 */
@Table(name = "cmpunionreq", id = "reqid")
public class CmpUnionReq {
	public static final byte DEALFLG_N = 0;

	public static final byte DEALFLG_Y = 1;

	private long reqid;

	private long uid;

	private long objId;

	private int reqflg;

	private byte dealflg;

	private Date createTime;

	private String data;

	public long getReqid() {
		return reqid;
	}

	public void setReqid(long reqid) {
		this.reqid = reqid;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getObjId() {
		return objId;
	}

	public void setObjId(long objId) {
		this.objId = objId;
	}

	public int getReqflg() {
		return reqflg;
	}

	public void setReqflg(int reqflg) {
		this.reqflg = reqflg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte getDealflg() {
		return dealflg;
	}

	public void setDealflg(byte dealflg) {
		this.dealflg = dealflg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}