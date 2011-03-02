package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspWriter;
import com.hk.frame.web.taglib.PropertyParam;

public class TextTag extends BaseWapTag {
	private static final long serialVersionUID = 4963297790560396168L;

	private Object name;

	private Object maxlength;

	private Object value;

	private Object clazz;

	private Object size;

	private Object style;

	@Override
	protected void exeTag(JspWriter writer) throws IOException {
		List<PropertyParam> list = new ArrayList<PropertyParam>();
		list.add(new PropertyParam("type", "text"));
		list.add(new PropertyParam("name", name));
		list.add(new PropertyParam("size", size));
		list.add(new PropertyParam("class", clazz));
		list.add(new PropertyParam("maxlength", maxlength));
		list.add(new PropertyParam("value", value));
		list.add(new PropertyParam("style", style));
		writer.append("<input");
		writer.append(PropertyParam.renderProperty(list));
		writer.append("/>");
	}

	public Object getName() {
		return name;
	}

	public void setName(Object name) {
		this.name = name;
	}

	public Object getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(Object maxlength) {
		this.maxlength = maxlength;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getClazz() {
		return clazz;
	}

	public void setClazz(Object clazz) {
		this.clazz = clazz;
	}

	public Object getSize() {
		return size;
	}

	public void setSize(Object size) {
		this.size = size;
	}

	public Object getStyle() {
		return style;
	}

	public void setStyle(Object style) {
		this.style = style;
	}
}