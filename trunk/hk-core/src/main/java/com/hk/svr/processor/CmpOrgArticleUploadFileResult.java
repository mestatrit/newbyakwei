package com.hk.svr.processor;

import java.util.List;

import com.hk.bean.CmpOrgFile;

public class CmpOrgArticleUploadFileResult {

	private List<CmpOrgFile> cmpOrgFileList;

	private int outOfSizeErrorNum;

	private int fmtErrorNum;

	private boolean fileUploadError;

	public void setFileUploadError(boolean fileUploadError) {
		this.fileUploadError = fileUploadError;
	}

	public boolean isFileUploadError() {
		return fileUploadError;
	}

	public void addOutOfSizeErrorNum(int add) {
		this.setOutOfSizeErrorNum(this.getOutOfSizeErrorNum() + add);
	}

	public void addFmtErrorNum(int add) {
		this.setFmtErrorNum(this.getFmtErrorNum() + add);
	}

	public void setCmpOrgFileList(List<CmpOrgFile> cmpOrgFileList) {
		this.cmpOrgFileList = cmpOrgFileList;
	}

	public List<CmpOrgFile> getCmpOrgFileList() {
		return cmpOrgFileList;
	}

	public int getOutOfSizeErrorNum() {
		return outOfSizeErrorNum;
	}

	public void setOutOfSizeErrorNum(int outOfSizeErrorNum) {
		this.outOfSizeErrorNum = outOfSizeErrorNum;
	}

	public int getFmtErrorNum() {
		return fmtErrorNum;
	}

	public void setFmtErrorNum(int fmtErrorNum) {
		this.fmtErrorNum = fmtErrorNum;
	}
}