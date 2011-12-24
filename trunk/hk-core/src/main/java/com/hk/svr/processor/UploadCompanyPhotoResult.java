package com.hk.svr.processor;

import java.util.List;

import com.hk.bean.CompanyPhoto;
import com.hk.svr.pub.Err;

/**
 * 图片上传返回值,图片最多能上传300张
 * 
 * @author akwei
 */
public class UploadCompanyPhotoResult {

	/**
	 * 图片上传错误的数量
	 */
	private int errCount;

	/**
	 * 图片超过限制大小的数量
	 */
	private int outOfSizeCount;

	/**
	 * 图片格式错误的数量
	 */
	private int fmtErrCount;

	/**
	 * 错误代码 @see {@link Err#SUCCESS} {@link Err#COMPANYPHOTO_OUT_OF_COUNT}
	 */
	private int errorCode;

	/**
	 * 上传成功的图片集合
	 */
	private List<CompanyPhoto> list;

	/**
	 * 是否所有图片都上传成功
	 */
	private boolean allImgUploadSuccess;

	public void addErrCount(int add) {
		this.setErrCount(this.getErrCount() + add);
	}

	public int getErrCount() {
		return errCount;
	}

	public void setErrCount(int errCount) {
		this.errCount = errCount;
	}

	public void addOutOfSizeCount(int add) {
		this.setOutOfSizeCount(this.getOutOfSizeCount() + add);
	}

	public int getOutOfSizeCount() {
		return outOfSizeCount;
	}

	public void setOutOfSizeCount(int outOfSizeCount) {
		this.outOfSizeCount = outOfSizeCount;
	}

	public void addFmtErrCount(int add) {
		this.setFmtErrCount(this.getFmtErrCount() + add);
	}

	public int getFmtErrCount() {
		return fmtErrCount;
	}

	public void setFmtErrCount(int fmtErrCount) {
		this.fmtErrCount = fmtErrCount;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public List<CompanyPhoto> getList() {
		return list;
	}

	public void setList(List<CompanyPhoto> list) {
		this.list = list;
	}

	public boolean isAllImgUploadSuccess() {
		return allImgUploadSuccess;
	}

	public void setAllImgUploadSuccess(boolean allImgUploadSuccess) {
		this.allImgUploadSuccess = allImgUploadSuccess;
	}
}