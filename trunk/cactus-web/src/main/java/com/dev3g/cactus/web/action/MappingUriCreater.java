package com.dev3g.cactus.web.action;

import javax.servlet.http.HttpServletRequest;

public class MappingUriCreater {

	private String url_extension;

	private static final String endpfix = "/";

	public void setUrl_extension(String urlExtension) {
		url_extension = urlExtension;
	}

	/**
	 * 解析uri,"_"作为action与方法名的分隔符。例如：/user_list。可以对应UserAction中list的方法
	 * 
	 * @param request
	 * @return
	 */
	public String findMappingUri(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String localuri = uri.substring(request.getContextPath().length(),
				uri.length());
		String mappingUri = null;
		// 如果uri有后缀则去掉后缀。例如/user_list.do，需要获得有用的部分为/user_list
		if (url_extension != null && localuri.endsWith(url_extension)) {
			mappingUri = localuri.substring(0,
					localuri.lastIndexOf(url_extension));
		}
		else {
			// 如果uri为"/"结尾，则需要去掉""。例如/user_list/，需要获得有用的部分为/user_list
			// 这种情况还没遇到，不知是否要去掉
			if (localuri.endsWith(endpfix)) {
				mappingUri = localuri.substring(0,
						localuri.lastIndexOf(endpfix));
			}
			else {
				mappingUri = localuri;
			}
		}
		return mappingUri;
	}
}
