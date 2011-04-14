package tuxiazi.webapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Fans;
import tuxiazi.bean.Friend;
import tuxiazi.bean.User;
import tuxiazi.svr.iface.FriendService;
import tuxiazi.svr.iface.UserService;
import tuxiazi.util.Err;
import tuxiazi.web.util.APIUtil;

import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/api/friend")
public class FriendAction extends BaseApiAction {

	@Autowired
	private FriendService friendService;

	@Autowired
	private UserService userService;

	/**
	 * 创建关注关系
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String prvcreate(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		if (uid <= 0) {
			APIUtil.writeErr(req, resp, Err.USER_NOTEXIST);
			return null;
		}
		User user2 = this.userService.getUser(uid);
		if (user2 == null) {
			APIUtil.writeErr(req, resp, Err.USER_NOTEXIST);
			return null;
		}
		User user = this.getUser(req);
		Friend friend = new Friend();
		friend.setUserid(user.getUserid());
		friend.setFriendid(uid);
		this.friendService.createFriend(friend, true, true);
		APIUtil.writeSuccess(req, resp);
		return null;
	}

	/**
	 * 取消关注关系
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String prvdelete(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		User user = this.getUser(req);
		this.friendService.deleteFriend(user.getUserid(), uid, true);
		APIUtil.writeSuccess(req, resp);
		return null;
		// will
		// long uid = req.getLong("uid");
		// User user = this.getUser(req);
		// User dbUser = this.userService.getUser(user.getUserid());
		// User friendUser = this.userService.getUser(uid);
		// if (dbUser == null || friendUser == null) {
		// APIUtil.writeErr(req, resp, Err.USER_NOTEXIST);
		// }
		// this.friendService.deleteFriend(user, friendUser, true);
		// APIUtil.writeSuccess(req, resp);
		// return null;
	}

	@Override
	public String execute(HkRequest req, HkResponse resp) {
		long relationUserid = 0;
		User loginUser = this.getUser(req);
		long uid = req.getLong("uid");
		if (loginUser != null) {
			relationUserid = loginUser.getUserid();
		}
		int page = req.getInt("page", 1);
		int size = req.getInt("size", 20);
		SimplePage simplePage = new SimplePage(size, page);
		List<Friend> list = this.friendService.getFriendListByUserid(uid, true,
				relationUserid, simplePage.getBegin(), simplePage.getSize());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		APIUtil.writeData(resp, map, "vm/friendlist.vm");
		return null;
	}

	public String fans(HkRequest req, HkResponse resp) {
		long relationUserid = 0;
		User loginUser = this.getUser(req);
		long uid = req.getLong("uid");
		if (loginUser != null && loginUser.getUserid() != uid) {
			relationUserid = loginUser.getUserid();
		}
		int page = req.getInt("page", 1);
		int size = req.getInt("size", 20);
		SimplePage simplePage = new SimplePage(size, page);
		List<Fans> list = this.friendService.getFansListByUserid(uid, true,
				relationUserid, simplePage.getBegin(), simplePage.getSize());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		APIUtil.writeData(resp, map, "vm/fanslist.vm");
		return null;
	}
}