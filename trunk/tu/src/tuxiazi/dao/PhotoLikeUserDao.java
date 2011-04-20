package tuxiazi.dao;

import tuxiazi.bean.PhotoLikeUser;

import com.hk.frame.dao.query2.BaseDao;

public class PhotoLikeUserDao extends BaseDao<PhotoLikeUser> {

	@Override
	public Class<PhotoLikeUser> getClazz() {
		return PhotoLikeUser.class;
	}
}
