package tuxiazi.web.util;

import halo.util.ResourceConfig;
import halo.util.VelocityUtil;
import halo.web.action.HkResponse;
import halo.web.util.ServletUtil;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import tuxiazi.util.Err;

public class APIUtil {

	public static String getErrMsg(int err) {
		return ResourceConfig.getTextFromResource("err", String.valueOf(err));
	}

	public static void write(HttpServletResponse response, String vmpath,
			VelocityContext context) {
		try {
			ServletUtil.sendXml(response,
					VelocityUtil.writeToString(vmpath, context));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeErr(HkResponse resp, int err) {
		VelocityContext context = new VelocityContext();
		context.put("errcode", err);
		context.put("err_msg",
				ResourceConfig.getTextFromResource("err", String.valueOf(err)));
		APIUtil.write(resp, "vm/sinaerr.vm", context);
	}

	public static void writeSuccess(HkResponse resp) {
		VelocityContext context = new VelocityContext();
		context.put("errcode", Err.SUCCESS);
		context.put(
				"err_msg",
				ResourceConfig.getTextFromResource("err",
						String.valueOf(Err.SUCCESS)));
		APIUtil.write(resp, "vm/sinaerr.vm", context);
	}

	public static void writeData(HttpServletResponse response,
			Map<String, Object> map, String vmPath) {
		VelocityContext context = new VelocityContext();
		Set<Entry<String, Object>> set = map.entrySet();
		for (Entry<String, Object> e : set) {
			context.put(e.getKey(), e.getValue());
		}
		context.put("err", Err.SUCCESS);
		APIUtil.write(response, vmPath, context);
	}
}