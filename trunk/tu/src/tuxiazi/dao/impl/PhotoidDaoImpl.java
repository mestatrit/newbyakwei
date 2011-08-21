package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;

import org.springframework.stereotype.Component;

import tuxiazi.bean.Photoid;
import tuxiazi.dao.PhotoidDao;

@Component("photoidDao")
public class PhotoidDaoImpl extends BaseDao<Photoid> implements PhotoidDao {

	@Override
	public Class<Photoid> getClazz() {
		return Photoid.class;
	}
}
