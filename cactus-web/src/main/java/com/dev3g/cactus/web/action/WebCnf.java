package com.dev3g.cactus.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.dev3g.cactus.util.HkUtil;
import com.dev3g.cactus.web.util.ServletUtil;

/**
 * 当使用默认配置时，会使用此选项
 * 
 * @author akwei
 */
public class WebCnf implements InitializingBean {

	public static final String UPLOAD_LIMIT_SIZE_KEY = "COM.DEV3G.CACTUS_HTTPREQUEST_UPLOAD_LIMIT_SIZE_KEY";

	public static final String UPLOAD_EXCEEDEDSIZE_KEY = "COM.DEV3G.CACTUS_UPLOAD_EXCEEDEDSIZE_KEY";

	public static final String WEBCNF_OBJ_KEY = "COM.DEV3G.CACTUS_HTTPREQUEST_WEBCNF_OBJ_KEY";

	public static final String MULTI_HTTPSERVLETREQUEST_KEY = "COM.DEV3G.CACTUS_MULTI_HTTPSERVLETREQUEST";

	public static final String SIMPLEPAGE_ATTRIBUTE = "COM.DEV3G.CACTUS.SIMPLEPAGE.ATTRIBUTE";

	public static final String PAGESUPPORT_ATTRIBUTE = "COM.DEV3G.CACTUS.PAGESUPPORT.ATTRIBUTE";

	public static final String ACTION_EXE_ATTR_KEY = "COM.DEV3G.CACTUS.ACTION_EXE_ATTR_KEY";

	private List<String> scanPathList;

	private ActionExe actionExe;

	/**
	 * 源数据编码
	 */
	private String sourceCharset;

	/**
	 * 目标数据编码
	 */
	private String targetCharset;

	private String uploadFileTempPath = "/cactustemp/";

	private MappingUriCreater mappingUriCreater;

	private ActionResultProcessor actionResultProcessor;

	private List<String> fileUploadCheckUriCnfList;

	private ActionFinder actionFinder;

	private final List<UploadFileCheckCnf> uploadFileCheckCnfs = new ArrayList<UploadFileCheckCnf>();

	private final Map<String, UploadFileCheckCnf> map = new HashMap<String, UploadFileCheckCnf>();

	private boolean mustCheckUpload;

	/**
	 * 源数据编码，如果不设置编码，默认将会对get方式的url进行iso-8859-1到utf-8编码的转换
	 * 
	 * @param sourceCharset
	 */
	public void setSourceCharset(String sourceCharset) {
		if (sourceCharset == null || sourceCharset.length() == 0) {
			return;
		}
		this.sourceCharset = sourceCharset;
		ServletUtil.CHARSET_SOURCE = this.sourceCharset;
	}

	/**
	 * 目标数据编码，如果不设置编码，默认将会对get方式的url进行iso-8859-1到utf-8编码的转换
	 * 
	 * @param targetCharset
	 */
	public void setTargetCharset(String targetCharset) {
		if (targetCharset == null || targetCharset.length() == 0) {
			return;
		}
		this.targetCharset = targetCharset;
		ServletUtil.CHARSET_TARGET = this.targetCharset;
	}

	public String getSourceCharset() {
		return sourceCharset;
	}

	public String getTargetCharset() {
		return targetCharset;
	}

	/**
	 * 设置是否强制检查允许上传的uri，只有配置过的uri才能接受文件上传，否则不允许文件上传，并且传递的参数也无法获取
	 * 
	 * @param mustCheckUpload
	 */
	public void setMustCheckUpload(boolean mustCheckUpload) {
		this.mustCheckUpload = mustCheckUpload;
	}

	/**
	 * 设置文件上传的临时目录
	 * 
	 * @param uploadFileTempPath
	 */
	public void setUploadFileTempPath(String uploadFileTempPath) {
		if (!uploadFileTempPath.endsWith("/")) {
			this.uploadFileTempPath = uploadFileTempPath + "/";
		}
		else {
			this.uploadFileTempPath = uploadFileTempPath;
		}
	}

	public String getUploadFileTempPath() {
		return uploadFileTempPath;
	}

	public boolean isMustCheckUpload() {
		return mustCheckUpload;
	}

	/**
	 * 设置文件上传所允许通过的urlmapping以及允许上传文件的大小以kb来计算
	 * 
	 * @param fileUploadCheckUriList
	 */
	public void setFileUploadCheckUriCnfList(
			List<String> fileUploadCheckUriCnfList) {
		this.fileUploadCheckUriCnfList = fileUploadCheckUriCnfList;
	}

	public List<String> getFileUploadCheckUriCnfList() {
		return fileUploadCheckUriCnfList;
	}

	public void setScanPathList(List<String> scanPathList) {
		this.scanPathList = scanPathList;
	}

	private void initActionExe() {
		if (actionExe == null) {
			actionFinder = (ActionFinder) HkUtil.getBean("actionFinder");
			if (actionFinder == null) {
				DefActionFinder defActionFinder = new DefActionFinder();
				defActionFinder.setScanPathList(this.scanPathList);
				actionFinder = defActionFinder;
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

	private void initUploadFileCheckCnf() {
		if (this.fileUploadCheckUriCnfList == null) {
			return;
		}
		for (String cnf : this.fileUploadCheckUriCnfList) {
			String[] s = cnf.split(":");
			if (s.length != 2) {
				continue;
			}
			int maxSize = Integer.valueOf(s[1]);
			if (maxSize > 0) {
				UploadFileCheckCnf o = new UploadFileCheckCnf(maxSize, s[0]);
				this.uploadFileCheckCnfs.add(o);
				map.put(s[0], o);
			}
		}
	}

	public UploadFileCheckCnf getUploadFileCheckCnf(String mappingUri) {
		return map.get(mappingUri);
	}

	public ActionFinder getActionFinder() {
		return actionFinder;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.initMappingUriCreator();
		this.initActionResultProcessor();
		this.initActionExe();
		this.initUploadFileCheckCnf();
	}
}