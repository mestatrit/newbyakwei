package tuxiazi.dao;

import tuxiazi.bean.HotPhoto;

import com.hk.frame.dao.query2.BaseDao;

public class HotPhotoDao extends BaseDao<HotPhoto> {

	@Override
	public Class<HotPhoto> getClazz() {
		return HotPhoto.class;
	}
}
