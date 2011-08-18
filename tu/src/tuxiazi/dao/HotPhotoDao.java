package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.HotPhoto;

public class HotPhotoDao extends BaseDao<HotPhoto> {

	@Override
	public Class<HotPhoto> getClazz() {
		return HotPhoto.class;
	}
}
