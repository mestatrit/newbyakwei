package iwant.web.api;

import iwant.bean.User;
import iwant.bean.enumtype.GenderType;
import iwant.svr.UserSvr;

import java.util.Date;

import com.hk.frame.util.HkUtil;
import com.hk.frame.web.action.Action;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

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