package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 企业论坛
 * 
 * @author akwei
 */
@Table(name = "cmpbbsdel")
public class CmpBbsDel {

	public CmpBbsDel() {
	}

	public CmpBbsDel(CmpBbs cmpBbs, CmpBbsContent cmpBbsContent) {
		this.setBbsId(cmpBbs.getBbsId());
		this.setKindId(cmpBbs.getKindId());
		this.setUserId(cmpBbs.getUserId());
		this.setTitle(cmpBbs.getTitle());
		this.setLastReplyUserId(cmpBbs.getLastReplyUserId());
		this.setLastReplyTime(cmpBbs.getLastReplyTime());
		this.setCreateTime(cmpBbs.getCreateTime());
		this.setPicpath(cmpBbs.getPicpath());
		this.setReplyCount(cmpBbs.getReplyCount());
		this.setViewCount(cmpBbs.getViewCount());
		this.setIp(cmpBbs.getIp());
		this.setPhotoId(cmpBbs.getPhotoId());
		this.setCompanyId(cmpBbs.getCompanyId());
		this.setContent(cmpBbsContent.getContent());
	}

	/**
	 * 帖子id
	 */
	@Id
	private long bbsId;

	@Column
	private long companyId;

	/**
	 * 帖子分类
	 */
	@Column
	private long kindId;

	/**
	 * 发帖人id
	 */
	@Column
	private long userId;

	/**
	 * 帖子标题
	 */
	@Column
	private String title;

	/**
	 * 最后回复人的id
	 */
	@Column
	private long lastReplyUserId;

	/**
	 * 发表时间
	 */
	@Column
	private Date createTime;

	/**
	 * 最后回复时间
	 */
	@Column
	private Date lastReplyTime;

	@Column
	private long photoId;

	/**
	 * 如果有图片就存储图片路径
	 */
	@Column
	private String picpath;

	/**
	 * 回复数量
	 */
	@Column
	private int replyCount;

	/**
	 * 浏览数量
	 */
	@Column
	private int viewCount;

	/**
	 * 话题内容
	 */
	@Column
	private String content;

	/**
	 * 发布ip地址
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

	private User opuser;

	public void setOpuser(User opuser) {
		this.opuser = opuser;
	}

	public User getOpuser() {
		return opuser;
	}

	public long getBbsId() {
		return bbsId;
	}

	public void setBbsId(long bbsId) {
		this.bbsId = bbsId;
	}

	public long getKindId() {
		return kindId;
	}

	public void setKindId(long kindId) {
		this.kindId = kindId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getLastReplyUserId() {
		return lastReplyUserId;
	}

	public void setLastReplyUserId(long lastReplyUserId) {
		this.lastReplyUserId = lastReplyUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastReplyTime() {
		return lastReplyTime;
	}

	public void setLastReplyTime(Date lastReplyTime) {
		this.lastReplyTime = lastReplyTime;
	}

	public String getPicpath() {
		return picpath;
	}

	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
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

	public long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}