package com.hk.frame.web.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class XMLRequest extends HttpServletRequestWrapper {
	private final Map<String, String> paraMap = new HashMap<String, String>();

	public XMLRequest(HttpServletRequest request) {
		super(request);
		this.buildXmlParameter(request);
	}

	@SuppressWarnings("unchecked")
	protected void buildXmlParameter(HttpServletRequest request) {
		BufferedReader reader = null;
		InputStream is = null;
		StringBuffer xml = new StringBuffer();
		try {
			reader = request.getReader();
			String line = null;
			while ((line = reader.readLine()) != null) {
				xml.append(line);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			is = new ByteArrayInputStream(xml.toString().getBytes("UTF-8"));
		}
		catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		SAXBuilder sb = new SAXBuilder();
		Document doc = null;
		try {
			doc = sb.build(is);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			try {
				if (is != null) {
					is.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		Element root = doc.getRootElement();
		List<Element> el = root.getChildren();
		for (Element e : el) {
			this.paraMap.put(e.getName(), e.getValue());
		}
	}
}