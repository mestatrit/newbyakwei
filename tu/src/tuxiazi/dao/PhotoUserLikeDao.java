package tuxiazi.dao;

import tuxiazi.bean.PhotoUserLike;

import com.hk.frame.dao.query2.BaseDao;

public class PhotoUserLikeDao extends BaseDao<PhotoUserLike> {

	@Override
	public Class<PhotoUserLike> getClazz() {
		return PhotoUserLike.class;
	}
}
