package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.User;
import tuxiazi.dao.Api_user_sinaDao;
import tuxiazi.dao.UserDao;

@Component("api_user_sinaDao")
public class Api_user_sinaDaoImpl extends BaseDao<Api_user_sina> implements
		Api_user_sinaDao {

	@Autowired
	private UserDao userDao;

	@Override
	public Class<Api_user_sina> getClazz() {
		return Api_user_sina.class;
	}

	public Api_user_sina getByUserid(long userid) {
		return this.getObject("userid=?", new Object[] { userid });
	}

	@Override
	public List<Api_user_sina> getListInSina_userid(List<Long> idList,
			boolean buildUser) {
		if (idList.isEmpty()) {
			return new ArrayList<Api_user_sina>(0);
		}
		List<Api_user_sina> list = this.getListInField("sina_userid", idList);
		if (buildUser) {
			List<Long> id2List = new ArrayList<Long>();
			for (Api_user_sina o : list) {
				id2List.add(o.getUserid());
			}
			List<User> userlist = this.userDao.getListInId(id2List);
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
}
