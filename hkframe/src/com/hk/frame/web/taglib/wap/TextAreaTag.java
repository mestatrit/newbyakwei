package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import com.hk.frame.util.DataUtil;
import com.hk.frame.web.taglib.PropertyParam;

public class TextAreaTag extends BaseWapTag {

	private static final long serialVersionUID = 1652555587725221652L;

	private Object rows;

	private Object cols;

	private Object clazz;

	private Object name;

	private Object value;

	private Object style;

	@Override
	protected void exeTag(JspWriter writer) throws IOException {
		StringBuilder sb = new StringBuilder("<textarea");
		List<PropertyParam> list = new ArrayList<PropertyParam>();
		list.add(new PropertyParam("name", name));
		list.add(new PropertyParam("id", oid));
		list.add(new PropertyParam("class", clazz));
		list.add(new PropertyParam("rows", rows));
		list.add(new PropertyParam("cols", cols));
		list.add(new PropertyParam("style", style));
		sb.append(PropertyParam.renderProperty(list));
		sb.append(">\n");
		if (value != null) {
			// sb.append(HkUtil.toTextValue(value.toString()));
			sb.append(DataUtil.toText(value.toString()));
		}
		sb.append("</textarea>");
		String s = sb.toString();
		writer.append(s);
		this.setIngoreString(s);
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}

	public void setCols(Object cols) {
		this.cols = cols;
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

	public void setStyle(Object style) {
		this.style = style;
	}
}