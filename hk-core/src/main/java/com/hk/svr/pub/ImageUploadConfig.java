package com.hk.svr.pub;

public class ImageUploadConfig {
	private String rootPath;

	private String currentUploadRootPath;

	private String url;

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getCurrentUploadRootPath() {
		return currentUploadRootPath;
	}

	public void setCurrentUploadRootPath(String currentUploadRootPath) {
		this.currentUploadRootPath = currentUploadRootPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}