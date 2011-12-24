package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 商品分类
 * 
 * @author akwei
 */
@Table(name = "tb_item_cat")
public class Tb_Item_Cat {

	/**
	 * id
	 */
	@Id
	private long cid;

	/**
	 * 父分类id
	 */
	@Column
	private long parent_cid;

	/**
	 * 名称
	 */
	@Column
	private String name;

	public static final byte PARENTFLG_N = 0;

	public static final byte PARENTFLG_Y = 1;

	/**
	 * 是否是父分类,0:不是(没有子分类),1:是(有子分类)
	 */
	@Column
	private byte parentflg;

	public static final byte STATUS_NORMAL = 0;

	public static final byte STATUS_DELETE = 1;

	/**
	 * 0:正常(对应淘宝normal),1:删除(对应淘宝delete)
	 */
	@Column
	private byte status;

	/**
	 * 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
	 */
	@Column
	private int sort_order;

	public static final byte CHILD_UPDATE_N = 0;

	public static final byte CHILD_UPDATE_Y = 1;

	/**
	 * 下一级子分类是否更新完毕
	 */
	@Column
	private byte child_update;

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

	public byte getParentflg() {
		return parentflg;
	}

	public void setParentflg(byte parentflg) {
		this.parentflg = parentflg;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public int getSort_order() {
		return sort_order;
	}

	public void setSort_order(int sortOrder) {
		sort_order = sortOrder;
	}

	public byte getChild_update() {
		return child_update;
	}

	public void setChild_update(byte childUpdate) {
		child_update = childUpdate;
	}
}