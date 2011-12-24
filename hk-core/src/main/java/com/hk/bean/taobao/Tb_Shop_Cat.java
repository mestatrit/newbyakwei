package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 店铺分类
 * 
 * @author akwei
 */
@Table(name = "tb_shop_cat")
public class Tb_Shop_Cat {

	@Id
	private long cid;

	@Column
	private String name;

	@Column
	private long parent_cid;

	public static final byte PARENTFLG_N = 0;

	public static final byte PARENTFLG_Y = 1;

	@Column
	private byte parentflg;

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getParent_cid() {
		return parent_cid;
	}

	public void setParent_cid(long parentCid) {
		parent_cid = parentCid;
	}

	public byte getParentflg() {
		return parentflg;
	}

	public void setParentflg(byte parentflg) {
		this.parentflg = parentflg;
	}
}