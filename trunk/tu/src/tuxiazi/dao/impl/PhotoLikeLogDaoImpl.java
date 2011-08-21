package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;

import org.springframework.stereotype.Component;

import tuxiazi.bean.PhotoLikeLog;
import tuxiazi.dao.PhotoLikeLogDao;

@Component("photoLikeLogDao")
public class PhotoLikeLogDaoImpl extends BaseDao<PhotoLikeLog> implements
		PhotoLikeLogDao {

	@Override
	public Class<PhotoLikeLog> getClazz() {
		return PhotoLikeLog.class;
	}
}
