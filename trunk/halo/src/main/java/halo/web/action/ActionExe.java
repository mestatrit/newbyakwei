package halo.web.action;

import halo.util.FileUtil;
import halo.web.util.HaloWebUtil;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * mvc程序处理的核心类，对匹配的url进行处理并返回处路径
 * 
 * @author akwei
 */
public class ActionExe {

	private ActionMappingCreator actionMappingCreator = new ActionMappingCreator();

	public ActionExe(List<String> list) {
		this.actionMappingCreator.setScanPathList(list);
	}

	/**
	 * 处理对应mappingUri的action
	 * 
	 * @param mappingUri
	 *            去除contextPath，后缀之后剩下的部分<br>
	 *            例如：/webapp/user_list.do,mappingUri=/user_list
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String invoke(String mappingUri, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HkRequest hkRequest = HaloWebUtil.getHkRequest(request);
		HkResponse hkResponse = HaloWebUtil.getHkResponse(response);
		try {
			ActionMapping actionMapping = this.actionMappingCreator
					.getActionMapping(mappingUri);
			if (actionMapping.hasMethod()) {
				if (actionMapping.getAsmAction() != null) {
					return actionMapping.getAsmAction().execute(hkRequest,
							hkResponse);
				}
				return (String) actionMapping.getActionMethod().invoke(
						actionMapping.getAction(), hkRequest, hkResponse);
			}
			return actionMapping.getAction().execute(hkRequest, hkResponse);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			afterProcess(hkRequest);
		}
	}

	public void afterProcess(HkRequest request) {
		this.deleteFiles(request);
	}

	private boolean deleteFiles(HkRequest request) {
		File[] files = request.getFiles();
		if (files != null) {
			for (File f : files) {
				if (!FileUtil.deleteFile(f)) {
					continue;
				}
			}
		}
		return true;
	}
}