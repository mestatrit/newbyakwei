package tuxiazi.webapi;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.User;
import tuxiazi.svr.iface.FriendService;
import tuxiazi.svr.iface.UserService;
import tuxiazi.util.Err;
import tuxiazi.web.util.APIUtil;

@Component("/api/user")
public class UserAction extends BaseApiAction {

	@Autowired
	private UserService userService;

	@Autowired
	private FriendService friendService;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLong("uid");
		User user = this.userService.getUser(uid);
		if (user == null) {
			APIUtil.writeErr(resp, Err.USER_NOTEXIST);
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		boolean isfriend = false;
		User loginUser = this.getUser(req);
		if (loginUser != null && loginUser.getUserid() != uid) {
			if (this.friendService.getFriendByUseridAndFriendid(
					loginUser.getUserid(), uid) != null) {
				isfriend = true;
			}
		}
		map.put("isfriend", isfriend);
		APIUtil.writeData(resp, map, "vm/user.vm");
		return null;
	}
}