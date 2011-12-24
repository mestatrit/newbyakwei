package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

@Table(name = "tb_ask_cat")
public class Tb_Ask_Cat {

	@Id
	private long cid;

	@Column
	private long parent_cid;

	/**
	 * 是否是父节点
	 */
	@Column
	private byte parentflg;

	@Column
	private String name;

	@Column
	private int order_num;

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public long getParent_cid() {
		return parent_cid;
	}

	public void setParent_cid(long parentCid) {
		parent_cid = parentCid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder_num() {
		return order_num;
	}

	public void setOrder_num(int orderNum) {
		order_num = orderNum;
	}

	public byte getParentflg() {
		return parentflg;
	}

	public void setParentflg(byte parentflg) {
		this.parentflg = parentflg;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 50)) {
			return Err.TB_ASK_CAT_NAME_ERROR;
		}
		return Err.SUCCESS;
	}

	public static final byte PARENTFLG_N = 0;

	public static final byte PARENTFLG_Y = 1;

	public boolean isParent() {
		if (this.parentflg == PARENTFLG_Y) {
			return true;
		}
		return false;
	}
}