package halo.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceConfig {

	private static String resource;

	public void setResource(String resource) {
		ResourceConfig.resource = resource;
	}

	public static String getText(Locale locale, String key, Object... args) {
		ResourceBundle rb = ResourceBundle.getBundle(resource, locale);
		if (args == null) {
			try {
				return rb.getString(key);
			}
			catch (Exception e) {
				P.println("resource no mapping [ " + key + " ]");
				return key;
			}
		}
		try {
			String value = rb.getString(key);
			if (value == null) {
				return key;
			}
			return MessageFormat.format(value, args);
		}
		catch (Exception e) {
			P.println("************** begin *************************");
			P.println(e.getMessage());
			P.println("resource no mapping [ " + key + " ]");
			P.println("*************** end **************************");
			return key;
		}
	}

	public static String getText(String key, Object... args) {
		Locale locale = Locale.SIMPLIFIED_CHINESE;
		return getText(locale, key, args);
	}

	public static String getTextFromResource(String resource, String key,
			Object... args) {
		ResourceBundle rb = ResourceBundle.getBundle(resource);
		if (args == null) {
			return rb.getString(key);
		}
		String value = rb.getString(key);
		if (value == null) {
			return key;
		}
		return MessageFormat.format(value, args);
	}
}