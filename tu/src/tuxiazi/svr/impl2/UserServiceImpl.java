package tuxiazi.svr.impl2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.SinaUser;
import tuxiazi.bean.SinaUserFromAPI;
import tuxiazi.bean.User;
import tuxiazi.dao.Api_user_sinaDao;
import tuxiazi.svr.exception.UserAlreadyExistException;
import tuxiazi.svr.iface.UserService;
import tuxiazi.svr.impl.jms.HkMsgProducer;
import tuxiazi.svr.impl.jms.JmsMsg;
import tuxiazi.svr.impl.jms.JsonKey;
import tuxiazi.web.util.SinaUtil;
import weibo4j.WeiboException;

@Component("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private HkMsgProducer hkMsgProducer;

	@Autowired
	private Api_user_sinaDao api_user_sinaDao;

	@Override
	public User createUserFromSina(SinaUserFromAPI sinaUserFromAPI)
			throws UserAlreadyExistException {
		Api_user_sina api_user_sina = this.api_user_sinaDao
				.getById(sinaUserFromAPI.getSinaUserId());
		if (api_user_sina != null) {
			throw new UserAlreadyExistException("user already exist. userid:[ "
					+ api_user_sina.getUserid() + " ]");
		}
		User user = new User(sinaUserFromAPI);
		user.save();
		JmsMsg jmsMsg = new JmsMsg();
		jmsMsg.setHead(JmsMsg.HEAD_PHOTO_CREATEUSER);
		jmsMsg.addData(JsonKey.userid,
				String.valueOf(user.getApi_user_sina().getUserid()));
		jmsMsg.addData(JsonKey.sina_userid,
				String.valueOf(user.getApi_user_sina().getSina_userid()));
		jmsMsg.addData(JsonKey.access_token, user.getApi_user_sina()
				.getAccess_token());
		jmsMsg.addData(JsonKey.token_secret, user.getApi_user_sina()
				.getToken_secret());
		jmsMsg.buildBody();
		this.hkMsgProducer.send(jmsMsg.toMessage());
		return user;
	}

	@Override
	public void update(User user) {
		user.update();
	}

	@Override
	public void updateApi_user_sina(Api_user_sina apiUserSina) {
		apiUserSina.update();
	}

	private Map<Long, Api_user_sina> getApi_user_sinaMapInSina_userid(
			List<Long> idList, boolean buildUser) {
		if (idList.isEmpty()) {
			return new HashMap<Long, Api_user_sina>(0);
		}
		List<Api_user_sina> list = this.api_user_sinaDao.getListInSina_userid(
				idList, buildUser);
		Map<Long, Api_user_sina> map = new HashMap<Long, Api_user_sina>();
		for (Api_user_sina o : list) {
			map.put(o.getSina_userid(), o);
		}
		return map;
	}

	@Override
	public List<SinaUser> getSinaFansList(Api_user_sina apiUserSina, int page,
			int size) {
		List<SinaUser> list = new ArrayList<SinaUser>();
		try {
			List<weibo4j.User> sinalist = SinaUtil.getFansList(
					apiUserSina.getAccess_token(),
					apiUserSina.getToken_secret(),
					String.valueOf(apiUserSina.getSina_userid()), page, size);
			return this.buildSinaUserList(sinalist);
		}
		catch (WeiboException e) {
			return list;
		}
	}

	@Override
	public List<SinaUser> getSinaFriendList(Api_user_sina apiUserSina,
			int page, int size) {
		List<SinaUser> list = new ArrayList<SinaUser>();
		try {
			List<weibo4j.User> sinalist = SinaUtil.getFriendList(
					apiUserSina.getAccess_token(),
					apiUserSina.getToken_secret(),
					String.valueOf(apiUserSina.getSina_userid()), page, size);
			return this.buildSinaUserList(sinalist);
		}
		catch (WeiboException e) {
			return list;
		}
	}

	private List<SinaUser> buildSinaUserList(List<weibo4j.User> sinalist) {
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
		return list;
	}
}