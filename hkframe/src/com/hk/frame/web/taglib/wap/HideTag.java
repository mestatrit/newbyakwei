package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import com.hk.frame.util.DataUtil;

public class HideTag extends BaseWapTag {
	private static final long serialVersionUID = -7636803970255173785L;

	private String name;

	private Object value;

	private boolean decode;

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(Object value) {
		this.value = value;
		if (this.value == null) {
			this.value = "";
		}
	}

	public void setDecode(boolean decode) {
		this.decode = decode;
	}

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		writer.append("<input ");
		writer.append("type=\"hidden\" name=\"");
		writer.append(name);
		writer.append("\" value=\"");
		if (decode) {
			writer.append(DataUtil.urlDecoder(value.toString()));
		}
		else {
			writer.append(value.toString());
		}
		writer.append("\"/>");
	}
}
