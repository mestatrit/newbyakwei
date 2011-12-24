package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.Err;

/**
 * 用户对商品的评分
 * 
 * @author akwei
 */
@Table(name = "tb_item_score")
public class Tb_Item_Score {

	@Id
	private long oid;

	@Column
	private long itemid;

	@Column
	private long userid;

	@Column
	private int score;

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

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public int validate() {
		if (this.score < 0 || this.score > 5) {
			this.score = 0;
		}
		return Err.SUCCESS;
	}
}