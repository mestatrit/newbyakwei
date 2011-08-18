package tuxiazi.webapi;

import halo.util.ResourceConfig;
import halo.util.VelocityUtil;
import halo.web.action.Action;
import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import org.apache.velocity.VelocityContext;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.User;

public class BaseApiAction implements Action {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected User getUser(HkRequest req) {
		return (User) req.getAttribute("user");
	}

	protected Api_user_sina getApiUserSina(HkRequest req) {
		return (Api_user_sina) req.getAttribute("apiUserSina");
	}

	protected boolean isForwardPage(HkRequest req) {
		if (req.getInt("ch") == 0) {
			return true;
		}
		return false;
	}

	protected int getSize(HkRequest req) {
		int size = req.getInt("size");
		if (size > 20) {
			size = 20;
		}
		if (size <= 0) {
			size = 10;
		}
		return size;
	}

	protected String getUser_key(HkRequest req) {
		return req.getString("user_key");
	}

	protected void write(HkResponse resp, String vmpath, VelocityContext context) {
		try {
			resp.sendXML(VelocityUtil.writeToString(vmpath, context));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected String getErrMsg(int err) {
		return ResourceConfig.getTextFromResource("err", String.valueOf(err));
	}
}