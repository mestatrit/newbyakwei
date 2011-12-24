package com.hk.bean.taobao;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

@Table(name = "tb_ask")
public class Tb_Ask {

	@Id
	private long aid;

	@Column
	private long userid;

	/**
	 * 最佳答案id
	 */
	@Column
	private long ansid;

	@Column
	private long cid;

	@Column
	private long parent_cid;

	@Column
	private String title;

	@Column
	private String content;

	@Column
	private Date create_time;

	/**
	 * 答案数量
	 */
	@Column
	private int answer_num;

	/**
	 * 未解决
	 */
	public static final byte RESOLVE_STATUS_N = 0;

	/**
	 * 已解决
	 */
	public static final byte RESOLVE_STATUS_Y = 1;

	/**
	 * 是否已经解决
	 */
	@Column
	private byte resolve_status;

	private Tb_User tbUser;

	/**
	 * 最佳答案
	 */
	private Tb_Answer tbAnswer;

	public void setTbAnswer(Tb_Answer tbAnswer) {
		this.tbAnswer = tbAnswer;
	}

	public Tb_Answer getTbAnswer() {
		return tbAnswer;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getAnsid() {
		return ansid;
	}

	public void setAnsid(long ansid) {
		this.ansid = ansid;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Tb_User getTbUser() {
		return tbUser;
	}

	public void setTbUser(Tb_User tbUser) {
		this.tbUser = tbUser;
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}

	public int getAnswer_num() {
		return answer_num;
	}

	public void setAnswer_num(int answerNum) {
		answer_num = answerNum;
	}

	public void setResolve_status(byte resolveStatus) {
		resolve_status = resolveStatus;
	}

	public byte getResolve_status() {
		return resolve_status;
	}

	public int validate() {
		if (this.cid == 0) {
			return Err.TB_ASK_CID_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.title, true, 50)) {
			return Err.TB_ASK_TITLE_ERROR;
		}
		if (!HkValidate.validateLength(this.content, true, 500)) {
			return Err.TB_ASK_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}

	public boolean isResolved() {
		if (this.resolve_status == RESOLVE_STATUS_Y) {
			return true;
		}
		return false;
	}

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
}