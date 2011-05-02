package cactus.web.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import cactus.web.taglib.OptionTag.Option;

public class SelectTag extends BaseWapTag {

	private static final long serialVersionUID = 8272378800903804578L;

	private Object name;

	private Object clazz;

	private Object checkedvalue;

	private Object onchange;

	private Object forcecheckedvalue;

	private String style;

	@Override
	protected void exeTag(JspWriter writer) throws IOException {
		writer.append("<select");
		List<PropertyParam> list = new ArrayList<PropertyParam>();
		list.add(new PropertyParam("name", name));
		list.add(new PropertyParam("class", clazz));
		list.add(new PropertyParam("id", oid));
		list.add(new PropertyParam("onchange", onchange));
		list.add(new PropertyParam("style", style));
		writer.append(PropertyParam.renderProperty(list));
		writer.append(">");
		List<HkTag> tagList = this.getChildTagList();
		for (HkTag tag : tagList) {
			writer.append("<option");
			List<PropertyParam> list2 = new ArrayList<PropertyParam>();
			Option otag = (Option) tag;
			list2.add(new PropertyParam("value", otag.getValue()));
			if (this.equalValue(otag.getValue(), this.checkedvalue)) {
				list2.add(new PropertyParam("selected", "selected"));
			}
			else if (this.equalValue(otag.getValue(), forcecheckedvalue)) {
				list2.add(new PropertyParam("selected", "selected"));// 强制设为默认值
			}
			writer.append(PropertyParam.renderProperty(list2));
			writer.append(">");
			if (otag.getData() != null) {
				writer.append(otag.getData().toString());
			}
			writer.append("</option>");
		}
		this.clearChildTagList();
		writer.append("</select>");
	}

	public Object getName() {
		return name;
	}

	public void setName(Object name) {
		this.name = name;
	}

	public Object getClazz() {
		return clazz;
	}

	public void setClazz(Object clazz) {
		this.clazz = clazz;
	}

	public Object getCheckedvalue() {
		return checkedvalue;
	}

	public void setCheckedvalue(Object checkedvalue) {
		this.checkedvalue = checkedvalue;
	}

	public Object getOnchange() {
		return onchange;
	}

	public void setOnchange(Object onchange) {
		this.onchange = onchange;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Object getForcecheckedvalue() {
		return forcecheckedvalue;
	}

	public void setForcecheckedvalue(Object forcecheckedvalue) {
		this.forcecheckedvalue = forcecheckedvalue;
	}
}