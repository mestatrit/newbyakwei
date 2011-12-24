package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 商品与分类的关系表
 * 
 * @author akwei
 */
@Table(name = "tb_item_cat_ref")
public class Tb_Item_Cat_Ref {

	@Id
	private long oid;

	@Column
	private long cid;

	@Column
	private long itemid;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public long getItemid() {
		return itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}
}