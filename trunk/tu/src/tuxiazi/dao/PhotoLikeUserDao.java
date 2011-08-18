package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.PhotoLikeUser;

public class PhotoLikeUserDao extends BaseDao<PhotoLikeUser> {

	@Override
	public Class<PhotoLikeUser> getClazz() {
		return PhotoLikeUser.class;
	}
}
