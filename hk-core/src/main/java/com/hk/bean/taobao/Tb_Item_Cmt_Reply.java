package com.hk.bean.taobao;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 点评的回复
 * 
 * @author akwei
 */
@Table(name = "tb_item_cmt_reply")
public class Tb_Item_Cmt_Reply {

	@Id
	private long replyid;

	@Column
	private long itemid;

	@Column
	private long userid;

	@Column
	private long cmtid;

	@Column
	private String content;

	@Column
	private Date create_time;

	private Tb_User tbUser;

	public void setTbUser(Tb_User tbUser) {
		this.tbUser = tbUser;
	}

	public Tb_User getTbUser() {
		return tbUser;
	}

	public long getReplyid() {
		return replyid;
	}

	public void setReplyid(long replyid) {
		this.replyid = replyid;
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

	public long getCmtid() {
		return cmtid;
	}

	public void setCmtid(long cmtid) {
		this.cmtid = cmtid;
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

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.content, true, 500)) {
			return Err.TB_ITEM_CMT_REPLY_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}
}