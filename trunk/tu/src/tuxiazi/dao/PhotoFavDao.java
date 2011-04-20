package tuxiazi.dao;

import tuxiazi.bean.PhotoFav;

import com.hk.frame.dao.query2.BaseDao;

public class PhotoFavDao extends BaseDao<PhotoFav> {

	@Override
	public Class<PhotoFav> getClazz() {
		return PhotoFav.class;
	}
}
