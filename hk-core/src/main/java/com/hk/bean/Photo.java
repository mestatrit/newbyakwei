package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

@Table(name = "photo", id = "photoid")
public class Photo {

	@Id
	private long photoId;

	@Column
	private long userId;

	@Column
	private String path;

	@Column
	private Date createTime;

	@Column
	private String name;

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

	public int validate() {
		if (!DataUtil.isEmpty(name)) {
			if (DataUtil.toTextRow(name).length() > 20) {
				return Err.PHOTO_NAME_LENGTH_TOO_LONG;
			}
		}
		if (this.userId < 1) {
			return Err.USERID_ERROR;
		}
		return Err.SUCCESS;
	}
}