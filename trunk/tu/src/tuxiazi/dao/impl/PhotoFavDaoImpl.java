package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;

import org.springframework.stereotype.Component;

import tuxiazi.bean.PhotoFav;
import tuxiazi.dao.PhotoFavDao;

@Component("photoFavDao")
public class PhotoFavDaoImpl extends BaseDao<PhotoFav> implements PhotoFavDao {

	@Override
	public Class<PhotoFav> getClazz() {
		return PhotoFav.class;
	}
}
