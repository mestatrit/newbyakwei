package cactus.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * mvc程序处理的核心类
 * 
 * @author akwei
 */
public interface ActionExe {

	String invoke(String mappingUri, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}