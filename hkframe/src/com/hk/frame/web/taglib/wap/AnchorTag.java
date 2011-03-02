package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspWriter;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.taglib.PropertyParam;

public class AnchorTag extends BaseWapTag {
	private static final long serialVersionUID = 5032277520629395982L;

	private String name;

	private String href;

	private String clazz;

	private boolean page;

	private String target;

	private String domain;

	private String nameadd;

	private boolean top;

	private boolean needreturnurl;

	private boolean decode;

	private String onclick;

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		writer.append(this.buildAnchor(this.getBodyContentAsString()));
	}

	private String buildAnchor(String inner) {
		this.getRequest().setAttribute(HkUtil.NEEDTOPVIEW, this.top);
		StringBuilder sb = new StringBuilder("<a");
		List<PropertyParam> list = new ArrayList<PropertyParam>();
		if (nameadd != null) {
			list.add(new PropertyParam("name", name + nameadd));
		}
		else {
			list.add(new PropertyParam("name", name));
		}
		list.add(new PropertyParam("id", oid));
		list.add(new PropertyParam("class", clazz));
		list.add(new PropertyParam("onclick", onclick));
		list.add(new PropertyParam("target", target));
		sb.append(PropertyParam.renderProperty(list));
		if (href != null) {
			sb.append(" href=\"");
			if (href.equals("#")) {
				sb.append("#");
			}
			else {
				if (page) {
					SimplePage page = (SimplePage) this.getRequest()
							.getAttribute(HkUtil.SIMPLEPAGE_ATTRIBUTE);
					String prefix = this.getPrefix();
					if (page != null) {
						this.href = this.href + prefix + "repage="
								+ page.getPage();
					}
					else {
						this.href = this.href + prefix + "repage=1";
					}
				}
				if (needreturnurl) {
					String enc_url = (String) this.getRequest().getAttribute(
							HkUtil.RETURN_URL);
					if (enc_url != null) {
						String prefix = this.getPrefix();
						this.href = this.href + prefix + HkUtil.RETURN_URL
								+ "=" + enc_url;
					}
				}
				if (decode) {
					this.href = DataUtil.urlDecoder(this.href);
				}
				sb.append(buildURL(this.domain, this.href));
			}
			sb.append("\"");
		}
		sb.append(">");
		sb.append(inner);
		sb.append("</a>");
		if (this.top) {
			sb.append("|<a name=\"menu\" href=\"#hktop\">顶部</a>");
		}
		return sb.toString();
	}

	private String getPrefix() {
		String prefix = null;
		if (this.href.indexOf("?") != -1) {
			prefix = "&";
		}
		else {
			prefix = "?";
		}
		return prefix;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public void setNameadd(String nameadd) {
		this.nameadd = nameadd;
	}

	public void setNeedreturnurl(boolean needreturnurl) {
		this.needreturnurl = needreturnurl;
	}

	public void setDecode(boolean decode) {
		this.decode = decode;
	}
}