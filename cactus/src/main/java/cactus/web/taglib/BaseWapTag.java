package cactus.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

public abstract class BaseWapTag extends BaseBodyTag {

	public static final String CSS_CONTENT_ATTR = "com.hk.frame.web.taglib.wap.csstag.csscontent";

	public static final String CSS_IMPORT_ATTR = "com.hk.frame.web.taglib.wap.importcsstag.import";

	public static final String BODY_OTHERBODYPARAM = "otherBodyParam";

	public static final String HK_HEAD_INNER_CONTENT = "hk_head_inner_content";

	public static final String HK_META_DATA = "hk_meta_data";

	private static final long serialVersionUID = -4669121910331334124L;

	protected Object oid;

	public void setOid(Object oid) {
		this.oid = oid;
	}

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		exeTag(writer);
	}

	protected void exeTag(JspWriter writer) throws IOException {
		renderBodyContent(writer);
	}
}