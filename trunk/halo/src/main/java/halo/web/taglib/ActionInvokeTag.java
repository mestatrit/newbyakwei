package halo.web.taglib;

import halo.web.action.ActionExe;
import halo.web.util.WebCnf;

import javax.servlet.jsp.JspWriter;

public class ActionInvokeTag extends BaseTag {

	private static final long serialVersionUID = -1283364414996755943L;

	private String mappinguri;

	private boolean flush;

	public void setFlush(boolean flush) {
		this.flush = flush;
	}

	public void setMappinguri(String mappinguri) {
		this.mappinguri = mappinguri;
	}

	public String getMappinguri() {
		return mappinguri;
	}

	@Override
	protected void adapter(JspWriter writer) throws Exception {
		if (flush) {
			writer.flush();
		}
		ActionExe actionExe = WebCnf.getInstance().getActionExe();
		if (actionExe == null) {
			return;
		}
		actionExe.invoke(mappinguri, this.getRequest(), this.getResponse());
	}
}