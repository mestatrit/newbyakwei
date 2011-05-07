package iwant.web.api;

import iwant.bean.User;
import iwant.bean.enumtype.GenderType;
import iwant.svr.UserSvr;

import java.util.Date;

import cactus.util.HkUtil;
import cactus.web.action.Action;
import cactus.web.action.HkRequest;
import cactus.web.action.HkResponse;

public class BaseApiAction implements Action {

	protected User loadUser(String device_token) {
		UserSvr userSvr = (UserSvr) HkUtil.getBean("userSvr");
		User user = userSvr.getUserByDevice_token(device_token);
		if (user == null) {
			user = new User();
			user.setDevice_token(device_token);
			user.setCreatetime(new Date());
			user.setEmail("");
			user.setMobile("");
			user.setGender(GenderType.NONE.getValue());
			user.setName("");
			userSvr.createUser(user);
		}
		return user;
	}

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}
}