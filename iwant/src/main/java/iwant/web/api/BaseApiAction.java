package iwant.web.api;

import halo.util.HaloUtil;
import halo.web.action.Action;
import halo.web.action.HkRequest;
import halo.web.action.HkResponse;
import iwant.bean.User;
import iwant.bean.enumtype.GenderType;
import iwant.svr.UserSvr;

import java.util.Date;

public class BaseApiAction implements Action {

	protected User loadUser(String device_token) {
		UserSvr userSvr = (UserSvr) HaloUtil.getBean("userSvr");
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