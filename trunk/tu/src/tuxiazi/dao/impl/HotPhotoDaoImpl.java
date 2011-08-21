package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;

import java.util.List;

import org.springframework.stereotype.Component;

import tuxiazi.bean.HotPhoto;
import tuxiazi.dao.HotPhotoDao;

@Component("hotPhotoDao")
public class HotPhotoDaoImpl extends BaseDao<HotPhoto> implements HotPhotoDao {

	@Override
	public Class<HotPhoto> getClazz() {
		return HotPhoto.class;
	}

	public int deleteByPhotoid(long photoid) {
		return this.delete(null, "photoid=?", new Object[] { photoid });
	}

	public List<HotPhoto> getList(int begin, int size) {
		return this.getList(null, null, "oid desc", begin, size);
	}
}
