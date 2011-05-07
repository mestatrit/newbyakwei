package iwant.web.api;

import iwant.web.admin.util.Err;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import cactus.util.ResourceConfig;
import cactus.util.VelocityUtil;
import cactus.web.action.HkResponse;
import cactus.web.util.ServletUtil;

public class APIUtil {

	public static void write(HttpServletResponse response, String vmpath,
			VelocityContext context) {
		try {
			ServletUtil.sendXml(response, VelocityUtil.writeToString(vmpath,
					context));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void writeErr(HkResponse resp, String err) {
		VelocityContext context = new VelocityContext();
		context.put("errcode", err);
		context.put("err_msg", ResourceConfig.getText(err));
		APIUtil.write(resp, "vm/syserr.vm", context);
	}

	public static void writeSuccess(HkResponse resp) {
		VelocityContext context = new VelocityContext();
		context.put("errcode", Err.SUCCESS);
		context.put("err_msg", ResourceConfig.getText(Err.SUCCESS));
		APIUtil.write(resp, "vm/syserr.vm", context);
	}

	public static void writeData(HttpServletResponse response,
			Map<String, Object> map, String vmPath) {
		VelocityContext context = new VelocityContext();
		Set<Entry<String, Object>> set = map.entrySet();
		for (Entry<String, Object> e : set) {
			context.put(e.getKey(), e.getValue());
		}
		context.put("errcode", Err.SUCCESS);
		context.put("err_msg", "");
		APIUtil.write(response, vmPath, context);
	}
}