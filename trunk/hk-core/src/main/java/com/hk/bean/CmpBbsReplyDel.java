package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 帖子回复
 * 
 * @author akwei
 */
@Table(name = "cmpbbsreplydel")
public class CmpBbsReplyDel {

	public CmpBbsReplyDel() {
	}

	public CmpBbsReplyDel(CmpBbsReply cmpBbsReply) {
		this.setReplyId(cmpBbsReply.getReplyId());
		this.setUserId(cmpBbsReply.getUserId());
		this.setBbsId(cmpBbsReply.getBbsId());
		this.setContent(cmpBbsReply.getContent());
		this.setCreateTime(cmpBbsReply.getCreateTime());
		this.setPhotoId(cmpBbsReply.getPhotoId());
		this.setPicpath(cmpBbsReply.getPicpath());
		this.setReplyUserId(cmpBbsReply.getReplyUserId());
		this.setIp(cmpBbsReply.getIp());
		this.setCompanyId(cmpBbsReply.getCompanyId());
	}

	/**
	 * 回复id
	 */
	@Id
	private long replyId;

	@Column
	private long companyId;

	/**
	 * 回复人id
	 */
	@Column
	private long userId;

	/**
	 * 帖子id
	 */
	@Column
	private long bbsId;

	/**
	 * 回复内容
	 */
	@Column
	private String content;

	/**
	 * 回复时间
	 */
	@Column
	private Date createTime;

	/**
	 * 图片id，如果有图片
	 */
	@Column
	private long photoId;

	/**
	 * 图片路径
	 */
	@Column
	private String picpath;

	/**
	 * 回复某人id
	 */
	@Column
	private long replyUserId;

	/**
	 * 回复ip地址
	 */
	@Column
	private String ip;

	/**
	 * 管理员id
	 */
	@Column
	private long opuserId;

	/**
	 * 管理员操作时间
	 */
	@Column
	private Date optime;

	private User user;

	private User opuser;

	public void setOpuser(User opuser) {
		this.opuser = opuser;
	}

	public User getOpuser() {
		return opuser;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public long getReplyId() {
		return replyId;
	}

	public void setReplyId(long replyId) {
		this.replyId = replyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getBbsId() {
		return bbsId;
	}

	public void setBbsId(long bbsId) {
		this.bbsId = bbsId;
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

	public long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}

	public String getPicpath() {
		return picpath;
	}

	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}

	public long getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(long replyUserId) {
		this.replyUserId = replyUserId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getOpuserId() {
		return opuserId;
	}

	public void setOpuserId(long opuserId) {
		this.opuserId = opuserId;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}