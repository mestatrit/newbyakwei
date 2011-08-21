package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import tuxiazi.bean.PhotoLikeLog;
import tuxiazi.dao.PhotoLikeLogDao;

public class PhotoLikeLogDaoImpl extends BaseDao<PhotoLikeLog> implements
		PhotoLikeLogDao {

	@Override
	public Class<PhotoLikeLog> getClazz() {
		return PhotoLikeLog.class;
	}
}
