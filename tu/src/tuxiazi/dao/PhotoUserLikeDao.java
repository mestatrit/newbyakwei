package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.PhotoUserLike;

public class PhotoUserLikeDao extends BaseDao<PhotoUserLike> {

	@Override
	public Class<PhotoUserLike> getClazz() {
		return PhotoUserLike.class;
	}
}
