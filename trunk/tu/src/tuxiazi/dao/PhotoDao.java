package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Photo;

public class PhotoDao extends BaseDao<Photo> {

	@Override
	public Class<Photo> getClazz() {
		return Photo.class;
	}
}
