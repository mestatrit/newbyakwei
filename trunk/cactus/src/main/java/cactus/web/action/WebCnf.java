package cactus.web.action;

import java.util.List;

/**
 * 当使用默认配置时，会使用此选项
 * 
 * @author akwei
 */
public class WebCnf {

	private List<String> scanPathList;

	private String url_extension;

	public List<String> getScanPathList() {
		return scanPathList;
	}

	public void setScanPathList(List<String> scanPathList) {
		this.scanPathList = scanPathList;
	}

	public String getUrl_extension() {
		return url_extension;
	}

	public void setUrl_extension(String urlExtension) {
		url_extension = urlExtension;
	}
}