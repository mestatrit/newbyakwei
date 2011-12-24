package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 帖子回复
 * 
 * @author akwei
 */
@Table(name = "cmpbbsreply")
public class CmpBbsReply {

	public CmpBbsReply() {
	}

	public CmpBbsReply(CmpBbsReplyDel cmpBbsReplyDel) {
		this.setReplyId(cmpBbsReplyDel.getReplyId());
		this.setUserId(cmpBbsReplyDel.getUserId());
		this.setBbsId(cmpBbsReplyDel.getBbsId());
		this.setContent(cmpBbsReplyDel.getContent());
		this.setCreateTime(cmpBbsReplyDel.getCreateTime());
		this.setPhotoId(cmpBbsReplyDel.getPhotoId());
		this.setPicpath(cmpBbsReplyDel.getPicpath());
		this.setReplyUserId(cmpBbsReplyDel.getReplyUserId());
		this.setIp(cmpBbsReplyDel.getIp());
		this.setCompanyId(cmpBbsReplyDel.getCompanyId());
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

	private User user;

	private User replyUser;

	public void setReplyUser(User replyUser) {
		this.replyUser = replyUser;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getReplyUser() {
		return replyUser;
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

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public int validate() {
		String s = DataUtil.toText(this.content);
		if (DataUtil.isEmpty(s) || s.length() > 5000) {
			return Err.CMPBBSREPLY_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}

	public String getPic800Url() {
		return ImageConfig.getPic800Url(this.picpath);
	}

	public String getPic600Url() {
		return ImageConfig.getPic600Url(this.picpath);
	}

	public String getPic120Url() {
		return ImageConfig.getPic120Url(this.picpath);
	}
}