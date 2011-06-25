package com.dev3g.cactus.web.action;

import javax.servlet.http.HttpServletRequest;

public class MappingUriCreater {

	private static final String endpfix = "/";

	private String seperator = "/";

	private String dot = ".";

	/**
	 * 解析uri,"_"作为action与方法名的分隔符。例如：/user_list。可以对应UserAction中list的方法
	 * 
	 * @param request
	 * @return
	 */
	public String findMappingUri(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String localuri = uri.substring(request.getContextPath().length(), uri
				.length());
		String postfix = this.getPostfix(localuri);
		if (postfix == null) {
			// 如果uri为"/"结尾，则需要去掉""。例如/user_list/，需要获得有用的部分为/user_list
			// 这种情况还没遇到，不知是否要去掉
			if (localuri.endsWith(endpfix)) {
				return localuri.substring(0, localuri.lastIndexOf(endpfix));
			}
			return localuri;
		}
		// 如果uri有后缀则去掉后缀。例如/user_list.do，需要获得有用的部分为/user_list
		return localuri.substring(0, localuri.lastIndexOf(postfix));
	}

	/**
	 * 获得uri后缀
	 * 
	 * @param localuri
	 * @return
	 */
	private String getPostfix(String localuri) {
		// 获得最后一个"/"的位置
		int lastidx_dot = localuri.lastIndexOf(this.dot);
		// 例：/user/set_method
		if (lastidx_dot == -1) {
			return null;
		}
		int lastidx_sep = localuri.lastIndexOf(this.seperator);
		// 例： user.do，postfix=.do
		if (lastidx_sep == -1) {
			return localuri.substring(lastidx_dot);
		}
		// 例：/user.su/set_method
		if (lastidx_sep > lastidx_dot) {
			return null;
		}
		// 例：/user/set_method.do,/user/set.do,/user.do
		return localuri.substring(lastidx_dot);
	}
}
