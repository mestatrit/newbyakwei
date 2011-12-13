package halo.web.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class HaloResource {

	private static String resource = "resource";

	public static String getText(Locale locale, String resource, String key,
			Object... args) {
		ResourceBundle rb = ResourceBundle.getBundle(resource, locale);
		String value;
		try {
			if (args == null) {
				return rb.getString(key);
			}
			value = rb.getString(key);
		}
		catch (Exception e) {
			return key;
		}
		if (value == null) {
			return key;
		}
		return MessageFormat.format(value, args);
	}

	public static String getDefText(Locale locale, String key, Object... args) {
		ResourceBundle rb = ResourceBundle.getBundle(resource, locale);
		if (args == null) {
			try {
				return rb.getString(key);
			}
			catch (Exception e) {
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
			return key;
		}
	}

	public static String getDefText(String key, Object... args) {
		Locale locale = Locale.SIMPLIFIED_CHINESE;
		return getDefText(locale, key, args);
	}
}