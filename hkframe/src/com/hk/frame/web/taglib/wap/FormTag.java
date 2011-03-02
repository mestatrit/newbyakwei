package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspWriter;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.web.taglib.PropertyParam;

public class FormTag extends BaseWapTag {
	private static final long serialVersionUID = 6700994854647429733L;

	private String method;

	private String action;

	private String clazz;

	private String name;

	private String enctype;

	private String domain;

	private String onsubmit;

	private boolean needreturnurl;

	private Object target;

	public void setTarget(Object target) {
		this.target = target;
	}

	public void setOnsubmit(String onsubmit) {
		this.onsubmit = onsubmit;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setNeedreturnurl(boolean needreturnurl) {
		this.needreturnurl = needreturnurl;
	}

	@Override
	public void exeTag(JspWriter writer) throws IOException {
		writer.append(this.buildForm(this.getBodyContentAsString()));
	}

	private String buildForm(String inner) {
		if (method == null || "".equals(method)) {
			method = "post";
		}
		StringBuilder sb = new StringBuilder("<form");
		List<PropertyParam> list = new ArrayList<PropertyParam>();
		list.add(new PropertyParam("id", oid));
		list.add(new PropertyParam("name", name));
		list.add(new PropertyParam("class", clazz));
		list.add(new PropertyParam("method", method));
		list.add(new PropertyParam("enctype", enctype));
		list.add(new PropertyParam("onsubmit", onsubmit));
		list.add(new PropertyParam("target", target));
		if (action != null && action.equals("#")) {
			list.add(new PropertyParam("action", "#"));
		}
		else {
			if (DataUtil.isEmpty(this.domain)) {
				list.add(new PropertyParam("action", this.getRequest()
						.getContextPath()
						+ action));
			}
			else {
				list.add(new PropertyParam("action", this.buildURL(domain,
						action)));
			}
		}
		sb.append(PropertyParam.renderProperty(list));
		sb.append(">");
		if (needreturnurl) {
			String enc_url = (String) this.getRequest().getAttribute(
					HkUtil.RETURN_URL);
			if (!DataUtil.isEmpty(enc_url)) {
				String dnc_url = DataUtil.urlDecoder(enc_url);
				sb
						.append(
								"<input type=\"hidden\" name=\"return_url\" value=\"")
						.append(dnc_url).append("\"/>");
			}
		}
		sb.append(inner);
		sb.append("</form>");
		return sb.toString();
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}
}