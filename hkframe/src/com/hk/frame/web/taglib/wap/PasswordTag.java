package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspWriter;
import com.hk.frame.web.taglib.PropertyParam;

public class PasswordTag extends BaseWapTag {
	private static final long serialVersionUID = -4926939383752650820L;

	private String clazz;

	private String name;

	private String value;

	private String maxlength;

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		List<PropertyParam> list = new ArrayList<PropertyParam>();
		list.add(new PropertyParam("type", "password"));
		list.add(new PropertyParam("name", name));
		list.add(new PropertyParam("class", clazz));
		list.add(new PropertyParam("value", value));
		list.add(new PropertyParam("maxlength", maxlength));
		writer.append("<input");
		writer.append(PropertyParam.renderProperty(list));
		writer.append("/>");
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
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