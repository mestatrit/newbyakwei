package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 企业上传的文件的总容量(包括文件系统以及视频系统上传的文件)
 * 
 * @author akwei
 */
@Table(name = "cmpotherwebinfo")
public class CmpOtherWebInfo {

	public static final byte ORGCHECK_N = 0;

	public static final byte ORGCHECK_Y = 1;

	/**
	 * 企业id
	 */
	@Id
	private long companyId;

	/**
	 * 文件总大小(单位K)
	 */
	@Column
	private long totalFileSize;

	/**
	 * 已使用的空间(单位K)
	 */
	@Column
	private long usedFileSize;

	/**
	 * 机构是否需要审核
	 */
	@Column
	private byte orgcheck;

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getTotalFileSize() {
		return totalFileSize;
	}

	public void setTotalFileSize(long totalFileSize) {
		this.totalFileSize = totalFileSize;
	}

	public void setUsedFileSize(long usedFileSize) {
		this.usedFileSize = usedFileSize;
	}

	public long getUsedFileSize() {
		return usedFileSize;
	}

	public long getRemainFileSize() {
		long remain = this.getTotalFileSize() - this.getUsedFileSize();
		if (remain < 0) {
			remain = 0;
		}
		return remain;
	}

	public void addFileSize(long add) {
		this.setUsedFileSize(this.getUsedFileSize() + add);
	}

	public byte getOrgcheck() {
		return orgcheck;
	}

	public void setOrgcheck(byte orgcheck) {
		this.orgcheck = orgcheck;
	}

	public boolean isOrgNeedCheck() {
		if (this.orgcheck == ORGCHECK_Y) {
			return true;
		}
		return false;
	}
}