package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "tb_domain_item")
public class Tb_Domain_Item {

	@Id
	private long oid;

	@Column
	private int domainid;

	@Column
	private long itemid;

	@Column
	private int hkscore;

	@Column
	private int huo_status;

	@Column
	private long volume;

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

	public void setDomainid(int domainid) {
		this.domainid = domainid;
	}

	public int getDomainid() {
		return domainid;
	}

	public long getItemid() {
		return itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	public int getHkscore() {
		return hkscore;
	}

	public void setHkscore(int hkscore) {
		this.hkscore = hkscore;
	}

	public void setHuo_status(int huoStatus) {
		huo_status = huoStatus;
	}

	public int getHuo_status() {
		return huo_status;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}
}