package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.PhotoFav;

public class PhotoFavDao extends BaseDao<PhotoFav> {

	@Override
	public Class<PhotoFav> getClazz() {
		return PhotoFav.class;
	}
}
