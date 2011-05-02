package cactus.web.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.JspWriter;

import cactus.util.DataUtil;
import cactus.util.ResourceConfig;
import cactus.web.action.HkI18n;

public class CheckBoxTag extends BaseWapTag {

	private static final long serialVersionUID = 4901343569123173841L;

	private String name;

	private String clazz;

	private Object value;

	private Object checkedvalue;

	private String data;

	protected boolean res;

	private String checkedvalues;

	@Override
	protected void exeTag(JspWriter writer) throws IOException {
		writer.append("<input");
		List<PropertyParam> list = new ArrayList<PropertyParam>();
		if (oid != null) {
			list.add(new PropertyParam("id", oid));
		}
		list.add(new PropertyParam("name", name));
		list.add(new PropertyParam("type", "checkbox"));
		list.add(new PropertyParam("class", clazz));
		list.add(new PropertyParam("value", value));
		if (!DataUtil.isEmpty(checkedvalues)) {
			String[] ts = this.checkedvalues.split(",");
			for (String s : ts) {
				if (this.equalValue(this.value, s)) {
					list.add(new PropertyParam("checked", "checked"));
				}
			}
		}
		else {
			if (this.equalValue(value, checkedvalue)) {
				list.add(new PropertyParam("checked", "checked"));
			}
		}
		writer.append(PropertyParam.renderProperty(list));
		writer.append("/>");
		if (data != null) {
			writeData(writer);
		}
	}

	private void writeData(JspWriter writer) throws IOException {
		if (isRes()) {
			Locale locale = (Locale) this.getRequest().getAttribute(
					HkI18n.I18N_KEY);
			if (locale == null) {
				locale = Locale.SIMPLIFIED_CHINESE;
			}
			writer.append(ResourceConfig.getText(locale, data));
		}
		else {
			writer.append(data);
		}
	}

	public void setRes(boolean res) {
		this.res = res;
	}

	public boolean isRes() {
		return res;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setCheckedvalue(Object checkedvalue) {
		this.checkedvalue = checkedvalue;
	}

	public void setCheckedvalues(String checkedvalues) {
		this.checkedvalues = checkedvalues;
	}
}