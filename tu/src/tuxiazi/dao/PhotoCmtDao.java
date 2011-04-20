package tuxiazi.dao;

import tuxiazi.bean.PhotoCmt;

import com.hk.frame.dao.query2.BaseDao;

public class PhotoCmtDao extends BaseDao<PhotoCmt> {

	@Override
	public Class<PhotoCmt> getClazz() {
		return PhotoCmt.class;
	}
}
