package cactus.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * mvc程序处理的核心类，对匹配的url进行处理并返回处路径
 * 
 * @author akwei
 */
public interface ActionExe {

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
	String invoke(String mappingUri, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}