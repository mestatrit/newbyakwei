package tuxiazi.webapi;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;
import halo.web.util.SimplePage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Fans;
import tuxiazi.bean.Friend;
import tuxiazi.bean.User;
import tuxiazi.dao.FansDao;
import tuxiazi.dao.FriendDao;
import tuxiazi.dao.UserDao;
import tuxiazi.svr.iface.FriendService;
import tuxiazi.util.Err;
import tuxiazi.web.util.APIUtil;

@Component("/api/friend")
public class FriendAction extends BaseApiAction {

	@Autowired
	private FriendService friendService;

	@Autowired
	private FriendDao friendDao;

	@Autowired
	private FansDao fansDao;

	@Autowired
	private UserDao userDao;

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
			APIUtil.writeErr(resp, Err.USER_NOTEXIST);
			return null;
		}
		User user2 = this.userDao.getById(uid);
		if (user2 == null) {
			APIUtil.writeErr(resp, Err.USER_NOTEXIST);
			return null;
		}
		User user = this.getUser(req);
		Friend friend = new Friend();
		friend.setUserid(user.getUserid());
		friend.setFriendid(uid);
		this.friendService.createFriend(friend, true, true);
		APIUtil.writeSuccess(resp);
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
		User _u = this.userDao.getById(user.getUserid());
		User _f = this.userDao.getById(uid);
		this.friendService.deleteFriend(_u, _f, true);
		APIUtil.writeSuccess(resp);
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
		List<Friend> list = this.friendDao.getListByUserid(uid, true,
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
		List<Fans> list = this.fansDao.getListByUserid(uid, true,
				relationUserid, simplePage.getBegin(), simplePage.getSize());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		APIUtil.writeData(resp, map, "vm/fanslist.vm");
		return null;
	}
}