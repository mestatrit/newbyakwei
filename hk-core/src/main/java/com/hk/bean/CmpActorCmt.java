package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 对美发师点评
 * 
 * @author akwei
 */
@Table(name = "cmpactorcmt")
public class CmpActorCmt {

	@Id
	private long cmtId;

	@Column
	private long userId;

	@Column
	private long actorId;

	@Column
	private long companyId;

	@Column
	private String content;

	@Column
	private int score;

	@Column
	private Date createTime;

	private CmpActor cmpActor;

	private User user;

	public long getCmtId() {
		return cmtId;
	}

	public void setCmtId(long cmtId) {
		this.cmtId = cmtId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getActorId() {
		return actorId;
	}

	public void setActorId(long actorId) {
		this.actorId = actorId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public CmpActor getCmpActor() {
		return cmpActor;
	}

	public void setCmpActor(CmpActor cmpActor) {
		this.cmpActor = cmpActor;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.content, true, 1000)) {
			return Err.CMPACTORCMT_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}