package cactus.web.taglib;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

import cactus.util.DataUtil;
import cactus.util.ResourceConfig;
import cactus.web.action.HkI18n;

public class OptionTag2 extends BaseTag {

	private static final long serialVersionUID = -1801602826391447801L;

	protected Object data;

	protected Object value;

	private boolean res;

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		SelectTag2 parent = this.getSelectTag(this.getParent());
		writer.append("<option value=\"");
		writer.append(this.value.toString());
		writer.append("\"");
		if (parent.getCheckedvalue() != null && this.value != null
				&& parent.getCheckedvalue().equals(this.value)) {
			writer.append(" selected=\"selected\"");
		}
		writer.append(">");
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
			writer.append(ResourceConfig.getText(locale, v));
		}
		else {
			if (this.data != null) {
				writer
						.append(DataUtil.toHtmlSimpleOneRow(this.data
								.toString()));
			}
		}
		writer.append("</option>");
	}

	protected SelectTag2 getSelectTag(Tag tag) {
		if (tag instanceof SelectTag2) {
			return (SelectTag2) tag;
		}
		return getSelectTag(tag.getParent());
	}

	public void setRes(boolean res) {
		this.res = res;
	}

	static class Option extends OptionTag2 {

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