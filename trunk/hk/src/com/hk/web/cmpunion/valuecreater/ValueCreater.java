package com.hk.web.cmpunion.valuecreater;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理接口。用于组装数据
 * 
 * @author akwei
 */
public interface ValueCreater {
	/**
	 * 根据obj 返回相应数据
	 * 
	 * @param request
	 * @param obj
	 * @return
	 */
	String getValue(HttpServletRequest request, Object obj);
}
