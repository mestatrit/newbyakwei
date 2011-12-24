package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户与商品的关系表
 * 
 * @author akwei
 */
@Table(name = "tb_item_user_ref")
public class Tb_Item_User_Ref {

	@Id
	private long oid;

	@Column
	private long userid;

	@Column
	private long itemid;

	/**
	 * 用户推荐的
	 */
	public static final byte FLG_CMD = 1;

	/**
	 * 用户想要的
	 */
	public static final byte FLG_WANT = 2;

	/**
	 * 用户拥有的
	 */
	public static final byte FLG_HOLD = 3;

	/**
	 * 用户点评的
	 */
	public static final byte FLG_CMT = 4;

	@Column
	private byte flg;

	@Column
	private long cmtid;

	private Tb_Item tbItem;

	private Tb_User tbUser;

	private Tb_Item_Cmt tbItemCmt;

	public void setTbUser(Tb_User tbUser) {
		this.tbUser = tbUser;
	}

	public Tb_User getTbUser() {
		return tbUser;
	}

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

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getItemid() {
		return itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	public byte getFlg() {
		return flg;
	}

	public void setFlg(byte flg) {
		this.flg = flg;
	}

	public boolean isItemOwnner() {
		if (this.flg == FLG_HOLD) {
			return true;
		}
		return false;
	}

	public boolean isItemCmt() {
		if (this.flg == FLG_CMT) {
			return true;
		}
		return false;
	}

	public boolean isItemWanter() {
		if (this.flg == FLG_WANT) {
			return true;
		}
		return false;
	}

	public long getCmtid() {
		return cmtid;
	}

	public void setCmtid(long cmtid) {
		this.cmtid = cmtid;
	}

	public Tb_Item_Cmt getTbItemCmt() {
		return tbItemCmt;
	}

	public void setTbItemCmt(Tb_Item_Cmt tbItemCmt) {
		this.tbItemCmt = tbItemCmt;
	}
}