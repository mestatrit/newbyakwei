package cactus.web.taglib;

import javax.servlet.jsp.JspWriter;

import cactus.util.HkUtil;
import cactus.web.action.ActionExe;

public class ActionInvokeTag extends BaseBodyTag {

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
		actionExe.invoke(this.getRequest(), this.getResponse());
	}
}