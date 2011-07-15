package iwant.web.api;

import halo.util.ResourceConfig;
import halo.util.VelocityUtil;
import halo.web.action.HkResponse;
import halo.web.util.ServletUtil;
import iwant.web.admin.util.Err;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

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

	public static String writeErr(HkResponse resp, String err) {
		VelocityContext context = new VelocityContext();
		context.put("errcode", err);
		context.put("err_msg", ResourceConfig.getText(err));
		APIUtil.write(resp, "vm/syserr.vm", context);
		return null;
	}

	public static String writeSuccess(HkResponse resp) {
		VelocityContext context = new VelocityContext();
		context.put("errcode", Err.SUCCESS);
		context.put("err_msg", ResourceConfig.getText(Err.SUCCESS));
		APIUtil.write(resp, "vm/syserr.vm", context);
		return null;
	}

	public static String writeData(HttpServletResponse response,
			Map<String, Object> map, String vmPath) {
		VelocityContext context = new VelocityContext();
		Set<Entry<String, Object>> set = map.entrySet();
		for (Entry<String, Object> e : set) {
			context.put(e.getKey(), e.getValue());
		}
		context.put("errcode", Err.SUCCESS);
		context.put("err_msg", "");
		APIUtil.write(response, vmPath, context);
		return null;
	}
}