package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Photoid;

public class PhotoidDao extends BaseDao<Photoid> {

	@Override
	public Class<Photoid> getClazz() {
		return Photoid.class;
	}
}
