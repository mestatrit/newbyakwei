package tuxiazi.web.util;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import tuxiazi.util.Err;

import com.hk.frame.util.ServletUtil;
import com.hk.frame.util.VelocityUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

public class APIUtil {

	public static String getErrMsg(HkRequest req, int err) {
		return req.getText(String.valueOf(err));
	}

	public static void write(HttpServletResponse response, String vmpath,
			VelocityContext context) {
		try {
			ServletUtil.sendXml2(response, VelocityUtil.writeToString(vmpath,
					context));
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeErr(HkRequest req, HkResponse resp, int err) {
		VelocityContext context = new VelocityContext();
		context.put("errcode", err);
		context.put("err_msg", req.getText(String.valueOf(err)));
		APIUtil.write(resp, "vm/sinaerr.vm", context);
	}

	public static void writeSuccess(HkRequest req, HkResponse resp) {
		VelocityContext context = new VelocityContext();
		context.put("errcode", Err.SUCCESS);
		context.put("err_msg", req.getText(String.valueOf(Err.SUCCESS)));
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