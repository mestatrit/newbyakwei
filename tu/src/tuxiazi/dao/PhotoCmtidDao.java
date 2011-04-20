package tuxiazi.dao;

import tuxiazi.bean.PhotoCmtid;

import com.hk.frame.dao.query2.BaseDao;

public class PhotoCmtidDao extends BaseDao<PhotoCmtid> {

	@Override
	public Class<PhotoCmtid> getClazz() {
		return PhotoCmtid.class;
	}
}
