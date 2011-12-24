package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 可下载的文件
 * 
 * @author akwei
 */
@Table(name = "cmpdownfile")
public class CmpDownFile {

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private long cmpNavOid;

	@Column
	private String name;

	@Column
	private String path;

	@Column
	private int dcount;

	/**
	 * 文件大小
	 */
	@Column
	private long fileSize;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getCmpNavOid() {
		return cmpNavOid;
	}

	public void setCmpNavOid(long cmpNavOid) {
		this.cmpNavOid = cmpNavOid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 20)) {
			return Err.CMPDOWNFILE_NAME_ERROR;
		}
		return Err.SUCCESS;
	}

	public int getDcount() {
		return dcount;
	}

	public void setDcount(int dcount) {
		this.dcount = dcount;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}