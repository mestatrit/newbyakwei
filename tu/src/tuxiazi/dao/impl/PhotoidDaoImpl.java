package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Photoid;
import tuxiazi.dao.PhotoidDao;

public class PhotoidDaoImpl extends BaseDao<Photoid> implements PhotoidDao {

	@Override
	public Class<Photoid> getClazz() {
		return Photoid.class;
	}
}
