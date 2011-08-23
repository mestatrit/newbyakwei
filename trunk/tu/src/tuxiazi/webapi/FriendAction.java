package tuxiazi.webapi;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;
import halo.web.util.SimplePage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	private final Log log = LogFactory.getLog(FriendAction.class);

	/**
	 * 创建关注关系
	 * 
	 * @param req
	 * @param resp
	 * @return 0:提交成功<br/>
	 *         4:系统错误<br/>
	 *         9:用户不存在
	 */
	public String prvcreate(HkRequest req, HkResponse resp) {
		try {
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
			User _user = this.userDao.getById(user.getUserid());
			User _friendUser = this.userDao.getById(uid);
			this.friendService.createFriend(_user, _friendUser, true, true);
			APIUtil.writeSuccess(resp);
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}

	/**
	 * 取消关注关系
	 * 
	 * @param req
	 * @param resp
	 * @return 0:操作成功<br/>
	 *         4:系统出错
	 */
	public String prvdelete(HkRequest req, HkResponse resp) {
		try {
			long uid = req.getLong("uid");
			User user = this.getUser(req);
			User _u = this.userDao.getById(user.getUserid());
			User _f = this.userDao.getById(uid);
			this.friendService.deleteFriend(_u, _f, true);
			APIUtil.writeSuccess(resp);
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}

	/**
	 * 查看关注列表<br/>
	 * 成功返回数据列表<br/>
	 * 4:系统出错
	 */
	@Override
	public String execute(HkRequest req, HkResponse resp) {
		try {
			long relationUserid = 0;
			User loginUser = this.getUser(req);
			long uid = req.getLong("uid");
			if (loginUser != null) {
				relationUserid = loginUser.getUserid();
			}
			int page = req.getInt("page", 1);
			int size = req.getInt("size", 20);
			SimplePage simplePage = new SimplePage(size, page);
			List<Friend> list = this.friendDao
					.getListByUserid(uid, true, relationUserid,
							simplePage.getBegin(), simplePage.getSize());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			APIUtil.writeData(resp, map, "vm/friendlist.vm");
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}

	/**
	 * 查看粉丝列表
	 * 
	 * @param req
	 * @param resp
	 * @return 成功返回数据列表<br/>
	 *         4:系统出错
	 */
	public String fans(HkRequest req, HkResponse resp) {
		try {
			long relationUserid = 0;
			User loginUser = this.getUser(req);
			long uid = req.getLong("uid");
			if (loginUser != null && loginUser.getUserid() != uid) {
				relationUserid = loginUser.getUserid();
			}
			int page = req.getInt("page", 1);
			int size = req.getInt("size", 20);
			SimplePage simplePage = new SimplePage(size, page);
			List<Fans> list = this.fansDao
					.getListByUserid(uid, true, relationUserid,
							simplePage.getBegin(), simplePage.getSize());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			APIUtil.writeData(resp, map, "vm/fanslist.vm");
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}
}