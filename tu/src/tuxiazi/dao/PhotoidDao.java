package tuxiazi.dao;

import tuxiazi.bean.Photoid;

import com.hk.frame.dao.query2.BaseDao;

public class PhotoidDao extends BaseDao<Photoid> {

	@Override
	public Class<Photoid> getClazz() {
		return Photoid.class;
	}
}
