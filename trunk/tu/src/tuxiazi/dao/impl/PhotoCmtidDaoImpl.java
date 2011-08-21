package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;

import org.springframework.stereotype.Component;

import tuxiazi.bean.PhotoCmtid;
import tuxiazi.dao.PhotoCmtidDao;

@Component("photoCmtidDao")
public class PhotoCmtidDaoImpl extends BaseDao<PhotoCmtid> implements
		PhotoCmtidDao {

	@Override
	public Class<PhotoCmtid> getClazz() {
		return PhotoCmtid.class;
	}
}
