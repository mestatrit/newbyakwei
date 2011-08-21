package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import tuxiazi.bean.PhotoFav;
import tuxiazi.dao.PhotoFavDao;

public class PhotoFavDaoImpl extends BaseDao<PhotoFav> implements PhotoFavDao {

	@Override
	public Class<PhotoFav> getClazz() {
		return PhotoFav.class;
	}
}
