package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;

import java.util.List;

import org.springframework.stereotype.Component;

import tuxiazi.bean.User_photo;
import tuxiazi.dao.User_photoDao;

@Component("user_photoDao")
public class User_photoDaoImpl extends BaseDao<User_photo> implements
		User_photoDao {

	@Override
	public Class<User_photo> getClazz() {
		return User_photo.class;
	}

	public int deleteByUseridAndPhotoid(long userid, long photoid) {
		return this.delete(null, "userid=? and photoid=?", new Object[] {
				userid, photoid });
	}

	public int countByUserid(long userid) {
		return this.count("userid=?", new Object[] { userid });
	}

	public List<User_photo> getListByUserid(long userid, int begin, int size) {
		return this.getList("userid=?", new Object[] { userid },
				"photoid desc", begin, size);
	}
}
