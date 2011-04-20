package tuxiazi.dao;

import tuxiazi.bean.Lasted_photo;

import com.hk.frame.dao.query2.BaseDao;

public class Lasted_photoDao extends BaseDao<Lasted_photo> {

	@Override
	public Class<Lasted_photo> getClazz() {
		return Lasted_photo.class;
	}
}
