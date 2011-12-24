package com.hk.svr.processor;

import java.util.List;

import com.hk.bean.CmpProductPhoto;

/**
 * 上传图片的结果
 * 
 * @author akwei
 */
public class UploadCmpProductPhotoResult {

	private int errornum;

	private boolean canUpload;

	private List<CmpProductPhoto> cmpProductPhotoList;

	/**
	 * 图片上传失败的张数
	 * 
	 * @return
	 *         2010-5-16
	 */
	public int getErrornum() {
		return errornum;
	}

	public void addErrnum(int add) {
		this.setErrornum(this.getErrornum() + add);
	}

	public void setErrornum(int errornum) {
		this.errornum = errornum;
	}

	/**
	 * 是否可以上传图片 false:超过30张限制，不能上传,true:可以上传
	 * 
	 * @return
	 *         2010-5-16
	 */
	public boolean isCanUpload() {
		return canUpload;
	}

	public void setCanUpload(boolean canUpload) {
		this.canUpload = canUpload;
	}

	public List<CmpProductPhoto> getCmpProductPhotoList() {
		return cmpProductPhotoList;
	}

	public void setCmpProductPhotoList(List<CmpProductPhoto> cmpProductPhotoList) {
		this.cmpProductPhotoList = cmpProductPhotoList;
	}
}