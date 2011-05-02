package cactus.web.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

import cactus.util.ResourceConfig;

public class RadioTag extends BaseWapTag {

	private static final long serialVersionUID = 2292675869286958062L;

	protected Object value;

	protected Object data;

	protected boolean res;

	protected RadioAreaTag getRadioAreaTag(Tag tag) {
		if (tag instanceof RadioAreaTag) {
			return (RadioAreaTag) tag;
		}
		return getRadioAreaTag(tag.getParent());
	}

	@Override
	protected void exeTag(JspWriter writer) throws IOException {
		RadioAreaTag parent = this.getRadioAreaTag(this.getParent());
		Object name = parent.getName();
		Object clazz = parent.getClazz();
		Object checkedValue = parent.getCheckedvalue();
		Object forcecheckedvalue = parent.getForcecheckedvalue();
		boolean dateBefore = parent.isDatabefore();
		if (dateBefore) {
			writeData(writer);
		}
		writer.append("<input");
		List<PropertyParam> list = new ArrayList<PropertyParam>();
		if (this.oid != null) {
			list.add(new PropertyParam("id", this.oid));
		}
		list.add(new PropertyParam("name", name));
		list.add(new PropertyParam("type", "radio"));
		list.add(new PropertyParam("class", clazz));
		list.add(new PropertyParam("value", value));
		if (this.equalValue(value, checkedValue)) {
			list.add(new PropertyParam("checked", "checked"));
		}
		else if (this.equalValue(value, forcecheckedvalue)) {
			list.add(new PropertyParam("checked", "checked"));// 强制设为默认值
		}
		writer.append(PropertyParam.renderProperty(list));
		writer.append("/>");
		if (!dateBefore) {
			writeData(writer);
		}
	}

	private void writeData(JspWriter writer) throws IOException {
		if (isRes() && data != null) {
			writer.append(ResourceConfig.getText(data.toString()));
		}
		else if (data != null) {
			writer.append(data.toString());
		}
	}

	public void setRes(boolean res) {
		this.res = res;
	}

	public boolean isRes() {
		return res;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	static class Radio extends RadioTag {

		private static final long serialVersionUID = -6801093425638707905L;

		public Radio(Object data, Object value) {
			this.data = data;
			this.value = value;
		}
	}
}