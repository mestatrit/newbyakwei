package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 企业图集与图片的关系表
 * 
 * @author akwei
 */
@Table(name = "cmpphotosetref")
public class CmpPhotoSetRef {

	@Id
	private long oid;

	@Column
	private long setId;

	@Column
	private long photoId;

	@Column
	private long companyId;

	private CompanyPhoto companyPhoto;

	private CmpPhotoSet cmpPhotoSet;

	public void setCmpPhotoSet(CmpPhotoSet cmpPhotoSet) {
		this.cmpPhotoSet = cmpPhotoSet;
	}

	public CmpPhotoSet getCmpPhotoSet() {
		return cmpPhotoSet;
	}

	public void setCompanyPhoto(CompanyPhoto companyPhoto) {
		this.companyPhoto = companyPhoto;
	}

	public CompanyPhoto getCompanyPhoto() {
		return companyPhoto;
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