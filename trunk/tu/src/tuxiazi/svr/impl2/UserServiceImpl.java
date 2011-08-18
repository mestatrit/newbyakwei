package tuxiazi.svr.impl2;

import halo.util.NumberUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tuxiazi.bean.Api_user;
import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Invitelog;
import tuxiazi.bean.SinaUser;
import tuxiazi.bean.User;
import tuxiazi.bean.Userid;
import tuxiazi.dao.Api_userDao;
import tuxiazi.dao.Api_user_sinaDao;
import tuxiazi.dao.UserDao;
import tuxiazi.dao.UseridDao;
import tuxiazi.svr.iface.InvitelogService;
import tuxiazi.svr.iface.UserService;
import tuxiazi.svr.impl.jms.HkMsgProducer;
import tuxiazi.svr.impl.jms.JmsMsg;
import tuxiazi.svr.impl.jms.JsonKey;
import tuxiazi.web.util.SinaUtil;
import weibo4j.WeiboException;

public class UserServiceImpl implements UserService {

	private HkMsgProducer hkMsgProducer;

	private InvitelogService invitelogService;

	private Api_user_sinaDao api_user_sinaDao;

	private UserDao userDao;

	private Api_userDao api_userDao;

	private UseridDao useridDao;

	@Override
	public void createApi_user_sina(Api_user_sina apiUserSina, String nick,
			String head_path) {
		boolean create = false;
		// 是否存在新浪用户
		Api_user_sina db_obj = this.api_user_sinaDao.getById(null, apiUserSina
				.getSina_userid());
		if (db_obj == null) {
			this.api_user_sinaDao.save(null, apiUserSina);
			create = true;
		}
		else {
			apiUserSina.setUserid(db_obj.getUserid());
		}
		// 检查是否已经有用户信息
		if (apiUserSina.getUserid() == 0) {
			User user = new User();
			user.setUserid(NumberUtil.getLong(this.useridDao.save(null,
					new Userid())));
			user.setNick(nick);
			user.setHead_path(head_path);
			user.setCreate_time(new Date());
			this.userDao.save(null, user);
			// 更新api_user_sina中userid
			apiUserSina.setUserid(user.getUserid());
			this.api_user_sinaDao.update(null, apiUserSina);
		}
		if (this.api_userDao
				.getObject(null, "userid=? and api_type=?", new Object[] {
						apiUserSina.getUserid(), Api_user.API_TYPE_SINA }) == null) {
			Api_user apiUser = new Api_user();
			apiUser.setUserid(apiUserSina.getUserid());
			apiUser.setApi_type(Api_user.API_TYPE_SINA);
			this.api_userDao.save(null, apiUser);
		}
		if (create) {
			JmsMsg jmsMsg = new JmsMsg();
			jmsMsg.setHead(JmsMsg.HEAD_PHOTO_CREATEUSER);
			jmsMsg.addData(JsonKey.userid, String.valueOf(apiUserSina
					.getUserid()));
			jmsMsg.addData(JsonKey.sina_userid, String.valueOf(apiUserSina
					.getSina_userid()));
			jmsMsg.addData(JsonKey.access_token, apiUserSina.getAccess_token());
			jmsMsg.addData(JsonKey.token_secret, apiUserSina.getToken_secret());
			jmsMsg.buildBody();
			this.hkMsgProducer.send(jmsMsg.toMessage());
		}
	}

	@Override
	public void deleteApi_user(Api_user apiUser) {
		if (apiUser.getApi_type() == Api_user.API_TYPE_SINA) {
			this.api_user_sinaDao.delete(null, "userid=?",
					new Object[] { apiUser.getUserid() });
		}
		this.api_userDao.deleteById(apiUser.getOid());
	}

	@Override
	public User getUser(long userid) {
		return this.userDao.getById(null, userid);
	}

	@Override
	public void update(User user) {
		this.userDao.update(null, user);
	}

	@Override
	public void updateApi_user_sina(Api_user_sina apiUserSina) {
		this.api_user_sinaDao.update(null, apiUserSina);
	}

	@Override
	public Api_user getApi_userByUseridAndApi_type(long userid, int apiType) {
		return this.api_userDao.getObject(null, "userid=? and api_type=?",
				new Object[] { userid, apiType });
	}

	@Override
	public Api_user_sina getApi_user_sinaBySina_userid(long sina_userid) {
		return this.api_user_sinaDao.getById(null, sina_userid);
	}

	@Override
	public Api_user_sina getApi_user_sinaByUserid(long userid) {
		return this.api_user_sinaDao.getObject(null, "userid=?",
				new Object[] { userid });
	}

	@Override
	public void addUserPic_numByUserid(long userid, int add) {
		this.userDao.addPi_num(null, userid, add);
	}

	@Override
	public Map<Long, Api_user_sina> getApi_user_sinaMapInSina_userid(
			List<Long> idList, boolean buildUser) {
		if (idList.isEmpty()) {
			return new HashMap<Long, Api_user_sina>(0);
		}
		List<Api_user_sina> list = this.getApi_user_sinaListInSina_userid(
				idList, buildUser);
		Map<Long, Api_user_sina> map = new HashMap<Long, Api_user_sina>();
		for (Api_user_sina o : list) {
			map.put(o.getSina_userid(), o);
		}
		return map;
	}

	@Override
	public List<Api_user_sina> getApi_user_sinaListInSina_userid(
			List<Long> idList, boolean buildUser) {
		if (idList.isEmpty()) {
			return new ArrayList<Api_user_sina>(0);
		}
		List<Api_user_sina> list = this.api_user_sinaDao.getListInField(null,
				"sina_userid", idList);
		if (buildUser) {
			List<Long> id2List = new ArrayList<Long>();
			for (Api_user_sina o : list) {
				id2List.add(o.getUserid());
			}
			List<User> userlist = this.getUserListInId(id2List);
			Map<Long, User> usermap = new HashMap<Long, User>();
			for (User o : userlist) {
				usermap.put(o.getUserid(), o);
			}
			for (Api_user_sina o : list) {
				o.setUser(usermap.get(o.getUserid()));
			}
		}
		return list;
	}

	@Override
	public Map<Long, User> getUserMapInId(List<Long> idList) {
		List<User> list = this.getUserListInId(idList);
		Map<Long, User> map = new HashMap<Long, User>();
		for (User o : list) {
			map.put(o.getUserid(), o);
		}
		return map;
	}

	@Override
	public List<User> getUserListInId(List<Long> idList) {
		return this.userDao.getListInField(null, "userid", idList);
	}

	@Override
	public List<SinaUser> getSinaFansListBy(Api_user_sina apiUserSina,
			int page, int size) {
		List<SinaUser> list = new ArrayList<SinaUser>();
		try {
			List<weibo4j.User> sinalist = SinaUtil.getFansList(apiUserSina
					.getAccess_token(), apiUserSina.getToken_secret(), String
					.valueOf(apiUserSina.getSina_userid()), page, size);
			return this.buildSinaUserList(apiUserSina, sinalist);
		}
		catch (WeiboException e) {
			return list;
		}
	}

	private List<SinaUser> buildSinaUserList(Api_user_sina apiUserSina,
			List<weibo4j.User> sinalist) {
		List<SinaUser> list = new ArrayList<SinaUser>();
		List<Long> idList = new ArrayList<Long>();
		for (weibo4j.User o : sinalist) {
			idList.add(new Long(o.getId()));
		}
		Map<Long, Api_user_sina> map = this.getApi_user_sinaMapInSina_userid(
				idList, true);
		SinaUser sinaUser = null;
		for (weibo4j.User o : sinalist) {
			Api_user_sina apiUserSina2 = map.get(new Long(o.getId()));
			sinaUser = new SinaUser();
			sinaUser.setNick(o.getScreenName());
			sinaUser.setSinaUserid(o.getId());
			sinaUser.setHead(o.getProfileImageURL().toString());
			if (apiUserSina2 != null) {
				sinaUser.setUserid(apiUserSina2.getUserid());
				sinaUser.setPic_num(apiUserSina2.getUser().getPic_num());
			}
			list.add(sinaUser);
		}
		List<String> sinaUseridList = new ArrayList<String>();
		for (SinaUser o : list) {
			sinaUseridList.add(String.valueOf(o.getSinaUserid()));
		}
		List<Invitelog> loglist = this.invitelogService
				.getInvitelogListByUseridAndApi_typeAndInOtherid(apiUserSina
						.getUserid(), Api_user.API_TYPE_SINA, sinaUseridList);
		Map<String, Invitelog> logmap = new HashMap<String, Invitelog>();
		for (Invitelog o : loglist) {
			logmap.put(o.getOtherid(), o);
		}
		for (SinaUser o : list) {
			if (logmap.containsKey(String.valueOf(o.getSinaUserid()))) {
				o.setInvited(true);
			}
			else {
				o.setInvited(false);
			}
		}
		return list;
	}

	@Override
	public List<SinaUser> getSinaFriendListBy(Api_user_sina apiUserSina,
			int page, int size) {
		List<SinaUser> list = new ArrayList<SinaUser>();
		try {
			List<weibo4j.User> sinalist = SinaUtil.getFriendList(apiUserSina
					.getAccess_token(), apiUserSina.getToken_secret(), String
					.valueOf(apiUserSina.getSina_userid()), page, size);
			return this.buildSinaUserList(apiUserSina, sinalist);
		}
		catch (WeiboException e) {
			return list;
		}
	}

	public void setUseridDao(UseridDao useridDao) {
		this.useridDao = useridDao;
	}

	public void setApi_userDao(Api_userDao apiUserDao) {
		api_userDao = apiUserDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setApi_user_sinaDao(Api_user_sinaDao apiUserSinaDao) {
		api_user_sinaDao = apiUserSinaDao;
	}

	public void setInvitelogService(InvitelogService invitelogService) {
		this.invitelogService = invitelogService;
	}

	public void setHkMsgProducer(HkMsgProducer hkMsgProducer) {
		this.hkMsgProducer = hkMsgProducer;
	}
}