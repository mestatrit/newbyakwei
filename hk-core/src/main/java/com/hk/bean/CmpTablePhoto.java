package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

@Table(name = "cmptablephoto", id = "oid")
public class CmpTablePhoto {
	private long oid;

	private long setId;

	private long photoId;

	private long companyId;

	private String path;

	private String name;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPic60() {
		return ImageConfig.getPic60Url(path);
	}

	public String getPic240() {
		return ImageConfig.getPic240Url(path);
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getSetId() {
		return setId;
	}

	public void setSetId(long setId) {
		this.setId = setId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.name);
		if (s != null && s.length() > 15) {
			return Err.CMPTABLEPHOTO_NMAE_ERROR;
		}
		return Err.SUCCESS;
	}
}