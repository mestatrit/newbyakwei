package iwant.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BackUrlUtil {

	private static final String backUrl_key = "back_url_key";

	private static final int back_size = 5;

	public static BackUrl getBackUrl(HttpServletRequest request,
			HttpServletResponse response) {
		return new BackUrl(backUrl_key, back_size, request, response);
	}
}