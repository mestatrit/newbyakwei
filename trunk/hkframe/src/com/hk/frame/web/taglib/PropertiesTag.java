package com.hk.frame.web.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.JspWriter;

import com.hk.frame.util.ResourceConfig;
import com.hk.frame.util.i18n.HkI18n;

public class PropertiesTag extends BaseBodyTag {

	private static final long serialVersionUID = -2452461789404041648L;

	private int DEF_SIZE = 4;

	private String key;

	private String arg0;

	private String arg1;

	private String arg2;

	private String arg3;

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		String res = null;
		Locale locale = (Locale) this.getRequest()
				.getAttribute(HkI18n.I18N_KEY);
		if (locale == null) {
			locale = Locale.SIMPLIFIED_CHINESE;
		}
		if (arg0 == null && arg1 == null && arg2 == null && arg3 == null) {
			res = ResourceConfig.getText(locale, key);
		}
		else {
			res = ResourceConfig.getText(locale, key, this.buildArg());
		}
		writer.append(res);
	}

	private Object[] buildArg() {
		List<String> argList = new ArrayList<String>(DEF_SIZE);
		if (isNotEmpty(arg0)) {
			argList.add(arg0);
		}
		if (isNotEmpty(arg1)) {
			argList.add(arg1);
		}
		if (isNotEmpty(arg2)) {
			argList.add(arg2);
		}
		if (isNotEmpty(arg3)) {
			argList.add(arg3);
		}
		String[] v = argList.toArray(new String[DEF_SIZE]);
		return v;
	}

	private boolean isNotEmpty(String value) {
		if (value == null || value.trim().length() == 0) {
			return false;
		}
		return true;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setArg0(String arg0) {
		this.arg0 = arg0;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}

	public void setArg3(String arg3) {
		this.arg3 = arg3;
	}
}