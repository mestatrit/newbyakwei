package com.dev3g.cactus.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev3g.cactus.util.HkUtil;

/**
 * web运行的入口
 * 
 * @author akwei
 */
public class ActionFilter implements Filter {

	/**
	 * 忽略匹配的文件集合，通过web.xml进行配置，只需要配置扩展名称，例如：.jpg,.jsp，暂不支持通配符*
	 */
	private List<String> ingoreList = new ArrayList<String>();

	private ActionExe actionExe;

	private MappingUriCreater mappingUriCreater;

	private ActionResultProcessor actionResultProcessor;

	private static final String REQUESTCONTEXTPATH_KEY = "appctx_path";

	@Override
	public void init(FilterConfig config) throws ServletException {
		WebCnf webCnf = (WebCnf) HkUtil.getBean("webCnf");
		this.actionExe = webCnf.getActionExe();
		this.mappingUriCreater = webCnf.getMappingUriCreater();
		this.actionResultProcessor = webCnf.getActionResultProcessor();
		String endIngore = config.getInitParameter("endIngore");
		if (endIngore != null) {
			String[] t = endIngore.split(",");
			if (t != null) {
				for (String s : t) {
					ingoreList.add(s);
				}
			}
		}
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse resp = (HttpServletResponse) arg1;
		String uri = req.getRequestURI();
		if (uri.endsWith(".jsp")) {// 默认不对jsp进行过滤
			arg2.doFilter(arg0, arg1);
			return;
		}
		for (String s : ingoreList) {
			if (uri.endsWith(s)) {
				arg2.doFilter(arg0, arg1);
				return;
			}
		}
		req.setAttribute(REQUESTCONTEXTPATH_KEY, req.getContextPath());
		String mappingUri = this.mappingUriCreater.findMappingUri(req);
		try {
			this.actionResultProcessor.processResult(this.actionExe.invoke(
					mappingUri, req, resp), req, resp);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void destroy() {
		this.actionExe = null;
	}
}