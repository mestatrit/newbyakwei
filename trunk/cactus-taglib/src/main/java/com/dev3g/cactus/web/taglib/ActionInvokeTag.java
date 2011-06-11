package com.dev3g.cactus.web.taglib;

import javax.servlet.jsp.JspWriter;

import com.dev3g.cactus.web.action.ActionExe;
import com.dev3g.cactus.web.action.WebCnf;

public class ActionInvokeTag extends BaseTag {

	private static final long serialVersionUID = -1283364414996755943L;

	private String mappinguri;

	public void setMappinguri(String mappinguri) {
		this.mappinguri = mappinguri;
	}

	public String getMappinguri() {
		return mappinguri;
	}

	@Override
	protected void adapter(JspWriter writer) throws Exception {
		ActionExe actionExe = (ActionExe) this.getRequest().getAttribute(
				WebCnf.ACTION_EXE_ATTR_KEY);
		if (actionExe == null) {
			return;
		}
		actionExe.invoke(mappinguri, this.getRequest(), this.getResponse());
	}
}