package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.page.SimplePage;

public class SimplePageTag extends BaseWapTag {
	private static final long serialVersionUID = -4737865099063656930L;

	private String href;

	private String clazz;

	private boolean top;

	private String domain;

	private String returnhref;

	private String returndata;

	private String returndomain;

	public void setReturndomain(String returndomain) {
		this.returndomain = returndomain;
	}

	public void setReturnhref(String returnhref) {
		this.returnhref = returnhref;
	}

	public void setReturndata(String returndata) {
		this.returndata = returndata;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	@Override
	protected void exeTag(JspWriter writer) throws IOException {
		this.getRequest().setAttribute(HkUtil.NEEDTOPVIEW, this.top);
		SimplePage page = (SimplePage) this.getRequest().getAttribute(
				HkUtil.SIMPLEPAGE_ATTRIBUTE);
		String paflg = null;
		if (this.href.indexOf("?") != -1) {
			paflg = "&";
		}
		else {
			paflg = "?";
		}
		if (clazz == null || "".equals(clazz)) {
			clazz = "page";
		}
		writer.append("<div class=\"");
		writer.append(clazz);
		writer.append("\">");
		boolean needFen = false;
		if (page.getSize() == page.getListSize()) {
			String nextHref = this.href + paflg + "page="
					+ (page.getPage() + 1);
			writer.append("<a href=\"");
			writer.append(this.buildURL(this.domain, nextHref));
			writer.append("\">下页</a>");
			needFen = true;
		}
		if (page.getPage() > 1) {
			String preHref = this.href + paflg + "page=" + (page.getPage() - 1);
			String firstHref = this.href + paflg + "page=1";
			if (needFen) {
				writer.append("|");
			}
			writer.append("<a href=\"");
			writer.append(this.buildURL(this.domain, preHref));
			writer.append("\">前页</a>|<a href=\"");
			writer.append(this.buildURL(this.domain, firstHref));
			writer.append("\">首页</a>");
			needFen = true;
		}
		if (this.returnhref != null) {
			if (needFen) {
				writer.append("|");
			}
			writer.append("<a href=\"");
			writer.append(this.buildURL(returndomain, returnhref));
			writer.append("\">");
			writer.append(this.returndata);
			writer.append("</a>");
			needFen = true;
		}
		if (this.top) {
			if (needFen) {
				writer.append("|");
			}
			writer.append("<a name=\"menu\" href=\"#hktop\">顶部</a>");
		}
		writer.append("</div>");
	}
}