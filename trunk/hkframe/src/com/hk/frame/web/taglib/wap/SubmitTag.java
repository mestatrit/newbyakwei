package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.JspWriter;
import com.hk.frame.util.ResourceConfig;
import com.hk.frame.util.i18n.HkI18n;
import com.hk.frame.web.taglib.PropertyParam;

public class SubmitTag extends BaseWapTag {

	private static final long serialVersionUID = -2661842953332539917L;

	private String name;

	private String value;

	private String clazz;

	private boolean res;

	private String onclick;

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		writer.append("<input");
		List<PropertyParam> list = new ArrayList<PropertyParam>();
		list.add(new PropertyParam("id", oid));
		list.add(new PropertyParam("onclick", onclick));
		list.add(new PropertyParam("type", "submit"));
		list.add(new PropertyParam("name", name));
		list.add(new PropertyParam("class", clazz));
		if (res) {
			Locale locale = (Locale) this.getRequest().getAttribute(
					HkI18n.I18N_KEY);
			if (locale == null) {
				locale = Locale.SIMPLIFIED_CHINESE;
			}
			list.add(new PropertyParam("value", ResourceConfig.getText(locale,
					value)));
		}
		else {
			list.add(new PropertyParam("value", value));
		}
		writer.append(PropertyParam.renderProperty(list));
		writer.append("/>");
	}

	public void setRes(boolean res) {
		this.res = res;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}
}