package com.hk.frame.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.hk.frame.util.HkUtil;
import com.hk.frame.util.TokenUtil;

/**
 * 创建Token隐藏值
 * 
 * @author jyy
 */
public class TokenTag extends BaseBodyTag {

	private static final long serialVersionUID = -4061267517131524511L;

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		StringBuilder sb = new StringBuilder("<input type=\"hidden\" name=\"");
		sb.append(HkUtil.REQ_TOKEN_KEY).append("\" ");
		sb.append("value=\"").append(TokenUtil.saveToken(this.getRequest()))
				.append("\"/>");
		writer.append(sb.toString());
	}
}