package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Lasted_photo;

public class Lasted_photoDao extends BaseDao<Lasted_photo> {

	@Override
	public Class<Lasted_photo> getClazz() {
		return Lasted_photo.class;
	}
}
