package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 企业论坛
 * 
 * @author akwei
 */
@Table(name = "cmpbbs")
public class CmpBbs {

	public CmpBbs() {
	}

	public CmpBbs(CmpBbsDel cmpBbsDel) {
		this.setBbsId(cmpBbsDel.getBbsId());
		this.setKindId(cmpBbsDel.getKindId());
		this.setUserId(cmpBbsDel.getUserId());
		this.setTitle(cmpBbsDel.getTitle());
		this.setLastReplyUserId(cmpBbsDel.getLastReplyUserId());
		this.setLastReplyTime(cmpBbsDel.getLastReplyTime());
		this.setCreateTime(cmpBbsDel.getCreateTime());
		this.setPicpath(cmpBbsDel.getPicpath());
		this.setReplyCount(cmpBbsDel.getReplyCount());
		this.setViewCount(cmpBbsDel.getViewCount());
		this.setIp(cmpBbsDel.getIp());
		this.setPhotoId(cmpBbsDel.getPhotoId());
		this.setCompanyId(cmpBbsDel.getCompanyId());
		this.setCmppinkTime(this.getCreateTime());
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

	/**
	 * 图片id，如果有图片
	 */
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
	 * 发布ip地址
	 */
	@Column
	private String ip;

	@Column
	private byte cmppink;

	@Column
	private Date cmppinkTime;

	private User user;

	private User lastReplyUser;

	public void setUser(User user) {
		this.user = user;
	}

	public void setLastReplyUser(User lastReplyUser) {
		this.lastReplyUser = lastReplyUser;
	}

	public User getUser() {
		return user;
	}

	public User getLastReplyUser() {
		return lastReplyUser;
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

	public int validate() {
		String s = DataUtil.toTextRow(this.title);
		if (s == null || s.length() > 30) {
			return Err.CMPBBS_TITLE_ERROR;
		}
		if (this.kindId <= 0) {
			return Err.CMPBBS_KINDID_ERROR;
		}
		return Err.SUCCESS;
	}

	public byte getCmppink() {
		return cmppink;
	}

	public void setCmppink(byte cmppink) {
		this.cmppink = cmppink;
	}

	public Date getCmppinkTime() {
		return cmppinkTime;
	}

	public void setCmppinkTime(Date cmppinkTime) {
		this.cmppinkTime = cmppinkTime;
	}

	public String getPic800Url() {
		return ImageConfig.getPic800Url(this.picpath);
	}

	public String getPic600Url() {
		return ImageConfig.getPic600Url(this.picpath);
	}

	public String getPic240Url() {
		return ImageConfig.getPic240Url(this.picpath);
	}

	public String getPic120Url() {
		return ImageConfig.getPic120Url(this.picpath);
	}
}