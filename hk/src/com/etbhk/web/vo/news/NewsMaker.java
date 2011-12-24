package com.etbhk.web.vo.news;

import javax.servlet.http.HttpServletRequest;

import com.etbhk.web.vo.Tb_NewsVo;

public interface NewsMaker {

	/**
	 * 输出动态结果
	 * 
	 * @param request
	 * @param tbNewsVo
	 * @return
	 *         2010-9-16
	 */
	String getNewsData(HttpServletRequest request, Tb_NewsVo tbNewsVo);
}