package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;

/**
 * 足迹，宝贝的酷币数量增加货或减少的日志记录
 * 
 * @author akwei
 */
@Table(name = "hkobjorderhkblog", id = "oid")
public class HkObjOrderHkbLog {
	private long oid;

	private long hkObjId;

	private byte addflg;// 0:减少 1:增加

	private int hkb;// 增加或减少的酷币数量

	private Date createTime;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getHkObjId() {
		return hkObjId;
	}

	public void setHkObjId(long hkObjId) {
		this.hkObjId = hkObjId;
	}

	public byte getAddflg() {
		return addflg;
	}

	public void setAddflg(byte addflg) {
		this.addflg = addflg;
	}

	public int getHkb() {
		return hkb;
	}

	public void setHkb(int hkb) {
		this.hkb = hkb;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}