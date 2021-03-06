package tuxiazi.webapi;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.User;
import tuxiazi.dao.FriendDao;
import tuxiazi.dao.UserDao;
import tuxiazi.util.Err;
import tuxiazi.web.util.APIUtil;

@Component("/api/user")
public class UserAction extends BaseApiAction {

	@Autowired
	private FriendDao friendDao;

	@Autowired
	private UserDao userDao;

	private final Log log = LogFactory.getLog(UserAction.class);

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		try {
			long uid = req.getLong("uid");
			User user = this.userDao.getById(uid);
			if (user == null) {
				APIUtil.writeErr(resp, Err.USER_NOTEXIST);
				return null;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user", user);
			boolean isfriend = false;
			User loginUser = this.getUser(req);
			if (loginUser != null && loginUser.getUserid() != uid) {
				if (this.friendDao.getByUseridAndFriendid(
						loginUser.getUserid(), uid) != null) {
					isfriend = true;
				}
			}
			map.put("isfriend", isfriend);
			APIUtil.writeData(resp, map, "vm/user.vm");
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}
}