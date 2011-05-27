package iwant.web.api;

import iwant.bean.User;
import iwant.bean.enumtype.GenderType;
import iwant.svr.UserSvr;
import iwant.web.admin.util.Err;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.dev3g.cactus.web.action.HkRequest;
import com.dev3g.cactus.web.action.HkResponse;

@Lazy
@Component("/api/user")
public class UserAction extends BaseApiAction {

	@Autowired
	private UserSvr userSvr;

	private Log log = LogFactory.getLog(UserAction.class);

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		try {
			String device_token = req.getStringRow("device_token");
			User user = new User();
			user.setDevice_token(device_token);
			user.setCreatetime(new Date());
			user.setEmail("");
			user.setMobile("");
			user.setGender(GenderType.NONE.getValue());
			user.setName("");
			this.userSvr.createUser(user);
			APIUtil.writeSuccess(resp);
		}
		catch (Exception e) {
			log.error(e.getMessage());
			APIUtil.writeErr(resp, Err.USER_CREATE_ERR);
		}
		return null;
	}
}