package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.ImageConfig;

@Table(name = "cmpproductphoto", id = "oid")
public class CmpProductPhoto {

	private long oid;

	private long photoId;

	private long productId;

	private long companyId;

	private String path;

	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
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

	public String getPic120() {
		return ImageConfig.getPic120Url(path);
	}

	public String getPic240() {
		return ImageConfig.getPic240Url(path);
	}

	public String getPic320() {
		return ImageConfig.getPic320Url(path);
	}

	public String getPic640() {
		return ImageConfig.getPic600Url(path);
	}

	public String getPic800() {
		return ImageConfig.getPic800Url(path);
	}
}