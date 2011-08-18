package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.PhotoCmt;

public class PhotoCmtDao extends BaseDao<PhotoCmt> {

	@Override
	public Class<PhotoCmt> getClazz() {
		return PhotoCmt.class;
	}
}
