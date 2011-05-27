package com.dev3g.cactus.web.tablib;

import javax.servlet.jsp.JspWriter;

import com.dev3g.cactus.util.HkUtil;
import com.dev3g.cactus.web.action.ActionExe;

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
				HkUtil.ACTION_EXE_ATTR_KEY);
		if (actionExe == null) {
			return;
		}
		actionExe.invoke(mappinguri, this.getRequest(), this.getResponse());
	}
}