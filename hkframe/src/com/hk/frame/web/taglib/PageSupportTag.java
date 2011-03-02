package com.hk.frame.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.hk.frame.util.HkUtil;
import com.hk.frame.util.page.PageSupport;

public class PageSupportTag extends BaseHkTag {

	private static final long serialVersionUID = 1248474091846401791L;

	private String url;

	private boolean input;

	private Object midcount;

	public void setMidcount(Object midcount) {
		this.midcount = midcount;
	}

	public void setInput(boolean input) {
		this.input = input;
	}

	@Override
	public void adapter(JspWriter writer) throws IOException {
		writer.append(this.printPage());
	}

	public String printPage() {
		PageSupport pageSupport = (PageSupport) this.getRequest().getAttribute(
				HkUtil.PAGESUPPORT_ATTRIBUTE);
		if (pageSupport == null) {
			return "";
		}
		StringBuilder local_url = new StringBuilder(this.getRequest()
				.getContextPath());
		local_url.append(this.url);
		if (this.url.indexOf('?') != -1) {
			local_url.append("&page=");
		}
		else {
			local_url.append("?page=");
		}
		buildURL(null, this.url);
		if (midcount != null) {
			pageSupport.outPageNo(Integer.parseInt(midcount.toString()));// 设置中间显示几页
		}
		else {
			pageSupport.outPageNo(10);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<ul class=\"pagenation\">");
		// 显示上一页按钮
		if (pageSupport.getPage() > 1) {
			sb.append("<li><a class=\"prev\" href=\"");
			sb.append(local_url).append(pageSupport.getPage() - 1);
			sb.append("\">&lt; 上一页</a></li>");
		}
		// 如果分页数量>10则进行缩略显示
		if (pageSupport.getPageCount() > 10) {
			if (pageSupport.getBeginPage() > 1) {
				sb.append("<li><a href=\"");
				sb.append(local_url).append(1);
				sb.append("\">1</a></li>");
				sb.append("<li class=\"more\">...</li>");
			}
		}
		// 如果分页数量<=10则显示所有页数
		for (int i = pageSupport.getBeginPage(); i <= pageSupport.getEndPage(); i++) {
			if (i == pageSupport.getPage()) {
				sb.append("<li class=\"now\"><a href=\"#\">").append(i).append(
						"</a></li>");
			}
			else {
				sb.append("<li><a href=\"");
				sb.append(local_url).append(i);
				sb.append("\">").append(i).append("</a></li>");
			}
		}
		if (pageSupport.getPageCount() > 10) {
			if (pageSupport.getEndPage() < pageSupport.getPageCount()) {
				sb.append("<li class=\"more\">...</li>");
				sb.append("<li><a href=\"");
				sb.append(local_url).append(pageSupport.getPageCount());
				sb.append("\">").append(pageSupport.getPageCount()).append(
						"</a></li>");
			}
		}
		// 显示下一页按钮
		if (pageSupport.getPage() < pageSupport.getPageCount()) {
			sb.append("<li><a class=\"next\" href=\"");
			sb.append(local_url).append(pageSupport.getPage() + 1);
			sb.append("\">下一页 &gt;</a></li>");
		}
		sb.append("</ul>");
		if (this.input) {// 暂时没有用到
		}
		return sb.toString();
	}

	public void setUrl(String url) {
		this.url = url;
	}
}