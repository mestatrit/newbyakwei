package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import tuxiazi.bean.PhotoCmtid;
import tuxiazi.dao.PhotoCmtidDao;

public class PhotoCmtidDaoImpl extends BaseDao<PhotoCmtid> implements
		PhotoCmtidDao {

	@Override
	public Class<PhotoCmtid> getClazz() {
		return PhotoCmtid.class;
	}
}
