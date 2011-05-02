package cactus.web.taglib;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;

public abstract class BaseHkTag extends BaseBodyTag {
	private static final long serialVersionUID = 5207627802663307352L;

	@Override
	public abstract void adapter(JspWriter writer) throws IOException;
}