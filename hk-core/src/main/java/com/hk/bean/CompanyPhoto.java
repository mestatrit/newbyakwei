package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.ImageConfig;

@Table(name = "companyphoto", id = "photoid")
public class CompanyPhoto {

	public static final byte PINKFLG_N = 0;

	public static final byte PINKFLG_Y = 1;

	public CompanyPhoto() {
	}

	public CompanyPhoto(Photo photo) {
		this.photoId = photo.getPhotoId();
		this.name = photo.getName();
		this.path = photo.getPath();
		this.createTime = photo.getCreateTime();
		this.userId = photo.getUserId();
	}

	@Id
	private long photoId;

	@Column
	private long companyId;

	@Column
	private long userId;

	@Column
	private String path;

	@Column
	private Date createTime;

	@Column
	private String name;

	@Column
	private byte pinkflg;// 0:普通 1:精华

	/**
	 * 投票数量
	 */
	@Column
	private int voteCount;

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public boolean isPink() {
		if (this.pinkflg == PINKFLG_Y) {
			return true;
		}
		return false;
	}

	public byte getPinkflg() {
		return pinkflg;
	}

	public void setPinkflg(byte pinkflg) {
		this.pinkflg = pinkflg;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPic60() {
		return ImageConfig.getPic60Url(path);
	}

	public String getPic240() {
		return ImageConfig.getPic240Url(path);
	}

	public String getPic320() {
		return ImageConfig.getPic320Url(path);
	}

	/**
	 * 显示的为600的图片
	 * 
	 * @return
	 *         2010-8-16
	 */
	public String getPic640() {
		return ImageConfig.getPic600Url(path);
	}

	public String getPic600() {
		return ImageConfig.getPic600Url(path);
	}

	public String getPic800() {
		return ImageConfig.getPic800Url(path);
	}
}