package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspWriter;
import com.hk.frame.web.taglib.PropertyParam;

public class ImgTag extends BaseWapTag {
	private static final long serialVersionUID = 5879779777468370143L;

	private String src;

	private String alt;

	private String title;

	private String clazz;

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		List<PropertyParam> list = new ArrayList<PropertyParam>();
		list.add(new PropertyParam("src", src));
		list.add(new PropertyParam("class", clazz));
		list.add(new PropertyParam("alt", alt));
		list.add(new PropertyParam("title", title));
		writer.append("<img");
		writer.append(PropertyParam.renderProperty(list));
		writer.append("/>");
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}