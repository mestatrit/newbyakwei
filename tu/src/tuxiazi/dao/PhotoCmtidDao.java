package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.PhotoCmtid;

public class PhotoCmtidDao extends BaseDao<PhotoCmtid> {

	@Override
	public Class<PhotoCmtid> getClazz() {
		return PhotoCmtid.class;
	}
}
