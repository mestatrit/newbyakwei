package com.hk.api.util;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.velocity.VelocityContext;
import com.hk.frame.util.ResourceConfig;
import com.hk.frame.util.ServletUtil;
import com.hk.svr.pub.Err;

public class APIUtil {
	public static String status_fail = "fail";

	public static String status_success = "ok";

	public static final String SESSIONKEY_ATTR = "api.hk.sessoinkey";

	public static final String APIUSER_ATTR = "api.hk.apiuser";

	public static SessionKey getSessionKey(HttpServletRequest request) {
		return (SessionKey) request.getAttribute(APIUtil.SESSIONKEY_ATTR);
	}

	public static void sendRespStatus(HttpServletResponse resp, String status,
			int code) {
		VelocityContext context = new VelocityContext();
		context.put("status", status);
		context.put("code", code);
		context.put("msg", ResourceConfig.getText(code + ""));
		ServletUtil.writeValue(resp, "vm/respstatus.vm", context);
	}

	public static void sendFailRespStatus(HttpServletResponse resp, int code) {
		VelocityContext context = new VelocityContext();
		context.put("status", status_fail);
		context.put("code", code);
		context.put("msg", ResourceConfig.getText(code + ""));
		ServletUtil.writeValue(resp, "vm/respstatus.vm", context);
	}

	public static void sendSuccessRespStatus(HttpServletResponse resp) {
		VelocityContext context = new VelocityContext();
		context.put("status", status_success);
		context.put("code", Err.SUCCESS);
		context.put("msg", "");
		ServletUtil.writeValue(resp, "vm/respstatus.vm", context);
	}

	public static void sendSuccessRespStatus(HttpServletResponse resp,
			Map<String, Object> map) {
		VelocityContext context = new VelocityContext();
		context.put("status", status_success);
		context.put("code", Err.SUCCESS);
		context.put("msg", "");
		Set<Entry<String, Object>> set = map.entrySet();
		for (Entry<String, Object> e : set) {
			context.put(e.getKey(), e.getValue());
		}
		ServletUtil.writeValue(resp, "vm/respstatus.vm", context);
	}
}