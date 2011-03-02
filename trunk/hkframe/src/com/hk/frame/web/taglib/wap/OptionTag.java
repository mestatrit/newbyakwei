package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspWriter;

import com.hk.frame.util.ResourceConfig;
import com.hk.frame.util.i18n.HkI18n;

public class OptionTag extends BaseWapTag {

	private static final long serialVersionUID = -1801602826391447801L;

	protected Object data;

	protected Object value;

	private boolean res;

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		if (res) {
			Locale locale = (Locale) this.getRequest().getAttribute(
					HkI18n.I18N_KEY);
			if (locale == null) {
				locale = Locale.SIMPLIFIED_CHINESE;
			}
			String v = null;
			if (data != null) {
				v = data.toString();
			}
			this.setChildInRequest(new Option(value, ResourceConfig.getText(
					locale, v)));
		}
		else {
			this.setChildInRequest(new Option(value, data));
		}
	}

	public void setRes(boolean res) {
		this.res = res;
	}

	static class Option extends OptionTag {

		private static final long serialVersionUID = -311905930326800146L;

		public Option(Object value, Object data) {
			this.value = value;
			this.data = data;
		}
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isRes() {
		return res;
	}
}