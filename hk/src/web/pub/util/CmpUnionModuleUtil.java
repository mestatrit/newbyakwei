package web.pub.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class CmpUnionModuleUtil {
	public static final int MODULE_GONGGAO = 1;

	public static final int MODULE_ACT = 2;

	public static final int MODULE_BOX = 3;

	public static final int MODULE_COUPON = 4;

	public static final int MODULE_KIND = 5;

	public static final int MODULE_LINK = 6;

	public static String defaultData = "1:0;2:0;3:0;4:0;5:0;6:0";

	public static final String DATA_ATTR = "site_data";

	public static String getDefaultOrder() {
		return defaultData;
	}

	public static void buildSite(HttpServletRequest request) {
		CmpUnionSite cmpUnionSite = (CmpUnionSite) request
				.getAttribute("cmpUnionSite");
		List<String> indexmodvaluelist = new ArrayList<String>();
		String value = null;
		for (CmpUnionSiteOrder order : cmpUnionSite.getCmpUnionSiteOrderList()) {
			value = (String) request.getAttribute("index_" + order.getModule());
			if (value != null) {
				indexmodvaluelist.add(value);
			}
		}
		request.setAttribute("indexmodvaluelist", indexmodvaluelist);
	}
}