package com.hk.bean.taobao;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 推荐的商品
 * 
 * @author akwei
 */
@Table(name = "tb_cmditem")
public class Tb_CmdItem {

	@Id
	private long oid;

	@Column
	private long itemid;

	@Column
	private Date create_time;

	private Tb_Item tbItem;

	public void setTbItem(Tb_Item tbItem) {
		this.tbItem = tbItem;
	}

	public Tb_Item getTbItem() {
		return tbItem;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getItemid() {
		return itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}
}