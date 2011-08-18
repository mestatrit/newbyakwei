package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.User_photo;

public class User_photoDao extends BaseDao<User_photo> {

	@Override
	public Class<User_photo> getClazz() {
		return User_photo.class;
	}
}
