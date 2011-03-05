package tuxiazi.webapi;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.User;
import tuxiazi.svr.iface.UserService;
import tuxiazi.web.util.APIUtil;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/api/validateuser")
public class ValidateUserAction extends BaseApiAction {

	@Autowired
	private UserService userService;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long userid = req.getLong("userid");
		String access_token = req.getString("access_token");
		String token_secret = req.getString("token_secret");
		if (userid <= 0) {
			return this.validateFail(resp);
		}
		Api_user_sina apiUserSina = userService
				.getApi_user_sinaByUserid(userid);
		if (apiUserSina == null) {
			return this.validateFail(resp);
		}
		if (!apiUserSina.getAccess_token().equals(access_token)
				|| !apiUserSina.getToken_secret().equals(token_secret)) {
			return this.validateFail(resp);
		}
		User user = userService.getUser(apiUserSina.getUserid());
		if (user == null) {
			return this.validateFail(resp);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("validate", true);
		APIUtil.writeData(resp, map, "vm/validateuser.vm");
		return null;
	}

	private String validateFail(HkResponse resp) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("validate", false);
		APIUtil.writeData(resp, map, "vm/validateuser.vm");
		return null;
	}
}
