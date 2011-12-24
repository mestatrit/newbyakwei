package com.hk.bean.taobao;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 商品推荐点评
 * 
 * @author akwei
 */
@Table(name = "tb_item_cmt")
public class Tb_Item_Cmt {

	@Id
	private long cmtid;

	@Column
	private long itemid;

	@Column
	private long sid;

	@Column
	private long userid;

	@Column
	private int score;

	@Column
	private String content;

	@Column
	private Date create_time;

	@Column
	private int reply_count;

	private Tb_User tbUser;

	private Tb_Item tbItem;

	public void setTbItem(Tb_Item tbItem) {
		this.tbItem = tbItem;
	}

	public Tb_Item getTbItem() {
		return tbItem;
	}

	public void setTbUser(Tb_User tbUser) {
		this.tbUser = tbUser;
	}

	public Tb_User getTbUser() {
		return tbUser;
	}

	public long getCmtid() {
		return cmtid;
	}

	public void setCmtid(long cmtid) {
		this.cmtid = cmtid;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getReply_count() {
		return reply_count;
	}

	public void setReply_count(int replyCount) {
		reply_count = replyCount;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.content, true, 500)) {
			return Err.TB_ITEM_CMT_CONTENT_ERROR;
		}
		if (this.score < 0 || this.score > 5) {
			this.score = 0;
		}
		return Err.SUCCESS;
	}

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}
}