package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspWriter;
import com.hk.frame.util.ResourceConfig;
import com.hk.frame.web.taglib.PropertyParam;

public class ButtonTag extends BaseWapTag {
	private static final long serialVersionUID = -2661842953332539917L;

	private Object name;

	private Object value;

	private Object clazz;

	private boolean res;

	private Object onclick;

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		writer.append("<input");
		List<PropertyParam> list = new ArrayList<PropertyParam>();
		list.add(new PropertyParam("id", oid));
		list.add(new PropertyParam("onclick", onclick));
		list.add(new PropertyParam("type", "button"));
		list.add(new PropertyParam("name", name));
		list.add(new PropertyParam("class", clazz));
		if (res && value != null) {
			list.add(new PropertyParam("value", ResourceConfig.getText(value
					.toString())));
		}
		else if (value != null) {
			list.add(new PropertyParam("value", value.toString()));
		}
		writer.append(PropertyParam.renderProperty(list));
		writer.append("/>");
	}

	public void setRes(boolean res) {
		this.res = res;
	}

	public void setClazz(Object clazz) {
		this.clazz = clazz;
	}

	public void setName(Object name) {
		this.name = name;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}