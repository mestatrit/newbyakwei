package tuxiazi.dao;

import tuxiazi.bean.User_photo;

import com.hk.frame.dao.query2.BaseDao;

public class User_photoDao extends BaseDao<User_photo> {

	@Override
	public Class<User_photo> getClazz() {
		return User_photo.class;
	}
}
