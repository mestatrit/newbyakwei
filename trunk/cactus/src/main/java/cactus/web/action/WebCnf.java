package cactus.web.action;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import cactus.util.HkUtil;

/**
 * 当使用默认配置时，会使用此选项
 * 
 * @author akwei
 */
public class WebCnf implements InitializingBean {

	private List<String> scanPathList;

	private String url_extension;

	private ActionExe actionExe;

	private MappingUriCreater mappingUriCreater;

	private ActionResultProcessor actionResultProcessor;

	private List<String> fileUploadCheckUriList;

	/**
	 * 设置文件上传所允许通过的urlmapping以及允许上传文件的大小以kb来计算
	 * 
	 * @param fileUploadCheckUriList
	 */
	public void setFileUploadCheckUriList(List<String> fileUploadCheckUriList) {
		this.fileUploadCheckUriList = fileUploadCheckUriList;
	}

	public List<String> getFileUploadCheckUriList() {
		return fileUploadCheckUriList;
	}

	public void setScanPathList(List<String> scanPathList) {
		this.scanPathList = scanPathList;
	}

	public void setUrl_extension(String urlExtension) {
		url_extension = urlExtension;
	}

	private void initActionExe() {
		this.actionExe = (ActionExe) HkUtil.getBean("actionExe");
		if (actionExe == null) {
			ActionFinder actionFinder = (ActionFinder) HkUtil
					.getBean("actionFinder");
			if (actionFinder == null) {
				actionFinder = new DefActionFinder();
				((DefActionFinder) actionFinder)
						.setScanPathList(this.scanPathList);
			}
			ActionMappingCreator actionMappingCreator = new ActionMappingCreator(
					actionFinder);
			ActionExeImpl impl = new ActionExeImpl();
			impl.setActionMappingCreator(actionMappingCreator);
			this.actionExe = impl;
		}
	}

	private void initMappingUriCreator() {
		this.mappingUriCreater = (MappingUriCreater) HkUtil
				.getBean("mappingUriCreater");
		if (this.mappingUriCreater == null) {
			this.mappingUriCreater = new MappingUriCreater();
			this.mappingUriCreater.setUrl_extension(this.url_extension);
		}
	}

	private void initActionResultProcessor() {
		this.actionResultProcessor = (ActionResultProcessor) HkUtil
				.getBean("actionResultProcessor");
		if (this.actionResultProcessor == null) {
			this.actionResultProcessor = new ActionResultProcessor();
		}
	}

	public void setActionExe(ActionExe actionExe) {
		this.actionExe = actionExe;
	}

	public void setMappingUriCreater(MappingUriCreater mappingUriCreater) {
		this.mappingUriCreater = mappingUriCreater;
	}

	public void setActionResultProcessor(
			ActionResultProcessor actionResultProcessor) {
		this.actionResultProcessor = actionResultProcessor;
	}

	public ActionExe getActionExe() {
		return actionExe;
	}

	public MappingUriCreater getMappingUriCreater() {
		return mappingUriCreater;
	}

	public ActionResultProcessor getActionResultProcessor() {
		return actionResultProcessor;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.initMappingUriCreator();
		this.initActionResultProcessor();
		this.initActionExe();
	}
}