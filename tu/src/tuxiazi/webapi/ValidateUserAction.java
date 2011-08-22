package tuxiazi.webapi;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.User;
import tuxiazi.dao.Api_user_sinaDao;
import tuxiazi.dao.UserDao;
import tuxiazi.web.util.APIUtil;

@Component("/api/validateuser")
public class ValidateUserAction extends BaseApiAction {

	@Autowired
	private UserDao userDao;

	@Autowired
	private Api_user_sinaDao api_user_sinaDao;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long userid = req.getLong("userid");
		String access_token = req.getString("access_token");
		String token_secret = req.getString("token_secret");
		if (userid <= 0) {
			return this.validateFail(resp);
		}
		Api_user_sina apiUserSina = api_user_sinaDao.getByUserid(userid);
		if (apiUserSina == null) {
			return this.validateFail(resp);
		}
		if (!apiUserSina.getAccess_token().equals(access_token)
				|| !apiUserSina.getToken_secret().equals(token_secret)) {
			return this.validateFail(resp);
		}
		User user = userDao.getById(apiUserSina.getUserid());
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
